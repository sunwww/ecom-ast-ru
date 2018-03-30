package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие профиля медицинской помощи и профиля коек
 */
@Entity
public class E2MedHelpProfileBedType extends BaseEntity {

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return theProfile;}
    public void setProfile(VocE2MedHelpProfile aProfile) {theProfile = aProfile;}
    /** Профиль мед. помощи */
    private VocE2MedHelpProfile theProfile ;

    /** Профиль коек */
    @Comment("Профиль коек")
    @OneToOne
    public VocBedType getBedType() {return theBedType;}
    public void setBedType(VocBedType aBedType) {theBedType = aBedType;}
    /** Профиль коек */
    private VocBedType theBedType ;
}
