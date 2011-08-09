package ru.ecom.ejb.util;

/**
 */
public class EntityNameUtil {

    private EntityNameUtil() {

    }

    public static EntityNameUtil getInstance() {
        return new EntityNameUtil();
    }


    public String getEntityName(Class aClass) {
        return aClass.getSimpleName();
    }
}
