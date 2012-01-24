package ru.ecom.expomc.ejb.services.check;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

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
import ru.ecom.expomc.ejb.domain.message.Message;
//import ru.ecom.expomc.ejb.domain.message.MessageChange;
//import ru.ecom.expomc.ejb.domain.message.MessageLog;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlMultipleQueriesSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.ISqlSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.NativeSqlContext;
import ru.ecom.expomc.ejb.services.check.impl.CheckContext;
import ru.ecom.expomc.ejb.services.check.result.ResultLog;
import ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm;
import ru.ecom.expomc.ejb.services.importservice.ImportServiceBean;
import ru.ecom.expomc.ejb.services.voc.allvalues.AllowedChecksAllValues;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;

/**
 * Проверка реестра
 */
@Stateless
@Remote(ICheckService.class)
@Local(ICheckServiceLocal.class) 
public class CheckServiceBean implements ICheckService, ICheckServiceLocal {

    private final static Logger LOG = Logger.getLogger(CheckServiceBean.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private void clearChecks(ImportTime aTime) {
    	EntityManager manager = theFactory.createEntityManager() ;
    	
//    	manager.createNativeQuery("delete from MESSAGE_CHANGE where message_id in " 
//    				+"(select id from MESSAGE where importTime_id="+aTime.getId()+")")
//    				.executeUpdate() ;
//    	manager.createNativeQuery("delete from MESSAGE_LOG where message_id in " 
//				+"(select id from MESSAGE where importTime_id="+aTime.getId()+")")
//				.executeUpdate() ;
//    	manager.createQuery("delete from Message where importTime=:time").setParameter("time", aTime).executeUpdate() ;
    	
        Collection<Message> messages = aTime.getMessages();
//        for (Message message : messages) {
//            theManager.remove(message);
//            theManager.flush() ;
//            theManager.clear();
//        }
        messages.clear() ;
    	manager.close() ;
    }

    public String checkByFormatHtml(String aFormatName, Map<String, Object> aMap) {
    	try {
        	
    		StringBuilder sb = new StringBuilder() ;
    		Format format ;
    		try {
    			format = (Format) theManager.createQuery("from Format where comment=:name")
        		.setParameter("name", aFormatName).getSingleResult() ; 
    		} catch (Exception e) {
    			throw new IllegalStateException("Ошибка при поиске формата с комментарием "+aFormatName,e) ;
    		}
        	
        	Collection<Field> fields = Collections.unmodifiableCollection(format.getFields());
        	Class entityClass = ClassLoaderHelper.getInstance().loadClass(format.getDocument().getEntityClassName()) ;
        	Object entity = entityClass.newInstance() ;
        	ImportServiceBean.copyMapToEntity(fields, aMap, entity) ;

            HashMap<String, Object> map = new HashMap<String, Object>();
            copyToMap(entity, map,entityClass);
        	
            // список полей
            HashSet<String> allowedFields = new HashSet<String>();
            for (Method method : entityClass.getMethods()) {
                if(method.getAnnotation(Comment.class)!=null) {
                    allowedFields.add(PropertyUtil.getPropertyName(method)) ;
                }
            }
            
            CheckContext ctx = new CheckContext(format, map, allowedFields, new java.sql.Date(new Date().getTime()), theManager, entity);

            // проверки
            Collection<Check> list = format.getDocument().getChecks();
            sb.append("\n<ol class='result'>") ;
            for (Check check : list) {
                ICheck icheck = (ICheck) findCheckClass(check).newInstance() ;
                setCheckProperties(check, icheck);
                try {
                	if(!check.getDisabled()) {
                        CheckResult result = icheck.check(ctx) ;

                        if(result.isAccepted()) {
                        	sb.append("\n<li class='checkType").append(check.getCheckType()).append("'>") ;
                        	sb.append(check.getName()) ;
                        	if(check.getComment()!=null) {
                        		sb.append(" <span class='comment'>").append(check.getComment()).append("</span>") ;
                        	}
                        	
                        	if(!result.getChanged().isEmpty()) {
                        		sb.append("\n<ol>") ;
                        		for (Entry<String, Object> entry : result.getChanged().entrySet()) {
                        			sb.append("<li>") ;
    								sb.append(entry.getKey()).append(" = ").append(entry.getValue()) ;
                        			sb.append("</li>") ;
    							}
                        		sb.append("</ol>\n") ;
                        	}
                        	sb.append("</li>") ;
                        }
                    }
                } catch(Throwable e) {
                	LOG.warn(e.getMessage(),e) ;
                	sb.append("<li>") ;
                	sb.append(check.getName()).append(": ").append(e.getMessage()) ;
                	sb.append("</li>") ;
                }
            }
            sb.append("</ol>") ;
        	return sb.toString() ; 
    		
    	} catch (Exception e) {
    		throw new IllegalStateException(e) ;
    	}
    	
    }
    
    private static class CheckPair {
    	private CheckPair(Check aCheck, ICheck aIcheck) {
    		check = aCheck ;
    		icheck = aIcheck ;
    	}
    	private final Check check ;
    	private final ICheck icheck ;
    }
    
    private Collection<CheckPair> createIteratorsChecks(Collection<Check> aChecks, boolean aSqlSupports) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
    	LinkedList<CheckPair> ret = new LinkedList<CheckPair>() ;
    	for (Check check : aChecks) {
            if(!check.getDisabled()) {
	            ICheck icheck = (ICheck) findCheckClass(check).newInstance() ;
	            boolean checkImplementsSqlSupports = icheck instanceof ISqlSupports ;
	            boolean canAdd = false;
	            if(checkImplementsSqlSupports && aSqlSupports ) {
	            	canAdd = true ;
	            } else if(!checkImplementsSqlSupports && !aSqlSupports) {
	            	canAdd = true ;
	            }
	            if(canAdd) {
	                CheckPair pair = new CheckPair(check, icheck) ;
	                setCheckProperties(pair.check, pair.icheck);
	                ret.add(pair) ;
	            }
            }
            
		}
    	return ret ;
    }
    
