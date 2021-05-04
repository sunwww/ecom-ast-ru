package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.NewBornInfRiskEstimation;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Оценка риска бактериальной инфекции у новорожденных
 * @author oegorova
 *
 */


@EntityForm
@EntityFormPersistance(clazz= NewBornInfRiskEstimation.class)
@Comment("Оценка риска бактериальной инфекции у новорожденных")
@WebTrail(comment = "Оценка риска бактериальной инфекции у новорожденных", nameProperties= "id", view="entityParentView-preg_newBornInfRiskEstimation.do", list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/NewBornInfRiskEstimation")
@Setter
public class NewBornInfRiskEstimationForm extends InspectionForm {
	
	/** Длительность безводного периода */
	@Comment("Длительность безводного периода")
	@Persist @Required
	public Long getWaterlessDuration() {return waterlessDuration;}

	/** Температура матери в родах */
	@Comment("Температура матери в родах")
	@Persist @Required
	public Long getMotherTemperature() {return motherTemperature;}

	/** Характер амниотических вод */
	@Comment("Характер амниотических вод")
	@Persist @Required
	public Long getWaterNature() {return waterNature;}

	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	@Persist @Required
	public Long getApgar() {return apgar;}

	/** Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов  */
	@Comment("Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов")
	@Persist @Required
	public Long getMotherInfectiousDiseases() {return motherInfectiousDiseases;}

	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	@Persist @Required
	public Long getCommonMark() {return commonMark;}

	/** Масса тела ребенка, гр. */
	@Comment("Масса тела ребенка, гр.")
	@Persist @Required
	public Long getNewBornWeight() {return newBornWeight;}

	/** Масса тела ребенка, гр. */
	private Long newBornWeight;
	/** Длительность безводного периода */
	private Long waterlessDuration;
	/** Температура матери в родах */
	private Long motherTemperature;
	/** Характер амниотических вод */
	private Long waterNature;
	/** Оценка по Апгар */
	private Long apgar;
	/** Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов*/
	private Long motherInfectiousDiseases;
	/** Общая оценка (балл) */
	private Long commonMark;

}
