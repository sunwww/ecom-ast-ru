package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountOperation.class)
@Comment("Операция договорного счета")
@WebTrail(comment = "Операция договорного счета", nameProperties= "id", list="entityParentList-contract_accountOperation.do", view="entityParentView-contract_accountOperation.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation")
@Subclasses({OperationAccrualForm.class,OperationReservationForm.class
	,OperationReturnForm.class,OperationWriteOffForm.class})
public class ContractAccountOperationForm extends IdEntityForm{
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
	 * Дата
	 */
	@Comment("Дата")
	@Persist
	@DateString @DoDateString
	public String getOperationDate() {
		return theOperationDate;
	}
	public void setOperationDate(String aOperationDate) {
		theOperationDate = aOperationDate;
	}
	/**
	 * Дата
	 */
	private String theOperationDate;
	/**
	 * Время операции
	 */
	@Comment("Время операции")
	@Persist
	@TimeString @DoTimeString
	public String getOperationTime() {
		return theOperationTime;
	}
	public void setOperationTime(String aOperationTime) {
		theOperationTime = aOperationTime;
	}
	/**
	 * Время операции
	 */
	private String theOperationTime;
	/**
	 * Стоимость
	 */
	@Comment("Стоимость")
	@Required
	@Persist
	public String getCost() {
		return theCost;
	}
	public void setCost(String aCost) {
		theCost = aCost;
	}
	/**
	 * Стоимость
	 */
	private String theCost;

	/**
	 * Отменившая операция
	 */
	@Comment("Отменившая операция")
	@Persist
	public Long getRepealOperation() {
		return theRepealOperation;
	}
	public void setRepealOperation(Long aRepealOperation) {
		theRepealOperation = aRepealOperation;
	}
	/**
	 * Отменившая операция
	 */
	private Long theRepealOperation;
	/**
	 * Оператор
	 */
	@Comment("Оператор")
	@Persist
	public Long getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(Long aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}
	/**
	 * Оператор
	 */
	private Long theWorkFunction;
	/**
	 * Скидка
	 */
	@Comment("Скидка")
	@Persist
	public String getDiscount() {
		return theDiscount;
	}
	public void setDiscount(String aDiscount) {
		theDiscount = aDiscount;
	}
	/**
	 * Скидка
	 */
	private String theDiscount;
	
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
	
	/** Услуги */
	@Comment("Услуги")
	public String getMedServicies() {
		return theMedServicies;
	}

	public void setMedServicies(String aMedServicies) {
		theMedServicies = aMedServicies;
	}

	/** Услуги */
	private String theMedServicies;
}
