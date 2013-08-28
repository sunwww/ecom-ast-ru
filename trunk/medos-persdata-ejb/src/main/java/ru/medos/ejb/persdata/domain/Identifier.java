package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.medos.personaldata.Person;
import ru.medos.personaldata.voc.VocIdentificationSystem;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Идентификатор
	 */
	@Comment("Идентификатор")
@Entity
@Table(schema="SQLUser")
public class Identifier extends JournalData{
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
	/**
	 * Идентифицирующая система
	 */
	@Comment("Идентифицирующая система")
	@OneToOne
	public VocIdentificationSystem getIdentificationSystem() {
		return theIdentificationSystem;
	}
	public void setIdentificationSystem(VocIdentificationSystem aIdentificationSystem) {
		theIdentificationSystem = aIdentificationSystem;
	}
	/**
	 * Идентифицирующая система
	 */
	private VocIdentificationSystem theIdentificationSystem;
	/**
	 * Временный
	 */
	@Comment("Временный")
	
	public Boolean getIsTransient() {
		return theIsTransient;
	}
	public void setIsTransient(Boolean aIsTransient) {
		theIsTransient = aIsTransient;
	}
	/**
	 * Временный
	 */
	private Boolean theIsTransient;
	/**
	 * Идентификационный номер
	 */
	@Comment("Идентификационный номер")
	
	public String getIdentificationNumber() {
		return theIdentificationNumber;
	}
	public void setIdentificationNumber(String aIdentificationNumber) {
		theIdentificationNumber = aIdentificationNumber;
	}
	/**
	 * Идентификационный номер
	 */
	private String theIdentificationNumber;
}
