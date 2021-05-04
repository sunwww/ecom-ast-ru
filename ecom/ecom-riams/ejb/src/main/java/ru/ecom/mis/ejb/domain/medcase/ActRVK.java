package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ActRVK extends BaseEntity {
    /** Дата начала акта */
    private Date dateStart;
    
    /** Дата окончания акта */
    private Date dateFinish;

    /** Примечание */
    @Comment("Примечание")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getComment() {return comment;}
    /** Примечание */
    private String comment;

    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return patient;}
    /** Пациент */
    private Patient patient;

    /** Окончательный диагноз */
    @Comment("Окончательный диагноз")
    @OneToOne
    public VocIdc10 getIdc10() {return idc10;}
    private VocIdc10 idc10;

    /** Текст диагноза */
    private String diagnosis;
    
    /** Parent госпитализация/визит */
    @Comment("Parent госпитализация/визит")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    private MedCase medCase;

    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return department;}
    private MisLpu department;

    /** Номер акта */
    private String numAct;

    /** Дата создания */
    private Date createDate;

    /** Дата редактирования */
    private Date editDate;

    /** Время создания */
    private Time createTime;

    /** Время редактрования */
    private Time editTime;

    /** Пользователь, который создал запись */
    private String createUsername;

    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** Рабочая функция открывшего*/
    @Comment("Рабочая функция открывшего")
    @OneToOne
    public WorkFunction getWorkFunctionStart() {return workFunctionStart;}
    /** Рабочая функция открывшего*/
    private WorkFunction workFunctionStart;
    
    /** Рабочая функция закрывшего*/
    @Comment("Рабочая функция закрывшего")
    @OneToOne
    public WorkFunction getWorkFunctionFinish() {return workFunctionFinish;}
    /** Рабочая функция закрывшего*/
    private WorkFunction workFunctionFinish;

    /** Название раб. функции открывшего акт*/
    @Comment("Название раб. функции открывшего акт")
    @OneToOne
    public VocWorkFunction getSpecName() {return specName;}
    /** Название раб. функции открывшего акт*/
    private VocWorkFunction specName;
}