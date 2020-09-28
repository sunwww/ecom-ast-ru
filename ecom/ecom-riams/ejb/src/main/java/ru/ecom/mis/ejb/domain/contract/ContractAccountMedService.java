package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Мед услуги по счету
 */
@Comment("Мед услуги по счету")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"account"})
	,@AIndex(properties = {"medService"})
	
})
@EntityListeners(DeleteListener.class)
public class ContractAccountMedService extends BaseEntity{

	/** Рабочая функция */
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}
	private WorkFunction theWorkFunction;

	/** Признак удаленной записи */
	@Comment("Признак удаленной записи")
	public Boolean getIsDeleted() {return theIsDeleted;}
	public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
	private Boolean theIsDeleted ;

	/** Договорной счет */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {
		return theAccount;
	}
	public void setAccount(ContractAccount aAccount) {
		theAccount = aAccount;
	}
	private ContractAccount theAccount;

	/** Мед Услуга */
	@Comment("Мед Услуга")
	@OneToOne
	public PriceMedService getMedService() {
		return theMedService;
	}
	public void setMedService(PriceMedService aMedService) {
		theMedService = aMedService;
	}
	private PriceMedService theMedService;

	/** Количество */
	@Comment("Количество")
	public Integer getCountMedService() {
		return theCountMedService;
	}
	public void setCountMedService(Integer aCountMedService) {
		theCountMedService = aCountMedService;
	}
	private Integer theCountMedService;
	
	public BigDecimal getCost() {
		return theCost;
	}
	public void setCost(BigDecimal aCost) {
		theCost = aCost;
	}
	private BigDecimal theCost;

	/** Обслуживаемая персона */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {return theServedPerson;}
	public void setServedPerson(ServedPerson aServedPerson) {theServedPerson = aServedPerson;}
	private ServedPerson theServedPerson;
	
	/** Гарантийное письмо */
	@Comment("Гарантийное письмо")
	@OneToOne
	public ContractGuarantee getGuarantee() {return theGuarantee;}
	public void setGuarantee(ContractGuarantee aGuarantee) {theGuarantee = aGuarantee;}
	private ContractGuarantee theGuarantee;
	
	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}
	private VocIdc10 theIdc10;

	/** Дата начала */
	@Comment("Дата начала")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	private Date theDateFrom;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
	private Date theDateTo;

	/** СМО */
	@Comment("СМО")
	public Long getSmo() {return theSmo;}
	public void setSmo(Long aSmo) {theSmo = aSmo;}
	private Long theSmo;

	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}
	private String theLastname;

	/** Имя */
	@Comment("Имя")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}
	private String theFirstname;

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}
	private String theMiddlename;

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}
	private Date theBirthday;

	/** Тип услуги */
	@Comment("Тип услуги")
	public String getTypeService() {return theTypeService;}
	public void setTypeService(String aTypeService) {theTypeService = aTypeService;}
	private String theTypeService;

	/** ИД мед. случая */
	@Comment("ИД мед. случая")
	public Long getIdService() {return theIdService;}
	public void setIdService(Long aIdService) {theIdService = aIdService;}
	/** ИД мед. случая */
	private Long theIdService ;
	
	/** Летальный исход */
	@Comment("Летальный исход")
	public Boolean getIsDeath() {
		return theIsDeath;
	}
	public void setIsDeath(Boolean aIsDeath) {
		theIsDeath = aIsDeath;
	}
	private Boolean theIsDeath;
	
	/** Полис */
	@Comment("Полис")
	public String getPolSeries() {return thePolSeries;}
	public void setPolSeries(String aPolSeries) {thePolSeries = aPolSeries;}
	private String thePolSeries;

	/** Номер полиса */
	@Comment("Номер полиса")
	public String getPolNumber() {return thePolNumber;}
	public void setPolNumber(String aPolNumber) {thePolNumber = aPolNumber;}
	private String thePolNumber;

	/** Услуга внутр */
	@Comment("Услуга внутр")
	public Long getServiceIn() {
		return theServiceIn;
	}
	public void setServiceIn(Long aServiceIn) {
		theServiceIn = aServiceIn;
	}
	private Long theServiceIn;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getDoctor() {
		return theDoctor;
	}
	public void setDoctor(Long aDoctor) {
		theDoctor = aDoctor;
	}
	private Long theDoctor;
	
	/** Основной СМО */
	@Comment("Основной СМО")
	public Long getMainParent() {
		return theMainParent;
	}
	public void setMainParent(Long aMainParent) {
		theMainParent = aMainParent;
	}
	private Long theMainParent;
	
	/** Диагноз */
	@Comment("Диагноз")
	public Long getDiagnosis() {
		return theDiagnosis;
	}
	public void setDiagnosis(Long aDiagnosis) {
		theDiagnosis = aDiagnosis;
	}
	private Long theDiagnosis;
	
	/** Проверино */
	@Comment("Проверино")
	public Boolean getIsCheck() {
		return theIsCheck;
	}
	public void setIsCheck(Boolean aIsCheck) {
		theIsCheck = aIsCheck;
	}
	private Boolean theIsCheck;
	
	/** Удаленная запись */
	@Comment("Удаленная запись")
	public Boolean getIsDelete() {return theIsDelete;}
	public void setIsDelete(Boolean aIsDelete) {theIsDelete = aIsDelete;}
	private Boolean theIsDelete;

	/** Отредактированная запись */
	@Comment("Отредактированная запись")
	public Boolean getIsEdit() {return theIsEdit;}
	public void setIsEdit(Boolean aIsEdit) {theIsEdit = aIsEdit;}
	private Boolean theIsEdit;
	
	/** Добавленная запись */
	@Comment("Добавленная запись")
	public Boolean getIsCreate() {return theIsCreate;}
	public void setIsCreate(Boolean aIsCreate) {theIsCreate = aIsCreate;}
	private Boolean theIsCreate;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {	thePatient = aPatient;}
	private Long thePatient;

	/** Комлексная услуга, частью которой является текущий объект */
	@Comment("Комлексная услуга, частью которой является текущий объект")
	public Long getFromComplexMedServiceId() {return theFromComplexMedServiceId;}
	public void setFromComplexMedServiceId(Long aFromComplexMedServiceId) {theFromComplexMedServiceId = aFromComplexMedServiceId;}
	private Long theFromComplexMedServiceId;
}
