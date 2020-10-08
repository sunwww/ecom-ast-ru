package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolPreCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolSaveInterceptor;
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
 * Created by Milamesher on 01.08.2019.
 */
@Comment("Протокол ЕДКЦ")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.Protocol.class)
public class EdkcProtocolForm extends IdEntityForm {
    /** Лист наблюдения */
    @Comment("Лист наблюдения")
    @Persist
    public Long getObsSheet() {return theObsSheet;}
    public void setObsSheet(Long aObsSheet) {theObsSheet = aObsSheet;}
    /** Лист наблюдения */
    private Long theObsSheet;

    /** Дата регистрации */
    @Persist @Required
    @Comment("Дата регистрации")
    @DateString
    @DoDateString
    @MaxDateCurrent
    public String getDateRegistration() {    return theDateRegistration ;}
    public void setDateRegistration(String aDateRegistration ) {  theDateRegistration = aDateRegistration ; }
    /** Дата регистрации */
    private String theDateRegistration ;

    /** Дата */
    @Persist @DateString @DoDateString
    @Comment("Дата")
    public String getDate() {    return theDate ;}
    public void setDate(String aDate ) {  theDate = aDate ; }
    /** Дата */
    private String theDate ;

    /** Запись */
    @Persist @Required
    @Comment("Запись")
    public String getRecord() {    return theRecord ;}
    public void setRecord(String aRecord ) {  theRecord = aRecord ; }
    /** Запись */
    private String theRecord ;

    /** Специалист */
    @Comment("Специалист")
    @Persist
    public Long getSpecialist() {return theSpecialist;}
    public void setSpecialist(Long aSpecialist) {theSpecialist = aSpecialist;}
    /** Специалист */
    private Long theSpecialist;

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
    public String getUsername() {return theUsername;}
    public void setUsername(String aUsername) {theUsername = aUsername;}
    /** Пользователь */
    private String theUsername;

    /** Время регистрации */
    @Comment("Время регистрации")
    @Persist @Required
    @TimeString
    @DoTimeString
    public String getTimeRegistration() {return theTimeRegistration;}
    public void setTimeRegistration(String aTimeRegistration) {theTimeRegistration = aTimeRegistration;}
    /** Время регистрации */
    private String theTimeRegistration;

    /** Время создания */
    @Comment("Время создания")
    @Persist @TimeString @DoTimeString
    public String getTime() {return theTime;}
    public void setTime(String aTime) {theTime = aTime;}
    /** Время создания */
    private String theTime;

    /** Дата редактирования*/
    @Persist @DateString @DoDateString
    @Comment("Дата редактирования")
    public String getEditDate() {    return theEditDate ;}
    public void setEditDate(String aEditDate ) {  theEditDate = aEditDate ; }
    /** Дата редактирования */
    private String  theEditDate;

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

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return theEditUsername;
    }

    public void setEditUsername(String aEditUsername) {
        theEditUsername = aEditUsername;
    }

    /** Пользователь последний, изменявший запись */
    private String theEditUsername;

    /** Шаблон, на основе которого создано заключение */
    @Comment("Шаблон, на основе которого создано заключение")
    @Persist
    public Long getTemplateProtocol() {return theTemplateProtocol;}
    public void setTemplateProtocol(Long aTemplateProtocol) {theTemplateProtocol = aTemplateProtocol;}
    /** Шаблон, на основе которого создано заключение */
    private Long theTemplateProtocol;

    /** Параметры шаблона */
    @Comment("Параметры шаблона")
    public String getParams() {return theParams;}
    public void setParams(String aParams) {theParams = aParams;}
    /** Параметры шаблона */
    private String theParams;

    /** Тип протокола */
    @Comment("Тип протокола")
    @Persist
    public Long getType() {return theType;}
    public void setType(Long aType) {theType = aType;}
    /** Тип протокола */
    private Long theType;

    /** ИД */
    @Id
    @Comment("ИД")
    public long getId() {    return theId ;}
    public void setId(long aId ) {  theId = aId ; }
    private long theId ;

    /** Дата печати */
    @Comment("Дата печати")
    @Persist @DateString @DoDateString
    public String getPrintDate() {return thePrintDate;}
    public void setPrintDate(String aPrintDate) {thePrintDate = aPrintDate;}

    /** Время печати */
    @Comment("Время печати")
    @Persist @DoTimeString @TimeString
    public String getPrintTime() {return thePrintTime;}
    public void setPrintTime(String aPrintTime) {thePrintTime = aPrintTime;}

    /** Время печати */
    private String thePrintTime;
    /** Дата печати */
    private String thePrintDate;
}
