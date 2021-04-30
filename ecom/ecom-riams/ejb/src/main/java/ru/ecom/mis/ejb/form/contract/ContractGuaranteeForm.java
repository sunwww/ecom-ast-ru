package ru.ecom.mis.ejb.form.contract;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ContractGuarantee.class)
@Comment("Гарантийный документ по договору")
@WebTrail(comment = "Гарантийный документ по договору", nameProperties= "id", list="entityParentList-contract_guarantee.do", view="entityParentView-contract_guarantee.do")
@Subclasses(value = { ContractGuaranteeLetterForm.class, ContractMedPolicyForm.class, ContractPaymentOrderForm.class })
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractGuarantee")
@Setter
public class ContractGuaranteeForm extends IdEntityForm{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private Long contract;
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@Persist @Required
	public Long getContractPerson() {
		return contractPerson;
	}
	/**
	 * Договорная персона
	 */
	private Long contractPerson;
	
	/** Лимит денег */
	@Comment("Лимит денег")
	@Persist
	public String getLimitMoney() {return limitMoney;}

	/** Лимит денег */
	private String limitMoney;
	
	/** Номер */
	@Comment("Номер")
	@Persist @Required
	public String getNumberDoc() {return numberDoc;}

	/** Номер */
	private String numberDoc;
	
	/** Дата выдачи */
	@Comment("Дата выдачи")
	@Persist @DateString @DoDateString @Required
	public String getIssueDate() {return issueDate;}

	
	/** Дата действия */
	@Comment("Дата действия")
	@Persist @DateString @DoDateString @Required
	public String getActionDate() {return actionDate;}

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	@Persist @DateString @DoDateString 
	public String getActionDateTo() {return actionDateTo;}

	/** Дата окончания действия */
	private String actionDateTo;	
	/** Дата действия */
	private String actionDate;
	/** Дата выдачи */
	private String issueDate;
	/** Без лимита */
	@Comment("Без лимита")
	@Persist
	public Boolean getIsNoLimit() {return isNoLimit;}

	/** Без лимита */
	private Boolean isNoLimit;
	
	/** Вид медицинской помощи */
	@Comment("Вид медицинской помощи")
	@Persist @Required 
	public Long getKindHelp() {return kindHelp;}

	/** Вид медицинской помощи */
	private Long kindHelp;
	/** Палата */
	@Comment("Палата")
	@Persist 
	public Long getRoomType() {
		return roomType;
	}

	/** Палата */
	private Long roomType;

}
