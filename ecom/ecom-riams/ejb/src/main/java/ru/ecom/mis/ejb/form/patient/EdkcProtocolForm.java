package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
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
@WebTrail(comment = "Протокол ЕДКЦ", nameProperties = "id"
        , view = "entityParentView-edkcProtocol.do"
        ,list = "entityParentList-edkcProtocol.do"
)
@Parent(property = "obsSheet", parentForm = ObservationSheetForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet")
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


    /** Специалист */
    @Comment("Специалист")
    @Persist
    public Long getSpecialist() {return theSpecialist;}
    public void setSpecialist(Long aSpecialist) {theSpecialist = aSpecialist;}

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
    public String getUsername() {return theUsername;}
    public void setUsername(String aUsername) {theUsername = aUsername;}

    /** Время регистрации */
    @Comment("Время регистрации")
    @Persist
    @TimeString
    @DoTimeString
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
    /** Специалист */
    private Long theSpecialist;
    private String theRecord ;
    private String theDate ;
    private long theId ;

    /** Дата регистрации */
    private String theDateRegistration ;


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
}
