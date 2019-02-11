package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2MedHelpProfileBedType;
import ru.ecom.expert2.form.voc.VocE2MedHelpProfileForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


/**
 * Соответствие профиля медицинской помощи и профиля коек
 */
@EntityForm
@EntityFormPersistance(clazz = E2MedHelpProfileBedType.class)
@Comment("Заполнение")
@WebTrail(comment = "Заполнение", nameProperties = "id", view = "entityParentView-e2_medHelpBedType.do")
@Parent(property = "profile", parentForm = VocE2MedHelpProfileForm.class)
@EntityFormSecurityPrefix("/Policy/E2")

public class E2MedHelpProfileBedTypeForm extends IdEntityForm {

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist
    public Long getProfile() {return theProfile;}
    public void setProfile(Long aProfile) {theProfile = aProfile;}
    /** Профиль мед. помощи */
    private Long theProfile ;

    /** Профиль коек */
    @Comment("Профиль коек")
    @Persist
    public Long getBedType() {return theBedType;}
    public void setBedType(Long aBedType) {theBedType = aBedType;}
    /** Профиль коек */
    private Long theBedType ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @Persist
    public Long getSubType() {return theSubType;}
    public void setSubType(Long aSubType) {theSubType = aSubType;}
    /** Подтип коек */
    private Long theSubType ;
}
