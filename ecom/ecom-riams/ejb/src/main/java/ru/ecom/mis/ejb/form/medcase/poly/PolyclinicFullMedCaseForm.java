package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

//import ru.nuzmsh.commons.formpersistence.annotation.Persist;

@Comment("Случай поликлинического обслуживания")
@EntityForm
@EntityFormPersistance(clazz= PolyclinicMedCase.class)
@WebTrail(comment = "Случай поликлинического обслуживания", nameProperties= "id"
, view="entityParentView-smo_short_spo.do",shortList="entityParentList-smo_short_spo.do?short=Short",list="entityParentList-smo_short_spo.do")
@Parent(property="medcard", parentForm= MedcardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Spo")
public class PolyclinicFullMedCaseForm extends PolyclinicMedCaseForm {
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	private Long theServiceStream ;
	/** Код МКБ-10 */
	@Comment("Код МКБ-10")
	@Persist @Mkb  @Required
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Кто завершил */
	@Comment("Кто завершил")
	@Persist
	public Long getFinishFunction() {return theFinishFunction;}
	public void setFinishFunction(Long aFinishWorker) {	theFinishFunction = aFinishWorker;	}

	/** Владелец */
	@Comment("Владелец")
	@Persist @Required
	public Long getOwnerFunction() {return theOwnerFunction;}
	public void setOwnerFunction(Long aOwner) {theOwnerFunction = aOwner;}


	
	/** Владелец */
	private Long theOwnerFunction;
	/** Кто завершил */
	private Long theFinishFunction;
	/** Код МКБ-10 */
	private Long theIdc10;
	
	/** Дата окончания */
	private String theDateFinish;
	
	/** Даты визитов */
	@Comment("Даты визитов")
	public String getOtherTicketDates() {return theOtherTicketDates;}
	public void setOtherTicketDates(String aOtherTicketDates) {theOtherTicketDates = aOtherTicketDates;}
	/** Даты визитов */
	private String theOtherTicketDates;

	/** Дата другого визита */
	@Comment("Дата другого визита")
	@DoDateString @DateString
	public String getOtherTicketDate() {return theOtherTicketDate;}
	public void setOtherTicketDate(String aOtherTicketDate) {theOtherTicketDate = aOtherTicketDate;}
	/** Дата другого визита */
	private String theOtherTicketDate;

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@TimeString @DoTimeString
	public String getTimeExecute() {return theTimeExecute;	}
	public void setTimeExecute(String aNewProperty) {theTimeExecute = aNewProperty;}

	/** Время исполнения */
	private String theTimeExecute;
	
	/** Инфо по полису */
	@Comment("Инфо по полису")
	public String getInfoByPolicy() {return theInfoByPolicy;}
	public void setInfoByPolicy(String aInfoByPolicy) {theInfoByPolicy = aInfoByPolicy;}

	/** Инфо по полису */
	private String theInfoByPolicy;
	
	

    /** Разговор с родственником */
	@Comment("Разговор с родственником")
	
	public Boolean getIsTalk() {return theIsTalk;}
	public void setIsTalk(Boolean aIsTalk) {theIsTalk = aIsTalk;}

	
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
	 @Required
	public Long getVisitResult() {return theVisitResult;	}
	public void setVisitResult(Long aResult) {theVisitResult = aResult;}

	/** Цель визита */
	@Comment("Цель визита")
	 @Required
	public Long getVisitReason() {return theVisitReason;	}
	public void setVisitReason(Long aReason) {theVisitReason = aReason;	}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	 @Required
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;	}

	 /** Диспансерный учет * */
    
    public Long getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(Long aVocDispanseryRegistration) {theDispRegistration = aVocDispanseryRegistration;}

    /** Скорая помощь */
	@Comment("Скорая помощь")
	
	public Long getAmbulance() {return theAmbulance;}
	public void setAmbulance(Long aAmbulance) {theAmbulance = aAmbulance;}

	/** Исход визита */
	@Comment("Исход визита")
	
	public Long getVisitOutcome() {return theVisitOutcome;}
	public void setVisitOutcome(Long aVisitOutcome) {theVisitOutcome = aVisitOutcome;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent @Required
	public String getDateStart() {return theDateStart;}
	public void setDateStart(String aNewProperty) {theDateStart = aNewProperty;}
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
	
	/** Заключительный диагноз */
	private String theConcludingDiagnos;
	
	
	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	
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
	@Comment("Медицинская карта")
    
    public Long getMedcard() {return theMedcard;}
    public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}
    /** Медицинская карта */
    private Long theMedcard;
}
