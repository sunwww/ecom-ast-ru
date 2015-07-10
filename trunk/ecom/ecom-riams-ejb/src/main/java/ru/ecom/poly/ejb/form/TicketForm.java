package ru.ecom.poly.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.ecom.poly.ejb.form.interceptors.TicketSaveInterceptor;
import ru.ecom.poly.ejb.form.interceptors.TicketViewInterceptor;
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
 * Талон
 * User: Yoda Date: 11.01.2007 Time: 14:51:43
 */

@EntityForm
@EntityFormPersistance(clazz = Ticket.class)
@Comment("Талон")
@Parent(property = "medcard", parentForm = MedcardForm.class,orderBy="date")
@WebTrail(comment = "Талон", nameProperties = "ticketInfo"
	, view = "entityView-poly_ticket.do"
	, shortView = "entityShortView-poly_ticket.do"
	, list = "entityParentList-poly_ticket.do"
	, shortList = "entityParentShortList-poly_old_ticket.do" 
)
@EntityFormSecurityPrefix("/Policy/Poly/Ticket")
@ASaveInterceptors(
        @AEntityFormInterceptor(TicketSaveInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(TicketSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(TicketViewInterceptor.class)
)
public class TicketForm extends IdEntityForm {

	/** Даты визитов */
	@Comment("Даты визитов")
	public String getOtherTicketDates() {return theOtherTicketDates;}
	public void setOtherTicketDates(String aOtherTicketDates) {theOtherTicketDates = aOtherTicketDates;}
	/** Даты визитов */
	private String theOtherTicketDates;

	/** Дата другового визита */
	@Comment("Дата другового визита")
	@DoDateString @DateString
	public String getOtherTicketDate() {return theOtherTicketDate;}
	public void setOtherTicketDate(String aOtherTicketDate) {theOtherTicketDate = aOtherTicketDate;}
	/** Дата другового визита */
	private String theOtherTicketDate;

	/** КСГ */
	@Comment("КСГ")
	@Persist
	public Long getKsg() {return theKsg;}
	public void setKsg(Long aKsg) {theKsg = aKsg;}

	/** Сопутствующие заболевания */
	@Comment("Сопутствующие заболевания")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocIdc10.class)
	public String getConcomitantDiseases() {return theConcomitantDiseases;}
	public void setConcomitantDiseases(String aConcomitantDiseases) {theConcomitantDiseases = aConcomitantDiseases;}

    @Comment("Медицинская карта")
    @Persist @Required
    public Long getMedcard() {return theMedcard;}
    public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}

    @Comment("Дата приема")
    @Required @Persist
    @DateString @DoDateString @MaxDateCurrent
    public String getDate() {return theDate;}
    public void setDate(String aDate) {theDate = aDate;}

    @Comment("Время приема")
    @Required @Persist
    @TimeString @DoTimeString
    public String getTime() { return theTime; }
    public void setTime(String aTime) { theTime = aTime; }

    @Comment("Вид оплаты")
    @Persist @Required
    public Long getVocPaymentType() {return theVocPaymentType;}
    public void setVocPaymentType(Long aVocPaymentType) {theVocPaymentType = aVocPaymentType;}

    @Comment("Место обслуживания")
    @Persist @Required
    public Long getVocServicePlace() {return theVocServicePlace;}
    public void setVocServicePlace(Long aVocServicePlace) {theVocServicePlace = aVocServicePlace;}


    @Comment("Цель посещения")
    @Persist @Required
    public Long getVocReason() {return theVocReason;}
    public void setVocReason(Long aVocReason) {theVocReason = aVocReason;}

    @Comment("Результат обращения")
    @Persist
    public Long getVocVisitResult() {return theVocVisitResult;}
    public void setVocVisitResult(Long aVocVisitResult) {theVocVisitResult = aVocVisitResult;}

    @Comment("Код мед. услуги (посещения, СМП, КЭС)  -  несколько")
    @Persist
    public Long getVocMedUsluga() {return theVocMedUsluga;}
    public void setVocMedUsluga(Long aVocMedUsluga) {theVocMedUsluga = aVocMedUsluga;}

    @Comment("Характер заболевания")
    @Persist
    public Long getVocIllnesType() {return theVocIllnesType;}
    public void setVocIllnesType(Long aVocIllnesType) {theVocIllnesType = aVocIllnesType;}

    @Comment("Первичность")
    @Persist
    public Long getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(Long aVocDispanseryRegistration) {theDispRegistration = aVocDispanseryRegistration;}

    @Comment("Травма") @Persist
    public Long getVocTrauma() {return theVocTrauma;}
    public void setVocTrauma(Long aVocTrauma) {theVocTrauma = aVocTrauma;}
    
    /** Статус документа нетрудоспособности*/
	@Comment("Статус документа нетрудоспособности")
	@Persist
	public Long getDisabilityDocumentStatus() {return theDisabilityDocumentStatus;}
	public void setDisabilityDocumentStatus(Long aDisabilityDocumentStatus) {theDisabilityDocumentStatus = aDisabilityDocumentStatus;}


    /** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@Persist
	public Long getDisabilityReason() {return theDisabilityReason;}
	public void setDisabilityReason(Long aDisabilityReason) {theDisabilityReason = aDisabilityReason;}

	
    /** @return Пациент **/
    @Persist
    @Comment("ФИО пациента")
    public String getPatientName() { return thePatientName; }
    public void setPatientName(String aPatientName) { thePatientName = aPatientName; }

    
    @Comment("Статус пациента")
    @Persist
    /** @return Статус пациента **/
    public String getStatusName() { return theStatusName; }
    public void setStatusName(String aStatusName) { theStatusName = aStatusName; }

    /** Информация по талону */
	@Comment("Информация по талону")
	@Persist
	public String getTicketInfo() {return theTicketInfo;}
	public void setTicketInfo(String aTicketInfo) {theTicketInfo = aTicketInfo;}

    @Comment("Метка")
    @Persist
    public Long getSpecialLabel() { return theLabel; }
    public void setSpecialLabel(Long aLabel) { theLabel = aLabel; }
    
    /** Закрыть талон? */
	@Comment("Закрыть талон?")
	@Persist
	public Boolean getIsTicketClosed() {return theIsTicketClosed;}
	public void setIsTicketClosed(Boolean aIsTicketClosed) {theIsTicketClosed = aIsTicketClosed;}

    @Persist
    @Comment("Ранее зарегистрированный диагноз")
    public Long getPrevIdc10() {return thePreviousDiagnosisIdc;}
    public void setPrevIdc10(Long idc10) {thePreviousDiagnosisIdc = idc10;}

    @Comment("Дата ранее зарегистрированного диагноза")
    @Persist @DateString @DoDateString
    public String getPrevIdc10Date(){return thePrevIdc10Date;}
    public void setPrevIdc10Date(String aPrevDate){thePrevIdc10Date = aPrevDate;}

    @Comment("Диагноз по МКБ10")
    @Mkb @Persist @Required
    public Long getIdc10() {return theVocIdc10;}
    public void setIdc10(Long idc10) {theVocIdc10 = idc10;}

	/** Рабочая функция исполнения */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunction() {	return theWorkFunction;}
	public void setWorkFunction(Long aNewProperty) {theWorkFunction = aNewProperty;}
	
	/**Рабочая функция исполнения(Инфо) */
	@Comment("Рабочая функция исполнения(Инфо)")
	@Persist
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aNewProperty) {theWorkFunctionInfo = aNewProperty;}
	
	/** Справочник по ДТП*/
	@Comment("Справочник по ДТП")
	@Persist
	public Long  getRoadTrafficInjury() {return theRoadTrafficInjury;}
	public void setRoadTrafficInjury(Long aNewProperty) {theRoadTrafficInjury = aNewProperty;}
	
	/** Первичность */
	@Comment("Первичность")
	@Persist 
	public Long getPrimary() {return thePrimary;}
	public void setPrimary(Long aNewProperty) {thePrimary = aNewProperty;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return theDateCreate;}
	public void setDateCreate(String aDateCreate) {theDateCreate = aDateCreate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTimeCreate() {return theTimeCreate;}
	public void setTimeCreate(String aTimeCreate) {theTimeCreate = aTimeCreate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getUsernameCreate() {return theUsernameCreate;}
	public void setUsernameCreate(String aUsernameCreate) {theUsernameCreate = aUsernameCreate;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return theKinsman;}
	public void setKinsman(Long aKinsman) {theKinsman = aKinsman;}
	
	@Comment("Обращение по поводу данного заболевания в текущем году (впервые, повторно)")
	@Persist @Required
	public Long getHospitalization() {return theHospitalization;}
	public void setHospitalization(Long aHospitalization) {theHospitalization = aHospitalization;}
	
	/** Условная единица трудоемкости */
	@Comment("Условная единица трудоемкости")
	@Persist
	public String getUet() {return theUet;}
	public void setUet(String aUet) {theUet = aUet;}

	/** Направлен на стац. лечение */
	@Comment("Направлен на стац. лечение")
	@Persist
	public Boolean getDirectHospital() {return theDirectHospital;}
	public void setDirectHospital(Boolean aDirectHospital) {theDirectHospital = aDirectHospital;}

	/** Разговор с родственником */
	@Comment("Разговор с родственником")
	@Persist
	public Boolean getTalk() {return theTalk;}
	public void setTalk(Boolean aTalk) {theTalk = aTalk;}

	/** Неотложная помощь */
	@Comment("Неотложная помощь")
	@Persist
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aEmergency) {theEmergency = aEmergency;}

	/** Мед.услуги */
	@Comment("Мед.услуги")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=MedService.class
			,valueProperty="medService_id",parentProperty="ticket_id",tableName="RenderedService")
	public String getMedServices() {return theMedServices;}
	public void setMedServices(String aMedServices) {theMedServices = aMedServices;}

	/** Мед.услуги */
	private String theMedServices;
	/** Неотложная помощь */
	private Boolean theEmergency;
	/** Разговор с родственником */
	private Boolean theTalk;
	/** Направлен на стац. лечение */
	private Boolean theDirectHospital;
	/** Условная единица трудоемкости */
	private String theUet;
	private Long theHospitalization;
	/** Представитель */
	private Long theKinsman;
	/** Пользователь, создавший запись */
	private String theUsernameCreate;
	/** Время создания */
	private String theTimeCreate;
	/** Дата создания */
	private String theDateCreate;
	/** Статус документа нетрудоспособности */
	private Long theDisabilityDocumentStatus;
	/** Причина нетрудоспособности */
	private Long theDisabilityReason;
	/** Закрыть талон? */
	private Boolean theIsTicketClosed;
    /** Метка **/
    private Long theLabel;
    /** Статус пациента **/
    private String theStatusName;
    /** Пациент **/
    private String thePatientName;
    /** Травма */
    private Long theVocTrauma;
    /** Первичность */
    private Long theDispRegistration;
    /** Характер заболевания */
    private Long theVocIllnesType;
    /** Код мед. услуги (посещения, СМП, КЭС) */
    private Long theVocMedUsluga;
    /** Результат обращения */
    private Long theVocVisitResult;
    /** Цель посещения */
    private Long theVocReason;
    /**Место обслуживания */
    private Long theVocServicePlace;
    /** Вид оплаты */
    private Long theVocPaymentType;
    /** Дата выдачи/создания(?) талона */
    private String theDate;
    /** Медицинская карта */
    private Long theMedcard;
	/** Рабочая функция исполнения */
	private Long theWorkFunction;
	/** Первичность */
	private Long thePrimary;
	/** Рабочая функция исполнения(Инфо) */
	private String theWorkFunctionInfo;
	/** Справочник по ДТП*/
	private Long theRoadTrafficInjury;
	/** КСГ */
	private Long theKsg;
    /** Диагноз по МКБ10 */
    private Long theVocIdc10;
	/** Сопутствующие заболевания */
	private String theConcomitantDiseases;
	/** Дата ранее зарегистрированного диагноза */
	private String thePrevIdc10Date;
	/** Дата ранее зарегистрированного диагноза MKБ10 */
	private Long thePreviousDiagnosisIdc;
    /** Время приема **/
    private String theTime;
	/** Информация по талону */
	private String theTicketInfo;
    
	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public Long getIllnesPrimary() {return theIllnesPrimary;}
	public void setIllnesPrimary(Long aIllnesPrimary) {theIllnesPrimary = aIllnesPrimary;}

	/** Диагноз */
	private Long theIllnesPrimary;

	/** СПО */
	@Comment("СПО")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}

	/** СПО */
	private Long theParent;
	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@Persist
	public Long getAmbulance() {return theAmbulance;}
	public void setAmbulance(Long aAmbulance) {theAmbulance = aAmbulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@Persist
	public Long getVisitOutcome() {return theVisitOutcome;}
	public void setVisitOutcome(Long aVisitOutcome) {theVisitOutcome = aVisitOutcome;}

	/** Исход визита */
	private Long theVisitOutcome;
	/** Бригада скорой помощи */
	private Long theAmbulance;

}
