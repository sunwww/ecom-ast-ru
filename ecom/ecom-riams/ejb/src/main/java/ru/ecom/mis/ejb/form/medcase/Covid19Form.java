package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Covid19;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

import java.sql.Date;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = Covid19.class)
@Comment("Карта коронавируса")
@WebTrail(comment = "Карта коронавируса", list = "entityParentList-smo_covid19.do"
        , nameProperties = "cardNumber", view = "entityParentView-smo_covid19.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Covid19")
@Parent(parentForm = HospitalMedCaseForm.class, property = "medCase")
@Setter
public class Covid19Form extends IdEntityForm {
    /**
     * Пациент
     */
    @Comment("Пациент")
    @Persist
    @Required
    public Long getPatient() {
        return patient;
    }

    private Long patient;

    /**
     * СМО
     */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {
        return medCase;
    }

    private Long medCase;

    /**
     * Номер ИБ
     */
    @Comment("Номер ИБ")
    @Persist
    public String getCardNumber() {
        return cardNumber;
    }

    private String cardNumber;

    /**
     * Дата появления клинических симптомов
     */
    @Comment("Дата появления клинических симптомов")
    @Persist
    @DateString
    @DoDateString
    @Required
    public String getSymptomsDate() {
        return symptomsDate;
    }

    private String symptomsDate;

    /**
     * Дата постановки диагноза
     */
    @Comment("Дата постановки диагноза")
    @Persist
    @DateString
    @DoDateString
    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    private String diagnosisDate;

    /**
     * Дата исследования на COVID
     */
    @Comment("Дата исследования на COVID")
    @Persist
    @DateString
    @DoDateString
    public String getCovidResearchDate() {
        return covidResearchDate;
    }

    private String covidResearchDate;

    /**
     * МО, где проводилось ЛИ
     */
    @Comment("МО, где проводилось ЛИ")
    @Persist
    public String getLabOrganization() {
        return labOrganization;
    }

    private String labOrganization;

    /**
     * Результаты лабораторных исследования
     */
    @Comment("Результаты лабораторных исследования")
    @Persist
    public String getLabResult() {
        return labResult;
    }

    private String labResult;

    /**
     * Номер анализа
     */
    @Comment("Номер анализа")
    @Persist
    public String getLabResultNumber() {
        return labResultNumber;
    }

    private String labResultNumber;

    /**
     * Вакцинация от гриппа
     */
    @Comment("Вакцинация от гриппа")
    @Persist
    public Long getVacFlu() {
        return vacFlu;
    }

    private Long vacFlu;

    /**
     * Беременность
     */
    @Comment("Беременность")
    @Persist
    public Boolean getIsPregnant() {
        return isPregnant;
    }

    private Boolean isPregnant;

    /**
     * Противовирусное лечение
     */
    @Comment("Противовирусное лечение")
    @Persist
    public Long getIsAntivirus() {
        return isAntivirus;
    }

    private Long isAntivirus;

    /**
     * Респираторная поддержка
     */
    @Comment("Респираторная поддержка")
    @Persist
    public Long getIsIvl() {
        return isIvl;
    }

    private Long isIvl;

    /**
     * Хронические заболевания бронхолегочной системы
     */
    @Comment("Хронические заболевания бронхолегочной системы")
    @Persist
    public String getSoputBronho() {
        return soputBronho;
    }

    private String soputBronho;

    /**
     * Хронические заболевания сердечно-сосудистой системы
     */
    @Comment("Хронические заболевания сердечно-сосудистой системы")
    @Persist
    public String getSoputHeart() {
        return soputHeart;
    }

    private String soputHeart;

    /**
     * Хронические заболевания эндокринной системы
     */
    @Comment("Хронические заболевания эндокринной системы")
    @Persist
    public String getSoputEndo() {
        return soputEndo;
    }

    private String soputEndo;

    /**
     * Онкологические заболевания
     */
    @Comment("Онкологические заболевания")
    @Persist
    public String getSoputOnko() {
        return soputOnko;
    }

    private String soputOnko;

    /**
     * Болезнь, вызванная ВИЧ
     */
    @Comment("Болезнь, вызванная ВИЧ")
    @Persist
    public String getSoputSpid() {
        return soputSpid;
    }

    private String soputSpid;

    /**
     * Туберкулез
     */
    @Comment("Туберкулез")
    @Persist
    public String getSoputTuber() {
        return soputTuber;
    }

    private String soputTuber;

    /**
     * Иные
     */
    @Comment("Иные ")
    @Persist
    public String getSoputOther() {
        return soputOther;
    }

