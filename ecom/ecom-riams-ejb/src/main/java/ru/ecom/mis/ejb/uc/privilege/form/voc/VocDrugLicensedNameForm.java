package ru.ecom.mis.ejb.uc.privilege.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocLicensedName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= VocLicensedName.class)
@WebTrail(comment = "Лекарственное средство (патен.)", nameProperties= "name", view="entityView-voc_drugLN.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocDrugLicensedName")
public class VocDrugLicensedNameForm extends IdEntityForm {
	/** Название */
	@Comment("Название")
	@Persist @Required 
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	@Comment("Внешний код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Внешний код */
	private String theCode;
	/** Название */
	private String theName;
}
