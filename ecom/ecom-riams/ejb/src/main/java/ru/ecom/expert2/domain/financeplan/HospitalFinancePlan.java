package ru.ecom.expert2.domain.financeplan;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.domain.med.VocKsgGroup;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class HospitalFinancePlan extends FinancePlan {

    /** Группа КСГ */
    @Comment("Группа КСГ")
    @OneToOne
    public VocKsgGroup getKsgGroup() {return ksgGroup;}
    private VocKsgGroup ksgGroup;

    /** КСГ */
    @Comment("КСГ")
    @OneToOne
    public VocKsg getKsg() {return ksg;}
    private VocKsg ksg;

    /** Подтип коек */
    @Comment("Подтип коек")
    @OneToOne
    public VocBedSubType getBedSubType() {return bedSubType;}
    private VocBedSubType bedSubType;


}
