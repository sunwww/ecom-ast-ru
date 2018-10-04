package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

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

	private String theLastname;
	private String theFirstname;
	private String theMiddlename;
	private Date theBirthday;
	private String theSnils;
	private Date theDeathDate;

	private String theStreet;
	private String theDepartment;
	private String theDoctorSnils;
	private String theOkato;
	private Boolean theIsDifference;
	private String theDocumentType;
	private String theDocumentSeries;
	private String theDocumentNumber;
	private String theCompanyCode;
	private String thePolicySeries;
	private String thePolicyNumber;
	private String theCommonNumber;
	private String theKladr;
	private String theHouse;
	private String theHouseBuilding;
	private String theFlat;
	private Date theCheckDate;
	private String theCheckType;
	private Date thePolicyDateFrom;
	private Date thePolicyDateTo;
	private String theCompanyOgrn;
	private String theCompanyOkato;
	private String theChecker;
	private String theCompabyCodeF;
	private String theLpuAttached;
	private String theAttachedType;
	private Date theAttachedDate;
	private String theDocumentDateIssued;
	private String theDocumentWhomIssued;

	private Long thePatient;
	private Boolean theIsAttachmentUpdate;
	private Boolean theIsPolicyUpdate;
	private Boolean theIsDocumentUpdate;
	private Boolean theIsPatientUpdate;
	private PatientFondCheckData theCheckTime;
	private Timestamp dateTimeCreate;

	public PatientFond() {
		this.dateTimeCreate = new Timestamp(System.currentTimeMillis());
		this.theCheckDate=new java.sql.Date(System.currentTimeMillis());
	}

	@Comment("Фамилия")
	public String getLastname() {
		return theLastname;
	}
	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}

	@Comment("Имя")
	public String getFirstname() {
		return theFirstname;
	}
	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}

	@Comment("Отчество")
	public String getMiddlename() {
		return theMiddlename;
	}
	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}

	@Comment("Дата рождения")
	public Date getBirthday() {
		return theBirthday;
	}
	public void setBirthday(Date aBirthday) {
		theBirthday = aBirthday;
	}

	@Comment("СНИЛС")
	public String getSnils() {
		return theSnils;
	}
	public void setSnils(String aSnils) {
		theSnils = aSnils;
	}

	@Comment("Вид идентифицирующего документа")
	public String getDocumentType() {
		return theDocumentType;
	}
	public void setDocumentType(String aDocumentType) {
		theDocumentType = aDocumentType;
	}

	@Comment("Серия идентифицирующего документа")
	public String getDocumentSeries() {
		return theDocumentSeries;
	}
	public void setDocumentSeries(String aDocumentSeries) {
		theDocumentSeries = aDocumentSeries;
	}

	@Comment("Номер идентифицирующего документа")
	public String getDocumentNumber() {
		return theDocumentNumber;
	}
	public void setDocumentNumber(String aDocumentNumber) {
		theDocumentNumber = aDocumentNumber;
	}

	@Comment("Региональный код страховой компании")
	public String getCompanyCode() {
		return theCompanyCode;
	}
	public void setCompanyCode(String aCompanyCode) {
		theCompanyCode = aCompanyCode;
	}

	@Comment("Серия полиса")
	public String getPolicySeries() {
		return thePolicySeries;
	}
	public void setPolicySeries(String aPolicySeries) {
		thePolicySeries = aPolicySeries;
	}

	@Comment("Номер полиса")
	public String getPolicyNumber() {
		return thePolicyNumber;
	}
	public void setPolicyNumber(String aPolicyNumber) {
		thePolicyNumber = aPolicyNumber;
	}

	@Comment("Единый номер")
	public String getCommonNumber() {
		return theCommonNumber;
	}
	public void setCommonNumber(String aCommonNumber) {
		theCommonNumber = aCommonNumber;
	}

	@Comment("Кладр")
	public String getKladr() {
		return theKladr;
	}
	public void setKladr(String aKladr) {
		theKladr = aKladr;
	}

	@Comment("Дом")
	public String getHouse() {
		return theHouse;
	}
	public void setHouse(String aHouse) {
		theHouse = aHouse;
	}

	@Comment("Корпус")
	public String getHouseBuilding() {
		return theHouseBuilding;
	}
	public void setHouseBuilding(String aHouseBuilding) {
		theHouseBuilding = aHouseBuilding;
	}

	@Comment("Квартира")
	public String getFlat() {
		return theFlat;
	}
	public void setFlat(String aFlat) {
		theFlat = aFlat;
	}

	@Comment("Дата проверки")
	public Date getCheckDate() {
		return theCheckDate;
	}
	public void setCheckDate(Date aCheckDate) {
		theCheckDate = aCheckDate;
	}

	@Comment("Тип проверки")
	public String getCheckType() {
		return theCheckType;
	}
	public void setCheckType(String aCheckType) {
		theCheckType = aCheckType;
	}

	@Comment("Дата начала действия полиса")
	public Date getPolicyDateFrom() {
		return thePolicyDateFrom;
	}
	public void setPolicyDateFrom(Date aPolicyDateFrom) {
		thePolicyDateFrom = aPolicyDateFrom;
	}

	@Comment("Дата окончания действия полиса")
	public Date getPolicyDateTo() {
		return thePolicyDateTo;
	}
	public void setPolicyDateTo(Date aPolicyDateTo) {
		thePolicyDateTo = aPolicyDateTo;
	}

	@Comment("ОГРН страховой компании")
	public String getCompanyOgrn() {
		return theCompanyOgrn;
	}
	public void setCompanyOgrn(String aCompanyOgrn) {
		theCompanyOgrn = aCompanyOgrn;
	}

	@Comment("ОКАТО страховой компании")
	public String getCompanyOkato() {
		return theCompanyOkato;
	}
	public void setCompanyOkato(String aCompanyOkato) {
		theCompanyOkato = aCompanyOkato;
	}

	@Comment("Кто проверял")
	public String getChecker() {
		return theChecker;
	}
	public void setChecker(String aChecker) {
		theChecker = aChecker;
	}

	@Comment("Федеральный код страховой компании")
	public String getCompabyCodeF() {
		return theCompabyCodeF;
	}
	public void setCompabyCodeF(String aCompabyCodeF) {
		theCompabyCodeF = aCompabyCodeF;
	}

	@Comment("ЛПУ прикрепления")
	public String getLpuAttached() {return theLpuAttached;}
	public void setLpuAttached(String aLpuAttached) {theLpuAttached = aLpuAttached;}

	@Comment("Тип прикрепления")
	public String getAttachedType() {return theAttachedType;}
	public void setAttachedType(String aAttachedType) {theAttachedType = aAttachedType;}

	@Comment("Дата прикрепления")
	public Date getAttachedDate() {return theAttachedDate;}
	public void setAttachedDate(Date aAttachedDate) {theAttachedDate = aAttachedDate;}

	@Comment("Дата смерти")
	public Date getDeathDate() {return theDeathDate;}
	public void setDeathDate(Date aDeathDate) {theDeathDate = aDeathDate;}

	@Comment("Код подразделения")
	public String getDepartment() {return theDepartment;}
	public void setDepartment(String aDepartment) {theDepartment = aDepartment;}

	@Comment("СНИЛС участкового врача")
	public String getDoctorSnils() {return theDoctorSnils;}
	public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}

	@Comment("Дата выдачи документа")
	public String getDocumentDateIssued() {return theDocumentDateIssued;}
	public void setDocumentDateIssued(String aDocumentDateIssued) {theDocumentDateIssued = aDocumentDateIssued;}

	@Comment("Кем выдан документа")
	public String getDocumentWhomIssued() {return theDocumentWhomIssued;}
	public void setDocumentWhomIssued(String aDocumentWhomIssued) {theDocumentWhomIssued = aDocumentWhomIssued;}

	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	@Comment("Прикрепление обновлено")
	public Boolean getIsAttachmentUpdate() {return theIsAttachmentUpdate;}
	public void setIsAttachmentUpdate(Boolean aIsAttachmentUpdate) {theIsAttachmentUpdate = aIsAttachmentUpdate;}

	@Comment("Полис обновлен")
	public Boolean getIsPolicyUpdate() {return theIsPolicyUpdate;}
	public void setIsPolicyUpdate(Boolean aIsPolicyUpdate) {theIsPolicyUpdate = aIsPolicyUpdate;}

	@Comment("Документы обновлены")
	public Boolean getIsDocumentUpdate() {return theIsDocumentUpdate;}
	public void setIsDocumentUpdate(Boolean aIsDocumentUpdate) {theIsDocumentUpdate = aIsDocumentUpdate;}

	@Comment("Пациент обновлен")
	public Boolean getIsPatientUpdate() {return theIsPatientUpdate;}
	public void setIsPatientUpdate(Boolean aIsPatientUpdate) {theIsPatientUpdate = aIsPatientUpdate;}

	@Comment("Данные проверки")
	@ManyToOne
	public PatientFondCheckData getCheckTime() {return theCheckTime;}
	public void setCheckTime(PatientFondCheckData aCheckTime) {theCheckTime = aCheckTime;}

	@Comment("Расхождение данных")
	public Boolean getIsDifference() {return theIsDifference;}
	public void setIsDifference(Boolean aIsDifference) {theIsDifference = aIsDifference;}

	@Comment("ОКАТО")
	public String getOkato() {return theOkato;}
	public void setOkato(String aOkato) {theOkato = aOkato;}

	@Comment("Улица")
	public String getStreet() {return theStreet;}
	public void setStreet(String aStreet) {theStreet = aStreet;}

	@Comment("Дата и время проверки")
	public Timestamp getDateTimeCreate() {
		return dateTimeCreate;
	}
	public void setDateTimeCreate(Timestamp dateTimeCreate) {
		this.dateTimeCreate = dateTimeCreate;
	}
}
