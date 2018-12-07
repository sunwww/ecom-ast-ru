package ru.ecom.ejb.services.entityform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Условие для разворота
 * <pre>
 *	@RowPersistProperty( 
 *		mathes = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="0")
 *				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
 *	)
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RowPersistMatch {

	/** Куда сохранять */
	String property() ;
	/** По какому свойству искать */
	String matchProperty() ;
	/** Значения, которое должно совпасть */
	String matchValue() ;
}
