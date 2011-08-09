package ru.ecom.ejb.services.quickquery.config;

import ru.ecom.ejb.services.quickquery.IWhereClause;

public class ConfigWhereClause {

	/** Ключ */
	public String getKey() {
		return theKey;
	}

	public void setKey(String aKey) {
		theKey = aKey;
	}

	/** Ограничени */
	public IWhereClause getWhereClause() {
		return theWhereClause;
	}

	public void setWhereClause(IWhereClause aWhereClause) {
		theWhereClause = aWhereClause;
	}

	/** Ограничени */
	private IWhereClause theWhereClause;
	
	/** Ключ */
	private String theKey;
}
