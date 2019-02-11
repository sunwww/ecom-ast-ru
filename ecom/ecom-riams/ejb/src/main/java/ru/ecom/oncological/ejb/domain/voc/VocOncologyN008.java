package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N008 - классификатор гистологии*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN008 extends VocBaseFederal {
    /** Histology */
    @Comment("Histology")
    public String getHistology() {return theHistology;}
    public void setHistology(String aHistology) {theHistology = aHistology;}
    /** Histology */
    private String theHistology ;
}
