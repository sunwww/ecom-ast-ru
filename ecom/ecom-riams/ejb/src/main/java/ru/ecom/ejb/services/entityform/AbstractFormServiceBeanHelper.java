package ru.ecom.ejb.services.entityform;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.ejb.services.entityform.jsinterceptor.JavaScriptFormInterceptorContext;
import ru.ecom.ejb.services.entityform.jsinterceptor.JavaScriptFormInterceptorManager;
import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.ecom.ejb.services.live.domain.journal.DeleteJournal;
import ru.ecom.ejb.util.FormAfterLoadInterceptor;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.MapForm;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.*;
import java.io.StringWriter;
import java.lang.annotation.ElementType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author esinev Date: 16.08.2006 Time: 10:32:01
 */
public class AbstractFormServiceBeanHelper implements IFormService {
	private static final Logger LOG = Logger
			.getLogger(AbstractFormServiceBeanHelper.class);

	void checkDynamicPermission(Class aFormClass, Object aId,
			String aPolicyAction) {
		if (aId == null)
			throw new IllegalArgumentException("Нет параметра aId");
		ADynamicSecurityInterceptor interceptor = (ADynamicSecurityInterceptor) aFormClass
				.getAnnotation(ADynamicSecurityInterceptor.class);
		if (interceptor != null) {
			InterceptorContext ctx = new InterceptorContext(theManager,
					theContext);
			for (Class interceptorClass : interceptor.value()) {
				try {
					IDynamicSecurityInterceptor dynamicInterceptor = (IDynamicSecurityInterceptor) interceptorClass
							.newInstance();
					dynamicInterceptor.check(aPolicyAction, aId, ctx);
				} catch (InstantiationException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	private String getSecurityRole(Class aFormClass, String aSuffix) {
		EntityFormSecurityPrefix prefix = (EntityFormSecurityPrefix) aFormClass
				.getAnnotation(EntityFormSecurityPrefix.class);
		if (prefix != null) {
			return prefix.value() + "/" + aSuffix;
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
		checkIsObjectDeleted(entity);
		try {
			T form = aFormClass.newInstance();
			copyEntityToForm(entity, form);
			checkDynamicPermission(aFormClass, aId, "View");
			// FIXME может быть и понадобится
			if(aFormClass.isAnnotationPresent(AViewInterceptors.class)) {
				AViewInterceptors interceptors = aFormClass.getAnnotation(AViewInterceptors.class) ;
				invokeFormInterceptors(interceptors.value(), form, entity);
			}
			if(theRowPersistDelegate.isRowPersistEnable(aFormClass)) {
				theRowPersistDelegate.load(form, theManager, entity);
			}
			invokeJavaScriptInterceptor("onView", form, entity, null, null);
			return form;
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
		if(aOrigForm instanceof MapForm) {
			MapForm form = (MapForm) aOrigForm ;
			Class cl = loadMapForm("$$map$$"+form.getStrutsFormName()) ;
			try {
				MapForm dest = (MapForm) cl.newInstance() ;
				BeanUtils.copyProperties(dest, aOrigForm);
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
	    				createManyToManyOneProperty(aForm,entity,getIdMethod.invoke(entity)) ;
				}
				copyFormToEntity(aForm, entity, false); // не копируем @Id
				if( !doPersistBefore ) manager.persist(entity);
				if(theRowPersistDelegate.isRowPersistEnable(aForm.getClass())) {
					theRowPersistDelegate.create( aForm, theManager, entity);
				}

				if(aForm.getClass().isAnnotationPresent(ACreateInterceptors.class)) {
					ACreateInterceptors interceptors = aForm.getClass().getAnnotation(ACreateInterceptors.class) ;
					invokeFormInterceptors(interceptors.value(),aForm, entity);
				}
				invokeJavaScriptInterceptor("onCreate", aForm, entity, null, null);
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

	private void createManyToManyOneProperty(IEntityForm aForm, Object aEntity, Object aId) throws Exception {
		Class clazz = aForm.getClass() ;
	    Method[] methods = clazz.getMethods() ;
	    for(Method method : methods) {
			if(method.isAnnotationPresent(PersistManyToManyOneProperty.class)) {
				boolean isViewable = isEntityMethodDataAvailable(aEntity,aEntity.getClass(), method, aId);
				if(isViewable) {
						String json = (String) method.invoke(aForm);

						PersistManyToManyOneProperty pm = method
								.getAnnotation(PersistManyToManyOneProperty.class);
						Class type = pm.collectionGenericType();
						String tableName = pm.tableName() ;
						String parentProperty = pm.parentProperty() ;
						String valueProperty = pm.valueProperty() ;
						Collection collection =null ;
						if (!tableName.equals("")) {
							saveOneToManyOneProperty(json, collection, type,tableName,parentProperty,valueProperty, aId);
						}
					} 
				}
			}
	    }

	
	private void checkIsObjectDeleted(Object aEntity) throws IllegalArgumentException {
		Class entityClass = aEntity.getClass();
		if (entityClass.isAnnotationPresent(UnDeletable.class)) {
			UnDeletable unDeletable = (UnDeletable) entityClass.getAnnotation(UnDeletable.class);
			try {
				Method getterIsDeleted = PropertyUtil.getGetterMethod(entityClass,unDeletable.fieldName());
				Boolean isDeleted = (Boolean)getterIsDeleted.invoke(aEntity);
				if (Boolean.TRUE==isDeleted) {
					throw new IllegalArgumentException("Этот объект был удален");
				}
			} catch (InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
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
			checkIsObjectDeleted(entity);
			invokeJavaScriptInterceptor("onPreSave", aForm, entity, null, null);
			copyFormToEntity(aForm, entity);
			if(aForm.getClass().isAnnotationPresent(ASaveInterceptors.class)) {
				ASaveInterceptors interceptors = aForm.getClass().getAnnotation(ASaveInterceptors.class) ;
				invokeFormInterceptors(interceptors.value(),aForm, entity);
			}
			if(theRowPersistDelegate.isRowPersistEnable(aForm.getClass())) {
				theRowPersistDelegate.save(aForm, theManager, entity);
			}
			invokeJavaScriptInterceptor("onSave", aForm, entity, null, null);
			checkDynamicPermission(aForm.getClass(), idValue, "Edit");
			
		} catch (Exception e) {
			LOG.error("Ошибка копирование из формы "+aForm+" в объект",e) ;
			throw new EntityFormException(
//					"Ошибка копирование из формы "+aForm+" в объект", e)
					"ОШИБКА", e);
		}
	}

	private void invokeJavaScriptInterceptor(String aFunctionName, IEntityForm aForm, Object aEntity, Object aId, Class aStrutsFormClass) {
		JavaScriptFormInterceptorContext ctx = new JavaScriptFormInterceptorContext(theManager, theContext, theEjbInjection) ;
		theJavaScriptFormInterceptorManager.invoke(aFunctionName, aForm, aEntity, aId, aStrutsFormClass, ctx);
	}
	
	private void invokeFormInterceptors(AEntityFormInterceptor[] aInterceptors, IEntityForm aForm, Object aEntity) throws InstantiationException, IllegalAccessException {
		for(AEntityFormInterceptor interceptor: aInterceptors) {
			if (interceptor.value().newInstance() instanceof IFormInterceptor) {
				IFormInterceptor formInterceptor = (IFormInterceptor) interceptor.value().newInstance() ;
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
		Class entityClass =findFormPersistanceClass(aFormClass);
		Object entity = theManager.find(entityClass, aId);
		checkIsObjectDeleted(entity);
		invokeJavaScriptInterceptor("onPreDelete", null, null, aId, aFormClass) ;
		if (entityClass.isAnnotationPresent(UnDeletable.class)) { //Если у формы есть аннотация, не удаляем
            String username = theContext.getCallerPrincipal().getName();
			String fieldName = ((UnDeletable) entityClass.getAnnotation(UnDeletable.class)).fieldName();
			theManager.createQuery("update "+entityClass.getCanonicalName()+" set "+fieldName+"='1' where id=:id").setParameter("id",aId).executeUpdate();
            DeleteJournal deleteJournal = new DeleteJournal();
            deleteJournal.setClassName(entityClass.getCanonicalName());
            deleteJournal.setDeleteDate(new java.sql.Date(new java.util.Date().getTime()));
            deleteJournal.setDeleteTime(new java.sql.Time(new java.util.Date().getTime()));
            deleteJournal.setLoginName(username);
            deleteJournal.setObjectId(""+aId);
            deleteJournal.setSerialization("Помечено на удаление");
            theManager.persist(deleteJournal);
            deleteJournal.setStatus(2L);
		} else {
			theManager.remove(theManager.find(entityClass,aId));
		}
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
									.getAnnotation(PersistManyToManyOneProperty.class),id);
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
				if (clazz.newInstance() instanceof IFormInterceptor) {
				IFormInterceptor interceptor = (IFormInterceptor) clazz
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

	private String createJsonOneToManyOneProperty(Object aEntity,
		Method aEntityGetMethod, PersistManyToManyOneProperty aAnnotation, Object aId) {
		String tableName = aAnnotation.tableName() ; 
		try {
			if (tableName.equals("")) {
				StringWriter out = new StringWriter();
				JSONWriter j = new JSONWriter(out);
				j.object();
	
				j.key("childs").array();
				Collection childs = (Collection) aEntityGetMethod.invoke(aEntity);
				for (Object child : childs) {
					// FIXME child.getClass() - получше придумать
					j.object().key("value").value(getIdValue(child, child.getClass()));
					j.endObject();
				}
				j.endArray();
	
				j.endObject();
				return out.toString();
			} else {
				return PersistList.getArrayJson(tableName, aAnnotation.parentProperty(), PersistList.parseLong(aId), aAnnotation.valueProperty(), theManager) ;
			}
			

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
			formSetterMethod.invoke(aForm, null);
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
		Method idMethod = aEntityClass.getMethod("getId") ; //Если нет метода с таким именем, произойдет NoSuchMethodException
		if( ! idMethod.isAnnotationPresent(Id.class)) {
			// FIXME У класса "+aEntityClass+"есть метод getId, но нет аннотации @Id
			LOG.warn("У класса "+aEntityClass+" есть метод getId, но нет аннотации @Id") ;
		}
		return idMethod ;
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
			throws SecurityException, NoSuchMethodException {
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

					PersistManyToManyOneProperty pm = method
							.getAnnotation(PersistManyToManyOneProperty.class);
					Class type = pm.collectionGenericType();
					String tableName = pm.tableName() ;
					String parentProperty = pm.parentProperty() ;
					String valueProperty = pm.valueProperty() ;
					Collection collection =null ;
					if (tableName.equals("")) {
						collection = (Collection) entityClass.getMethod(
								method.getName()).invoke(aEntity);
					}
					
					saveOneToManyOneProperty(json, collection, type,tableName,parentProperty,valueProperty, id);
				} else if (method.getAnnotation(Persist.class) != null
						|| (aMustCopyId && (method.getAnnotation(Id.class) != null))) {
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
			Class aType,String aTableName, String aParentProperty, String aValueProperty, Object aId) throws JSONException, ParseException,
			SecurityException, NoSuchMethodException {
		if (aTableName==null || aTableName.equals("")) {
			JSONArray ar ;
			if (aJson!=null && !aJson.equals("")) {
				JSONObject obj =  new JSONObject(aJson) ;
				ar = obj.getJSONArray("childs");
			} else {
				ar = new JSONArray() ;
			}
			Set<Object> set = new HashSet<>();
			for (int i = 0; i < ar.length(); i++) {
				JSONObject child = (JSONObject) ar.get(i);
				String jsonId = String.valueOf(child.get("value"));
				if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
					Object id = PropertyUtil.convertValue(String.class,
							getIdClass(aType), jsonId);
					Object entity = theManager.find(aType, id);
					set.add(entity);
				}
			}

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
		} else {
			Long id = PersistList.parseLong(aId) ;
			if (id!=null && id>0) PersistList.saveArrayJson(aTableName, id, aJson, aParentProperty , aValueProperty, theManager);
		}
	}

	protected EntityFormPersistance findFormPersistance(
			Class<IEntityForm> aFormClass) {
		if (aFormClass == null)
			throw new IllegalArgumentException(
					"Параметр aFormClass не должен быть равен null");

		EntityFormPersistance p = aFormClass
				.getAnnotation(EntityFormPersistance.class);
		
		if (p == null)
			throw new IllegalArgumentException("У формы " + aFormClass
					+ " нет аннотации EntityFormPersistance");
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
	    	ArrayList<IEntityForm> ret = new ArrayList<>() ;
	    	int ind = 1 ;
	    	for(IEntityForm form : aList) {
	    		MapEntityForm mapForm = new MapEntityForm() ;
	    		BeanUtils.copyProperties(mapForm, form);
	    		mapForm.setSn(ind++) ;
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
	private final RowPersistDelegate theRowPersistDelegate = new RowPersistDelegate() ;

}
