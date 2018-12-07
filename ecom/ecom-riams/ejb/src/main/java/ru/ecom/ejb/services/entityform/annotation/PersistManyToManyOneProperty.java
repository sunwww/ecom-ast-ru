package ru.ecom.ejb.services.entityform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PersistManyToManyOneProperty {
	Class collectionGenericType() ;
    String valueProperty()  default "";
    String parentProperty() default "";
	String tableName() default "";
	//ListPersist.saveArrayJson("WorkFunctionService", id, frm.getVocWorkFunctions(),"medService_id","vocWorkFunction_id", aContext.getEntityManager()) ;
	//ListPersist.getArrayJson("WorkFunctionService", "medService_id", frm.getId(), "vocWorkFunction_id", manager)
}
