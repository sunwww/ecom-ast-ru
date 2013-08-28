package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.medos.ejb.persdata.domain.voc.VocNationality;
import ru.medos.ejb.persdata.domain.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Персона
	 */
	@Comment("Персона")
@Entity
@Table(schema="SQLUser")
public class Person extends JournalData{
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<PersonalAddress> getAddresses() {
		return theAddresses;
	}
	public void setAddresses(List<PersonalAddress> aAddresses) {
		theAddresses = aAddresses;
	}
	/**
	 * Адреса
	 */
	private List<PersonalAddress> theAddresses;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<ContactPerson> getContactPersons() {
		return theContactPersons;
	}
	public void setContactPersons(List<ContactPerson> aContactPersons) {
		theContactPersons = aContactPersons;
	}
	/**
	 * Контактные персоны
	 */
	private List<ContactPerson> theContactPersons;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<Identifier> getIdentifier() {
		return theIdentifier;
	}
	public void setIdentifier(List<Identifier> aIdentifier) {
		theIdentifier = aIdentifier;
	}
	/**
	 * Идентификатор
	 */
	private List<Identifier> theIdentifier;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<Phone> getPhones() {
		return thePhones;
	}
	public void setPhones(List<Phone> aPhones) {
		thePhones = aPhones;
	}
	/**
	 * Телефоны
	 */
	private List<Phone> thePhones;
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	
	public String getLastname() {
		return theLastname;
	}
	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}
	/**
	 * Фамилия
	 */
	private String theLastname;
	/**
	 * Отчество
	 */
	@Comment("Отчество")
	
	public String getPatronymic() {
		return thePatronymic;
	}
	public void setPatronymic(String aPatronymic) {
		thePatronymic = aPatronymic;
	}
	/**
	 * Отчество
	 */
	private String thePatronymic;
	/**
	 * Имя
	 */
	@Comment("Имя")
	
	public String getFirstname() {
		return theFirstname;
	}
	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}
	/**
	 * Имя
	 */
	private String theFirstname;
	/**
	 * Дата рождения
	 */
	@Comment("Дата рождения")
	
	public Date getBirthdate() {
		return theBirthdate;
	}
	public void setBirthdate(Date aBirthdate) {
		theBirthdate = aBirthdate;
	}
	/**
	 * Дата рождения
	 */
	private Date theBirthdate;
	/**
	 * Пол
	 */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return theSex;
	}
	public void setSex(VocSex aSex) {
		theSex = aSex;
	}
	/**
	 * Пол
	 */
	private VocSex theSex;
	/**
	 * СНИЛС
	 */
	@Comment("СНИЛС")
	
	public String getSnils() {
		return theSnils;
	}
	public void setSnils(String aSnils) {
		theSnils = aSnils;
	}
	/**
	 * СНИЛС
	 */
	private String theSnils;
	/**
	 * Национальность
	 */
	@Comment("Национальность")
	@OneToOne
	public VocNationality getNationality() {
		return theNationality;
	}
	public void setNationality(VocNationality aNationality) {
		theNationality = aNationality;
	}
	/**
	 * Национальность
	 */
	private VocNationality theNationality;
	/**
	 * Электронная почта
	 */
	@Comment("Электронная почта")
	
	public String getEmail() {
		return theEmail;
	}
	public void setEmail(String aEmail) {
		theEmail = aEmail;
	}
	/**
	 * Электронная почта
	 */
	private String theEmail;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<ComingDocument> getComingDocuments() {
		return theComingDocuments;
	}
	public void setComingDocuments(List<ComingDocument> aComingDocuments) {
		theComingDocuments = aComingDocuments;
	}
	/**
	 * Входящие документы
	 */
	private List<ComingDocument> theComingDocuments;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<EmergencyIdentification> getEmergencyIdentifications() {
		return theEmergencyIdentifications;
	}
	public void setEmergencyIdentifications(List<EmergencyIdentification> aEmergencyIdentifications) {
		theEmergencyIdentifications = aEmergencyIdentifications;
	}
	/**
	 * Экстренные идентификации
	 */
	private List<EmergencyIdentification> theEmergencyIdentifications;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<OutgoingDocument> getOutgoingDocuments() {
		return theOutgoingDocuments;
	}
	public void setOutgoingDocuments(List<OutgoingDocument> aOutgoingDocuments) {
		theOutgoingDocuments = aOutgoingDocuments;
	}
	/**
	 * Исходящие документы
	 */
	private List<OutgoingDocument> theOutgoingDocuments;
}
