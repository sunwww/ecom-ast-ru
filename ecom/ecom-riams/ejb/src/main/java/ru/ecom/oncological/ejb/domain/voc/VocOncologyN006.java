package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;
import javax.persistence.Table;

/**N006 - справочник соответсвия стадий (Stad+Tumor+Nodus+Metastasis)*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN006 extends VocBaseFederal {

    private Integer id_voc;
    private String ds;
    private Integer id_stad;
    private Integer id_tumor;
    private Integer id_nodus;
    private Integer id_metastasis;

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

    public Integer getId_stad() {
        return id_stad;
    }
    public void setId_stad(Integer id_stad) {
        this.id_stad = id_stad;
    }

    public Integer getId_tumor() {
        return id_tumor;
    }
    public void setId_tumor(Integer id_tumor) {
        this.id_tumor = id_tumor;
    }

    public Integer getId_nodus() {
        return id_nodus;
    }
    public void setId_nodus(Integer id_nodus) {
        this.id_nodus = id_nodus;
    }

    public Integer getId_metastasis() {
        return id_metastasis;
    }
    public void setId_metastasis(Integer id_metastasis) {
        this.id_metastasis = id_metastasis;
    }
}
