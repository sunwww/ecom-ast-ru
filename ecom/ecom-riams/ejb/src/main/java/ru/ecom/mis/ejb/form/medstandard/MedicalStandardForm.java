package ru.ecom.mis.ejb.form.medstandard;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medstandard.MedicalStandard;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;


@EntityForm
@EntityFormPersistance(clazz= MedicalStandard.class)
@Comment("Стандарт оказания мед. помощи")
@WebTrail(comment = "Стандарт оказания мед. помощи", nameProperties= "name"
, view="entityView-mis_medicalStandard.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedicalStandard")

public class MedicalStandardForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	private String theName;
	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	@Persist @Required
	@DateString @DoDateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
	/** Дата начала действия */
	private String theStartDate;
	
	/** Дата окончания действия */
	@Persist
	@Comment("Дата окончания действия")
	@DateString @DoDateString
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания действия */
	private String theFinishDate;
	
	/** Родительский стандарт */
	@Comment("Родительский стандарт")
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}
	/** Родительский стандарт */
	private Long theParent;
}
