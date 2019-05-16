package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.hospital.OmcCriterion;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Классификационные критерии
 */
@EntityForm
@EntityFormPersistance(clazz = OmcCriterion.class)
@Comment("Классификационные критерии")
@WebTrail(comment = "Классификационные критерии", nameProperties = "id", view = "entityView-stac_omcCriterion.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcCriterion")
@Parent(property = "medCase", parentForm = HospitalMedCaseForm.class)
public class OmcCriterionForm extends IdEntityForm {
    /** Случай лечения */
    @Comment("Случай лечения")
    @Persist @Required
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    /** Случай лечения */
    private Long theMedCase ;

    /** Критерий */
    @Comment("Критерий")
    @Persist @Required
    public Long getCriterion() {return theCriterion;}
    public void setCriterion(Long aCriterion) {theCriterion = aCriterion;}
    /** Критерий */
    private Long theCriterion ;

}
