package ru.ecom.expert2.domain.voc.federal;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
/**
 * Сочетания схемы лечения и группы препаратов
 */
@Setter
public class VocE2FondV032 extends VocBaseFederal {

    private VocE2FondV030 schema;
    private VocE2FondV031 drugGroup;

    @ManyToOne
    public VocE2FondV030 getSchema() {
        return schema;
    }

    @ManyToOne
    public VocE2FondV031 getDrugGroup() {
        return drugGroup;
    }
}
