package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.ApgarEstimation;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Оценка новорожденного по Апгар
 * @author oegorova
 *
 */


@EntityForm
@EntityFormPersistance(clazz= ApgarEstimation.class)
@Comment("Оценка новорожденного по Апгар")
@WebTrail(comment = "Оценка новорожденного по Апгар", nameProperties= "id", view="entitySubclassView-preg_inspection.do" ,list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/ApgarEstimation")
@Setter
public class ApgarEstimationForm extends InspectionForm {
	
	/** Время после рождения (мин) */
	@Comment("Время после рождения (мин)")
	@Persist @Required
	public String getPostNatalTime() {return postNatalTime;}

	/** Сердцебиение */
	@Comment("Сердцебиение")
	@Persist @Required
	public Long getPalpitation() {return palpitation;}

	/** Дыхание */
	@Comment("Дыхание")
	@Persist @Required
	public Long getRespiration() {return respiration;}

	/** Окраска кожи */
	@Comment("Окраска кожи")
	@Persist @Required
	public Long getSkinColor() {return skinColor;}

	/** Тонус мышц */
	@Comment("Тонус мышц")
	@Persist @Required
	public Long getMuscleTone() {return muscleTone;}

	/** Рефлексы */
	@Comment("Рефлексы")
	@Persist @Required
	public Long getReflexes() {return reflexes;}

	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	@Persist @Required
	public String getCommonMark() {return commonMark;}

	/** Время после рождения (мин) */
	private String postNatalTime;
	/** Сердцебиение */
	private Long palpitation;
	/** Дыхание */
	private Long respiration;
	/** Окраска кожи */
	private Long skinColor;
	/** Тонус мышц */
	private Long muscleTone;
	/** Рефлексы */
	private Long reflexes;
	/** Общая оценка (балл) */
	private String commonMark;

}
