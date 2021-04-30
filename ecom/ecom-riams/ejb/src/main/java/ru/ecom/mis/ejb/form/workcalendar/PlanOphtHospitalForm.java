package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
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
@Setter
public class PlanOphtHospitalForm extends IdEntityForm {
    /** Отделение */
    @Comment("Отделение")
    @Persist
    @Required
    public Long getDepartment() {return department;}

    /** Телефон пациента */
    @Comment("Телефон пациента")
    @Persist @Required
    public String getPhone() {return phone;}

    /** Пациент */
    @Comment("Пациент")
    @Persist @Required
    public Long getPatient() {return patient;}

    /** Примечание */
    @Comment("Примечание")
    @Persist
    public String getComment() {return comment;}

    /** Дата ОКТ */
    @Comment("Дата ОКТ")
    @Persist @DateString
    @DoDateString
    @Required
    public String getDateOKT() {return dateOKT;}

    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    @Comment("Глаз, в который будут вводить ингибиторы ангиогенеза")
    @Persist @Required
    public Long getEye() {return eye;}

    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getVisit() {return visit;}

    /** Дата создания */
    @Comment("Дата создания")
    @DateString @DoDateString @Persist
    public String getCreateDate() {return createDate;}

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString @DoDateString @Persist
    public String getEditDate() {return editDate;}

    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return createTime;}
    /** Время редактрования */
    @Comment("Время редактрования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return editTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return editUsername;}

    /** Фактическая госпитализация */
    @Comment("Фактическая госпитализация")
    @Persist
    public Long getMedCase() {return medCase;}

    /** Рабочая функция */
    @Comment("Рабочая функция")
    @Persist
    public Long getWorkFunction() {return workFunction;}

    /** Предполагаемая дата начала госпитализации */
    @Comment("Предполагаемая дата начала госпитализации")
    @Persist @DateString
    @DoDateString
    public String getDateFrom() {return dateFrom;}

    /** Отделение */
    private Long department;
    /** Телефон пациента */
    private String phone;
    /** Пациент */
    private Long patient;
    /** Примечание */
    private String comment;
    /** Дата ОКТ */
    private String dateOKT;
    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    private Long eye;
    /** Пользователь, который последний редактировал запись */
    private String editUsername;
    /** Пользователь, который создал запись */
    private String createUsername;
    /** Время редактрования */
    private String editTime;
    /** Время создания */
    private String createTime;
    /** Дата редактирования */
    private String editDate;
    /** Дата создания */
    private String createDate;
    /** СМО */
    private Long visit;
    /** Фактическая госпитализация */
    private Long medCase;
    /** Рабочая функция */
    private Long workFunction;
    /** Предполагаемая дата начала госпитализации */
    private String dateFrom;
}