package ru.ecom.ejb.services.entityform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Пометка класса на удаление вместо удаления записи из БД
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UnDeletable {
    String fieldName() default "isDeleted" ;
}
