package ru.ecom.mis.ejb.uc.privilege.form.voc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugUnlicensedName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= VocDrugUnlicensedName.class)
@WebTrail(comment = "Лекарственное средство (непатен.)", nameProperties= "name", view="entityView-voc_drugUN.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocDrugUnlicensedName")
@Setter
public class VocDrugUnlicensedNameForm extends IdEntityForm {
	/** Название */
	@Comment("Название")
	@Persist @Required 
	public String getName() {return name;}

	@Comment("Внешний код")
	@Persist
	public String getCode() {return code;}

	/** Внешний код */
	private String code;
	/** Название */
	private String name;
}
