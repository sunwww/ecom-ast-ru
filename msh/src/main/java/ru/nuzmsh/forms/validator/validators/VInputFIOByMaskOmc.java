package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Нельзя вводить латинские символы
 */
@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface VInputFIOByMaskOmc {

}
