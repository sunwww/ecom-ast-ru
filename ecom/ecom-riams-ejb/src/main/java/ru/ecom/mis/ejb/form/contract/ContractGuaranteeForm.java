package ru.ecom.mis.ejb.form.contract;


import java.sql.Date;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
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
public class ContractGuaranteeForm extends IdEntityForm{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return theContract;
	}
	public void setContract(Long aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private Long theContract;
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@Persist
	public Long getContractPerson() {
		return theContractPerson;
	}
	public void setContractPerson(Long aContractPerson) {
		theContractPerson = aContractPerson;
	}
	/**
	 * Договорная персона
	 */
	private Long theContractPerson;
	
	/** Лимит денег */
	@Comment("Лимит денег")
	@Persist
	public String getLimitMoney() {return theLimitMoney;}
	public void setLimitMoney(String aLimitMoney) {theLimitMoney = aLimitMoney;}

	/** Лимит денег */
	private String theLimitMoney;
	
	/** Номер */
	@Comment("Номер")
	@Persist @Required
	public String getNumberDoc() {return theNumberDoc;}
	public void setNumberDoc(String aNumberDoc) {theNumberDoc = aNumberDoc;}

	/** Номер */
	private String theNumberDoc;
	
	/** Дата выдачи */
	@Comment("Дата выдачи")
	@Persist @DateString @DoDateString @Required
	public String getIssueDate() {return theIssueDate;}
	public void setIssueDate(String aIssueDate) {theIssueDate = aIssueDate;}

	
	/** Дата действия */
	@Comment("Дата действия")
	@Persist @DateString @DoDateString @Required
	public String getActionDate() {return theActionDate;}
	public void setActionDate(String aActionDate) {theActionDate = aActionDate;}

	/** Дата действия */
	private String theActionDate;
	/** Дата выдачи */
	private String theIssueDate;
}
