package ru.ecom.expert2.domain.voc.federal;

import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Классификатор медицинских специальностей
 */
@Entity
public class VocE2FondV015 extends VocBaseFederal {
    @PrePersist
    void prePersist() {}

    @PreUpdate
    void preUpdate() {}

    /** Родитель */
    @Comment("Родитель")
    public String getParent() {return theParent;}
    public void setParent(String aParent) {theParent = aParent;}
    /** Родитель */
    private String theParent ;

    /** ОКСО */
    @Comment("ОКСО")
    public String getOkso() {return theOkso;}
    public void setOkso(String aOkso) {theOkso = aOkso;}
    /** ОКСО */
    private String theOkso ;

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

    /** Главный специалист в диагностическом СПО */
    @Comment("Главный специалист в диагностическом СПО")
    public Boolean getIsKdoChief() {return theIsKdoChief;}
    public void setIsKdoChief(Boolean aIsKdoChief) {theIsKdoChief = aIsKdoChief;}
    /** Главный специалист в диагностическом СПО */
    private Boolean theIsKdoChief ;

    /** Мед. специальность V021 по профилю */
    @Comment("Мед. специальность V021 по профилю")
    @OneToOne
    public VocE2FondV021 getMedSpecV021() {return theMedSpecV021;}
    public void setMedSpecV021(VocE2FondV021 aMedSpecV021) {theMedSpecV021 = aMedSpecV021;}
    /** Мед. специальность по профилю */
    private VocE2FondV021 theMedSpecV021 ;
}
