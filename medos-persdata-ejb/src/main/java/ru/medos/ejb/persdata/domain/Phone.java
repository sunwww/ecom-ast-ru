package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.medos.ejb.persdata.domain.Person;
import ru.medos.ejb.persdata.domain.voc.VocPhone;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Телефон
	 */
	@Comment("Телефон")
@Entity
@Table(schema="SQLUser")
	@AIndexes(value = { 
			@AIndex(properties = { "person" }) }
	)

public class Phone extends JournalData{
	/**
	 * Персоны
	 */
	@Comment("Персоны")
	@ManyToOne
	public Person getPerson() {
		return thePerson;
	}
	public void setPerson(Person aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Персоны
	 */
	private Person thePerson;
	/**
	 * Тип телефона
	 */
	@Comment("Тип телефона")
	@OneToOne
	public VocPhone getPhoneType() {
		return thePhoneType;
	}
	public void setPhoneType(VocPhone aPhoneType) {
		thePhoneType = aPhoneType;
	}
	/**
	 * Тип телефона
	 */
	private VocPhone thePhoneType;
	/**
	 * Номер телефона
	 */
	@Comment("Номер телефона")
	
	public String getPhoneNumber() {
		return thePhoneNumber;
	}
	public void setPhoneNumber(String aPhoneNumber) {
		thePhoneNumber = aPhoneNumber;
	}
	/**
	 * Номер телефона
	 */
	private String thePhoneNumber;
	/**
	 * Основной телефон
	 */
	@Comment("Основной телефон")
	
	public Boolean getIsPrimary() {
		return theIsPrimary;
	}
	public void setIsPrimary(Boolean aIsPrimary) {
		theIsPrimary = aIsPrimary;
	}
	/**
	 * Основной телефон
	 */
	private Boolean theIsPrimary;
	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарий
	 */
	private String theComment;
}
