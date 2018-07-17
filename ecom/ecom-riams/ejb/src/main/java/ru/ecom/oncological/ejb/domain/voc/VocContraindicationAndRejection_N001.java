package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;

/**​N001 - классификатор противопоказаний и отказов*/
@Entity
public class VocContraindicationAndRejection_N001 extends VocBaseFederal {

    private Integer id_voc;

    public Integer getId_voc() {
        return id_voc;
    }
    public void setId_voc(Integer id_voc) {
        this.id_voc = id_voc;
    }
}
