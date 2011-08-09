package ru.ecom.expomc.ejb.uc.iterate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Перебор
 */
@Entity
@Comment("Перебор")
@Table(schema="SQLUser")
public class Iterate extends BaseEntity {

	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Строка запроса */
	@Comment("Строка запроса")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getHqlString() {
		return theHqlString;
	}

	public void setHqlString(String aHqlString) {
		theHqlString = aHqlString;
	}

	/** Скрипт для выполнения */
	@Comment("Скрипт для выполнения")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getIterateScript() {
		return theIterateScript;
	}

	public void setIterateScript(String aIterateScript) {
		theIterateScript = aIterateScript;
	}

	/** Инициализация */
	@Comment("Инициализация")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getInitScript() {
		return theInitScript;
	}

	public void setInitScript(String aInitScript) {
		theInitScript = aInitScript;
	}

	/** Инициализация */
	private String theInitScript;
	/** Скрипт для выполнения */
	private String theIterateScript;
	/** Строка запроса */
	private String theHqlString;
	/** Название */
	private String theName;
}
