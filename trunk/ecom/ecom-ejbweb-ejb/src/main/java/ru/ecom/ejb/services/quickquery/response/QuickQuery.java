package ru.ecom.ejb.services.quickquery.response;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class QuickQuery implements Serializable{
	
	/** Название */
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	public Collection<String> getHeaders() {
		return theHeaders ;
	}
	
	/** Строки */
	public LinkedList<QuickQueryRow> getRows() {
		return theRows;
	}

	/** Строки */
	private final LinkedList<QuickQueryRow> theRows = new LinkedList<QuickQueryRow>();
	private final LinkedList<String> theHeaders = new LinkedList<String>() ; 
	/** Название */
	private String theName;

}
