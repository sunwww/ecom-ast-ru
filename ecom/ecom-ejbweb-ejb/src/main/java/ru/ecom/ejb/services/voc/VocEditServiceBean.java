package ru.ecom.ejb.services.voc;

import java.lang.reflect.Method;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.ejb.services.voc.helper.EntityVocService;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.voc.IVocService;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocValue;

@Stateless
@Remote(IVocEditService.class)
@SecurityDomain("other")
public class VocEditServiceBean implements IVocEditService {

	public Object createVocValue(String aVocKey, VocValue aVocValue, VocAdditional aAdditional) {
		IVocContextService vocService = theInfoService.getVocService(aVocKey) ;
		if(vocService==null) throw new IllegalArgumentException("Нет справочника "+aVocKey) ;
		if(vocService instanceof EntityVocService) {
			EntityVocService entityVocService = (EntityVocService) vocService ;
			String name = entityVocService.getNames()[0] ;
			return  createEntity(entityVocService.getEntityClass(), name, aVocValue.getName()) ;
		}
		throw new IllegalStateException("Справочник "+aVocKey+" не поддерживает функцию добавления значений") ;
	}
	
	/**
	 * 
	 * @param aEntitClass
	 * @param aNameField
	 * @param aValue
	 * @return идентификатор нового объекта
	 * @throws Exception
	 */
	private Object createEntity(Class aEntitClass, String aNameField, String aValue) {
		try {
			Object obj = aEntitClass.newInstance() ;
			setProperty(obj, aNameField, aValue) ;
			EntityManager manager = theFactory.createEntityManager() ;
			manager.persist(obj) ;
			manager.close() ;
			return PropertyUtil.getPropertyValue(obj, "id") ;
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка создания нового "+aEntitClass.getName()
					+" [property="+aNameField+", value="+aValue+"]: "+e.getMessage(), e) ;
		}
	}
	
	private void setProperty(Object aObject, String aProperty, String aValue) throws Exception {
		if(aObject==null) throw new NullPointerException("Нет объекта aObject, aObject==null") ;
		Class clazz = aObject.getClass() ;
		Method getterMethod = PropertyUtil.getGetterMethod(clazz, aProperty) ;
		Method setterMethod = PropertyUtil.getSetterMethod(clazz, getterMethod) ;
		//String getter = PropertyUtil.getGetterMethodNameForProperty(aProperty) ;
		//String setter = PropertyUtil.getSetterMethodName(getter) ;
		//Method m = aObject.getClass().getMethod(setter, String.class) ;
		setterMethod.invoke(aObject, aValue) ;
	}

	public boolean isVocEditabled(String aVocKey) {
//		return true ;
		StringBuilder sb = new StringBuilder("/Policy/Voc/") ;
		sb.append(aVocKey) ;
		sb.append("/Create") ;
		return theContext.isCallerInRole(sb.toString()) ;
	}

	private @EJB IVocInfoService theInfoService ;
	private @Resource SessionContext theContext;
	private @PersistenceUnit EntityManagerFactory theFactory;
	
}   


