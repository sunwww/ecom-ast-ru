package ru.ecom.ejb.services.entityform.interceptors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * При просмотре формы
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface AViewInterceptors {
     AEntityFormInterceptor[] value() ;
}
