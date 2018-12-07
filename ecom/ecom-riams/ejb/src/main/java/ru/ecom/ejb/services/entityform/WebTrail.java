package ru.ecom.ejb.services.entityform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Для создания переходов на родители
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WebTrail {
    String comment() ;
    String[] nameProperties() ;
    String view() ;
    String list() default "";
    String[] listStyle() default {} ;
    String shortView() default "";
    String shortList() default "";
    String listComment() default "Список" ;
    boolean journal() default false ;
}
