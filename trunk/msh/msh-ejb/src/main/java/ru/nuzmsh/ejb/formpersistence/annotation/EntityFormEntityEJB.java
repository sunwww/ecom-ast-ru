package ru.nuzmsh.ejb.formpersistence.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Сохранения для Entity EJB
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface EntityFormEntityEJB {
    /**
     * Простое название в JNDI
     * Например: если полное название ejb/stac/VocStacOTD, то jdniSimpleName - VocStacOTD
     */
    String jndiSimpleName() ;

    /**
     * Home interface
     */
    Class home() ;
}
