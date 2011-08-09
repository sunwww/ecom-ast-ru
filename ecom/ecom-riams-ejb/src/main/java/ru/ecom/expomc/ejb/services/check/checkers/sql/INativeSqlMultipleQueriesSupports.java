package ru.ecom.expomc.ejb.services.check.checkers.sql;

import java.util.Collection;

/**
 * Проверка поддерживает несколько SQL-запросов для ускорения работы
 *
 */
public interface INativeSqlMultipleQueriesSupports extends ISqlSupports {

	Collection<String> getNativeSql(NativeSqlContext aContext) ;
}
