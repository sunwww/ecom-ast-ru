package ru.ecom.mis.ejb.domain.contract;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocAccountOperation;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Операция договорного счета
 */
@Comment("Операция договорного счета")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"account"})
})
public class ContractAccountOperation extends BaseEntity{
	/** Договорной счет */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {return theAccount;}
	public void setAccount(ContractAccount aAccount) {theAccount = aAccount;}
	/** Договорной счет */
	private ContractAccount theAccount;
	/**
	 * Тип операции
	 */
	@Comment("Тип операции")
	@OneToOne
	public VocAccountOperation getType() {return theType;}
	public void setType(VocAccountOperation aType) {theType = aType;}
	/**
	 * Тип операции
	 */
	private VocAccountOperation theType;
	/**
	 * Дата
	 */
	@Comment("Дата")
	public Date getOperationDate() {return theOperationDate;}
	public void setOperationDate(Date aOperationDate) {theOperationDate = aOperationDate;}
	/**
	 * Дата
	 */
	private Date theOperationDate;
	/**
	 * Время операции
	 */
	@Comment("Время операции")
	public Time getOperationTime() {return theOperationTime;}
	public void setOperationTime(Time aOperationTime) {theOperationTime = aOperationTime;}
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
	/** Скидка  */
	@Comment("Скидка")
	public BigDecimal getDiscount() {return theDiscount;}
	public void setDiscount(BigDecimal aDiscount) {theDiscount = aDiscount;}
	/** Скидка */
	private BigDecimal theDiscount;
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Дата последнего изменения */
	@Comment("Дата последнего изменения")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время, последнего изменения */
	@Comment("Время, последнего изменения")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	
	/** Пользователь, последний изменивший запись */
	@Comment("Пользователь, последний изменивший запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний изменивший запись */
	private String theEditUsername;
	/** Время, последнего изменения */
	private Time theEditTime;
	/** Дата последнего изменения */
	private Date theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	
}
