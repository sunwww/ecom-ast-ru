package ru.ecom.ejb.services.entityform;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

@Stateless
@Remote(IParentEntityFormService.class)
@Local(IParentEntityFormService.class)
@SecurityDomain("other")
public class ParentEntityFormServiceBean extends AbstractFormServiceBeanHelper implements IParentEntityFormService, Serializable {

    private static final Logger LOG = Logger.getLogger(ParentEntityFormServiceBean.class);


    public IEntityForm prepareToCreate(String aFormClassName, Object aParentId) throws EntityFormException {
        return convertToMapForm(aFormClassName, prepareToCreate(loadMapForm(aFormClassName), aParentId));
    }

    public <T extends IEntityForm> T prepareToCreate(Class<T> aFormClass, Object aParentId) throws EntityFormException {
        checkPermission(aFormClass, "Create");
        if (aParentId == null) throw new IllegalArgumentException("Нет параметра aParentId (null)");
        try {
            T form = aFormClass.newInstance();
            Method getterFormMethod = ParentUtil.findParentMethod(aFormClass);
            if (getterFormMethod.getReturnType().getAnnotation(Entity.class) != null) {
                Class entityClass = findFormPersistanceClass(aFormClass);
                Object linkedEntity = manager.find(entityClass, aParentId);
                setValue(form, linkedEntity, entityClass, getterFormMethod);
            } else {
                Method formSetterMethod = PropertyUtil.getSetterMethod(
                        aFormClass, getterFormMethod);
                //setValue(form, aParentId, getterFormMethod);
                formSetterMethod.invoke(form, aParentId);
            }
            intercept(form, aParentId, aFormClass.getAnnotation(AParentPrepareCreateInterceptors.class));
            checkDynamicParentPermission(aFormClass, aParentId, "Create");
            return form;
        } catch (Exception e) {
            throw new EntityFormException("Ошибка подготовки к созданию новой формы", e);
        }
    }

    private void intercept(IEntityForm aForm, Object aParentId, AParentPrepareCreateInterceptors aAnnotation) throws IllegalAccessException, InstantiationException {
        if (aAnnotation != null) {
            InterceptorContext ctx = new InterceptorContext(manager, context);
            for (AParentEntityFormInterceptor interceptor : aAnnotation.value()) {
                IParentFormInterceptor inter = (IParentFormInterceptor) interceptor.value().newInstance();
                inter.intercept(aForm, null, aParentId, ctx);
            }
        }
    }

    public Collection<IEntityForm> listAll(String aClassName, Object aParentId) throws EntityFormException {
        return convertToMapFormCollection(aClassName, listAll(loadMapForm(aClassName), aParentId));
    }

    public <E extends IEntityForm> Collection<E> listAll(Class<E> type, Object aParentId) throws EntityFormException {
        checkPermission(type, "View");
        checkDynamicParentPermission(type, aParentId, "View");

        try {
            String parentPropertyName = ParentUtil.getParentPropertyName(type);
            String orderBy = ParentUtil.getOrderBy(type);
            Class entityClass = findFormPersistanceClass(type);
            Class parentClass = findParentType(entityClass, parentPropertyName);
            Object entity = manager.find(parentClass, aParentId);

            // FIXME для кэша второго уровня. Доработать!!!
            Collection results; //getOneToManyCollection(entity, parentClass, entityClass);
            LOG.debug("listAll: creating query...");

            StringBuilder query = new StringBuilder();
            query.append("from ").append(findFormPersistance((Class<IEntityForm>) type).clazz().getSimpleName()).append(" c where ")
                    .append(parentPropertyName);
            if (entity == null) {
                query.append(" is null ");
            } else {
                query.append(" = :").append(parentPropertyName);
            }

            if (StringUtil.isNullOrEmpty(orderBy)) {
                query.append(" order by id");
            } else {
                query.append(" order by ").append(orderBy);
            }

            Query query2 = manager.createQuery(query.toString());
            if (entity != null) {
                query2.setParameter(parentPropertyName, entity);
            }
            results = query2.setMaxResults(1000).getResultList();

            LinkedList<IEntityForm> ret = new LinkedList<>();
            int ind = 1;
            for (Object o : results) {
                IEntityForm form = type.newInstance();
                copyEntityToForm(o, form);
                form.setSn(ind++);
                try {
                    Object id = PropertyUtil.getPropertyValue(o, "id");

                    checkDynamicPermission(type, id, "View");
                    ret.add(form);
                } catch (Exception e) {
                    //e.printStackTrace() ;
                    // нельзя просматривать
                }

            }
            return (Collection<E>) ret;
        } catch (Exception e) {
            LOG.error(e, e);
            throw new EntityFormException("Ошибка", e);
        }

    }


    private Class findParentType(Class aFormClass, String aPropertyName) throws NoSuchMethodException {
        return PropertyUtil.getGetterMethod(aFormClass, aPropertyName).getReturnType();
    }

    private void checkDynamicParentPermission(Class aFormClass, Object aParentId, String aPolicyAction) {
        ADynamicSecurityInterceptor interceptor = (ADynamicSecurityInterceptor) aFormClass.getAnnotation(ADynamicSecurityInterceptor.class);
        if (interceptor != null) {
            InterceptorContext ctx = new InterceptorContext(manager, context);
            for (Class interceptorClass : interceptor.value()) {
                try {
                    IDynamicParentSecurityInterceptor dynamicInterceptor = (IDynamicParentSecurityInterceptor) interceptorClass.newInstance();
                    dynamicInterceptor.checkParent(aPolicyAction, aParentId, ctx);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
