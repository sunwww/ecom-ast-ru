package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Операция с данными (CRUD)+P(purge)
	 */
	@Comment("Операция с данными (CRUD)+P(purge)")
@Entity
@Table(schema="SQLUser")
public class DataOperation extends BaseEntity{
	/**
	 * Изменения данных
	 */
	@Comment("Изменения данных")
	
	public String getChanges() {
		return theChanges;
	}
	public void setChanges(String aChanges) {
		theChanges = aChanges;
	}
	/**
	 * Изменения данных
	 */
	private String theChanges;
	/**
	 * Пользователь
	 */
	@Comment("Пользователь")
	@ManyToOne
	public SecUser getUser() {
		return theUser;
	}
	public void setUser(SecUser aUser) {
		theUser = aUser;
	}
	/**
	 * Пользователь
	 */
	private SecUser theUser;
	/**
	 * Имя класса данных
	 */
	@Comment("Имя класса данных")
	
	public String getClassName() {
		return theClassName;
	}
	public void setClassName(String aClassName) {
		theClassName = aClassName;
	}
	/**
	 * Имя класса данных
	 */
	private String theClassName;
	/**
	 * Дата операции
	 */
	@Comment("Дата операции")
	
	public Date getOperationDate() {
		return theOperationDate;
	}
	public void setOperationDate(Date aOperationDate) {
		theOperationDate = aOperationDate;
	}
	/**
	 * Дата операции
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
	 * ИД данных
	 */
	@Comment("ИД данных")
	
	public Long getDataId() {
		return theDataId;
	}
	public void setDataId(Long aDataId) {
		theDataId = aDataId;
	}
	/**
	 * ИД данных
	 */
	private Long theDataId;
}