    public void checkDocumentData(long aMonitorId, long aTime) throws CheckDocumentDataException {
        IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Начало проверки ...") ;
    	if(CAN_DEBUG) LOG.debug("monitor="+aMonitorId) ;
    	if(CAN_DEBUG) LOG.debug("time="+aTime) ;
        try {
            ImportTime time = theManager.find(ImportTime.class, aTime) ;
            if (CAN_DEBUG)
				LOG.debug("checkDocumentData: time = " + time); 

            ImportDocument document = time.getDocument() ;
            if (CAN_DEBUG)
				LOG.debug("checkDocumentData: document = " + document); 

            Format format = (Format) time.getFormat() ;
            if (CAN_DEBUG)
				LOG.debug("checkDocumentData: format = " + format); 

            // перебор всех записей в реестре

        	if(CAN_DEBUG) LOG.debug(" Определение количества записей...") ;
        	Query countQuery ;
        	if(document.isTimeSupport()) {
        		countQuery = theManager.createQuery("select count(*) from "+document.getEntityClassName()+" where time =:time")
            	.setParameter("time", aTime) ;
        	} else {
        		countQuery = theManager.createQuery("select count(*) from "+document.getEntityClassName()) ;
        	}
            Long count = (Long) countQuery.getSingleResult() ;
        	if(CAN_DEBUG) LOG.debug(" Количество = "+count) ;
            
            //Collection data = theManager.createQuery("from "+document.getEntityClassName()+" c where time =:time").setParameter("time", aTime).getResultList();
            monitor = theMonitorService.startMonitor(aMonitorId, "Проверка", count) ;
            monitor.setText("Удаление старых проверок ...") ;
            clearChecks(time);
            theManager.persist(time);

            
            monitor.setText("Создание списка полей...") ;
            Class entityClass = ClassLoaderHelper.getInstance().loadClass(document.getEntityClassName());
            // список полей
            HashSet<String> allowedFields = new HashSet<String>();
            for (Method method : entityClass.getMethods()) {
                if(method.getAnnotation(Comment.class)!=null) {
                    allowedFields.add(PropertyUtil.getPropertyName(method)) ;
                }
            }
            
            monitor.setText("Создание списка проверок по SQL...") ;
            Collection<CheckPair> sqlPairs = createIteratorsChecks(document.getChecks(), true) ;
            executeSqlSupports(entityClass, sqlPairs, aTime, monitor) ;
            
//            for (Field field : format.getFields()) {
//                allowedFields.add(field.getName()) ;
//            }
            Collection<CheckPair> list = createIteratorsChecks(document.getChecks(), false) ;
            int i = 0 ;
            monitor.setText("Другие проверки...") ;
            Query query ;
            if(document.isTimeSupport()) {
             	query = theManager.createQuery("from "+document.getEntityClassName()+" c where time =:time").setParameter("time", aTime) ; 
            } else {
            	query = theManager.createQuery("from "+document.getEntityClassName()) ; 
            }
            Iterator iterator = QueryIteratorUtil.iterate(query) ;
            while (iterator.hasNext()) {
            	Object entity = iterator.next();
            	// if(CAN_DEBUG) LOG.debug("Checking entity "+entity+" ...") ;
            	if( ++i%10==0) {
            		if(monitor.isCancelled()) throw new IllegalMonitorStateException("Прервано пользователем") ;
                    monitor.advice(10);
                    monitor.setText("Проверяется "+i+" из "+count+"...");
            	}
            	
                // копируем все в MAP
                HashMap<String, Object> map = new HashMap<String, Object>();
                copyToMap(entity, map, entityClass);
                CheckContext ctx = new CheckContext(format, map, allowedFields, time.getActualDateFrom(), theManager,entity);

                // проверки
                //Collection<Check> list = document.getChecks();
                for (CheckPair pair : list) {
                	if(CAN_DEBUG) LOG.debug(" Checking "+pair.check.getName()+" ...") ;
                	CheckResult result ;
                	try {
                        result = pair.icheck.check(ctx) ;
                		
                	} catch (Exception e) {
                		String error = "Ошибка при сохранении результатов: [entity="
                				+entity
                				+", check = "+pair.check
                				+", map = "+map
                				+"]" ;
                		LOG.error(error,e) ;
                		result = new CheckResult() ;
                		result.setAccepted(true);
                		result.error(error);
                		// throw new IllegalArgumentException(error, e) ;
                				
                	}

                    if(result.isAccepted()) {
                    	try {
                            storeResult(entity, time, (Long)PropertyUtil.getPropertyValue(entity, "id"), result, pair.check, map);
                    	} catch (Exception e) {
                    		String error = "Ошибка при сохранении результатов: [entity="
                    				+entity
                    				+", check = "+pair.check
                    				+", result = "+result
                    				+", map = "+map
                    				+"]" ;
                    		LOG.error(error,e) ;
                    		throw new IllegalArgumentException(error, e) ;
                    	}
                    }
                }
                //theManager.persist(entity) ;
                theManager.flush();
                theManager.clear() ;
            }
            monitor.finish(String.valueOf(aTime));
        } catch (Exception e) {
        	LOG.error("Ошибка: "+e,e) ;
            if(monitor!=null) monitor.error(e.getMessage(),e) ;
            throw new CheckDocumentDataException("Ошибка проверки: "+e.getMessage(),e);
        }
    }


