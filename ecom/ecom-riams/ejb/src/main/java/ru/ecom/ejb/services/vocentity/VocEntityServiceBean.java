package ru.ecom.ejb.services.vocentity;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Stateless
@Remote(IVocEntityService.class)
public class VocEntityServiceBean implements IVocEntityService {

	public void removeVocEntity(String aClassname, String aId) {
		Class clazz = loadVocEntityClass(aClassname);
		long id = Long.parseLong(aId);
		Object entity = manager.find(clazz, id);
		manager.remove(entity);
	}
	
	public Object setVocEntityValue(String aClassname, String aId, String aProperty, String aValue) {
		Class clazz = loadVocEntityClass(aClassname);
		Object ret ;
		Object entity ;
		if(StringUtil.isNullOrEmpty(aId)) {
			try {
				entity = clazz.newInstance() ;
				manager.persist(entity);
				ret = PropertyUtil.getPropertyValue(entity, "id");
			} catch (Exception e) {
				throw new IllegalStateException("Ошибка при создании нового: "+e,e) ;
			}
		} else {
			long id = Long.parseLong(aId);
			entity = manager.find(clazz, id);
			ret = id ;
		}
		try {
			PropertyUtil.setPropertyValue(entity, aProperty, aValue);
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка при установке свойства: "+e,e) ;
		}
		return ret ;
	}
	
	public VocEntityInfo getVocEntityInfo(String aClassname) {
		Class clazz = loadVocEntityClass(aClassname);
		return getInfo(clazz);
	}


	private Class loadVocEntityClass(String aClassname) {
		try {
			Class clazz = classLoaderHelper.loadClass(aClassname);
			if(clazz.isAnnotationPresent(Deprecated.class)) {
				throw new IllegalStateException("Класс "+aClassname+" устарел") ;
			}
			if(entityHelper.isVocEntity(clazz)) {
				return clazz; 
			} else {
				throw new IllegalStateException("Класс "+aClassname+" не является справочником") ;
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Класс "+aClassname+" не найден: "+e,e) ;
		}
	}
	
	private VocEntityInfo getInfo(Class aVocEntityClass) {
		Comment comment = (Comment) aVocEntityClass.getAnnotation(Comment.class) ;
		String commentName = comment!=null?comment.value():aVocEntityClass.getSimpleName();
		
		VocEntityInfo info = new VocEntityInfo(aVocEntityClass.getName(), commentName, getCount(aVocEntityClass));
		for(Method m : aVocEntityClass.getMethods()) {
			if(! m.getName().equals("getId")
					&& m.getName().startsWith("get")
					&& !m.getName().equals("getClass")
					&& entityHelper.hasSimpleType(m)) {
				String name = PropertyUtil.getPropertyName(m);
				Comment commentMethodAnnotation = m.getAnnotation(Comment.class) ;
				String propComment = commentMethodAnnotation!=null ? commentMethodAnnotation.value()  : name ;
				VocEntityPropertyInfo propInfo = new VocEntityPropertyInfo(name, propComment) ;
				info.getProperties().add(propInfo);
			}
		}
		return info ;
	}
	
	public Collection<VocEntityInfo> listVocEntities() {
		Collection<Class> entities = entityHelper.listAllEntities() ;
		ArrayList<VocEntityInfo> ret = new ArrayList<>(entities.size()) ;
		for(Class clazz : entities) {
			if(entityHelper.isVocEntity(clazz) && !clazz.isAnnotationPresent(Deprecated.class)) {
				ret.add(getInfo(clazz));
			}
		}
		return ret;
	}
	
	private int getCount(Class clazz) {
		Long totalCount = (Long)manager.createQuery("select count(*) from "
				+ entityHelper.getEntityName(clazz)).getSingleResult() ;
		return totalCount.intValue() ;
	}
	public String loadJsonValues(String aClassname, int aFrom, int aCount, String aOrderBy, boolean aAscending) {
		Class clazz = loadVocEntityClass(aClassname);
		VocEntityInfo info = getInfo(clazz);
		if(StringUtil.isNullOrEmpty(aOrderBy) ) {aOrderBy = "id";
		}
		List <Object> list1 = manager.createNativeQuery("select id from "+entityHelper.getTableName(clazz)
		+ " order by "+aOrderBy+" "+(aAscending?"asc":"desc")+", id "+(aAscending?"asc":"desc"))
		.setMaxResults(aFrom+aCount).getResultList() ;

		StringBuilder ids=new StringBuilder() ;
		if (list1.size()>aFrom) {
			for (int i=aFrom;i<list1.size();i++){
				ids.append(",").append(list1.get(i)) ;
			}
		}
		StringBuilder sql = new StringBuilder() ;
		

		sql.append("from ").append(entityHelper.getEntityName(clazz))
		.append(" where id in (").append(ids.length()>0?ids.substring(1):"").append(")")
		.append(" order by ").append(aOrderBy).append(" ").append((aAscending?"asc":"desc")).append(", id ").append((aAscending?"asc":"desc"));
		List  list = manager.createQuery(sql.toString())
				.setMaxResults(aCount)
				.getResultList();
		
		StringBuilder sb = new StringBuilder(2048) ;
		sb.append("{'totalCount':'").append(getCount(clazz)).append("',");
		sb.append("'topics':[") ;
		boolean firstPassed = false ;
		for(Object o : list) {
			if(firstPassed) sb.append(", ") ;else firstPassed=true;
			sb.append("{") ;
			try {
				sb.append("'id':'").append(PropertyUtil.getPropertyValue(o, "id")).append("'");
				for(VocEntityPropertyInfo prop : info.getProperties()) {
					Object value = PropertyUtil.getPropertyValue(o, prop.getName()) ;
					String strValue = value!=null?value.toString():"";
					sb.append(", '").append(prop.getName()).append("':'").append(strValue).append("'");
					
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
			sb.append("}") ;
		}
		sb.append("]}") ;
		return sb.toString();
		
	}
	
	private final EntityHelper entityHelper = EntityHelper.getInstance();
	private final ClassLoaderHelper classLoaderHelper = ClassLoaderHelper.getInstance();
    private @PersistenceContext EntityManager manager ;
}
