package ru.ecom.mis.ejb.form.expert.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit_Diagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Справочник соответствия критерия оценки качества и диагноза
 */
@EntityForm
@EntityFormPersistance(clazz = VocQualityEstimationCrit_Diagnosis.class)
@Comment("Справочник соответствия критерия оценки качества и диагноза")
@WebTrail(comment = "Соответствие критерия и диагноза", nameProperties = {"VQECrit","vocIdc10"}, view = "entityParentView-exp_vocCrit.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocQualityEstimationCrit")
public class VocQualityEstimationCrit_DiagnosisForm extends IdEntityForm {
    /** Критерий оценки качества*/
    @Comment("Критерий оценки качества")
    @Persist
    @Required
    public Long getVQECrit() {
        return VQECrit;
    }
    public void setVQECrit(Long VQECrit) {
        this.VQECrit = VQECrit;
    }

    /** Диагноз*/
    @Comment("Диагноз")
    @Persist
    @Required
    public Long getVocIdc10() {
        return vocIdc10;
    }
    public void setVocIdc10(Long vocIdc10) {
        this.vocIdc10 = vocIdc10;
    }

    /** Критерий оценки качества */
    private Long VQECrit;
    /** Диагноз */
    private Long vocIdc10;
}