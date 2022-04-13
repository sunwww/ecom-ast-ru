package ru.ecom.expert2.domain.voc.federal;

import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
/**
 * Соответствие кода препарата схеме лечения
 */
@Setter
public class VocE2FondV033 extends BaseEntity {

    private VocE2FondV032 groupSchema;
    private VocE2FondN020 drug;

    @ManyToOne
    public VocE2FondV032 getGroupSchema() {
        return groupSchema;
    }

    @ManyToOne
    public VocE2FondN020 getDrug() {
        return drug;
    }
}
