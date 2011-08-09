package ru.ecom.ejb.services.vocentity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;


@Stateless
@Remote(IVocEntityService.class)
public class VocEntityServiceBean implements IVocEntityService {

	public void removeVocEntity(String aClassname, String aId) {
		Class clazz = loadVocEntityClass(aClassname);
		long id = Long.parseLong(aId);
		Object entity = theManager.find(clazz, id);
		theManager.remove(entity);
	}
	
	public Object setVocEntityValue(String aClassname, String aId, String aProperty, String aValue) {
		Class clazz = loadVocEntityClass(aClassname);
		Object ret ;
		Object entity ;
		if(StringUtil.isNullOrEmpty(aId)) {
			try {
				entity = clazz.newInstance() ;
				theManager.persist(entity);
				ret = PropertyUtil.getPropertyValue(entity, "id");
			} catch (Exception e) {
				throw new IllegalStateException("Ошибка при создании нового: "+e,e) ;
			}
		} else {
			long id = Long.parseLong(aId);
			entity = theManager.find(clazz, id);
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
			Class clazz = theClassLoaderHelper.loadClass(aClassname);
			if(clazz.isAnnotationPresent(Deprecated.class)) {
				throw new IllegalStateException("Класс "+aClassname+" устарел") ;
			}
			if(theEntityHelper.isVocEntity(clazz)) {
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
					&& theEntityHelper.hasSimpleType(m)) {
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
		Collection<Class> entities = theEntityHelper.listAllEntities() ;
		ArrayList<VocEntityInfo> ret = new ArrayList<VocEntityInfo>(entities.size()) ; 
		for(Class clazz : entities) {
			if(theEntityHelper.isVocEntity(clazz) && !clazz.isAnnotationPresent(Deprecated.class)) {
				ret.add(getInfo(clazz));
			}
		}
		return ret;
	}
	
	private int getCount(Class clazz) {
		Long totalCount = (Long)theManager.createQuery("select count(*) from "
				+ theEntityHelper.getEntityName(clazz)).getSingleResult() ;
		return totalCount.intValue() ;
	}
	public String loadJsonValues(String aClassname, int aFrom, int aCount, String aOrderBy, boolean aAscending) {
		Class clazz = loadVocEntityClass(aClassname);
		VocEntityInfo info = getInfo(clazz);
		//String fieldFind = "" ;
		if(StringUtil.isNullOrEmpty(aOrderBy) ) {aOrderBy = "id"; 
			//fieldFind="id" ;
		} else {
			//fieldFind=aOrderBy+"||'#'||id";
		}
		List <Object> list1 = theManager.createNativeQuery("select id from "+theEntityHelper.getTableName(clazz)
		+ " order by "+aOrderBy+" "+(aAscending?"asc":"desc")+", id "+(aAscending?"asc":"desc"))
		.setMaxResults(aFrom+aCount).getResultList() ;
		//System.out.println("size="+list1.size()+" from="+aFrom) ;
		
		StringBuilder ids=new StringBuilder() ;
		if (list1.size()>aFrom) {
			//val = list1.get(list1.size()-1) ;
			//System.out.println("get="+list1.get(list1.size()-aCount));
			for (int i=aFrom;i<list1.size();i++){
				ids.append(",").append(list1.get(i)) ;
			}
		}
		StringBuilder sql = new StringBuilder() ;
		
		/*sql.append("from "+theEntityHelper.getEntityName(clazz)) 
			.append(" where ").append(fieldFind).append((aAscending?">":"<")).append("'").append(val).append("'")
			.append(" order by ").append(aOrderBy).append(" ").append((aAscending?"asc":"desc")).append(", id ").append((aAscending?"asc":"desc"));
		*/
		
		sql.append("from "+theEntityHelper.getEntityName(clazz)) 
		.append(" where id in (").append(ids.length()>0?ids.substring(1):"").append(")")
		.append(" order by ").append(aOrderBy).append(" ").append((aAscending?"asc":"desc")).append(", id ").append((aAscending?"asc":"desc"));
		//System.out.println(sql.toString()) ;
		List  list = theManager.createQuery(sql.toString())
				//.setParameter("val", val)
				.setMaxResults(aCount)
				.getResultList();
		
		/*List list = theManager.createQuery("from "+theEntityHelper.getEntityName(clazz) 
				+ " order by "+aOrderBy+" "+(aAscending?"asc":"desc"))
			.setFirstResult(aFrom)
			.setMaxResults(aCount)
			.getResultList();
		*/
		StringBuilder sb = new StringBuilder(2048) ;
		sb.append("{'totalCount':'"+getCount(clazz)+"',") ;
		sb.append("'topics':[") ;
		boolean firstPassed = false ;
		for(Object o : list) {
			if(firstPassed) sb.append(", ") ;else firstPassed=true;
			sb.append("{") ;
			try {
				sb.append("'id':'"+PropertyUtil.getPropertyValue(o, "id")+"'") ;
				for(VocEntityPropertyInfo prop : info.getProperties()) {
					Object value = PropertyUtil.getPropertyValue(o, prop.getName()) ;
					String strValue = value!=null?value.toString():"";
					sb.append(", '"+prop.getName()+"':'"+strValue+"'") ;
					
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
			sb.append("}") ;
		}
		sb.append("]}") ;
		return sb.toString();
		
	}
	
	private final EntityHelper theEntityHelper = EntityHelper.getInstance();
	private final ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();
    private @PersistenceContext EntityManager theManager ;
}
