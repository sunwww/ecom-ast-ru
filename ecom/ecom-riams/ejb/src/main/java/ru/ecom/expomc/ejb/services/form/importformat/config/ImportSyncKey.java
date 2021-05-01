/**
 * Синхронизация по таблице c использованием состаного ключа
 * @author ikouzmin 15.03.2007 10:57:26
 */
package ru.ecom.expomc.ejb.services.form.importformat.config;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.util.EntityNameUtil;
import ru.nuzmsh.util.PropertyUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportSyncKey extends ImportKey {
	
	private static final Logger LOG = Logger.getLogger(ImportSyncKey.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    private String entityName;
    private Query query;
    private String entityClassName;


    public String getEntityName() {
        return entityName;
    }

    private List<ImportKey> getKeys() {
        String[] properties = getProperty().split(";");
        String[] selects = getSelect().split(";");
        List<ImportKey> list = new ArrayList<>();
        for (int i=0; i<properties.length; i++) {
            ImportKey importKey = new ImportKey();
            importKey.setProperty(properties[i]);
            importKey.setSelect(selects[i]);
            list.add(importKey);
        }
        return list;
    }

    private String getWhereClause() {
        String[] properties = getProperty().split(";");
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<properties.length; i++) {
            if (i > 0) sb.append(" AND ");
            //sb.append("cast("+properties[i]+" as string) = :p"+i);
            sb.append(properties[i]).append(" = :p").append(i);
        }
        return sb.toString();
    }

    /**
     * Возвращает строку WHERE для элемента
     * @param anElement
     */
    private String getWhereClause(Element anElement) {
        String[] properties = getProperty().split(";");
        String[] selects = getSelect().split(";");
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<properties.length; i++) {
            String s = "";
            try {
                Element el = (Element) XPath.selectSingleNode(anElement,selects[i]);
                s = el.getValue();
            } catch (JDOMException e) {}

            if (i > 0) sb.append(" AND ");
            sb.append(properties[i]).append(" = '").append(s).append("'");
        }
        return sb.toString();
    }

    public Query createQuery(EntityManager aManager,String entityClassName1) throws ClassNotFoundException {
        entityClassName = entityClassName1;
        Class clazz = Class.forName(entityClassName);
        entityName = EntityNameUtil.getInstance().getEntityName(clazz);
        String sql = "select id from "+entityName +" where "+getWhereClause();
        log(sql);
        try {
        	query = aManager.createQuery(sql);
        } catch (Exception e) {
        	throw new RuntimeException("Ошибка выполнения запроса "+sql+" : "+e) ;
        }
        return query;
    }

    public Object openId(EntityManager aManager,String id) {
        if (id==null || "".equals(id)) return null;
        //LOG.info("ID:"+id);
        try {
            return aManager.find(Class.forName(entityClassName),new Long(id));
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public String findIdObject(Object anElement) {
        if (anElement instanceof Element) {
            return findId((Element) anElement);
        }
        if (anElement instanceof HashMap) {
            return findId((HashMap<String, Object>) anElement);
        }

        throw new IllegalStateException("Illegal object type for synchronization");

    }

    public String findId(HashMap<String, Object> mapValues) {
        log("поиск записи");
        inclev();
        String[] properties = getProperty().split(";");
        String[] selects = getSelect().split(";");
    	Class entityClass;
		try {
			entityClass = ClassLoaderHelper.getInstance().loadClass(entityClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Ошибка при поиске класса "+entityClassName+" : "+e,e);
		}
        for (int i=0; i<properties.length; i++) {
            Object val = convertType(entityClass,properties[i], mapValues.get(selects[i])) ;
            log(i+":\t"+properties[i]+" = '"+val+"' \t {"+selects[i]+"}");
            query.setParameter("p"+i,val);
        }
        List keyList = query.getResultList();

        if (keyList.size()==1) {
            String keyid = keyList.get(0).toString();
            log("id:"+keyid);
            declev();
            return keyid;
        } else {
            log("найдено: "+keyList.size());
            declev();
            return null;
        }
    }


    private Object convertType(Class aClass, String aProperty, Object aValue) {
    	int dotPosition =aProperty.indexOf('.') ;
    	if(dotPosition>0) { // переход по вложенным
    		String firstProperty = aProperty.substring(0,dotPosition) ;
    		try {
    			Method m = PropertyUtil.getGetterMethod(aClass, firstProperty);
    			Class clazz = m.getReturnType() ;
    			String otherProperties = aProperty.substring(dotPosition+1) ;
    			if (CAN_DEBUG)
					LOG.debug("convertType: otherProperties = " + otherProperties);

    			return convertType(clazz, otherProperties, aValue);
    		} catch (Exception e) {
    			throw new RuntimeException("Ошибка при получении свойства "+firstProperty+": "+e,e) ;
    		}
    	} else {
        	try {
        		//Class entityClass = ClassLoaderHelper.getInstance().loadClass(theEntityClassName);
        		Method m = PropertyUtil.getGetterMethod(aClass, aProperty);
        		Class type = m.getReturnType() ;
        		if(aValue==null) {
        			return null ;
        		} else {
        			return PropertyUtil.convertValue(aValue.getClass(), type, aValue) ;
        		}

        	} catch (Exception e) {
        		throw new RuntimeException("Ошибка преобразования для свойства "+aProperty+ " в классе "+entityClassName+" для значения "+aValue+" : "+e,e);
        	}
        	
    	}
    }
    
    public String findId(Element anElement) {
        log("поиск записи");
        inclev();
        String[] properties = getProperty().split(";");
        String[] selects = getSelect().split(";");


        String val = "";
        for (int i=0; i<properties.length; i++) {
            try {
                val = getElementValue(XPath.selectSingleNode(anElement, selects[i]));
            } catch (JDOMException e) {}
            log(i+":\t"+properties[i]+" = '"+val+"' \t {"+selects[i]+"}");
//            LOG.info("\tSETP:"+i+":"+val);
            query.setParameter("p"+i,val);
        }

        if (properties.length == 1 && val.equals("")) {
            log("Синхронизация по пустому значению");
            declev();
            return null;
        }


        List keyList = query.getResultList();

//        LOG.info("COUNT:"+keyList.size());
        if (keyList.size()==1) {
            String keyid = keyList.get(0).toString();
            log("id:"+keyid);
            declev();
            return keyid;
        } else if (keyList.size()>1) {
        	log("найдено: "+keyList.size()+", пропускаем");
        	declev();
        	return "0";
        } else {
            log("найдено: "+keyList.size());
            declev();
            return null;
        }
    }

    public static String getElementValue(Object element) {
        String value;
        if (element == null) return "";
        if (element instanceof Element) {
            value = ((Element) element).getValue();
        } else if (element instanceof Attribute) {
            value = ((Attribute) element).getValue();
        } else {
            value = element.toString();
        }
        return value;
    }


}

