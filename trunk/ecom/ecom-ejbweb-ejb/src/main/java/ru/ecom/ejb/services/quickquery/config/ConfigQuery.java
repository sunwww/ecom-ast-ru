package ru.ecom.ejb.services.quickquery.config;

import ru.ecom.ejb.services.quickquery.IWhereClause;

public class ConfigQuery {
	
	
	/** Название */
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	/** Запрос */
	public String getSql() {
		return theSql;
	}

	public void setSql(String aSql) {
		theSql = aSql;
	}

	/** Запрос */
	private String theSql;
}
