package ru.ecom.expert2.form;

import lombok.Setter;
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
@Setter
public class E2MedHelpProfileBedTypeForm extends IdEntityForm {

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist @Required
    public Long getProfile() {return profile;}
    /** Профиль мед. помощи */
    private Long profile ;

    /** Профиль койки V020 */
    @Comment("Профиль койки V020")
    @Persist @Required
    public Long getBedProfile() {return bedProfile;}
    /** Профиль койки V020 */
    private Long bedProfile ;

    /** Профиль коек */
    @Comment("Профиль коек")
    @Persist
    public Long getBedType() {return bedType;}
    /** Профиль коек */
    private Long bedType ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @Persist
    public Long getSubType() {return subType;}
    /** Подтип коек */
    private Long subType ;

    /** Дата действия с */
    @Comment("Дата действия с")
    @Persist @DateString
    @DoDateString
    @Required
    public String getStartDate() {return startDate;}
    /** Дата действия с */
    private String startDate ;

    /** Дата действия по */
    @Comment("Дата действия по")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return finishDate;}
    /** Дата действия по */
    private String finishDate ;

    public Long getNewMedHelpProfile() {return newMedHelpProfile;}
    private Long newMedHelpProfile ;
}
