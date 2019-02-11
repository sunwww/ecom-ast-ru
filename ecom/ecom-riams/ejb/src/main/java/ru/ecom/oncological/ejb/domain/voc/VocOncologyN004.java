package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N004 - классификатор Nodus*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN004 extends VocBaseFederal {
    private String ds;

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }

    /** Nodus код */
    @Comment("Nodus код")
    public String getNodusCode() {return theNodusCode;}
    public void setNodusCode(String aNodusCode) {theNodusCode = aNodusCode;}
    /** Nodus код */
    private String theNodusCode ;
}


