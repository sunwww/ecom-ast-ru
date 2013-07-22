package ru.ecom.mis.ejb.domain.contract;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Мед услуги по счету
 */
@Comment("Мед услуги по счету")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"account"})
	,@AIndex(unique= false, properties = {"medService"})
	
})
public class ContractAccountMedService extends BaseEntity{
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {
		return theAccount;
	}
	public void setAccount(ContractAccount aAccount) {
		theAccount = aAccount;
	}
	/**
	 * Договорной счет
	 */
	private ContractAccount theAccount;
	/**
	 * Мед Услуга
	 */
	@Comment("Мед Услуга")
	@OneToOne
	public PriceMedService getMedService() {
		return theMedService;
	}
	public void setMedService(PriceMedService aMedService) {
		theMedService = aMedService;
	}
	/**
	 * Мед Услуга
	 */
	private PriceMedService theMedService;
	
	/**
	 * Рабочая функция
	 */
	public WorkFunction getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(WorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}	
	/**
	 * Рабочая функция
	 */
	private WorkFunction theWorkFunction;
	
	/** Количество */
	@Comment("Количество")
	public Integer getCountMedService() {
		return theCountMedService;
	}

	public void setCountMedService(Integer aCountMedService) {
		theCountMedService = aCountMedService;
	}

	/** Количество */
	private Integer theCountMedService;
	
	public BigDecimal getCost() {
		return theCost;
	}
	public void setCost(BigDecimal aCost) {
		theCost = aCost;
	}
	/**
	 * Цена
	 */
	private BigDecimal theCost;
	
	
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {return theServedPerson;}
	public void setServedPerson(ServedPerson aServedPerson) {theServedPerson = aServedPerson;}
	/**
	 * Обслуживаемая персона
	 */
	private ServedPerson theServedPerson;

}
