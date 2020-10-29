package ru.ecom.mis.ejb.domain.patient.voc;/**
 * Created by Milamesher on 30.04.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

@Entity
@Table(schema="SQLUser")
@Comment("Доп. информация о пациенте (для браслета)")
@NamedQueries({
        @NamedQuery( name="VocColorIdentityPatient.getByCode"
                , query="from VocColorIdentityPatient where code=:code ")
})
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

    /** Патология в лаборатории? */
    @Comment("Патология в лаборатории?")
    public Boolean getIsForPatology() {return theIsForPatology;}
    public void setIsForPatology(Boolean aIsForPatology) {theIsForPatology = aIsForPatology;}
    /** Патология в лаборатории?  */
    private Boolean theIsForPatology ;

    /** Запрещено создавать вручную? */
    @Comment("Запрещено создавать вручную?")
    public Boolean getIsDeniedManual() {return theIsDeniedManual;}
    public void setIsDeniedManual(Boolean aIsDeniedManual) {theIsDeniedManual = aIsDeniedManual;}
    /** Запрещено создавать вручную?  */
    private Boolean theIsDeniedManual ;
}