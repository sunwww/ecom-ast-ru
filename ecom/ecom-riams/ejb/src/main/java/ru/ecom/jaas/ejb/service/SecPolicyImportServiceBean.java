package ru.ecom.jaas.ejb.service;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.nuzmsh.util.StringUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Импорт политик безопасности
 */
@Stateless
@Remote(ISecPolicyImportService.class)
@Local(ISecPolicyImportService.class)
public class SecPolicyImportServiceBean implements ISecPolicyImportService {

    @Override
    public Long addPolicy(String aPolicy, String aName) {
        Map<String, SecPolicy> hash = new HashMap<>();
        Map<String, String> names = new CreateReplaceMapHelper().create();
        hash.put("/", findRootPolicy());
        SecPolicy policy = importPolicy(aPolicy, hash, names, null);
        if (policy != null) {
            if (!StringUtil.isNullOrEmpty(aName) && StringUtil.isNullOrEmpty(policy.getName())) {
                policy.setName(aName);
                policy.setComment(aName);
                manager.persist(policy);
            }
            return policy.getId();
        }
        return null;
    }

    private SecPolicy findRootPolicy() {
        SecPolicy policy = QueryResultUtil.getFirst(SecPolicy.class
                , manager.createQuery("from SecPolicy where key=:key")
                        .setParameter("key", "/"));
        if (policy == null) {
            policy = new SecPolicy();
            policy.setKey("/");
            policy.setName("ROOT");
            policy.setComment("Корневая политика безопасности");
            manager.persist(policy);
        }
        return policy;
    }

    @Override
    public void addToRole(long aRoleId, String aStr) {
        SecPolicy policy = importPolicy(aStr);
        policy = manager.find(SecPolicy.class, policy.getId());
        SecRole role = manager.find(SecRole.class, aRoleId);
        if (!role.getSecPolicies().contains(policy)) {
            role.getSecPolicies().add(policy);
            manager.persist(role);
        }
    }

    /**
     * С поддержкой кэша
     *
     * @param aPolicies массив политик
     * @param aNames    перевод политик
     */
    @Override
    public void importPolicies(long aMonitorId, Collection<String> aPolicies, Map<String, String> aNames, Map<String, String> aComments) {
        if (aNames == null) aNames = new CreateReplaceMapHelper().create();
        IMonitor monitor = monitorService.acceptMonitor(aMonitorId, "Подготовка к импорту");
        try {
            monitor = monitorService.startMonitor(aMonitorId, "Импорт политик безопасности", aPolicies.size());
            Map<String, SecPolicy> hash = new HashMap<>();
            hash.put("/", findRootPolicy());
            for (String policy : aPolicies) {
                if (monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем");
                monitor.advice(1);
                monitor.setText("Импортируется " + policy);
                importPolicy(policy, hash, aNames, aComments);
            }
            monitor.finish(hash.get("/").getId() + "");
        } catch (Exception e) {
            monitor.error(e.getMessage(), e);
        }

    }

    private SecPolicy importPolicy(String aPolicyLine, Map<String, SecPolicy> aHash, Map<String, String> aNames, Map<String, String> aComments) {
        // уже импортировали
        if (aHash.containsKey(aPolicyLine)) {
            return null;
        } else { // поиск от самой верхней
            StringTokenizer st = new StringTokenizer(aPolicyLine, " /\\\"'\t,.:|\n");
            StringBuilder sb = new StringBuilder();
            SecPolicy parentPolicy = aHash.get("/");
            while (st.hasMoreTokens()) {
                String key = st.nextToken();
                if (sb.length() != 1) sb.append(key);
                parentPolicy = findOrCreatePolicy(parentPolicy, key, aHash, sb.toString(), aNames);
            }
            return parentPolicy;
        }
    }

    private SecPolicy findOrCreatePolicy(SecPolicy aParentPolicy, String aKey, Map<String, SecPolicy> aHash, String aFullPath, Map<String, String> aNames) {
        SecPolicy policy = aHash.get(aFullPath);
        if (policy == null) {
            policy = QueryResultUtil.getFirst(SecPolicy.class
                    , manager.createQuery("from SecPolicy where key=:key and parentSecPolicy=:parent")
                            .setParameter("key", aKey)
                            .setParameter("parent", aParentPolicy));
            if (policy == null) {

                policy = new SecPolicy();
                policy.setKey(aKey);
                // name
                policy.setParentSecPolicy(aParentPolicy);
                manager.persist(policy);
                aHash.put(aFullPath, policy);
            }
        }

        String name = aNames.get(aFullPath);
        if (name == null) name = aNames.get(aKey);
        if (name != null) policy.setName(name);

        return policy;
    }

    public SecPolicy importPolicy(String aStr, Map<String, String> aNames) {
        map.putAll(aNames);
        return importPolicy(aStr);
    }


    public SecPolicy importPolicy(String aStr) {
        SecPolicy parentPolicy = findRootPolicy();
        StringTokenizer st = new StringTokenizer(aStr, "/ ");
        while (st.hasMoreTokens()) {
            parentPolicy = createOrFindPolicy(parentPolicy, st.nextToken());
        }
        manager.flush();
        manager.clear();
        return parentPolicy;
    }

    private SecPolicy createOrFindPolicy(SecPolicy aParentPolicy, String aKey) {
        return createOrFindPolicy(aParentPolicy, aKey, "");
    }

    private SecPolicy createOrFindPolicy(SecPolicy aParentPolicy, String aKey, String aComment) {
        if (aParentPolicy == null) throw new IllegalArgumentException("aParentPolicy не должен быть равен NULL");
        SecPolicy ret = null;

        if (aParentPolicy.getChildsSecPolicies() != null) {
            for (SecPolicy policy : aParentPolicy.getChildsSecPolicies()) {
                if (policy.getKey() != null && policy.getKey().equals(aKey)) {
                    ret = policy;
                }
            }
        }
        if (ret == null) {
            ret = new SecPolicy();
            ret.setParentSecPolicy(aParentPolicy);
            ret.setKey(aKey);
            ret.setComment(aComment);
            if (map.containsKey(aKey)) {
                ret.setName(map.get(aKey));
            } else {
                ret.setName(aKey);
            }
            manager.persist(ret);
        } else {
            if (map.containsKey(aKey)) {
                ret.setName(map.get(aKey));
                manager.persist(ret);
            }
        }
        return ret;
    }

    public void standartPolicyByParent(Long aParentPolicy) {
        SecPolicy parentpolicy = manager.find(SecPolicy.class, aParentPolicy);
        createOrFindPolicy(parentpolicy, "View", "Просмотр объекта");
        createOrFindPolicy(parentpolicy, "Delete", "Удаление объекта");
        createOrFindPolicy(parentpolicy, "Create", "Создание объекта");
        createOrFindPolicy(parentpolicy, "Edit", "Редактирование объекта");
    }

    private @PersistenceContext
    EntityManager manager;
    private @EJB
    ILocalMonitorService monitorService;
    private final Map<String, String> map = new CreateReplaceMapHelper().create();
}
