package ru.ecom.mis.ejb.domain.medcase.hospital;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocClassificationCriterion;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
/**Классификационные критерии по случаю*/
public class OmcCriterion extends BaseEntity {
    /** Случай лечения */
    @Comment("Случай лечения")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    /** Случай лечения */
    private MedCase theMedCase ;

    /** Критерий */
    @Comment("Критерий")
    @OneToOne
    public VocClassificationCriterion getCriterion() {return theCriterion;}
    public void setCriterion(VocClassificationCriterion aCriterion) {theCriterion = aCriterion;}
    /** Критерий */
    private VocClassificationCriterion theCriterion ;
}
