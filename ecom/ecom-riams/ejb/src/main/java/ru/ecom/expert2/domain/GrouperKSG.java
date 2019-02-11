package ru.ecom.expert2.domain;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Группировщик КСГ
 */
@Entity
public class GrouperKSG extends BaseEntity {
    /** Тип коек (дневные, круглосуточные */
    @Comment("Тип коек (дневные, круглосуточные")
    @OneToOne
    public VocBedSubType getBedType() {return theBedType;}
    public void setBedType(VocBedSubType aBedType) {theBedType = aBedType;}
    /** Тип стационара (дневной, круглосуточный */
    private VocBedSubType theBedType ;

    /** Активный */
    @Comment("Активный")
    public Boolean getIsActive() {return theIsActive;}
    public void setIsActive(Boolean aIsActive) {theIsActive = aIsActive;}
    /** Активный */
    private Boolean theIsActive ;

    /** Год группировщика */
    @Comment("Год группировщика")
    public Integer getYear() {return theYear;}
    public void setYear(Integer aYear) {theYear = aYear;}
    /** Год группировщика */
    private Integer theYear ;
}
