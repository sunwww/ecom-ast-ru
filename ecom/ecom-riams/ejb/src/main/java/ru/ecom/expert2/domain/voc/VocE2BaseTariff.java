package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Справочник базовых тарифов
 */
@Entity
@Getter
@Setter
public class VocE2BaseTariff extends VocCoefficient {

    /** Тип стационар (круглосуточный, дневной */
    @Comment("Тип стационар (круглосуточный, дневной")
    @OneToOne
    public VocBedSubType getStacType() {return stacType;}
    /** Тип стационар (круглосуточный, дневной */
    private VocBedSubType stacType ;
    
    /** Тип тарифа */
    @Comment("Тип тарифа")
    @OneToOne
    public VocE2BaseTariffType getType() {return type;}
    /** Тип тарифа */
    private VocE2BaseTariffType type ;
}
