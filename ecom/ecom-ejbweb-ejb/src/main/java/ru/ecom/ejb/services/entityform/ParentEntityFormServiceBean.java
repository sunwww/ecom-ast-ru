package ru.ecom.ejb.services.entityform;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

@Stateless
@Remote(IParentEntityFormService.class)
@Local(IParentEntityFormService.class)
@SecurityDomain("other")
public class ParentEntityFormServiceBean extends AbstractFormServiceBeanHelper implements IParentEntityFormService, Serializable {

    private final static Logger LOG = Logger.getLogger(ParentEntityFormServiceBean.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    public IEntityForm prepareToCreate(String aFormClassName, Object aParentId) throws EntityFormException {
    	return convertToMapForm(aFormClassName, prepareToCreate(loadMapForm(aFormClassName), aParentId));
    }
    
    public <T extends IEntityForm> T prepareToCreate(Class<T> aFormClass, Object aParentId) throws EntityFormException {
        checkPermission(aFormClass, "Create") ;
        if(aParentId==null) throw new IllegalArgumentException("Нет параметра aParentId ("+aParentId+")") ;
        try {
            IEntityForm form = aFormClass.newInstance() ;
            Method getterFormMethod = ParentUtil.findParentMethod(aFormClass) ;
            if(getterFormMethod.getReturnType().getAnnotation(Entity.class)!=null) {
                Class entityClass = findFormPersistanceClass(aFormClass);
                Object linkedEntity = theManager.find(entityClass, aParentId);
                setValue(form, linkedEntity, entityClass, getterFormMethod);
            } else {
            	Method formSetterMethod = PropertyUtil.getSetterMethod(
        				aFormClass, getterFormMethod);
                //setValue(form, aParentId, getterFormMethod);
            	formSetterMethod.invoke(form, aParentId);
            }
            intercept(form, aParentId, aFormClass.getAnnotation(AParentPrepareCreateInterceptors.class)) ;
            checkDynamicParentPermission(aFormClass, aParentId, "Create") ;
            return (T) form ;
        } catch (Exception e) {
            throw new EntityFormException("Ошибка подготовки к созданию новой формы",e);
        }
    }

    private void intercept(IEntityForm aForm, Object aParentId, AParentPrepareCreateInterceptors aAnnotation) throws IllegalAccessException, InstantiationException {
        if(aAnnotation!=null) {
            InterceptorContext ctx = new InterceptorContext(theManager, theContext);
            for (AParentEntityFormInterceptor interceptor : aAnnotation.value()) {
                IParentFormInterceptor inter = (IParentFormInterceptor) interceptor.value().newInstance() ;
                inter.intercept(aForm, null, aParentId, ctx);
            }
        }
    }

    public  Collection<IEntityForm> listAll(String aClassName, Object aParentId) throws EntityFormException {
    	return convertToMapFormCollection(aClassName, listAll(loadMapForm(aClassName), aParentId));
    }

    private Collection getOneToManyCollection(Object aParentEntity, Class aParentClass, Class aChildClass) throws SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	if(aParentEntity==null) return null ;
    	String name =  "get"+aChildClass.getSimpleName()+"s" ;
    	if (CAN_DEBUG) LOG.debug("getOneToManyCollection: name = " + name); 
		try {
	    	Method m  = aParentClass.getMethod(name) ;
	    	if (CAN_DEBUG)
				LOG.debug("getOneToManyCollection: method = " + m); 

	    	return (Collection)m.invoke(aParentEntity) ;
		} catch (NoSuchMethodException e) {
			return null ;
		}
    	
    	
    }
    
