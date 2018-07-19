package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N009 - классификатор соответсвия гистологии диагнозам*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN009 extends VocBaseFederal {

    private String ds;
    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }

    /** Histology */
    @Comment("Histology")
    public String getHistology() {return theHistology;}
    public void setHistology(String aHistology) {theHistology = aHistology;}
    /** Histology */
    private String theHistology ;

}
