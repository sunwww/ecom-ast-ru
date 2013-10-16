package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Журналируемые данные
	 */
	@Comment("Журналируемые данные")
@Entity
@Table(schema="SQLUser")
@SuppressWarnings("serial")
@MappedSuperclass
public class JournalData extends BaseEntity{
	/**
	 * Дата начала актуальности
	 */
	@Comment("Дата начала актуальности")
	
	public Date getUrgencyStartDate() {
		return theUrgencyStartDate;
	}
	public void setUrgencyStartDate(Date aUrgencyStartDate) {
		theUrgencyStartDate = aUrgencyStartDate;
	}
	/**
	 * Дата начала актуальности
	 */
	private Date theUrgencyStartDate;
	/**
	 * Дата окончания актуальности
	 */
	@Comment("Дата окончания актуальности")
	
	public Date getUrgencyExpiryDate() {
		return theUrgencyExpiryDate;
	}
	public void setUrgencyExpiryDate(Date aUrgencyExpiryDate) {
		theUrgencyExpiryDate = aUrgencyExpiryDate;
	}
	/**
	 * Дата окончания актуальности
	 */
	private Date theUrgencyExpiryDate;
	/**
	 * Удалено
	 */
	@Comment("Удалено")
	
	public Boolean getIsDeleted() {
		return theIsDeleted;
	}
	public void setIsDeleted(Boolean aIsDeleted) {
		theIsDeleted = aIsDeleted;
	}
	/**
	 * Удалено
	 */
	private Boolean theIsDeleted;
	/**
	 * Последнее состояние
	 */
	@Comment("Последнее состояние")
	
	public Boolean getIsLast() {
		return theIsLast;
	}
	public void setIsLast(Boolean aIsLast) {
		theIsLast = aIsLast;
	}
	/**
	 * Последнее состояние
	 */
	private Boolean theIsLast;

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;

}
