package ru.ecom.ejb.services.quickquery.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

@Getter
@Setter
public class QuickQuery implements Serializable{
	


	/** Строки */
	private final LinkedList<QuickQueryRow> rows = new LinkedList<QuickQueryRow>();
	private final LinkedList<String> headers = new LinkedList<String>() ; 
	/** Название */
	private String name;

}
