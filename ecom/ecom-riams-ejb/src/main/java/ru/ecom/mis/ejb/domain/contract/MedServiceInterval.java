package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.ContractMedServiceGroup;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.validators.Required;

	/**
	 * Интервал медицинских услуг (A01.01.001-A01.01.005, A01.001.
	 */
	@Comment("Интервал медицинских услуг (A01.01.001-A01.01.005, A01.001.")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(unique= false, properties = {"name"})
	})
public class MedServiceInterval extends BaseEntity{
	/**
	 * Маска кодов мед. услуг
	 */
	@Comment("Маска кодов мед. услуг")
	@Persist @Required
	public String getMedServiceMask() {
		return theMedServiceMask;
	}
	public void setMedServiceMask(String aMedServiceMask) {
		theMedServiceMask = aMedServiceMask;
	}
	/**
	 * Маска кодов мед. услуг
	 */
	private String theMedServiceMask;
	/**
	 * Группа медицинских услуг
	 */
	@Comment("Группа медицинских услуг")
	@ManyToOne
	public ContractMedServiceGroup getMedServiceGroup() {
		return theMedServiceGroup;
	}
	public void setMedServiceGroup(ContractMedServiceGroup aMedServiceGroup) {
		theMedServiceGroup = aMedServiceGroup;
	}
	/**
	 * Группа медицинских услуг
	 */
	private ContractMedServiceGroup theMedServiceGroup;
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
	/**
	 * Начиная с кода медицинской услуги
	 */
	@Comment("Начиная с кода медицинской услуги")
	
	public String getFromMedServiceCode() {
		return theFromMedServiceCode;
	}
	public void setFromMedServiceCode(String aFromMedServiceCode) {
		theFromMedServiceCode = aFromMedServiceCode;
	}
	/**
	 * Начиная с кода медицинской услуги
	 */
	private String theFromMedServiceCode;
	/**
	 * Заканчивая кодом медицинской услуги
	 */
	@Comment("Заканчивая кодом медицинской услуги")
	
	public String getToMedServiceCode() {
		return theToMedServiceCode;
	}
	public void setToMedServiceCode(String aToMedServiceCode) {
		theToMedServiceCode = aToMedServiceCode;
	}
	/**
	 * Заканчивая кодом медицинской услуги
	 */
	private String theToMedServiceCode;

	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@OneToOne
	public MedService getFromCode() {
		return theFromCode;
	}
	public void setFromCode(MedService aFromCode) {
		theFromCode = aFromCode;
	}
	/**
	 * Начиная с кода
	 */
	private MedService theFromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@OneToOne
	public MedService getToCode() {
		return theToCode;
	}
	public void setToCode(MedService aToCode) {
		theToCode = aToCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private MedService theToCode;	
}
