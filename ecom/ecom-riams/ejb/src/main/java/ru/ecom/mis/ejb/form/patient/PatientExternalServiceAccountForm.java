package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class PatientExternalServiceAccountForm extends IdEntityForm {
    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return patient;}
    /** Пациент */
    private Long patient ;

    /** Идентификатор во внешней системе */
    @Comment("Идентификатор во внешней системе")
    @Persist
    public String getExternalCode() {return externalCode;}
    /** Идентификатор во внешней системе */
    private String externalCode ;

    /** Дата получения согласия на передачу данных */
    @Comment("Дата получения согласия на передачу данных")
    @DateString @DoDateString
    @Persist
    public String getDateStart() {return dateStart;}
    /** Дата получения согласия на передачу данных */
    private String dateStart ;

    /** Дата отзыва согласия на передачу данных */
    @Comment("Дата отзыва согласия на передачу данных")
    @DateString @DoDateString
    @Persist
    public String getDateTo() {return dateTo;}
    /** Дата отзыва согласия на передачу данных */
    private String dateTo ;

    /** Номер мобильного телефона */
    @Comment("Номер мобильного телефона")
    @Persist
    public String getPhoneNumber() {return phoneNumber;}
    /** Номер мобильного телефона */
    private String phoneNumber ;

    /** Адрес электронной почты */
    @Comment("Адрес электронной почты")
    @Persist
    public String getEmail() {return email;}
    /** Адрес электронной почты */
    private String email ;

    /** Создатель */
    @Comment("Создатель")
    @Persist
    public String getUsername() {return username;}
    /** Создатель */
    private String username ;

    /** Дата создания */
    @Comment("Дата создания")
    @DateString @DoDateString
    @Persist
    public String getCreateDate() {return createDate;}
    /** Дата создания */
    private String createDate ;

    /** Пользователь, редактировавший запись */
    @Comment("Пользователь, редактировавший запись")
    @Persist
    public String getEditUsername() {return editUsername;}
    /** Пользователь, редактировавший запись */
    private String editUsername ;

    /** Дата редактирования записи */
    @Comment("Дата редактирования записи")
    @DateString @DoDateString
    @Persist
    public String getEditDate() {return editDate;}
    /** Дата редактирования записи */
    private String editDate ;

    /** Аннулировать согласие */
    @Comment("Аннулировать согласие")
    public String getDisabled() {return disabled;}
    /** Аннулировать согласие */
    private String disabled ;

    /** Разрешить выгрузку всей истории болезни */
    @Comment("Разрешить выгрузку всей истории болезни")
    @Persist
    public Boolean getExportAllHistory() {return exportAllHistory;}
    /** Разрешить выгрузку всей истории болезни */
    private Boolean exportAllHistory ;

    /** Выгрзуить историю лечения повторно */
    @Comment("Выгрзуить историю лечения повторно")
    public String getSendHistoryAgain() {return sendHistoryAgain;}
    /** Выгрзуить историю лечения повторно */
    private String sendHistoryAgain ;


}


