package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
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
@Setter
public class ContractAccountMedServiceJuridicalForm extends IdEntityForm{

	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@Persist
	public Long getServedPerson() {return servedPerson;}
	/**
	 * Обслуживаемая персона
	 */
	private Long servedPerson;
	
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@Persist
	public Long getAccount() {return account;}
	/**
	 * Договорной счет
	 */
	private Long account;
	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	@Persist @Required
	public Long getMedService() {
		return medService;
	}

	/**
	 * Мед. Услуги
	 */
	private Long medService;
	/**
	 * Рабочая функция
	 */	
	@Persist 
	public Long getWorkFunction() {
		return workFunction;
	}
	/**
	 * Рабочая функция
	 */
	private Long workFunction;
	/** Количество */
	@Comment("Количество")
	@Persist @Required
	public Integer getCountMedService() {
		return countMedService;
	}


	/** Количество */
	private Integer countMedService;
	
	
	/** Гарантийное письмо */
	@Comment("Гарантийное письмо")
	@Persist
	public Long getGuarantee() {return guarantee;}

	/** Гарантийное письмо */
	private Long guarantee;
	
	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public Long getIdc10() {return idc10;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @Required @DateString @DoDateString 
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateTo() {return dateTo;}

	/** Основной СМО */
	@Comment("Основной СМО")
	public Long getMainParent() {return mainParent;}

	/** Основной СМО */
	private Long mainParent;
	/** Дата окончания */
	private String dateTo;
	/** Дата начала */
	private String dateFrom;
	/** Диагноз */
	private Long idc10;
	
	/** Проверино */
	@Comment("Проверино")
	@Persist
	public Boolean getIsCheck() {
		return isCheck;
	}


	/** Проверино */
	private Boolean isCheck;
	
	/** Удаленная запись */
	@Comment("Удаленная запись")
	@Persist
	public Boolean getIsDelete() {return isDelete;}

	/** Удаленная запись */
	private Boolean isDelete;
	/** Отредактированная запись */
	@Comment("Отредактированная запись")
	@Persist
	public Boolean getIsEdit() {return isEdit;}

	/** Отредактированная запись */
	private Boolean isEdit;
	
	/** Добавленная запись */
	@Comment("Добавленная запись")
	@Persist
	public Boolean getIsCreate() {return isCreate;}

	/** Добавленная запись */
	private Boolean isCreate;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {
		return patient;
	}


	/** Пациент */
	private Long patient;
	
	/** Фамилия */
	@Comment("Фамилия")
	@Persist
	public String getLastname() {return lastname;}

	/** Имя */
	@Comment("Имя")
	@Persist
	public String getFirstname() {return firstname;}

	/** Отчество */
	@Comment("Отчество")
	@Persist
	public String getMiddlename() {return middlename;}

	/** Дата рождения */
	@Comment("Дата рождения")
	@Persist @DoDateString @DateString
	public String getBirthday() {return birthday;}

	/** Дата рождения */
	private String birthday;
	/** Отчество */
	private String middlename;
	/** Имя */
	private String firstname;
	/** Фамилия */
	private String lastname;
	
	
	/** Летальный исход */
	@Comment("Летальный исход")
	@Persist
	public Boolean getIsDeath() {
		return isDeath;
	}


	/** Летальный исход */
	private Boolean isDeath;
	
	/** Полис */
	@Comment("Полис")
	@Persist
	public String getPolSeries() {return polSeries;}

	/** Номер полиса */
	@Comment("Номер полиса")
	@Persist
	public String getPolNumber() {return polNumber;}

	/** Номер полиса */
	private String polNumber;
	/** Полис */
	private String polSeries;
	/** Услуга внутр */
	@Comment("Услуга внутр")
	public Long getServiceIn() {
		return serviceIn;
	}


	/** Услуга внутр */
	private Long serviceIn;
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getDoctor() {
		return doctor;
	}


	/** Рабочая функция */
	private Long doctor;

	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public Long getDiagnosis() {
		return diagnosis;
	}

	/** Диагноз */
	private Long diagnosis;
}