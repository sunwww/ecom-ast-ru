package ru.ecom.ejb.services.quickquery.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.LinkedList;

@Getter
@Setter
public class ConfigQuickQuery {

	/** Добавление ограничения */
	public ConfigWhereClause getWhereClauseConfig() {
		return whereClause;
	}

	/** Запросы */
	private final Collection<ConfigQuery> queries = new LinkedList<ConfigQuery>();
	/** Название */
	private String name;
	/** Добавление ограничения */
	private final ConfigWhereClause whereClause = new ConfigWhereClause();
}
