package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Журналируемые данные
	 */
	@Comment("Журналируемые данные")
@Entity
@Table(schema="SQLUser")
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
}
