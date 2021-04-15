package ru.ecom.expert2.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Accessors(prefix = "the")
public class GrouperKSG extends BaseEntity {
    /** Тип коек (дневные, круглосуточные */
    @OneToOne
    private VocBedSubType theBedType ;

    /** Активный */
    private Boolean theIsActive ;

    /** Год группировщика */
    private Integer theYear ;
}
