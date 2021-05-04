package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.WfConsultation;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 14.09.2018.
 * Консультация специалиста
 */
@Comment("Консультация специалиста")
@EntityForm
@EntityFormPersistance(clazz= WfConsultation.class)
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@WebTrail(comment = "Консультация специалиста", nameProperties= "id", view="entityParentView-pres_wfConsultation.do")
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/ServicePrescription")
@Setter
public class WfConsultationForm extends IdEntityForm {
    /** Тип консультации */
    private Long vocConsultingType;
    /** Заключение */
    private Long diary;
    /** Пользователь - создатель */
    private String createUsername;
    /** Дата создания */
    private String createDate;
    /** Пользователь последний, изменявший запись */
    private String editUsername;
    /** Дата редактирования */
    private String editDate;
    /** Пользователь, передавший направление специалисту */
    private String transferUsername;
    /** Дата передачи специалисту */
    private String transferDate;
    /** Лист назначений */
    private Long prescriptionList;
    /** Специалист */
    private Long prescriptCabinet;
    /** Время создания */
    private String createTime;
    /** Время редактрования */
    private String editTime;
    /** Время передачи */
    private String transferTime;
    /** Дата выполнения */
    private String intakeDate;
    /** Время выполнения */
    private String intakeTime;
    /** Пользователь, который выполнил */
    private Long intakeSpecial;

    /** Тип консультации */
    @Comment("Тип консультации")
    @Persist @Required
    public Long getVocConsultingType() {return vocConsultingType; }

    /** Заключение */
    @Comment("Заключение")
    @Persist
    public Long getDiary() { return diary; }

    /** Пользователь - создатель */
    @Comment("Пользователь - создатель")
    @Persist
    public String getCreateUsername() { return createUsername; }

    /** Дата создания */
    @Comment("Дата создания")
    @Persist @DateString
    public String getCreateDate() { return createDate; }

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() { return editUsername; }

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DateString
    public String getEditDate() { return editDate; }

    /** Пользователь, передавший направление специалисту */
    @Comment("Пользователь, передавший направление специалисту")
    @Persist
    public String getTransferUsername() { return transferUsername; }

    /** Дата передачи специалисту */
    @Comment("Дата передачи специалисту")
    @Persist @DateString
    public String getTransferDate() { return transferDate; }

    /** Лист назначений */
    @Comment("Лист назначений")
    @Persist @DateString
    public Long getPrescriptionList() { return prescriptionList; }

    /** Кабинет назначения */
    @Comment("Кабинет назначения")
    @Persist @Required
    public Long getPrescriptCabinet() {
        return prescriptCabinet;
    }

    /** Время создания */
    @Comment("Время создания")
    @TimeString @DoTimeString @Persist
    public String getCreateTime() {return createTime;}

    /** Время редактрования */
    @Comment("Время редактрования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return editTime;}

    /** Время передачи */
    @Comment("Время передачи")
    @TimeString @DoTimeString @Persist
    public String getTransferTime() {return transferTime;}

    /** Дата выполнения */
    @Comment("Дата выполнения")
    @Persist @DateString
    public String getIntakeDate() { return intakeDate; }

    /** Время выполнения */
    @Comment("Время выполнения")
    @TimeString @DoTimeString @Persist
    public String getIntakeTime() {return intakeTime;}

    /** Пользователь, который выполнил */
    @Comment("Пользователь, который выполнил")
    @Persist
    public Long getIntakeSpecial() { return intakeSpecial; }
}