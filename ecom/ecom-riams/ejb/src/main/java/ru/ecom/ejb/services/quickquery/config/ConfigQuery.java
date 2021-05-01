package ru.ecom.ejb.services.quickquery.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigQuery {

	/** Название */
	private String name;

	/** Запрос */
	private String sql;
}
