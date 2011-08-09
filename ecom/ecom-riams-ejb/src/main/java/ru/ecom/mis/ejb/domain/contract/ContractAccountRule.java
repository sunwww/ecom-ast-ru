package ru.ecom.mis.ejb.domain.contract;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Правило договорного счета
	 */
	@Comment("Правило договорного счета")
@Entity
@Table(schema="SQLUser")
public class ContractAccountRule extends BaseEntity{
	/**
	 * Скидка
	 */
	@Comment("Скидка")
	
	public BigDecimal getDiscount() {
		return theDiscount;
	}
	public void setDiscount(BigDecimal aDiscount) {
		theDiscount = aDiscount;
	}
	/**
	 * Скидка
	 */
	private BigDecimal theDiscount;
	/**
	 * Предел платежей
	 */
	@Comment("Предел платежей")
	
	public BigDecimal getPaymentLimit() {
		return thePaymentLimit;
	}
	public void setPaymentLimit(BigDecimal aPaymentLimit) {
		thePaymentLimit = aPaymentLimit;
	}
	/**
	 * Предел платежей
	 */
	private BigDecimal thePaymentLimit;
	/**
	 * Предел блокирования платежей
	 */
	@Comment("Предел блокирования платежей")
	
	public BigDecimal getBlockLimit() {
		return theBlockLimit;
	}
	public void setBlockLimit(BigDecimal aBlockLimit) {
		theBlockLimit = aBlockLimit;
	}
	/**
	 * Предел блокирования платежей
	 */
	private BigDecimal theBlockLimit;
	/**
	 * Автоматическое создание счета
	 */
	@Comment("Автоматическое создание счета")
	
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
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private Date theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private Date theDateTo;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@OneToOne
	public MedContract getContract() {
		return theContract;
	}
	public void setContract(MedContract aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private MedContract theContract;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {
		return theServedPerson;
	}
	public void setServedPerson(ServedPerson aServedPerson) {
		theServedPerson = aServedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private ServedPerson theServedPerson;
}
