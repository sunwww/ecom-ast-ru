package ru.ecom.ejb.services.entityform;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.util.PropertyUtil;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

/**
 *  Работа с EntityForm
 *
 */
@Stateless
@Remote(IEntityFormService.class)
@Local(ILocalEntityFormService.class)
@SecurityDomain("other")
public class EntityFormServiceBean extends AbstractFormServiceBeanHelper implements IEntityFormService, Serializable, ILocalEntityFormService {

    private static final Logger LOG = Logger.getLogger(EntityFormServiceBean.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();


    @SuppressWarnings("unchecked")
	public <T extends IEntityForm> T loadForm(Class<T> aFormClass, Object aEntity) throws EntityFormException {
        checkPermission(aFormClass, "View") ;
        try {
            T form = aFormClass.newInstance();
            copyEntityToForm(aEntity, form);
            return form;
        } catch (Exception e) {
            throw new EntityFormException("Ошибка копирования",e);
        }
    }


    /**
     * Подготовка к созданию формы
     * @return форма
     */
    public <T extends IEntityForm> T prepareToCreate(Class<T> aFormClass) throws EntityFormException {
        checkPermission(aFormClass, "Create") ;
        try {
            return aFormClass.newInstance();
        } catch (Exception e) {
            throw new EntityFormException("Ошибка подготовке с созданию");
        }
    }

    /**
     * Список всех
     * @return коллекция форм
     */
    @SuppressWarnings("unchecked")
	public <E extends IEntityForm> Collection<E> listAll(Class<E> type) throws EntityFormException {
    	if (CAN_DEBUG) LOG.debug("listAll: checkingPersissions() ..."); 
        checkPermission(type, "View") ;

        if (CAN_DEBUG) LOG.debug("listAll: executing query ...");
        Class entityClass = findFormPersistance((Class<IEntityForm>) type).clazz();
        StringBuilder sql = new StringBuilder();
        sql.append("from ").append(entityClass.getSimpleName()).append(" c ");
        if (entityClass.isAnnotationPresent(UnDeletable.class)) {
            UnDeletable a =(UnDeletable) entityClass.getAnnotation(UnDeletable.class);
            sql.append(" where ").append(a.fieldName()).append(" is null or ").append(a.fieldName()).append(" is false");
        }
        Collection results = manager.createQuery( sql.toString()+ " order by id")
                .setMaxResults(300).getResultList();
        
    	if (CAN_DEBUG) LOG.debug("listAll: copying ..."); 
        LinkedList<IEntityForm> ret = new LinkedList<>();
        try {
        	int ind=1 ;
            for (Object o : results) {
                IEntityForm form = type.newInstance();
                copyEntityToForm(o, form);
                try {
                	Object id = PropertyUtil.getPropertyValue(o, "id") ;
                	form.setSn(ind++) ;
                	checkDynamicPermission(type, id, "View") ;
                    ret.add(form);
                } catch (Exception e) {
                	// нельзя просматривать
                }
            }
        } catch (Exception e) {
            throw new EntityFormException("Ошибка", e);
        }
        return (Collection<E>) ret;

    }


	public Collection<IEntityForm> listAll(String aClassname) throws EntityFormException {

		Collection<IEntityForm> ret = listAll(loadMapForm(aClassname));
		return convertToMapFormCollection(aClassname, ret) ;
	}
	
    public IEntityForm prepareToCreate(String aFormClassName) throws EntityFormException {
    	return convertToMapForm(aFormClassName, prepareToCreate(loadMapForm(aFormClassName)));
    }


}
