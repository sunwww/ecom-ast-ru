package ru.ecom.expert2.domain.voc;

import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Справочник базовых тарифов
 */
@Entity
public class VocE2BaseTariff extends VocCoefficient {

    /** Тип стационар (круглосуточный, дневной */
    @Comment("Тип стационар (круглосуточный, дневной")
    @OneToOne
    public VocBedSubType getStacType() {return theStacType;}
    public void setStacType(VocBedSubType aStacType) {theStacType = aStacType;}
    /** Тип стационар (круглосуточный, дневной */
    private VocBedSubType theStacType ;
    
    /** Тип тарифа */
    @Comment("Тип тарифа")
    @OneToOne
    public VocE2BaseTariffType getType() {return theType;}
    public void setType(VocE2BaseTariffType aType) {theType = aType;}
    /** Тип тарифа */
    private VocE2BaseTariffType theType ;
}
