package ru.ecom.mis.ejb.form.contract;

import java.math.BigDecimal;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountMedService.class)
@Comment("Медицинские услуги")
@WebTrail(comment = "Медицинские услуги", nameProperties= "id", list="entityParentList-contract_accountMedService.do", view="entityParentView-contract_accountMedService.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService")
public class ContractAccountMedServiceForm extends IdEntityForm{
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@Persist
	public Long getAccount() {
		return theAccount;
	}
	public void setAccount(Long aAccount) {
		theAccount = aAccount;
	}
	/**
	 * Договорной счет
	 */
	private Long theAccount;
	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	@Persist @Required
	public Long getMedService() {
		return theMedService;
	}
	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}

	/**
	 * Мед. Услуги
	 */
	private Long theMedService;
	/**
	 * Рабочая функция
	 */	
	@Persist
	public Long getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(Long aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}
	/**
	 * Рабочая функция
	 */
	private Long theWorkFunction;
	/** Количество */
	@Comment("Количество")
	@Persist @Required
	public Integer getCountMedService() {
		return theCountMedService;
	}

	public void setCountMedService(Integer aCountMedService) {
		theCountMedService = aCountMedService;
	}

	/** Количество */
	private Integer theCountMedService;
	
	@Persist
	public String getCost() {
		return theCost;
	}
	public void setCost(String aCost) {
		theCost = aCost;
	}
	/**
	 * Цена
	 */
	private String theCost;
	
	/** Все услуги */
	@Comment("Все услуги")
	public String getAllServicies() {
		return theAllServicies;
	}

	public void setAllServicies(String aAllServicies) {
		theAllServicies = aAllServicies;
	}

	/** Все услуги */
	private String theAllServicies;
}
