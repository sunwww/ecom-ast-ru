package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.MedServiceInterval;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = MedServiceInterval.class)
@Comment("Интервалы")
@WebTrail(comment = "Интервалы", nameProperties= "id", list="entityParentList-contract_medServiceInterval.do", view="entityParentView-contract_medserviceInterval.do")
@Parent(property="medServiceGroup", parentForm=ContractMedServiceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/MedServiceInterval")
public class MedServiceIntervalForm extends IdEntityForm{
	/**
	 * Маска кодов мед. услуг
	 */
	@Comment("Маска кодов мед. услуг")
	@Persist @DoUpperCase
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
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@Persist
	public Long getFromCode() {
		return theFromCode;
	}
	public void setFromCode(Long aFromCode) {
		theFromCode = aFromCode;
	}
	/**
	 * Начиная с кода
	 */
	private Long theFromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@Persist
	public Long getToCode() {
		return theToCode;
	}
	public void setToCode(Long aToCode) {
		theToCode = aToCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private Long theToCode;
	/**
	 * Группа медицинских услуг
	 */
	@Comment("Группа медицинских услуг")
	@Persist
	public Long getMedServiceGroup() {
		return theMedServiceGroup;
	}
	public void setMedServiceGroup(Long aMedServiceGroup) {
		theMedServiceGroup = aMedServiceGroup;
	}
	/**
	 * Группа медицинских услуг
	 */
	private Long theMedServiceGroup;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
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
	@Persist
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
	@Persist
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
}
