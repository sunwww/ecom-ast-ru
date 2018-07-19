package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;
import javax.persistence.Table;

/**N003 - классификатор Tumor*/
@Entity
@Table(schema="SQLUser")
public class VocTumor_N003 extends VocBaseFederal {

    private Integer id_voc;
    private String ds;

    public Integer getId_voc() {
        return id_voc;
    }
    public void setId_voc(Integer id_voc) {
        this.id_voc = id_voc;
    }

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }
}
