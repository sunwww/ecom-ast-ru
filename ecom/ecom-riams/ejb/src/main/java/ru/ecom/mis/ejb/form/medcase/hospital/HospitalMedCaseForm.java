package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;


@Comment("Госпитализация (СЛС общ. инф.)")
@EntityForm
@EntityFormPersistance(clazz= HospitalMedCase.class)
@WebTrail(comment = "Госпитализация", nameProperties= "statCardNumber", view="entityParentView-stac_ssl.do"
	,shortView="entityShortView-stac_ssl.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl")
@AViewInterceptors(
        @AEntityFormInterceptor(HospitalMedCaseViewInterceptor.class)
)

public class HospitalMedCaseForm extends MedCaseForm {
	/** Дата начала */
	@Comment("Дата начала") 
	@DateString @DoDateString 
	@Persist 
	public String getDateStart() {return theDateStart;	}
	public void setDateStart(String aDateStart) {theDateStart = aDateStart;}
	

	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNewProperty) {theNoActuality = aNewProperty;}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	public Long getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(Long aOrderWorkFunction) {theOrderWorkFunction = aOrderWorkFunction;}

	
	/** Экстренность */
	@Comment("Экстренность")
	@Persist
	public Boolean getEmergency() {return theEmergency;	}
	public void setEmergency(Boolean aNewProperty) {theEmergency = aNewProperty;}
	
	/** Внешний идентификатор */
	@Comment("Внешний идентификатор")
	@Persist
	public String getExternalId() {return theExternalId;}
	public void setExternalId(String aNewProperty) {theExternalId = aNewProperty;	}
	
	/** Дефекты догоспитального этапа */
	@Comment("Дефекты догоспитального этапа")
	@Persist
	public Long getPreAdmissionDefect() { return thePreAdmissionDefect;}
	public void setPreAdmissionDefect(Long aPreAdmissionDefect) {	thePreAdmissionDefect = aPreAdmissionDefect;}
	
	/** Тип доставки */
	@Comment("Тип доставки")
	@Persist
	public String getSupplyType() {return theSupplyType;	}
	public void setSupplyType(String aSupplyType) {theSupplyType= aSupplyType;}
	
	/** Номер направления */
	@Comment("Номер направления")
	@Persist
	public String getOrderNumber() {return theOrderNumber;	}
	public void setOrderNumber(String aOrderNumber) {theOrderNumber = aOrderNumber;	}

	/** Номер наряда доставки */
	@Comment("Номер наряда доставки")
	@Persist
	public String getSupplyOrderNumber() {return theSupplyOrderNumber;	}
	public void setSupplyOrderNumber(String aSupplyOrderNumber) {theSupplyOrderNumber = aSupplyOrderNumber;}
	

	/** Время заболевания до госпитализации */
	@Comment("Время заболевания до госпитализации")
	@Persist
	public Long getPreAdmissionTime() {	return thePreAdmissionTime;	}
	public void setPreAdmissionTime(Long aPreAdmissionTime) {thePreAdmissionTime = aPreAdmissionTime;}

	/** Время поступления */
	@Comment("Время поступления")
	@TimeString @DoTimeString
	@Persist 
	public String getEntranceTime() {	return theEntranceTime;	}
	public void setEntranceTime(String aEntranceTime) {theEntranceTime = aEntranceTime;}

	/** Время выписки */
	@Comment("Время выписки")
	@TimeString @DoTimeString
	@Persist
	public String getDischargeTime() {return theDischargeTime;}
	public void setDischargeTime(String aDischargeTime) {theDischargeTime = aDischargeTime;	}

	/** Исход госпитализации */
	@Comment("Исход госпитализации")
	@Persist
	public Long getOutcome() {	return theOutcome;}
	public void setOutcome(Long aOutcome) {theOutcome = aOutcome;	}

	/** Результат госпитализации */
	@Comment("Результат госпитализации")
	@Persist
	public Long getResult() {return theResult;	}
	public void setResult(Long aResult) {theResult = aResult;}

	///** Обследован на RW */
	//@Comment("Обследован на RW")
	//@Persist
	//public Boolean getRwExamination() {return theRwExamination;}
	//public void setRwExamination(Boolean aRwExamination) {theRwExamination = aRwExamination;}

	///** Обследован на ВИЧ */
	//@Comment("Обследован на ВИЧ")
	//@Persist
	//public Boolean getAidsExamination() {return theAidsExamination;}
	//public void setAidsExamination(Boolean aAidsExamination) {theAidsExamination = aAidsExamination;}
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	@Required
	public Long getPatient() {	return thePatient;	}
	public void setPatient(Long aPatient) {thePatient = aPatient;	}
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}

	/** Количество дней */
	@Comment("Количество дней")
	@Persist
	public String getDaysCount() {	return theDaysCount;}
	public void setDaysCount(String aDaysCount) {	theDaysCount = aDaysCount;}

	
	/** Состояние опьянения */
	@Comment("Состояние опьянения")
	@Persist
	public Long getIntoxication() {	return theIntoxication;	}
	public void setIntoxication(Long aIntoxication) {theIntoxication = aIntoxication;	}
	
	@Comment("Вид травмы")
	public Long getTrauma() {return theTrauma;	}
	public void setTrauma(Long aTrauma) {theTrauma = aTrauma;}
	
	/** Кем доставлен (тип доставки) */
	@Comment("Кем доставлен (тип доставки)")
	@Persist
	public Long getOrderType() {return theOrderType;}
	public void setOrderType(Long aOrderType) {theOrderType = aOrderType;}
	
	/** Отказ от госпитализации */
	@Comment("Отказ от госпитализации")
	@Persist
	public Long getDeniedHospitalizating() {return theDeniedHospitalizating;}
	public void setDeniedHospitalizating(Long aDeniedHospitalizating) {theDeniedHospitalizating = aDeniedHospitalizating;}

	///** Дата RW */
	//@Comment("Дата RW")
	//@DateString @DoDateString
	//@Persist
	//public String getRwDate() {return theRwDate;}
	//public void setRwDate(String aRwDate) {theRwDate = aRwDate;}

	/** Диагноз при поступлении */
	@Comment("Диагноз при поступлении")
	public String getEntranceDiagnos() {return theEntranceDiagnos;	}
	public void setEntranceDiagnos(String aEntranceDiagnos) {theEntranceDiagnos = aEntranceDiagnos;}

	/** Код МКБ приемного отделения */
	@Comment("Код МКБ приемного отделения")
	public Long getEntranceMkb() {return theEntranceMkb;}
	public void setEntranceMkb(Long aEntranceMkb) {theEntranceMkb = aEntranceMkb;}

	/** Диагноз направившего учреждения */
	@Comment("Диагноз направившего учреждения")
	public String getOrderDiagnos() {return theOrderDiagnos;}
	public void setOrderDiagnos(String aOrderDiagnos) {theOrderDiagnos = aOrderDiagnos;}

	/** Код МКБ направившего учреждения */
	@Comment("Код МКБ направившего учреждения")
	public Long getOrderMkb() {return theOrderMkb;	}
	public void setOrderMkb(Long aOrderMkb) {theOrderMkb = aOrderMkb;}

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}

	/** Оператор */
	@Comment("Оператор")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	
	/** Случай является отказом от госпитализации? */
	@Comment("Случай является отказом от госпитализации?")
	@Persist
	public Boolean getIsDeniedHospitalizating() {return theIsDeniedHospitalizating;}
	public void setIsDeniedHospitalizating(Boolean aIsDeniedHospitalizating) {theIsDeniedHospitalizating = aIsDeniedHospitalizating;}

	/** Амбулаторное лечение */
	@Comment("Амбулаторное лечение")
	@Persist
	public Boolean getAmbulanceTreatment() {return theAmbulanceTreatment;}
	public void setAmbulanceTreatment(Boolean aAmbulanceTreatment) {theAmbulanceTreatment = aAmbulanceTreatment;}

	/** Номер стат. карты */
	@Comment("Номер стат. карты")
	@Persist
	public String getStatCardNumber() {return theStatCardNumber;	}
	public void setStatCardNumber(String aStatCardNumber) {theStatCardNumber = aStatCardNumber;}

	///** Номер RW */
	//@Comment("Номер RW")
	//@Persist
	//public String getRwNumber() {return theRwNumber;}
	//public void setRwNumber(String aRwNumber) {theRwNumber = aRwNumber;}

	/** Сообщение родственникам */
	@Comment("Сообщение родственникам")
	@Persist
	public Boolean getRelativeMessage() {return theRelativeMessage;	}
	public void setRelativeMessage(Boolean aRelativeMessage) {theRelativeMessage = aRelativeMessage;}

	/** Оказана мед. помощь в приемном отделении */
	@Comment("Оказана мед. помощь в приемном отделении")
	@Persist
	public Boolean getMedicalAid() {return theMedicalAid;}
	public void setMedicalAid(Boolean aMedicalAid) {theMedicalAid = aMedicalAid;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist 
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;	}

	@Comment("Провизорность")
	@Persist @Deprecated
	public Boolean getProvisional() {return theProvisional;}
	public void setProvisional(Boolean aProvisional) {theProvisional = aProvisional;}

	/** Перевод в другое ЛПУ */
	@Comment("Перевод в другое ЛПУ")
	@Persist
	public Long getMoveToAnotherLPU() {return theMoveToAnotherLPU;}
	public void setMoveToAnotherLPU(Long aMoveToAnotherLPU) {theMoveToAnotherLPU = aMoveToAnotherLPU;}

	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@Persist
	public Long getHospType() {return theHospType;}
	public void setHospType(Long aHospType) {theHospType = aHospType;}

	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist
	public Long getHospitalization() {return theHospitalization;}
	public void setHospitalization(Long aHospitalization) {theHospitalization = aHospitalization;}

	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@Persist
	public Long getOwnerFunction() {return theOwnerFunction;}
	public void setOwnerFunction(Long aOwnerFunction) {theOwnerFunction = aOwnerFunction;}
	
	/** Выписной эпикриз */
	@Comment("Выписной эпикриз")
	public String getDischargeEpicrisis() {return theDischargeEpicrisis;}
	public void setDischargeEpicrisis(String aDischargeEpicrisis) {theDischargeEpicrisis = aDischargeEpicrisis;}

	/** Клинический диагноз */
	@Comment("Клинический диагноз")
	public String getClinicalDiagnos() {return theClinicalDiagnos;}
	public void setClinicalDiagnos(String aClinicalDiagnos) {theClinicalDiagnos = aClinicalDiagnos;}

	/** Код МКБ клинического диагноза */
	@Comment("Код МКБ клинического диагноза")
	@Mkb
	public Long getClinicalMkb() {return theClinicalMkb;}
	public void setClinicalMkb(Long aClinicalMkb) {theClinicalMkb = aClinicalMkb;}
	
	/** Патанатомический диагноз */
	@Comment("Патанатомический диагноз")
	public String getPathanatomicalDiagnos() {return thePathanatomicalDiagnos;}
	public void setPathanatomicalDiagnos(String aPathanatomicalDiagnos) {thePathanatomicalDiagnos = aPathanatomicalDiagnos;}	
	
	/** Патанатомический диагноз по МКБ-10 */
	@Comment("Патанатомический диагноз по МКБ-10")
	@Mkb
	public Long getPathanatomicalMkb() {return thePathanatomicalMkb;}
	public void setPathanatomicalMkb(Long aPathanatomicalMkb) {thePathanatomicalMkb = aPathanatomicalMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return theConcludingDiagnos;}
	public void setConcludingDiagnos(String aConcludingDiagnos) {theConcludingDiagnos = aConcludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb
	public Long getConcludingMkb() {return theConcludingMkb;}
	public void setConcludingMkb(Long aConcludingMkb) {theConcludingMkb = aConcludingMkb;}
	
	/** Сопровождающее лицо */
	@Comment("Сопровождающее лицо")
	@Persist
	public String getAttendant() {return theAttendant;}
	public void setAttendant(String aAttendant) {theAttendant = aAttendant;}
	
	/** Отделение (текст) */
	@Comment("Отделение (текст)")
	@Persist
	public String getDepartmentInfo() {return theDepartmentInfo;}
	public void setDepartmentInfo(String aDepartmentInfo) {theDepartmentInfo = aDepartmentInfo;}

	/** Педикулез */
	@Comment("Педикулез")
	@Persist
	public Long getPediculosis() {return thePediculosis;}
	public void setPediculosis(Long aPediculosis) {thePediculosis = aPediculosis;}

	/** Педикулез */
	private Long thePediculosis;

	/** Редкий случай */
	@Comment("Редкий случай")
	@Persist
	public Boolean getRareCase() {return theRareCase;}
	public void setRareCase(Boolean aRareCase) {theRareCase = aRareCase;}

	/** Диагноз клин. сопутствующий */
	@Comment("Диагноз клин. сопутствующий")
	public String getConcomitantDiagnos() {return theConcomitantDiagnos;}
	public void setConcomitantDiagnos(String aConcomitantDiagnos) {theConcomitantDiagnos = aConcomitantDiagnos;}

	/** Диагноз клин. сопутствующий МКБ */
	@Comment("Диагноз клин. сопутствующий МКБ")
	@Mkb
	public Long getConcomitantMkb() {return theConcomitantMkb;}
	public void setConcomitantMkb(Long aConcomitantMkb) {theConcomitantMkb = aConcomitantMkb;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getClinicalActuity() {return theClinicalActuity;}
	public void setClinicalActuity(Long aClinicalActuity) {theClinicalActuity = aClinicalActuity;}

	 /**
	  * Причина госпитализации в психиатрический стационар
	  */
	 @Comment("Причина госпитализации в психиатрический стационар")
	 @Persist
	 public Long getPsychReason() {return thePsychReason;}
	 public void setPsychReason(Long aPsychReason) {thePsychReason = aPsychReason;}
		/** Дата решения суда */
		@Comment("Дата решения суда")
		@DateString @DoDateString @Persist
		public String getLawCourtDesicionDate() {return theLawCourtDesicionDate;}
		public void setLawCourtDesicionDate(String aLawCourtDesicionDate) {theLawCourtDesicionDate = aLawCourtDesicionDate;}

		/** Острота диагноза заключительного */
		@Comment("Острота диагноза клинического")
		public Long getConcludingActuity() {return theConcludingActuity;}
		public void setConcludingActuity(Long aClinicalActuity) {theConcludingActuity = aClinicalActuity;}

		/** Острота диагноза заключительного */
		private Long theConcludingActuity;
		/** Дата решения суда */
		private String theLawCourtDesicionDate;
	 /**
	  * Причина госпитализации в психиатрический стационар
	  */
	 private Long thePsychReason;
	/** Острота диагноза клинического */
	private Long theClinicalActuity;
	/** Диагноз клин. сопутствующий МКБ */
	private Long theConcomitantMkb;
	/** Диагноз клин. сопутствующий */
	private String theConcomitantDiagnos;
	
	
	/** Редкий случай */
	private Boolean theRareCase;

	/** Отделение (текст) */
	private String theDepartmentInfo;
	/** Сопровождающее лицо */
	private String theAttendant;
	/** Заключительный диагноз по МКБ-10 */
	private Long theConcludingMkb;
	/** Заключительный диагноз */
	private String theConcludingDiagnos;
	/** Патанатомический диагноз по МКБ-10 */
	private Long thePathanatomicalMkb;
	/** Патанатомический диагноз */
	private String thePathanatomicalDiagnos;
	/** Код МКБ клинического диагноза */
	private Long theClinicalMkb;
	/** Клинический диагноз */
	private String theClinicalDiagnos;
	/** Выписной эпикриз */
	private String theDischargeEpicrisis;
	/** Рабочая функция лечащего врача */
	private Long theOwnerFunction;
	/** Госпитализация (впервые, повторно) */
	private Long theHospitalization;
	/** Тип текущего стационара */
	private Long theHospType;	
	/** Перевод в другое ЛПУ */
	private Long theMoveToAnotherLPU;
	/** Провизорность */
	private Boolean theProvisional;
	/** Лечебное учреждение */
	private Long theLpu;
	/** Оказана мед. помощь в приемном отделении */
	private Boolean theMedicalAid;
	/** Сообщение родственникам */
	private Boolean theRelativeMessage;
	///** Номер RW */
	//private String theRwNumber;
	/** Номер стат. карты */
	private String theStatCardNumber;
	/** Амбулаторное лечение */
	private Boolean theAmbulanceTreatment;
	/** Случай является отказом от госпитализации? */
	private Boolean theIsDeniedHospitalizating;
	/** Отделение */
	private Long theDepartment;
	/** Оператор */
	private String theUsername;
	/** Профиль коек */
	private Long theBedType;
	/** Код МКБ направившего учреждения */
	private Long theOrderMkb;
	/** Диагноз направившего учреждения */
	private String theOrderDiagnos;
	/** Код МКБ приемного отделения */
	private Long theEntranceMkb;
	/** Диагноз при поступлении */
	private String theEntranceDiagnos;
	///** Дата RW */
	//private String theRwDate;
	/** Отказ от госпитализации */
	private Long theDeniedHospitalizating;
	/** Кем доставлен (тип доставки) */
	private Long theOrderType;
	/** Вид травмы */
	private Long theTrauma;
	/** Состояние опьянения */
	private Long theIntoxication;
	/** Количество дней */
	private String theDaysCount;
	/** Дата окончания */
	private String theDateFinish;
	/** Пациент */
	private Long thePatient;
	///** Обследован на ВИЧ */
	//private Boolean theAidsExamination;
	///** Обследован на RW */
	//private Boolean theRwExamination;
	/** Результат госпитализации */
	private Long theResult;
	/** Исход госпитализации */
	private Long theOutcome;
	/** Время выписки */
	private String theDischargeTime;
	/** Время поступления */
	private String theEntranceTime;
	/** Время заболевания до госпитализации */
	private Long thePreAdmissionTime;

	/** Номер наряда доставки */
	private String theSupplyOrderNumber;
	/** Номер направления */
	private String theOrderNumber;
	/** Тип доставки */
	private String theSupplyType;
	/** Дефекты догоспитального этапа */
	private Long thePreAdmissionDefect;
	/** Внешний идентификатор */
	private String theExternalId;
	/** Экстренность */
	private Boolean theEmergency;
	/** Недействительность */
	private Boolean theNoActuality;
	/** Дата начала */
	private String theDateStart;
	/** Рабочая функция направителя */
	private Long theOrderWorkFunction;

	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist
	public String getOrderDate() {return theOrderDate;}
	public void setOrderDate(String aOrderDate) {theOrderDate = aOrderDate;}

	/** Дата направления */
	private String theOrderDate;
	
	/** Направитель */
	@Comment("Направитель")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Направитель */
	private Long theOrderLpu;
	
	/** Порядок поступления */
	@Comment("Порядок поступления")
	@Persist
	public Long getAdmissionOrder() {return theAdmissionOrder;}
	public void setAdmissionOrder(Long aAdmissionOrder) {theAdmissionOrder = aAdmissionOrder;}

	/** Решение суда по 35 статье */
	@Comment("Решение суда по 35 статье")
	@Persist
	public Long getJudgment35() {return theJudgment35;}
	public void setJudgment35(Long aJudgment35) {theJudgment35 = aJudgment35;}

	///** Число дней лечебных отпусков */
	//@Comment("Число дней лечебных отпусков")
	//@Persist
	//public Integer getMedicalHolidayDays() {return theMedicalHolidayDays;}
	//public void setMedicalHolidayDays(Integer aMedicalHolidayDays) {theMedicalHolidayDays = aMedicalHolidayDays;}

	///** Число лечебных отпусков */
	//@Comment("Число лечебных отпусков")
	//@Persist
	//public Integer getMedicalHolidays() {return theMedicalHolidays;}
	//public void setMedicalHolidays(Integer aMedicalHolidays) {theMedicalHolidays = aMedicalHolidays;}

	///** Число лечебных отпусков */
	//private Integer theMedicalHolidays;
	///** Число дней лечебных отпусков */
	//private Integer theMedicalHolidayDays;
	/** Решение суда по 35 статье */
	private Long theJudgment35;
	/** Порядок поступления */
	private Long theAdmissionOrder;
	
	/** Осложнение текст диагноза */
	@Comment("Осложнение текст диагноза")
	public String getComplicationDiagnos() {return theComplicationDiagnos;}
	public void setComplicationDiagnos(String aComplicationDiagnos) {theComplicationDiagnos = aComplicationDiagnos;}

	/** Осложнение МКБ диагноза */
	@Comment("Осложнение МКБ диагноза")
	public Long getComplicationMkb() {return theComplicationMkb;}
	public void setComplicationMkb(Long aComplicationMkb) {theComplicationMkb = aComplicationMkb;}

	/** Осложнение МКБ диагноза */
	private Long theComplicationMkb;
	/** Осложнение текст диагноза */
	private String theComplicationDiagnos;
	
	/** Диагнозы осложнение */
	@Comment("Диагнозы осложнение")
	public String getComplicationDiags() { return theComplicationDiags; }
	public void setComplicationDiags(String aComplicationDiags) { theComplicationDiags = aComplicationDiags; }

	/** Диагнозы осложнение */
	private String theComplicationDiags;
	
	/** Диагнозы сопутствующие */
	@Comment("Диагнозы сопутствующие")
	public String getConcomitantDiags() { return theConcomitantDiags; }

	public void setConcomitantDiags(String aConcomitantDiags) { theConcomitantDiags = aConcomitantDiags; }

	/** Диагнозы сопутствующие */
	private String theConcomitantDiags;


	/** Поступление в данный стационар */
	@Comment("Поступление в данный стационар")
	@Persist
	public Long getAdmissionInHospital() {return theAdmissionInHospital;}
	public void setAdmissionInHospital(Long aAdmissionInHospital) {theAdmissionInHospital = aAdmissionInHospital;}

	/** Откуда поступил */
	@Comment("Откуда поступил")
	@Persist
	public Long getWhereAdmission() {return theWhereAdmission;}
	public void setWhereAdmission(Long aWhereAdmission) {theWhereAdmission = aWhereAdmission;}

	/** Откуда поступил */
	private Long theWhereAdmission;
	/** Поступление в данный стационар */
	private Long theAdmissionInHospital;

	/** Дата перевода */
	@Comment("Дата перевода")
	@DateString @DoDateString
	@Persist @MaxDateCurrent
	public String getTransferDate() {return theTransferDate;	}
	public void setTransferDate(String aTransferDate) {theTransferDate = aTransferDate;}

	/** Время перевода */
	@Comment("Время перевода")
	@TimeString @DoTimeString
	@Persist
	public String getTransferTime() {return theTransferTime;	}
	public void setTransferTime(String aTransferTime) {theTransferTime = aTransferTime;}

	/** Время перевода */
	private String theTransferTime;
	/** Дата перевода */
	private String theTransferDate;
	
	/** Причина выписки */
	@Comment("Причина выписки")
	@Persist
	public Long getReasonDischarge() {return theReasonDischarge;}
	public void setReasonDischarge(Long aReasonDischarge) {theReasonDischarge = aReasonDischarge;}
	
	/** Причина выписки */
	private Long theReasonDischarge;
	/** Итог выписки */
	@Comment("Итог выписки")
	@Persist
	public Long getResultDischarge() {return theResultDischarge;}
	public void setResultDischarge(Long aResultDischarge) {theResultDischarge = aResultDischarge;}

	/** Причина выписки */
	private Long theResultDischarge;
	/* Рост */
	@Comment("Рост")
	@Persist
	public Integer getHeight() { return theHeight; }
	public void setHeight(Integer aHeight) {
		theHeight = aHeight;
	}
	/* Вес */
	@Comment("Вес")
	@Persist
	public Integer getWeight() {
		return theWeight;
	}
	public void setWeight(Integer aWeight) {
		theWeight = aWeight;
	}
	/* Индекс массы тела */
	@Comment("Индекс массы тела")
	@Persist
	public Double getTheIMT() {
		return theIMT;
	}
	public void setTheIMT(Double aIMT) {
		theIMT = aIMT;
	}
	/** Вес */
	private Integer theWeight;
	/** Рост */
	private Integer theHeight;
	/** Индекс массы тела */
	private Double theIMT;

	/** Дата идентификации */
	@Comment("Дата идентификации")
	@DateString @DoDateString
	@MaxDateCurrent
	public String getIdentDate() {return theIdentDate;	}
	public void setIdentDate(String aIdentDate) {theIdentDate = aIdentDate;}

	/** Время идентификации */
	@Comment("Время идентификации")
	@DoTimeString @TimeString
	public String getIdentTime() {return theIdentTime;	}
	public void setIdentTime(String aIdentTime) {theIdentTime = aIdentTime;}

	/** Кто провёл идентификацию */
	@Comment("Кто провёл идентификацию")
	public String getIdentUsername() {return theIdentUsername;}
	public void setIdentUsername(String aIdentUsername) {theIdentUsername = aIdentUsername;}

	/** Была ли проведена идентификация пациента? */
	@Comment("Была ли проведена идентификация пациента?")
	public Boolean getIsIdentified() { return theIsIdentified; }
	public void setIsIdentified(Boolean aIsIdentified) { theIsIdentified = aIsIdentified; }

	/** Дата идентификации */
	private String theIdentDate;
	/** Время идентификации */
	private String theIdentTime;
	/** Кто провёл идентификацию */
	private String theIdentUsername;
	/** Была ли проведена идентификация пациента */
	private Boolean theIsIdentified;
}
