package ru.ecom.expert2.domain.voc.federal;

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
public class VocE2FondV021 extends VocBaseFederal {
    @PrePersist
    void prePersist() {}

    @PreUpdate
    void preUpdate() {}

    /** Услуга по умолчанию для поликлиники */
    @Comment("Услуга по умолчанию для поликлиники")
    @OneToOne
    public VocMedService getDefaultMedService() {return theDefaultMedService;}
    public void setDefaultMedService(VocMedService aDefaultMedService) {theDefaultMedService = aDefaultMedService;}
    /** Услуга по умолчанию для поликлиники */
    private VocMedService theDefaultMedService ;
    
    /** Услуга по умолчания (повторный визит) */
    @Comment("Услуга по умолчания (повторный визит)")
    @OneToOne
    public VocMedService getRepeatMedService() {return theRepeatMedService;}
    public void setRepeatMedService(VocMedService aRepeatMedService) {theRepeatMedService = aRepeatMedService;}
    /** Услуга по умолчания (повторный визит) */
    private VocMedService theRepeatMedService ;

    /** Профиль мед. помощи для подачи по стационару */
    @Comment("Профиль мед. помощи для подачи по стационару")
    @OneToOne
    public VocE2MedHelpProfile getStacProfile() {return theStacProfile;}
    public void setStacProfile(VocE2MedHelpProfile aStacProfile) {theStacProfile = aStacProfile;}
    /** Профиль мед. помощи для подачи по стационару */
    private VocE2MedHelpProfile theStacProfile ;

    /** Профиль мед. помощи для подачи по поликлинике */
    @Comment("Профиль мед. помощи для подачи по поликлинике")
    @OneToOne
    public VocE2MedHelpProfile getPolicProfile() {return thePolicProfile;}
    public void setPolicProfile(VocE2MedHelpProfile aPolicProfile) {thePolicProfile = aPolicProfile;}
    /** Профиль мед. помощи для подачи по поликлинике */
    private VocE2MedHelpProfile thePolicProfile ;

    /** Признак подушевого финансирования специальности */
    @Comment("Признак подушевого финансирования специальности")
    public Boolean getIsPodushevoy() {return theIsPodushevoy;}
    public void setIsPodushevoy(Boolean aIsPodushevoy) {theIsPodushevoy = aIsPodushevoy;}
    private Boolean theIsPodushevoy ;
}
