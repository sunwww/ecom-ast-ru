package ru.ecom.ejb.services.hibernatecacheconfig;

import java.lang.reflect.Method;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

@Stateless
@Remote(IHibernateCacheConfigService.class)
public class HibernateCacheConfigServiceBean implements IHibernateCacheConfigService {

	public String generateHibernateCacheProperties() {
		try {
			StringBuilder sb = new StringBuilder() ;
			for(Class clazz : theEntityHelper.getInstance().listAllEntities()) {
				if(isCacheable(clazz)) {
					String comment = theEntityHelper.getComment(clazz) ;
					if(StringUtil.isNullOrEmpty(comment)) comment = clazz.getSimpleName();
					//sb.append("# ").append(comment+" , количество "+getRowsCount(clazz)) ;
					//sb.append("\n") ;
					sb.append("hibernate.ejb.classcache.") ;
					sb.append(clazz.getName()) ;
					sb.append("=transactional\n") ;
					for(Method m : clazz.getDeclaredMethods()) {
						if(m.isAnnotationPresent(OneToMany.class) || m.isAnnotationPresent(ManyToMany.class)) {
							sb.append("hibernate.ejb.collectioncache.") ;
							sb.append(clazz.getName()+"."+PropertyUtil.getPropertyName(m)) ;
							sb.append("=transactional\n") ;
						}
					}
					
				}
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("Ошибка создания списка кешированных классов :"+e,e) ;
		}
	} 

	public String generateEhCache() {
		try {
			StringBuilder sb = new StringBuilder() ;
			
			for(Class clazz : theEntityHelper.getInstance().listAllEntities()) {
				if(isCacheable(clazz)) {
					String comment = theEntityHelper.getComment(clazz) ;
					if(StringUtil.isNullOrEmpty(comment)) comment = clazz.getSimpleName();
					//sb.append("# ").append(comment+" , количество "+getRowsCount(clazz)) ;
					//sb.append("\n") ;
					sb.append("hibernate.ejb.classcache.") ;
					sb.append(clazz.getName()) ;
					sb.append("=read-write\n") ;
					
				}
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("Ошибка создания списка кешированных классов :"+e,e) ;
		}
	} 
	
	private long getRowsCount(Class aEntityClass) {
		return (Long) theManager.createQuery("select count(*) from "+aEntityClass.getName())
		.getSingleResult();
	}
	
	public static boolean isCacheable(Class aEntityClass) {
		return theEntityHelper.isCacheable(aEntityClass);
	}
	
	private static boolean isVoc(Class aClass) {
		Class superClass = aClass.getSuperclass() ;
		if(superClass==null) {
			return false ;
		} if(superClass.equals(VocIdCodeName.class)) {
			return true ;
		} else {
			return isVoc(superClass);
		}
	}
	
	
	private final static EntityHelper theEntityHelper = EntityHelper.getInstance();
    private @PersistenceContext EntityManager theManager ;
   

}
