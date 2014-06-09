package ru.ecom.mis.web.action.medcase.journal;

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
	public Boolean getHour8() {return theHour8;}
	public void setHour8(Boolean aHour8Is) {theHour8 = aHour8Is;}

	/** 8 часов */
	private Boolean theHour8;
	/** Время */
	@Comment("Время")
	@DoTimeString @TimeString
	public String getTimeBegin() {return theTimeBegin;}
	public void setTimeBegin(String aTimeBegin) {theTimeBegin = aTimeBegin;}

	/** Тип стациионара */
	@Comment("Тип стациионара")
	public Long getHospType() {return theHospType;}
	public void setHospType(Long aHospType) {theHospType = aHospType;}
	
	/** Специалист */
	@Comment("Специалист")
	public Long getSpec() {return theSpec;}
	public void setSpec(Long aSpec) {theSpec = aSpec;}

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

	/** Фильтр 1 */
	@Comment("Фильтр 1")
	public String getFilterAdd1() {
		return theFilterAdd1;
	}

	public void setFilterAdd1(String aFilterAdd1) {
		theFilterAdd1 = aFilterAdd1;
	}

	/** Фильтр 2 */
	@Comment("Фильтр 2")
	public String getFilterAdd2() {
		return theFilterAdd2;
	}

	public void setFilterAdd2(String aFilterAdd2) {
		theFilterAdd2 = aFilterAdd2;
	}

	/** Фильтр 2 */
	private String theFilterAdd2;
	/** Фильтр 1 */
	private String theFilterAdd1;
	/** Фильтр */
	private String theFilterAdd;
	/** Тип коек */
	@Comment("Тип коек")
	public Long getRoomType() {
		return theRoomType;
	}

	public void setRoomType(Long aRoomType) {
		theRoomType = aRoomType;
	}

	/** Тип коек */
	private Long theRoomType;
	
	/** Кол-во коек в палате */
	@Comment("Кол-во коек в палате")
	public Long getCountBed() {
		return theCountBed;
	}

	public void setCountBed(Long aCountBed) {
		theCountBed = aCountBed;
	}

	/** Кол-во коек в палате */
	private Long theCountBed;
	/** Тип сообщения в полицию */
	@Comment("Тип сообщения в полицию")
	public Long getPhoneMessageType() {
		return thePhoneMessageType;
	}

	public void setPhoneMessageType(Long aPhoneMessageType) {
		thePhoneMessageType = aPhoneMessageType;
	}
	
	/** Подтип сообщений в полицию */
	@Comment("Подтип сообщений в полицию")
	public Long getPhoneMessageSubType() {
		return thePhoneMessageSubType;
	}

	public void setPhoneMessageSubType(Long aPhoneMessageSubType) {
		thePhoneMessageSubType = aPhoneMessageSubType;
	}

	/** Подтип сообщений в полицию */
	private Long thePhoneMessageSubType;

	/** Тип сообщения в полицию */
	private Long thePhoneMessageType;
	
	/** ЛПУ направителя */
	@Comment("ЛПУ направителя")
	public Long getLpuDirect() {return theLpuDirect;}
	public void setLpuDirect(Long aLpuDirect) {theLpuDirect = aLpuDirect;}

	/** Тип ЛПУ направителя */
	@Comment("Тип ЛПУ направителя")
	public Long getLpuFunctionDirect() {return theLpuFunctionDirect;}
	public void setLpuFunctionDirect(Long aLpuFunctionDirect) {theLpuFunctionDirect = aLpuFunctionDirect;}

	/** Тип ЛПУ направителя */
	private Long theLpuFunctionDirect;
	/** ЛПУ направителя */
	private Long theLpuDirect;
	
	/** Пол */
	@Comment("Пол")
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}

	/** Расхождение по диагнозу */
	@Comment("Расхождение по диагнозу")
	public Long getCategoryDifference() {return theCategoryDifference;}
	public void setCategoryDifference(Long aCategoryDifference) {theCategoryDifference = aCategoryDifference;}

	/** Профиль коек */
	@Comment("Профиль коек")
	public Long getBedType() {
		return theBedType;
	}

	public void setBedType(Long aBedType) {
		theBedType = aBedType;
	}

	/** Стандарт */
	@Comment("Стандарт")
	public Long getStandart() {
		return theStandart;
	}

	public void setStandart(Long aStandart) {
		theStandart = aStandart;
	}

	/** Стандарт */
	private Long theStandart;
	/** Профиль коек */
	private Long theBedType;
	/** Расхождение по диагнозу */
	private Long theCategoryDifference;
	/** Пол */
	private Long theSex;
	
	/** Отделение 1 */
	@Comment("Отделение 1")
	public Long getDepartment1() {return theDepartment1;}
	public void setDepartment1(Long aDepartment1) {theDepartment1 = aDepartment1;}

	/** Отделение 2 */
	@Comment("Отделение 2")
	public Long getDepartment2() {return theDepartment2;}
	public void setDepartment2(Long aDepartment2) {theDepartment2 = aDepartment2;}

	/** Отделение 2 */
	private Long theDepartment2;
	/** Отделение 1 */
	private Long theDepartment1;
	
	/** Кол-во символов */
	@Comment("Кол-во символов")
	public Long getCntSymbols() {return theCntSymbols;}
	public void setCntSymbols(Long aCntSymbols) {theCntSymbols = aCntSymbols;}

	/** Кол-во символов */
	private Long theCntSymbols;
	/** Раб.функция */
	@Comment("Раб.функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Раб.функция */
	private Long theWorkFunction;
	
	/** illnesPrimary */
	@Comment("illnesPrimary")
	public Long getIllnesPrimary() {
		return theIllnesPrimary;
	}

	public void setIllnesPrimary(Long aIllnesPrimary) {
		theIllnesPrimary = aIllnesPrimary;
	}

	/** illnesPrimary */
	private Long theIllnesPrimary;
	
	/** AdditionStatus */
	@Comment("AdditionStatus")
	public Long getAdditionStatus() {
		return theAdditionStatus;
	}

	public void setAdditionStatus(Long aAdditionStatus) {
		theAdditionStatus = aAdditionStatus;
	}

	/** AdditionStatus */
	private Long theAdditionStatus;
	
	/** Строка отчета */
	@Comment("Строка отчета")
	public Long getReportStr() {return theReportStr;}
	public void setReportStr(Long aReportStr) {theReportStr = aReportStr;}

	/** Строка отчета */
	private Long theReportStr;
}
