package ru.ecom.ejb.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

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
