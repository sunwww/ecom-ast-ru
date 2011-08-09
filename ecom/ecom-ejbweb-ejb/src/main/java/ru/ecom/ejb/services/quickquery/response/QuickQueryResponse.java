package ru.ecom.ejb.services.quickquery.response;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class QuickQueryResponse implements Serializable {

	/** Название */
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	/** Запросы */
	public Collection<QuickQuery> getQueries() {
		return theQueries;
	}


	/** Запросы */
	private final Collection<QuickQuery> theQueries = new LinkedList<QuickQuery>();
}
