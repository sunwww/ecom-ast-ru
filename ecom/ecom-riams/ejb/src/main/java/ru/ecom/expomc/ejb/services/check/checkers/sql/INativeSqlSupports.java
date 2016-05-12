package ru.ecom.expomc.ejb.services.check.checkers.sql;

/**
 * Проверка поддерживает несколько SQL-запросов для ускорения работы
 *
 */
public interface INativeSqlSupports extends ISqlSupports {

	String getNativeSql(NativeSqlContext aContext) ;
}
