package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ContractPerson.class)
@Comment("Договорная персона")
@WebTrail(comment = "Договорная персона", nameProperties= "id", list="entityList-contract_contractPerson.do", view="entityView-contract_contractPerson.do")
@Subclasses(value={NaturalPersonForm.class,JuridicalPersonForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/Contract/ContractPerson")
public class ContractPersonForm extends IdEntityForm{
	/** Информация о договорной персоне */
	@Comment("Информация о договорной персоне")
	@Persist
	public String getInformation() {
		return theInformation;
	}

	public void setInformation(String aInformation) {
		theInformation = aInformation;
	}

	/** Информация о договорной персоне */
	private String theInformation;
}
