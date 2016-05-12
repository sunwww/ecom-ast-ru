package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Журнал изменений по пациенту
 * @author user
 *
 */
@Entity
@AIndexes({
	@AIndex(properties={"patient"})
    , @AIndex(unique = false, properties= {"lastname","firstname","middlename", "birthday"})
})
@Table(schema="SQLUser")
public class JournalChangePatient extends BaseEntity {
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

	/** Фамилия */
	private String theLastname;
	
	/** Имя */
	@Comment("Имя")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}

	/** Адрес */
	@Comment("Адрес")
	@OneToOne
	public Address getAddress() {return theAddress;}
	public void setAddress(Address aAddress) {theAddress = aAddress;}

	/** Адрес проживания */
	@Comment("Адрес проживания")
	@OneToOne
	public Address getRealAddress() {return theRealAddress;}
	public void setRealAddress(Address aRealAddress) {theRealAddress = aRealAddress;}

	/** Адрес проживания */
	private Address theRealAddress;
	/** Адрес */
	private Address theAddress;
	/** Дата рождения */
	private Date theBirthday;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Patient thePatient;
	
	/** Дата изменения */
	@Comment("Дата изменения")
	public Date getChangeDate() {return theChangeDate;}
	public void setChangeDate(Date aChangeDate) {theChangeDate = aChangeDate;}

	/** Время изменения */
	@Comment("Время изменения")
	public Time getChangeTime() {return theChangeTime;}
	public void setChangeTime(Time aChangeTime) {theChangeTime = aChangeTime;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getChangeUsername() {return theChangeUsername;}
	public void setChangeUsername(String aChangeUsername) {theChangeUsername = aChangeUsername;}

	/** Пользователь */
	private String theChangeUsername;
	/** Время изменения */
	private Time theChangeTime;
	/** Дата изменения */
	private Date theChangeDate;
}
