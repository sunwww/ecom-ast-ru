/**
 * Синхронизация по таблице c использованием состаного ключа
 * @author ikouzmin 15.03.2007 10:57:26
 */
package ru.ecom.expomc.ejb.services.form.importformat.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.util.EntityNameUtil;
import ru.nuzmsh.util.PropertyUtil;

public class ImportSyncKey extends ImportKey {
	
	private final static Logger LOG = Logger.getLogger(ImportSyncKey.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    private String theEntityName;
    private Query theQuery;
    private String theEntityClassName;


    public String getEntityName() {
        return theEntityName;
    }

    private List<ImportKey> getKeys() {
        String[] properties = getProperty().split(";");
        String[] selects = getSelect().split(";");
        List<ImportKey> list = new ArrayList<ImportKey>();
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
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<properties.length; i++) {
            if (i > 0) sb.append(" AND ");
            //sb.append("cast("+properties[i]+" as string) = :p"+i);
            sb.append(properties[i]+" = :p"+i);
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
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<properties.length; i++) {
            String s = "";
            try {
                Element el = (Element) XPath.selectSingleNode(anElement,selects[i]);
                s = el.getValue();
            } catch (JDOMException e) {}

            if (i > 0) sb.append(" AND ");
            sb.append(properties[i]+" = '" + s + "'");
        }
        return sb.toString();
    }

    public Query createQuery(EntityManager aManager,String entityClassName) throws ClassNotFoundException {
        theEntityClassName = entityClassName;
        Class clazz = Class.forName(theEntityClassName);
        theEntityName = EntityNameUtil.getInstance().getEntityName(clazz);
        String sql = "select id from "+theEntityName +" where "+getWhereClause();
        log(sql);
        try {
        	theQuery = aManager.createQuery(sql);
        } catch (Exception e) {
        	throw new RuntimeException("Ошибка выполнения запроса "+sql+" : "+e) ;
        }
        return theQuery;
    }

    public Object openId(EntityManager aManager,String id) {
        if (id==null || "".equals(id)) return null;
        //LOG.info("ID:"+id);
        try {
            return aManager.find(Class.forName(theEntityClassName),new Long(id));
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
			entityClass = ClassLoaderHelper.getInstance().loadClass(theEntityClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Ошибка при поиске класса "+theEntityClassName+" : "+e,e);
		}
        for (int i=0; i<properties.length; i++) {
            //String val = "";
            //val =  mapValues.get(selects[i]).toString();
            Object val = convertType(entityClass,properties[i], mapValues.get(selects[i])) ;
            log(i+":\t"+properties[i]+" = '"+val+"' \t {"+selects[i]+"}");
            //theQuery.
            theQuery.setParameter("p"+i,val);
        }
        List keyList = theQuery.getResultList();

//        LOG.info("COUNT:"+keyList.size());
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
        		throw new RuntimeException("Ошибка преобразования для свойства "+aProperty+ " в классе "+theEntityClassName+" для значения "+aValue+" : "+e,e);
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
            theQuery.setParameter("p"+i,val);
        }

        if (properties.length == 1 && val.equals("")) {
            log("Синхронизация по пустому значению");
            declev();
            return null;
        }


        List keyList = theQuery.getResultList();

//        LOG.info("COUNT:"+keyList.size());
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

