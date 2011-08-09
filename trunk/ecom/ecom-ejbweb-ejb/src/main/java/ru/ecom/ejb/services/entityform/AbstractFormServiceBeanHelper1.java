package ru.ecom.ejb.services.entityform;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import ru.ecom.ejb.services.entityform.annotation.PersistListProperty;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.entityform.jsinterceptor.JavaScriptFormInterceptorContext;
import ru.ecom.ejb.services.entityform.jsinterceptor.JavaScriptFormInterceptorManager;
import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.ecom.ejb.util.FormAfterLoadInterceptor;
//import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.MapForm;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

/**
 * @author esinev Date: 16.08.2006 Time: 10:32:01
 */
public class AbstractFormServiceBeanHelper1 implements IFormService {
	private final static Logger LOG = Logger
			.getLogger(AbstractFormServiceBeanHelper.class);

	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	void checkDynamicPermission(Class aFormClass, Object aId,
			String aPolicyAction) {
		if (aId == null)
			throw new IllegalArgumentException("Нет параметра aId");
		Class clazz = aFormClass;
		ADynamicSecurityInterceptor interceptor = (ADynamicSecurityInterceptor) clazz
				.getAnnotation(ADynamicSecurityInterceptor.class);
		// String policyToExtend = getSecurityRole(clazz, aSuffix) ;
		if (interceptor != null) {
			// StringBuilder sb = new StringBuilder();
			// sb.append(getSecurityRole(clazz, aSuffix)) ;
			// sb.append('/') ;
			InterceptorContext ctx = new InterceptorContext(theManager,
					theContext);
			for (Class interceptorClass : interceptor.value()) {
				try {
					IDynamicSecurityInterceptor dynamicInterceptor = (IDynamicSecurityInterceptor) interceptorClass
							.newInstance();
					dynamicInterceptor.check(aPolicyAction, aId, ctx);
					// sb.append(dynamicInterceptor.getExtend(aForm, ctx)) ;
					// if(!theContext.isCallerInRole(sb.toString())) {
					// throw new IllegalStateException("Нет политики
					// "+sb.toString()) ;
					// }
				} catch (InstantiationException e) {
					throw new IllegalStateException(e);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		}

	}

	String getSecurityRole(Class aFormClass, String aSuffix) {
		// 
//		if(aFormClass.equals(MapEntityForm.class)) {
//			try {
//				aFormClass = new MapClassLoader(Thread.currentThread().getContextClassLoader())
//					.loadClass("$$asdf") ;
//			} catch (ClassNotFoundException e) {
//				System.err.println(e);
//				e.printStackTrace();
//			}
//		}
		//
		
		EntityFormSecurityPrefix prefix = (EntityFormSecurityPrefix) aFormClass
				.getAnnotation(EntityFormSecurityPrefix.class);
		if (prefix != null) {
			return new StringBuilder().append(prefix.value()).append("/")
					.append(aSuffix).toString();
		} else {
			throw new IllegalArgumentException("У формы "
					+ aFormClass.getName()
					+ " нет аннотации EntityFormSecurityPrefix");
		}
	}

	void checkPermission(Class aFormClass, String aSuffix) {
		String policy = getSecurityRole(aFormClass, aSuffix);
		if (!theContext.isCallerInRole(policy)) {
			throw new IllegalStateException("Нет политики " + policy);
		}
	}

    public IEntityForm load(String aFormClassName, Object aId) throws EntityFormException {
    	if(!aFormClassName.startsWith("$$map$$")) {
    		return load(loadMapForm(aFormClassName), aId) ; 
    	} else {
        	return convertToMapForm(aFormClassName, load(loadMapForm(aFormClassName), aId));
    	}
    }
	
	/**
	 * Загружает форму
	 * 
	 * @param aId
	 *            идентификатор
	 * @return форма
	 */
	public <T extends IEntityForm> T load(Class<T> aFormClass, Object aId)
			throws EntityFormException {
		checkPermission(aFormClass, "View");
		
		Object entity = theManager.find(findFormPersistanceClass(aFormClass),
				aId);
		if (entity == null)
			throw new IllegalArgumentException("Не найден объект типа "
					+ aFormClass + " c идентификатором " + aId);
		try {
			IEntityForm form = aFormClass.newInstance();
			copyEntityToForm(entity, form);
			checkDynamicPermission(aFormClass, aId, "View");
			// FIXME может быть и понадобится
			if(aFormClass.isAnnotationPresent(AViewInterceptors.class)) {
				AViewInterceptors interceptors = aFormClass.getAnnotation(AViewInterceptors.class) ;
				invokeFormInterceptors(interceptors.value(), form, entity);
//				for(AEntityFormInterceptor entityFormInterceptor : interceptors.value()) {
//					IFormInterceptor interceptor = (IFormInterceptor)entityFormInterceptor.value().newInstance() ;
//					interceptor.intercept(form, entity, theManager) ;
//				}
			}
			if(theRowPersistDelegate.isRowPersistEnable(aFormClass)) {
				theRowPersistDelegate.load(form, theManager, entity);
			}
			invokeJavaScriptInterceptor("onView", form, entity, null, null);
			return (T) form;
		} catch (Exception e) {
			throw new EntityFormException("Ошибка копирования данных", e);
		}
	}

	public <T extends IEntityForm> T loadBySubclass(Class<T> aFormClass,
			Object aId) throws EntityFormException {
		checkPermission(aFormClass, "View");
		if (aFormClass.getAnnotation(Subclasses.class) == null)
			throw new IllegalArgumentException("У класса формы "
					+ aFormClass.getName() + " нет аннотации @Subclasses");
		Object entity = theManager.find(findFormPersistanceClass(aFormClass),
				aId);
		if (entity == null)
			throw new IllegalArgumentException("Не найден объект типа "
					+ aFormClass + " c идентификатором " + aId);
		Class entityClass = entity.getClass();
		IEntityForm ret = null;
		try {
			for (Class subFormClass : aFormClass
					.getAnnotation(Subclasses.class).value()) {
				EntityFormPersistance entityFormPersistance = (EntityFormPersistance) subFormClass
						.getAnnotation(EntityFormPersistance.class);
				if (entityFormPersistance == null)
					throw new IllegalStateException(
							"Нет аннотации @EntityFormPersistance в классе "
									+ subFormClass.getName());
				Class subEntityClass = entityFormPersistance.clazz();
				if (entityClass.equals(subEntityClass)) {
					IEntityForm form = (IEntityForm) subFormClass.newInstance();
					copyEntityToForm(entity, form);
					ret = form;
				}
			}
			if (ret == null)
				throw new IllegalStateException("Не найден класс "
						+ entityClass.getName()
						+ " в аннотации @Subclasses в класса ");
			checkDynamicPermission(aFormClass, aId, "View");
			return (T) ret;
		} catch (Exception e) {
			throw new EntityFormException("Ошибка загрузки класса ", e);
		}

	}

	private IEntityForm transformMapForm(IEntityForm aOrigForm) {
		if (CAN_DEBUG)
			LOG.debug("transformMapForm: aForm = " + aOrigForm); 

		if(aOrigForm instanceof MapForm) {
			MapForm form = (MapForm) aOrigForm ;
			if (CAN_DEBUG)
				LOG.debug("transformMapForm: form.getName() = " + form.getStrutsFormName()); 

			Class cl = loadMapForm("$$map$$"+form.getStrutsFormName()) ;
			try {
				
				MapForm dest = (MapForm) cl.newInstance() ;
				BeanUtils.copyProperties(dest, aOrigForm);
				
				if(aOrigForm instanceof BaseValidatorForm) {
					BaseValidatorForm baseValidatorForm = (BaseValidatorForm) aOrigForm;
					if(!baseValidatorForm.isTypeCreate()) {
						// загружаем недостающие данные, чтобы не перезатереть их
						MapForm fromBaseForm = (MapForm) load(cl, dest.getValue("id"));
						for(Entry<String,Object> entry : fromBaseForm.getPrivateValues().entrySet()) {
							String key = entry.getKey();
							if(dest.getValue(key)==null) {
								dest.setValue(key, entry.getValue());
							}
						}
					}
				}

				return (IEntityForm) dest ;
			} catch (Exception e) {
				throw new IllegalStateException(e) ;
			}
		} else {
			return aOrigForm ;
		}
	}
	/**
	 * Создание нового
	 * 
	 * @param aForm
	 *            форма
	 */
	public long create(IEntityForm aForm) throws EntityFormException {
		aForm = transformMapForm(aForm) ;
		
		checkPermission(aForm.getClass(), "Create");
		try {
			invokeJavaScriptInterceptor("onPreCreate", aForm, null, null, null);
		} catch(Exception e) {
			throw new EntityFormException(e.getMessage()) ;
		}
		
		Class entityClass = findFormPersistanceClass(aForm.getClass());
		
		// MapForm
		
		try {
			Object entity = entityClass.newInstance();
			Method getIdMethod = entityClass.getMethod("getId");
			EntityManager manager = theManager ; //theFactory.createEntityManager();
			try {
				boolean doPersistBefore = hasManyToManyOneProperty(aForm.getClass()) ;
				if(doPersistBefore) {
	    				manager.persist(entity);
	    				manager.flush() ;
	    				manager.clear() ;
	    				manager.refresh(entity) ; // для ManyToManyOneProperty, чтобы коллекции были инициализированы
				}
				//manager.persist(entity);
				//manager.refresh(entity) ; // для ManyToManyOneProperty, чтобы коллекции были инициализированы
				if (CAN_DEBUG)
					LOG.debug("create() Id before copy = "
							+ getIdMethod.invoke(entity));
				copyFormToEntity(aForm, entity, false); // не копируем @Id
				if (CAN_DEBUG)
					LOG.debug("create() Id after copy = "
							+ getIdMethod.invoke(entity));
				
				if( !doPersistBefore ) manager.persist(entity);
				
				if(theRowPersistDelegate.isRowPersistEnable(aForm.getClass())) {
					theRowPersistDelegate.create( aForm, theManager, entity);
				}
				
				if(aForm.getClass().isAnnotationPresent(ACreateInterceptors.class)) {
					ACreateInterceptors interceptors = aForm.getClass().getAnnotation(ACreateInterceptors.class) ;
					invokeFormInterceptors(interceptors.value(),aForm, entity);
				}
				
				invokeJavaScriptInterceptor("onCreate", aForm, entity, null, null);
				
				//if(true) throw new IllegalStateException("//fixme") ;
				
			} finally {
				//manager.close();
			}
			
			
			return (Long) getIdMethod.invoke(entity);
		} catch (Exception e) {
			LOG.error("Ошибка создания нового " + entityClass, e);
			throw new EntityFormException("Ошибка создания нового", e);
		}

	}
	
	
	private static boolean hasManyToManyOneProperty(Class aClass) {
	    Method[] methods = aClass.getMethods() ;
	    for(Method m : methods) {
			if(m.isAnnotationPresent(PersistManyToManyOneProperty.class)) {
			    return true ;
			}
	    }
	    return false ;
	}
	
	private static boolean hasPersistListProperty(Class aClass) {
	    Method[] methods = aClass.getMethods() ;
	    for(Method m : methods) {
			if(m.isAnnotationPresent(PersistListProperty.class)) {
			    return true ;
			}
	    }
	    return false ;
	}

	/**
	 * Сохранение
	 * 
	 * @param aForm
	 *            форма
	 */
	public void save(IEntityForm aForm) throws EntityFormException {
		if (aForm == null)
			throw new IllegalArgumentException("Форма не должна быть null");
		
		// FIXME если MapForm, копируем данные из базы, а потом накладываем наши данные
		
		// MapForm
		aForm = transformMapForm(aForm) ;

		checkPermission(aForm.getClass(), "Edit");
		
		
		try {
			Object idValue = getIdValue(aForm, aForm.getClass());
			Object entity = theManager.find(findFormPersistanceClass(aForm
					.getClass()), idValue);
			invokeJavaScriptInterceptor("onPreSave", aForm, entity, null, null);
			copyFormToEntity(aForm, entity);
			if(aForm.getClass().isAnnotationPresent(ASaveInterceptors.class)) {
				ASaveInterceptors interceptors = aForm.getClass().getAnnotation(ASaveInterceptors.class) ;
				invokeFormInterceptors(interceptors.value(),aForm, entity);
			}
			// ROW PERSIST
			if(theRowPersistDelegate.isRowPersistEnable(aForm.getClass())) {
				theRowPersistDelegate.save(aForm, theManager, entity);
			}
			invokeJavaScriptInterceptor("onSave", aForm, entity, null, null);
			checkDynamicPermission(aForm.getClass(), idValue, "Edit");
			
		} catch (Exception e) {
			LOG.error("Ошибка копирование из формы "+aForm+" в объект",e) ;
			throw new EntityFormException(
//					"Ошибка копирование из формы "+aForm+" в объект", e)
					"ОШИБКА", e)
			
			;
		}
	}

	private void invokeJavaScriptInterceptor(String aFunctionName, IEntityForm aForm, Object aEntity, Object aId, Class aStrutsFormClass) {
		JavaScriptFormInterceptorContext ctx = new JavaScriptFormInterceptorContext(theManager, theContext, theEjbInjection) ;
		theJavaScriptFormInterceptorManager.invoke(aFunctionName, aForm, aEntity, aId, aStrutsFormClass, ctx);
	}
	
	private void invokeFormInterceptors(AEntityFormInterceptor[] aInterceptors, IEntityForm aForm, Object aEntity) throws InstantiationException, IllegalAccessException {
		for(AEntityFormInterceptor interceptor: aInterceptors) {
			if (interceptor.value().newInstance() instanceof ru.ecom.ejb.util.IFormInterceptor) {
				ru.ecom.ejb.util.IFormInterceptor formInterceptor = (ru.ecom.ejb.util.IFormInterceptor) interceptor.value().newInstance() ;
				formInterceptor.intercept(aForm, aEntity, theManager) ;
			}
			if (interceptor.value().newInstance() instanceof ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor) {
				ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor formInterceptor = (ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor) interceptor.value().newInstance() ;
				formInterceptor.intercept(aForm, aEntity, new InterceptorContext(theManager, theContext));
			}
		}
	}
	
	/**
	 * Удалить объект
	 * 
	 * @param aId
	 *            идентификатор
	 */
	public void delete(Class aFormClass, Object aId) {
		checkPermission(aFormClass, "Delete");
		checkDynamicPermission(aFormClass, aId, "Delete");
		invokeJavaScriptInterceptor("onPreDelete", null, null, aId, aFormClass) ;
		theManager.remove(theManager.find(findFormPersistanceClass(aFormClass),
				aId));
		invokeJavaScriptInterceptor("onDelete", null, null, aId, aFormClass) ;
	}

    public void delete(String aFormClassName, Object aId) {
    	delete(loadMapForm(aFormClassName), aId);
    }
	
	protected Class<?> findFormPersistanceClass(Class aFormClass) {
		return findFormPersistance(aFormClass).clazz();
	}

	private boolean isEntityMethodDataAvailable(Object aEntity, Class aEntityClass, Method method, Object id) throws InstantiationException, IllegalAccessException {
		boolean isViewable = true ;
		
		if(method.isAnnotationPresent(ADynamicSecurityInterceptor.class)) {
//			// если нет данных, то не надо закрывать на просмотр
//			try {
//				Method setterMethod = aEntityClass.getMethod(method.getName()) ;
//				Object value = setterMethod.invoke(aEntity) ;
//				LOG.info(setterMethod.getName()+" = <"+value+">") ;
//				if(value==null) return true ;
//				if(value instanceof String && StringUtil.isNullOrEmpty((String)value)) {
//					return true ;
//				}
//			} catch (Exception e1) {
//				LOG.error(e1) ;
//			}
			
			// проверка на доступность
			ADynamicSecurityInterceptor interceptor = method.getAnnotation(ADynamicSecurityInterceptor.class) ;
			for(Class<IDynamicSecurityInterceptor> interClass: interceptor.value()) {
				IDynamicSecurityInterceptor inter = interClass.newInstance() ;
				try {
					inter.check("View", id, new InterceptorContext(theManager, theContext, ElementType.METHOD)) ;
					isViewable = true ;
				} catch (Exception e) {
					isViewable = false ;
				}
			}
		}
		return isViewable ;
	}
	/**
	 * Загружает форму
	 */
	protected void copyEntityToForm(Object aEntity, IEntityForm aForm)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, ParseException, InstantiationException {
		Class formClass = aForm.getClass();
		Class entityClass = aEntity.getClass();
		Object id = entityClass.getMethod("getId").invoke(aEntity) ;
		BaseValidatorForm baseValidatorForm ;
		if(aForm instanceof BaseValidatorForm) {
			baseValidatorForm = (BaseValidatorForm) aForm ;
		} else {
			baseValidatorForm = null ;
		}
		for (Method method : formClass.getMethods()) {
			
			boolean isViewable = isEntityMethodDataAvailable(aEntity, entityClass, method, id);
			if(!isViewable) {
				if(baseValidatorForm!=null) {
					String name = PropertyUtil.getPropertyName(method) ;
					baseValidatorForm.addPrivateField(name) ;
				}
			}
			
			if (isViewable && ( method.getAnnotation(Persist.class) != null
					|| method.getAnnotation(Id.class) != null)) {
				if (method.getAnnotation(PersistManyToManyOneProperty.class) != null) {
					Method formSetterMethod = PropertyUtil.getSetterMethod(
							formClass, method);
					Method ejbGetterMethod = entityClass.getMethod(method
							.getName());

					String json = createJsonOneToManyOneProperty(
							aEntity,
							ejbGetterMethod,
							method
									.getAnnotation(PersistManyToManyOneProperty.class));
					if (CAN_DEBUG) LOG.debug("json = " + json);
					
					formSetterMethod.invoke(aForm, json);
				} else if (method.getAnnotation(PersistListProperty.class) != null) {
					Method formSetterMethod = PropertyUtil.getSetterMethod(
							formClass, method);
					Method ejbGetterMethod = entityClass.getMethod(method
							.getName());

					String json = createJsonListProperty(
							aEntity,
							ejbGetterMethod,
							method
									.getAnnotation(PersistListProperty.class));
					if (CAN_DEBUG) LOG.debug("json = " + json);
					
					formSetterMethod.invoke(aForm, json);
				} else {
					
				
					copyEntityToForm(aEntity, aForm, method);
				}
				
			}
			
		}
		FormAfterLoadInterceptor afterLoadInterceptor = (FormAfterLoadInterceptor) formClass
			.getAnnotation(FormAfterLoadInterceptor.class);
		if (afterLoadInterceptor != null) {
			for (Class clazz : afterLoadInterceptor.value()) {
				if (clazz.newInstance() instanceof ru.ecom.ejb.util.IFormInterceptor) {
				ru.ecom.ejb.util.IFormInterceptor interceptor = (ru.ecom.ejb.util.IFormInterceptor) clazz
						.newInstance();
				interceptor.intercept(aForm, aEntity, theManager);
				}
				if (clazz.newInstance() instanceof ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor) {
					ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor interceptor = (ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor) clazz
							.newInstance();
					interceptor.intercept(aForm, aEntity, new InterceptorContext(theManager,theContext));
					}
			}
		}
	}

	private String createJsonListProperty(Object aEntity,
			Method aEntityGetMethod, PersistListProperty aAnnotation) {
		try {
			String valueProperty = aAnnotation.valueProperty() ;
			
			
			StringWriter out = new StringWriter();
			JSONWriter j = new JSONWriter(out);
			j.object();
			
			j.key("childs").array();
			Collection childs = (Collection) aEntityGetMethod.invoke(aEntity);
			for (Object child : childs) {
				// FIXME child.getClass() - получше придумать
				//j.object().key("value").value(getIdValue(child, child.getClass())); // PropertyUtil.getPropertyValue(child,
				Object value = PropertyUtil.getPropertyValue(child, valueProperty) ;
				if (value!=null) {
					j.object().key("value").value(getIdValue(value, value.getClass()));
				}
				// idProperty))
				// ;
				// Object voc = PropertyUtil.getPropertyValue(child,
				// valueProperty) ;
				// Object value = getIdValue(voc) ;
				// j.key("value").value(value) ;
				j.endObject();
			}
			j.endArray();
			
			j.endObject();
			return out.toString();
			
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	private String createJsonOneToManyOneProperty(Object aEntity,
			Method aEntityGetMethod, PersistManyToManyOneProperty aAnnotation) {
		try {
			// String valueProperty = aAnnotation.valueProperty() ;
			// String idProperty = "id" ;

			StringWriter out = new StringWriter();
			JSONWriter j = new JSONWriter(out);
			j.object();

			j.key("childs").array();
			Collection childs = (Collection) aEntityGetMethod.invoke(aEntity);
			for (Object child : childs) {
				// FIXME child.getClass() - получше придумать
				j.object().key("value").value(getIdValue(child, child.getClass())); // PropertyUtil.getPropertyValue(child,
																	// idProperty))
																	// ;
				// Object voc = PropertyUtil.getPropertyValue(child,
				// valueProperty) ;
				// Object value = getIdValue(voc) ;
				// j.key("value").value(value) ;
				j.endObject();
			}
			j.endArray();

			j.endObject();
			return out.toString();

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected static void copyEntityToForm(Object aEntity, IEntityForm aForm,
			Method aFormGetterMethod) throws NoSuchMethodException,
			ParseException, IllegalAccessException, InvocationTargetException {
		Class entityClass = aEntity.getClass();
		Method ejbGetterMethod = entityClass.getMethod(aFormGetterMethod
				.getName());
		Class ejbReturnType = ejbGetterMethod.getReturnType();
		if (ejbReturnType.getAnnotation(Entity.class) != null) {
			Object manyToOneValue = ejbGetterMethod.invoke(aEntity);
			Object manyToOneValueIdValue = getIdValue(manyToOneValue, ejbGetterMethod.getReturnType());
			Class manyToOneValueIdClass = getIdClass(ejbReturnType);
			Object formValue = PropertyUtil.convertValue(aFormGetterMethod
					.getReturnType(), manyToOneValueIdClass,
					manyToOneValueIdValue);
			Method formSetterMethod = PropertyUtil.getSetterMethod(aForm
					.getClass(), aFormGetterMethod);
			formSetterMethod.invoke(aForm, formValue);
		} else {
			PropertyUtil.copyProperty(aForm, aEntity, ejbGetterMethod);
		}
	}

	protected static void setValue(IEntityForm aForm, Object aValue, Class aValueClass,
			Method aFormGetterMethod) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Method formSetterMethod = PropertyUtil.getSetterMethod(
				aForm.getClass(), aFormGetterMethod);
		if (aValue == null) {
			formSetterMethod.invoke(aForm, aValue);
		} else {
			if (aValueClass.isAnnotationPresent(Entity.class)) {
				Object manyToOneValueIdValue = getIdValue(aValue, aValueClass);
				formSetterMethod.invoke(aForm, manyToOneValueIdValue);
			} else {
				formSetterMethod.invoke(aForm, aValue);
			}
		}
	}

	private static Method getIdMethod(Class aEntityClass) throws SecurityException, NoSuchMethodException {
		// оптимизация. // FIXME использовать хэш
		Method idMethod = aEntityClass.getMethod("getId") ;
		if(idMethod!=null) {
			if( ! idMethod.isAnnotationPresent(Id.class)) {
				// FIXME У класса "+aEntityClass+"есть метод getId, но нет аннотации @Id
				LOG.warn("У класса "+aEntityClass+" есть метод getId, но нет аннотации @Id") ;
			}
			return idMethod ;
		} else {
			for (Method method : aEntityClass.getMethods()) {
				if (method.isAnnotationPresent(Id.class)) {
					return method ;
				}
			}
			// ПРОТОКОЛ ОШИБКИ
			LOG.error("У класса "+aEntityClass+ " нет описанного идентификатора. Нет аннотации @Id") ;
			LOG.warn("ОПИСАНИЕ ОШИБКИ: ") ;
			for (Method method : aEntityClass.getMethods()) {
				for (Annotation ann: method.getAnnotations()) {
					LOG.warn("   "+ann) ;
				}
				LOG.warn(" ПОИСК @ID: "+method);
				if (method.isAnnotationPresent(Id.class)) {
					LOG.warn("   НАЙДЕНО");
				}
			}
			
			throw new IllegalArgumentException(
					"У класса "+aEntityClass+ " нет описанного идентификатора. Нет аннотации @Id");
		}
	}
	
	private static Object getIdValue(Object aEntity, Class aEntityClass)
			throws IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		if (aEntity == null) return null;
		return getIdMethod(aEntityClass).invoke(aEntity) ;
	}

	/**
	 * Тип идентификатора
	 */
	private static Class getIdClass(Class aEntityClass)
			throws IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		return getIdMethod(aEntityClass).getReturnType() ;
	}

	/**
	 * Копировать все
	 * 
	 * @Persist +
	 * @Id
	 * @param aForm
	 *            форма
	 * @param aEntity
	 *            сущность
	 * @throws InstantiationException 
	 */
	private void copyFormToEntity(IEntityForm aForm, Object aEntity)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, ParseException, JSONException, InstantiationException {
		copyFormToEntity(aForm, aEntity, true);
	}

	/**
	 * Копировать все
	 * 
	 * @Persion и
	 * @Id по условию aMustCopyId
	 * @param aForm
	 *            форма
	 * @param aEntity
	 *            сущность
	 * @param aMustCopyId
	 *            нужно копировать и
	 * @throws InstantiationException 
	 * @Id
	 */
	private void copyFormToEntity(IEntityForm aForm, Object aEntity,
			boolean aMustCopyId) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, ParseException,
			JSONException, InstantiationException {
		Class formClass = aForm.getClass();
		Class entityClass = aEntity.getClass();
		Object id = getIdValue(aForm, formClass);

		for (Method method : formClass.getMethods()) {
			boolean isViewable = isEntityMethodDataAvailable(aEntity, entityClass, method, id);
			if(isViewable) {
				if (method.isAnnotationPresent(PersistManyToManyOneProperty.class)) {
					String json = (String) method.invoke(aForm);
					// Method entityGetterMethod =
					Class valueClass = entityClass.getMethod(method.getName())
							.getReturnType();
					PersistManyToManyOneProperty pm = method
							.getAnnotation(PersistManyToManyOneProperty.class);
					Class type = pm.collectionGenericType();
					Collection collection = (Collection) entityClass.getMethod(
							method.getName()).invoke(aEntity);

					
					saveOneToManyOneProperty(json, collection, type);
				} else if (method.isAnnotationPresent(PersistListProperty.class)) {
					String json = (String) method.invoke(aForm) ;
					Class valueClass = entityClass.getMethod(method.getName()).getReturnType() ;
					PersistListProperty pl = method.getAnnotation(PersistListProperty.class) ;
					Class type = pl.collectionGenericType() ;
					Class persist = pl.tablePersist() ;
					Collection collection = (Collection) entityClass.getMethod(
							method.getName()).invoke(aEntity);
					saveListProperty(json, collection, persist, type) ;
				} else if (method.getAnnotation(Persist.class) != null
						|| (aMustCopyId && (method.getAnnotation(Id.class) != null))) {
					//LOG.info(" Copy "+method) ;
					Method ejbGetterMethod = entityClass
							.getMethod(method.getName());
					if(!ejbGetterMethod.isAnnotationPresent(Transient.class)) {
						Class ejbReturnType = ejbGetterMethod.getReturnType();

						if (ejbReturnType.getAnnotation(Entity.class) != null) {
							Object idEntity = method.invoke(aForm);
							Object entity = idEntity != null ? theManager.find(
									ejbReturnType, idEntity) : null;
							Method ejbSetterMethod = PropertyUtil.getSetterMethod(
									entityClass, ejbGetterMethod);
							ejbSetterMethod.invoke(aEntity, entity);
						} else {
							PropertyUtil.copyProperty(aEntity, aForm, method);
						}
					}
				}
				
			}
		}
	}

	private void saveOneToManyOneProperty(String aJson, Collection aCollection,
			Class aType) throws JSONException, ParseException,
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		System.out.println(aJson);
		JSONObject obj = new JSONObject(aJson);
		
		JSONArray ar = obj.getJSONArray("childs");
		// Class entityClass = aEntity.getClass() ;
		// ashMap<Object, Object> map = new HashMap<Object, Object>();
		Set<Object> set = new HashSet<Object>();
		for (int i = 0; i < ar.length(); i++) {
			JSONObject child = (JSONObject) ar.get(i);
			String jsonId = String.valueOf(child.get("value"));
			if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
				Object id = PropertyUtil.convertValue(String.class,
						getIdClass(aType), jsonId);
				// Object value = PropertyUtil.convertValue(String.class,
				// aValueClass, child.get("value")) ;
				// System.out.println("id="+id) ;
				Object entity = theManager.find(aType, id);
				set.add(entity);
				// aCollection.add(entity) ;
				// map.put(id, value) ;
			}
			// map.put(id, value) ;
		}
		// remove
		if(aCollection!=null) {
			Iterator it = aCollection.iterator();
			while (it.hasNext()) {
				Object objEntity = it.next();
				if (!set.contains(objEntity)) {
					it.remove();
				}
			}
			Iterator it2 = set.iterator();
			while (it2.hasNext()) {
				Object o2 = it2.next();
				if (!aCollection.contains(o2)) {
					it2.remove();
					aCollection.add(o2);
				}
			}
		} else {
			throw new IllegalStateException("Нет коллекции для добавления значений. aCollection=null") ;
		}
		
		// if (CAN_DEBUG) LOG.debug("map = " + map);
	}
	private void saveListProperty(String aJson, Collection aCollection,
			Class aPersistTable, Class aType) throws JSONException, ParseException,
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		System.out.println(aJson);
		JSONObject obj = new JSONObject(aJson);

		JSONArray ar = obj.getJSONArray("childs");
		// Class entityClass = aEntity.getClass() ;
		// ashMap<Object, Object> map = new HashMap<Object, Object>();
		Set<Object> set = new HashSet<Object>();
		for (int i = 0; i < ar.length(); i++) {
			JSONObject child = (JSONObject) ar.get(i);
			String jsonId = String.valueOf(child.get("value"));
			if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
				Object id = PropertyUtil.convertValue(String.class,
						getIdClass(aType), jsonId);
				// Object value = PropertyUtil.convertValue(String.class,
				// aValueClass, child.get("value")) ;
				// System.out.println("id="+id) ;
				Object entity = theManager.find(aType, id);
				set.add(entity);
				// aCollection.add(entity) ;
				// map.put(id, value) ;
			}
			// map.put(id, value) ;
		}
		// remove
		if(aCollection!=null) {
			Iterator it = aCollection.iterator();
			while (it.hasNext()) {
				Object objEntity = it.next();
				if (!set.contains(objEntity)) {
					it.remove();
				}
			}
			Iterator it2 = set.iterator();
			while (it2.hasNext()) {
				Object o2 = it2.next();
				if (!aCollection.contains(o2)) {
					it2.remove();
					aCollection.add(o2);
				}
			}
		} else {
			throw new IllegalStateException("Нет коллекции для добавления значений. aCollection=null") ;
		}

