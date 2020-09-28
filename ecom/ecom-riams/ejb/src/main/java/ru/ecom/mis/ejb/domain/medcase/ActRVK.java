package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

/**
 * Акт РВК
 * @author Milamesher
 *
 */

@Comment("Акт РВК")
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="medCase"),
        @AIndex(properties="dateStart"),
        @AIndex(properties="dateFinish")
})
public class ActRVK extends BaseEntity {
    /** Дата начала акта */
    @Comment("Дата начала акта")
    public Date getDateStart() {return theDateStart;}
    public void setDateStart(Date aDateStart) {theDateStart = aDateStart;}
    /** Дата начала акта */
    private Date theDateStart;
    
    /** Дата окончания акта */
    @Comment("Дата окончания акта")
    public Date getDateFinish() {return theDateFinish;}
    public void setDateFinish(Date aDateFinish) {theDateFinish = aDateFinish;}
    /** Дата окончания акта */
    private Date theDateFinish;

    /** Примечание */
    @Comment("Примечание")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    /** Примечание */
    private String theComment;

    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return thePatient;}
    public void setPatient(Patient aPatient) {thePatient = aPatient;}
    /** Пациент */
    private Patient thePatient;

    /** Окончательный диагноз */
    @Comment("Окончательный диагноз")
    @OneToOne
    public VocIdc10 getIdc10() {return theFinishIdc10;}
    public void setIdc10(VocIdc10 aFinishIdc10) {theFinishIdc10 = aFinishIdc10;}
    /** Окончательный диагноз */
    private VocIdc10 theFinishIdc10;

    /** Текст диагноза */
    @Comment("Текст диагноза")
    public String getDiagnosis() {return theDiagnosis;}
    public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}
    /** Текст диагноза */
    private String theDiagnosis;
    
    /** Parent госпитализация/визит */
    @Comment("Parent госпитализация/визит")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    /** Parent госпитализация/визит */
    private MedCase theMedCase;

    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return theDepartment;}
    public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
    /** Отделение */
    private MisLpu theDepartment;

    /** Номер акта */
    @Comment("Номер акта")
    public String getNumAct() {return theNumAct;}
    public void setNumAct(String aNumAct) {theNumAct = aNumAct;}
    /** Номер акта */
    private String theNumAct;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private Date theCreateDate;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private Date theEditDate;

    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    /** Время создания */
    private Time theCreateTime;

    /** Время редактрования */
    @Comment("Время редактрования")
    public Time getEditTime() {return theEditTime;}
    public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
    /** Время редактрования */
    private Time theEditTime;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который создал запись */
    private String theCreateUsername;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;

    /** Рабочая функция открывшего*/
    @Comment("Рабочая функция открывшего")
    @OneToOne
    public WorkFunction getWorkFunctionStart() {return theWorkFunctionStart;}
    public void setWorkFunctionStart(WorkFunction aWorkFunctionStart) {theWorkFunctionStart = aWorkFunctionStart;}
    /** Рабочая функция открывшего*/
    private WorkFunction theWorkFunctionStart;
    
    /** Рабочая функция закрывшего*/
    @Comment("Рабочая функция закрывшего")
    @OneToOne
    public WorkFunction getWorkFunctionFinish() {return theWorkFunctionFinish;}
    public void setWorkFunctionFinish(WorkFunction aWorkFunctionFinish) {theWorkFunctionFinish = aWorkFunctionFinish;}
    /** Рабочая функция закрывшего*/
    private WorkFunction theWorkFunctionFinish;

    /** Название раб. функции открывшего акт*/
    @Comment("Название раб. функции открывшего акт")
    @OneToOne
    public VocWorkFunction getSpecName() {return theSpecName;}
    public void setSpecName(VocWorkFunction aSpecName) {theSpecName = aSpecName;}
    /** Название раб. функции открывшего акт*/
    private VocWorkFunction theSpecName;
}