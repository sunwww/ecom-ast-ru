package ru.ecom.ejb.util;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author esinev
 * Date: 31.08.2006
 * Time: 12:04:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface FormAfterLoadInterceptor {
    Class[] value() ;
}
