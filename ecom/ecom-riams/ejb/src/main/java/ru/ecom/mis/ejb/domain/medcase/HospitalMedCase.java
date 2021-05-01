package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcFrm;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychHospitalReason;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Time;

/**
 * Стационарный случай медицинского обслуживания
 */
@Comment("Стационарный случай медицинского обслуживания")
@Entity
@AIndexes({
        @AIndex(properties = "statisticStub", table = "MedCase")
        , @AIndex(properties = "result", table = "MedCase")
})
@Getter
@Setter
public class HospitalMedCase extends LongMedCase {

    /**
     * Время перевода
     */
    private Time transferTime;
    /**
     * Дата перевода
     */
    private Date transferDate;

    /**
     * Дефекты догоспитального этапа
     */
    @Comment("Дефекты догоспитального этапа")
    @OneToOne
    public VocPreAdmissionDefect getPreAdmissionDefect() {
        return preAdmissionDefect;
    }

    /**
     * Время заболевания до госпитализации
     */
    @Comment("Время заболевания до госпитализации")
    @OneToOne
    public VocPreAdmissionTime getPreAdmissionTime() {
        return preAdmissionTime;
    }

    /**
     * Исход госпитализации
     */
    @Comment("Исход госпитализации")
    @OneToOne
    public VocHospitalizationOutcome getOutcome() {
        return outcome;
    }

    /**
     * Результат госпитализации
     */
    @Comment("Результат госпитализации")
    @OneToOne
    public VocHospitalizationResult getResult() {
        return result;
    }

    /**
     * Кем доставлен
     */
    @Comment("Кем доставлен")
    @OneToOne
    public OmcFrm getOrderType() {
        return orderType;
    }

    /**
     * Отказ от госпитализации
     */
    @Comment("Отказ от госпитализации")
    @OneToOne
    public VocDeniedHospitalizating getDeniedHospitalizating() {
        return deniedHospitalizating;
    }

    /**
     * Стат. карта
     */
    @Comment("Стат. карта")
    @OneToOne
    public StatisticStubExist getStatisticStub() {
        return statisticStub;
    }

    /**
     * Профиль коек
     */
    @Comment("Профиль коек")
    @OneToOne
    @Deprecated //unused
    public VocBedType getBedType() {
        return bedType;
    }

    /**
     * Рабочая функция направителя
     */
    @Comment("Рабочая функция направителя")
    @OneToOne
    public WorkFunction getOrderWorkFunction() {
        return orderWorkFunction;
    }

    /**
     * Провизорность
     */
    @Comment("Провизорность")
    @Deprecated
    public Boolean getProvisional() {
        return provisional;
    }

    /**
     * Перевод в другое ЛПУ
     */
    @Comment("Перевод в другое ЛПУ")
    @OneToOne
    public MisLpu getMoveToAnotherLPU() {
        return moveToAnotherLPU;
    }

    /**
     * Тип текущего стационара
     */
    @Comment("Тип текущего стационара")
    @OneToOne
    public VocHospType getHospType() {
        return hospType;
    }

    /**
     * Выписной эпикриз
     */
    @Comment("Выписной эпикриз")
    @Column(length = 30000)
    public String getDischargeEpicrisis() {
        return dischargeEpicrisis;
    }

    /**
     * Поступление в данный стационар
     */
    @Comment("Поступление в данный стационар")
    @OneToOne
    public VocHospitalization getAdmissionInHospital() {
        return admissionInHospital;
    }

    /**
     * Откуда поступил
     */
    @Comment("Откуда поступил")
    @OneToOne
    public VocHospitalizationOutcome getWhereAdmission() {
        return whereAdmission;
    }

    /**
     * Откуда поступил
     */
    private VocHospitalizationOutcome whereAdmission;
    /**
     * Поступление в данный стационар
     */
    private VocHospitalization admissionInHospital;
    /**
     * Редкий случай
     */
    private Boolean rareCase;

    /**
     * Случай является отказом от госпитализации?
     */
    @Comment("Случай является отказом от госпитализации?")
    @Transient
    public Boolean getIsDeniedHospitalizating() {
        return getDeniedHospitalizating() != null;
    }

    /**
     * Номер стат.карты
     */
    @Comment("Номер стат.карты")
    @Transient
    public String getStatCardNumber() {
		return statisticStub != null ? statisticStub.getCode() :"";
    }

    /**
     * Номер стат.карты
     */
    @Comment("Номер стат.карты")
    @Transient
    public Long getReasonDischarge() {
		return statisticStub != null && statisticStub.getReasonDischarge() != null ? statisticStub.getReasonDischarge().getId() : null;
    }

    @Transient
    public Long getResultDischarge() {
		return statisticStub != null && statisticStub.getResultDischarge() != null ? statisticStub.getResultDischarge().getId() : null;
    }

    /**
     * Главное ЛПУ инфо
     */
    @Comment("Главное ЛПУ инфо")
    @Transient
    public String getMainLpuInfo() {
        return getParentLpu(getLpu()).getFullname();
    }

