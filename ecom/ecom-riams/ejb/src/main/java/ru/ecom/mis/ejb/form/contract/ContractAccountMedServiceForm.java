package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountMedService.class)
@Comment("Медицинские услуги")
@WebTrail(comment = "Медицинские услуги", nameProperties= "id", list="entityParentList-contract_accountMedService.do", view="entityParentView-contract_accountMedService.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService")
public class ContractAccountMedServiceForm extends IdEntityForm{

	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {return theServedPerson;}
	public void setServedPerson(Long aServedPerson) {theServedPerson = aServedPerson;}
	/**
	 * Обслуживаемая персона
	 */
	private Long theServedPerson;
	
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@Persist
	public Long getAccount() {return theAccount;}
	public void setAccount(Long aAccount) {theAccount = aAccount;}
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

	/** Рабочая функция */
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Рабочая функция */
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
	
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	/** Дата начала */
	private String theDateFrom;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}
	/** Дата окончания */
	private String theDateTo;

	
}
