package ru.ecom.expomc.ejb.uc.filltime.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.monitor.MonitorId;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.JBossConfigUtil;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.ejb.util.RhinoHelper;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.uc.filltime.domain.FillTime;
import ru.ecom.expomc.ejb.uc.filltime.domain.FillTimeProperty;
import ru.nuzmsh.util.PropertyUtil;

@Stateless
@Remote(IFillTimeService.class)
public class FillTimeServiceBean implements IFillTimeService {

	private final static Logger LOG = Logger
			.getLogger(FillTimeServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public long fill(long aFillTime, MonitorId aMonitorId) {
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Запуск заполнения "+aFillTime) ;
		try {
			FillTime fillTime = theManager.find(FillTime.class, aFillTime);
			monitor = theMonitorService.startMonitor(aMonitorId
					, "Выполнение Заполнения: "+fillTime.getName()
					, calcCount(fillTime)) ;
			ImportDocument importDocument = fillTime.getOutputDocument() ;
			
			// создание ImportTime
			ImportTime importTime = new ImportTime() ;
			importTime.setComment("Заполнение: "+fillTime.getName());
			importTime.setDocument(importDocument);
			importTime.setImportDate(new Date());
			theManager.persist(importTime);
			
			// выполнение запроса
			monitor.setText("Выполнение запроса: "+fillTime.getQueryString());
			Iterator it = QueryIteratorUtil.iterate(theManager.createQuery(fillTime.getQueryString()));
			Class clazz = theClassLoaderHelper.loadClass(importDocument.getEntityClassName()) ;
			long importTimeId = importTime.getId();
			long count = 0 ;
			
			// создание JavaScript context
			Context jsContext = Context.enter() ;
			jsContext.setOptimizationLevel(9);
			Scriptable scope = jsContext.initStandardObjects();
			theRhinoHelper.loadLibraryFiles(jsContext, scope);
			initScript(jsContext, scope,fillTime);
			addFunctions(fillTime.getProperties(), jsContext, scope);
			while( it.hasNext() && !monitor.isCancelled() ) {
				count++ ;
				monitor.advice(1);
				//if(monitor.isCancelled()) throw
				Object sourceEntity = it.next();
				Object targetEntity = clazz.newInstance();
				//if (CAN_DEBUG)
				//	LOG.debug("fill: sourceEntity = " + sourceEntity); 

				IImportData itime = (IImportData)targetEntity ;
				itime.setTime(importTimeId);
				
				invokeJs(fillTime.getProperties(), sourceEntity, itime, jsContext, scope);
				theManager.persist(itime);
				if (CAN_DEBUG)
					LOG.debug("fill: itime = " + itime); 

				theManager.flush();
				theManager.clear();
			}
			if (CAN_DEBUG)
				LOG.debug("fill: count = " + count); 

			monitor.finish(String.valueOf(importTime.getId()));
			return importTime.getId();
		} catch (Exception e) {
			monitor.error("Ошибка: "+e.getMessage(), e) ;
			throw new IllegalStateException(e);
		} finally {
			Context.exit() ;
		}
	}

	
	
	private void loadFile2(Context aContext, Scriptable aScope) {
		try {
			File file = JBossConfigUtil.getDataFile("filltime.js");
			InputStreamReader in = new InputStreamReader(new FileInputStream(file),"utf-8") ;
			try {
				Script script = aContext.compileReader(in, file.getName(), 0, null);
				script.exec(aContext, aScope);
			} finally {
				in.close();
			}		
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	private void addFunctions(List<FillTimeProperty> aProperties, Context aContext, Scriptable aScope) {
		for(FillTimeProperty prop : aProperties) {
			addFunction(aContext, aScope, prop.getProperty(), prop.getTransformText());
		}
	}
	
	private void invokeInitScriptOnEachEntity(Context aContext, Scriptable aScope) {
		Object fObj = aScope.get("initScriptOnEachEntity", aScope);
		Function f = (Function)fObj;
		f.call(aContext, aScope, aScope, null);		
	}
	
	private void initScript(Context aContext, Scriptable aScope, FillTime aFill) {
		StringBuilder sb = new StringBuilder() ;
		sb.append("function initScriptOnEachEntity() {\n") ;
		sb.append(aFill.getInitText());
		sb.append("}");
		Script script = aContext.compileString(sb.toString(), "initScriptOnEachEntity", 0, null);
		script.exec(aContext, aScope) ;
	}
	
	private void addFunction(Context aContext, Scriptable aScope, String aPropertyName, String aBody) {
		StringBuilder sb = new StringBuilder() ;
		sb.append("function ").append(aPropertyName).append("Transform() {\n") ;
		if( aBody.trim().indexOf('\n')==-1 && aBody.indexOf("return ")==-1) {
				sb.append(" return ") ;
		}
		sb.append(aBody);
		sb.append("\n}") ;
		LOG.info("function is:\n"+sb+"\n");
		Script functionScript = aContext.compileString(sb.toString(), aPropertyName, 0, null);
		functionScript.exec(aContext, aScope) ;
	}
	
	public String testInvoke(long aFillTime, String aProperty, String aCode) {
		FillTime fillTime = theManager.find(FillTime.class, aFillTime) ;
		List list = theManager.createQuery(fillTime.getQueryString()).setMaxResults(1).getResultList() ;
		if(list!=null && !list.isEmpty()) {
			Object sourceEntity = list.iterator().next();
			Object targetEntity;
			try {
				targetEntity = theClassLoaderHelper.loadClass(fillTime.getOutputDocument().getEntityClassName()).newInstance() ;
			} catch (Exception e) {
				throw new IllegalArgumentException("Ошибка инициализации объекта типа: "+fillTime.getOutputDocument().getEntityClassName()) ;
			}
			Context jsContext = Context.enter() ;
			try {
				Scriptable scope = jsContext.initStandardObjects();
				initEvalContext(sourceEntity, jsContext, scope, targetEntity) ;
				theRhinoHelper.loadLibraryFiles(jsContext, scope);
				addFunction(jsContext, scope, aProperty, aCode);
				initScript(jsContext, scope, fillTime);
				invokeInitScriptOnEachEntity(jsContext, scope);
				try {
					invokeJsOnProperty(aProperty, aCode, sourceEntity, targetEntity, jsContext, scope) ;
				} catch (Exception e) {
					if (CAN_DEBUG) {
						LOG.error("Ошибка пробного запуска [aFillTime="+aFillTime+", aProperty="+aProperty+", aCode="+aCode+" ]: "+e.getMessage(),e);
					}
					throw new IllegalStateException(e.getMessage()) ;
				}
				try {
					return String.valueOf(PropertyUtil.getPropertyValue(targetEntity, aProperty)) ;
				} catch (Exception e) {
					throw new IllegalStateException("Ошибка получения свойства "+aProperty+" у "+targetEntity) ;
				}
			} finally {
				Context.exit() ;
			}
			
		} else {
			return "Запрос не вернул данных. "+fillTime.getQueryString() ;
		}
	}
	
	public List<PropertyByFieldRow> listByFormat(long aFillTime) {
		FillTime fillTime = theManager.find(FillTime.class, aFillTime);
		if(fillTime.getFormat()!=null) {
			List<PropertyByFieldRow> list = new LinkedList<PropertyByFieldRow>() ;
			for(Field field : fillTime.getFormat().getFields()) {
				String propName = field.getProperty();
				 PropertyByFieldRow row = new PropertyByFieldRow() ;
				 row.setComment(field.getComment());
				 row.setProperty(propName);
				 row.setField(field.getName());
				 FillTimeProperty prop = findFillTimePropertyByPropertyName(fillTime, propName);
				 if(prop!=null) {
					 row.setTransformText(prop.getTransformText());
					 row.setId(String.valueOf(prop.getId())) ;
				 } else {
					 row.setId(fillTime.getId()+","+propName) ;
				 }
				 list.add(row);
			}
			return list ;
		} else {
			throw new IllegalStateException("Нет формата") ;
		}
	}
	
	private FillTimeProperty findFillTimePropertyByPropertyName(FillTime aFillTime, String aPropertyName) {
		Query query = theManager.createNamedQuery("FillTimeProperty.byPropertyName")
			.setParameter("fillTime", aFillTime)
			.setParameter("property", aPropertyName);
		return QueryResultUtil.getFirst(FillTimeProperty.class, query);
	}
	
	
	private void initEvalContext(Object aSourceEntity, Context aContext, Scriptable aScope, Object aTargetEntity) {
		aScope.put("entity", aScope, aSourceEntity);
		aScope.put("manager", aScope, theManager);
		aScope.put("injection", aScope, theInjection) ;
		aScope.put("target", aScope, aTargetEntity) ;		
		IImportData importData = (IImportData) aTargetEntity ; 
		aScope.put("importTime", aScope, importData.getTime()) ;
	}
	
	private void invokeJs(List<FillTimeProperty> aProperties, Object aSourceEntity, Object aTargetEntity, Context aContext, Scriptable aScope) {
		initEvalContext(aSourceEntity, aContext, aScope, aTargetEntity) ;
		invokeInitScriptOnEachEntity(aContext, aScope);
		for(FillTimeProperty prop : aProperties) {
			try {
				invokeJsOnProperty(prop.getProperty(), prop.getTransformText(), aSourceEntity, aTargetEntity, aContext, aScope);
			} catch (Exception e) {
				LOG.warn(prop.getProperty() +" -> "+prop.getTransformText()+" : "+e.getMessage()) ;
			}
		}
	}
	
	private long calcCount(FillTime aTime) {
		return (Long) theManager.createQuery("select count(*) "+aTime.getQueryString()).getSingleResult() ;
	}
	
	private static void invokeJsOnProperty(String aProperty, String aText, Object aSourceEntity, Object aTargetEntity, Context aContext, Scriptable aScope) {
		Object fObj = aScope.get(aProperty+"Transform", aScope);
		Function f = (Function)fObj;
		Object result = f.call(aContext, aScope, aScope, null);

//		Object result = aContext.evaluateString(aScope, aText
//				, aProperty, 1, null) ;
		if(result instanceof NativeJavaObject) {
			NativeJavaObject njo = (NativeJavaObject) result ;
			result = njo.unwrap() ;
		}
		try {
			PropertyUtil.setPropertyValue(aTargetEntity, aProperty, result);
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка установки свойства "+aProperty+" у "+aTargetEntity+": "+e.getMessage(),e);
		}
	}
	
	private @EJB ILocalMonitorService theMonitorService ;
	private @PersistenceContext EntityManager theManager ;
	private ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();
	private final EjbInjection theInjection = EjbInjection.getInstance() ;
	private final RhinoHelper  theRhinoHelper = RhinoHelper.getInstance() ;
}
