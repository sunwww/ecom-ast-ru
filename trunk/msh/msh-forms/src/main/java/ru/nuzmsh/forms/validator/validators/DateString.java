package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Ввод даты в виде строки dd.MM.yyyy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface DateString {

}
