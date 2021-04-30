package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Конкретизация операции договорного счета по услуге 
 */
@Comment("Конкретизация операции договорного счета по услуге")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"accountMedService"})
	,@AIndex(properties = {"accountOperation"})
})
@Getter
@Setter
public class ContractAccountOperationByService extends BaseEntity {
	/** Операция договорного счета */
	@Comment("Операция договорного счета")
	@OneToOne
	public ContractAccountOperation getAccountOperation() {return accountOperation;}

	/** Услуга по счету */
	@Comment("Услуга по счету")
	@OneToOne
	public ContractAccountMedService getAccountMedService() {return accountMedService;}

	/**
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedcase() {return medcase;}
	/**
	 * Случай медицинского обслуживания
	 */
	private MedCase medcase;
	/** Услуга по счету */
	private ContractAccountMedService accountMedService;
	/** Операция договорного счета */
	private ContractAccountOperation accountOperation;

	/** Тип мед. случая */
	private String serviceType ;

	/** ИД мед. случая */
	private Long serviceId ;
}
