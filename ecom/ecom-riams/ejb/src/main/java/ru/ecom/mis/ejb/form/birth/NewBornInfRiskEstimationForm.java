package ru.ecom.mis.ejb.form.birth;

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
public class NewBornInfRiskEstimationForm extends InspectionForm {
	
	/** Длительность безводного периода */
	@Comment("Длительность безводного периода")
	@Persist @Required
	public Long getWaterlessDuration() {return theWaterlessDuration;}
	public void setWaterlessDuration(Long aWaterlessDuration) {theWaterlessDuration = aWaterlessDuration;}
	
	/** Температура матери в родах */
	@Comment("Температура матери в родах")
	@Persist @Required
	public Long getMotherTemperature() {return theMotherTemperature;}
	public void setMotherTemperature(Long aMotherTemperature) {theMotherTemperature = aMotherTemperature;}

	/** Характер амниотических вод */
	@Comment("Характер амниотических вод")
	@Persist @Required
	public Long getWaterNature() {return theWaterNature;}
	public void setWaterNature(Long aWaterNature) {theWaterNature = aWaterNature;}
	
	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	@Persist @Required
	public Long getApgar() {return theApgar;}
	public void setApgar(Long aApgar) {theApgar = aApgar;}
	
	/** Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов  */
	@Comment("Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов")
	@Persist @Required
	public Long getMotherInfectiousDiseases() {return theMotherInfectiousDiseases;}
	public void setMotherInfectiousDiseases(Long aMotherInfectiousDiseases) {theMotherInfectiousDiseases = aMotherInfectiousDiseases;}

	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	@Persist @Required
	public Long getCommonMark() {return theCommonMark;}
	public void setCommonMark(Long aCommonMark) {theCommonMark = aCommonMark;}
	
	/** Масса тела ребенка, гр. */
	@Comment("Масса тела ребенка, гр.")
	@Persist @Required
	public Long getNewBornWeight() {return theNewBornWeight;}
	public void setNewBornWeight(Long aNewBornWeight) {theNewBornWeight = aNewBornWeight;}

	/** Масса тела ребенка, гр. */
	private Long theNewBornWeight;
	/** Длительность безводного периода */
	private Long theWaterlessDuration;
	/** Температура матери в родах */
	private Long theMotherTemperature;
	/** Характер амниотических вод */
	private Long theWaterNature;
	/** Оценка по Апгар */
	private Long theApgar;
	/** Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов*/
	private Long theMotherInfectiousDiseases;
	/** Общая оценка (балл) */
	private Long theCommonMark;

}
