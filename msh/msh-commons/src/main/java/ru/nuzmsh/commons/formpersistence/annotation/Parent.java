package ru.nuzmsh.commons.formpersistence.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Связанный родитель
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface Parent {
    /**
     * Метод для получения значения
     */
    String property() ;

    Class parentForm() default Void.class ;

    String orderBy() default "" ;
    
    String parentMapForm() default "" ;
}
