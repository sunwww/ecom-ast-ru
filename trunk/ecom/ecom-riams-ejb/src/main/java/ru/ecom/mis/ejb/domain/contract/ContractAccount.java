package ru.ecom.mis.ejb.domain.contract;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperation;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
@Entity
@Table(schema="SQLUser")
public class ContractAccount extends BaseEntity{
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
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	public List<ContractAccountOperation> getOperations() {
		return theOperations;
	}
	public void setOperations(List<ContractAccountOperation> aOperations) {
		theOperations = aOperations;
	}
	/**
	 * Операции по счету
	 */
	private List<ContractAccountOperation> theOperations;
	/**
	 * Сумма баланса
	 */
	@Comment("Сумма баланса")
	
	public BigDecimal getBalanceSum() {
		return theBalanceSum;
	}
	public void setBalanceSum(BigDecimal aBalanceSum) {
		theBalanceSum = aBalanceSum;
	}
	/**
	 * Сумма баланса
	 */
	private BigDecimal theBalanceSum;
	/**
	 * Резервированная сумма
	 */
	@Comment("Резервированная сумма")
	
	public BigDecimal getReservationSum() {
		return theReservationSum;
	}
	public void setReservationSum(BigDecimal aReservationSum) {
		theReservationSum = aReservationSum;
	}
	/**
	 * Резервированная сумма
	 */
	private BigDecimal theReservationSum;
	/**
	 * Дата открытия
	 */
	@Comment("Дата открытия")
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата открытия
	 */
	private Date theDateFrom;
	/**
	 * Дата закрытия
	 */
	@Comment("Дата закрытия")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата закрытия
	 */
	private Date theDateTo;
	/**
	 * Блокирован
	 */
	@Comment("Блокирован")
	
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
