package ru.ecom.ejb.services.entityform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

/**
 * Работа с родителями
 */
public class ParentUtil {

    public static Object getParentIdValue(IEntityForm aForm) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return findParentMethod(aForm.getClass()).invoke(aForm) ;
    }
    public static Method findParentMethod(Class aFormClass) throws NoSuchMethodException {
        String property = getParentPropertyName(aFormClass) ;
        return PropertyUtil.getGetterMethod(aFormClass, property) ;
        //return aFormClass.getMethod(PropertyUtil.getGetterMethodNameForProperty(property)) ;
    }

    public static Class getParentFormClass(Class<IEntityForm> aClass) {
        Parent parent = aClass.getAnnotation(Parent.class);
        if(parent==null) throw new IllegalArgumentException("У класса "+aClass+" нет аннотации Parent") ;
        if(!StringUtil.isNullOrEmpty(parent.parentMapForm())) {
        	MapClassLoader loader = new MapClassLoader(Thread.currentThread().getContextClassLoader()) ;
        	try {
				return loader.loadClass(parent.parentMapForm());
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("Ошибка загрузки класса "+parent.parentMapForm()+" при обработки аннотации "+parent+" у формы "+aClass,e) ;
			}
        } else {
            if(Void.class.equals(parent.parentForm())) {
            	throw new IllegalArgumentException("Не установлен атрубут parentForm в анотации Parent у класса "+aClass) ;
            }
            return parent.parentForm() ;
        }
    }

    protected static String getParentPropertyName(Class<?> type) {
        Parent parent = type.getAnnotation(Parent.class);
        if(parent==null) throw new IllegalArgumentException("У класса "+type+" нет аннотации Parent") ;
        return parent.property();
    }

    protected static String getOrderBy(Class<?> aClass) {
        Parent parent = aClass.getAnnotation(Parent.class);
        if(parent==null) throw new IllegalArgumentException("У класса "+aClass+" нет аннотации Parent") ;
        return parent.orderBy();
    }

}
