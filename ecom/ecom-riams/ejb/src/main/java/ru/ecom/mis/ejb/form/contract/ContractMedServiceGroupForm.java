package ru.ecom.mis.ejb.form.contract;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractMedServiceGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;
@EntityForm
@EntityFormPersistance(clazz = ContractMedServiceGroup.class)
@Comment("Группу медицинских услуг по договору")
@WebTrail(comment = "Группу медицинских услуг по договору", nameProperties= "id", list="entityParentList-contract_medServiceGroup.do", view="entityParentView-contract_medServiceGroup.do")
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup")
@Setter
public class ContractMedServiceGroupForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;
	
	/** Диапозон */
	@Comment("Диапозон")
	public String getRangeMkb() {
		return rangeMkb;
	}


	/** Диапозон */
	private String rangeMkb;
}
