package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountMedService.class)
@Comment("Медицинские услуги")
@WebTrail(comment = "Медицинские услуги", nameProperties= "id"
, view="entityParentView-contract_juridicalAccountMedService.do")
@Parent(property="account", parentForm=ContractAccountJuridicalForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/JuridicalMedService")
public class ContractAccountMedServiceJuridicalForm extends IdEntityForm{

	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {return theServedPerson;}
	public void setServedPerson(Long aServedPerson) {theServedPerson = aServedPerson;}
	/**
	 * Обслуживаемая персона
	 */
	private Long theServedPerson;
	
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@Persist
	public Long getAccount() {return theAccount;}
	public void setAccount(Long aAccount) {theAccount = aAccount;}
	/**
	 * Договорной счет
	 */
	private Long theAccount;
	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	@Persist @Required
	public Long getMedService() {
		return theMedService;
	}
	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}

	/**
	 * Мед. Услуги
	 */
	private Long theMedService;
	/**
	 * Рабочая функция
	 */	
	@Persist 
	public Long getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(Long aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}
	/**
	 * Рабочая функция
	 */
	private Long theWorkFunction;
	/** Количество */
	@Comment("Количество")
	@Persist @Required
	public Integer getCountMedService() {
		return theCountMedService;
	}

	public void setCountMedService(Integer aCountMedService) {
		theCountMedService = aCountMedService;
	}

	/** Количество */
	private Integer theCountMedService;
	
	
	/** Гарантийное письмо */
	@Comment("Гарантийное письмо")
	@Persist
	public Long getGuarantee() {return theGuarantee;}
	public void setGuarantee(Long aGuarantee) {theGuarantee = aGuarantee;}

	/** Гарантийное письмо */
	private Long theGuarantee;
	
	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @Required @DateString @DoDateString 
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Основной СМО */
	@Comment("Основной СМО")
	public Long getMainParent() {return theMainParent;}
	public void setMainParent(Long aMainParent) {theMainParent = aMainParent;}

	/** Основной СМО */
	private Long theMainParent;
	/** Дата окончания */
	private String theDateTo;
	/** Дата начала */
	private String theDateFrom;
	/** Диагноз */
	private Long theIdc10;
	
	/** Проверино */
	@Comment("Проверино")
	@Persist
	public Boolean getIsCheck() {
		return theIsCheck;
	}

	public void setIsCheck(Boolean aIsCheck) {
		theIsCheck = aIsCheck;
	}

	/** Проверино */
	private Boolean theIsCheck;
	
	/** Удаленная запись */
	@Comment("Удаленная запись")
	@Persist
	public Boolean getIsDelete() {return theIsDelete;}
	public void setIsDelete(Boolean aIsDelete) {theIsDelete = aIsDelete;}

	/** Удаленная запись */
	private Boolean theIsDelete;
	/** Отредактированная запись */
	@Comment("Отредактированная запись")
	@Persist
	public Boolean getIsEdit() {return theIsEdit;}
	public void setIsEdit(Boolean aIsEdit) {theIsEdit = aIsEdit;}

	/** Отредактированная запись */
	private Boolean theIsEdit;
	
	/** Добавленная запись */
	@Comment("Добавленная запись")
	@Persist
	public Boolean getIsCreate() {return theIsCreate;}
	public void setIsCreate(Boolean aIsCreate) {theIsCreate = aIsCreate;}

	/** Добавленная запись */
	private Boolean theIsCreate;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {
		return thePatient;
	}

	public void setPatient(Long aPatient) {
		thePatient = aPatient;
	}

	/** Пациент */
	private Long thePatient;
	
	/** Фамилия */
	@Comment("Фамилия")
	@Persist
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

	/** Имя */
	@Comment("Имя")
	@Persist
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

	/** Отчество */
	@Comment("Отчество")
	@Persist
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Дата рождения */
	@Comment("Дата рождения")
	@Persist @DoDateString @DateString
	public String getBirthday() {return theBirthday;}
	public void setBirthday(String aBirthday) {theBirthday = aBirthday;}

	/** Дата рождения */
	private String theBirthday;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
	
	
	/** Летальный исход */
	@Comment("Летальный исход")
	@Persist
	public Boolean getIsDeath() {
		return theIsDeath;
	}

	public void setIsDeath(Boolean aIsDeath) {
		theIsDeath = aIsDeath;
	}

	/** Летальный исход */
	private Boolean theIsDeath;
	
	/** Полис */
	@Comment("Полис")
	@Persist
	public String getPolSeries() {return thePolSeries;}
	public void setPolSeries(String aPolSeries) {thePolSeries = aPolSeries;}

	/** Номер полиса */
	@Comment("Номер полиса")
	@Persist
	public String getPolNumber() {return thePolNumber;}
	public void setPolNumber(String aPolNumber) {thePolNumber = aPolNumber;}

	/** Номер полиса */
	private String thePolNumber;
	/** Полис */
	private String thePolSeries;
	/** Услуга внутр */
	@Comment("Услуга внутр")
	public Long getServiceIn() {
		return theServiceIn;
	}

	public void setServiceIn(Long aServiceIn) {
		theServiceIn = aServiceIn;
	}

	/** Услуга внутр */
	private Long theServiceIn;
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getDoctor() {
		return theDoctor;
	}

	public void setDoctor(Long aDoctor) {
		theDoctor = aDoctor;
	}

	/** Рабочая функция */
	private Long theDoctor;

	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public Long getDiagnosis() {
		return theDiagnosis;
	}

	public void setDiagnosis(Long aDiagnosis) {
		theDiagnosis = aDiagnosis;
	}

	/** Диагноз */
	private Long theDiagnosis;
}