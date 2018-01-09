package ru.ecom.mis.ejb.form.expert;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.expert.QualityEstimation;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 13.12.2017.
 */@EntityForm
@EntityFormPersistance(clazz = QualityEstimation.class)
@Comment("Оценочные баллы")
@WebTrail(comment = "Оценочные баллы", nameProperties = "id", view = "entityParentView-expert_qualityEstimationForm.do")
@EntityFormSecurityPrefix("/Policy/Mis/Order203")
@Parent(property = "card", parentForm =QualityEstimationCardForm.class)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(QualityEstimationPrepareCreateInterceptor.class)
)
public class QualityEstimationDraftForm extends QualityEstimationForm {

}