    private void executeSqlSupports(Class aEntityClass, Collection<CheckPair> sqlPairs, long aTimeId, IMonitor aMonitor) {
    	String tableName = EntityHelper.getInstance().getTableName(aEntityClass) ;
    	for(CheckPair pair: sqlPairs) {
    		if(aMonitor.isCancelled()) throw new IllegalMonitorStateException("Прервано пользователем") ;
        	long checkId = pair.check.getId() ;
        	NativeSqlContext ctx = new NativeSqlContext(theManager) ;
        	ctx.setCheckId(checkId) ;
        	ctx.setTimeId(aTimeId) ;
        	ctx.setTableName(tableName) ;
        	ctx.setEntityClass(aEntityClass) ;
        	
        	if(pair.icheck instanceof INativeSqlMultipleQueriesSupports) {
        		INativeSqlMultipleQueriesSupports multi = (INativeSqlMultipleQueriesSupports) pair.icheck ;
        		Collection<String> queries = multi.getNativeSql(ctx) ;
        		for(String query: queries) {
        			executeUpdate(aMonitor, query, pair) ;
        		}
        	} else {
            	INativeSqlSupports nativeSqlSupports = (INativeSqlSupports) pair.icheck ;
            	String query = nativeSqlSupports.getNativeSql(ctx) ;
    			executeUpdate(aMonitor, query, pair) ;
        	}
    	}
	}
    
    private void executeUpdate(IMonitor aMonitor, String aQuery, CheckPair aPair) {
    	aMonitor.setText(aPair.check.getName()+"<span style='margin-left: 1em; color: gray; font-weight: normal;'>"+aQuery+"</query>") ;
    	LOG.debug(aQuery) ;
    	int i = theManager.createNativeQuery(aQuery).executeUpdate() ;
    	LOG.debug("count = "+i) ;
    }

