package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.MedicalManipulation;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.MedicalManipulationCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

import javax.persistence.EntityListeners;

/**
 * Created by Milamesher on 31.08.2017.
 * Медицинская манипуляция
 */
@Comment("Манипуляция")
@EntityForm
@EntityFormPersistance(clazz= MedicalManipulation.class)
@WebTrail(comment = "Манипуляция", nameProperties= "id", view="entityParentView-stac_bandage.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Bandage")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedicalManipulationCreateInterceptor.class)
)
@EntityListeners(DeleteListener.class)
@Setter
public class MedicalManipulationForm extends IdEntityForm {
    /** Дата начала */
    @Comment("Дата начала")
    @Persist
    @Required
    @MaxDateCurrent
    @DateString
    @DoDateString
    public String getStartDate() {return startDate;}

    /** Время начала */
    @Comment("Время начала")
    @Persist @Required
    @TimeString
    @DoTimeString
    public String getStartTime() {return startTime;}

    /** Отделение */
    @Comment("Отделение")
    @Persist @Required
    public Long getDepartment() {return department;}

    /** Анестизия */
    @Comment("Анестезия")
    public Long getAnesthesia() {return anesthesia;}

    /** Случай медицинского обслуживания */
    @Comment("Случай медицинского обслуживания")
    @Persist
    public Long getMedCase() {return medCase;}

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return patient;}

    /** Лечебное учреждение */
    @Comment("Лечебное учреждение")
    @Persist
    public Long getLpu() {return lpu;}

    /** Кол-во  анастезии */
    @Comment("Кол-во  анастезии")
    @IntegerString
    @DoIntegerString
    public String getAnesthesiaAmount() {return anesthesiaAmount;}

    /** Дата окончания */
    @Comment("Дата окончания")
    @DateString
    @DoDateString
    @Persist  @MaxDateCurrent
    public String getEndDate() {return endDate;}


    /** Анестезиолог */
    @Comment("Анестезиолог")
    public Long getAnaesthetist() {return anaesthetist;}

    /** Показания */
    @Comment("Показания ")
    @Persist
    public Long getAspect() {return aspect;}

    /** Медсестра */
    @Comment("Медсестра")
    @Persist
    public Long getNurse() {return nurse;}

    /** Хирург */
    @Comment("Хирург")
    @Persist
    public Long getSurgeon() {return surgeon;}

    /** Эпикриз */
    @Comment("Эпикриз")
    @Persist
    public String getEpicrisis() {return epicrisis;}


    /** Описание */
    @Comment("Описание")
    @Persist
    public String getText() {return text;}

    /** Время окончания */
    @Comment("Время окончания")
    @Persist
    @TimeString
    @DoTimeString
    public String getEndTime() {return endTime;}

    /** Информация */
    @Comment("Информация")
    @Persist
    public String getInformation() {return information;}

    /** Период */
    @Comment("Период")
    @Persist
    public String getPeriod() {return period;}

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    @Persist
    public String getPatientInfo() {return patientInfo;}

    /** Номер в журнале */
    @Comment("Номер в журнале")
    @Persist
    public String getNumberInJournal() {return numberInJournal;}

    /** Информация о хир.операции */
    private String information;

    /** Хирург */
    private Long surgeon;

    /** Номер в журнале */
    private String numberInJournal;

    /** Информация о пациенте */
    private String patientInfo;
    /** Период */
    private String period;

    /** Кол-во  анастезии */
    private String anesthesiaAmount;
    /** Анестезиолог */
    private Long anaesthetist;
    /** Дата манипуляции по */
    private String endDate;
    /** Эпикриз */
    private String epicrisis;
    /** Описание */
    private String text;
    /** Медсестра */
    private Long nurse;
    /** Время окончания */
    private String endTime;

    /** Лечебное учреждение */
    private Long lpu;
    /** Пациент */
    private Long patient;
    /** Случай медицинского обслуживания */
    private Long medCase;
    /** Анестезия */
    private Long anesthesia;
    /** Отделение */
    private Long department;
    /** Время манипуляции */
    private String startTime;
    /** Дата манипуляции */
    private String startDate;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist @DoDateString @DateString
    public String getCreateDate() {return createDate;}

    /** Пользователь, создавший запись */
    @Comment("Пользователь, создавший запись")
    @Persist
    public String getCreateUsername() {return createUsername;}

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DoDateString @DateString
    public String getEditDate() {return editDate;}

    /** Пользователь, последний изменявший запись */
    @Comment("Пользователь, последний изменявший запись")
    @Persist
    public String getEditUsername() {return editUsername;}

    /** Поток обслуживания */
    @Comment("Поток обслуживания")
    @Persist @Required
    public Long getServiceStream() {return serviceStream;}

    /** Поток обслуживания */
    private Long serviceStream;

    /** Пользователь, последний изменявший запись */
    private String editUsername;
    /** Дата редактирования */
    private String editDate;
    /** Пользователь, создавший запись */
    private String createUsername;
    /** Дата создания */
    private String createDate;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @Persist @Required
    public Long getMedService() {return medService;}


    /** Мед. услуга */
    private Long medService;
    /** Дата печати */
    @Comment("Дата печати")
    @Persist @DateString @DoDateString
    public String getPrintDate() {return printDate;}

    /** Дата печати */
    private String printDate;

    /** Время печати */
    @Comment("Время печати")
    @Persist @TimeString @DoTimeString
    public String getPrintTime() {return printTime;}

    /** Время печати */
    private String printTime;

    /** Пользователь, посл. распечат. документ */
    @Comment("Пользователь, посл. распечат. документ")
    @Persist
    public String getPrintUsername() {return printUsername;}

    /** Пользователь, посл. распечат. документ */
    private String printUsername;

    /** Проводилась анестезия */
    @Comment("Проводилась анестезия")
    public Long getIsAnesthesia() {return isAnesthesia;}

    /** Проводилась анестезия */
    private Long isAnesthesia;

    /** Анестезия вида */
    @Comment("Анестезия вида")
    public Long getAnesthesiaType() {return anesthesiaType;}

    /** Анестезия вида */
    private Long anesthesiaType;

    /** Анестезия услуга */
    @Comment("Анестезия услуга")
    public Long getAnesthesiaService() {
        return anesthesiaService;
    }

    /** Анестезия услуга */
    private Long anesthesiaService;

    /** Длительность */
    @Comment("Длительность")
    public Integer getAnesthesiaDuration() {
        return anesthesiaDuration;
    }

    /** Длительность */
    private Integer anesthesiaDuration;
    /** Показания */
    private Long aspect;
}