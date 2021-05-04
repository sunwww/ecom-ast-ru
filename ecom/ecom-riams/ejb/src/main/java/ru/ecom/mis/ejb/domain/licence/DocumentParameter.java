package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

	/**
	 * Параметр документа
	 */
	@Comment("Параметр документа")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "document" }) })
	@Getter
	@Setter
public class DocumentParameter extends BaseEntity{
	/**
	 * Параметр
	 */
	@Comment("Параметр")
	@OneToOne
	public VocDocumentParameter getParameter() {
		return parameter;
	}
	/**
	 * Параметр
	 */
	private VocDocumentParameter parameter;
	/**
	 * Значение
	 */
	private String value;
	/**
	 * Дата получения значения
	 */
	private Date valueDate;
	/**
	 * Время получения значения
	 */
	private Time valueTime;
	/**
	 * Документ
	 */
	@Comment("Документ")
	@OneToOne
	public ExternalDocument getDocument() {
		return document;
	}
	/**
	 * Документ
	 */
	private ExternalDocument document;
	
	/** Порядковый номер */
	private int orderNumber;
}
