package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocDiagnosticVisit;
import ru.ecom.expert2.form.voc.federal.VocBaseFederalForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = VocDiagnosticVisit.class)
@Comment("Справочник КДП")
@WebTrail(comment = "Справочник КДП", nameProperties = "id", view = "entityView-e2_vocKdp.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocDiagnosticVisitForm extends VocBaseFederalForm {

    /** Цена  */
    @Comment("Цена")
    @Persist @Required
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    /** Цена */
    private String theCost ;

    /** Профиль основной мед. специальности V021*/
    @Comment("Профиль основной мед. специальности V021")
    @Persist @Required
    public Long getSpeciality() {return theSpeciality;}
    public void setSpeciality(Long aSpeciality) {theSpeciality = aSpeciality;}
    /** Профиль основной мед. специальности */
    private Long theSpeciality ;

    /** Список подходящих услуг */
    @Comment("Список подходящих услуг")
    @Persist
    public String getMedServicesList() {return theMedServicesList;}
    public void setMedServicesList(String aMedServicesList) {theMedServicesList = aMedServicesList;}
    /** Список подходящих услуг */
    private String theMedServicesList ;
}