package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.poly.PolyclinicMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.poly.VisitMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Форма случая медицинского обслуживания
 *
 * @author STkacheva
 */
@EntityForm
@EntityFormPersistance(clazz = MedCase.class)
@Comment("Случай медицинского обслуживания")
@WebTrail(comment = "СМО", nameProperties = "info", view = "entitySubclassView-mis_medCase.do")
@Parent(property = "patient", parentForm = PatientForm.class)
@Subclasses({ServiceMedCaseForm.class, PolyclinicMedCaseForm.class
        , VisitMedCaseForm.class, DepartmentMedCaseForm.class, AdmissionMedCaseForm.class
        , TicketMedCaseForm.class
        , ExtHospitalMedCaseForm.class, HospitalMedCaseForm.class
})
@EntityFormSecurityPrefix("/Policy/Mis/MedCase")
@Setter
public class MedCaseForm extends IdEntityForm {

    /**
     * Поток обслуживания
     */
    @Comment("Поток обслуживания")
    @Persist
    public Long getServiceStream() {
        return serviceStream;
    }

    /**
     * Пациент
     */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {
        return patient;
    }

    /**
     * Дата начала
     */
    @Comment("Дата начала")
    @Persist
    @DateString
    @DoDateString
    public String getDateStart() {
        return dateStart;
    }

    /**
     * Родительский СМО
     */
    @Comment("Родительский СМО")
    @Persist
    public Long getParent() {
        return parent;
    }

    /**
     * Недействительность
     */
    @Comment("Недействительность")
    @Persist
    public Boolean getNoActuality() {
        return noActuality;
    }

    /**
     * Исполнитель
     */
    @Comment("Исполнитель")
    @Persist
    public Long getStartFunction() {
        return startFunction;
    }

    /**
     * ЛПУ - место исполнения
     */
    @Comment("ЛПУ - место исполнения ")
    @Persist
    public Long getLpu() {
        return lpu;
    }

    /**
     * Опьянение
     */
    @Comment("Опьянение")
    @Persist
    public Long getIntoxication() {
        return intoxication;
    }

    /**
     * Информация о пациенте
     */
    @Comment("Информация о пациенте")
    @Persist
    public String getPatientInfo() {
        return patientInfo;
    }

    /**
     * Информация о СМО
     */
    @Comment("Информация о СМО")
    @Persist
    public String getInfo() {
        return info;
    }

    /**
     * Оператор
     */
    @Comment("Оператор")
    @Persist
    public String getUsername() {
        return username;
    }

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    @DoDateString
    @DateString
    @Persist
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Представитель
     */
    @Comment("Представитель")
    @Persist
    public Long getKinsman() {
        return kinsman;
    }

    /**
     * Госпитализация (впервые, повторно)
     */
    @Comment("Госпитализация (впервые, повторно)")
    @Persist
    public Long getHospitalization() {
        return hospitalization;
    }

    /**
     * Условная единица трудоемкости
     */
    @Comment("Условная единица трудоемкости")
    @Persist
    public String getUet() {
        return uet;
    }

    /**
     * Распечатан?
     */
    @Comment("Распечатан?")
    @Persist
    public Long getIsPrint() {
        return isPrint;
    }

    /**
     * Диагноз?
     */
    @Comment("Диагноз?")
    @Persist
    public Long getIsDiagnosisCreate() {
        return isDiagnosisCreate;
    }

    /**
     * Создавать дневник
     */
    @Comment("Создавать дневник")
    @Persist
    public Long getIsDiaryCreate() {
        return isDiaryCreate;
    }

    /**
     * IsPrintInfo
     */
    @Comment("IsPrintInfo")
    @Persist
    public Boolean getIsPrintInfo() {
        return isPrintInfo;
    }

    /**
     * Экстренность
     */
    @Comment("Экстренность")
    @Persist
    public Boolean getEmergency() {
        return emergency;
    }

    /**
     * Дата печати
     */
    @Comment("Дата печати")
    @DateString
    @DoDateString
    public String getPrintDate() {
        return printDate;
    }

