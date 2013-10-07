package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.medos.ejb.persdata.domain.Person;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Исходящий документ
	 */
	@Comment("Исходящий документ")
@Entity
@Table(schema="SQLUser")
	@AIndexes(value = { 
			@AIndex(properties = { "person" }) }
	)

public class OutgoingDocument extends JournalData{
	/**
	 * Дата изготовления
	 */
	@Comment("Дата изготовления")
	
	public Date getProductionDate() {
		return theProductionDate;
	}
	public void setProductionDate(Date aProductionDate) {
		theProductionDate = aProductionDate;
	}
	/**
	 * Дата изготовления
	 */
	private Date theProductionDate;
	/**
	 * Дата выдачи
	 */
	@Comment("Дата выдачи")
	
	public Date getIssueDate() {
		return theIssueDate;
	}
	public void setIssueDate(Date aIssueDate) {
		theIssueDate = aIssueDate;
	}
	/**
	 * Дата выдачи
	 */
	private Date theIssueDate;
	/**
	 * Выдана лично владельцу
	 */
	@Comment("Выдана лично владельцу")
	
	public Boolean getIsPersonallyIssue() {
		return theIsPersonallyIssue;
	}
	public void setIsPersonallyIssue(Boolean aIsPersonallyIssue) {
		theIsPersonallyIssue = aIsPersonallyIssue;
	}
	/**
	 * Выдана лично владельцу
	 */
	private Boolean theIsPersonallyIssue;
	/**
	 * Номер
	 */
	@Comment("Номер")
	
	public String getDocumentNumber() {
		return theDocumentNumber;
	}
	public void setDocumentNumber(String aDocumentNumber) {
		theDocumentNumber = aDocumentNumber;
	}
	/**
	 * Номер
	 */
	private String theDocumentNumber;
	/**
	 * Персона
	 */
	@Comment("Персона")
	@ManyToOne
	public Person getPerson() {
		return thePerson;
	}
	public void setPerson(Person aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Персона
	 */
	private Person thePerson;
}
