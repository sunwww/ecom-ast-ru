package ru.nuzmsh.forms.util;

import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

import javax.ejb.EJBLocalObject;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;

import ru.nuzmsh.forms.validator.validators.TimeString;
import ru.nuzmsh.util.at.AField;
import ru.nuzmsh.util.at.AtUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * @author ESinev
 *         Date: 19.12.2005
 *         Time: 17:08:46
 */
public class FormUtil {
    private final static Logger LOG = Logger.getLogger(FormUtil.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;



    public static void saveData(ActionForm aForm, EJBLocalObject aBean, Class aBeanClass) {
        Class formClass = aForm.getClass();
        Class beanClass = aBean.getClass();
        Method[] methods = formClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            AField field = method.getAnnotation(AField.class);
            if (field != null) {
                if(CAN_DEBUG) LOG.debug("save To EJB: " + method.getName());
                try {
                    Object value =  method.invoke(aForm);
                    if(method.isAnnotationPresent(TimeString.class) && value!=null) {
                    	value = value.toString().replace(':', '.');
                    }
                    saveDataCopyToBean(aBean, "s" + method.getName().substring(1), value, AtUtil.getFieldType(field)) ;
                } catch (Exception e) {
                    LOG.debug(e + "", e);
                    throw new RuntimeException("Ошибка при установке свойства "
                            + method +" у EJB "+ aBeanClass.getSimpleName() +" :"+e,e) ;
                }
            }
        }
    }

    /**
     * Если "", то null
     */
    private static void saveDataCopyToBean(EJBLocalObject aBean, String aSetterMethodName, Object aValue, String aType) throws ClassNotFoundException, NoSuchMethodException, ParseException, IllegalAccessException, InvocationTargetException {
        Class clazz = aBean.getClass() ;
        Class typeClazz ;
        if("String".equals(aType)) {
            typeClazz = String.class ;
        } else if("Long".equals(aType)){
            typeClazz = Long.class ;
        } else {
            typeClazz = Class.forName(aType) ;
        }
        Method method = clazz.getMethod(aSetterMethodName, typeClazz) ;
        String typeClazzName = typeClazz.getName() ;
        Object value  ;
        if("java.util.Date".equals(typeClazzName) && aValue instanceof String && aValue!=null) {
            if(!"".equals(aValue)) {
                Date d = DateFormat.parseDate((String) aValue) ;
                value = d ;
            } else {
                value = null ;
            }
        } else if("java.math.BigDecimal".equals(typeClazzName) && aValue instanceof String && aValue!=null) {
            if(!"".equals(aValue)) {
                value = new BigDecimal((String) aValue) ;
            } else {
                value = null ;
            }
        } else if(typeClazz.equals(String.class)) {

            value = "".equals(aValue) ? null : aValue ;
            if(CAN_DEBUG) LOG.debug("saveDataCopyToBean(): value = '" + value+"'");
        } else {
            value = aValue ;
        }
        if(CAN_DEBUG) LOG.debug("saveDataCopyToBean(): "+aSetterMethodName+" = "+value);
        method.invoke(aBean, value) ;
    }


    public static void loadForm(EJBLocalObject aBean, ActionForm aForm, Class aBeanClass) throws NoSuchMethodException {
        Class formClass = aForm.getClass();
        Class beanClass = aBean.getClass();
        Method[] methods = formClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            AField field = method.getAnnotation(AField.class);
            if (field != null) {
                if(CAN_DEBUG) LOG.debug("loadData : " + method.getName());
                try {

                    Method beanGetterMethod = beanClass.getMethod(method.getName());
                    Class beanGetterMethodReturnType = beanGetterMethod.getReturnType() ;
                    String formSetterMethodName = "s" + method.getName().substring(1);
                    if (beanGetterMethodReturnType.equals(String.class)) { // STRING
                        Object value = (String) beanGetterMethod.invoke(aBean);
                        if(method.isAnnotationPresent(TimeString.class) && value!=null) {
                        	value = value.toString().replace('.', ':');
                        }
                        Method formSetterMethod = formClass.getMethod(formSetterMethodName, String.class);
                        formSetterMethod.invoke(aForm, value);
                    } else if (beanGetterMethodReturnType.equals(Date.class)) { // DATE
                        Date value = (Date) beanGetterMethod.invoke(aBean);

                        Method formSetterMethod = formClass.getMethod(formSetterMethodName, String.class);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        String date = value != null ? dateFormat.format(value) : null;
                        formSetterMethod.invoke(aForm, date);
                    } else if(beanGetterMethodReturnType.equals(Boolean.class)){ // BOOLEAN
                        //boolean value = (Boolean) beanGetterMethod.invoke(aBean);
                        Boolean valueBoolean = (Boolean) beanGetterMethod.invoke(aBean);
                        boolean value = valueBoolean != null ? valueBoolean.booleanValue() : false ;
                        Method formSetterMethod = formClass.getMethod(formSetterMethodName, boolean.class);
                        formSetterMethod.invoke(aForm, value) ;
                    } else if(beanGetterMethodReturnType.equals(Long.class)){ // LONG
                        Long value = (Long) beanGetterMethod.invoke(aBean);
                        Method formSetterMethod = formClass.getMethod(formSetterMethodName, Long.class);
                        formSetterMethod.invoke(aForm, value) ;
                    } else if (beanGetterMethodReturnType.equals(BigDecimal.class)) { // DATE
                        BigDecimal value = (BigDecimal) beanGetterMethod.invoke(aBean);

                        Method formSetterMethod = formClass.getMethod(formSetterMethodName, String.class);

                        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        String date = value != null ? value.toString() : null;
                        formSetterMethod.invoke(aForm, date);
                    } else {
                        throw new RuntimeException("Нет сохранения для типа данных: "+beanGetterMethod.getReturnType()) ;
                    }

                } catch (Exception e) {
                    LOG.debug(e + "", e);
                    LOG.debug("Методы "+aBeanClass+" :") ;
                    for(Method m : aBeanClass.getMethods()) {
                    	LOG.debug("     "+m) ;
                    }
                    throw new NoSuchMethodException("Метода " + method + " у EJB "
                            + aBeanClass.getSimpleName() + " не существует."
                            +" :"+e
                    );
                }
            }
        }
    }

}
