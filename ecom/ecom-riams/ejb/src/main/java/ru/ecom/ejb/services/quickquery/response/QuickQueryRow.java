package ru.ecom.ejb.services.quickquery.response;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class QuickQueryRow implements Serializable {

	/** Данные */
	public Collection<Object> getCells() {
		return theCells;
	}


	/** Данные */
	private final Collection<Object> theCells = new LinkedList<Object>();
}
