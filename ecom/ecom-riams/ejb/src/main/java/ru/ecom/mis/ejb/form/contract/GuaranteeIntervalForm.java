package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.GuaranteeInterval;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = GuaranteeInterval.class)
@Comment("Интервал гарантийных документов")
@WebTrail(comment = "Интервал гарантийных документов", nameProperties= "id", list="entityParentList-contract_guaranteeInterval.do", view="entityParentView-contract_guaranteeInterval.do")
@Parent(property="guaranteeGroup", parentForm=ContractGuaranteeGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/GuaranteeInterval")
@Setter
public class GuaranteeIntervalForm extends IdEntityForm{
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
	 * Маска выбора номеров
	 */
	@Comment("Маска выбора номеров")
	@Persist @DoUpperCase
	public String getNumberMask() {
		return numberMask;
	}
	/**
	 * Маска выбора номеров
	 */
	private String numberMask;
	
	/**
	 * Начиная с номера
	 */
	@Comment("Начиная с номера")
	@Persist
	public String getFromNumber() {
		return fromNumber;
	}
	/**
	 * Начиная с номера
	 */
	private String fromNumber;
	/**
	 * Заканчивая номером
	 */
	@Comment("Заканчивая номером")
	@Persist
	public String getToNumber() {
		return toNumber;
	}
	/**
	 * Заканчивая номером
	 */
	private String toNumber;

	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;
	/**
	 * Серия
	 */
	@Comment("Серия")
	@Persist @DoUpperCase 
	public String getSeries() {
		return series;
	}
	/**
	 * Серия
	 */
	private String series;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@Persist
	public Long getServiceProgram() {
		return serviceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private Long serviceProgram;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@Persist
	public Long getServedPersonStatus() {
		return servedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private Long servedPersonStatus;
	
	/** Статус обслуживаемой персоны (инфо) */
	@Comment("Статус обслуживаемой персоны (инфо)")
	@Persist
	public String getServedPersonStatusInfo() {
		return servedPersonStatusInfo;
	}


	/** Статус обслуживаемой персоны (инфо) */
	private String servedPersonStatusInfo;
	
	/** Программа обслуживания (инфо) */
	@Comment("Программа обслуживания (инфо)")
	@Persist
	public String getServiceProgramInfo() {
		return serviceProgramInfo;
	}


	/** Программа обслуживания (инфо) */
	private String serviceProgramInfo;
	
}
