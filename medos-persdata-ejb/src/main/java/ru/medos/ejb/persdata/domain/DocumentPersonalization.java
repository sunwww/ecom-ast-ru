package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.medos.ejb.persdata.domain.ExternalCarrier;
import ru.medos.ejb.persdata.domain.voc.VocPersonalizationDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Персонализация документа
	 */
	@Comment("Персонализация документа")
@Entity
@Table(schema="SQLUser")
public class DocumentPersonalization extends BaseEntity{
	/**
	 * Тип
	 */
	@Comment("Тип")
	@OneToOne
	public VocPersonalizationDocument getType() {
		return theType;
	}
	public void setType(VocPersonalizationDocument aType) {
		theType = aType;
	}
	/**
	 * Тип
	 */
	private VocPersonalizationDocument theType;
	/**
	 * Внешний носитель
	 */
	@Comment("Внешний носитель")
	@OneToOne
	public ExternalCarrier getExternalCarrier() {
		return theExternalCarrier;
	}
	public void setExternalCarrier(ExternalCarrier aExternalCarrier) {
		theExternalCarrier = aExternalCarrier;
	}
	/**
	 * Внешний носитель
	 */
	private ExternalCarrier theExternalCarrier;
	/**
	 * ФИО заказчика
	 */
	@Comment("ФИО заказчика")
	
	public String getCustomerFullname() {
		return theCustomerFullname;
	}
	public void setCustomerFullname(String aCustomerFullname) {
		theCustomerFullname = aCustomerFullname;
	}
	/**
	 * ФИО заказчика
	 */
	private String theCustomerFullname;
	/**
	 * Должность заказчика
	 */
	@Comment("Должность заказчика")
	
	public String getCustomerPost() {
		return theCustomerPost;
	}
	public void setCustomerPost(String aCustomerPost) {
		theCustomerPost = aCustomerPost;
	}
	/**
	 * Должность заказчика
	 */
	private String theCustomerPost;
}
