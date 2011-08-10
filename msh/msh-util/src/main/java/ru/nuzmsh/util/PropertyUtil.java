package ru.nuzmsh.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.nuzmsh.util.format.DateFormat;

/**
 * Утилиты для работы со свойствами
 */
public class PropertyUtil {

	private final static Logger LOG = Logger.getLogger(PropertyUtil.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

    /**
     * Копировать значение свойства
     *
     * @param aDestObject     куда копировать
     * @param aSourceObject   откуда копировать
     * @param aGetterMethod   метов get*
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyProperty(Object aDestObject, Object aSourceObject, Method aGetterMethod) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        Method setterMethod = getSetterMethod(aDestObject.getClass(), aGetterMethod);
        Class inType = aGetterMethod.getReturnType() ;
        Class outType = aDestObject.getClass().getMethod(aGetterMethod.getName()).getReturnType() ;
        Object value = aGetterMethod.invoke(aSourceObject);
        Object convertedValue = convertValue(inType, outType, value) ;
        if (CAN_DEBUG) {
            LOG.debug("copyProperty():") ;
            LOG.debug(" getter = "+aGetterMethod);
            LOG.debug(" setter = "+setterMethod);
            LOG.debug(" value = " + value);
            LOG.debug(" convertedValue = " + convertedValue);
            if(value!=null) {
                LOG.debug(" value class = "+value.getClass()) ;
            }
            if(convertedValue!=null) {
                LOG.debug(" convertedValue class = "+convertedValue.getClass()) ;
            }
            
        }

        setterMethod.invoke(aDestObject, convertedValue);
    }


    /**
     * Поиск метода set* по методу get*
     * @param aClass        класс, где искать метод
     * @param aGetterMethod метод get*
     * @return метод set*
     * @throws NoSuchMethodException
     */
    public static Method getSetterMethod(Class aClass, Method aGetterMethod) throws NoSuchMethodException {
        String setterMethodName = getSetterMethodName(aGetterMethod.getName());
        Method getterMethod = aClass.getMethod(aGetterMethod.getName()) ;
        try {
            return aClass.getMethod(setterMethodName, getterMethod.getReturnType());
        } catch (NoSuchMethodException e) {
            for (Method method : aClass.getMethods()) {
                LOG.error("method = " + method);
            }
            throw e;
        }
    }

    /**
     * Получение название метода set* по названию метода get*
     * @param aGetterMethodName
     * @return название метода set*
     */
    public static String getSetterMethodName(String aGetterMethodName) {
    	String ret = GET_SETTER_METHOD_NAME.get(aGetterMethodName);
    	if(ret==null) {
            StringBuilder sb = new StringBuilder();
            if (CAN_DEBUG) LOG.debug("aGetterMethodName = " + aGetterMethodName);
            if(aGetterMethodName.startsWith("is")) {
                sb.append("set") ;
                sb.append(aGetterMethodName.substring(2));
            } else {
                sb.append("s");
                sb.append(aGetterMethodName.substring(1));
            }
            ret =  sb.toString();
            GET_SETTER_METHOD_NAME.put(aGetterMethodName, ret);
    	}
    	return ret ;
    }


    public static Object convertValue(Class aInClass, Class aOutClass, Object aValue) throws ParseException {
    	if(CAN_DEBUG) LOG.debug("convertValues()...") ;
        if (CAN_DEBUG) LOG.debug("   aInClass = " + aInClass);
        if (CAN_DEBUG) LOG.debug("   aOutClass = " + aOutClass);
        if (CAN_DEBUG) LOG.debug("   aValue = " + aValue);
//        System.out.println("convertValue "+aInClass+" - "+aOutClass+" - "+aValue);
//        if(aValue==null) {
//            return null ;
//        } else if(aInClass.equals(aOutClass)) {
//            return aValue ;
//        } else if (aInClass.equals(java.sql.Date.class) && aOutClass.equals(String.class)) {
//            return DateFormat.formatToDate((Date) aValue) ;
//        } else if (aInClass.equals(String.class) && aOutClass.equals(java.sql.Date.class)) {
//            java.util.Date utilDate = DateFormat.parseDate((String) aValue) ;
//            return utilDate!=null ? new java.sql.Date(utilDate.getTime()) : null ;
//        } else if(aInClass.equals(Long.TYPE) && aOutClass.equals(Long.class)) {
//            return (Long) aValue ;
//        } else if(aInClass.equals(Long.class) && aOutClass.equals(Long.TYPE)) {
//            return aValue ;
//        } else if (aInClass.equals(java.util.Date.class) && aOutClass.equals(String.class)) {
//            return DateFormat.formatToDate((Date) aValue) ;
//        }
//        throw new IllegalArgumentException("Нет преобразования из "+aInClass+" в "+aOutClass+ " для значения "+aValue) ;
    	if(aValue==null && aOutClass.equals(Integer.TYPE)) {
    		return 0 ;
    	} else if(aValue==null && aOutClass.equals(Boolean.TYPE)) {
        		return false ;
    	} else if(aValue==null && aOutClass.equals(Long.TYPE)) {
    		return 0L ;
    	} else if(aValue==null) {
            return null ;
        } else if(aInClass.equals(aOutClass)) {
            return aValue ;
        } else if (aInClass.equals(java.sql.Date.class) && aOutClass.equals(String.class)) {
            return DateFormat.formatToDate((Date) aValue) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(java.sql.Date.class)) {
            java.util.Date utilDate = DateFormat.parseDate((String) aValue) ;
            return utilDate!=null ? new java.sql.Date(utilDate.getTime()) : null ;
        } else if (aInClass.equals(java.sql.Timestamp.class) && aOutClass.equals(String.class)) {
        return aValue.toString() ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(java.sql.Timestamp.class)) {
        return Timestamp.valueOf((String) aValue) ;
        } else if(aInClass.equals(Long.TYPE) && aOutClass.equals(Long.class)) {
            return (Long) aValue ;
        } else if(aInClass.equals(Long.class) && aOutClass.equals(Long.TYPE)) {
            return aValue ;
        } else if (aInClass.equals(java.util.Date.class) && aOutClass.equals(String.class)) {
            return DateFormat.formatToDate((Date) aValue) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(BigDecimal.class)) {
        	return StringUtil.isNullOrEmpty((String)aValue) ? null : new BigDecimal((String) aValue) ; 
        } else if (aInClass.equals(BigDecimal.class) && aOutClass.equals(String.class)) {
        	return aValue!=null ? aValue.toString() : null ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Integer.TYPE)) {
            String str = (String) aValue ;
            return StringUtil.isNullOrEmpty(str) ? 0 : (int)Double.parseDouble(str) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Long.TYPE)) {
            String str = (String) aValue ;
            return StringUtil.isNullOrEmpty(str) ? 0 : Long.parseLong(str) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Long.class)) {
            String str = (String) aValue ;
            return StringUtil.isNullOrEmpty(str) ? 0 : Long.valueOf(str) ;
        } else if (aInClass.equals(Long.class) && aOutClass.equals(String.class)) {
            return aValue.toString() ;
        } else if (aInClass.equals(Integer.class) && aOutClass.equals(String.class)) {
            return aValue.toString() ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Integer.class)) {
        	 String str = (String) aValue ;
             return StringUtil.isNullOrEmpty(str) ? 0 : Integer.valueOf(str) ;
        } else if (aInClass.equals(java.util.Date.class) && aOutClass.equals(java.sql.Date.class)) {
        	java.util.Date date = (Date) aValue ;
        	return new java.sql.Date(date.getTime()) ;
        } else if (aInClass.equals(Long.class) && aOutClass.equals(Integer.TYPE)) {
        	 Long l = (Long) aValue ;
        	 return l.intValue() ;
        } else if (aInClass.equals(Integer.class) && aOutClass.equals(Integer.TYPE)) {
	       	 Integer i = (Integer) aValue ;
	       	 return i.intValue() ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(boolean.class)) {
	       	 return Boolean.parseBoolean((String)aValue) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Boolean.class)) {
	       	 return Boolean.valueOf((String)aValue) ;
        } else if (aInClass.equals(Boolean.class) && aOutClass.equals(String.class)) {
	       	 return String.valueOf(aValue) ;
        } else if (aInClass.equals(Boolean.class) && aOutClass.equals(Boolean.TYPE)) {
	       	 return ((Boolean)aValue).booleanValue() ;
        } else if (aInClass.equals(Boolean.TYPE) && aOutClass.equals(Boolean.class)) {
	       	 return (Boolean)aValue ;
        } else if (aInClass.equals(Integer.TYPE) && aOutClass.equals(Integer.class)) {
	       	 return (Integer)aValue ;
        } else if (aInClass.equals(Long.TYPE) && aOutClass.equals(Long.class)) {
	       	 return (Long)aValue ;
        } else if (aInClass.equals(boolean.class) && aOutClass.equals(String.class)) {
	       	 return String.valueOf(aValue) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(java.sql.Time.class)) {
            return DateFormat.parseSqlTime((String) aValue);
       } else if (aInClass.equals (java.sql.Time.class) && aOutClass.equals(String.class)) {
            return DateFormat.formatToTime((java.sql.Time) aValue);
       }
    	throw new IllegalArgumentException("Нет преобразования из "+aInClass+" в "+aOutClass+ " для значения "+aValue) ;

    }
    /**
     * Получение имени свойства по названию метода set* или get*
     * @param aMethod метод
     * @return название свойства
     * 
     * HASH
     */
    public static String getPropertyName(Method aMethod) {
    	String propertyName = GET_PROPERTY_NAME_HASH.get(aMethod);
    	if(propertyName==null) {
            String methodName = aMethod.getName() ;
            if(methodName.length()>2 && methodName.startsWith("is")) {
                propertyName = new StringBuilder().append(Character.toLowerCase(methodName.charAt(2))).append(methodName.substring(3)).toString() ;
                GET_PROPERTY_NAME_HASH.put(aMethod, propertyName) ;
            } else if(methodName.length()>3 && methodName.startsWith("get")) {
                propertyName = new StringBuilder().append(Character.toLowerCase(methodName.charAt(3))).append(methodName.substring(4)).toString() ;
                GET_PROPERTY_NAME_HASH.put(aMethod, propertyName) ;
            } else {
                throw new IllegalArgumentException("Метод "+aMethod+" не является методом свойтсва") ;
            }
    	}
    	return propertyName ;
    }
    
    
    /**
     *
     * @param aObject
     * @param aPropertyName
     */
    public static Object getPropertyValue(Object aObject, String aPropertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	if(aObject!=null) {
	    	if(aObject instanceof Object[]) {
	    		Object[]  arr = (Object[]) aObject ;
	    		return String.valueOf(arr[Integer.parseInt(aPropertyName)]) ;
	    	} else if(aObject instanceof Map ) {
	    		Map map = (Map) aObject ;
	    		return map.get(aPropertyName);
	    	} else {
	    		int pos = aPropertyName.indexOf('.') ;
	    		if(pos>=0) {
	    			String property = aPropertyName.substring(0,pos);
	    			Object obj = getPropertyValue(aObject, property) ;
	    			
	    			return obj!=null ? getPropertyValue(obj, aPropertyName.substring(pos+1)) : null;
	    		} else {
		        	return getMethodFormProperty(aObject.getClass(), aPropertyName).invoke(aObject) ;
	    		}
	    	}
        } else {
            throw new IllegalArgumentException("Нет объекта") ;
        }
    }

    public static Method getMethodFormProperty(Class aClass, String aPropertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	return getGetterMethod(aClass, aPropertyName) ;
    }

    public static Method getGetterMethod(Class aClass, String aPropertyName) throws NoSuchMethodException {
    	try {
	    	Method m ;
	    	try {
	    		m = aClass.getMethod(getGetterMethodNameForProperty(aPropertyName)) ;
	    	} catch (NoSuchMethodException e) {
	    		m = aClass.getMethod(getIsMethodNameForProperty(aPropertyName)) ;
	    	}
	        return m ;
    	} catch (Exception e) {
    		throw new IllegalStateException(e) ;
    	}
    }
    
    public static void setPropertyValue(Object aObject, String aProperty, Object aValue) throws NoSuchMethodException, ParseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	if(aObject==null) throw new IllegalArgumentException("Нет объекта. aObject==null") ;
    	Class clazz = aObject.getClass() ;
        //String getterMethodName = PropertyUtil.getGetterMethodNameForProperty(aProperty) ;
        Method getterMethod = PropertyUtil.getGetterMethod(clazz, aProperty) ; //aObject.getClass().getMethod(getterMethodName) ;
        Method setterMethod = PropertyUtil.getSetterMethod(clazz, getterMethod);
        if(aValue==null) {
            setterMethod.invoke(aObject, aValue) ; // setting null
        } else {
            Object convertedValue = convertValue(aValue.getClass(), getterMethod.getReturnType(), aValue) ;
            setterMethod.invoke(aObject, convertedValue) ;
        }
    }
    
    
    
    private static String getGetterMethodNameForProperty(String aPropertyName) {
        return new StringBuilder().append("get").append(Character.toUpperCase(aPropertyName.charAt(0))).append(aPropertyName.substring(1)).toString();
    }

    private static String getIsMethodNameForProperty(String aPropertyName) {
        return new StringBuilder().append("is").append(Character.toUpperCase(aPropertyName.charAt(0))).append(aPropertyName.substring(1)).toString();

    }
    
    
    public static void destroy() {
    	if (CAN_DEBUG) LOG.debug("Clearing GET_PROPERTY_NAME_HASH: " + GET_PROPERTY_NAME_HASH.size()); 
    	GET_PROPERTY_NAME_HASH.clear() ;
    	if (CAN_DEBUG) LOG.debug("Clearing GET_SETTER_METHOD_NAME: " + GET_SETTER_METHOD_NAME.size()); 
    	GET_SETTER_METHOD_NAME.clear() ;
    }
    // 
    private static final Map<Method,String> GET_PROPERTY_NAME_HASH = new HashMap<Method, String>() ;
    
    // getSetterMethodName
    private static final Map<String, String> GET_SETTER_METHOD_NAME = new HashMap<String, String>() ;
}
