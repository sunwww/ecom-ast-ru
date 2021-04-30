package ru.ecom.expert2.domain.voc.federal;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Классификатор медицинских специальностей
 */
@Entity
@Getter
@Setter
public class VocE2FondV021 extends VocBaseFederal {
    @PrePersist
    void prePersist() {}

    @PreUpdate
    void preUpdate() {}

    /** Услуга по умолчанию для поликлиники */
    @Comment("Услуга по умолчанию для поликлиники")
    @OneToOne
    public VocMedService getDefaultMedService() {return defaultMedService;}
    private VocMedService defaultMedService;
    
    /** Услуга по умолчания (повторный визит) */
    @Comment("Услуга по умолчания (повторный визит)")
    @OneToOne
    public VocMedService getRepeatMedService() {return repeatMedService;}
    private VocMedService repeatMedService;

    /** Профиль мед. помощи для подачи по стационару */
    @Comment("Профиль мед. помощи для подачи по стационару")
    @OneToOne
    public VocE2MedHelpProfile getStacProfile() {return stacProfile;}
    private VocE2MedHelpProfile stacProfile;

    /** Профиль мед. помощи для подачи по поликлинике */
    @Comment("Профиль мед. помощи для подачи по поликлинике")
    @OneToOne
    public VocE2MedHelpProfile getPolicProfile() {return policProfile;}
    private VocE2MedHelpProfile policProfile;

    /** Признак подушевого финансирования специальности */
    private Boolean isPodushevoy;

    /** Стоматолог */
    private Boolean isDentalDoctor;
}
