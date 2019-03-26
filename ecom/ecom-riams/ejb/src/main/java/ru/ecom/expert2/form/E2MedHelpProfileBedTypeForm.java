package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2MedHelpProfileBedType;
import ru.ecom.expert2.form.voc.federal.VocE2FondV020Form;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;


/**
 * Соответствие профиля медицинской помощи и профиля коек
 */
@EntityForm
@EntityFormPersistance(clazz = E2MedHelpProfileBedType.class)
@Comment("Заполнение")
@WebTrail(comment = "Заполнение", nameProperties = "id", view = "entityParentView-e2_medHelpBedType.do")
@Parent(property = "bedProfile", parentForm = VocE2FondV020Form.class)
@EntityFormSecurityPrefix("/Policy/E2")
public class E2MedHelpProfileBedTypeForm extends IdEntityForm {

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist @Required
    public Long getProfile() {return theProfile;}
    public void setProfile(Long aProfile) {theProfile = aProfile;}
    /** Профиль мед. помощи */
    private Long theProfile ;

    /** Профиль койки V020 */
    @Comment("Профиль койки V020")
    @Persist @Required
    public Long getBedProfile() {return theBedProfile;}
    public void setBedProfile(Long aBedProfile) {theBedProfile = aBedProfile;}
    /** Профиль койки V020 */
    private Long theBedProfile ;

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

    /** Дата действия с */
    @Comment("Дата действия с")
    @Persist @DateString
    @DoDateString
    @Required
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    /** Дата действия с */
    private String theStartDate ;

    /** Дата действия по */
    @Comment("Дата действия по")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата действия по */
    private String theFinishDate ;

    public Long getNewMedHelpProfile() {return theNewMedHelpProfile;}
    public void setNewMedHelpProfile(Long aNewMedHelpProfile) {theNewMedHelpProfile = aNewMedHelpProfile;}
    private Long theNewMedHelpProfile ;
}
