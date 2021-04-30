package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class E2MedHelpProfileBedType extends BaseEntity {

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return profile;}
    private VocE2MedHelpProfile profile;

    /** Профиль коек */
    @Comment("Профиль коек")
    @OneToOne
    @Deprecated
    public VocBedType getBedType() {return bedType;}
    private VocBedType bedType;

    /** Профиль койки V020 */
    @Comment("Профиль койки V020")
    @OneToOne
    public VocE2FondV020 getBedProfile() {return bedProfile;}
    private VocE2FondV020 bedProfile;

    /** Подтип коек */
    @Comment("Подтип коек")
    @OneToOne
    public VocBedSubType getSubType() {return subType;}
    private VocBedSubType subType;

    /** Дата действия с */
    private Date startDate;

    /** Дата действия по */
    private Date finishDate;
}
