package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2DrugEntry;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Использование лек. препарата в случае
 */
@EntityForm
@EntityFormPersistance(clazz = E2DrugEntry.class)
@Comment("Использование лек. препарата в ослучае")
@WebTrail(comment = "Использование лек. препарата в случае", nameProperties = "id", view = "entityView-e2_drugEntry.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "entry", parentForm = E2EntryForm.class)
@Setter
public class E2DrugEntryForm extends IdEntityForm {

    private Long entry;
    private String injectDate;
    private Long drugGroupSchema;
    private Long drug;
    private Long injectUnit;
    private String injectAmount;
    private Integer injectNumber;
    private Long injectMethod;

    @Persist
    public Long getEntry() {
        return entry;
    }

    @Persist
    @Comment("Дата введения препарата")
    @DateString @DoDateString
    @Required
    public String getInjectDate() {
        return injectDate;
    }

    @Persist
    @Comment("Схема лечения V032")
    @Required
    public Long getDrugGroupSchema() {
        return drugGroupSchema;
    }

    @Persist
    @Comment("Лекарственный препарат N020")
    public Long getDrug() {
        return drug;
    }

    @Persist
    @Comment("Единица измерения дозы введения препарата V034")
    public Long getInjectUnit() {
        return injectUnit;
    }

    @Persist
    @Comment("Доза введения препарата")
    public String getInjectAmount() {
        return injectAmount;
    }

    @Persist
    @Comment("Количество введения препарата")
    public Integer getInjectNumber() {
        return injectNumber;
    }

    @Persist
    @Comment("Метод введения препарата V035")
    public Long getInjectMethod() {
        return injectMethod;
    }

    private Long vocSchema;

    @Comment("Справочник схема чего-то там")
    public Long getVocSchema() {
        return vocSchema;
    }
}
