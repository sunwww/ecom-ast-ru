package ru.ecom.mis.ejb.form.equipment.voc;

import lombok.Setter;
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

@Setter
public class VocTypeEquipForm extends IdEntityForm{
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return name;}
	private String name;
	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return code;}

	/** Код */
	private String code;
}
