package ru.ecom.mis.ejb.form.contract;

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
public class ContractMedCaseForm {
	/** Основной СМО */
	@Comment("Основной СМО")
	@Persist
	public Long getMainMedCase() {return theMainMedCase;}
	public void setMainMedCase(Long aMainMedCase) {theMainMedCase = aMainMedCase;}

	/** Основной СМО */
	private Long theMainMedCase;
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	
	/** Гарантийный документ */
	@Comment("Гарантийный документ")
	@Persist
	public Long getGuarantee() {return theGuarantee;}
	public void setGuarantee(Long aGuarantee) {theGuarantee = aGuarantee;}

	/** Договор */
	@Comment("Договор")
	@Persist
	public Long getContract() {return theContract;}
	public void setContract(Long aContract) {theContract = aContract;}

	/** Счет */
	@Comment("Счет")
	@Persist
	public Long getAccount() {return theAccount;}
	public void setAccount(Long aAccount) {theAccount = aAccount;}

	/** Счет */
	private Long theAccount;
	/** Договор */
	private Long theContract;
	/** Гарантийный документ */
	private Long theGuarantee;
	/** СМО */
	private Long theMedCase;
	
	/** Операция */
	@Comment("Операция")
	@Persist
	public Long getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(Long aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}

	/** Операция */
	private Long theSurgicalOperation;
	
	/** Анестезия */
	@Comment("Анестезия")
	@Persist
	public Long getAnesthesia() {return theAnesthesia;}
	public void setAnesthesia(Long aAnesthesia) {theAnesthesia = aAnesthesia;}

	/** Анестезия */
	private Long theAnesthesia;
	
	/** Услуга по прейскуранту */
	@Comment("Услуга по прейскуранту")
	@Persist
	public Long getPricePosition() {return thePricePosition;}
	public void setPricePosition(Long aPricePosition) {thePricePosition = aPricePosition;}

	/** Услуга по прейскуранту */
	private Long thePricePosition;
	
	/** Услуга внутренния */
	@Comment("Услуга внутренния")
	@Persist
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}

	/** Услуга внутренния */
	private Long theMedService;
	
	/** PriceMedService */
	@Comment("PriceMedService")
	@Persist
	public Long getPriceMedService() {return thePriceMedService;}
	public void setPriceMedService(Long aPriceMedService) {thePriceMedService = aPriceMedService;}

	/** PriceMedService */
	private Long thePriceMedService;
}
