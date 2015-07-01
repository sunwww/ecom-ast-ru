package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Пациент ФОМС
	 */
	@Comment("Пациент ФОМС")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "lastname","firstname","middlename","birthday" }),
		@AIndex(properties = { "commonNumber" })
})
public class PatientFond extends BaseEntity{
	public final static String STATUS_CHECK_TYPE_AUTOMATIC="A" ;
	public final static String STATUS_CHECK_TYPE_PACKAGE="P" ;
	public final static String STATUS_CHECK_TYPE_MANUAL="M" ;
	
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
	 * Отчество
	 */
	@Comment("Отчество")
	public String getMiddlename() {
		return theMiddlename;
	}
	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}
	/**
	 * Отчество
	 */
	private String theMiddlename;
	/**
	 * Дата рождения
	 */
	@Comment("Дата рождения")
	public Date getBirthday() {
		return theBirthday;
	}
	public void setBirthday(Date aBirthday) {
		theBirthday = aBirthday;
	}
	/**
	 * Дата рождения
	 */
	private Date theBirthday;
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
	 * Вид идентифицирующего документа
	 */
	@Comment("Вид идентифицирующего документа")
	public String getDocumentType() {
		return theDocumentType;
	}
	public void setDocumentType(String aDocumentType) {
		theDocumentType = aDocumentType;
	}
	/**
	 * Вид идентифицирующего документа
	 */
	private String theDocumentType;
	/**
	 * Серия идентифицирующего документа
	 */
	@Comment("Серия идентифицирующего документа")
	public String getDocumentSeries() {
		return theDocumentSeries;
	}
	public void setDocumentSeries(String aDocumentSeries) {
		theDocumentSeries = aDocumentSeries;
	}
	/**
	 * Серия идентифицирующего документа
	 */
	private String theDocumentSeries;
	/**
	 * Номер идентифицирующего документа
	 */
	@Comment("Номер идентифицирующего документа")
	public String getDocumentNumber() {
		return theDocumentNumber;
	}
	public void setDocumentNumber(String aDocumentNumber) {
		theDocumentNumber = aDocumentNumber;
	}
	/**
	 * Номер идентифицирующего документа
	 */
	private String theDocumentNumber;
	/**
	 * Региональный код страховой компании
	 */
	@Comment("Региональный код страховой компании")
	public String getCompanyCode() {
		return theCompanyCode;
	}
	public void setCompanyCode(String aCompanyCode) {
		theCompanyCode = aCompanyCode;
	}
	/**
	 * Региональный код страховой компании
	 */
	private String theCompanyCode;
	/**
	 * Серия полиса
	 */
	@Comment("Серия полиса")
	public String getPolicySeries() {
		return thePolicySeries;
	}
	public void setPolicySeries(String aPolicySeries) {
		thePolicySeries = aPolicySeries;
	}
	/**
	 * Серия полиса
	 */
	private String thePolicySeries;
	/**
	 * Номер полиса
	 */
	@Comment("Номер полиса")
	public String getPolicyNumber() {
		return thePolicyNumber;
	}
	public void setPolicyNumber(String aPolicyNumber) {
		thePolicyNumber = aPolicyNumber;
	}
	/**
	 * Номер полиса
	 */
	private String thePolicyNumber;
	/**
	 * Единый номер
	 */
	@Comment("Единый номер")
	public String getCommonNumber() {
		return theCommonNumber;
	}
	public void setCommonNumber(String aCommonNumber) {
		theCommonNumber = aCommonNumber;
	}
	/**
	 * Единый номер
	 */
	private String theCommonNumber;
	/**
	 * Кладр
	 */
	@Comment("Кладр")
	public String getKladr() {
		return theKladr;
	}
	public void setKladr(String aKladr) {
		theKladr = aKladr;
	}
	/**
	 * Кладр
	 */
	private String theKladr;
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
	 * Корпус
	 */
	@Comment("Корпус")
	public String getHouseBuilding() {
		return theHouseBuilding;
	}
	public void setHouseBuilding(String aHouseBuilding) {
		theHouseBuilding = aHouseBuilding;
	}
	/**
	 * Корпус
	 */
	private String theHouseBuilding;
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
	 * Дата проверки
	 */
	@Comment("Дата проверки")
	public Date getCheckDate() {
		return theCheckDate;
	}
	public void setCheckDate(Date aCheckDate) {
		theCheckDate = aCheckDate;
	}
	/**
	 * Дата проверки
	 */
	private Date theCheckDate;
	/**
	 * Тип проверки
	 */
	@Comment("Тип проверки")
	public String getCheckType() {
		return theCheckType;
	}
	public void setCheckType(String aCheckType) {
		theCheckType = aCheckType;
	}
	/**
	 * Тип проверки
	 */
	private String theCheckType;
	/**
	 * Дата начала действия полиса
	 */
	@Comment("Дата начала действия полиса")
	public Date getPolicyDateFrom() {
		return thePolicyDateFrom;
	}
	public void setPolicyDateFrom(Date aPolicyDateFrom) {
		thePolicyDateFrom = aPolicyDateFrom;
	}
	/**
	 * Дата начала действия полиса
	 */
	private Date thePolicyDateFrom;
	/**
	 * Дата окончания действия полиса
	 */
	@Comment("Дата окончания действия полиса")
	public Date getPolicyDateTo() {
		return thePolicyDateTo;
	}
	public void setPolicyDateTo(Date aPolicyDateTo) {
		thePolicyDateTo = aPolicyDateTo;
	}
	/**
	 * Дата окончания действия полиса
	 */
	private Date thePolicyDateTo;
	/**
	 * ОГРН страховой компании
	 */
	@Comment("ОГРН страховой компании")
	public String getCompanyOgrn() {
		return theCompanyOgrn;
	}
	public void setCompanyOgrn(String aCompanyOgrn) {
		theCompanyOgrn = aCompanyOgrn;
	}
	/**
	 * ОГРН страховой компании
	 */
	private String theCompanyOgrn;
	/**
	 * ОКАТО страховой компании
	 */
	@Comment("ОКАТО страховой компании")
	public String getCompanyOkato() {
		return theCompanyOkato;
	}
	public void setCompanyOkato(String aCompanyOkato) {
		theCompanyOkato = aCompanyOkato;
	}
	/**
	 * ОКАТО страховой компании
	 */
	private String theCompanyOkato;
	/**
	 * Кто проверял
	 */
	@Comment("Кто проверял")
	public String getChecker() {
		return theChecker;
	}
	public void setChecker(String aChecker) {
		theChecker = aChecker;
	}
	/**
	 * Кто проверял
	 */
	private String theChecker;
	/**
	 * Федеральный код страховой компании
	 */
	@Comment("Федеральный код страховой компании")
	public String getCompabyCodeF() {
		return theCompabyCodeF;
	}
	public void setCompabyCodeF(String aCompabyCodeF) {
		theCompabyCodeF = aCompabyCodeF;
	}
	/**
	 * Федеральный код страховой компании
	 */
	private String theCompabyCodeF;
	
	/** ЛПУ прикрепления */
	@Comment("ЛПУ прикрепления")
	public String getLpuAttached() {return theLpuAttached;}
	public void setLpuAttached(String aLpuAttached) {theLpuAttached = aLpuAttached;}
	/** ЛПУ прикрепления */
	private String theLpuAttached;
	
	/** Тип прикрепления */
	@Comment("Тип прикрепления")
	public String getAttachedType() {return theAttachedType;}
	public void setAttachedType(String aAttachedType) {theAttachedType = aAttachedType;}
	/** Тип прикрепления */
	private String theAttachedType;
	
	/** Дата прикрепления */
	@Comment("Дата прикрепления")
	public Date getAttachedDate() {return theAttachedDate;}
	public void setAttachedDate(Date aAttachedDate) {theAttachedDate = aAttachedDate;}
	/** Дата прикрепления */
	private Date theAttachedDate;
	
	/** Дата смерти */
	@Comment("Дата смерти")
	public Date getDeathDate() {return theDeathDate;}
	public void setDeathDate(Date aDeathDate) {theDeathDate = aDeathDate;}
	/** Дата смерти */
	private Date theDeathDate;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	public String getDepartment() {return theDepartment;}
	public void setDepartment(String aDepartment) {theDepartment = aDepartment;}
	/** Код подразделения */
	private String theDepartment;
	
	/** СНИЛС участкового врача */
	@Comment("СНИЛС участкового врача")
	public String getDoctorSnils() {return theDoctorSnils;}
	public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}
	/** СНИЛС участкового врача */
	private String theDoctorSnils;
	
	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	public String getDocumentDateIssued() {return theDocumentDateIssued;}
	public void setDocumentDateIssued(String aDocumentDateIssued) {theDocumentDateIssued = aDocumentDateIssued;}
	/** Дата выдачи документа */
	private String theDocumentDateIssued;
	
	/** Кем выдан документа */
	@Comment("Кем выдан документа")
	public String getDocumentWhomIssued() {return theDocumentWhomIssued;}
	public void setDocumentWhomIssued(String aDocumentWhomIssued) {theDocumentWhomIssued = aDocumentWhomIssued;}
	/** Кем выдан документа */
	private String theDocumentWhomIssued;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Patient thePatient;

	/** Пациент обновлен */
	@Comment("Пациент обновлен")
	public Boolean getIsPatientUpdate() {return theIsPatientUpdate;}
	public void setIsPatientUpdate(Boolean aIsPatientUpdate) {theIsPatientUpdate = aIsPatientUpdate;}
	/** Пациент обновлен */
	private Boolean theIsPatientUpdate;
	
	/** Данные проверки */
	@Comment("Данные проверки")
	@ManyToOne
	public PatientFondCheckData getCheckTime() {return theCheckTime;}
	public void setCheckTime(PatientFondCheckData aCheckTime) {theCheckTime = aCheckTime;}
	/** Данные проверки */
	private PatientFondCheckData theCheckTime;
}
