package ru.ecom.expert2.form;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expert2.domain.E2MedHelpProfileBedType;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.form.interceptors.EntryListCreateInterceptor;
import ru.ecom.expert2.form.voc.VocE2MedHelpProfileForm;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return theProfile;}
    public void setProfile(VocE2MedHelpProfile aProfile) {theProfile = aProfile;}
    /** Профиль мед. помощи */
    private VocE2MedHelpProfile theProfile ;

    /** Профиль коек */
    @Comment("Профиль коек")
    @OneToOne
    public VocBedType getBedType() {return theBedType;}
    public void setBedType(VocBedType aBedType) {theBedType = aBedType;}
    /** Профиль коек */
    private VocBedType theBedType ;
}
