package ru.ecom.mis.ejb.form.equipment.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.equipment.voc.VocTypeEquip;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz= VocTypeEquip.class)
@Comment("Тип оборудование")
@WebTrail(comment = "Тип оборудования", nameProperties= "name", view="entityView-voc_typeEquip.do")
@EntityFormSecurityPrefix("/Policy/Mis/Equipment/Equipment")

public class VocTypeEquipForm extends IdEntityForm{
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	private String theName;
	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
}
