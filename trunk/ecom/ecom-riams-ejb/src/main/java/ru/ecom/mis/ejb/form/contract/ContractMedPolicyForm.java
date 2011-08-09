package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractMedPolicy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ContractMedPolicy.class)
@Comment("Медицинский полис по договору")
@WebTrail(comment = "Медицинский полис по договору", nameProperties= "id", list="entityParentList-contract_contractMedPolicy.do", view="entityParentView-contract_contractMedPolicy.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy")
public class ContractMedPolicyForm extends ContractGuaranteeForm {
	/**
	 * Медицинский полис
	 */
	@Comment("Медицинский полис")
	@Persist
	public Long getMedPolicy() {
		return theMedPolicy;
	}
	public void setMedPolicy(Long aMedPolicy) {
		theMedPolicy = aMedPolicy;
	}
	/**
	 * Медицинский полис
	 */
	private Long theMedPolicy;
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
	 * День рождения
	 */
	@Comment("День рождения")
	@Persist
	@DateString @DoDateString
	public String getBirthday() {
		return theBirthday;
	}
	public void setBirthday(String aBirthday) {
		theBirthday = aBirthday;
	}
	/**
	 * День рождения
	 */
	private String theBirthday;
	/**
	 * Серия
	 */
	@Comment("Серия")
	@Persist
	public String getSeries() {
		return theSeries;
	}
	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}
	/**
	 * Серия
	 */
	private String theSeries;
	/**
	 * Номер
	 */
	@Comment("Номер")
	@Persist
	public String getNumber() {
		return theNumber;
	}
	public void setNumber(String aNumber) {
		theNumber = aNumber;
	}
	/**
	 * Номер
	 */
	private String theNumber;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String theDateTo;
	/**
	 * Дата объявления недействительности
	 */
	@Comment("Дата объявления недействительности")
	@Persist
	@DateString @DoDateString
	public String getNullityDate() {
		return theNullityDate;
	}
	public void setNullityDate(String aNullityDate) {
		theNullityDate = aNullityDate;
	}
	/**
	 * Дата объявления недействительности
	 */
	private String theNullityDate;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@Persist
	public Long getServiceProgram() {
		return theServiceProgram;
	}
	public void setServiceProgram(Long aServiceProgram) {
		theServiceProgram = aServiceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private Long theServiceProgram;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@Persist
	public Long getServedPersonStatus() {
		return theServedPersonStatus;
	}
	public void setServedPersonStatus(Long aServedPersonStatus) {
		theServedPersonStatus = aServedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private Long theServedPersonStatus;
	/**
	 * Территория
	 */
	@Comment("Территория")
	@Persist
	public Long getTerritory() {
		return theTerritory;
	}
	public void setTerritory(Long aTerritory) {
		theTerritory = aTerritory;
	}
	/**
	 * Территория
	 */
	private Long theTerritory;
}