    /**
     * Время печати
     */
    @Comment("Время печати")
    @DoTimeString
    @TimeString
    public String getPrintTime() {
        return printTime;
    }

    /**
     * Беременность
     */
    @Comment("Беременность")
    @Persist
    public Long getPregnancy() {
        return pregnancy;
    }

    /**
     * Какая по счету беременность
     */
    @Comment("Какая по счету беременность")
    public Integer getPregnancyOrderNumber() {
        return pregnancyOrderNumber;
    }

    @Comment("Количество родов")
    public Integer getChildbirthAmount() {
        return childbirthAmount;
    }

    /**
     * Дата редактирования
     */
    @Comment("Дата редактирования")
    @Persist
    @DoDateString
    @DateString
    public String getEditDate() {
        return editDate;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return editUsername;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    private String editUsername;
    /**
     * Дата редактирования
     */
    private String editDate;
    /**
     * Количество родов
     */
    private Integer childbirthAmount;
    /**
     * Какая по счету беременность
     */
    private Integer pregnancyOrderNumber;
    /**
     * Беременность
     */
    private Long pregnancy;
    /**
     * Время печати
     */
    private String printTime;
    /**
     * Дата печати
     */
    private String printDate;
    /**
     * Экстренность
     */
    private Boolean emergency;
    /**
     * IsPrintInfo
     */
    private Boolean isPrintInfo;
    /**
     * Создавать дневник
     */
    private Long isDiaryCreate;
    /**
     * Диагноз?
     */
    private Long isDiagnosisCreate;
    /**
     * Распечатан?
     */
    private Long isPrint;
    /**
     * Условная единица трудоемкости
     */
    private String uet;
    /**
     * Госпитализация (впервые, повторно)
     */
    private Long hospitalization;
    /**
     * Представитель
     */
    private Long kinsman;
    /**
     * Дата создания
     */
    private String createDate;
    /**
     * Оператор
     */
    private String username;
    /**
     * Информация о СМО
     */
    private String info;
    /**
     * Информация о пациенте
     */
    private String patientInfo;
    /**
     * Опьянение
     */
    private Long intoxication;
    /**
     * ЛПУ - место исполнения
     */
    private Long lpu;
    /**
     * Исполнитель
     */
    private Long startFunction;
    /**
     * Недействительность
     */
    private boolean noActuality;
    /**
     * Родительский СМО
     */
    private Long parent;
    /**
     * Дата начала
     */
    private String dateStart;
    /**
     * Пациент
     */
    private Long patient;
    /**
     * Поток обслуживания
     */
    private Long serviceStream;

    /**
     * Закрыть
     */
    @Comment("Закрыть СПО")
    public Boolean getIsCloseSpo() {
        return isCloseSpo;
    }

    /**
     * Закрыть
     */
    private Boolean isCloseSpo;

    /**
     * Гостиничная услуга
     */
    @Comment("Гостиничная услуга")
    @Persist
    public Boolean getHotelServices() {
        return hotelServices;
    }

    /**
     * Гостиничная услуга
     */
    private Boolean hotelServices;

    /**
     * Дефект
     */
    @Comment("Дефект")
    @Persist
    public Long getMedCaseDefect() {
        return medCaseDefect;
    }

    /**
     * Дефект
     */
    private Long medCaseDefect;

    /**
     * Время создания
     */
    @Comment("Время создания")
    @Persist
    @DoTimeString
    @TimeString
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Время создания
     */
    private String createTime;

    /**
     * Время редактирования
     */
    @Comment("Время редактирования")
    @Persist
    @DoTimeString
    @TimeString
    public String getEditTime() {
        return editTime;
    }

    /**
     * Время редактирования
     */
    private String editTime;

    /**
     * Услуга оплачена
     */
    @Comment("Услуга оплачена")
    @Persist
    public Boolean getIsPaid() {
        return isPaid;
    }

    /**
     * Услуга оплачена
     */
    private Boolean isPaid;


    /**
     * Гарантийное письмо
     */
    @Comment("Гарантийное письмо")
    @Persist
    public Long getGuarantee() {
        return guarantee;
    }

    /**
     * Гарантийное письмо
     */
    private Long guarantee;
}

