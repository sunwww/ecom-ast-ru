package ru.ecom.mis.ejb.domain.contract;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperation;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"servedPerson"})
})
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
	
	@Comment("Мед. услуги")
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	public List<ContractAccountMedService> getMedService() {
		return theMedService;
	}
	public void setMedService(List<ContractAccountMedService> aMedService) {
		theMedService = aMedService;
	}
	
	/**
	 * Мед. услуги
	 */
	private List<ContractAccountMedService> theMedService;
	
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
	
	@Transient
	public String getInfo() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		StringBuilder res=new StringBuilder().append(theDateFrom!=null?format.format(theDateFrom):"нет даты открытия") ;
		if (theDateFrom!=null) {
			res.append("-").append(theDateTo!=null?format.format(theDateTo):"нет даты окончания") ;
		} 
		if (theBlock!=null && theBlock) res.append("(счет заблокирован");
		return res.toString();
	}
	}
