package ru.nuzmsh.ejb.formpersistence.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  Сохранения для Entity Persistance EJB3
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface EntityFormPersistance {
    Class clazz() ;
}
