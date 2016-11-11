package ru.ecom.mis.ejb.form.expert.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCritDefect;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = VocQualityEstimationCritDefect.class)
@Comment("Справочник дефектов оценок качества")
@WebTrail(comment = "Дефекты оценок качества", nameProperties = {"code","name"}, view = "entityParentView-exp_vocCritDefect.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocQualityEstimationCritDefect")
@Parent(property = "criterion", parentForm = VocQualityEstimationCritForm.class)
public class VocQualityEstimationCritDefectForm extends IdEntityForm {
	/** Критерий оценки качества */
	@Comment("Критерий оценки качества")
	@Persist
	public Long getCriterion() {return theCriterion;}
	public void setCriterion(Long aCriterion) {theCriterion = aCriterion;}
	/** Критерий оценки качества */
	private Long theCriterion;
}
