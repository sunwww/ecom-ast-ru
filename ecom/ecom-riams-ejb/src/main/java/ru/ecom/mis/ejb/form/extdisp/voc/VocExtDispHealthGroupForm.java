package ru.ecom.mis.ejb.form.extdisp.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispHealthGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VocExtDispHealthGroup.class)
@Comment("Группы здоровья")
@WebTrail(comment = "Группы здоровья", nameProperties= "id"
, view="entityView-extDisp_vocHealthGroup.do")
@Parent(property="dispType", parentForm=VocExtDispForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc/HealthGroup")
public class VocExtDispHealthGroupForm extends IdEntityForm{
	/** Вид доп. диспансеризации */
	@Comment("Вид доп. диспансеризации")
	@Persist
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}

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
	/** Вид доп. диспансеризации */
	private Long theDispType;
}
