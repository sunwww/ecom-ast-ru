package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Конкретизация операции договорного счета по услуге 
 */
@Comment("Конкретизация операции договорного счета по услуге")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"accountMedService"})
	,@AIndex(unique= false, properties = {"accountOperation"})
})
public class ContractAccountOperationByService extends BaseEntity {
	/** Операция договорного счета */
	@Comment("Операция договорного счета")
	@OneToOne
	public ContractAccountOperation getAccountOperation() {return theAccountOperation;}
	public void setAccountOperation(ContractAccountOperation aAccountOperation) {theAccountOperation = aAccountOperation;}

	/** Услуга по счету */
	@Comment("Услуга по счету")
	@OneToOne
	public ContractAccountMedService getAccountMedService() {return theAccountMedService;}
	public void setAccountMedService(ContractAccountMedService aAccountMedService) {theAccountMedService = aAccountMedService;}

	/**
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedcase() {return theMedcase;}
	public void setMedcase(MedCase aMedcase) {theMedcase = aMedcase;}
	/**
	 * Случай медицинского обслуживания
	 */
	private MedCase theMedcase;
	/** Услуга по счету */
	private ContractAccountMedService theAccountMedService;
	/** Операция договорного счета */
	private ContractAccountOperation theAccountOperation;
}
