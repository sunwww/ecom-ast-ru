package ru.ecom.ejb.services.entityform.interceptors;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface ADynamicSecurityInterceptor {
    Class[] value() ;
}
