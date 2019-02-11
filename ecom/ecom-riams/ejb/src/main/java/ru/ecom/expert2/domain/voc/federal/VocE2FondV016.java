package ru.ecom.expert2.domain.voc.federal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Справочник типов диспансеризации
 */
@Entity
public class VocE2FondV016 extends VocBaseFederal {

    /** Rule */
    @Comment("Rule")
    public String getRule() {return theRule;}
    public void setRule(String aRule) {theRule = aRule;}
    /** dTrULE */
    private String theRule ;

}
