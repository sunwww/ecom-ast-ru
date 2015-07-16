package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(name="journalPatientFondCheck", schema="SQLUser")
public class JournalPatientNotFoundFondCheck extends BaseEntity{ 
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
	/** Имя */
	private String theFirstname;

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}
	/** Отчество */
	private String theMiddlename;

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}
	/** Дата рождения */
	private Date theBirthday;

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Patient thePatient;
	
	/** Проверка по базе ФОМС */
	@Comment("Проверка по базе ФОМС")
	@OneToOne
	public PatientFondCheckData getCheckTime() {return theCheckTime;}
	public void setCheckTime(PatientFondCheckData aCheckTime) {theCheckTime = aCheckTime;}
	/** Проверка по базе ФОМС */
	private PatientFondCheckData theCheckTime;
	
	/** Найден в базе */
	@Comment("Найден в базе")
	public Boolean getIsFound() {return theIsFound;}
	public void setIsFound(Boolean aIsFound) {theIsFound = aIsFound;}
	/** Найден в базе */
	private Boolean theIsFound;
	/** ИД пациента (текст) */
	@Comment("ИД пациента (текст)")
	public String getRemovedPatientId() {return theRemovedPatientId;}
	public void setRemovedPatientId(String aRemovedPatientId) {theRemovedPatientId = aRemovedPatientId;}
	/** ИД пациента (текст) */
	private String theRemovedPatientId;
	
	/** Пациент удален */
	@Comment("Пациент удален")
	public Boolean getIsPatientRemoved() {return theIsPatientRemoved;}
	public void setIsPatientRemoved(Boolean aIsPatientRemoved) {theIsPatientRemoved = aIsPatientRemoved;}
	/** Пациент удален */
	private Boolean theIsPatientRemoved;
}
