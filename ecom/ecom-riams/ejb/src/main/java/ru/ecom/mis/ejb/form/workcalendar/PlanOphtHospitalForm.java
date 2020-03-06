package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.workcalendar.PlanOphtHospital;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.PlanOphtHospitalCreate;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 31.10.2019.
 */
@EntityForm
@EntityFormPersistance(clazz = PlanOphtHospital.class)
@Comment("Планирование введения ингибиторов ангиогенеза")
@Parent(property="patient", parentForm=PatientForm.class)
@WebTrail(comment = "Планирование введения ингибиторов ангиогенеза", nameProperties= "id"
        ,list="entityParentList-stac_planOphtHospital.do"
        , view="entityView-stac_planOphtHospital.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(PlanOphtHospitalCreate.class)
)
public class PlanOphtHospitalForm extends IdEntityForm {
    /** Отделение */
    @Comment("Отделение")
    @Persist
    @Required
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

    /** Телефон пациента */
    @Comment("Телефон пациента")
    @Persist @Required
    public String getPhone() {return thePhone;}
    public void setPhone(String aPhone) {thePhone = aPhone;}

    /** Пациент */
    @Comment("Пациент")
    @Persist @Required
    public Long getPatient() {return thePatient;}
    public void setPatient(Long aPatient) {thePatient = aPatient;}

    /** Примечание */
    @Comment("Примечание")
    @Persist
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}

    /** Дата ОКТ */
    @Comment("Дата ОКТ")
    @Persist @DateString
    @DoDateString
    @Required
    public String getDateOKT() {return theDateOKT;}
    public void setDateOKT(String aDateOKT) {theDateOKT = aDateOKT;}

    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    @Comment("Глаз, в который будут вводить ингибиторы ангиогенеза")
    @Persist @Required
    public Long getEye() {return theEye;}
    public void setEye(Long aEye) {theEye = aEye;}

    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getVisit() {return theVisit;}
    public void setVisit(Long aVisit) {theVisit = aVisit;}

    /** Дата создания */
    @Comment("Дата создания")
    @DateString @DoDateString @Persist
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString @DoDateString @Persist
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    /** Время редактрования */
    @Comment("Время редактрования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

    /** Фактическая госпитализация */
    @Comment("Фактическая госпитализация")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

    /** Рабочая функция */
    @Comment("Рабочая функция")
    @Persist
    public Long getWorkFunction() {return theWorkFunction;}
    public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

    /** Предполагаемая дата начала госпитализации */
    @Comment("Предполагаемая дата начала госпитализации")
    @Persist @DateString
    @DoDateString
    @Required
    public String getDateFrom() {return theDateFrom;}
    public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

    /** Отделение */
    private Long theDepartment;
    /** Телефон пациента */
    private String thePhone;
    /** Пациент */
    private Long thePatient;
    /** Примечание */
    private String theComment;
    /** Дата ОКТ */
    private String theDateOKT;
    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    private Long theEye;
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;
    /** Пользователь, который создал запись */
    private String theCreateUsername;
    /** Время редактрования */
    private String theEditTime;
    /** Время создания */
    private String theCreateTime;
    /** Дата редактирования */
    private String theEditDate;
    /** Дата создания */
    private String theCreateDate;
    /** СМО */
    private Long theVisit;
    /** Фактическая госпитализация */
    private Long theMedCase;
    /** Рабочая функция */
    private Long theWorkFunction;
    /** Предполагаемая дата начала госпитализации */
    private String theDateFrom;
}