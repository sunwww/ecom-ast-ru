package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.PatientExternalServiceAccount;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Created by vtsybulin on 23.05.2017.
 */
@EntityForm
@EntityFormPersistance(clazz = PatientExternalServiceAccount.class)
@Parent(property = "patient", parentForm = PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/PatientExternalServiceAccount")
@Comment("Учетная запись пациента")
@WebTrail(comment = "Учетная запись пациента", nameProperties = {"externalCode"}
        , view = "entityView-mis_patientExternalServiceAccount.do"
       )
public class PatientExternalServiceAccountForm extends IdEntityForm {
    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return thePatient;}
    public void setPatient(Long aPatient) {thePatient = aPatient;}
    /** Пациент */
    private Long thePatient ;

    /** Идентификатор во внешней системе */
    @Comment("Идентификатор во внешней системе")
    @Persist
    public String getExternalCode() {return theExternalCode;}
    public void setExternalCode(String aExternalCode) {theExternalCode = aExternalCode;}
    /** Идентификатор во внешней системе */
    private String theExternalCode ;

    /** Дата получения согласия на передачу данных */
    @Comment("Дата получения согласия на передачу данных")
    @DateString @DoDateString
    @Persist
    public String getDateStart() {return theDateStart;}
    public void setDateStart(String aDateStart) {theDateStart = aDateStart;}
    /** Дата получения согласия на передачу данных */
    private String theDateStart ;

    /** Дата отзыва согласия на передачу данных */
    @Comment("Дата отзыва согласия на передачу данных")
    @DateString @DoDateString
    @Persist
    public String getDateTo() {return theDateTo;}
    public void setDateTo(String aDateTo) {theDateTo = aDateTo;}
    /** Дата отзыва согласия на передачу данных */
    private String theDateTo ;

    /** Номер мобильного телефона */
    @Comment("Номер мобильного телефона")
    @Persist
    public String getPhoneNumber() {return thePhoneNumber;}
    public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
    /** Номер мобильного телефона */
    private String thePhoneNumber ;

    /** Адрес электронной почты */
    @Comment("Адрес электронной почты")
    @Persist
    public String getEmail() {return theEmail;}
    public void setEmail(String aEmail) {theEmail = aEmail;}
    /** Адрес электронной почты */
    private String theEmail ;

    /** Создатель */
    @Comment("Создатель")
    @Persist
    public String getUsername() {return theUsername;}
    public void setUsername(String aUsername) {theUsername = aUsername;}
    /** Создатель */
    private String theUsername ;

    /** Дата создания */
    @Comment("Дата создания")
    @DateString @DoDateString
    @Persist
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private String theCreateDate ;

    /** Пользователь, редактировавший запись */
    @Comment("Пользователь, редактировавший запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, редактировавший запись */
    private String theEditUsername ;

    /** Дата редактирования записи */
    @Comment("Дата редактирования записи")
    @DateString @DoDateString
    @Persist
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования записи */
    private String theEditDate ;

    /** Аннулировать согласие */
    @Comment("Аннулировать согласие")
    public String getDisabled() {return theDisabled;}
    public void setDisabled(String aDisabled) {theDisabled = aDisabled;}
    /** Аннулировать согласие */
    private String theDisabled ;

    /** Разрешить выгрузку всей истории болезни */
    @Comment("Разрешить выгрузку всей истории болезни")
    @Persist
    public Boolean getExportAllHistory() {return theExportAllHistory;}
    public void setExportAllHistory(Boolean aExportAllHistory) {theExportAllHistory = aExportAllHistory;}
    /** Разрешить выгрузку всей истории болезни */
    private Boolean theExportAllHistory ;

    /** Выгрзуить историю лечения повторно */
    @Comment("Выгрзуить историю лечения повторно")
    public String getSendHistoryAgain() {return theSendHistoryAgain;}
    public void setSendHistoryAgain(String aSendHistoryAgain) {theSendHistoryAgain = aSendHistoryAgain;}
    /** Выгрзуить историю лечения повторно */
    private String theSendHistoryAgain ;


}


