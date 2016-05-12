package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractRule;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ContractRule.class)
@Comment("Договорное правило")
@WebTrail(comment = "Договорное правило", nameProperties= {"id","dateFrom","dateTo"}, list="entityParentList-contract_rule.do", view="entityParentView-contract_rule.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractRule")
public class ContractRuleForm extends IdEntityForm{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return theContract;
	}
	public void setContract(Long aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private Long theContract;
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@Persist
	public Long getNosologyGroup() {
		return theNosologyGroup;
	}
	public void setNosologyGroup(Long aNosologyGroup) {
		theNosologyGroup = aNosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private Long theNosologyGroup;
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
	 * Группа гарантийных документов
	 */
	@Comment("Группа гарантийных документов")
	@Persist
	public Long getGuaranteeGroup() {
		return theGuaranteeGroup;
	}
	public void setGuaranteeGroup(Long aGuaranteeGroup) {
		theGuaranteeGroup = aGuaranteeGroup;
	}
	/**
	 * Группа гарантийных документов
	 */
	private Long theGuaranteeGroup;
	/**
	 * Период действия
	 */
	@Comment("Период действия")
	@Persist
	public Long getPeriod() {
		return thePeriod;
	}
	public void setPeriod(Long aPeriod) {
		thePeriod = aPeriod;
	}
	/**
	 * Период действия
	 */
	private Long thePeriod;
	/**
	 * Количество медицинских услуг
	 */
	@Comment("Количество медицинских услуг")
	@Persist
	public Integer getMedserviceAmount() {
		return theMedserviceAmount;
	}
	public void setMedserviceAmount(Integer aMedserviceAmount) {
		theMedserviceAmount = aMedserviceAmount;
	}
	/**
	 * Количество медицинских услуг
	 */
	private Integer theMedserviceAmount;
	/**
	 * Количество курсов
	 */
	@Comment("Количество курсов")
	@Persist
	public Integer getCourseAmount() {
		return theCourseAmount;
	}
	public void setCourseAmount(Integer aCourseAmount) {
		theCourseAmount = aCourseAmount;
	}
	/**
	 * Количество курсов
	 */
	private Integer theCourseAmount;
	/**
	 * Количество медицинских услуг на курс
	 */
	@Comment("Количество медицинских услуг на курс")
	@Persist
	public Integer getMedserviceCourseAmount() {
		return theMedserviceCourseAmount;
	}
	public void setMedserviceCourseAmount(Integer aMedserviceCourseAmount) {
		theMedserviceCourseAmount = aMedserviceCourseAmount;
	}
	/**
	 * Количество медицинских услуг на курс
	 */
	private Integer theMedserviceCourseAmount;
	/**
	 * Разрешение
	 */
	@Comment("Разрешение")
	@Persist @Required
	public Long getPermission() {
		return thePermission;
	}
	public void setPermission(Long aPermission) {
		thePermission = aPermission;
	}
	/**
	 * Разрешение
	 */
	private Long thePermission;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String theDateTo;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {
		return theServedPerson;
	}
	public void setServedPerson(Long aServedPerson) {
		theServedPerson = aServedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private Long theServedPerson;
	
	/** Название правила */
	@Comment("Название правила")
	@Persist @Required @DoUpperCase
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Название правила */
	private String theName;
	
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	public Long getPerson() {return thePerson;}
	public void setPerson(Long aPerson) {thePerson = aPerson;}
	/**
	 * Договорная персона
	 */
	private Long thePerson;

}
