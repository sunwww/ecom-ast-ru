package ru.ecom.mis.ejb.form.prescription;

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
public class WfConsultationForm extends IdEntityForm {
    /** Тип консультации */
    private Long theVocConsultingType;
    /** Заключение */
    private Long theDiary;
    /** Пользователь - создатель */
    private String theCreateUsername;
    /** Дата создания */
    private String theCreateDate;
    /** Пользователь последний, изменявший запись */
    private String theEditUsername;
    /** Дата редактирования */
    private String theEditDate;
    /** Пользователь, передавший направление специалисту */
    private String theTransferUsername;
    /** Дата передачи специалисту */
    private String theTransferDate;
    /** Лист назначений */
    private Long thePrescriptionList;
    /** Специалист */
    private Long thePrescriptCabinet;
    /** Время создания */
    private String theCreateTime;
    /** Время редактрования */
    private String theEditTime;
    /** Время передачи */
    private String theTransferTime;
    /** Дата выполнения */
    private String theIntakeDate;
    /** Время выполнения */
    private String theIntakeTime;
    /** Пользователь, который выполнил */
    private Long theIntakeSpecial;

    /** Тип консультации */
    @Comment("Тип консультации")
    @Persist @Required
    public Long getVocConsultingType() {return theVocConsultingType; }
    public void setVocConsultingType(Long aVocConsultingType) { theVocConsultingType = aVocConsultingType; }

    /** Заключение */
    @Comment("Заключение")
    @Persist
    public Long getDiary() { return theDiary; }
    public void setDiary(Long aDiary) { theDiary = aDiary; }

    /** Пользователь - создатель */
    @Comment("Пользователь - создатель")
    @Persist
    public String getCreateUsername() { return theCreateUsername; }
    public void setCreateUsername(String aCreateUsername) { theCreateUsername = aCreateUsername; }

    /** Дата создания */
    @Comment("Дата создания")
    @Persist @DateString
    public String getCreateDate() { return theCreateDate; }
    public void setCreateDate(String aCreateDate) { theCreateDate = aCreateDate; }

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() { return theEditUsername; }
    public void setEditUsername(String aEditUsername) { theEditUsername = aEditUsername; }

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DateString
    public String getEditDate() { return theEditDate; }
    public void setEditDate(String aEditDate) { theEditDate = aEditDate; }

    /** Пользователь, передавший направление специалисту */
    @Comment("Пользователь, передавший направление специалисту")
    @Persist
    public String getTransferUsername() { return theTransferUsername; }
    public void setTransferUsername(String aTransferUsername) { theTransferUsername = aTransferUsername; }

    /** Дата передачи специалисту */
    @Comment("Дата передачи специалисту")
    @Persist @DateString
    public String getTransferDate() { return theTransferDate; }
    public void setTransferDate(String aTransferDate) { theTransferDate = aTransferDate; }

    /** Лист назначений */
    @Comment("Лист назначений")
    @Persist @DateString
    public Long getPrescriptionList() { return thePrescriptionList; }
    public void setPrescriptionList(Long aPrescriptionList) { thePrescriptionList = aPrescriptionList; }

    /** Кабинет назначения */
    @Comment("Кабинет назначения")
    @Persist @Required
    public Long getPrescriptCabinet() {
        return thePrescriptCabinet;
    }
    public void setPrescriptCabinet(Long aPrescriptCabinet) {
        thePrescriptCabinet = aPrescriptCabinet;
    }

    /** Время создания */
    @Comment("Время создания")
    @TimeString @DoTimeString @Persist
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}

    /** Время редактрования */
    @Comment("Время редактрования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}

    /** Время передачи */
    @Comment("Время передачи")
    @TimeString @DoTimeString @Persist
    public String getTransferTime() {return theTransferTime;}
    public void setTransferTime(String aTransferTime) {theTransferTime = aTransferTime;}

    /** Дата выполнения */
    @Comment("Дата выполнения")
    @Persist @DateString
    public String getIntakeDate() { return theIntakeDate; }
    public void setIntakeDate(String aIntakeDate) { theIntakeDate = aIntakeDate; }

    /** Время выполнения */
    @Comment("Время выполнения")
    @TimeString @DoTimeString @Persist
    public String getIntakeTime() {return theIntakeTime;}
    public void setIntakeTime(String aIntakeTime) {theIntakeTime = aIntakeTime;}

    /** Пользователь, который выполнил */
    @Comment("Пользователь, который выполнил")
    @Persist
    public Long getIntakeSpecial() { return theIntakeSpecial; }
    public void setIntakeSpecial(Long aIntakeSpecial) { theIntakeSpecial = aIntakeSpecial; }
}