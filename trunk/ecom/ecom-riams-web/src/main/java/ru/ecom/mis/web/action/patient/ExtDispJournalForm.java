package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class ExtDispJournalForm extends BaseValidatorForm {

	/** Дата начала периода */
	@Comment("Дата начала периода")
	@DateString @DoDateString @Required
	public String getBeginDate() {return theBeginDate;}
	public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@DateString @DoDateString @Required
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** Дата окончания периода */
	private String theFinishDate;
	/** Дата начала периода */
	private String theBeginDate;
	
	/** Социальный статус */
	@Comment("Социальный статус")
	public Long getSocialStatus() {return theSocialStatus;}
	public void setSocialStatus(Long aSocialStatus) {theSocialStatus = aSocialStatus;}

	/** Социальный статус */
	private Long theSocialStatus;
	
	/** Тип диспасеризации */
	@Comment("Тип диспасеризации")
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}

	/** Тип диспасеризации */
	private Long theDispType;
	
	/** Возрасная категория */
	@Comment("Возрасная категория")
	public Long getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(Long aAgeGroup) {theAgeGroup = aAgeGroup;}

	/** Возрасная категория */
	private Long theAgeGroup;
	
	/** Группа здоровья */
	@Comment("Группа здоровья")
	public Long getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(Long aHealthGroup) {theHealthGroup = aHealthGroup;}

	/** Фактор риска */
	@Comment("Фактор риска")
	public Long getRisk() {return theRisk;}
	public void setRisk(Long aRisk) {theRisk = aRisk;}

	/** Фактор риска */
	private Long theRisk;
	/** Группа здоровья */
	private Long theHealthGroup;
}
