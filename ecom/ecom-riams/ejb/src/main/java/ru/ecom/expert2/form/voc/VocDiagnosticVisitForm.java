package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocDiagnosticVisit;
import ru.ecom.expert2.form.voc.federal.VocBaseFederalForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

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
    @Persist
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    /** Цена */
    private String theCost ;

    /** Мед услуга (для добавления) */
    @Comment("Мед услуга (для добавления)")
    public Long getMedServiceAdd() {return theMedServiceAdd;}
    public void setMedServiceAdd(Long aMedServiceAdd) {theMedServiceAdd = aMedServiceAdd;}
    /** Мед услуга (для добавления) */
    private Long theMedServiceAdd ;
}