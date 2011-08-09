package ru.ecom.mis.ejb.service.vocabulary;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.jaas.ejb.domain.SystemVocabulary;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;



@Stateless
@Remote(IVocabularyService.class)
public class VocabularyServiceBean {
	public Collection<VocEntityInfo> listVocEntities() {
		Collection<Class> entities = theEntityHelper.listAllEntities() ;
		ArrayList<VocEntityInfo> ret = new ArrayList<VocEntityInfo>(entities.size()) ; 
		for(Class clazz : entities) {
			if(theEntityHelper.isVocEntity(clazz) && !clazz.isAnnotationPresent(Deprecated.class)) {
				//if (theContext.isCallerInRole("/Policy/Voc/"+clazz.getSimpleName())) {
					ret.add(getInfo(clazz));
				//}
			}
		}
		return ret;
	}
	
	/**
	 * Получить информацию по справочнику
	 * @param aVocEntityClass
	 * @return объект VocEntityInfo
	 */
	private VocEntityInfo getInfo(Class aVocEntityClass) {
		Comment comment = (Comment) aVocEntityClass.getAnnotation(Comment.class) ;
		String commentName = comment!=null?comment.value():aVocEntityClass.getSimpleName();
		SystemVocabulary systemIs = (SystemVocabulary)aVocEntityClass.getAnnotation(SystemVocabulary.class) ;
		
		VocEntityInfo info = new VocEntityInfo(aVocEntityClass.getName(),theEntityHelper.getEntityName(aVocEntityClass), commentName, 0,systemIs!=null?true:false);
		/*for(Method m : aVocEntityClass.getMethods()) {
			if(! m.getName().equals("getId")
					&& m.getName().startsWith("get")
					&& !m.getName().equals("getClass")
					&& theEntityHelper.hasSimpleType(m)) {
				String name = PropertyUtil.getPropertyName(m);
				Comment commentMethodAnnotation = m.getAnnotation(Comment.class) ;
				String propComment = commentMethodAnnotation!=null ? commentMethodAnnotation.value()  : name ;
				VocEntityPropertyInfo propInfo = new VocEntityPropertyInfo(name, propComment) ;
				info.getProperties().add(propInfo);
			}
		}*/
		return info ;
	}
	
	private int getCount(Class clazz) {
		Long totalCount = (Long)theManager.createQuery(new StringBuilder().append("select count(*) from ").append(
				theEntityHelper.getEntityName(clazz)).toString()).getSingleResult() ;
		return totalCount.intValue() ;
	}
	public int getCount(String clazz) {
		Long totalCount = (Long)theManager.createQuery(new StringBuilder().append("select count(*) from ").append(
				clazz).toString()).getSingleResult() ;
		return totalCount.intValue() ;
	}
	private @PersistenceContext EntityManager theManager ;
	private final EntityHelper theEntityHelper = EntityHelper.getInstance();
	@Resource SessionContext theContext ;
}