    private String soputOther;

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    @Persist
    @DateString
    @DoDateString
    public String getCreateDate() {
        return createDate;
    }

    private String createDate;

    /**
     * Время создания
     */
    @Comment("Время создания")
    @Persist
    @TimeString
    @DoTimeString
    public String getCreateTime() {
        return createTime;
    }

    private String createTime;

    /**
     * Создатель
     */
    @Comment("Создатель")
    @Persist
    public String getCreateUsername() {
        return createUsername;
    }

    private String createUsername;

    /**
     * Дата выгрузки на портал
     */
    @Comment("Дата выгрузки на портал")
    @Persist
    @DateString
    @DoDateString
    public String getExportDate() {
        return exportDate;
    }

    private String exportDate;

    /**
     * Время выгузки на портал
     */
    @Comment("Время выгузки на портал")
    @Persist
    @TimeString
    @DoTimeString
    public String getExportTime() {
        return exportTime;
    }

    private String exportTime;

    /**
     * Пользователь, выгузивший на портал
     */
    @Comment("Пользователь, выгузивший на портал")
    @Persist
    public String getExportUsername() {
        return exportUsername;
    }

    private String exportUsername;

    /**
     * Карта заменена на новую
     */
    @Comment("Карта заменена на новую")
    @Persist
    public Boolean getNoActual() {
        return noActual;
    }

    private Boolean noActual;

    /**
     * Дата исхода
     */
    @Comment("Дата исхода")
    @Persist
    @DateString
    @DoDateString
    public String getIshodDate() {
        return ishodDate;
    }

    private String ishodDate;

    /**
     * Результат исхода
     */
    @Comment("Результат исхода")
    @Persist
    public Long getHospResult() {
        return hospResult;
    }

    private Long hospResult;

    /**
     * Место работы, должность
     */
    @Comment("Место работы, должность")
    @Persist
    @Required
    public String getWorkPlace() {
        return workPlace;
    }

    private String workPlace;

    /**
     * Диагноз
     */
    @Comment("Диагноз")
    @Persist
    @Required
    public Long getMkb() {
        return mkb;
    }

    private Long mkb;

    /**
     * Номер бригады СМП
     */
    @Comment("Номер бригады СМП")
    @Persist
    public String getBrigadeNumber() {
        return brigadeNumber;
    }

    private String brigadeNumber;

    /**
     * Эпид. номер
     */
    @Comment("Эпид. номер")
    @Persist
    public String getEpidNumber() {
        return epidNumber;
    }

    private String epidNumber;

    /**
     * Форма контакта
     */
    @Comment("Форма контакта")
    public Covid19ContactForm getContactForm() {
        return contactForm;
    }

    private Covid19ContactForm contactForm = new Covid19ContactForm();

    /**
     * Мед. работник
     */
    @Comment("Мед. работник")
    @Persist
    public Boolean getIsDoctor() {
        return isDoctor;
    }

    private Boolean isDoctor;

    /**
     * Дата первичной выгрузки
     */
    @Comment("Дата первичной выгрузки")
    @DateString
    @DoDateString
    @Persist
    public String getExportFirstDate() {
        return exportFirstDate;
    }

    /**
     * Дата первичной выгрузки
     */
    private String exportFirstDate;

    /**
     * Время первичной выгрузки
     */
    @Comment("Время первичной выгрузки")
    @TimeString
    @DoTimeString
    @Persist
    public String getExportFirstTime() {
        return exportFirstTime;
    }

    /**
     * Время первичной выгрузки
     */
    private String exportFirstTime;

    /**
     * Кто первичную выгрузил?
     */
    @Comment("Кто первичную выгрузил?")
    @Persist
    public String getExportFirstUsername() {
        return exportFirstUsername;
    }

    /**
     * Кто первичную выгрузил?
     */
    private String exportFirstUsername;

    /**
     * Дата повторной выгрузки
     */
    @Comment("Дата повторной выгрузки")
    @DateString
    @DoDateString
    @Persist
    public String getExportDoubleDate() {
        return exportDoubleDate;
    }

    /**
     * Дата повторной выгрузки
     */
    private String exportDoubleDate;

    /**
     * Время повторной выгрузки
     */
    @Comment("Время повторной выгрузки")
    @TimeString
    @DoTimeString
    @Persist
    public String getExportDoubleTime() {
        return exportDoubleTime;
    }

    /**
     * Время повторной выгрузки
     */
    private String exportDoubleTime;

