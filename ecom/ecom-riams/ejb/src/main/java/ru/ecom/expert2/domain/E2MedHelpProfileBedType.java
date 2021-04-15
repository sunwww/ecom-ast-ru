package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Accessors(prefix = "the")
public class E2MedHelpProfileBedType extends BaseEntity {

    /** Профиль мед. помощи */
    @OneToOne
    private VocE2MedHelpProfile theProfile ;

    /** Профиль койки V020 */
    @OneToOne
    private VocE2FondV020 theBedProfile ;

    /** Подтип коек */
    @OneToOne
    private VocBedSubType theSubType ;

    /** Дата действия с */
    private Date theStartDate ;

    /** Дата действия по */
    private Date theFinishDate ;
}
