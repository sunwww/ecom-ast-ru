package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountRule;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountRule.class)
@Comment("Правило договорного счета")
@WebTrail(comment = "Правило договорного счета", nameProperties= "id", list="entityParentList-contract_contractAccountRule.do", view="entityParentView-contract_contractAccountRule.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractAccountRule")
public class ContractAccountRuleForm extends IdEntityForm{
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
	/**
	 * Предел платежей
	 */
	@Comment("Предел платежей")
	@Persist
	public String getPaymentLimit() {
		return thePaymentLimit;
	}
	public void setPaymentLimit(String aPaymentLimit) {
		thePaymentLimit = aPaymentLimit;
	}
	/**
	 * Предел платежей
	 */
	private String thePaymentLimit;
	/**
	 * Предел блокирования платежей
	 */
	@Comment("Предел блокирования платежей")
	@Persist
	public String getBlockLimit() {
		return theBlockLimit;
	}
	public void setBlockLimit(String aBlockLimit) {
		theBlockLimit = aBlockLimit;
	}
	/**
	 * Предел блокирования платежей
	 */
	private String theBlockLimit;
	/**
	 * Автоматическое создание счета
	 */
	@Comment("Автоматическое создание счета")
	@Persist
	public Boolean getAutocreateAccount() {
		return theAutocreateAccount;
	}
	public void setAutocreateAccount(Boolean aAutocreateAccount) {
		theAutocreateAccount = aAutocreateAccount;
	}
	/**
	 * Автоматическое создание счета
	 */
	private Boolean theAutocreateAccount;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String theDateTo;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return theContract;
	}
	public void setContract(Long aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private Long theContract;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {
		return theServedPerson;
	}
	public void setServedPerson(Long aServedPerson) {
		theServedPerson = aServedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private Long theServedPerson;
}
