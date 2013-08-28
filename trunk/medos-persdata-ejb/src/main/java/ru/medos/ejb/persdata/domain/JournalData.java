package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	@OneToMany(mappedBy="data", cascade=CascadeType.ALL)
	public List<DataOperation> getOperations() {
		return theOperations;
	}
	public void setOperations(List<DataOperation> aOperations) {
		theOperations = aOperations;
	}
	/**
	 * Операции
	 */
	private List<DataOperation> theOperations;
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
}
