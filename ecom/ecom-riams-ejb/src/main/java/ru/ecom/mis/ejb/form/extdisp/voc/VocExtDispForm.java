package ru.ecom.mis.ejb.form.extdisp.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VocExtDisp.class)
@Comment("Вид доп.диспансеризации")
@WebTrail(comment = "Вид доп.диспансеризации", nameProperties= "id"
, list="entityList-extDisp_voc.do"
, view="entityView-extDisp_voc.do")
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc")
public class VocExtDispForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
}
