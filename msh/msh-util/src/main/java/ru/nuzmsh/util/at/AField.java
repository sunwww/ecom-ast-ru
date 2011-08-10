package ru.nuzmsh.util.at;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author ESinev
 *         Date: 22.11.2005
 *         Time: 15:53:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface AField {
    Class at() ;
}
