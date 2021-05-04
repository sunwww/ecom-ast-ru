package ru.ecom.mis.ejb.domain.licence.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Справочник параметров документа
	 */
	@Comment("Справочник параметров документа")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "parameterGroup" }) })
	@Getter
	@Setter
public class VocDocumentParameter extends VocBaseEntity{
	/**
	 * Группа параметров
	 */
	@Comment("Группа параметров")
	@OneToOne
	public VocDocumentParameterGroup getParameterGroup() {
		return parameterGroup;
	}
	/**
	 * Группа параметров
	 */
	private VocDocumentParameterGroup parameterGroup;
	/**
	 * Минимум нормы
	 */
	private String normMinimum;
	/**
	 * Максимум нормы
	 */
	private String normMaximum;
	/**
	 * Размерность
	 */
	private String dimension;
	/**
	 * Норма
	 */
	@Comment("Норма")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getNorm() {
		return norm;
	}
	/**
	 * Норма
	 */
	private String norm;
}
