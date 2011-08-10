package ru.nuzmsh.commons.formpersistence.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Описание
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE,METHOD})
public @interface Comment {
    String value() ;
}


