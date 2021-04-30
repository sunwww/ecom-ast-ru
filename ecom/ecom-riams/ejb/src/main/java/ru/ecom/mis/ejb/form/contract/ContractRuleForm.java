package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
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
@Setter
public class ContractRuleForm extends IdEntityForm{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private Long contract;
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@Persist
	public Long getNosologyGroup() {
		return nosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private Long nosologyGroup;
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
	 * Группа гарантийных документов
	 */
	@Comment("Группа гарантийных документов")
	@Persist
	public Long getGuaranteeGroup() {
		return guaranteeGroup;
	}
	/**
	 * Группа гарантийных документов
	 */
	private Long guaranteeGroup;
	/**
	 * Период действия
	 */
	@Comment("Период действия")
	@Persist
	public Long getPeriod() {
		return period;
	}
	/**
	 * Период действия
	 */
	private Long period;
	/**
	 * Количество медицинских услуг
	 */
	@Comment("Количество медицинских услуг")
	@Persist
	public Integer getMedserviceAmount() {
		return medserviceAmount;
	}
	/**
	 * Количество медицинских услуг
	 */
	private Integer medserviceAmount;
	/**
	 * Количество курсов
	 */
	@Comment("Количество курсов")
	@Persist
	public Integer getCourseAmount() {
		return courseAmount;
	}
	/**
	 * Количество курсов
	 */
	private Integer courseAmount;
	/**
	 * Количество медицинских услуг на курс
	 */
	@Comment("Количество медицинских услуг на курс")
	@Persist
	public Integer getMedserviceCourseAmount() {
		return medserviceCourseAmount;
	}
	/**
	 * Количество медицинских услуг на курс
	 */
	private Integer medserviceCourseAmount;
	/**
	 * Разрешение
	 */
	@Comment("Разрешение")
	@Persist @Required
	public Long getPermission() {
		return permission;
	}
	/**
	 * Разрешение
	 */
	private Long permission;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String dateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String dateTo;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {
		return servedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private Long servedPerson;
	
	/** Название правила */
	@Comment("Название правила")
	@Persist @Required @DoUpperCase
	public String getName() {return name;}

	/** Название правила */
	private String name;
	
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	public Long getPerson() {return person;}
	/**
	 * Договорная персона
	 */
	private Long person;

}
