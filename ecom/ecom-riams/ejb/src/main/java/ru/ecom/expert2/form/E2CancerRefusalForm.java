package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerRefusal;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerRefusal.class)
@Comment("Противопоказание к онк. лечение")
@WebTrail(comment = "Противопоказание к онк. лечение", nameProperties = "id", view = "entityView-e2_cancerRefusal.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "cancerEntry", parentForm = E2CancerEntryForm.class)
public class E2CancerRefusalForm extends IdEntityForm {
    /** Случай рака */
    @Comment("Случай рака")
    @Persist
    public Long getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(Long aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Случай рака */
    private Long theCancerEntry ;

    /** Код противопоказания */
    @Comment("Код противопоказания")
    @Persist
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код противопоказания */
    private String theCode ;

    /** Дата регистрации противопоказания */
    @Comment("Дата регистрации противопоказания")
    @Persist
    @DateString @DoDateString
    public String getDate() {return theDate;}
    public void setDate(String aDate) {theDate = aDate;}
    /** Дата регистрации противопоказания */
    private String theDate ;
}
