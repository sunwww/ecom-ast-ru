package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperation;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@Setter
public class ContractAccountOperationForm extends IdEntityForm {

	/** Номер телефона для чека */
	@Comment("Номер телефона для чека")
	@Persist
	public String getCustomerPhone() {return customerPhone;}
	/** Номер телефона для чека */
	private String customerPhone ;

	/** Тип операции */
	@Comment("Тип операции")
	public Long getType() {return type;}
	/** Тип операции*/
	private Long type;
	
	/** Договорной счет */
	@Comment("Договорной счет")
	@Persist
	public Long getAccount() {return account;}
	/** Договорной счет */
	private Long account;
	/** Дата */
	@Comment("Дата")
	@Persist @DateString @DoDateString
	public String getOperationDate() {return operationDate;}
	/** Дата */
	private String operationDate;
	/** Время операции */
	@Comment("Время операции")
	@Persist @TimeString @DoTimeString
	public String getOperationTime() {return operationTime;}
	/** Время операции */
	private String operationTime;
	/** Стоимость */
	@Comment("Стоимость")
	@Required @Persist
	public String getCost() {return cost;}
	
	/** Стоимость */
	private String cost;

	/** Отменившая операция	 */
	@Comment("Отменившая операция")
	@Persist
	public Long getRepealOperation() {return repealOperation;}
	/** Отменившая операция */
	private Long repealOperation;
	/** Оператор */
	@Comment("Оператор")
	@Persist
	public Long getWorkFunction() {return workFunction;}
	/** Оператор */
	private Long workFunction;
	/** Скидка */
	@Comment("Скидка")
	@Persist
	public String getDiscount() {return discount;}
	/** Скидка */
	private String discount;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {return createTime;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	
	/** Дата последнего изменения */
	@Comment("Дата последнего изменения")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}
	
	/** Время, последнего изменения */
	@Comment("Время, последнего изменения")
	@Persist @DoTimeString @TimeString
	public String getEditTime() {return editTime;}
	
	/** Пользователь, последний изменивший запись */
	@Comment("Пользователь, последний изменивший запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, последний изменивший запись */
	private String editUsername;
	/** Время, последнего изменения */
	private String editTime;
	/** Дата последнего изменения */
	private String editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Время создания */
	private String createTime;
	/** Дата создания */
	private String createDate;
	
	/** Услуги */
	@Comment("Услуги")
	public String getMedServicies() {return medServicies;}
	/** Услуги */
	private String medServicies;
	
	@Comment("Оплата терминалом")
	@Persist
	public Boolean getIsPaymentTerminal() {return isPaymentTerminal;}
	/** Оплата терминалом */
	private Boolean isPaymentTerminal;

	/** Номер договора */
	@Comment("Номер договора")
	public String getContractNumber() {return contractNumber;}
	/** Номер договора */
	private String contractNumber;

	/** Дата договора */
	@Comment("Дата договора")
	public String getContractDate() {return contractDate;}
	/** Дата договора */
	private String contractDate ;

	/** Заказчик */
	@Comment("Заказчик")
	public String getContractCustomer() {return contractCustomer;}
	/** Заказчик */
	private String contractCustomer ;
}