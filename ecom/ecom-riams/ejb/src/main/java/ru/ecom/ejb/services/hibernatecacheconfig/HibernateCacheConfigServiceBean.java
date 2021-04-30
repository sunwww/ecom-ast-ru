package ru.ecom.ejb.services.hibernatecacheconfig;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.util.PropertyUtil;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;

@Stateless
@Remote(IHibernateCacheConfigService.class)
public class HibernateCacheConfigServiceBean implements IHibernateCacheConfigService {

	@Override
	public String generateHibernateCacheProperties() {
		try {
			StringBuilder sb = new StringBuilder() ;
			for(Class clazz : EntityHelper.getInstance().listAllEntities()) {
				if(isCacheable(clazz)) {
					sb.append("hibernate.ejb.classcache.") ;
					sb.append(clazz.getName()) ;
					sb.append("=transactional\n") ;
					for(Method m : clazz.getDeclaredMethods()) {
						if(m.isAnnotationPresent(OneToMany.class) || m.isAnnotationPresent(ManyToMany.class)) {
							sb.append("hibernate.ejb.collectioncache.") ;
							sb.append(clazz.getName()).append(".").append(PropertyUtil.getPropertyName(m));
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

	public static boolean isCacheable(Class aEntityClass) {
		return entityHelper.isCacheable(aEntityClass);
	}

	private static final EntityHelper entityHelper = EntityHelper.getInstance();


}
