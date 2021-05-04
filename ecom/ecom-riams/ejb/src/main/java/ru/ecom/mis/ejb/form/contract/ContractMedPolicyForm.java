package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
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
@Setter
public class ContractMedPolicyForm extends ContractGuaranteeForm {
	/**
	 * Медицинский полис
	 */
	@Comment("Медицинский полис")
	@Persist
	public Long getMedPolicy() {
		return medPolicy;
	}
	/**
	 * Медицинский полис
	 */
	private Long medPolicy;
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
	 * День рождения
	 */
	@Comment("День рождения")
	@Persist
	@DateString @DoDateString
	public String getBirthday() {
		return birthday;
	}
	/**
	 * День рождения
	 */
	private String birthday;
	/**
	 * Серия
	 */
	@Comment("Серия")
	@Persist
	public String getSeries() {
		return series;
	}
	/**
	 * Серия
	 */
	private String series;
	/**
	 * Номер
	 */
	@Comment("Номер")
	@Persist
	public String getNumber() {
		return number;
	}
	/**
	 * Номер
	 */
	private String number;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String dateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String dateTo;
	/**
	 * Дата объявления недействительности
	 */
	@Comment("Дата объявления недействительности")
	@Persist
	@DateString @DoDateString
	public String getNullityDate() {
		return nullityDate;
	}
	/**
	 * Дата объявления недействительности
	 */
	private String nullityDate;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@Persist
	public Long getServiceProgram() {
		return serviceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private Long serviceProgram;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@Persist
	public Long getServedPersonStatus() {
		return servedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private Long servedPersonStatus;
	/**
	 * Территория
	 */
	@Comment("Территория")
	@Persist
	public Long getTerritory() {
		return territory;
	}
	/**
	 * Территория
	 */
	private Long territory;
}
