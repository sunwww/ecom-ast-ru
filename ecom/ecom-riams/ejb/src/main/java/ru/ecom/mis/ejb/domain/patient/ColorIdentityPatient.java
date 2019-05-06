package ru.ecom.mis.ejb.domain.patient;/**
 * Created by Milamesher on 30.04.2019.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/** Информация о пациенте (для браслета) */
@Entity
public class ColorIdentityPatient extends BaseEntity {
    /** Доп. информация о пациенте (для браслета) */
    @Comment("Доп. информация о пациенте (для браслета)")
    @OneToOne
    public VocColorIdentityPatient getVocColorIndentity() {return theVocColorIndentity;}
    public void setVocColorIndentity(VocColorIdentityPatient aVocColorIndentity) {theVocColorIndentity = aVocColorIndentity;}
    /** Доп. информация о пациенте (для браслета) */
    private VocColorIdentityPatient theVocColorIndentity ;
    
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
}