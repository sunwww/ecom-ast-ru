package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerDrug;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Использование лек. препарата в онкослучае
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerDrug.class)
@Comment("Использование лек. препарата в онкослучае")
@WebTrail(comment = "Использование лек. препарата в онкослучае", nameProperties = "id", view = "entityView-e2_cancerDrug.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "cancerEntry", parentForm = E2CancerEntryForm.class)
public class E2CancerDrugForm extends IdEntityForm {

    /** Онкологический случай */
    @Comment("Онкологический случай")
    @Persist
    public Long getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(Long aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Онкологический случай */
    private Long theCancerEntry ;

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @Persist @Required
    public Long getDrug() {return theDrug;}
    public void setDrug(Long aDrug) {theDrug = aDrug;}
    /** Лекарственный препарат */
    private Long theDrug ;


    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @DateString @DoDateString
    public String getDate() {return theDate;}
    public void setDate(String aDate) {theDate = aDate;}
    /** Дата введения препарата */
    private String theDate ;

}
