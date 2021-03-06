package ru.ecom.mis.ejb.domain.licence;

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
public class DocumentParameter extends BaseEntity{
	/**
	 * Параметр
	 */
	@Comment("Параметр")
	@OneToOne
	public VocDocumentParameter getParameter() {
		return theParameter;
	}
	public void setParameter(VocDocumentParameter aParameter) {
		theParameter = aParameter;
	}
	/**
	 * Параметр
	 */
	private VocDocumentParameter theParameter;
	/**
	 * Значение
	 */
	@Comment("Значение")
	
	public String getValue() {
		return theValue;
	}
	public void setValue(String aValue) {
		theValue = aValue;
	}
	/**
	 * Значение
	 */
	private String theValue;
	/**
	 * Дата получения значения
	 */
	@Comment("Дата получения значения")
	
	public Date getValueDate() {
		return theValueDate;
	}
	public void setValueDate(Date aValueDate) {
		theValueDate = aValueDate;
	}
	/**
	 * Дата получения значения
	 */
	private Date theValueDate;
	/**
	 * Время получения значения
	 */
	@Comment("Время получения значения")
	
	public Time getValueTime() {
		return theValueTime;
	}
	public void setValueTime(Time aValueTime) {
		theValueTime = aValueTime;
	}
	/**
	 * Время получения значения
	 */
	private Time theValueTime;
	/**
	 * Документ
	 */
	@Comment("Документ")
	@OneToOne
	public ExternalDocument getDocument() {
		return theDocument;
	}
	public void setDocument(ExternalDocument aDocument) {
		theDocument = aDocument;
	}
	/**
	 * Документ
	 */
	private ExternalDocument theDocument;
	
	/** Порядковый номер */
	@Comment("Порядковый номер")
	public int getOrderNumber() {
		return theOrderNumber;
	}

	public void setOrderNumber(int aOrderNumber) {
		theOrderNumber = aOrderNumber;
	}

	/** Порядковый номер */
	private int theOrderNumber;
}