    public <E extends IEntityForm> Collection<E> listAll(Class<E> type, Object aParentId) throws EntityFormException {
        checkPermission(type, "View") ;
        checkDynamicParentPermission(type, aParentId, "View") ;

        try {
            String parentPropertyName = ParentUtil.getParentPropertyName(type);
            String orderBy = ParentUtil.getOrderBy(type) ;
            Class entityClass = findFormPersistanceClass(type);
            Class parentClass = findParentType(entityClass, parentPropertyName);
            Object entity = theManager.find(parentClass, aParentId);
            if (CAN_DEBUG) LOG.debug("entity = " + entity);

            // FIXME для кэша второго уровня. Доработать!!!
            Collection results = null ; //getOneToManyCollection(entity, parentClass, entityClass);
//            if (CAN_DEBUG)
//				LOG.debug("listAll: results = " + results); 

            if(results==null) {
            	if (CAN_DEBUG)
					LOG.debug("listAll: creating query..."); 

                StringBuilder query = new StringBuilder() ;
                query.append("from ").append(findFormPersistance((Class<IEntityForm>) type).clazz().getSimpleName()).append(" c where ")
                        .append(parentPropertyName) ;
                if(entity ==null) {
                    query.append(" is null ") ;
                } else {
                    query.append(" = :").append(parentPropertyName) ;
                }

                if(StringUtil.isNullOrEmpty(orderBy)) {
                    query.append(" order by id") ;
                } else {
                    query.append(" order by ").append(orderBy) ;
                }

                if (CAN_DEBUG) LOG.debug("query = " + query);
                Query query2 = theManager.createQuery(query.toString()) ;
                if(entity != null) {
                    query2.setParameter(parentPropertyName, entity) ;
                }
                results = query2.setMaxResults(1000).getResultList();
            }
            
            LinkedList<IEntityForm> ret = new LinkedList<IEntityForm>();
            int ind = 1 ;
            for (Object o : results) {
                IEntityForm form = type.newInstance() ;
                copyEntityToForm(o, form) ;
                form.setSn(ind++) ;
                try {
                	Object id = PropertyUtil.getPropertyValue(o, "id") ;
                	
                	checkDynamicPermission(type, id, "View") ;
                    ret.add(form);
                } catch (Exception e) {
                	//e.printStackTrace() ;
                	// нельзя просматривать
                }
                
            }
            return (Collection<E>) ret ;
        } catch (Exception e) {
        	LOG.error(e,e) ;
            throw new EntityFormException("Ошибка", e);
        }

    }


//    private Method findParentMethod(Class aFormClass) throws NoSuchMethodException {
//        String property = getParentPropertyName(aFormClass) ;
//        return aFormClass.getMethod(PropertyUtil.getGetterMethodNameForProperty(property)) ;
//    }

    private Class findParentType(Class aFormClass, String aPropertyName) throws NoSuchMethodException {
//        String methodName = PropertyUtil.getGetterMethodNameForProperty(aPropertyName) ;
//        return aFormClass.getMethod(methodName).getReturnType();
        return PropertyUtil.getGetterMethod(aFormClass, aPropertyName).getReturnType() ;
    }

    private void checkDynamicParentPermission(Class aFormClass, Object aParentId, String aPolicyAction) {
        ADynamicSecurityInterceptor interceptor = (ADynamicSecurityInterceptor) aFormClass.getAnnotation(ADynamicSecurityInterceptor.class) ;
        if(interceptor!=null) {
//            String policyToExtend = getSecurityRole(aFormClass, aSuffix) ;
            InterceptorContext ctx = new InterceptorContext(theManager, theContext);
            for (Class interceptorClass : interceptor.value()) {
                try {
                    IDynamicParentSecurityInterceptor dynamicInterceptor = (IDynamicParentSecurityInterceptor) interceptorClass.newInstance() ;
                    dynamicInterceptor.checkParent(aPolicyAction, aParentId, ctx); 
                } catch (InstantiationException e) {
                    throw new IllegalStateException(e) ;
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e) ;
                }
            }
            //sb.append(interceptor.valueProperty()) ;
        }

    }


//    private String getParentPropertyName(Class aClass) {
//        Parent parentProperty = (Parent) aClass.getAnnotation(Parent.class);
//        return parentProperty.property();
//    }
}
