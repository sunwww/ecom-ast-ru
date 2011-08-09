package ru.ecom.mis.ejb.domain.contract;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperation;
import ru.ecom.mis.ejb.domain.contract.voc.VocAccountOperation;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Операция договорного счета
	 */
	@Comment("Операция договорного счета")
@Entity
@Table(schema="SQLUser")
public class ContractAccountOperation extends BaseEntity{
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {
		return theAccount;
	}
	public void setAccount(ContractAccount aAccount) {
		theAccount = aAccount;
	}
	/**
	 * Договорной счет
	 */
	private ContractAccount theAccount;
	/**
	 * Тип операции
	 */
	@Comment("Тип операции")
	@OneToOne
	public VocAccountOperation getType() {
		return theType;
	}
	public void setType(VocAccountOperation aType) {
		theType = aType;
	}
	/**
	 * Тип операции
	 */
	private VocAccountOperation theType;
	/**
	 * Дата
	 */
	@Comment("Дата")
	
	public Date getOperationDate() {
		return theOperationDate;
	}
	public void setOperationDate(Date aOperationDate) {
		theOperationDate = aOperationDate;
	}
	/**
	 * Дата
	 */
	private Date theOperationDate;
	/**
	 * Время операции
	 */
	@Comment("Время операции")
	
	public Time getOperationTime() {
		return theOperationTime;
	}
	public void setOperationTime(Time aOperationTime) {
		theOperationTime = aOperationTime;
	}
	/**
	 * Время операции
	 */
	private Time theOperationTime;
	/**
	 * Стоимость
	 */
	@Comment("Стоимость")
	
	public BigDecimal getCost() {
		return theCost;
	}
	public void setCost(BigDecimal aCost) {
		theCost = aCost;
	}
	/**
	 * Стоимость
	 */
	private BigDecimal theCost;
	/**
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedcase() {
		return theMedcase;
	}
	public void setMedcase(MedCase aMedcase) {
		theMedcase = aMedcase;
	}
	/**
	 * Случай медицинского обслуживания
	 */
	private MedCase theMedcase;
	/**
	 * Отменившая операция
	 */
	@Comment("Отменившая операция")
	@OneToOne
	public ContractAccountOperation getRepealOperation() {
		return theRepealOperation;
	}
	public void setRepealOperation(ContractAccountOperation aRepealOperation) {
		theRepealOperation = aRepealOperation;
	}
	/**
	 * Отменившая операция
	 */
	private ContractAccountOperation theRepealOperation;
	/**
	 * Оператор
	 */
	@Comment("Оператор")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(WorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}
	/**
	 * Оператор
	 */
	private WorkFunction theWorkFunction;
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
}