    private MisLpu getParentLpu(MisLpu aLpu) {
        return aLpu.getParent() != null ? getParentLpu(aLpu.getParent()) : aLpu;
    }

    /**
     * Педикулез
     */
    @Comment("Педикулез")
    @OneToOne
    public VocPediculosis getPediculosis() {
        return pediculosis;
    }

    /**
     * Причина госпитализации в психиатрический стационар
     */
    @Comment("Причина госпитализации в психиатрический стационар")
    @OneToOne
    public VocPsychHospitalReason getPsychReason() {
        return psychReason;
    }


    /**
     * Дата решения суда
     */
    private Date lawCourtDesicionDate;
    /**
     * Причина госпитализации в психиатрический стационар
     */
    private VocPsychHospitalReason psychReason;
    /**
     * Педикулез
     */
    private VocPediculosis pediculosis;

    /**
     * Тип текущего стационара
     */
    private VocHospType hospType;
    /**
     * Перевод в другое ЛПУ
     */
    private MisLpu moveToAnotherLPU;
    /**
     * Провизорность
     */
    private Boolean provisional;
    /**
     * Результат госпитализации
     */
    private VocHospitalizationResult result;
    /**
     * Исход госпитализации
     */
    private VocHospitalizationOutcome outcome;
    /**
     * Время заболевания до госпитализации
     */
    private VocPreAdmissionTime preAdmissionTime;

    /**
     * Номер наряда доставки
     */
    private String supplyOrderNumber;
    /**
     * Номер направления
     */
    private String orderNumber;
    /**
     * Тип доставки
     */
    private String supplyType;
    /**
     * Дефекты догоспитального этапа
     */
    private VocPreAdmissionDefect preAdmissionDefect;
    /**
     * Рабочая функция направителя
     */
    private WorkFunction orderWorkFunction;
    /**
     * Оказана мед. помощь в приемном отделении
     */
    private Boolean medicalAid;
    /**
     * Профиль коек
     */
    private VocBedType bedType;
    /**
     * Сообщение родственникам
     */
    private Boolean relativeMessage;
    /**
     * Стат. карта
     */
    private StatisticStubExist statisticStub;
    /**
     * Амбулаторное лечение
     */
    private Boolean ambulanceTreatment;
    /**
     * Отказ от госпитализации
     */
    private VocDeniedHospitalizating deniedHospitalizating;
    /**
     * Кем доставлен
     */
    private OmcFrm orderType;
    /**
     * Выписной эпикриз
     */
    private String dischargeEpicrisis;
    /**
     * Сопровождающее лицо
     */
    private String attendant;


    @Transient
    public String getInfo() {
        return "СЛС " + getId() + " номер стат.карты " + getStatCardNumber();
    }

    /**
     * Количество дней
     */
    @Comment("Количество дней")
    @Transient
    @Override
    public String getDaysCount() {

        return "";
    }

    /**
     * Кем направлен
     */
    @Comment("Кем направлен")
    @OneToOne
    public MisLpu getOrderLpu() {
        return orderLpu;
    }

    /**
     * Кем направлен
     */
    private MisLpu orderLpu;

    /**
     * Порядок поступления
     */
    @Comment("Порядок поступления")
    @OneToOne
    public VocAdmissionOrder getAdmissionOrder() {
        return admissionOrder;
    }

    /**
     * Решение суда по 35 статье
     */
    @Comment("Решение суда по 35 статье")
    @OneToOne
    public VocJudgment getJudgment35() {
        return judgment35;
    }

    /**
     * Решение суда по 35 статье
     */
    private VocJudgment judgment35;
    /**
     * Порядок поступления
     */
    private VocAdmissionOrder admissionOrder;

    /**
     * Рост
     */
    @Comment("Рост")
    @Transient
    public Integer getHeight() {

        if (statisticStub == null) {
            return 0;
        }
        return statisticStub.getHeight() ;
    }

    /**
     * Вес
     */
    @Comment("Вес")
    @Transient
    public Integer getWeight() {
//        return statisticStub != null ? statisticStub.getWeight() : 0; //WTF?? NPE
        if (statisticStub == null) {
            return 0;
        }
        return statisticStub.getWeight();
    }

    /**
     * Индекс массы тела
     */
    @Comment("Индекс массы тела")
    @Transient
    public Double getImt() {

        //return statisticStub != null ? statisticStub.getIMT() : 0;
        if (statisticStub == null) {
            return 0.0;
        }
        return statisticStub.getImt();
    }

    /**
     * Вес
     */
    private Integer weight;
    /**
     * Рост
     */
    private Integer height;

    /**
     * Была ли проведена идентификация пациента
     */
    private Boolean isIdentified;

    /**
     * Кто провёл идентификацию
     */
    private String identUsername;

    /**
     * Дата идентификации
     */
    private Date identDate;

    /**
     * Время идентификации
     */
    private Time identTime;
}