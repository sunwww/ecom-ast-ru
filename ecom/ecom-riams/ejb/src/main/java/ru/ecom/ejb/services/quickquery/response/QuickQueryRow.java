package ru.ecom.ejb.services.quickquery.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

@Getter
public class QuickQueryRow implements Serializable {

	/** Данные */
	private final Collection<Object> cells = new LinkedList<Object>();
}
