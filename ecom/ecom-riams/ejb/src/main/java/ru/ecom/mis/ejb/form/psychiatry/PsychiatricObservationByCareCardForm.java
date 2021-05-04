package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PsychiaticObservation;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PsychiaticObservation.class)
@Comment("Динамика наблюдений (АДН, АПЛ)")
@WebTrail(comment = "Динамика наблюдений (АДН, АПЛ)", nameProperties = "id", view = "entityParentView-psych_careobservation.do")
@Parent(property = "careCard", parentForm = PsychiatricCareCardForm.class, orderBy = "startDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation")
@Setter
public class PsychiatricObservationByCareCardForm extends PsychiatricObservationForm {
    /**
     * Диспансерная группа
     */
    @Comment("Диспансерная группа")
    @Persist
    @Required
    public Long getDispensaryGroup() {
        return dispensaryGroup;
    }

    @Comment("Статья уголовного кодекса")
    @Persist
    @Required
    public Long getCriminalCodeArticle() {
        return criminalCodeArticle;
    }

    /**
     * Статья уголовного кодекса
     */
    private Long criminalCodeArticle;

    /**
     * Диспансерная группа
     */
    private Long dispensaryGroup;

}
