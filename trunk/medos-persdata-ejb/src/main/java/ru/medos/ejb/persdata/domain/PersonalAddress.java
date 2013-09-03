package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.medos.ejb.persdata.domain.Address;
import ru.medos.ejb.persdata.domain.Person;
import ru.medos.ejb.persdata.domain.voc.VocAddress;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Персональный адрес
	 */
	@Comment("Персональный адрес")
@Entity
@Table(schema="SQLUser")
public class PersonalAddress extends JournalData{
	/**
	 * Квартира
	 */
	@Comment("Квартира")
	
	public String getFlat() {
		return theFlat;
	}
	public void setFlat(String aFlat) {
		theFlat = aFlat;
	}
	/**
	 * Квартира
	 */
	private String theFlat;
	/**
	 * Корпус
	 */
	@Comment("Корпус")
	
	public String getBuilding() {
		return theBuilding;
	}
	public void setBuilding(String aBuilding) {
		theBuilding = aBuilding;
	}
	/**
	 * Корпус
	 */
	private String theBuilding;
	/**
	 * Дом
	 */
	@Comment("Дом")
	
	public String getHouse() {
		return theHouse;
	}
	public void setHouse(String aHouse) {
		theHouse = aHouse;
	}
	/**
	 * Дом
	 */
	private String theHouse;
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
	 * Адрес
	 */
	@Comment("Адрес")
	@OneToOne
	public Address getAddress() {
		return theAddress;
	}
	public void setAddress(Address aAddress) {
		theAddress = aAddress;
	}
	/**
	 * Адрес
	 */
	private Address theAddress;
	/**
	 * Тип адреса
	 */
	@Comment("Тип адреса")
	@OneToOne
	public VocAddress getType() {
		return theType;
	}
	public void setType(VocAddress aType) {
		theType = aType;
	}
	/**
	 * Тип адреса
	 */
	private VocAddress theType;
}
