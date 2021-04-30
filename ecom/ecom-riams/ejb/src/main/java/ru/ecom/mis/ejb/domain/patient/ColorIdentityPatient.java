package ru.ecom.mis.ejb.domain.patient;
/*
 * Created by Milamesher on 30.04.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.SurgicalOperation;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

/** Информация о пациенте (для браслета) */
@Entity
@Getter
@Setter
public class ColorIdentityPatient extends BaseEntity {
    /** Браслет */
    @Comment("Браслет")
    @OneToOne
    public VocColorIdentityPatient getVocColorIdentity() {return vocColorIdentity;}
    private VocColorIdentityPatient vocColorIdentity ;
    
    /** Дата установки */
    private Date startDate ;

    /** Дата снятия */
    private Date finishDate ;

    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** Пользователь, который создал запись */
    private String createUsername;

    /** Доп. информация о пациенте (для браслета) */
    private String info;

    /** Хирургическая операция, с которой связан  браслет */
    @Comment("Хирургическая операция, с которой связан  браслет")
    @OneToOne
    public SurgicalOperation getSurgOperation() {return surgOperation;}
    private SurgicalOperation surgOperation ;

    /** Температурный лист, с которым связан  браслет */
    @Comment("Температурный лист, с которым связан  браслет")
    @OneToOne
    public TemperatureCurve getTempCurve() {return tempCurve;}
    private TemperatureCurve tempCurve ;

    /** Время регистрации */
    private Time startTime;

    /** Время снятия с регистрации */
    private Time finishTime;

    public ColorIdentityPatient() {
        long current = System.currentTimeMillis();
        this.startDate = new Date(current);
        this.startTime = new Time(current);
    }

    /** Код сущности для привязки */
    private String entityName ;

    /** ИД сущности */
    private Long entityId ;
}