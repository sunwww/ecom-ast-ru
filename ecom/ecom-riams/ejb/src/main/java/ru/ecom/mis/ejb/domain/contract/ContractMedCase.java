package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class ContractMedCase extends BaseEntity {
	
	/** Основной СМО */
	@Comment("Основной СМО")
	public Long getMainMedCase() {return theMainMedCase;}
	public void setMainMedCase(Long aMainMedCase) {theMainMedCase = aMainMedCase;}

	/** Основной СМО */
	private Long theMainMedCase;
	/** СМО */
	@Comment("СМО")
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	
	/** Гарантийный документ */
	@Comment("Гарантийный документ")
	public Long getGuarantee() {return theGuarantee;}
	public void setGuarantee(Long aGuarantee) {theGuarantee = aGuarantee;}

	/** Договор */
	@Comment("Договор")
	public Long getContract() {return theContract;}
	public void setContract(Long aContract) {theContract = aContract;}

	/** Счет */
	@Comment("Счет")
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
	public Long getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(Long aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}

	/** Операция */
	private Long theSurgicalOperation;
	
	/** Анестезия */
	@Comment("Анестезия")
	public Long getAnesthesia() {return theAnesthesia;}
	public void setAnesthesia(Long aAnesthesia) {theAnesthesia = aAnesthesia;}

	/** Анестезия */
	private Long theAnesthesia;
	
	/** Услуга по прейскуранту */
	@Comment("Услуга по прейскуранту")
	public Long getPricePosition() {return thePricePosition;}
	public void setPricePosition(Long aPricePosition) {thePricePosition = aPricePosition;}

	/** Услуга по прейскуранту */
	private Long thePricePosition;
	
	/** Услуга внутренния */
	@Comment("Услуга внутренния")
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}

	/** Услуга внутренния */
	private Long theMedService;
	
	/** PriceMedService */
	@Comment("PriceMedService")
	public Long getPriceMedService() {return thePriceMedService;}
	public void setPriceMedService(Long aPriceMedService) {thePriceMedService = aPriceMedService;}

	/** PriceMedService */
	private Long thePriceMedService;
}