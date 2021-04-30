package ru.ecom.poly.ejb.form;

import lombok.Setter;
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
@Setter
public class TicketForm extends IdEntityForm {

	/** КСГ */
	@Comment("КСГ")
	@Persist
	public Long getKsg() {return ksg;}

	/** Сопутствующие заболевания */
	@Comment("Сопутствующие заболевания")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocIdc10.class)
	public String getConcomitantDiseases() {return concomitantDiseases;}

    @Comment("Медицинская карта")
    @Persist @Required
    public Long getMedcard() {return medcard;}

    @Comment("Дата приема")
    @Required @Persist
    @DateString @DoDateString @MaxDateCurrent
    public String getDate() {return date;}

    @Comment("Время приема")
    @Required @Persist
    @TimeString @DoTimeString
    public String getTime() { return time; }

    @Comment("Вид оплаты")
    @Persist @Required
    public Long getVocPaymentType() {return vocPaymentType;}

    @Comment("Место обслуживания")
    @Persist @Required
    public Long getVocServicePlace() {return vocServicePlace;}


    @Comment("Цель посещения")
    @Persist @Required
    public Long getVocReason() {return vocReason;}

    @Comment("Результат обращения")
    @Persist
    public Long getVocVisitResult() {return vocVisitResult;}

    @Comment("Код мед. услуги (посещения, СМП, КЭС)  -  несколько")
    @Persist
    public Long getVocMedUsluga() {return vocMedUsluga;}

    @Comment("Характер заболевания")
    @Persist
    public Long getVocIllnesType() {return vocIllnesType;}

    @Comment("Первичность")
    @Persist
    public Long getDispRegistration() {return dispRegistration;}

    @Comment("Травма")
	@Persist
    public Long getVocTrauma() {return vocTrauma;}

    /** Статус документа нетрудоспособности*/
	@Comment("Статус документа нетрудоспособности")
	@Persist
	public Long getDisabilityDocumentStatus() {return disabilityDocumentStatus;}


    /** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@Persist
	public Long getDisabilityReason() {return disabilityReason;}

	
    /** @return Пациент **/
    @Persist
    @Comment("ФИО пациента")
    public String getPatientName() { return patientName; }

    
    @Comment("Статус пациента")
    @Persist
    /** @return Статус пациента **/
    public String getStatusName() { return statusName; }

    /** Информация по талону */
	@Comment("Информация по талону")
	@Persist
	public String getTicketInfo() {return ticketInfo;}

    @Comment("Метка")
    @Persist
    public Long getSpecialLabel() { return label; }

    /** Закрыть талон? */
	@Comment("Закрыть талон?")
	@Persist
	public Boolean getIsTicketClosed() {return isTicketClosed;}

    @Persist
    @Comment("Ранее зарегистрированный диагноз")
    public Long getPrevIdc10() {return previousDiagnosisIdc;}

    @Comment("Дата ранее зарегистрированного диагноза")
    @Persist @DateString @DoDateString
    public String getPrevIdc10Date(){return prevIdc10Date;}

    @Comment("Диагноз по МКБ10")
    @Mkb @Persist @Required
    public Long getIdc10() {return vocIdc10;}

	/** Рабочая функция исполнения */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunction() {	return workFunction;}

	/**Рабочая функция исполнения(Инфо) */
	@Comment("Рабочая функция исполнения(Инфо)")
	@Persist
	public String getWorkFunctionInfo() {return workFunctionInfo;}

	/** Справочник по ДТП*/
	@Comment("Справочник по ДТП")
	@Persist
	public Long getRoadTrafficInjury() {return roadTrafficInjury;}

	/** Первичность */
	@Comment("Первичность")
	@Persist 
	public Long getPrimary() {return primary;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return dateCreate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTimeCreate() {return timeCreate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getUsernameCreate() {return usernameCreate;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return kinsman;}

	@Comment("Обращение по поводу данного заболевания в текущем году (впервые, повторно)")
	@Persist @Required
	public Long getHospitalization() {return hospitalization;}

	/** Условная единица трудоемкости */
	@Comment("Условная единица трудоемкости")
	@Persist
	public String getUet() {return uet;}

	/** Направлен на стац. лечение */
	@Comment("Направлен на стац. лечение")
	@Persist
	public Boolean getDirectHospital() {return directHospital;}

	/** Разговор с родственником */
	@Comment("Разговор с родственником")
	@Persist
	public Boolean getTalk() {return talk;}

	/** Неотложная помощь */
	@Comment("Неотложная помощь")
	@Persist
	public Boolean getEmergency() {return emergency;}

	/** Мед.услуги */
	@Comment("Мед.услуги")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=MedService.class
			,valueProperty="medService_id",parentProperty="ticket_id",tableName="RenderedService")
	public String getMedServices() {return medServices;}

	/** Мед.услуги */
	private String medServices;
	/** Неотложная помощь */
	private Boolean emergency;
	/** Разговор с родственником */
	private Boolean talk;
	/** Направлен на стац. лечение */
	private Boolean directHospital;
	/** Условная единица трудоемкости */
	private String uet;
	private Long hospitalization;
	/** Представитель */
	private Long kinsman;
	/** Пользователь, создавший запись */
	private String usernameCreate;
	/** Время создания */
	private String timeCreate;
	/** Дата создания */
	private String dateCreate;
	/** Статус документа нетрудоспособности */
	private Long disabilityDocumentStatus;
	/** Причина нетрудоспособности */
	private Long disabilityReason;
	/** Закрыть талон? */
	private Boolean isTicketClosed;
    /** Метка **/
    private Long label;
    /** Статус пациента **/
    private String statusName;
    /** Пациент **/
    private String patientName;
    /** Травма */
    private Long vocTrauma;
    /** Первичность */
    private Long dispRegistration;
    /** Характер заболевания */
    private Long vocIllnesType;
    /** Код мед. услуги (посещения, СМП, КЭС) */
    private Long vocMedUsluga;
    /** Результат обращения */
    private Long vocVisitResult;
    /** Цель посещения */
    private Long vocReason;
    /**Место обслуживания */
    private Long vocServicePlace;
    /** Вид оплаты */
    private Long vocPaymentType;
    /** Дата выдачи/создания(?) талона */
    private String date;
    /** Медицинская карта */
    private Long medcard;
	/** Рабочая функция исполнения */
	private Long workFunction;
	/** Первичность */
	private Long primary;
	/** Рабочая функция исполнения(Инфо) */
	private String workFunctionInfo;
	/** Справочник по ДТП*/
	private Long roadTrafficInjury;
	/** КСГ */
	private Long ksg;
    /** Диагноз по МКБ10 */
    private Long vocIdc10;
	/** Сопутствующие заболевания */
	private String concomitantDiseases;
	/** Дата ранее зарегистрированного диагноза */
	private String prevIdc10Date;
	/** Дата ранее зарегистрированного диагноза MKБ10 */
	private Long previousDiagnosisIdc;
    /** Время приема **/
    private String time;
	/** Информация по талону */
	private String ticketInfo;
    
	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public Long getIllnesPrimary() {return illnesPrimary;}

	/** Диагноз */
	private Long illnesPrimary;

	/** СПО */
	@Comment("СПО")
	@Persist
	public Long getParent() {return parent;}

	/** СПО */
	private Long parent;
	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@Persist
	public Long getAmbulance() {return ambulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@Persist
	public Long getVisitOutcome() {return visitOutcome;}

	/** Исход визита */
	private Long visitOutcome;
	/** Бригада скорой помощи */
	private Long ambulance;

}
