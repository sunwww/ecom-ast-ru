package ru.ecom.ejb.services.entityform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Разворот простыни в строки
 * <pre>
 * @RowPersist(
 *		rowEntityClass = MortalityReportRow.class
 *	 ,  parentProperty = "mortalityReportDate"
 *	 , defaultProperty = "amount"	  
 * )
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RowPersist {
	/** Куда сохранять строку */
	Class rowEntityClass() ;
	/** Свойство для связки */
	String parentProperty() ;
	/** Свойство, по-умолчанию, куда будут писаться значения*/
	String defaultProperty() ;
}
