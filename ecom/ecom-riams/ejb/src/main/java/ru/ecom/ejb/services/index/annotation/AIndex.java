package ru.ecom.ejb.services.index.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Индекс для таблицы
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AIndex {
    /** Уникальный ? По умолчанию - неуникальный */
    boolean unique() default false ;
    /** Свойства */
    String[] properties() ;
    /** Имя */
    String name() default "";
    String table() default "";
}