		// if (CAN_DEBUG) LOG.debug("map = " + map);
	}

	protected EntityFormPersistance findFormPersistance(
			Class<IEntityForm> aFormClass) {
    	if (CAN_DEBUG) LOG.debug("  findFormPersistance: starting..."); 
		
		if (aFormClass == null)
			throw new IllegalArgumentException(
					"Параметр aFormClass не должен быть равен null");

		if (CAN_DEBUG) LOG.debug("  findFormPersistance: getting annotation..."); 
		EntityFormPersistance p = aFormClass
				.getAnnotation(EntityFormPersistance.class);
		
    	if (CAN_DEBUG) LOG.debug("findFormPersistance: p = "+p);
		if (p == null)
			throw new IllegalArgumentException("У формы " + aFormClass
					+ " нет аннотации EntityFormPersistance");
		
    	if (CAN_DEBUG) LOG.debug("findFormPersistance: p.clazz = "+p.clazz());
		if (p.clazz() == null)
			throw new IllegalArgumentException(
					"У аннотации EntityFormPersistance нет поля clazz");
		return p;
	}

	Class loadMapForm(String aClassname) {
		MapClassLoader loader = new MapClassLoader(Thread.currentThread().getContextClassLoader()) ;
		Thread.currentThread().setContextClassLoader(loader);
		try {
			return loader.loadClass(aClassname);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected IEntityForm convertToMapForm(String aClassName, IEntityForm aForm) {
		try {
			MapEntityForm mapForm = new MapEntityForm() ;
			BeanUtils.copyProperties(mapForm, aForm);
			return mapForm ;
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка преобразования "+aClassName+" из "+aForm);
		}
	}
	
    protected Collection<IEntityForm> convertToMapFormCollection(String aClassName, Collection<IEntityForm> aList) {
    	try {
	    	ArrayList<IEntityForm> ret = new ArrayList<IEntityForm>() ;
	    	for(IEntityForm form : aList) {
	    		MapEntityForm mapForm = new MapEntityForm() ;
	    		BeanUtils.copyProperties(mapForm, form);
	    		ret.add(mapForm);
	    	}
	    	return ret ;
    	} catch (Exception e) {
    		throw new IllegalStateException("Ошибка преобразования коллекции "+aClassName,e) ;
    	}
    }
	
	@PersistenceContext
	EntityManager theManager;

	@Resource
	SessionContext theContext;

	JavaScriptFormInterceptorManager theJavaScriptFormInterceptorManager = JavaScriptFormInterceptorManager.getInstance();
	private final EjbInjection theEjbInjection = EjbInjection.getInstance(); 
	//private @PersistenceUnit
	//EntityManagerFactory theFactory;
	private final RowPersistDelegate theRowPersistDelegate = new RowPersistDelegate() ;

}
