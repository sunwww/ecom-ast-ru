package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.MedServiceInterval;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;

@EntityForm
@EntityFormPersistance(clazz = MedServiceInterval.class)
@Comment("Интервалы")
@WebTrail(comment = "Интервалы", nameProperties= "id", list="entityParentList-contract_medServiceInterval.do", view="entityParentView-contract_medserviceInterval.do")
@Parent(property="medServiceGroup", parentForm=ContractMedServiceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/MedServiceInterval")
@Setter
public class MedServiceIntervalForm extends IdEntityForm{
	/**
	 * Маска кодов мед. услуг
	 */
	@Comment("Маска кодов мед. услуг")
	@Persist @DoUpperCase
	public String getMedServiceMask() {
		return medServiceMask;
	}
	/**
	 * Маска кодов мед. услуг
	 */
	private String medServiceMask;
	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@Persist
	public Long getFromCode() {
		return fromCode;
	}
	/**
	 * Начиная с кода
	 */
	private Long fromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@Persist
	public Long getToCode() {
		return toCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private Long toCode;
	/**
	 * Группа медицинских услуг
	 */
	@Comment("Группа медицинских услуг")
	@Persist
	public Long getMedServiceGroup() {
		return medServiceGroup;
	}
	/**
	 * Группа медицинских услуг
	 */
	private Long medServiceGroup;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;

	/**
	 * Начиная с кода медицинской услуги
	 */
	@Comment("Начиная с кода медицинской услуги")
	@Persist
	public String getFromMedServiceCode() {
		return fromMedServiceCode;
	}
	/**
	 * Начиная с кода медицинской услуги
	 */
	private String fromMedServiceCode;
	/**
	 * Заканчивая кодом медицинской услуги
	 */
	@Comment("Заканчивая кодом медицинской услуги")
	@Persist
	public String getToMedServiceCode() {
		return toMedServiceCode;
	}
	/**
	 * Заканчивая кодом медицинской услуги
	 */
	private String toMedServiceCode;
}
