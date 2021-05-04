package ru.ecom.poly.ejb.form.protocol;


import lombok.Setter;
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
@Setter
public class ProtocolForm extends IdEntityForm {
    /** Запрет на ручное редактирование */
	@Comment("Запрет на ручное редактирование")
	@Persist
	public Boolean getDisableEdit() {return disableEdit;}
	/** Запрет на ручное редактирование */
	private Boolean disableEdit;
	
    /** Общая информация о протоколе */
    @Persist
    @Comment("Общая информация о протоколе")
    public String getInfo() {    return info ;}

    /** Дата регистрации талона */
    @Persist @Required
    @Comment("Дата регистрации талона")
    @DateString @DoDateString @MaxDateCurrent
    public String getDateRegistration() {    return dateRegistration ;}

    /** Талон */
    //@Persist
    @Comment("Талон")
    public Long getTicket() {    return ticket ;}

    /** ИД */
    @Id
    @Comment("ИД")
    public long getId() {    return id ;}

    /** Дата */
    @Persist @DateString @DoDateString
    @Comment("Дата") 
    public String getDate() {    return date ;}

    /** Запись */
    @Persist @Required
    @Comment("Запись")
    public String getRecord() {    return record ;}

    /** Ключевые слова */
    @Persist
    @Comment("Ключевые слова")
    public String getKeyWord() {    return keyWord ;}

    /** Специалист */
	@Comment("Специалист")
	@Persist
	public Long getSpecialist() {return specialist;}

	/** Специалист информация */
	@Comment("Специалист информация")
	@Persist
	public String getSpecialistInfo() {return specialistInfo;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;}

	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist 
	@TimeString @DoTimeString
	public String getTimeRegistration() {return timeRegistration;}

    /** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTime() {return time;}

	/** Время создания */
	private String time;
	/** Время регистрации */
	private String timeRegistration;
	/** Пользователь */
	private String username;
	/** Специалист информация */
	private String specialistInfo;
	/** Специалист */
	private Long specialist;
    private String keyWord ;
    private String record ;
    private String date ;
    private long id ;
    private Long ticket ;

    /** Дата регистрации талона */
    private String dateRegistration ;
    /** Общая информация о протоколе */
    private String info ;
    /** Информация для журнала */
    
	@Comment("Информация для журнала")
	@Persist
	public String getJournalText() {return journalText;}

	/** Информация для журнала */
	private String journalText;

    /** Время редактирования */
    @Comment("Время редактирования")
    @Persist
    @TimeString @DoTimeString
    public String getEditTime() {
        return editTime;
    }
    /** Время редактирования */
    private String  editTime;

    /** Визит */
    @Comment("Визит")
    @Persist
    public Long getMedCase() {return medCase;}
    /** Визит */
    private Long medCase;
}
