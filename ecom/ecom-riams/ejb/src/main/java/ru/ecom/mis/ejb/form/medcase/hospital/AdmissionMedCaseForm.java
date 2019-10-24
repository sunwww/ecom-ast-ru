package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Случай стационарного лечения. Поступление в приемном отделении")
@EntityForm
@EntityFormPersistance(clazz= HospitalMedCase.class)
@WebTrail(comment = "Случай стационарного лечения. Поступление в приемном отделении", nameProperties= "id", view="entityParentView-stac_sslAdmission.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Admission")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(AdmissionPreCreateInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(AdmissionSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(AdmissionViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(AdmissionSaveInterceptor.class)
})
public class AdmissionMedCaseForm extends HospitalMedCaseForm {

	/** Предварительная госпитализация */
	@Comment("Предварительная госпитализация")
	public Long getPreHosp() {return thePreHosp;}
	public void setPreHosp(Long aPreHosp) {thePreHosp = aPreHosp;}
	/** Предварительная госпитализация */
	private Long thePreHosp ;

	/** Дата начала */
	@Comment("Дата начала") 
	@DateString @DoDateString 
	@Persist @MaxDateCurrent @Required
	public String getDateStart() {return theDateStart;	}
	public void setDateStart(String aDateStart) {theDateStart = aDateStart;}
	

	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNewProperty) {theNoActuality = aNewProperty;}
	


	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}
	
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
	
	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist 
	public String getOrderDate() {return theOrderDate;}
	public void setOrderDate(String aOrderDate) {theOrderDate = aOrderDate;}

	/** Время заболевания до госпитализации */
	@Comment("Время заболевания до госпитализации")
	@Persist 
	public Long getPreAdmissionTime() {	return thePreAdmissionTime;	}
	public void setPreAdmissionTime(Long aPreAdmissionTime) {thePreAdmissionTime = aPreAdmissionTime;}

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
	public Long getPatient() {	return thePatient;	}
	public void setPatient(Long aPatient) {thePatient = aPatient;	}
		
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
	//@Required
	public String getEntranceDiagnos() {return theEntranceDiagnos;	}
	public void setEntranceDiagnos(String aEntranceDiagnos) {theEntranceDiagnos = aEntranceDiagnos;}

	/** Код МКБ приемного отделения */
	@Comment("Код МКБ приемного отделения")
	//@Required
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
	
	/** Дата диагноза при поступлении */
	@Comment("Дата диагноза при поступлении")
	@DoDateString @DateString 
	public String getEntranceDiagDate() {return theEntranceDiagDate;}
	public void setEntranceDiagDate(String aEntranceDiagDate) {theEntranceDiagDate = theDateStart;}

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
	@Persist @Required
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

	/** Время поступления */
	@Comment("Время поступления")
	@Persist @Required
	@DoTimeString @TimeString
	public String getEntranceTime() {return theEntranceTime;}
	public void setEntranceTime(String aEntranceTime) {theEntranceTime = aEntranceTime;}

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
	@Required
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;	}
	
	/** Откуда поступил */
	@Comment("Откуда поступил")
	@Persist
	public Long getSourceHospType() {
		return theSourceHospType;
	}

	public void setSourceHospType(Long aSourceHospType) {
		theSourceHospType = aSourceHospType;
	}

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
	public Double getIMT() {
		return theIMT;
	}
	public void setIMT(Double aIMT) { theIMT = aIMT;}

	/** Откуда поступил */
	private Long theSourceHospType;
	
	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@Persist
	public Long getHospType() {return theHospType;}
	public void setHospType(Long aHospType) {theHospType = aHospType;}


	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@Persist
	public Long getOwnerFunction() {return theOwnerFunction;}
	public void setOwnerFunction(Long aOwnerFunction) {theOwnerFunction = aOwnerFunction;}

	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist 
	public Long getHospitalization() {return theHospitalization;}
	public void setHospitalization(Long aHospitalization) {theHospitalization = aHospitalization;}

	/** Прикрепленные полисы */
	@Comment("Прикрепленные полисы")
	public Long getAttachedPolicies() {return theAttachedPolicies;}
	public void setAttachedPolicies(Long aAttachedPolicies) {theAttachedPolicies = aAttachedPolicies;}

	/** Прикрепленный полис ДМС */
	@Comment("Прикрепленный полис ДМС")
	public Long getAttachedPolicyDmc() {return theAttachedPolicyDmc;}
	public void setAttachedPolicyDmc(Long aAttachedPolicyDmc) {theAttachedPolicyDmc = aAttachedPolicyDmc;}

	/** Была ли проведена идентификация пациента? */
	@Comment("Была ли проведена идентификация пациента?")
	@Persist
	public Boolean getIsIdentified() { return theIsIdentified; }
	public void setIsIdentified(Boolean aIsIdentified) { theIsIdentified = aIsIdentified; }

	/** Дата идентификации */
	@Comment("Дата идентификации")
	@DateString @DoDateString
	@Persist @MaxDateCurrent
	public String getIdentDate() {return theIdentDate;	}
	public void setIdentDate(String aIdentDate) {theIdentDate = aIdentDate;}

	/** Время идентификации */
	@Comment("Время идентификации")
	@Persist
	@DoTimeString @TimeString
	public String getIdentTime() {return theIdentTime;	}
	public void setIdentTime(String aIdentTime) {theIdentTime = aIdentTime;}

	/** Кто провёл идентификацию */
	@Comment("Кто провёл идентификацию")
	@Persist
	public String getIdentUsername() {return theIdentUsername;}
	public void setIdentUsername(String aIdentUsername) {theIdentUsername = aIdentUsername;}

	/** Прикрепленный полис ДМС */
	private Long theAttachedPolicyDmc;
	/** Прикрепленные полисы */
	private Long theAttachedPolicies;
	
	/** Госпитализация (впервые, повторно) */
	private Long theHospitalization;
	/** Рабочая функция лечащего врача */
	private Long theOwnerFunction;
/** Тип текущего стационара */
	private Long theHospType;
	

	/** Лечебное учреждение */
	private Long theLpu;
	/** Оказана мед. помощь в приемном отделении */
	private Boolean theMedicalAid;
	/** Сообщение родственникам */
	private Boolean theRelativeMessage;
	/** Время поступления */
	private String theEntranceTime;
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
	/** Дата диагноза при поступлении */
	private String theEntranceDiagDate;
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
	/** Время заболевания до госпитализации */
	private Long thePreAdmissionTime;
	/** Дата направления */
	private String theOrderDate;
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
	/** Внешний направитель (ЛПУ) */
	private Long theOrderLpu;

	/** Недействительность */
	private Boolean theNoActuality;
	/** Дата начала */
	private String theDateStart;

	/** Вес */
	private Integer theWeight;
	/** Рост */
	private Integer theHeight;
	/** Индекс массы тела */
	private Double theIMT;

	/** Была ли проведена идентификация пациента */
	private Boolean theIsIdentified;
	/** Дата идентификации */
	private String theIdentDate;
	/** Время идентификации */
	private String theIdentTime;
	/** Кто провёл идентификацию */
	private String theIdentUsername;
}

