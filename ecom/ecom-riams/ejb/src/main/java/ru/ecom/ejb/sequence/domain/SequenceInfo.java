package ru.ecom.ejb.sequence.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Последовательность 
 */
@Entity @Comment("Последовательность")
@AIndexes({@AIndex(unique=true, properties="uniqueName")})
@Table(schema="SQLUser")
public class SequenceInfo extends BaseEntity {
	
	/** Последнее значение */
	@Comment("Последнее значение")
	public String getNextValue() {
		return theNextValue;
	}

	public void setNextValue(String aNextValue) {
		theNextValue = aNextValue;
	}

	
	/** Последнее значение */
	private String theNextValue;
	/** Уникальное название последовательности */
	@Comment("Уникальное название последовательности")
	public String getUniqueName() {
		return theUniqueName;
	}

	public void setUniqueName(String aUniqueName) {
		theUniqueName = aUniqueName;
	}

	/** Описание */
	@Comment("Описание")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Скрипт для получения следующего значения */
	@Comment("Скрипт для получения следующего значения")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getNextValueScript() {
		return theNextValueScript;
	}

	public void setNextValueScript(String aNextValueScript) {
		theNextValueScript = aNextValueScript;
	}

	/** Скрипт для получения следующего значения */
	private String theNextValueScript;
	/** Описание */
	private String theComment;
	/** Уникальное название последовательности */
	private String theUniqueName;
}
