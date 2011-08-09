package ru.ecom.mis.ejb.form.medcase.death;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathReason;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Причина смерти")
@EntityForm
@EntityFormPersistance(clazz=DeathReason.class)
@WebTrail(comment = "Причина смерти", nameProperties= "reasonTypeInfo", view="entityParentView-stac_deathReason.do")
@Parent(property="deathCase", parentForm= DeathCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/DeathCase/Reason")
public class DeathReasonForm extends IdEntityForm{
	/** Причина */
	@Comment("Причина")
	@Persist @Required
	public String getReason() {return theReason;}
	public void setReason(String aReason) {theReason = aReason;}

	/** Тип причины смерти */
	@Comment("Тип причины смерти")
	@Persist @Required
	public Long getReasonType() {return theReasonType;}
	public void setReasonType(Long aReasonType) {theReasonType = aReasonType;}

	/** Код МКБ */
	@Comment("Код МКБ")
	@Persist @Required
	public Long getIdc10() {return theIdc10;	}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Случай смерти */
	@Comment("Случай смерти")
	@Persist @Required 
	public Long getDeathCase() {return theDeathCase;}
	public void setDeathCase(Long aDeathCase) {theDeathCase = aDeathCase;}

	/** Тип причины смерти инфо */
	@Comment("Тип причины смерти инфо")
	@Persist
	public String getReasonTypeInfo() {return theReasonTypeInfo;}
	public void setReasonTypeInfo(String aReasonTypeInfo) {theReasonTypeInfo = aReasonTypeInfo;}

	/** Код МКБ 10 инфо */
	@Comment("Код МКБ 10 инфо")
	@Persist
	public String getIdc10Info() {return theIdc10Info;}
	public void setIdc10Info(String aIdc10Info) {theIdc10Info = aIdc10Info;}

	/** Код МКБ 10 инфо */
	private String theIdc10Info;
	/** Тип причины смерти инфо */
	private String theReasonTypeInfo;
	/** Случай смерти */
	private Long theDeathCase;
	/** Код МКБ */
	private Long theIdc10;
	/** Тип причины смерти */
	private Long theReasonType;
	/** Причина */
	private String theReason;

}
