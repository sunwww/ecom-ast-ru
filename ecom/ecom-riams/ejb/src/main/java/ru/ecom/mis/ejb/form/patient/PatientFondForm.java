package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.PatientFond;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = PatientFond.class)
@Comment("Пациент ФОМС")
@WebTrail(comment = "Пациент ФОМС", nameProperties= "id", list="entityParentList-patient_patientFond.do", view="entityParentView-patient_patientFond.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/Fond")
@Setter
public class PatientFondForm extends IdEntityForm{
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	@Persist
	public String getLastname() {
		return lastname;
	}
	/**
	 * Фамилия
	 */
	private String lastname;
	/**
	 * Имя
	 */
	@Comment("Имя")
	@Persist
	public String getFirstname() {
		return firstname;
	}
	/**
	 * Имя
	 */
	private String firstname;
	/**
	 * Отчество
	 */
	@Comment("Отчество")
	@Persist
	public String getMiddlename() {
		return middlename;
	}
	/**
	 * Отчество
	 */
	private String middlename;
	/**
	 * Дата рождения
	 */
	@Comment("Дата рождения")
	@Persist
	@DateString @DoDateString
	public String getBirthday() {
		return birthday;
	}
	/**
	 * Дата рождения
	 */
	private String birthday;
	/**
	 * СНИЛС
	 */
	@Comment("СНИЛС")
	@Persist
	public String getSnils() {
		return snils;
	}
	/**
	 * СНИЛС
	 */
	private String snils;
	/**
	 * Вид идентифицирующего документа
	 */
	@Comment("Вид идентифицирующего документа")
	@Persist
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * Вид идентифицирующего документа
	 */
	private String documentType;
	/**
	 * Серия идентифицирующего документа
	 */
	@Comment("Серия идентифицирующего документа")
	@Persist
	public String getDocumentSeries() {
		return documentSeries;
	}
	/**
	 * Серия идентифицирующего документа
	 */
	private String documentSeries;
	/**
	 * Номер идентифицирующего документа
	 */
	@Comment("Номер идентифицирующего документа")
	@Persist
	public String getDocumentNumber() {
		return documentNumber;
	}
	/**
	 * Номер идентифицирующего документа
	 */
	private String documentNumber;
	/**
	 * Региональный код страховой компании
	 */
	@Comment("Региональный код страховой компании")
	@Persist
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * Региональный код страховой компании
	 */
	private String companyCode;
	/**
	 * Серия полиса
	 */
	@Comment("Серия полиса")
	@Persist
	public String getPolicySeries() {
		return policySeries;
	}
	/**
	 * Серия полиса
	 */
	private String policySeries;
	/**
	 * Номер полиса
	 */
	@Comment("Номер полиса")
	@Persist
	public String getPolicyNumber() {
		return policyNumber;
	}
	/**
	 * Номер полиса
	 */
	private String policyNumber;
	/**
	 * Единый номер
	 */
	@Comment("Единый номер")
	@Persist
	public String getCommonNumber() {
		return commonNumber;
	}
	/**
	 * Единый номер
	 */
	private String commonNumber;
	/**
	 * Кладр
	 */
	@Comment("Кладр")
	@Persist
	public String getKladr() {
		return kladr;
	}
	/**
	 * Кладр
	 */
	private String kladr;
	/**
	 * Дом
	 */
	@Comment("Дом")
	@Persist
	public String getHouse() {
		return house;
	}
	/**
	 * Дом
	 */
	private String house;
	/**
	 * Корпус
	 */
	@Comment("Корпус")
	@Persist
	public String getHouseBuilding() {
		return houseBuilding;
	}
	/**
	 * Корпус
	 */
	private String houseBuilding;
	/**
	 * Квартира
	 */
	@Comment("Квартира")
	@Persist
	public String getFlat() {
		return flat;
	}
	/**
	 * Квартира
	 */
	private String flat;
	/**
	 * Дата проверки
	 */
	@Comment("Дата проверки")
	@Persist
	@DateString @DoDateString
	public String getCheckDate() {
		return checkDate;
	}
	/**
	 * Дата проверки
	 */
	private String checkDate;
	/**
	 * Тип проверки
	 */
	@Comment("Тип проверки")
	@Persist
	public String getCheckType() {
		return checkType;
	}
	/**
	 * Тип проверки
	 */
	private String checkType;
	/**
	 * Дата начала действия полиса
	 */
	@Comment("Дата начала действия полиса")
	@Persist
	@DateString @DoDateString
	public String getPolicyDateFrom() {
		return policyDateFrom;
	}
	/**
	 * Дата начала действия полиса
	 */
	private String policyDateFrom;
	/**
	 * Дата окончания действия полиса
	 */
	@Comment("Дата окончания действия полиса")
	@Persist
	@DateString @DoDateString
	public String getPolicyDateTo() {
		return policyDateTo;
	}
	/**
	 * Дата окончания действия полиса
	 */
	private String policyDateTo;
	/**
	 * ОГРН страховой компании
	 */
	@Comment("ОГРН страховой компании")
	@Persist
	public String getCompanyOgrn() {
		return companyOgrn;
	}
	/**
	 * ОГРН страховой компании
	 */
	private String companyOgrn;
	/**
	 * ОКАТО страховой компании
	 */
	@Comment("ОКАТО страховой компании")
	@Persist
	public String getCompanyOkato() {
		return companyOkato;
	}
	/**
	 * ОКАТО страховой компании
	 */
	private String companyOkato;
	/**
	 * Кто проверял
	 */
	@Comment("Кто проверял")
	@Persist
	public String getChecker() {
		return checker;
	}
	/**
	 * Кто проверял
	 */
	private String checker;
	/**
	 * Федеральный код страховой компании
	 */
	@Comment("Федеральный код страховой компании")
	@Persist
	public String getCompabyCodeF() {
		return compabyCodeF;
	}
	/**
	 * Федеральный код страховой компании
	 */
	private String compabyCodeF;

}
