package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.PersonalAddress;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = PersonalAddress.class)
@Comment("Персона")
@WebTrail(comment = "Персона", nameProperties= "id"
, view="entityParentView-pd_address.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/Person")
public class PersonalAddressForm extends JournalDataForm{
	/**
	 * Квартира
	 */
	@Comment("Квартира")
	@Persist
	public String getFlat() {return theFlat;}
	public void setFlat(String aFlat) {theFlat = aFlat;}
	/**
	 * Квартира
	 */
	private String theFlat;
	/**
	 * Корпус
	 */
	@Comment("Корпус")
	@Persist
	public String getBuilding() {return theBuilding;}
	public void setBuilding(String aBuilding) {theBuilding = aBuilding;}
	/**
	 * Корпус
	 */
	private String theBuilding;
	/**
	 * Дом
	 */
	@Comment("Дом")
	@Persist
	public String getHouse() {return theHouse;}
	public void setHouse(String aHouse) {theHouse = aHouse;}
	/**
	 * Дом
	 */
	private String theHouse;
	/**
	 * Персона
	 */
	@Comment("Персона")
	@Persist
	public Long getPerson() {return thePerson;}
	public void setPerson(Long aPerson) {thePerson = aPerson;}
	/**
	 * Персона
	 */
	private Long thePerson;
	/**
	 * Адрес
	 */
	@Comment("Адрес")
	@Persist
	public Long getAddress() {return theAddress;}
	public void setAddress(Long aAddress) {theAddress = aAddress;}
	/**
	 * Адрес
	 */
	private Long theAddress;
	/**
	 * Тип адреса
	 */
	@Comment("Тип адреса")
	@Persist
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}
	/**
	 * Тип адреса
	 */
	private Long theType;
}
