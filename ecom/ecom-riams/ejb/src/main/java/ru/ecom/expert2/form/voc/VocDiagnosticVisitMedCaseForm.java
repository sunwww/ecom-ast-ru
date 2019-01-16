package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocDiagnosticVisitMedService;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = VocDiagnosticVisitMedService.class)
@Comment("Справочник КДП")
@WebTrail(comment = "Справочник КДП", nameProperties = "id", view = "entityView-e2_vocKdp.do")
@Parent(property = "visit", parentForm = VocDiagnosticVisitForm.class)
@EntityFormSecurityPrefix("/Policy/E2")
public class VocDiagnosticVisitMedCaseForm extends IdEntityForm {

    /** КДП */
    @Comment("КДП")
    @Persist @Required
    public Long getVisit() {return theVisit;}
    public void setVisit(Long aVisit) {theVisit = aVisit;}
    /** КДП */
    private Long theVisit ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    @Persist @Required
    public Long getMedService() {return theMedService;}
    public void setMedService(Long aMedService) {theMedService = aMedService;}
    /** Медицинская услуга */
    private Long theMedService ;
}