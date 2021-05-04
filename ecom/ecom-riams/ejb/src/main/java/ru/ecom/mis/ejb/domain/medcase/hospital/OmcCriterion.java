package ru.ecom.mis.ejb.domain.medcase.hospital;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocClassificationCriterion;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
/**Классификационные критерии по случаю*/
@Getter
@Setter
public class OmcCriterion extends BaseEntity {
    /** Случай лечения */
    @Comment("Случай лечения")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    private MedCase medCase ;

    /** Критерий */
    @Comment("Критерий")
    @OneToOne
    public VocClassificationCriterion getCriterion() {return criterion;}
    private VocClassificationCriterion criterion ;
}
