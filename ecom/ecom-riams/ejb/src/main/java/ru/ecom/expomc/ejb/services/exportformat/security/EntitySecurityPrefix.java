/**
 * Права доступа к Hibernate's Entity
 *
 * @author ikouzmin 06.03.2007 12:53:33
 */
package ru.ecom.expomc.ejb.services.exportformat.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntitySecurityPrefix {
    String value() ;
}
