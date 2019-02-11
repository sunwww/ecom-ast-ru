package ru.ecom.mis.ejb.form.medcase.ticket;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketPreCreateInterceptor;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Форма короткого СМО
 * @author STkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz = ShortMedCase.class)
@Comment("Талон")
@Parent(property = "medcard", parentForm = MedcardForm.class,orderBy="dateStart")
@WebTrail(comment = "Талон", nameProperties = "id"
	, view = "entityView-smo_ticket.do"
	, shortView = "entityShortView-smo_ticket.do"
	, list = "js-smo_ticket-list.do"
	, shortList = "js-smo_ticket-list.do?short=Short" 
)
@EntityFormSecurityPrefix("/Policy/Poly/Ticket")
@ASaveInterceptors(
        @AEntityFormInterceptor(TicketMedCaseSaveInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(TicketMedCaseSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(TicketMedCaseViewInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(TicketPreCreateInterceptor.class)
)
public class TicketMedCaseForm extends ShortTicketMedCaseForm {
	/** Признак консультативно-диагностического обращения */
	@Comment("Признак консультативно-диагностического обращения")
	@Persist
	public Boolean getIsDiagnosticSpo() {return theIsDiagnosticSpo;}
	public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {theIsDiagnosticSpo = aIsDiagnosticSpo;}
	/** Признак консультативно-диагностического обращения */
	private Boolean theIsDiagnosticSpo ;

	/** Планируемая дата исполнения */
	@Comment("Планируемая дата исполнения")
	@Persist
	public Long getDatePlan() {return theDatePlan;	}
	public void setDatePlan(Long aNewProperty) {theDatePlan = aNewProperty;	}

	/** Планируемая рабочая функция исполнения */
	@Comment("Планируемая рабочая функция исполнения")
	@Persist
	public Long getWorkFunctionPlan() {return theWorkFunctionPlan;}
	public void setWorkFunctionPlan(Long aNewProperty) {theWorkFunctionPlan = aNewProperty;}

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return theWorkFunctionExecute;	}
	/** Рабочая функция исполнения */
	public void setWorkFunctionExecute(Long aNewProperty) {	theWorkFunctionExecute = aNewProperty;}

	/** Планируемое время исполнения */
	@Comment("Планируемое время исполнения")
	@Persist
	public Long getTimePlan() {return theTimePlan;}
	public void setTimePlan(Long aNewProperty) {	theTimePlan = aNewProperty;	}

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString
	public String getTimeExecute() {return theTimeExecute;	}
	public void setTimeExecute(String aNewProperty) {theTimeExecute = aNewProperty;}

	/** Время исполнения */
	private String theTimeExecute;
	/** Планируемое время исполнения */
	private Long theTimePlan;
	/** Рабочая функция исполнения */
	private Long theWorkFunctionExecute;
	/** Планируемая рабочая функция исполнения */
	private Long theWorkFunctionPlan;
	/** Планируемая дата исполнения */
	private Long theDatePlan;
	/** Инфо по полису */
	@Comment("Инфо по полису")
	public String getInfoByPolicy() {return theInfoByPolicy;}
	public void setInfoByPolicy(String aInfoByPolicy) {theInfoByPolicy = aInfoByPolicy;}

	/** Инфо по полису */
	private String theInfoByPolicy;
	
	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aOrderDate) {theDateFinish = aOrderDate;}

	/** Дата направления */
	private String theDateFinish;

    /** Разговор с родственником */
	@Comment("Разговор с родственником")
	@Persist
	public Boolean getIsTalk() {return theIsTalk;}
	public void setIsTalk(Boolean aIsTalk) {theIsTalk = aIsTalk;}

	/** Направление на госпитализацию */
	@Comment("Направление на госпитализацию")
	@Persist
	public Boolean getIsDirectHospital() {return theIsDirectHospital;}
	public void setIsDirectHospital(Boolean aIsDirectHospital) {theIsDirectHospital = aIsDirectHospital;}

	/** Направление на госпитализацию */
	private Boolean theIsDirectHospital;
	/** Разговор с родственником */
	private Boolean theIsTalk;
	
	/** Услуги */
	@Comment("Услуги")
	public String getMedServices() {return theMedServices;	}
	public void setMedServices(String aMedServices) {theMedServices = aMedServices;}

	/** Услуги */
	private String theMedServices;
	/** Сопутствующие диагнозы */
	@Comment("Сопутствующие диагнозы")
	public String getConcomitantDiseases() {return theConcomitantDiseases;}
	public void setConcomitantDiseases(String aConcomitantDiseases) {theConcomitantDiseases = aConcomitantDiseases;}

	/** Сопутствующие диагнозы */
	private String theConcomitantDiseases;
	
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@Required
	public String getConcludingDiagnos() {return theConcludingDiagnos;}
	public void setConcludingDiagnos(String aConcludingDiagnos) {theConcludingDiagnos = aConcludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb @Required
	public Long getConcludingMkb() {return theConcludingMkb;}
	public void setConcludingMkb(Long aConcludingMkb) {theConcludingMkb = aConcludingMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public Long getConcludingTrauma() {return theConcludingTrauma;}
	public void setConcludingTrauma(Long aConcludingTrauma) {theConcludingTrauma = aConcludingTrauma;}

	/** Острота диагноза заключительного */
	@Comment("Острота диагноза клинического")
	@Required
	public Long getConcludingActuity() {return theConcludingActuity;}
	public void setConcludingActuity(Long aClinicalActuity) {theConcludingActuity = aClinicalActuity;}

	/** Результат визита */
	@Comment("Результат визита")
	@Persist @Required
	public Long getVisitResult() {return theVisitResult;	}
	public void setVisitResult(Long aResult) {theVisitResult = aResult;}

	/** Цель визита */
	@Comment("Цель визита")
	@Persist @Required
	public Long getVisitReason() {return theVisitReason;	}
	public void setVisitReason(Long aReason) {theVisitReason = aReason;	}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist @Required
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;	}

	 /** Диспансерный учет * */
    @Persist
    public Long getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(Long aVocDispanseryRegistration) {theDispRegistration = aVocDispanseryRegistration;}

	/** Дата следующего визита */
	@Comment("Дата следующего визита")
	@DateString @DoDateString @Persist
	public String getNextVisitDate() {
		return theNextVisitDate;
	}

	public void setNextVisitDate(String aNextVisitDate) {
		theNextVisitDate = aNextVisitDate;
	}

    /** Скорая помощь */
	@Comment("Скорая помощь")
	@Persist
	public Long getAmbulance() {return theAmbulance;}
	public void setAmbulance(Long aAmbulance) {theAmbulance = aAmbulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@Persist
	public Long getVisitOutcome() {return theVisitOutcome;}
	public void setVisitOutcome(Long aVisitOutcome) {theVisitOutcome = aVisitOutcome;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent @Required
	public String getDateStart() {return theDateStart;}
	public void setDateStart(String aNewProperty) {theDateStart = aNewProperty;}
	/** Дата следующего визита */
	private String theNextVisitDate;
	/** Дата начала */
	private String theDateStart;
	/** Исход визита */
	private Long theVisitOutcome;
	/** Скорая помощь */
	private Long theAmbulance;
    private Long theDispRegistration;
    /** Тип рабочего места обслуживания */
	private Long theWorkPlaceType;
	/** Цель визита */
	private Long theVisitReason;
	/** Результат визита */
	private Long theVisitResult;
	/** Острота диагноза заключительного */
	private Long theConcludingActuity;
	/** Заключительный диагноз */
	private Long theConcludingTrauma;
	/** Заключительный диагноз по МКБ-10 */
	private Long theConcludingMkb;
	/** Заключительный диагноз */
	private String theConcludingDiagnos;
	
	/** Дата направления */
	@Comment("Дата направления")
	@Persist @DoDateString @DateString
	public String getOrderDate() {return theOrderDate;}
	public void setOrderDate(String aOrderDate) {theOrderDate = aOrderDate;}

	/** Дата направления */
	private String theOrderDate;
	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}
	/** Внешний направитель (ЛПУ) */
	private Long theOrderLpu;	
	/** МКБ */
	@Comment("МКБ")
	public String getMkb() {return theMkb;}
	public void setMkb(String aMkb) {theMkb = aMkb;}

	/** МКБ */
	private String theMkb;
	
	/** Доп.код мкб */
	@Comment("Доп.код мкб")
	public String getMkbAdc() {return theMkbAdc;}
	public void setMkbAdc(String aMkbAdc) {theMkbAdc = aMkbAdc;}

	/** Доп.код мкб */
	private String theMkbAdc;
	
	/** Карта скорой помощи */
	@Comment("Карта скорой помощи")
	public String getAmbulanceCard() {return theAmbulanceCard;}
	public void setAmbulanceCard(String aAmbulanceCard) {theAmbulanceCard = aAmbulanceCard;}

	/** Карта скорой помощи */
	private String theAmbulanceCard;
	
	/** Номер зуба */
	@Comment("Номер зуба")
	public String getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(String aOrderNumber) {theOrderNumber = aOrderNumber;}

	/** Номер зуба */
	private String theOrderNumber;
	
	/** Кол-во услуг */
	@Comment("Кол-во услуг")
	public Integer getMedServiceAmount() {
		return theMedServiceAmount;
	}

	public void setMedServiceAmount(Integer aMedServiceAmount) {
		theMedServiceAmount = aMedServiceAmount;
	}

	/** Кол-во услуг */
	private Integer theMedServiceAmount;
	
	/** услуга */
	@Comment("услуга")
	public Long getMedService() {
		return theMedService;
	}

	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}

	/** услуга */
	private Long theMedService;
	
	/** УЕТ */
	@Comment("УЕТ")
	public String getUetT() {
		return theUetT;
	}

	public void setUetT(String aUetT) {
		theUetT = aUetT;
	}

	/** УЕТ */
	private String theUetT;
	
	/** Время получения вызова СМП */
	@Comment("Время получения вызова СМП")
	@TimeString @DoTimeString
	public String getCallReceiveTime() {return theCallReceiveTime;}
	public void setCallReceiveTime(String aCallReceiveTime) {theCallReceiveTime = aCallReceiveTime;}
	/** Время получения вызова СМП */
	private String theCallReceiveTime;

	/** Время прибытия бригады СМП */
	@Comment("Время прибытия бригады СМП")
	@TimeString @DoTimeString
	public String getArrivalTime() {return theArrivalTime;}
	public void setArrivalTime(String aArrivalTime) {theArrivalTime = aArrivalTime;}
	/** Время прибытия бригады СМП */
	private String theArrivalTime;

	/** Примечания пациента при записи */
	@Comment("Примечания пациента при записи")
	public String getPatientComment() {return thePatientComment;}
	public void setPatientComment(String aPatientComment) {thePatientComment = aPatientComment;}
	/** Примечания пациента при записи */
	private String thePatientComment ;

	
}

