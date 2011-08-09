package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
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
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountOperation.class)
@Comment("Операция договорного счета")
@WebTrail(comment = "Операция договорного счета", nameProperties= "id", list="entityParentList-contract_contractAccountOperation.do", view="entityParentView-contract_contractAccountOperation.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation")
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
	 * Тип операции
	 */
	@Comment("Тип операции")
	@Persist
	public Long getType() {
		return theType;
	}
	public void setType(Long aType) {
		theType = aType;
	}
	/**
	 * Тип операции
	 */
	private Long theType;
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
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedcase() {
		return theMedcase;
	}
	public void setMedcase(Long aMedcase) {
		theMedcase = aMedcase;
	}
	/**
	 * Случай медицинского обслуживания
	 */
	private Long theMedcase;
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
}
