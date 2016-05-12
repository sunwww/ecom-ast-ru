package ru.ecom.mis.web.action.psych;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * 
 * @author stkacheva
 *
 */
public class AreaReportForm extends BaseValidatorForm {
	/** Дата начала периода */
	@Comment("Дата начала периода") 
	@Required @DoDateString @DateString
	public String getDateBegin() {return theDateBegin;}
	public void setDateBegin(String aDateBegin) {theDateBegin = aDateBegin;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	 @DoDateString @DateString
	public String getDateEnd() {return theDateEnd;}
	public void setDateEnd(String aDateEnd) {theDateEnd = aDateEnd;}
	
	/** Причина снятия */
	@Comment("Причина снятия")
	public Long getReasonEnd() {return theReasonEnd;}
	public void setReasonEnd(Long aReasonEnd) {theReasonEnd = aReasonEnd;}

	/** Причина взятия */
	@Comment("Причина взятия")
	public Long getReasonBegin() {
		return theReasonBegin;
	}

	public void setReasonBegin(Long aReasonBegin) {
		theReasonBegin = aReasonBegin;
	}

	/** Причина взятия */
	private Long theReasonBegin;
	/** Причина снятия */
	private Long theReasonEnd;

	/** Искать по дате выписке? */
	@Comment("Искать по дате выписке?")
	public Boolean getDischargeIs() {return theDischargeIs;}
	public void setDischargeIs(Boolean aDischargeIs) {theDischargeIs = aDischargeIs;}

	
	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergancyIs() {return theEmergancyIs;}
	public void setEmergancyIs(Boolean aEmergancyIs) {theEmergancyIs = aEmergancyIs;}

	/** Время */
	@Comment("Время")
	@DoTimeString @TimeString
	public String getTimeBegin() {return theTimeBegin;}
	public void setTimeBegin(String aTimeBegin) {theTimeBegin = aTimeBegin;}

	/** Вид наблюдения */
	@Comment("Вид наблюдения")
	@Required
	public Long getAmbulatoryCare() {return theAmbulatoryCare;}
	public void setAmbulatoryCare(Long aAmbulatoryCare) {theAmbulatoryCare = aAmbulatoryCare;}

	/** Группа наблюдений */
	@Comment("Группа наблюдений")
	public Long getGroup() {return theGroup;}
	public void setGroup(Long aGroup) {theGroup = aGroup;}

	/** Группа инвалидности */
	@Comment("Группа инвалидности")
	public Long getGroupInv() {return theGroupInv;}
	public void setGroupInv(Long aGroupInv) {theGroupInv = aGroupInv;}

	/** Принудительное лечение */
	@Comment("Принудительное лечение")
	public Long getCompTreatment() {return theCompTreatment;}
	public void setCompTreatment(Long aCompTreatment) {theCompTreatment = aCompTreatment;}

	/** Принудительное лечение */
	private Long theCompTreatment;
	/** Группа инвалидности */
	private Long theGroupInv;
	/** Группа наблюдений */
	private Long theGroup;
	/** Вид наблюдения */
	private Long theAmbulatoryCare;
	/** Время */
	private String theTimeBegin;
	/** Экстренность */
	private Boolean theEmergancyIs;
	/** Отделение */
	private Long theDepartment;
	/** Искать по дате выписке? */
	private Boolean theDischargeIs;
	/** Дата окончания периода */
	private String theDateEnd;
	/** Дата начала периода */
	private String theDateBegin;
	
	/** Участок */
	@Comment("Участок")
	@Required
	public Long getLpuArea() {return theLpuArea;}
	public void setLpuArea(Long aLpuArea) {theLpuArea = aLpuArea;}

	/** Причина перевода */
	@Comment("Причина перевода")
	public Long getReasonTransfer() {return theReasonTransfer;}
	public void setReasonTransfer(Long aReasonTransfer) {theReasonTransfer = aReasonTransfer;}

	/** Пол */
	@Comment("Пол")
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}

	/** Возраст с */
	@Comment("Возраст с")
	public Integer getAgeFrom() {return theAgeFrom;}
	public void setAgeFrom(Integer aAgeFrom) {theAgeFrom = aAgeFrom;}

	/** Возраст до */
	@Comment("Возраст до")
	public Integer getAgeTo() {
		return theAgeTo;
	}

	public void setAgeTo(Integer aAgeTo) {
		theAgeTo = aAgeTo;
	}
	/** Характер суицида */
	@Comment("Характер суицида")
	public Long getNatureSuicide() {
		return theNatureSuicide;
	}

	public void setNatureSuicide(Long aNatureSuicide) {
		theNatureSuicide = aNatureSuicide;
	}

	/** Характер суицида */
	private Long theNatureSuicide;
	/** Возраст до */
	private Integer theAgeTo;
	/** Возраст */
	private Integer theAgeFrom;
	/** Пол */
	private Long theSex;
	/** Причина перевода */
	private Long theReasonTransfer;
	/** Участок */
	private Long theLpuArea;
}
