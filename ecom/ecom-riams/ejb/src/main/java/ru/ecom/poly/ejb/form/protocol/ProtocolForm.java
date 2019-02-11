package ru.ecom.poly.ejb.form.protocol;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.poly.ejb.form.interceptors.ProtocolPreCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.ProtocolSaveInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 07.02.2007
 * Time: 15:58:40
 * To change this template use File | Settings | File Templates.
 */
@Comment("Заключение")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.Protocol.class)
@WebTrail(comment = "Заключение", nameProperties = "info", view = "entityParentView-poly_protocol.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Protocol")
@ASaveInterceptors(
        @AEntityFormInterceptor(ProtocolSaveInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(ProtocolSaveInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ProtocolPreCreateInterceptor.class)
)
public class ProtocolForm extends IdEntityForm {
    /** Запрет на ручное редактирование */
	@Comment("Запрет на ручное редактирование")
	@Persist
	public Boolean getDisableEdit() {return theDisableEdit;}
	public void setDisableEdit(Boolean aDisableEdit) {theDisableEdit = aDisableEdit;}
	/** Запрет на ручное редактирование */
	private Boolean theDisableEdit;
	
    /** Общая информация о протоколе */
    @Persist
    @Comment("Общая информация о протоколе")
    public String getInfo() {    return theInfo ;}
    public void setInfo(String aInfo ) {  theInfo = aInfo ; }

    /** Дата регистрации талона */
    @Persist @Required
    @Comment("Дата регистрации талона")
    @DateString @DoDateString @MaxDateCurrent
    public String getDateRegistration() {    return theDateRegistration ;}
    public void setDateRegistration(String aDateRegistration ) {  theDateRegistration = aDateRegistration ; }

    /** Талон */
    //@Persist
    @Comment("Талон")
    public Long getTicket() {    return theTicket ;}
    public void setTicket(Long aTicket ) {  theTicket = aTicket ; }

    /** ИД */
    @Id
    @Comment("ИД")
    public long getId() {    return theId ;}
    public void setId(long aId ) {  theId = aId ; }

    /** Дата */
    @Persist @DateString @DoDateString
    @Comment("Дата") 
    public String getDate() {    return theDate ;}
    public void setDate(String aDate ) {  theDate = aDate ; }

    /** Запись */
    @Persist @Required
    @Comment("Запись")
    public String getRecord() {    return theRecord ;}
    public void setRecord(String aRecord ) {  theRecord = aRecord ; }

    /** Ключевые слова */
    @Persist
    @Comment("Ключевые слова")
    public String getKeyWord() {    return theKeyWord ;}
    public void setKeyWord(String aKeyWord ) {  theKeyWord = aKeyWord ; }


    /** Специалист */
	@Comment("Специалист")
	@Persist
	public Long getSpecialist() {return theSpecialist;}
	public void setSpecialist(Long aSpecialist) {theSpecialist = aSpecialist;}

	/** Специалист информация */
	@Comment("Специалист информация")
	@Persist
	public String getSpecialistInfo() {return theSpecialistInfo;}
	public void setSpecialistInfo(String aSpecialistInfo) {theSpecialistInfo = aSpecialistInfo;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	
	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist 
	@TimeString @DoTimeString
	public String getTimeRegistration() {return theTimeRegistration;}
	public void setTimeRegistration(String aTimeRegistration) {theTimeRegistration = aTimeRegistration;}

    /** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTime() {return theTime;}
	public void setTime(String aTime) {theTime = aTime;}

	/** Время создания */
	private String theTime;
	/** Время регистрации */
	private String theTimeRegistration;
	/** Пользователь */
	private String theUsername;
	/** Специалист информация */
	private String theSpecialistInfo;
	/** Специалист */
	private Long theSpecialist;
    private String theKeyWord ;
    private String theRecord ;
    private String theDate ;
    private long theId ;
    private Long theTicket ;

    /** Дата регистрации талона */
    private String theDateRegistration ;
    /** Общая информация о протоколе */
    private String theInfo ;
    /** Информация для журнала */
    
	@Comment("Информация для журнала")
	@Persist
	public String getJournalText() {return theJournalText;}
	public void setJournalText(String aJournalText) {theJournalText = aJournalText;}

	/** Информация для журнала */
	private String theJournalText;

    /** Время редактирования */
    @Comment("Время редактирования")
    @Persist
    @TimeString @DoTimeString
    public String getEditTime() {
        return theEditTime;
    }
    public void setEditTime(String  aEditTime) {
        theEditTime = aEditTime;
    }
    /** Время редактирования */
    private String  theEditTime;

    /** Визит */
    @Comment("Визит")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    /** Визит */
    private Long theMedCase;
}
