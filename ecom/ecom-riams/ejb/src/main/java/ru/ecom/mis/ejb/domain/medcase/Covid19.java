package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocCT;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalizationResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNoMaybe;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
public class Covid19 extends BaseEntity {
    /**
     * Пациент
     */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {
        return patient;
    }

    private Patient patient;

    /**
     * СМО
     */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {
        return medCase;
    }

    private MedCase medCase;

    /**
     * Номер ИБ
     */
    private String cardNumber;

    /**
     * Место работы, должность
     */
    private String workPlace;

    /**
     * Дата появления клинических симптомов
     */
    private Date symptomsDate;

    /**
     * Диагноз
     */
    private String diagnosis;

    /**
     * Дата постановки диагноза
     */
    private Date diagnosisDate;

    /**
     * Дата исследования на COVID
     */
    private Date covidResearchDate;

    /**
     * МО, где проводилось ЛИ
     */
    private String labOrganization;

    /**
     * Результаты лабораторных исследования
     */
    private String labResult;

    /**
     * Номер анализа
     */
    private String labResultNumber;

    /**
     * Вакцинация пневмококком
     */
    @Comment("Вакцинация пневмококком")
    @OneToOne
    public VocYesNoMaybe getVacPnKok() {
        return vacPnKok;
    }

    private VocYesNoMaybe vacPnKok;

    /**
     * Вакцинация от гриппа
     */
    @Comment("Вакцинация от гриппа")
    @OneToOne
    public VocYesNoMaybe getVacFlu() {
        return vacFlu;
    }

    private VocYesNoMaybe vacFlu;

    /**
     * Беременность
     */
    private Boolean isPregnant;

    /**
     * Противовирусное лечение
     */
    @Comment("Противовирусное лечение")
    @OneToOne
    public VocYesNoMaybe getIsAntivirus() {
        return isAntivirus;
    }

    private VocYesNoMaybe isAntivirus;

    /**
     * Респираторная поддержка
     */
    @Comment("Респираторная поддержка")
    @OneToOne
    public VocYesNoMaybe getIsIvl() {
        return isIvl;
    }

    private VocYesNoMaybe isIvl;

    /**
     * Хронические заболевания бронхолегочной системы
     */
    private String soputBronho;

    /**
     * Хронические заболевания сердечно-сосудистой системы
     */
    private String soputHeart;

    /**
     * Хронические заболевания эндокринной системы
     */
    private String soputEndo;

    /**
     * Онкологические заболевания
     */
    private String soputOnko;

    /**
     * Болезнь, вызванная ВИЧ
     */
    private String soputSpid;

    /**
     * Туберкулез
     */
    private String soputTuber;

    /**
     * Иные
     */
    private String soputOther;

    /**
     * Уровень SpO2(%)(на момент поступления)
     */
    private String saturationLevel;

    /**
     * Контактные лица
     */
    @Comment("Контактные лица")
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    public List<Covid19Contact> getContactList() {
        return contactList;
    }

    private List<Covid19Contact> contactList;

    /**
     * Дата создания
     */
    private Date createDate;

    /**
     * Время создания
     */
    private Time createTime;

    /**
     * Создатель
     */
    private String createUsername;

    /**
     * Дата выгрузки на портал
     */
    private Date exportDate;

    /**
     * Время выгузки на портал
     */
    private Time exportTime;

    /**
     * Пользователь, выгузивший на портал
     */
    private String exportUsername;

    /**
     * Карта заменена на новую
     */
    private Boolean noActual;

    /**
     * Дата исхода
     */
    private Date ishodDate;

    /**
     * Результат исхода
     */
    @Comment("Результат исхода")
    @OneToOne
    public VocHospitalizationResult getHospResult() {
        return hospResult;
    }

    private VocHospitalizationResult hospResult;

    /**
     * Диагноз
     */
    @Comment("Диагноз")
    @OneToOne
    public VocIdc10 getMkb() {
        return mkb;
    }

    private VocIdc10 mkb;

    /**
     * Номер бригады СМП
     */
    private String brigadeNumber;

    /**
     * Эпид. номер
     */
    private String epidNumber;

    /**
     * Мед. работник
     */
    private Boolean isDoctor;

    @PrePersist
    void onPrePersist() {
        long createTime = System.currentTimeMillis();
        setCreateDate(new Date(createTime));
        setCreateTime(new Time(createTime));
    }

    /**
     * Дата первичной выгрузки
     */
    private Date exportFirstDate;

    /**
     * Время первичной выгрузки
     */
    private Time exportFirstTime;

    /**
     * Кто первичную выгрузил?
     */
    private String exportFirstUsername;

    /**
     * Дата повторной выгрузки
     */
    private Date exportDoubleDate;

    /**
     * Время повторной выгрузки
     */
    private Time exportDoubleTime;

    /**
     * Кто повторную выгрузил?
     */
    private String exportDoubleUsername;

    /**
     * Дата выписной выгрузки
     */
    private Date exportDischargeDate;

    /**
     * Время выписной выгрузки
     */
    private Time exportDischargeTime;

    /**
     * Кто при выписке выгрузил?
     */
    private String exportDischargeUsername;

    /**
     * Диагноз основной выписной
     */
    @Comment("Диагноз основной выписной")
    @OneToOne
    public VocIdc10 getMkbDischarge() {
        return mkbDischarge;
    }

    private VocIdc10 mkbDischarge;

    /**
     * КТ
     */
    @Comment("КТ")
    @OneToOne
    public VocCT getCt() {
        return ct;
    }

    private VocCT ct;

    /**
     * Дата проведения КТ
     */
    private Date dateCT;

    /**
     * Место проведения КТ
     */
    private String lpuCT;

    /**
     * Была ли вакцинация от COVID-19
     */
    private VocYesNo vaccinated;

    /**
     * Сделан ли первый компонент вакцины
     */
    private VocYesNo firstVaccineСomponent;

    /**
     * Сделан ли второй компонент вакцины
     */
    private VocYesNo secondVaccineСomponent;

    /**
     * Сделана ли ревакцинация
     */
    private VocYesNo reVaccineСomponent;

    /**
     * Дата первого компонента вакцины
     */
    private Date dateFirstVaccine;

    /**
     * Дата второго компонента вакцины
     */
    private Date dateSecondVaccine;

    /**
     * Дата ревакцинации
     */
    private Date dateReVaccine;

    /**
     * Была ли вакцинация от COVID-19
     */
    @Comment("Была ли вакцинация от COVID-19")
    @OneToOne
    public VocYesNo getVaccinated() {
        return vaccinated;
    }

    /**
     * Сделан ли первый компонент вакцины
     */
    @Comment("Сделан ли первый компонент вакцины")
    @OneToOne
    public VocYesNo getFirstVaccineСomponent() {
        return firstVaccineСomponent;
    }

    /**
     * Сделан ли первый компонент вакцины
     */
    @Comment("Сделан ли первый компонент вакцины")
    @OneToOne
    public VocYesNo getSecondVaccineСomponent() {
        return secondVaccineСomponent;
    }

    /**
     * Сделана ли ревакцинация
     */
    @Comment("Сделан ли первый компонент вакцины")
    @OneToOne
    public VocYesNo getReVaccineСomponent() {
        return reVaccineСomponent;
    }
}