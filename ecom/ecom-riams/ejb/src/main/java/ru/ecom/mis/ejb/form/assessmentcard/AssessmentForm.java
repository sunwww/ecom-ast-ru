package ru.ecom.mis.ejb.form.assessmentcard;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.assessmentcard.Assessment;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Assessment.class)
@Comment("Итоговый балл")
@WebTrail(comment = "Итоговый балл", nameProperties= "name", view="entityView-mis_assessmentCardTemplate.do" ,list = "entityList-mis_assessmentCardTemplate.do")
@Parent(property = "assessmentCard", parentForm=AssessmentCardTemplateForm.class) 

@EntityFormSecurityPrefix("/Policy/Mis/AssessmentCard")
@Setter
public class AssessmentForm extends IdEntityForm{
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return name;}
	private String name;
	
	
	/** Минимальное кол-во баллов */
	@Comment("Минимальное кол-во баллов")
	@Persist @Required
	public Integer getMinBall() {return minBall;}
	/** Минимальное кол-во баллов */
	private Integer minBall;

	/** Максимальное количество баллов */
	@Comment("Максимальное количество баллов")
	@Persist @Required
	public Integer getMaxBall() {return maxBall;}
	/** Максимальное количество баллов */
	private Integer maxBall;

	/** Тип карты оценки */
	@Comment("Тип карты оценки")
	@Persist
	public Long getAssessmentCard() {return assessmentCard;}
	/** Тип карты оценки */
	private Long assessmentCard;

}
