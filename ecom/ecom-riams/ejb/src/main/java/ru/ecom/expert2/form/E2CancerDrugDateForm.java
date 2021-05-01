package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerDrugDate;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Использование лек. препарата в онкослучае
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerDrugDate.class)
@Comment("Использование лек. препарата в онкослучае")
@WebTrail(comment = "Использование лек. препарата в онкослучае", nameProperties = "id", view = "entityView-e2_cancerDrugDate.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "drug", parentForm = E2CancerDrugForm.class)
@Setter
public class E2CancerDrugDateForm extends IdEntityForm {

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @Persist @Required
    public Long getDrug() {return drug;}
    /** Лекарственный препарат */
    private Long drug ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @Persist
    @DateString @DoDateString @Required
    public String getDate() {return date;}
    /** Дата введения препарата */
    private String date ;

}
