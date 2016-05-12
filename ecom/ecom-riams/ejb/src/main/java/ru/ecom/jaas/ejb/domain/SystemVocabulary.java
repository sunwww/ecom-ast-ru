package ru.ecom.jaas.ejb.domain;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Системный справочник
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE,METHOD})
public @interface SystemVocabulary {

}
