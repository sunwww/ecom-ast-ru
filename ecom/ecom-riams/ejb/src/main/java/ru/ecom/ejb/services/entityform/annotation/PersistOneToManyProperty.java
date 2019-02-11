package ru.ecom.ejb.services.entityform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PersistOneToManyProperty {
	Class collectionGenericType() ;
	Class spanTable() ;
    String collectionProperty()  default "";
    String parentProperty() default "";
}
