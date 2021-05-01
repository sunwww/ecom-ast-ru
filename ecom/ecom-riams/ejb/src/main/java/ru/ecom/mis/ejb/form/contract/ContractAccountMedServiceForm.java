package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
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
@Setter
public class ContractAccountMedServiceForm extends IdEntityForm{

	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {return servedPerson;}
	/**
	 * Обслуживаемая персона
	 */
	private Long servedPerson;
	
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@Persist
	public Long getAccount() {return account;}
	/**
	 * Договорной счет
	 */
	private Long account;
	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	@Persist @Required
	public Long getMedService() {
		return medService;
	}

	/**
	 * Мед. Услуги
	 */
	private Long medService;

	/** Рабочая функция */
	@Persist
	public Long getWorkFunction() {return workFunction;}
	/** Рабочая функция */
	private Long workFunction;

	/** Количество */
	@Comment("Количество")
	@Persist @Required
	public Integer getCountMedService() {
		return countMedService;
	}


	/** Количество */
	private Integer countMedService;
	
	@Persist
	public String getCost() {
		return cost;
	}
	/**
	 * Цена
	 */
	private String cost;
	
	/** Все услуги */
	@Comment("Все услуги")
	public String getAllServicies() {
		return allServicies;
	}


	/** Все услуги */
	private String allServicies;
	
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString
	public String getDateFrom() {return dateFrom;}
	/** Дата начала */
	private String dateFrom;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateTo() {return dateTo;}
	/** Дата окончания */
	private String dateTo;

	
}
