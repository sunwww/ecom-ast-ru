package ru.ecom.ejb.services.entityform.interceptors;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * При просмотре формы
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface AViewInterceptors {
     AEntityFormInterceptor[] value() ;
}
