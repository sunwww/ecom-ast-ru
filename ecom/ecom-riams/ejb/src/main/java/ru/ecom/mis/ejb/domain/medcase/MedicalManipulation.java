package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalAspect;
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
 * Created by Milamesher on 31.08.2017.
 */
@Entity
@Comment("Медицинская манипуляция")
@AIndexes({
        @AIndex(properties="medCase"),
        @AIndex(properties="startDate"),
        @AIndex(properties={"surgeon"}),
        @AIndex(properties={"medService"}),
        @AIndex(properties="patient"),
        @AIndex(properties="serviceStream"),
        @AIndex(properties="department")
})
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class MedicalManipulation extends BaseEntity {

    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return department;}

    /** Анестизия */
    @Comment("Анестезия")
    @OneToOne
    public VocAnesthesia getAnesthesia() {return anesthesia;}

    /** Случай медицинского обслуживания */
    @Comment("Случай медицинского обслуживания")
    @ManyToOne
    public MedCase getMedCase() {return medCase;}

    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return patient;}

    /**Рабочая функция врача, проводившего манипуляцию info */
    @Comment("Рабочая функция врача, проводившего манипуляцию info")
    @Transient
    public String getSurgeonInfo(){
        return surgeon!=null?surgeon.getWorkFunctionInfo():"";
    }

    /** Лечебное учреждение */
    @Comment("Лечебное учреждение")
    @OneToOne
    public MisLpu getLpu() {return lpu;}

    /** Анестезиолог */
    @Comment("Анестезиолог")
    @OneToOne
    public WorkFunction getAnaesthetist() {return anaesthetist;}

    /** Медсестра */
    @Comment("Медсестра")
    @OneToOne
    public WorkFunction getNurse() {return nurse;}

    /** Эпикриз */
    @Comment("Эпикриз")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getEpicrisis() {return epicrisis;}

    /** Описание */
    @Comment("Описание")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getText() {return text;}

    @Comment("Информация")
    @Transient
    public String getInformation() {
        return "Период: " + endTime + " " + endTime + " - " +
                endDate +
                " " + endTime +
                "Услуга: " + medService +
                "Хирург: " + getSurgeonInfo();
    }

    @Comment("Период")
    @Transient
    public String getPeriod() {
        return endDate + " " + startDate + " - " +
                endDate +
                " " + endTime;
    }

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    @Transient
    public String getPatientInfo() {
        return patient!=null?patient.getPatientInfo():"";
    }

    /** Показания*/
    @Comment("Показания")
    @OneToOne
    public VocHospitalAspect getAspect() {return aspect;}

    /** Показания */
    private VocHospitalAspect aspect;

    /** Анестезии */
    @Comment("Анестезии")
    @OneToMany(mappedBy="manipulation", cascade=CascadeType.ALL)
    public List<Anesthesia> getAnesthesies() {
        return anesthesies;
    }

    /** Номер в журнале */
    @Comment("Номер в журнале")
    public String getNumberInJournal() {
        return numberInJournal;
    }

    /** Хирург */
    @Comment("Хирург")
    @OneToOne
    public WorkFunction getSurgeon() {return surgeon;}

    /** Хирург */
    private WorkFunction surgeon;

    /** Номер в журнале */
    private String numberInJournal;

    /** Анестезии */
    private List<Anesthesia> anesthesies;


    /** Кол-во  анастезии */
    private BigDecimal anesthesiaAmount;
    /** Анестезиолог */
    private WorkFunction anaesthetist;
    /** Дата манипуляции по */
    private Date endDate;
    /** Эпикриз */
    private String epicrisis;
    /** Описание */
    private String text;
    /** Медсестра */
    private WorkFunction nurse;
    /** Время окончания */
    private Time endTime;

    /** Лечебное учреждение */
    private MisLpu lpu;
    /** Пациент */
    private Patient patient;
    /** Случай медицинского обслуживания */
    private MedCase medCase;
    /** Анестезия */
    private VocAnesthesia anesthesia;
    /** Отделение */
    private MisLpu department;
    /** Время манипуляции */
    private Time startTime;
    /** Дата манипуляции */
    private Date startDate;

    /** Пользователь, последний изменявший запись */
    private String editUsername;
    /** Дата редактирования */
    private Date editDate;
    /** Пользователь, создавший запись */
    private String createUsername;
    /** Дата создания */
    private Date createDate;

    /** Поток обслуживания */
    @Comment("Поток обслуживания")
    @OneToOne
    public VocServiceStream getServiceStream() {return serviceStream;}

    /** Поток обслуживания */
    private VocServiceStream serviceStream;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @OneToOne
    public MedService getMedService() {return medService;}

    /** Мед. услуга */
    private MedService medService;

    /** Дата печати */
    private Date printDate;

    /** Время печати */
    private Time printTime;

    /** Пользователь, посл. распечат. документ */
    private String printUsername;
}