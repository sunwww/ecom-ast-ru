package ru.ecom.mis.ejb.domain.patient.voc;/**
 * Created by Milamesher on 30.04.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="SQLUser")
@Comment("Доп. информация о пациенте (для браслета)")
public class VocColorIdentityPatient extends VocBaseEntity {
    /** Может ли заполняться в родах? */
    @Comment("Может ли заполняться в родах?")
    public Boolean getIsForNewborn() {return theIsForNewborn;}
    public void setIsForNewborn(Boolean aIsForNewborn) {theIsForNewborn = aIsForNewborn;}
    /** Может ли заполняться в родах?  */
    private Boolean theIsForNewborn ;

    /** Цвет */
    @OneToOne
    public VocColor getColor() { return theColor ; }
    public void setColor(VocColor aColor) { theColor = aColor ; }
    /** Цвет */
    private VocColor theColor;

    /** ЛПУ */
    @ManyToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }
    /** ЛПУ */
    private MisLpu theLpu;
}