package ru.ecom.mis.ejb.domain.contract;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"person"})
	,@AIndex(unique= false, properties = {"contract"})
})
public class ServedPerson extends BaseEntity{
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@OneToOne
	public ContractPerson getPerson() {
		return thePerson;
	}
	public void setPerson(ContractPerson aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Договорная персона
	 */
	private ContractPerson thePerson;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@ManyToOne
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
	 * Дата начала обслуживания
	 */
	@Comment("Дата начала обслуживания")
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала обслуживания
	 */
	private Date theDateFrom;
	/**
	 * Дата окончания обслуживания
	 */
	@Comment("Дата окончания обслуживания")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания обслуживания
	 */
	private Date theDateTo;

	/**
	 * Признак авто создания счета 
	 */
	public Boolean getAutoAccount() {
		return theAutoAccount;
	}
	public void setAutoAccount(Boolean aAutoAccount) {
		theAutoAccount = aAutoAccount;
	}	
	/**
	 * Признак авто создания счета 
	 */	
	private Boolean theAutoAccount;
	
	/** Информация */
	@Comment("Информация")
	@Transient
	public String getInfo() {return thePerson!=null?thePerson.getInformation():"нет информации";}
	
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
	/** Счет */
	@Comment("Счет")
	@OneToOne
	public ContractAccount getAccount() {return theAccount;}
	public void setAccount(ContractAccount aAccount) {theAccount = aAccount;}

	/** Счет */
	private ContractAccount theAccount;
}
