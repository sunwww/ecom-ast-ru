package ru.ecom.mis.ejb.form.contract;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ContractAccount.class)
@Comment("Договорной счет")
@WebTrail(comment = "Договорной счет", nameProperties= "id", list="entityParentList-contract_contractAccount.do", view="entityParentView-contract_contractAccount.do")
@Parent(property="servedPerson", parentForm=ServedPersonForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount")
public class ContractAccountForm extends IdEntityForm{
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
}
