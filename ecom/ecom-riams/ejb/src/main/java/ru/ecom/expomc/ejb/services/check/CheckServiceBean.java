package ru.ecom.expomc.ejb.services.check;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.check.CheckProperty;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlMultipleQueriesSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.ISqlSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.NativeSqlContext;
import ru.ecom.expomc.ejb.services.check.impl.CheckContext;
import ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm;
import ru.ecom.expomc.ejb.services.importservice.ImportServiceBean;
import ru.ecom.expomc.ejb.services.voc.allvalues.AllowedChecksAllValues;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Проверка реестра
 */
@Stateless
@Remote(ICheckService.class)
@Local(ICheckServiceLocal.class)
public class CheckServiceBean implements ICheckService, ICheckServiceLocal {
    /// удалить, нигде не используется
    private static final Logger LOG = Logger.getLogger(CheckServiceBean.class);
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
    private final AllowedChecksAllValues allowedChecksAllValues = new AllowedChecksAllValues();
    @PersistenceContext
    public EntityManager manager;
    private @EJB
    ILocalMonitorService monitorService;
    private @PersistenceUnit
    EntityManagerFactory factory;

    private static void setPropertyValue(Object aObject, String aProperty, Object aValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        PropertyUtil.setPropertyValue(aObject, aProperty, aValue);
    }

    private static void copyToMap(Object aEntity, Map<String, Object> aMap, Class aClass) throws IllegalAccessException, InvocationTargetException {
        for (Method method : aClass.getMethods()) {
            if (method.isAnnotationPresent(Comment.class)) {
                String property = PropertyUtil.getPropertyName(method);
                aMap.put(property, PropertyUtil.getPropertyValue(aEntity, property));
            }
        }

    }

