package ru.ecom.mis.ejb.domain.contract;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Мед услуги по счету
 */
@Comment("Мед услуги по счету")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"account"})
	,@AIndex(unique= false, properties = {"medService"})
	
})
@EntityListeners(DeleteListener.class)
public class ContractAccountMedService extends BaseEntity{

	/** Признак удаленной записи */
	@Comment("Признак удаленной записи")
	public Boolean getIsDeleted() {return theIsDeleted;}
	public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
	/** Признак удаленной записи */
	private Boolean theIsDeleted ;

	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {
		return theAccount;
	}
	public void setAccount(ContractAccount aAccount) {
		theAccount = aAccount;
	}
	/**
	 * Договорной счет
	 */
	private ContractAccount theAccount;
	/**
	 * Мед Услуга
	 */
	@Comment("Мед Услуга")
	@OneToOne
	public PriceMedService getMedService() {
		return theMedService;
	}
	public void setMedService(PriceMedService aMedService) {
		theMedService = aMedService;
	}
	/**
	 * Мед Услуга
	 */
	private PriceMedService theMedService;

	/** Количество */
	@Comment("Количество")
	public Integer getCountMedService() {
		return theCountMedService;
	}

	public void setCountMedService(Integer aCountMedService) {
		theCountMedService = aCountMedService;
	}

	/** Количество */ 
	private Integer theCountMedService;
	
	public BigDecimal getCost() {
		return theCost;
	}
	public void setCost(BigDecimal aCost) {
		theCost = aCost;
	}
	/**
	 * Цена
	 */
	private BigDecimal theCost;
	
	
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {return theServedPerson;}
	public void setServedPerson(ServedPerson aServedPerson) {theServedPerson = aServedPerson;}
	/**
	 * Обслуживаемая персона
	 */
	private ServedPerson theServedPerson;
	
	/** Гарантийное письмо */
	@Comment("Гарантийное письмо")
	@OneToOne
	public ContractGuarantee getGuarantee() {return theGuarantee;}
	public void setGuarantee(ContractGuarantee aGuarantee) {theGuarantee = aGuarantee;}

	/** Гарантийное письмо */
	private ContractGuarantee theGuarantee;
	
	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** Дата начала */
	@Comment("Дата начала")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** СМО */
	@Comment("СМО")
	public Long getSmo() {return theSmo;}
	public void setSmo(Long aSmo) {theSmo = aSmo;}

	/** СМО */
	private Long theSmo;
	/** Дата окончания */
	private Date theDateTo;
	/** Дата начала */
	private Date theDateFrom;
	/** Диагноз */
	private VocIdc10 theIdc10;
	
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

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

	/** Дата рождения */
	private Date theBirthday;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
	
	/** Тип услуги */
	@Comment("Тип услуги")
	public String getTypeService() {return theTypeService;}
	public void setTypeService(String aTypeService) {theTypeService = aTypeService;}

	/** Тип услуги */
	private String theTypeService;
	
	/** Летальный исход */
	@Comment("Летальный исход")
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
	public String getPolSeries() {return thePolSeries;}
	public void setPolSeries(String aPolSeries) {thePolSeries = aPolSeries;}

	/** Номер полиса */
	@Comment("Номер полиса")
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
	public Long getDoctor() {
		return theDoctor;
	}

	public void setDoctor(Long aDoctor) {
		theDoctor = aDoctor;
	}

	/** Рабочая функция */
	private Long theDoctor;
	
	/** Основной СМО */
	@Comment("Основной СМО")
	public Long getMainParent() {
		return theMainParent;
	}

	public void setMainParent(Long aMainParent) {
		theMainParent = aMainParent;
	}

	/** Основной СМО */
	private Long theMainParent;
	
	/** Диагноз */
	@Comment("Диагноз")
	public Long getDiagnosis() {
		return theDiagnosis;
	}

	public void setDiagnosis(Long aDiagnosis) {
		theDiagnosis = aDiagnosis;
	}

	/** Диагноз */
	private Long theDiagnosis;
	
	/** Проверино */
	@Comment("Проверино")
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
	public Boolean getIsDelete() {return theIsDelete;}
	public void setIsDelete(Boolean aIsDelete) {theIsDelete = aIsDelete;}

	/** Удаленная запись */
	private Boolean theIsDelete;
	/** Отредактированная запись */
	@Comment("Отредактированная запись")
	public Boolean getIsEdit() {return theIsEdit;}
	public void setIsEdit(Boolean aIsEdit) {theIsEdit = aIsEdit;}

	/** Отредактированная запись */
	private Boolean theIsEdit;
	
	/** Добавленная запись */
	@Comment("Добавленная запись")
	public Boolean getIsCreate() {return theIsCreate;}
	public void setIsCreate(Boolean aIsCreate) {theIsCreate = aIsCreate;}

	/** Добавленная запись */
	private Boolean theIsCreate;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {	thePatient = aPatient;}

	/** Пациент */
	private Long thePatient;
}
