package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
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
@Setter
public class ContractAccountRuleForm extends IdEntityForm{
	/**
	 * Скидка
	 */
	@Comment("Скидка")
	@Persist
	public String getDiscount() {
		return discount;
	}
	/**
	 * Скидка
	 */
	private String discount;
	/**
	 * Предел платежей
	 */
	@Comment("Предел платежей")
	@Persist
	public String getPaymentLimit() {
		return paymentLimit;
	}
	/**
	 * Предел платежей
	 */
	private String paymentLimit;
	/**
	 * Предел блокирования платежей
	 */
	@Comment("Предел блокирования платежей")
	@Persist
	public String getBlockLimit() {
		return blockLimit;
	}
	/**
	 * Предел блокирования платежей
	 */
	private String blockLimit;
	/**
	 * Автоматическое создание счета
	 */
	@Comment("Автоматическое создание счета")
	@Persist
	public Boolean getAutocreateAccount() {
		return autocreateAccount;
	}
	/**
	 * Автоматическое создание счета
	 */
	private Boolean autocreateAccount;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String dateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String dateTo;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private Long contract;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {
		return servedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private Long servedPerson;
}
