package ru.ecom.mis.web.action.medcase.journal;

import javax.persistence.UniqueConstraint;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;
/**
 * Журнал обращений
 * @author stkacheva
 */
public class AdmissionJournalForm extends BaseValidatorForm{
	/** Дата начала периода */
	@Comment("Дата начала периода") 
	@Required @DateString @DoDateString
	public String getDateBegin() {return theDateBegin;}
	public void setDateBegin(String aDateBegin) {theDateBegin = aDateBegin;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@DateString @DoDateString
	public String getDateEnd() {return theDateEnd;}
	public void setDateEnd(String aDateEnd) {theDateEnd = aDateEnd;}

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

	
	/** 8 часов */
	@Comment("8 часов")
	public Boolean getHour8() {
		return theHour8;
	}

	public void setHour8(Boolean aHour8Is) {
		theHour8 = aHour8Is;
	}

	/** 8 часов */
	private Boolean theHour8;
	/** Время */
	@Comment("Время")
	@DoTimeString @TimeString
	public String getTimeBegin() {return theTimeBegin;}
	public void setTimeBegin(String aTimeBegin) {theTimeBegin = aTimeBegin;}

	/** Тип стациионара */
	@Comment("Тип стациионара")
	public Long getHospType() {
		return theHospType;
	}

	public void setHospType(Long aHospType) {
		theHospType = aHospType;
	}
	
	/** Специалист */
	@Comment("Специалист")
	public Long getSpec() {
		return theSpec;
	}

	public void setSpec(Long aSpec) {
		theSpec = aSpec;
	}

	/** Специалист */
	private Long theSpec;

	/** Тип стациионара */
	private Long theHospType;
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
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getPigeonHole() {
		return thePigeonHole;
	}
	/**
	 * Новое свойство
	 */
	public void setPigeonHole(Long aPigeonHole) {
		thePigeonHole = aPigeonHole;
	}

	/**
	 * Новое свойство
	 */
	private Long thePigeonHole;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	public Long getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(Long aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private Long theServiceStream;
	
	/** Приоритет */
	@Comment("Приоритет")
	public Long getPriority() {
		return thePriority;
	}

	public void setPriority(Long aPriority) {
		thePriority = aPriority;
	}

	/** Приоритет */
	private Long thePriority;
	
	/** Тип регистрации */
	@Comment("Тип регистрации")
	public Long getRegistrationType() {
		return theRegistrationType;
	}

	public void setRegistrationType(Long aRegistrationType) {
		theRegistrationType = aRegistrationType;
	}

	/** Тип регистрации */
	private Long theRegistrationType;
	
	/** Тип коек */
	@Comment("Тип коек")
	public Long getBedSubType() {
		return theBedSubType;
	}

	public void setBedSubType(Long aBedSubType) {
		theBedSubType = aBedSubType;
	}

	/** Тип коек */
	private Long theBedSubType;
	/** Фильтр */
	@Comment("Фильтр")
	@DoUpperCase
	public String getFilterAdd() {
		return theFilterAdd;
	}

	public void setFilterAdd(String aFilterAdd) {
		theFilterAdd = aFilterAdd;
	}

	/** Фильтр */
	private String theFilterAdd;
}
