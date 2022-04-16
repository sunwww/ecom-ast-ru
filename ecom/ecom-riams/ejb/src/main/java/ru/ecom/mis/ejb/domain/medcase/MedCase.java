package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.ColorIdentityPatient;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Случай медицинского обслуживания
 */
@Entity
@Table(schema = "SQLUser")
@AIndexes({

        @AIndex(properties = "parent"),
        @AIndex(properties = "patient"),
        @AIndex(properties = "dateStart"),
        @AIndex(properties = "dateFinish")
})
@EntityListeners(DeleteListener.class)
@Getter
@Setter
abstract public class MedCase extends BaseEntity {

    /**
     * Цвета браслета пациента в госпитализации
     */
    @Comment("Цвета браслета пациента в госпитализации")
    @ManyToMany
    public List<ColorIdentityPatient> getColorsIdentity() {
        return colorsIdentity;
    }

    private List<ColorIdentityPatient> colorsIdentity;

    public void addColorsIdentity(ColorIdentityPatient colorIdentityPatient) {
        colorsIdentity.add(colorIdentityPatient);
    }

    /**
     * Случаи ВМП
     */
    @Comment("Случаи ВМП")
    @OneToMany(mappedBy = "medCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<HitechMedicalCase> getHitechMedicalCases() {
        return hitechMedicalCases;
    }

    private List<HitechMedicalCase> hitechMedicalCases;

