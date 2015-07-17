package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractNosologyGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;
@EntityForm
@EntityFormPersistance(clazz = ContractNosologyGroup.class)
@Comment("Нозологическая группа по договору")
@WebTrail(comment = "Нозологическая группа по договору", nameProperties= "id", list="entityList-contract_contractNosologyGroup.do", view="entityView-contract_contractNosologyGroup.do")
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractNosologyGroup")
public class ContractNosologyGroupForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
	
	/** Диапозон */
	@Comment("Диапозон")
	public String getRangeMkb() {
		return theRangeMkb;
	}

	public void setRangeMkb(String aRangeMkb) {
		theRangeMkb = aRangeMkb;
	}

	/** Диапозон */
	private String theRangeMkb;
}
