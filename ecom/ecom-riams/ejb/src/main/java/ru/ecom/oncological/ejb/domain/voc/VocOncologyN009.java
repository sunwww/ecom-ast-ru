package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;
import javax.persistence.Table;

/**N009 - классификатор соответсвия гистологии диагнозам*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN009 extends VocBaseFederal {

    private Integer id_voc;
    private String ds;
    private Integer id_histology;

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

    public Integer getId_histology() {
        return id_histology;
    }
    public void setId_histology(Integer id_histology) {
        this.id_histology = id_histology;
    }
}