    private Collection<CheckPair> createIteratorsChecks(Collection<Check> aChecks, boolean aSqlSupports) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
        LinkedList<CheckPair> ret = new LinkedList<>();
        for (Check check : aChecks) {
            if (!check.isDisabled()) {
                ICheck icheck = (ICheck) findCheckClass(check).newInstance();
                boolean checkImplementsSqlSupports = icheck instanceof ISqlSupports;
                boolean canAdd = false;
                if (checkImplementsSqlSupports && aSqlSupports) {
                    canAdd = true;
                } else if (!checkImplementsSqlSupports && !aSqlSupports) {
                    canAdd = true;
                }
                if (canAdd) {
                    CheckPair pair = new CheckPair(check, icheck);
                    setCheckProperties(pair.check, pair.icheck);
                    ret.add(pair);
                }
            }

        }
        return ret;
    }

    @Override
    public void checkDocumentData(long aMonitorId, long aTime) throws CheckDocumentDataException {
        IMonitor monitor = monitorService.acceptMonitor(aMonitorId, "Начало проверки ...");
        try {
            ImportTime time = manager.find(ImportTime.class, aTime);

            ImportDocument document = time.getDocument();

            Format format = (Format) time.getFormat();

            // перебор всех записей в реестре

            Query countQuery;
            if (document.isTimeSupport()) {
                countQuery = manager.createQuery("select count(*) from " + document.getEntityClassName() + " where time =:time")
                        .setParameter("time", aTime);
            } else {
                countQuery = manager.createQuery("select count(*) from " + document.getEntityClassName());
            }
            Long count = (Long) countQuery.getSingleResult();

            monitor = monitorService.startMonitor(aMonitorId, "Проверка", count);
            monitor.setText("Удаление старых проверок ...");
            manager.persist(time);


            monitor.setText("Создание списка полей...");
            Class entityClass = ClassLoaderHelper.getInstance().loadClass(document.getEntityClassName());
            // список полей
            HashSet<String> allowedFields = new HashSet<>();
            for (Method method : entityClass.getMethods()) {
                if (method.getAnnotation(Comment.class) != null) {
                    allowedFields.add(PropertyUtil.getPropertyName(method));
                }
            }

            monitor.setText("Создание списка проверок по SQL...");
            Collection<CheckPair> sqlPairs = createIteratorsChecks(document.getChecks(), true);
            executeSqlSupports(entityClass, sqlPairs, aTime, monitor);

            Collection<CheckPair> list = createIteratorsChecks(document.getChecks(), false);
            int i = 0;
            monitor.setText("Другие проверки...");
            Query query;
            if (document.isTimeSupport()) {
                query = manager.createQuery("from " + document.getEntityClassName() + " c where time =:time").setParameter("time", aTime);
            } else {
                query = manager.createQuery("from " + document.getEntityClassName());
            }
            Iterator iterator = QueryIteratorUtil.iterate(query);
            while (iterator.hasNext()) {
                Object entity = iterator.next();
                if (++i % 10 == 0) {
                    if (monitor.isCancelled()) throw new IllegalMonitorStateException("Прервано пользователем");
                    monitor.advice(10);
                    monitor.setText("Проверяется " + i + " из " + count + "...");
                }

                // копируем все в MAP
                HashMap<String, Object> map = new HashMap<>();
                copyToMap(entity, map, entityClass);
                CheckContext ctx = new CheckContext(format, map, allowedFields, time.getActualDateFrom(), manager, entity);

                // проверки
                for (CheckPair pair : list) {
                    CheckResult result;
                    try {
                        result = pair.icheck.check(ctx);

                    } catch (Exception e) {
                        String error = "Ошибка при сохранении результатов: [entity="
                                + entity
                                + ", check = " + pair.check
                                + ", map = " + map
                                + "]";
                        LOG.error(error, e);
                        result = new CheckResult();
                        result.setAccepted(true);
                    }

                    if (result.isAccepted()) {
                        try {
                            storeResult(entity,  result, pair.check, map);
                        } catch (Exception e) {
                            String error = "Ошибка при сохранении результатов: [entity="
                                    + entity
                                    + ", check = " + pair.check
                                    + ", result = " + result
                                    + ", map = " + map
                                    + "]";
                            LOG.error(error, e);
                            throw new IllegalArgumentException(error, e);
                        }
                    }
                }
                manager.flush();
                manager.clear();
            }
            monitor.finish(String.valueOf(aTime));
        } catch (Exception e) {
            LOG.error("Ошибка: " + e, e);
            if (monitor != null) monitor.error(e.getMessage(), e);
            throw new CheckDocumentDataException("Ошибка проверки: " + e.getMessage(), e);
        }
    }

    private void executeSqlSupports(Class aEntityClass, Collection<CheckPair> sqlPairs, long aTimeId, IMonitor aMonitor) {
        String tableName = EntityHelper.getInstance().getTableName(aEntityClass);
        for (CheckPair pair : sqlPairs) {
            if (aMonitor.isCancelled()) throw new IllegalMonitorStateException("Прервано пользователем");
            long checkId = pair.check.getId();
            NativeSqlContext ctx = new NativeSqlContext(manager);
            ctx.setCheckId(checkId);
            ctx.setTimeId(aTimeId);
            ctx.setTableName(tableName);
            ctx.setEntityClass(aEntityClass);

            if (pair.icheck instanceof INativeSqlMultipleQueriesSupports) {
                INativeSqlMultipleQueriesSupports multi = (INativeSqlMultipleQueriesSupports) pair.icheck;
                Collection<String> queries = multi.getNativeSql(ctx);
                for (String query : queries) {
                    executeUpdate(aMonitor, query, pair);
                }
            } else {
                INativeSqlSupports nativeSqlSupports = (INativeSqlSupports) pair.icheck;
                String query = nativeSqlSupports.getNativeSql(ctx);
                executeUpdate(aMonitor, query, pair);
            }
        }
    }

    private void executeUpdate(IMonitor aMonitor, String aQuery, CheckPair aPair) {
        aMonitor.setText(aPair.check.getName() + "<span style='margin-left: 1em; color: gray; font-weight: normal;'>" + aQuery + "</query>");
        LOG.debug(aQuery);
        int i = manager.createNativeQuery(aQuery).executeUpdate();
        LOG.debug("count = " + i);
    }

    @Override
    public void setCheckProperties(Check aCheck, ICheck aChecker) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
        for (CheckProperty property : aCheck.getProperties()) {
            setPropertyValue(aChecker, property.getProperty(), property.getValue());
        }
    }

    private Class findCheckClass(Check aCheck) {
        return allowedChecksAllValues.getCheckClassById(aCheck.getCheckId());
    }

    @Override
    public Collection<CheckPropertyRow> listProperties(long aCheckId) {
        Check check = manager.find(Check.class, aCheckId);

        Collection<CheckProperty> properties = check.getProperties();

        TreeMap<String, String> values = new TreeMap<>();
        TreeMap<String, Long> ids = new TreeMap<>();
        for (CheckProperty property : properties) {
            values.put(property.getProperty(), property.getValue());
            ids.put(property.getProperty(), property.getId());
        }

        Class clazz = findCheckClass(check);
        LinkedList<CheckPropertyRow> ret = new LinkedList<>();
        for (Method method : clazz.getMethods()) {
            Comment comment = method.getAnnotation(Comment.class);
            if (comment != null) {
                CheckPropertyRow row = new CheckPropertyRow();
                row.setProperty(PropertyUtil.getPropertyName(method));
                row.setComment(comment.value());
                row.setValue(values.get(row.getProperty()));
                if (ids.containsKey(row.getProperty())) {
                    row.setId(ids.get(row.getProperty()));
                }
                row.setCheck(aCheckId);
                ret.add(row);
            }
        }
        return ret;
    }

    @Override
    public CheckPropertyForm loadForm(long aCheckId, String aProperty) throws ClassNotFoundException {
        Collection<CheckPropertyRow> rows = listProperties(aCheckId);
        String value = null;
        Long id = null;
        for (CheckPropertyRow row : rows) {
            if (row.getProperty().equals(aProperty)) {
                value = row.getValue();
                id = row.getId();
                break;
            }
        }
        CheckPropertyForm form = new CheckPropertyForm();
        form.setId(id);
        form.setCheck(aCheckId);
        form.setProperty(aProperty);
        form.setValue(value);
        form.setTypeSave();
        return form;
    }

    @Override
    public void saveForm(CheckPropertyForm aForm) throws ClassNotFoundException {
        CheckPropertyForm fromBaseForm = loadForm(aForm.getCheck(), aForm.getProperty());
        CheckProperty property;
        if (fromBaseForm.getId() != null) {
            property = manager.find(CheckProperty.class, fromBaseForm.getId());
        } else {
            Check check = manager.find(Check.class, aForm.getCheck());
            property = new CheckProperty();
            property.setCheck(check);
            property.setProperty(aForm.getProperty());
        }
        property.setValue(aForm.getValue());
        manager.persist(property);
    }

    private void storeResult(Object aEntity, CheckResult aResult, Check aCheck, Map<String, Object> aOldValues) throws IllegalAccessException, NoSuchMethodException, ParseException, InvocationTargetException {
        if (aCheck.getCheckType() == Check.TYPE_CHANGE) {
            Map<String, Object> map = aResult.getChanged();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                setPropertyValue(aEntity, entry.getKey(), entry.getValue());
                aOldValues.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private static class CheckPair {
        private final Check check;
        private final ICheck icheck;

        private CheckPair(Check aCheck, ICheck aIcheck) {
            check = aCheck;
            icheck = aIcheck;
        }
    }

}
