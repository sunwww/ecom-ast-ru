package ru.ecom.expert2.domain;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Группировщик КСГ
 */
@Entity
@Getter
@Setter
public class GrouperKSG extends BaseEntity {
    /** Тип коек (дневные, круглосуточные */
    @Comment("Тип коек (дневные, круглосуточные")
    @OneToOne
    public VocBedSubType getBedType() {return bedType;}
    private VocBedSubType bedType ;

    /** Активный */
    private Boolean isActive ;

    /** Год группировщика */
    private Integer year ;
}
