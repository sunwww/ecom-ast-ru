package ru.ecom.mis.ejb.form.birth;

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
public class ApgarEstimationForm extends InspectionForm {
	
	/** Время после рождения (мин) */
	@Comment("Время после рождения (мин)")
	@Persist @Required
	public String getPostNatalTime() {return thePostNatalTime;}
	public void setPostNatalTime(String aPostNatalTime) {thePostNatalTime = aPostNatalTime;}

	/** Сердцебиение */
	@Comment("Сердцебиение")
	@Persist @Required
	public Long getPalpitation() {return thePalpitation;}
	public void setPalpitation(Long aPalpitation) {thePalpitation = aPalpitation;}

	/** Дыхание */
	@Comment("Дыхание")
	@Persist @Required
	public Long getRespiration() {return theRespiration;}
	public void setRespiration(Long aRespiration) {theRespiration = aRespiration;}

	/** Окраска кожи */
	@Comment("Окраска кожи")
	@Persist @Required
	public Long getSkinColor() {return theSkinColor;}
	public void setSkinColor(Long aSkinColor) {theSkinColor = aSkinColor;}

	/** Тонус мышц */
	@Comment("Тонус мышц")
	@Persist @Required
	public Long getMuscleTone() {return theMuscleTone;}
	public void setMuscleTone(Long aMuscleTone) {theMuscleTone = aMuscleTone;}

	/** Рефлексы */
	@Comment("Рефлексы")
	@Persist @Required
	public Long getReflexes() {return theReflexes;}
	public void setReflexes(Long aReflexes) {theReflexes = aReflexes;}

	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	@Persist @Required
	public String getCommonMark() {return theCommonMark;}
	public void setCommonMark(String aCommonMark) {theCommonMark = aCommonMark;}

	/** Время после рождения (мин) */
	private String thePostNatalTime;
	/** Сердцебиение */
	private Long thePalpitation;
	/** Дыхание */
	private Long theRespiration;
	/** Окраска кожи */
	private Long theSkinColor;
	/** Тонус мышц */
	private Long theMuscleTone;
	/** Рефлексы */
	private Long theReflexes;
	/** Общая оценка (балл) */
	private String theCommonMark;

}
