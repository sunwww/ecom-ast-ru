package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;


@EntityForm
@EntityFormPersistance(clazz = ContractAccount.class)
@Comment("Договорной счет")
@WebTrail(comment = "Договорной счет", nameProperties= "accountNumber", list="entityParentList-contract_juridicalAccount.do", view="entityParentView-contract_juridicalAccount.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount")
public class ContractAccountJuridicalForm extends IdEntityForm{

	/**
	 * Сумма баланса
	 */
	@Comment("Сумма баланса")
	@Persist
	public String getBalanceSum() {
		return theBalanceSum;
	}
	public void setBalanceSum(String aBalanceSum) {
		theBalanceSum = aBalanceSum;
	}
	/**
	 * Сумма баланса
	 */
	private String theBalanceSum;
	
	/** Мед.договор */
	@Comment("Мед.договор")
	@Persist
	public Long getContract() {return theContract;}
	public void setContract(Long aContract) {theContract = aContract;}

	/** Мед.договор */
	private Long theContract;
	/**
	 * Резервированная сумма
	 */
	@Comment("Резервированная сумма")
	@Persist
	public String getReservationSum() {
		return theReservationSum;
	}
	public void setReservationSum(String aReservationSum) {
		theReservationSum = aReservationSum;
	}
	/**
	 * Резервированная сумма
	 */
	private String theReservationSum;
	/**
	 * Дата открытия
	 */
	@Comment("Дата открытия")
	@Persist
	@Required
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата открытия
	 */
	private String theDateFrom;
	/**
	 * Дата закрытия
	 */
	@Comment("Дата закрытия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата закрытия
	 */
	private String theDateTo;
	/**
	 * Блокирован
	 */
	@Comment("Блокирован")
	@Persist
	public Boolean getBlock() {
		return theBlock;
	}
	public void setBlock(Boolean aBlock) {
		theBlock = aBlock;
	}
	/**
	 * Блокирован
	 */
	private Boolean theBlock;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Дата последнего изменения */
	@Comment("Дата последнего изменения")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время, последнего изменения */
	@Comment("Время, последнего изменения")
	@Persist @DoTimeString @TimeString
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	
	/** Пользователь, последний изменивший запись */
	@Comment("Пользователь, последний изменивший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Информация */
	private String theInfo;
	/** Пользователь, последний изменивший запись */
	private String theEditUsername;
	/** Время, последнего изменения */
	private String theEditTime;
	/** Дата последнего изменения */
	private String theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Время создания */
	private String theCreateTime;
	/** Дата создания */
	private String theCreateDate;
	


	/** Номер счета */
	@Comment("Номер счета")
	@Persist
	public String getAccountNumber() {
		return theAccountNumber;
	}

	public void setAccountNumber(String aAccountNumber) {
		theAccountNumber = aAccountNumber;
	}

	/** Номер счета */
	private String theAccountNumber;
	
	/** Оплачен */
	@Comment("Оплачен")
	@Persist
	public Boolean getIsFinished() {
		return theIsFinished;
	}

	public void setIsFinished(Boolean aIsFinished) {
		theIsFinished = aIsFinished;
	}

	/** Оплачен */
	private Boolean theIsFinished;
	
	/** Период с */
	@Comment("Период с")
	@Persist @DateString @DoDateString @Required
	public String getPeriodFrom() {
		return thePeriodFrom;
	}

	public void setPeriodFrom(String aPeriodFrom) {
		thePeriodFrom = aPeriodFrom;
	}
	
	/** Период по */
	@Comment("Период по")
	@Persist @DateString @DoDateString @Required
	public String getPeriodTo() {
		return thePeriodTo;
	}

	public void setPeriodTo(String aPeriodTo) {
		thePeriodTo = aPeriodTo;
	}

	/** Период по */
	private String thePeriodTo;
	/** Период с */
	private String thePeriodFrom;
}

