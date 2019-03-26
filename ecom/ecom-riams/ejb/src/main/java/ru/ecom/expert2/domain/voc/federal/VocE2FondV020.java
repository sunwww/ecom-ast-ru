package ru.ecom.expert2.domain.voc.federal;

import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Классификатор коек
 */
@Entity
public class VocE2FondV020 extends VocBaseFederal {
    @PrePersist
    void prePersist() {}

    @PreUpdate
    void preUpdate() {}

    /** Услуга по профилю для стационар по умолчанию */
    @Comment("Услуга по профилю для стационар по умолчанию")
    @OneToOne
    public VocMedService getDefaultStacMedService() {return theDefaultStacMedService;}
    public void setDefaultStacMedService(VocMedService aDefaultStacMedService) {theDefaultStacMedService = aDefaultStacMedService;}
    /** Услуга по профилю для стационар по умолчанию */
    private VocMedService theDefaultStacMedService ;
}
