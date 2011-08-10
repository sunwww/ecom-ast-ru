package ru.nuzmsh.forms.validator.fields;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Падающий список
 */
@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface ComboBox {

    /**
     * В каком справочнике находятся значения
     */
    String voc() ;
}
