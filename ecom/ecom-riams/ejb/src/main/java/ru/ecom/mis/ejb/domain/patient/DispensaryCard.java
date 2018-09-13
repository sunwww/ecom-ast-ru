package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.patient.voc.VocDispensaryEnd;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

/**Карта диспансерного наблюдения*/
@Entity
public class DispensaryCard extends BaseEntity {
    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return thePatient;}
    public void setPatient(Patient aPatient) {thePatient = aPatient;}
    /** Пациент */
    private Patient thePatient ;
    
    /** Дата постановки на Д учет */
    @Comment("Дата постановки на Д учет")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата постановки на Д учет */
    private Date theStartDate ;
    
    /** Дата снятия с учета */
    @Comment("Дата снятия с учета")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата снятия с учета */
    private Date theFinishDate ;
    
    /** Диагноз постановки на Д учет */
    @Comment("Диагноз постановки на Д учет")
    @OneToOne
    public VocIdc10 getDiagnosis() {return theDiagnosis;}
    public void setDiagnosis(VocIdc10 aDiagnosis) {theDiagnosis = aDiagnosis;}
    /** Диагноз постановки на Д учет */
    private VocIdc10 theDiagnosis ;

    /** Рабочая функция врача установившего наблюдение */
    @Comment("Рабочая функция врача установившего наблюдение")
    @OneToOne
    public WorkFunction getWorkFunction() {return theWorkFunction;}
    public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}
    /** Рабочая функция врача установившего наблюдение */
    private WorkFunction theWorkFunction ;

    /** Причина снятия с учета */
    @Comment("Причина снятия с учета")
    @OneToOne
    public VocDispensaryEnd getEndReason() {return theEndReason;}
    public void setEndReason(VocDispensaryEnd aEndReason) {theEndReason = aEndReason;}
    /** Причина снятия с учета */
    private VocDispensaryEnd theEndReason ;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private Date theCreateDate ;

    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    /** Время создания */
    private Time theCreateTime ;

    /** Создатель */
    @Comment("Создатель")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Создатель */
    private String theCreateUsername ;

    /** Редактировал */
    @Comment("Редактировал")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Редактировал */
    private String theEditUsername ;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private Date theEditDate ;

    /** Время редактирования */
    @Comment("Время редактирования")
    public Time getEditTime() {return theEditTime;}
    public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
    /** Время редактирования */
    private Time theEditTime ;

}
