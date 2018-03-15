package ru.nuzmsh.util.at;

import ru.nuzmsh.util.at.AField;

import java.lang.reflect.Field;

/**
 *  Работа с описателем класса в аннотациях
 */
public class AtUtil {
    
    /** Название класса */
    public static final String       AT_CLAZZ = "CLAZZ"         ;
    /** Новое название поля */
    public static final String    AT_NEW_NAME = "NEW_NAME"      ;
    /** Старое название поля */
    public static final String       AT_FIELD = "FIELD"         ;
    /** Тип поля */
    public static final String        AT_TYPE = "TYPE"          ;
    /** Соединенный класс */
    public static final String AT_LINKED_NAME = "LINKED_NAME"   ;
    /** Описание поля */
    public static final String     AT_COMMENT = "COMMENT"       ;

    public static String getClassName(Class aClass) {
        return getValue(aClass, AT_CLAZZ) ;
    }
    public static String getClassName(AField aField) {
        return getClassName(aField.at()) ;
    }

    public static String getNewName(Class aClass) {
        return getValue(aClass, AT_NEW_NAME) ;
    }
    public static String getNewName(AField aField) {
        return getNewName(aField.at()) ;
    }

    public static String getFieldType(Class aClass) {
        return getValue(aClass, AT_TYPE) ;
    }
    public static String getFieldType(AField aField) {
        return getFieldType(aField.at()) ;
    }

    public static String getFieldName(Class aClass) {
        return getValue(aClass, AT_FIELD) ;
    }
    public static String getFieldName(AField aField) {
        return getFieldName(aField.at()) ;
    }

    public static String getVocName(Class aClass) {
        return getValue(aClass, AT_LINKED_NAME) ;
    }
    public static String getVocName(AField aField) {
        return getVocName(aField.at()) ;
    }

    public static String getComment(Class aClass) {
        return getValue(aClass, AT_COMMENT) ;
    }
    public static String getComment(AField aField) {
        return getComment(aField.at()) ;
    }

    private static String getValue(Class aClass, String aConstantName) {
        try {
            Field field = aClass.getDeclaredField(aConstantName) ;
            return (String)field.get(null) ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(aClass+" "+aConstantName+" : "+e, e) ;
        }
    }

}
