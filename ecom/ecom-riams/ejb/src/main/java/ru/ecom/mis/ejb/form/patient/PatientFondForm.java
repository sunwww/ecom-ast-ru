package ru.ecom.mis.ejb.form.patient;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientFond;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = PatientFond.class)
@Comment("Пациент ФОМС")
@WebTrail(comment = "Пациент ФОМС", nameProperties= "id", list="entityParentList-patient_patientFond.do", view="entityParentView-patient_patientFond.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/Fond")
public class PatientFondForm extends IdEntityForm{
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
	@DateString @DoDateString
	public String getBirthday() {
		return theBirthday;
	}
	public void setBirthday(String aBirthday) {
		theBirthday = aBirthday;
	}
	/**
	 * Дата рождения
	 */
	private String theBirthday;
	/**
	 * СНИЛС
	 */
	@Comment("СНИЛС")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
	@DateString @DoDateString
	public String getCheckDate() {
		return theCheckDate;
	}
	public void setCheckDate(String aCheckDate) {
		theCheckDate = aCheckDate;
	}
	/**
	 * Дата проверки
	 */
	private String theCheckDate;
	/**
	 * Тип проверки
	 */
	@Comment("Тип проверки")
	@Persist
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
	@Persist
	@DateString @DoDateString
	public String getPolicyDateFrom() {
		return thePolicyDateFrom;
	}
	public void setPolicyDateFrom(String aPolicyDateFrom) {
		thePolicyDateFrom = aPolicyDateFrom;
	}
	/**
	 * Дата начала действия полиса
	 */
	private String thePolicyDateFrom;
	/**
	 * Дата окончания действия полиса
	 */
	@Comment("Дата окончания действия полиса")
	@Persist
	@DateString @DoDateString
	public String getPolicyDateTo() {
		return thePolicyDateTo;
	}
	public void setPolicyDateTo(String aPolicyDateTo) {
		thePolicyDateTo = aPolicyDateTo;
	}
	/**
	 * Дата окончания действия полиса
	 */
	private String thePolicyDateTo;
	/**
	 * ОГРН страховой компании
	 */
	@Comment("ОГРН страховой компании")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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

}
