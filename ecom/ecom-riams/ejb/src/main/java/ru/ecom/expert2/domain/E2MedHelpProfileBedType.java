package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV020;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

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
    @Deprecated
    public VocBedType getBedType() {return theBedType;}
    public void setBedType(VocBedType aBedType) {theBedType = aBedType;}
    /** Профиль коек */
    private VocBedType theBedType ;

    /** Профиль койки V020 */
    @Comment("Профиль койки V020")
    @OneToOne
    public VocE2FondV020 getBedProfile() {return theBedProfile;}
    public void setBedProfile(VocE2FondV020 aBedProfile) {theBedProfile = aBedProfile;}
    /** Профиль койки V020 */
    private VocE2FondV020 theBedProfile ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @OneToOne
    public VocBedSubType getSubType() {return theSubType;}
    public void setSubType(VocBedSubType aSubType) {theSubType = aSubType;}
    /** Подтип коек */
    private VocBedSubType theSubType ;

    /** Дата действия с */
    @Comment("Дата действия с")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата действия с */
    private Date theStartDate ;

    /** Дата действия по */
    @Comment("Дата действия по")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата действия по */
    private Date theFinishDate ;
}
