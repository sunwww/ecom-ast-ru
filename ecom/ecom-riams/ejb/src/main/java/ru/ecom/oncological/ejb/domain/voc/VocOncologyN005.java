package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N005 - классификатор Metastasis*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN005 extends VocBaseFederal {

    private String ds;


    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }

    /** Metastasis код */
    @Comment("Metastasis код")
    public String getMetastasisCode() {return theMetastasisCode;}
    public void setMetastasisCode(String aMetastasisCode) {theMetastasisCode = aMetastasisCode;}
    /** Metastasis код */
    private String theMetastasisCode ;
}
