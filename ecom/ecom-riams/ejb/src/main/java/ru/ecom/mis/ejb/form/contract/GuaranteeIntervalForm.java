package ru.ecom.mis.ejb.form.contract;

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
public class GuaranteeIntervalForm extends IdEntityForm{
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
	 * Маска выбора номеров
	 */
	@Comment("Маска выбора номеров")
	@Persist @DoUpperCase
	public String getNumberMask() {
		return theNumberMask;
	}
	public void setNumberMask(String aNumberMask) {
		theNumberMask = aNumberMask;
	}
	/**
	 * Маска выбора номеров
	 */
	private String theNumberMask;
	
	/**
	 * Начиная с номера
	 */
	@Comment("Начиная с номера")
	@Persist
	public String getFromNumber() {
		return theFromNumber;
	}
	public void setFromNumber(String aFromNumber) {
		theFromNumber = aFromNumber;
	}
	/**
	 * Начиная с номера
	 */
	private String theFromNumber;
	/**
	 * Заканчивая номером
	 */
	@Comment("Заканчивая номером")
	@Persist
	public String getToNumber() {
		return theToNumber;
	}
	public void setToNumber(String aToNumber) {
		theToNumber = aToNumber;
	}
	/**
	 * Заканчивая номером
	 */
	private String theToNumber;

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
	 * Серия
	 */
	@Comment("Серия")
	@Persist @DoUpperCase 
	public String getSeries() {
		return theSeries;
	}
	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}
	/**
	 * Серия
	 */
	private String theSeries;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@Persist
	public Long getServiceProgram() {
		return theServiceProgram;
	}
	public void setServiceProgram(Long aServiceProgram) {
		theServiceProgram = aServiceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private Long theServiceProgram;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@Persist
	public Long getServedPersonStatus() {
		return theServedPersonStatus;
	}
	public void setServedPersonStatus(Long aServedPersonStatus) {
		theServedPersonStatus = aServedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private Long theServedPersonStatus;
	
	/** Статус обслуживаемой персоны (инфо) */
	@Comment("Статус обслуживаемой персоны (инфо)")
	@Persist
	public String getServedPersonStatusInfo() {
		return theServedPersonStatusInfo;
	}

	public void setServedPersonStatusInfo(String aServedPersonStatusInfo) {
		theServedPersonStatusInfo = aServedPersonStatusInfo;
	}

	/** Статус обслуживаемой персоны (инфо) */
	private String theServedPersonStatusInfo;
	
	/** Программа обслуживания (инфо) */
	@Comment("Программа обслуживания (инфо)")
	@Persist
	public String getServiceProgramInfo() {
		return theServiceProgramInfo;
	}

	public void setServiceProgramInfo(String aServiceProgramInfo) {
		theServiceProgramInfo = aServiceProgramInfo;
	}

	/** Программа обслуживания (инфо) */
	private String theServiceProgramInfo;
	
}