	public void setCheckProperties(Check aCheck, ICheck aChecker) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
        for (CheckProperty property : aCheck.getProperties()) {
            setPropertyValue(aChecker, property.getProperty(), property.getValue());
        }
    }

    private static void setPropertyValue(Object aObject, String aProperty, Object aValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
    	PropertyUtil.setPropertyValue(aObject, aProperty, aValue);
    }

    private Class findCheckClass(Check aCheck) {
        return theAllowedChecksAllValues.getCheckClassById(aCheck.getCheckId()) ;
    }

    public Collection<CheckPropertyRow> listProperties(long aCheckId) throws ClassNotFoundException {
        Check check = theManager.find(Check.class, aCheckId) ;

        Collection<CheckProperty> properties = check.getProperties() ;

        TreeMap<String, String> values = new TreeMap<String, String>();
        TreeMap<String, Long> ids = new TreeMap<String, Long>();
        for (CheckProperty property : properties) {
            values.put(property.getProperty(), property.getValue()) ;
            ids.put(property.getProperty(), property.getId()) ;
        }

        Class clazz = findCheckClass(check) ;
        LinkedList<CheckPropertyRow> ret = new LinkedList<CheckPropertyRow>();
        for (Method method : clazz.getMethods()) {
            Comment comment = method.getAnnotation(Comment.class) ;
            if(comment!=null) {
                CheckPropertyRow row = new CheckPropertyRow();
                row.setProperty(PropertyUtil.getPropertyName(method));
                row.setComment(comment.value());
                row.setValue(values.get(row.getProperty()));
                if(ids.containsKey(row.getProperty())) {
                    row.setId(ids.get(row.getProperty()));
                }
                row.setCheck(aCheckId);
                ret.add(row) ;
            }
        }
        return ret ;
    }

    public CheckPropertyForm loadForm(long aCheckId, String aProperty) throws ClassNotFoundException {
        //StringTokenizer st = new StringTokenizer(aCheckAndProperty, ",");
        Collection<CheckPropertyRow> rows = listProperties(aCheckId) ;
        String value = null ;
        Long id = null ;
        for (CheckPropertyRow row : rows) {
            if(row.getProperty().equals(aProperty)) {
                value = row.getValue() ;
                id = row.getId() ;
                break ;
            }
        }
        CheckPropertyForm form = new CheckPropertyForm();
        form.setId(id);
        form.setCheck(aCheckId);
        form.setProperty(aProperty);
        form.setValue(value);
        form.setTypeSave();
        return form ;
    }

    public void saveForm(CheckPropertyForm aForm) throws ClassNotFoundException {
        CheckPropertyForm fromBaseForm = loadForm(aForm.getCheck(), aForm.getProperty()) ;
        CheckProperty property ;
        if(fromBaseForm.getId()!=null) {
            property = theManager.find(CheckProperty.class, fromBaseForm.getId()) ;
        } else {
            Check check = theManager.find(Check.class, aForm.getCheck()) ;
            property = new CheckProperty();
            property.setCheck(check);
            property.setProperty(aForm.getProperty());
        }
        property.setValue(aForm.getValue());
        theManager.persist(property);
    }

    private boolean storeResult(Object aEntity, ImportTime aTime, long aData, CheckResult aResult, Check aCheck, Map<String, Object> aOldValues) throws IllegalAccessException, NoSuchMethodException, ParseException, InvocationTargetException {
        Message message = new Message();
        message.setCheck(aCheck);
        message.setDataId(aData);
        message.setImportTime(aTime);
        theManager.persist(message);
        boolean ret = false;
        /*
        for (ResultLog log : aResult.getLogs()) {
            MessageLog messageLog = new MessageLog();
            messageLog.setText(log.getMessage());
            messageLog.setLogType(log.getType());
            messageLog.setMessage(message);
            theManager.persist(messageLog);
        }
        */
        if(aCheck.getCheckType()==Check.TYPE_CHANGE) {
            Map<String, Object> map = aResult.getChanged();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                /*MessageChange change = new MessageChange();
                change.setMessage(message);
                Object oldValue = aOldValues.get(entry.getKey()) ;
                change.setOldValue(oldValue!=null ? oldValue.toString() : null);
                change.setNewValue(entry.getValue()!=null ? entry.getValue().toString() : null);
                change.setPropertyName(entry.getKey());
                theManager.persist(change);
                */
                setPropertyValue(aEntity, entry.getKey(), entry.getValue());
                aOldValues.put(entry.getKey(), entry.getValue()) ;
                ret = true ;
            }
        }
        return ret ;
    }


    private static void copyToMap(Object aEntity, Map<String, Object> aMap, Class aClass) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Method method : aClass.getMethods()) {
            if(method.isAnnotationPresent(Comment.class)) {
                String property = PropertyUtil.getPropertyName(method) ;
                aMap.put(property, PropertyUtil.getPropertyValue(aEntity, property)) ;
            }
        }

   }

//    private static void copyToMap(Object aEntity, Map<String, Object> aMap, Format aFormat) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//       for (Field field : aFormat.getFields()) {
//           String name = field.getName();
//           String property = field.getProperty() ;
//           Object value = PropertyUtil.getPropertyValue(aEntity, property) ;
//           aMap.put(name, value ) ;
//       }
//   }

    private final AllowedChecksAllValues theAllowedChecksAllValues = new AllowedChecksAllValues();
    @PersistenceContext
    public EntityManager theManager ;
    private @EJB ILocalMonitorService theMonitorService ;

    private @PersistenceUnit
	EntityManagerFactory theFactory;
    
}
