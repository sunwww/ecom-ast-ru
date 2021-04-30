package ru.ecom.ejb.sequence.domain;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class SequenceInfo extends BaseEntity {

	
	/** Последнее значение */
	private String nextValue;

	/** Скрипт для получения следующего значения */
	@Comment("Скрипт для получения следующего значения")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getNextValueScript() {
		return nextValueScript;
	}

	/** Скрипт для получения следующего значения */
	private String nextValueScript;
	/** Описание */
	private String comment;
	/** Уникальное название последовательности */
	private String uniqueName;
}
