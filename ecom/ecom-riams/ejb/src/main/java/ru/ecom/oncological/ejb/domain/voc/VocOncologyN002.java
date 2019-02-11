package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;
import javax.persistence.Table;

/**N002 - классификатор стадий Stad*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN002 extends VocBaseFederal {

    private String ds;

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }
}
