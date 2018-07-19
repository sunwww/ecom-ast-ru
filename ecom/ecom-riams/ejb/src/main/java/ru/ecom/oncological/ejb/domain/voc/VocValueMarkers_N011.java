package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;
import javax.persistence.Table;

/**N011 - классификатор значений маркеров*/
@Entity
@Table(schema="SQLUser")
public class VocValueMarkers_N011 extends VocBaseFederal {

    private Integer id_voc;
    private Integer id_marker;

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
}
