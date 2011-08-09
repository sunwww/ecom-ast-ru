package ru.ecom.ejb.services.entityform;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.util.PropertyUtil;

/**
 *  Работа с EntityForm
 *
 */
@Stateless
@Remote(IEntityFormService.class)
@Local(ILocalEntityFormService.class)
@SecurityDomain("other")
public class EntityFormServiceBean extends AbstractFormServiceBeanHelper implements IEntityFormService, Serializable, ILocalEntityFormService {

//    private final static Log LOG = LogFactory.getLog(EntityFormServiceBean.class);
//    private final static boolean CAN_TRACE = LOG.isTraceEnabled();

    private final static Logger LOG = Logger
			.getLogger(EntityFormServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();


    @SuppressWarnings("unchecked")
	public <T extends IEntityForm> T loadForm(Class<T> aFormClass, Object aEntity) throws EntityFormException {
    	//System.out.println("loadForm local") ;
        checkPermission(aFormClass, "View") ;
        try {
            IEntityForm form = aFormClass.newInstance();
            copyEntityToForm(aEntity, form);
            return (T) form ;
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
        Collection results = theManager.createQuery("from "
                + findFormPersistance((Class<IEntityForm>) type).clazz().getSimpleName() + " c order by id")
                .setMaxResults(200).getResultList();
        
    	if (CAN_DEBUG) LOG.debug("listAll: copying ..."); 
        LinkedList<IEntityForm> ret = new LinkedList<IEntityForm>();
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
		//Class clazz = loadMapForm(aClassname);
		
		//if (CAN_DEBUG)
		//	LOG.debug("listAll: clazz = " + clazz); 
		
		Collection<IEntityForm> ret = listAll(loadMapForm(aClassname));
		//dumpCollection(ret);
		return convertToMapFormCollection(aClassname, ret) ;
	}
	
    public IEntityForm prepareToCreate(String aFormClassName) throws EntityFormException {
    	return convertToMapForm(aFormClassName, prepareToCreate(loadMapForm(aFormClassName)));
    }
	
    
	private void dumpCollection(Collection aObject) {
		if (CAN_DEBUG)
			LOG.debug("dumpCollection: collection = " + aObject); 

		for (Object o : aObject) {
			if (CAN_DEBUG)
				LOG.debug("   object: " + aObject); 

			dump(o);
		}
	}
	
	private void dump(Object aObject) {
		for (Method m : aObject.getClass().getMethods()) {
			if (CAN_DEBUG)
				LOG.debug("  " + m);
		}
	}
	
	
}
