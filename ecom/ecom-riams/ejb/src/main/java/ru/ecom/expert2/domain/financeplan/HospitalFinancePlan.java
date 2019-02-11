package ru.ecom.expert2.domain.financeplan;

import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class HospitalFinancePlan extends FinancePlan {

    /** КСГ */
    @Comment("КСГ")
    @OneToOne
    public VocKsg getKsg() {return theKsg;}
    public void setKsg(VocKsg aKsg) {theKsg = aKsg;}
    /** КСГ */
    private VocKsg theKsg ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @OneToOne
    public VocBedSubType getBedSubType() {return theBedSubType;}
    public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}
    /** Подтип коек */
    private VocBedSubType theBedSubType ;


}