    /**
     * Кто повторную выгрузил?
     */
    @Comment("Кто повторную выгрузил?")
    @Persist
    public String getExportDoubleUsername() {
        return exportDoubleUsername;
    }

    /**
     * Кто повторную выгрузил?
     */
    private String exportDoubleUsername;

    /**
     * Дата выписной выгрузки
     */
    @Comment("Дата выписной выгрузки")
    @DateString
    @DoDateString
    @Persist
    public String getExportDischargeDate() {
        return exportDischargeDate;
    }

    /**
     * Дата выписной выгрузки
     */
    private String exportDischargeDate;

    /**
     * Время выписной выгрузки
     */
    @Comment("Время выписной выгрузки")
    @TimeString
    @DoTimeString
    @Persist
    public String getExportDischargeTime() {
        return exportDischargeTime;
    }

    /**
     * Время выписной выгрузки
     */
    private String exportDischargeTime;

    /**
     * Кто при выписке выгрузил?
     */
    @Comment("Кто при выписке выгрузил?")
    @Persist
    public String getExportDischargeUsername() {
        return exportDischargeUsername;
    }

    /**
     * Кто при выписке выгрузил?
     */
    private String exportDischargeUsername;

    /**
     * Диагноз основной выписной
     */
    @Comment("Диагноз основной выписной")
    @Persist
    public Long getMkbDischarge() {
        return mkbDischarge;
    }

    private Long mkbDischarge;

    /**
     * Уровень SpO2(%)(на момент поступления)
     */
    @Comment("Уровень SpO2(%)(на момент поступления)")
    @Persist
    public String getSaturationLevel() {
        return saturationLevel;
    }

    private String saturationLevel;

    /**
     * КТ
     */
    @Comment("КТ")
    @Persist
    public Long getCt() {
        return ct;
    }

    private Long ct;

    /**
     * Дата проведения КТ
     */
    @Comment("Дата проведения КТ")
    @DateString
    @DoDateString
    @Persist
    public String getDateCT() {
        return dateCT;
    }

    private String dateCT;

    /**
     * Место проведения КТ
     */
    @Comment("Место проведения КТ")
    @Persist
    public String getLpuCT() {
        return lpuCT;
    }

    /**
     * Место проведения КТ
     */
    private String lpuCT;

    /**
     * Была ли вакцинация от COVID-19
     */
    private Long vaccinated;

    /**
     * Сделан ли первый компонент вакцины
     */
    private Long firstVaccineСomponent;

    /**
     * Сделан ли второй компонент вакцины
     */
    private Long secondVaccineСomponent;

    /**
     * Сделана ли ревакцинация
     */
    private Long reVaccineСomponent;

    /**
     * Дата первого компонента вакцины
     */
    private String dateFirstVaccine;

    /**
     * Дата второго компонента вакцины
     */
    private String dateSecondVaccine;

    /**
     * Дата ревакцинации
     */
    private String dateReVaccine;


    /**
     * Вакцина от COVID-19
     */
    private Long covVaccine;

    /**
     * Была ли вакцинация от COVID-19
     */
    @Comment("Была ли вакцинация от COVID-19")
    @Required
    @Persist
    public Long getVaccinated() {
        return vaccinated;
    }

    /**
     * Сделан ли первый компонент вакцины
     */
    @Comment("Сделан ли первый компонент вакцины")
    @Persist
    public Long getFirstVaccineСomponent() {
        return firstVaccineСomponent;
    }

    /**
     * Сделан ли второй компонент вакцины
     */
    @Comment("Сделан ли второй компонент вакцины")
    @Persist
    public Long getSecondVaccineСomponent() {
        return secondVaccineСomponent;
    }

    /**
     * Сделана ли ревакцинация
     */
    @Comment("Сделана ли ревакцинация")
    @Persist
    public Long getReVaccineСomponent() {
        return reVaccineСomponent;
    }

    @Comment("Дата первого компонента вакцины")
    @DateString
    @DoDateString
    @Persist
    public String getDateFirstVaccine() {
        return dateFirstVaccine;
    }

    @Comment("Дата второго компонента вакцины")
    @DateString
    @DoDateString
    @Persist
    public String getDateSecondVaccine() {
        return dateSecondVaccine;
    }

    @Comment("Дата ревакцинации")
    @DateString
    @DoDateString
    @Persist
    public String getDateReVaccine() {
        return dateReVaccine;
    }

    /**
     * Вакцина от COVID-19
     */
    @Comment("Вакцина от COVID-19")
    @Persist
    public Long getCovVaccine() {
        return covVaccine;
    }
}
