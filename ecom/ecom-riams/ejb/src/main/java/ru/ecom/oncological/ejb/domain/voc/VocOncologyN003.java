package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N003 - классификатор Tumor*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN003 extends VocBaseFederal {
    private String ds;

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }

    /** Tumor код */
    @Comment("Tumor код")
    public String getTumorCode() {return theTumorCode;}
    public void setTumorCode(String aTumorCode) {theTumorCode = aTumorCode;}
    /** Tumor код */
    private String theTumorCode ;
}
