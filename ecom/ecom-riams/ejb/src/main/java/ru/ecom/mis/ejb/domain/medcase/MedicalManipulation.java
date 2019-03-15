package ru.ecom.mis.ejb.domain.medcase;

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
public class MedicalManipulation extends BaseEntity {
    /** Дата начала*/
    @Comment("Дата начала")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;	}

    /** Время начала */
    @Comment("Время начала")
    public Time getStartTime() {return theStartTime;}
    public void setStartTime(Time aStartTime) {theStartTime = aStartTime;	}

    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return theDepartment;}
    public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}

    /** Анестизия */
    @Comment("Анестезия")
    @OneToOne
    public VocAnesthesia getAnesthesia() {return theAnesthesia;}
    public void setAnesthesia(VocAnesthesia aAnesthesia) {theAnesthesia = aAnesthesia;	}

    /** Случай медицинского обслуживания */
    @Comment("Случай медицинского обслуживания")
    @ManyToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return thePatient;}
    public void setPatient(Patient aPatient) {thePatient = aPatient;}

    /** Кол-во  анастезии */
    @Comment("Кол-во  анастезии")
    public BigDecimal getAnesthesiaAmount() {return theAnesthesiaAmount;}
    public void setAnesthesiaAmount(BigDecimal aAnesthesiaAmount) {theAnesthesiaAmount = aAnesthesiaAmount;}

    /**Рабочая функция врача, проводившего манипуляцию info */
    @Comment("Рабочая функция врача, проводившего манипуляцию info")
    @Transient
    public String getSurgeonInfo(){
        return theSurgeon!=null?theSurgeon.getWorkFunctionInfo():"";
    }

    /** Лечебное учреждение */
    @Comment("Лечебное учреждение")
    @OneToOne
    public MisLpu getLpu() {return theLpu;}
    public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

    /** Дата окончания */
    @Comment("Дата окончания")
    public Date getEndDate() {return theEndDate;}
    public void setEndDate(Date aEndDate) {theEndDate = aEndDate;}

    /** Анестезиолог */
    @Comment("Анестезиолог")
    @OneToOne
    public WorkFunction getAnaesthetist() {return theAnaesthetist;}
    public void setAnaesthetist(WorkFunction aAnaesthetist) {theAnaesthetist = aAnaesthetist;}

    /** Медсестра */
    @Comment("Медсестра")
    @OneToOne
    public WorkFunction getNurse() {return theNurse;}
    public void setNurse(WorkFunction aNurse) {theNurse = aNurse;}

    /** Эпикриз */
    @Comment("Эпикриз")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getEpicrisis() {return theEpicrisis;}
    public void setEpicrisis(String aEpicrisis) {theEpicrisis = aEpicrisis;}

    /** Описание */
    @Comment("Описание")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getText() {return theText;}
    public void setText(String aText) {theText = aText;}

    /** Время окончания */
    @Comment("Время окончания")
    public Time getEndTime() {return theEndTime;}
    public void setEndTime(Time aEndTime) {theEndTime = aEndTime;}

    @Comment("Информация")
    @Transient
    public String getInformation() {
        //ret.append("Анестезиолог: ").append(theAnaesthetist) ;
        //ret.append("Анестезия: ").append(theAnesthesia).append(" кол-во:").append(theAnesthesiaAmount);

        return "Период: " + theEndTime + " " + theEndTime + " - " +
                theEndDate +
                " " + theEndTime +
                "Услуга: " + theMedService +
                "Хирург: " + getSurgeonInfo();

    }

    @Comment("Период")
    @Transient
    public String getPeriod() {
        return theEndDate + " " + theStartDate + " - " +
                theEndDate +
                " " + theEndTime;
    }

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    @Transient
    public String getPatientInfo() {

        return thePatient!=null?thePatient.getPatientInfo():"";
    }

    /** Показания*/
    @Comment("Показания")
    @OneToOne
    public VocHospitalAspect getAspect() {return theAspect;}
    public void setAspect(VocHospitalAspect aAspect) {theAspect = aAspect;}

    /** Показания */
    private VocHospitalAspect theAspect;

    /** Анестезии */
    @Comment("Анестезии")
    @OneToMany(mappedBy="manipulation", cascade=CascadeType.ALL)
    public List<Anesthesia> getAnesthesies() {
        return theAnesthesies;
    }

    public void setAnesthesies(List<Anesthesia> aAnesthesies) {
        theAnesthesies = aAnesthesies;
    }

    /** Номер в журнале */
    @Comment("Номер в журнале")
    public String getNumberInJournal() {
        return theNumberInJournal;
    }

    public void setNumberInJournal(String aNumberInJournal) {
        theNumberInJournal = aNumberInJournal;
    }

    /** Хирург */
    @Comment("Хирург")
    @OneToOne
    public WorkFunction getSurgeon() {return theSurgeon;}
    public void setSurgeon(WorkFunction aSurgeon) {theSurgeon = aSurgeon;}

    /** Хирург */
    private WorkFunction theSurgeon;

    /** Номер в журнале */
    private String theNumberInJournal;

    /** Анестезии */
    private List<Anesthesia> theAnesthesies;


    /** Кол-во  анастезии */
    private BigDecimal theAnesthesiaAmount;
    /** Анестезиолог */
    private WorkFunction theAnaesthetist;
    /** Дата манипуляции по */
    private Date theEndDate;
    /** Эпикриз */
    private String theEpicrisis;
    /** Описание */
    private String theText;
    /** Медсестра */
    private WorkFunction theNurse;
    /** Время окончания */
    private Time theEndTime;

    /** Лечебное учреждение */
    private MisLpu theLpu;
    /** Пациент */
    private Patient thePatient;
    /** Случай медицинского обслуживания */
    private MedCase theMedCase;
    /** Анестезия */
    private VocAnesthesia theAnesthesia;
    /** Отделение */
    private MisLpu theDepartment;
    /** Время манипуляции */
    private Time theStartTime;
    /** Дата манипуляции */
    private Date theStartDate;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

    /** Пользователь, создавший запись */
    @Comment("Пользователь, создавший запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aUsername) {theCreateUsername = aUsername;}

    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}

    /** Пользователь, последний изменявший запись */
    @Comment("Пользователь, последний изменявший запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

    /** Пользователь, последний изменявший запись */
    private String theEditUsername;
    /** Дата редактирования */
    private Date theEditDate;
    /** Пользователь, создавший запись */
    private String theCreateUsername;
    /** Дата создания */
    private Date theCreateDate;

    /** Поток обслуживания */
    @Comment("Поток обслуживания")
    @OneToOne
    public VocServiceStream getServiceStream() {return theServiceStream;}
    public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}

    /** Поток обслуживания */
    private VocServiceStream theServiceStream;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @OneToOne
    public MedService getMedService() {return theMedService;}
    public void setMedService(MedService aMedService) {theMedService = aMedService;}

    /** Мед. услуга */
    private MedService theMedService;

    /** Дата печати */
    @Comment("Дата печати")
    public Date getPrintDate() {return thePrintDate;}
    public void setPrintDate(Date aPrintDate) {thePrintDate = aPrintDate;}

    /** Дата печати */
    private Date thePrintDate;

    /** Время печати */
    @Comment("Время печати")
    public Time getPrintTime() {return thePrintTime;}
    public void setPrintTime(Time aPrintTime) {thePrintTime = aPrintTime;}

    /** Время печати */
    private Time thePrintTime;

    /** Пользователь, посл. распечат. документ */
    @Comment("Пользователь, посл. распечат. документ")
    public String getPrintUsername() {return thePrintUsername;}
    public void setPrintUsername(String aPrintUsername) {thePrintUsername = aPrintUsername;}

    /** Пользователь, посл. распечат. документ */
    private String thePrintUsername;
}