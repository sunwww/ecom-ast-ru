package ru.ecom.ejb.services.quickquery.config;

import java.util.Collection;
import java.util.LinkedList;

public class ConfigQuickQuery {

	/** Добавление ограничения */
	public ConfigWhereClause getWhereClauseConfig() {
		return theWhereClause;
	}

	/** Название */
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Запросы */
	public Collection<ConfigQuery> getQueries() {
		return theQueries;
	}


	/** Запросы */
	private final Collection<ConfigQuery> theQueries = new LinkedList<ConfigQuery>();
	/** Название */
	private String theName;
	/** Добавление ограничения */
	private final ConfigWhereClause theWhereClause = new ConfigWhereClause();
}
