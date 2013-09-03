package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.medos.ejb.persdata.domain.Person;
import ru.medos.ejb.persdata.domain.voc.VocContactPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Контактная персона
	 */
	@Comment("Контактная персона")
@Entity
@Table(schema="SQLUser")
public class ContactPerson extends JournalData{
	/**
	 * Контактная персона
	 */
	@Comment("Контактная персона")
	@ManyToOne
	public Person getContactPerson() {
		return theContactPerson;
	}
	public void setContactPerson(Person aContactPerson) {
		theContactPerson = aContactPerson;
	}
	/**
	 * Контактная персона
	 */
	private Person theContactPerson;
	/**
	 * Тип связи с персоной
	 */
	@Comment("Тип связи с персоной")
	@OneToOne
	public VocContactPerson getContactType() {
		return theContactType;
	}
	public void setContactType(VocContactPerson aContactType) {
		theContactType = aContactType;
	}
	/**
	 * Тип связи с персоной
	 */
	private VocContactPerson theContactType;
	/**
	 * Персона
	 */
	@Comment("Персона")
	@OneToOne
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
	/**
	 * Основной контакт
	 */
	@Comment("Основной контакт")
	
	public Boolean getIsPrimary() {
		return theIsPrimary;
	}
	public void setIsPrimary(Boolean aIsPrimary) {
		theIsPrimary = aIsPrimary;
	}
	/**
	 * Основной контакт
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
