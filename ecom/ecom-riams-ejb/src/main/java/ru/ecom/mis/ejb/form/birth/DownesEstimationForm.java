package ru.ecom.mis.ejb.form.birth;

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
public class DownesEstimationForm extends InspectionForm {
	
	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	@Persist @Required
	public Long getRespirationRate() {return theRespirationRate;}
	public void setRespirationRate(Long aRespirationRate) {theRespirationRate = aRespirationRate;}
	
	/** Цианоз */
	@Comment("Цианоз")
	@Persist @Required
	public Long getCyanosis() {return theCyanosis;}
	public void setCyanosis(Long aCyanosis) {theCyanosis = aCyanosis;}
	
	/** Втяжение межреберных промежутков */
	@Comment("Втяжение межреберных промежутков")
	@Persist @Required
	public Long getIntercostalRetraction() {return theIntercostalRetraction;}
	public void setIntercostalRetraction(Long aIntercostalRetraction) {theIntercostalRetraction = aIntercostalRetraction;}
	
	/** Затрудненный выдох */
	@Comment("Затрудненный выдох")
	@Persist @Required
	public Long getDifficultExhalation() {return theDifficultExhalation;}
	public void setDifficultExhalation(Long aDifficultExhalation) {theDifficultExhalation = aDifficultExhalation;}
	
	/** Аускультация */
	@Comment("Аускультация")
	@Persist @Required
	public Long getAuscultation() {return theAuscultation;}
	public void setAuscultation(Long aAuscultation) {theAuscultation = aAuscultation;}

	/** Общая оценка */
	@Comment("Общая оценка")
	@Persist @Required
	public Long getCommonMark() {return theCommonMark;}
	public void setCommonMark(Long aCommonMark) {theCommonMark = aCommonMark;}

	/** Частота дыхательных движений */
	private Long theRespirationRate;
	/** Цианоз */
	private Long theCyanosis;
	/** Втяжение межреберных промежутков */
	private Long theIntercostalRetraction;
	/** Затрудненный выдох */
	private Long theDifficultExhalation;
	/** Аускультация */
	private Long theAuscultation;
	/** Общая оценка */
	private Long theCommonMark;

}
