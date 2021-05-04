package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractMedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = ContractMedCase.class)
@Comment("Привязка СМО к контракту")
@WebTrail(comment = "Привязка СМО к контракту", nameProperties= "id", list="entityParentList-contract_medCase.do", view="entityParentView-contract_medCase.do")
@Parent(property="medCase", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractMedCase")
@Setter
public class ContractMedCaseForm {
	/** Основной СМО */
	@Comment("Основной СМО")
	@Persist
	public Long getMainMedCase() {return mainMedCase;}

	/** Основной СМО */
	private Long mainMedCase;
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}
	
	/** Гарантийный документ */
	@Comment("Гарантийный документ")
	@Persist
	public Long getGuarantee() {return guarantee;}

	/** Договор */
	@Comment("Договор")
	@Persist
	public Long getContract() {return contract;}

	/** Счет */
	@Comment("Счет")
	@Persist
	public Long getAccount() {return account;}

	/** Счет */
	private Long account;
	/** Договор */
	private Long contract;
	/** Гарантийный документ */
	private Long guarantee;
	/** СМО */
	private Long medCase;
	
	/** Операция */
	@Comment("Операция")
	@Persist
	public Long getSurgicalOperation() {return surgicalOperation;}

	/** Операция */
	private Long surgicalOperation;
	
	/** Анестезия */
	@Comment("Анестезия")
	@Persist
	public Long getAnesthesia() {return anesthesia;}

	/** Анестезия */
	private Long anesthesia;
	
	/** Услуга по прейскуранту */
	@Comment("Услуга по прейскуранту")
	@Persist
	public Long getPricePosition() {return pricePosition;}

	/** Услуга по прейскуранту */
	private Long pricePosition;
	
	/** Услуга внутренния */
	@Comment("Услуга внутренния")
	@Persist
	public Long getMedService() {return medService;}

	/** Услуга внутренния */
	private Long medService;
	
	/** PriceMedService */
	@Comment("PriceMedService")
	@Persist
	public Long getPriceMedService() {return priceMedService;}

	/** PriceMedService */
	private Long priceMedService;
}
