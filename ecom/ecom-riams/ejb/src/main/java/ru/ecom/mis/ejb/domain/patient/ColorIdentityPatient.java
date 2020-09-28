package ru.ecom.mis.ejb.domain.patient;/**
 * Created by Milamesher on 30.04.2019.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.SurgicalOperation;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

/** Информация о пациенте (для браслета) */
@Entity
public class ColorIdentityPatient extends BaseEntity {
    /** Браслет */
    @Comment("Браслет")
    @OneToOne
    public VocColorIdentityPatient getVocColorIdentity() {return theVocColorIdentity;}
    public void setVocColorIdentity(VocColorIdentityPatient aVocColorIdentity) {theVocColorIdentity = aVocColorIdentity;}
    /** Браслет */
    private VocColorIdentityPatient theVocColorIdentity ;
    
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

    /** Доп. информация о пациенте (для браслета) */
    @Comment("Доп. информация о пациенте (для браслета)")
    public String getInfo() {return theInfo;}
    public void setInfo(String aInfo) {theInfo = aInfo;}
    /** Доп. информация о пациенте (для браслета) */
    private String theInfo;

    /** Хирургическая операция, с которой связан  браслет */
    @Comment("Хирургическая операция, с которой связан  браслет")
    @OneToOne
    public SurgicalOperation getSurgOperation() {return theSurgOperation;}
    public void setSurgOperation(SurgicalOperation aSurgOperation) {theSurgOperation = aSurgOperation;}
    /** Хирургическая операция, с которой связан  браслет */
    private SurgicalOperation theSurgOperation ;

    /** Время регистрации */
    @Comment("Время регистрации")
    public Time getStartTime() {return theStartTime;}
    public void setStartTime(Time aStartTime) {theStartTime = aStartTime;	}
    /** Время регистрации */
    private Time theStartTime;

    /** Время снятия с регистрации */
    @Comment("Время снятия с регистрации")
    public Time getFinishTime() {return theFinishTime;}
    public void setFinishTime(Time aFinishTime) {theFinishTime = aFinishTime;	}
    /** Время снятия с регистрации */
    private Time theFinishTime;

    public ColorIdentityPatient() {
        long current = System.currentTimeMillis();
        this.theStartDate = new Date(current);
        this.theStartTime = new Time(current);
    }

    /** Код сущности для привязки */
    @Comment("Код сущности для привязки")
    public String getEntityName() {return theEntityName;}
    public void setEntityName(String aEntityName) {theEntityName = aEntityName;}
    private String theEntityName ;

    /** ИД сущности */
    @Comment("ИД сущности")
    public Long getEntityId() {return theEntityId;}
    public void setEntityId(Long aEntityId) {theEntityId = aEntityId;}
    private Long theEntityId ;


}