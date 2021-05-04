package ru.ecom.ejb.services.quickquery.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

@Getter
@Setter
public class QuickQueryResponse implements Serializable {


	/** Название */
	private String name;
	/** Запросы */
	private final Collection<QuickQuery> queries = new LinkedList<QuickQuery>();
}
