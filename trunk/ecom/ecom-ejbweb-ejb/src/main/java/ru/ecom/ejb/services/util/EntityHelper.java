package ru.ecom.ejb.services.util;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

/**
 */
public class EntityHelper {

    private final static Logger LOG = Logger.getLogger(EntityHelper.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();


    private EntityHelper() {
    }

    public boolean hasSimpleType(Method aMethod) {
    	boolean complex = aMethod.isAnnotationPresent(OneToMany.class)
    		|| aMethod.isAnnotationPresent(OneToOne.class)
    		|| aMethod.isAnnotationPresent(ManyToMany.class)
    		;
    	return !complex ;
    	
    }
    
    public String getComment(Class aEntityClass) {
    	if(aEntityClass.isAnnotationPresent(Comment.class)) {
    		Comment comment = (Comment) aEntityClass.getAnnotation(Comment.class) ;
    		return comment.value() ;
    	} else {
    		return null ;
    	}
    }
    
    public Object convertId(Class aEntity, String aId) {
        return Long.parseLong(aId);
    }

    public static EntityHelper getInstance() {
        return new EntityHelper();
    }

    public Object getEntityId(Object aEntity) {
    	try {
			return PropertyUtil.getPropertyValue(aEntity, "id") ;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    public String getEntityName(Class aEntityClass) {
        return aEntityClass.getSimpleName();
    }

    public List<Class> listAllEntities() {
    	try {
	        InputStream in = getClass().getResourceAsStream("/META-INF/persistence.xml");
	        if(in==null) throw new javax.jms.IllegalStateException("Нет ресурса /META-INF/persistence.xml") ;
	        try {
	            StringBuilder sb = new StringBuilder("Registered entities: ");
	        	List<String> list = listAllEntitiesClassnames(in) ;
	        	List<Class> ret = new LinkedList<Class>() ;
	        	for(String className: list) {
	                Class entityClass = theClassLoaderHelper.loadClass(className);
	        		ret.add(entityClass) ;
	                sb.append(getEntityName(entityClass));
	                sb.append(", ");
	        	}
	            sb.deleteCharAt(sb.length() - 2);
	            sb.append('.');
	            LOG.info(sb);
	        	return ret ;
	        } catch(Exception e) {
	            throw new IllegalStateException(e);
	        } finally {
	       		in.close() ;
	        }
    	} catch (Exception e) {
            throw new IllegalStateException(e);
    	}
    }
    
    public List<String> listAllEntities(String aResource) {
        LOG.info(new StringBuilder().append("Loading ").append(aResource).append(" ...").toString());
        InputStream in = getClass().getResourceAsStream(aResource);
    	return listAllEntitiesClassnames(in);
    }
    
    public String getHibernateName(Class aEntityClass) {
        return getSuperClass(aEntityClass).getSimpleName();
    }
    private Class getSuperClass(Class aClass) {
    	if (aClass == null) return null ;
    	if (aClass.equals(BaseEntity.class)) return null ;
    	Class superclass = aClass.getSuperclass(); 
    	if (superclass==null) return null ;
    	if(superclass.equals(BaseEntity.class)) return aClass ;
    	if(superclass.equals(VocBaseEntity.class)) return aClass ;
    	if(superclass.equals(VocIdCodeName.class)) return aClass ;
    	if(superclass.getSimpleName().equals("OmcAbstractVoc")) return aClass ;
    	return getSuperClass(superclass) ;
    }
	public boolean isCacheable(Class aEntityClass) {
		boolean ret =  !aEntityClass.isAnnotationPresent(MappedSuperclass.class) ;
		if(ret) {
			Class superClass = aEntityClass.getSuperclass() ;
			ret = superClass==null || !superClass.isAnnotationPresent(Entity.class) ;
		}
		if(ret && !isVocEntity(aEntityClass)) {
				ret = false ;
		}
		return ret ;
//		if(isVoc(aEntityClass)) {
//			Long count = getRowsCount(aEntityClass);
//			if(count<1000) {
//				return true ;
//			}
//		}
//		return false ;
	}
    public boolean isVocEntity(Class aClazz) {
//    	if(true) return true ;
    	if(aClazz==null) throw new IllegalArgumentException("Нет параметра "+aClazz) ;
    	if(aClazz.equals(BaseEntity.class)) return false ;
    	if(aClazz.equals(VocBaseEntity.class)) return true ;
    	if(aClazz.equals(VocIdCodeName.class)) return true ;
    	if(aClazz.getSimpleName().equals("OmcAbstractVoc")) return true ;
    	if(aClazz.equals(Object.class)) return false ;
    	return isVocEntity(aClazz.getSuperclass());
    }
    
    public List<String> listAllEntitiesClassnames(InputStream in) {
        try {
            List<String> list = new LinkedList<String>();
            //InputStream in = getClass().getResourceAsStream(aResource);
            try {
                Document doc = new SAXBuilder().build(in);
                Element rootElement = doc.getRootElement();
                List<Element> persistenceUnits = rootElement.getChildren("persistence-unit");
                for (Element persistenceUnit : persistenceUnits) {
                    List<Element> classes = persistenceUnit.getChildren("class");
                    for (Element clazz : classes) {
                        String className = clazz.getTextTrim();
                        //Class entityClass = theClassLoaderHelper.loadClass(className);
                        list.add(className);
                    }
                }

            } finally {
                in.close();
            }
            return list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private final ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();

    
    public String getTableName(Class aEntitClass) {
        Table table = (Table) aEntitClass.getAnnotation(Table.class) ;
        return (table==null || table.name().equals("")) ? aEntitClass.getSimpleName() : table.name() ;
    }
    
    public String getColumnName(Class aEntityClass, String aProperty) throws NoSuchMethodException {
        //String methodName = PropertyUtil.getGetterMethodNameForProperty(aProperty) ;
        Method method = PropertyUtil.getGetterMethod(aEntityClass, aProperty) ; //aEntityClass.getMethod(methodName) ;
        Column column = method.getAnnotation(Column.class) ;
        if(column!=null && !StringUtil.isNullOrEmpty(column.name())) {
            return column.name() ;
        } else {
            OneToOne oneToOne = method.getAnnotation(OneToOne.class) ;
            ManyToOne manyToOne = method.getAnnotation(ManyToOne.class) ;

            if(oneToOne!=null || manyToOne!=null) {
                // FIXME id - добавить функцию в EntityHelper getIdPropertyName()
                String name = PropertyUtil.getPropertyName(method)
                        +"_"
                        +getColumnName(method.getReturnType(), "id") ;
                return name ;
            }
        }
        return aProperty ;
    }
    
}