    /**
     * Диагнозы по случаю
     */
    @Comment("Диагнозы по случаю")
    @OneToMany(mappedBy = "medCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    private List<Diagnosis> diagnoses;


    /**
     * Признак консультативно-диагностического обращения
     */
    @Comment("Признак консультативно-диагностического обращения")
    @Deprecated
    public Boolean getIsDiagnosticSpo() {
        return isDiagnosticSpo;
    }

    private Boolean isDiagnosticSpo;

    /**
     * Только принят
     */
    private static final int STATUS_NULL = 0;
    /**
     * ОТКАЗ
     */
    private static final int STATUS_REFUSAL = 1;
    /**
     * СОГЛАСИЛСЯ
     */
    private static final int STATUS_AGREED = 2;

    /**
     * Поток обслуживания
     */
    @Comment("Поток обслуживания")
    @OneToOne
    public VocServiceStream getServiceStream() {
        return serviceStream;
    }

    /**
     * Пациент
     */
    @Comment("Пациент")
    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    /**
     * Дата начала
     */
    @Comment("Дата начала")
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Внешний идентификатор
     */
    @Comment("Внешний идентификатор")
    public String getExternalId() {
        return externalId;
    }

    /**
     * Внешний идентификатор
     */
    private String externalId;

    /**
     * Родительский СМО
     */
    @Comment("Родительский СМО")
    @ManyToOne
    public MedCase getParent() {
        return parent;
    }

    /**
     * Тип СМО
     */
    @Comment("Тип СМО")
    @OneToOne
    public VocMedCaseDefect getMedCaseDefect() {
        return medCaseDefect;
    }


    /**
     * ЛПУ - место исполнения
     */
    @Comment("ЛПУ - место исполнения ")
    @OneToOne
    public MisLpu getLpu() {
        return lpu;
    }

    /**
     * Опьянение
     */
    @Comment("Опьянение")
    @OneToOne
    public VocIntoxication getIntoxication() {
        return intoxication;
    }

    /**
     * Хирургические операции
     */
    @Comment("Хирургические операции")
    @OneToMany(mappedBy = "medCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<SurgicalOperation> getSurgicalOperations() {
        return surgicalOperations;
    }

    /**
     * Экстренность
     */
    private Boolean emergency;

    /**
     * Представитель
     */
    @Comment("Представитель")
    @OneToOne
    public Kinsman getKinsman() {
        return kinsman;
    }

    @Comment("Госпитализация (впервые, повторно)")
    @OneToOne
    public VocHospitalization getHospitalization() {
        return hospitalization;
    }

    /**
     * Госпитализация (впервые, повторно)
     */
    private VocHospitalization hospitalization;
    /**
     * Представитель
     */
    private Kinsman kinsman;

    @Transient
    public String getPatientInfo() {
        return patient != null ? patient.getFio() : "";
    }

    @Transient
    public String getInfo() {
        return String.valueOf(getId());
    }

    @Transient
    public Boolean getIsPrintInfo() {
        return isPrint != null && isPrint.intValue() == STATUS_AGREED;
    }

    /**
     * Беременность
     */
    @Comment("Беременность")
    @OneToOne
    public Pregnancy getPregnancy() {
        return pregnancy;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    private String editUsername;
    /**
     * Дата редактирования
     */
    private Date editDate;
    /**
     * Гостиничная услуга
     */
    private Boolean hotelServices;
    /**
     * Беременность
     */
    private Pregnancy pregnancy;
    /**
     * Время печати
     */
    private Time printTime;
    /**
     * Дата печати
     */
    private Date printDate;
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
    private BigDecimal uet;
    /**
     * Дата создания
     */
    private Date createDate;
    /**
     * Оператор
     */
    private String username;
    /**
     * Поток обслуживания
     */
    private VocServiceStream serviceStream;
    /**
     * Пациент
     */
    private Patient patient;
    /**
     * Дата начала
     */
    private Date dateStart;
    /**
     * Родительский СМО
     */
    private MedCase parent;
    /**
     * Тип СМО
     */
    private VocMedCaseDefect medCaseDefect;

    /**
     * Недействительность
     */
    private boolean noActuality;

    /**
     * ЛПУ - место исполнения
     */
    private MisLpu lpu;
    /**
     * Хирургические операции
     */
    private List<SurgicalOperation> surgicalOperations;
    /**
     * Опьянение
     */
    private VocIntoxication intoxication;

    /**
     * Дата операции
     */
    private Date operationDate;

    /**
     * Осложнение при операции
     */
    private Long complication;

    /**
     * Отделение
     */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {
        return department;
    }

    /**
     * Отделение (текст)
     */
    @Comment("Отделение (текст)")
    @Transient
    public String getDepartmentInfo() {
        return department != null ? department.getName() : "";
    }

    /**
     * Отделение
     */
    private MisLpu department;

    /**
     * Рабочая функция начавшего случай
     */
    @Comment("Рабочая функция начавшего случай")
    @OneToOne
    public WorkFunction getStartFunction() {
        return startFunction;
    }

    /**
     * Рабочая функция начавшего случай
     */
    private WorkFunction startFunction;
    /**
     * Дата направления
     */
    private Date orderDate;

    /**
     * Время создания
     */
    private Time createTime;

    /**
     * Время редактирования
     */
    private Time editTime;

    /**
     * Дата окончания
     */
    private Date dateFinish;

    /**
     * Вид ВМП
     */
    @Comment("Вид ВМП")
    @OneToOne
    @Deprecated
    public VocKindHighCare getKindHighCare() {
        return kindHighCare;
    }

    /**
     * Метод ВМП
     */
    @Comment("Метод ВМП")
    @OneToOne
    @Deprecated
    public VocMethodHighCare getMethodHighCare() {
        return methodHighCare;
    }

    /**
     * Метод ВМП
     */
    private VocMethodHighCare methodHighCare;
    /**
     * Вид ВМП
     */
    private VocKindHighCare kindHighCare;

    /**
     * Услуга оплачена
     */
    private Boolean isPaid;

    /**
     * Гарантийное письмо
     */
    @Comment("Гарантийное письмо")
    @OneToOne
    public ContractGuarantee getGuarantee() {
        return guarantee;
    }

    /**
     * Гарантийное письмо
     */
    private ContractGuarantee guarantee;

    private Boolean upload = false;

    /**
     * Код в промеде
     **/
    private String promedCode;

    @Comment("Код в промеде")
    public String getPromedCode() {
        return promedCode;
    }

    @PrePersist
    void prePersist() {
        setCreateDate(new Date(System.currentTimeMillis()));
        setCreateTime(new Time(System.currentTimeMillis()));
    }
}
