package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;

/**N012 - классификатор соответствия маркеров диагнозам*/
@Entity
public class VocMarkers2Diagnoses_N012 extends VocBaseFederal {

    private Integer id_voc;
    private Integer id_marker;
    private String ds;

    public Integer getId_voc() {
        return id_voc;
    }
    public void setId_voc(Integer id_voc) {
        this.id_voc = id_voc;
    }

    public Integer getId_marker() {
        return id_marker;
    }
    public void setId_marker(Integer id_marker) {
        this.id_marker = id_marker;
    }

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }
}
