package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.medos.ejb.persdata.domain.Person;
import ru.medos.ejb.persdata.domain.voc.VocIdentificationSystem;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Экстренная идентификация
	 */
	@Comment("Экстренная идентификация")
@Entity
@Table(schema="SQLUser")
public class EmergencyIdentification extends BaseEntity{
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
	 * ФИО звонившего
	 */
	@Comment("ФИО звонившего")
	
	public String getCallerFullname() {
		return theCallerFullname;
	}
	public void setCallerFullname(String aCallerFullname) {
		theCallerFullname = aCallerFullname;
	}
	/**
	 * ФИО звонившего
	 */
	private String theCallerFullname;
	/**
	 * Должность звонившего
	 */
	@Comment("Должность звонившего")
	
	public String getCallerPost() {
		return theCallerPost;
	}
	public void setCallerPost(String aCallerPost) {
		theCallerPost = aCallerPost;
	}
	/**
	 * Должность звонившего
	 */
	private String theCallerPost;
	/**
	 * Откуда звонил
	 */
	@Comment("Откуда звонил")
	@OneToOne
	public VocIdentificationSystem getCallerSystem() {
		return theCallerSystem;
	}
	public void setCallerSystem(VocIdentificationSystem aCallerSystem) {
		theCallerSystem = aCallerSystem;
	}
	/**
	 * Откуда звонил
	 */
	private VocIdentificationSystem theCallerSystem;
}
