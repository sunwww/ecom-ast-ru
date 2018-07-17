package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;

/**N014 - классификатор типов хирургического лечения*/
@Entity
public class VocTypeSurgTreatment_N014  extends VocBaseFederal {

    private Integer id_voc;

    public Integer getId_voc() {
        return id_voc;
    }
    public void setId_voc(Integer id_voc) {
        this.id_voc = id_voc;
    }
}
