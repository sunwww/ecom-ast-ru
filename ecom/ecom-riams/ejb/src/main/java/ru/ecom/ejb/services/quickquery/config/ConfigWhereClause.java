package ru.ecom.ejb.services.quickquery.config;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.quickquery.IWhereClause;

@Getter
@Setter
public class ConfigWhereClause {

	/** Ограничени */
	private IWhereClause whereClause;
	
	/** Ключ */
	private String key;
}
