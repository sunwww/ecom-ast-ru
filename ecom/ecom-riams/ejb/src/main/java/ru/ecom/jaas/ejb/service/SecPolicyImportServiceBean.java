package ru.ecom.jaas.ejb.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.nuzmsh.util.StringUtil;

/**
 * Импорт политик безопасности
 */
@Stateless
@Remote(ISecPolicyImportService.class)
@Local(ISecPolicyImportService.class)
public class SecPolicyImportServiceBean implements ISecPolicyImportService {

    private final static Logger LOG = Logger.getLogger(SecPolicyImportServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
    public Long addPolicy(String aPolicy, String aName) {
    	Map<String, SecPolicy> hash = new HashMap<String, SecPolicy>() ;
    	Map<String,String> names = new CreateReplaceMapHelper().create() ;
    	hash.put("/", findRootPolicy()) ;    	
    	SecPolicy policy = importPolicy(aPolicy, hash, names,null) ;
    	// hash.get(aPolicy) ;
    	if (policy!=null ) {
    		if (!StringUtil.isNullOrEmpty(aName) && StringUtil.isNullOrEmpty(policy.getName())) {
    			policy.setName(aName) ;
    			policy.setComment(aName) ;
    			theManager.persist(policy) ;
    		}
    		return policy.getId() ;
    	}
    	return null ;
    }

    private SecPolicy findRootPolicy() {
    	SecPolicy policy = QueryResultUtil.getFirst(SecPolicy.class
    			, theManager.createQuery("from SecPolicy where key=:key")
    				.setParameter("key", "/")) ; 
        if (policy == null) {
            //throw new IllegalStateException("Нет корневой политики с идентификатором 1");
            policy = new SecPolicy();
            //policy.setId(1L);
            policy.setKey("/");
            policy.setName("ROOT");
            policy.setComment("Корневая политика безопасности");
            theManager.persist(policy);
        }
        return policy;
    }

//    private SecPolicy findPolicyByKey(String aStr) {
//        List<SecPolicy> list = theManager.createQuery("from SecPolicy where key = :key").setParameter("key", aStr).getResultList() ;
//        return list!=null && !list.isEmpty() ? list.iterator().next() : null ;
//    }


    public void addToRole(long aRoleId, String aStr) {
        SecPolicy policy = importPolicy(aStr);
        policy = theManager.find(SecPolicy.class, policy.getId()) ;
        SecRole role = theManager.find(SecRole.class, aRoleId);
        if (!role.getSecPolicies().contains(policy)) {

            role.getSecPolicies().add(policy);
            theManager.persist(role);
        }
    }

    /**
     * С поддержкой кэша
     * @param aPolicies массив политик
     * @param aNames    перевод политик
     */
    public void importPolicies(long aMonitorId, Collection<String> aPolicies, Map<String,String> aNames, Map<String,String> aComments) {
    	if(aNames==null) aNames = new CreateReplaceMapHelper().create() ;
    	IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
    	try {
    		monitor = theMonitorService.startMonitor(aMonitorId, "Импорт политик безопасности", aPolicies.size()) ;
        	Map<String, SecPolicy> hash = new HashMap<String, SecPolicy>() ;
        	hash.put("/", findRootPolicy()) ;
        	for(String policy : aPolicies) {
        		if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
        		monitor.advice(1) ;
        		monitor.setText("Импортируется "+policy);
        		if (CAN_DEBUG)
    				LOG.debug("importPolicies: policy = " + policy); 
        		importPolicy(policy, hash, aNames, aComments) ;
        	}
        	monitor.finish(hash.get("/").getId()+"") ;
    	} catch (Exception e) {
    		monitor.error(e.getMessage(), e) ;
    	}
    	
    }
    
    private SecPolicy importPolicy(String aPolicyLine, Map<String, SecPolicy> aHash, Map<String,String> aNames, Map<String,String> aComments) {
    	// уже импортировали
    	if(aHash.containsKey(aPolicyLine)) {
    		return null;
    	} else { // поиск от самой верхней
    		StringTokenizer st = new StringTokenizer(aPolicyLine, " /\\\"'\t,.:|\n") ; 
    		StringBuilder sb = new StringBuilder("") ;
    		SecPolicy parentPolicy = aHash.get("/") ; //findOrCreatePolicy("/", aHash) ;
    		while(st.hasMoreTokens()) {
    			String key = st.nextToken() ;
    			if(sb.length()!=1) sb.append(key) ;
    			parentPolicy = findOrCreatePolicy(parentPolicy, key, aHash, sb.toString(), aNames) ;
    		}
    		return parentPolicy ;
    	}
    }
    
    
    
    private SecPolicy findOrCreatePolicy(SecPolicy aParentPolicy, String aKey, Map<String, SecPolicy> aHash, String aFullPath, Map<String,String> aNames) {
    	SecPolicy policy = aHash.get(aFullPath) ;
    	if(policy==null) {
    		policy = QueryResultUtil.getFirst(SecPolicy.class
    				, theManager.createQuery("from SecPolicy where key=:key and parentSecPolicy=:parent")
    		  .setParameter("key", aKey)
    		  .setParameter("parent", aParentPolicy));
    		if(policy==null) {
    			
    			policy = new SecPolicy() ;
    			policy.setKey(aKey) ;
    			// name
    			policy.setParentSecPolicy(aParentPolicy) ;
    			theManager.persist(policy) ;
    			aHash.put(aFullPath, policy) ;
    		}
    	}
		
    	String name = aNames.get(aFullPath) ;
		if(name==null) name = aNames.get(aKey) ;
		if(name!=null) policy.setName(name) ;
		
    	return policy ;
	}

	public SecPolicy importPolicy(String aStr, Map<String, String> aNames) {
        if (CAN_DEBUG) LOG.debug("importPolicy() " + aStr+" "+aNames);
        for (Map.Entry<String, String> entry : aNames.entrySet()) {
            theMap.put(entry.getKey(), entry.getValue());
        }
        return importPolicy(aStr);
    }

    
    public SecPolicy importPolicy(String aStr) {
        if (CAN_DEBUG) LOG.debug("importPolicy() " + aStr);
        SecPolicy parentPolicy = findRootPolicy(); //theManager.find(SecPolicy.class, ROOT_POLICY_ID) ;
        StringTokenizer st = new StringTokenizer(aStr, "/ ");
        while (st.hasMoreTokens()) {
            parentPolicy = createOrFindPolicy(parentPolicy, st.nextToken());
        }
        theManager.flush();
        theManager.clear();
        return parentPolicy;
    }


    private SecPolicy createOrFindPolicy(SecPolicy aParentPolicy, String aKey) {
        if (aParentPolicy == null) throw new IllegalArgumentException("aParentPolicy не должен быть равен NULL");
//        System.out.println("aParentPolicy = " + aParentPolicy.getKey());
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
            if (theMap.containsKey(aKey)) {
                ret.setName(theMap.get(aKey));
            } else {
                ret.setName(aKey);
            }
            theManager.persist(ret);
        } else {
            if (CAN_DEBUG) LOG.debug("aKey = " + aKey);
            if (theMap.containsKey(aKey)) {
                ret.setName(theMap.get(aKey));
                theManager.persist(ret);
            }
        }
        if (CAN_DEBUG) LOG.debug("ret = " + ret);
        return ret;
    }

    public void standartPolicyByParent(Long aParentPolicy) {
    	SecPolicy parentpolicy = theManager.find(SecPolicy.class,aParentPolicy) ;
    	SecPolicy view = createOrFindPolicy(parentpolicy,"View") ;
    	if (CAN_DEBUG&&view!=null) LOG.debug("Создана политика View");
    	SecPolicy delete = createOrFindPolicy(parentpolicy,"Delete") ;
    	if (CAN_DEBUG&&delete!=null) LOG.debug("Создана политика Delete");
    	SecPolicy create = createOrFindPolicy(parentpolicy,"Create") ;
    	if (CAN_DEBUG&&create!=null) LOG.debug("Создана политика Create");
    	SecPolicy edit = createOrFindPolicy(parentpolicy,"Edit") ;
    	if (CAN_DEBUG&&edit!=null) LOG.debug("Создана политика Edit");
    }
    
    private @PersistenceContext EntityManager theManager;
    private @EJB ILocalMonitorService theMonitorService ;
    private Map<String, String> theMap = new CreateReplaceMapHelper().create();
}
