package ru.ecom.mis.ejb.domain.patient;/**
 * Created by Milamesher on 11.07.2019.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocObservationResult;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

/** Лист наблюдения пациента выездной
 * анестезиолого-реанимационной неонатальной
 * бригады ОПЦ*/
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="patient"),
        @AIndex(properties="startDate")
})
public class ObservationSheet extends BaseEntity {
    /** Дата установки */
    @Comment("Дата установки")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата установки */
    private Date theStartDate ;

    /** Дата снятия */
    @Comment("Дата снятия")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата снятия */
    private Date theFinishDate ;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который создал запись */
    private String theCreateUsername;

    /** Специалист, открывший ЛН */
    @Comment("Специалист, открывший ЛН")
    @OneToOne
    public WorkFunction getSpecialistStart() {return theSpecialistStart;}
    public void setSpecialistStart(WorkFunction aSpecialistStart) {theSpecialistStart = aSpecialistStart;}
    /** Специалист, открывший ЛН */
    private WorkFunction theSpecialistStart;

    /** Специалист, закрывший ЛН */
    @Comment("Специалист, закрывший ЛН")
    @OneToOne
    public WorkFunction getSpecialistFin() {return theSpecialistFin;}
    public void setSpecialistFin(WorkFunction aSpecialistFin) {theSpecialistFin = aSpecialistFin;}
    /** Специалист, закрывший ЛН */
    private WorkFunction theSpecialistFin;

    /** Результат наблюдения */
    @Comment("Результат наблюдения")
    @OneToOne
    public VocObservationResult getObservResult() {return theObservResult;}
    public void setObservResult(VocObservationResult aObservResult) {theObservResult = aObservResult;}
    /** Результат наблюдения */
    private VocObservationResult theObservResult;

    /** Пациент */
    @Comment("Пациент")
    @ManyToOne
    public Patient getPatient() {return thePatient;}
    public void setPatient(Patient aNewProperty) {thePatient = aNewProperty;}
    /**Пациент */
    private Patient thePatient;
}