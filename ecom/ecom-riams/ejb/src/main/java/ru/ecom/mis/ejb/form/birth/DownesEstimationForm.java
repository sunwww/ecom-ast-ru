package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.DownesEstimation;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Оценка респираторного дистресса новорожденного по Downes
 * @author oegorova
 *
 */
@EntityForm
@EntityFormPersistance(clazz= DownesEstimation.class)
@Comment("Оценка респираторного дистресса новорожденного по Downes")
@WebTrail(comment = "Оценка респираторного дистресса новорожденного по Downes", nameProperties= "id", view="entitySubclassView-preg_inspection.do" ,list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/DownesEstimation")
@Setter
public class DownesEstimationForm extends InspectionForm {
	
	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	@Persist @Required
	public Long getRespirationRate() {return respirationRate;}

	/** Цианоз */
	@Comment("Цианоз")
	@Persist @Required
	public Long getCyanosis() {return cyanosis;}

	/** Втяжение межреберных промежутков */
	@Comment("Втяжение межреберных промежутков")
	@Persist @Required
	public Long getIntercostalRetraction() {return intercostalRetraction;}

	/** Затрудненный выдох */
	@Comment("Затрудненный выдох")
	@Persist @Required
	public Long getDifficultExhalation() {return difficultExhalation;}

	/** Аускультация */
	@Comment("Аускультация")
	@Persist @Required
	public Long getAuscultation() {return auscultation;}

	/** Общая оценка */
	@Comment("Общая оценка")
	@Persist @Required
	public Long getCommonMark() {return commonMark;}

	/** Частота дыхательных движений */
	private Long respirationRate;
	/** Цианоз */
	private Long cyanosis;
	/** Втяжение межреберных промежутков */
	private Long intercostalRetraction;
	/** Затрудненный выдох */
	private Long difficultExhalation;
	/** Аускультация */
	private Long auscultation;
	/** Общая оценка */
	private Long commonMark;

}
