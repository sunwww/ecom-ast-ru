package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface Mkb {
	String clazz() default "VocIdc10";
    String field() default "code";
}
