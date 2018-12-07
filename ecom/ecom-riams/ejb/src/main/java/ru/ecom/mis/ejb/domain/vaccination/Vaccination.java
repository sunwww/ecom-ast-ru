package ru.ecom.mis.ejb.domain.vaccination;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.vaccination.voc.*;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Time;

/**
 * Вакцинация
 */
@Entity
@Comment("Вакцинация")
@Table(schema="SQLUser")
public class Vaccination extends BaseEntity {

	/** Вакцина */
	@Comment("Вакцина")
	@OneToOne
	public Vaccine getVaccine() {return theVaccine;}
	public void setVaccine(Vaccine aNewProperty) {theVaccine = aNewProperty;}

	/** Вакцина */
	private Vaccine theVaccine;

	/** Вакцинный материал */
	@Comment("Вакцинный материал")
	@OneToOne
	public VocVaccinationMaterial getMaterial() {return theMaterial;}

	/**
	 * Вакцинный материал
	 */
	public void setMaterial(VocVaccinationMaterial aNewProperty) {
		theMaterial = aNewProperty;
	}
	private VocVaccinationMaterial theMaterial;

	/**
	 * Исполнитель
	 */
	@Comment("Исполнитель")
	@OneToOne
	public WorkFunction getExecuteWorker() {
		return theExecuteWorker;
	}

	/**
	 * Исполнитель
	 */
	public void setExecuteWorker(WorkFunction aNewProperty) {
		theExecuteWorker = aNewProperty;
	}

	/**
	 * Исполнитель
	 */
	private WorkFunction theExecuteWorker;

	/**
	 * Дата исполнения
	 */
	@Comment("Дата исполнения")
	public Date getExecuteDate() {
		return theExecuteDate;
	}

	/**
	 * Дата исполнения
	 */
	public void setExecuteDate(Date aNewProperty) {
		theExecuteDate = aNewProperty;
	}

	/**
	 * Дата исполнения
	 */
	private Date theExecuteDate;

	/**
	 * Время исполнения
	 */
	@Comment("Время исполнения")
	public Time getExecuteTime() {
		return theExecuteTime;
	}

	/**
	 * Время исполнения
	 */
	public void setExecuteTime(Time aNewProperty) {
		theExecuteTime = aNewProperty;
	}

	/**
	 * Время исполнения
	 */
	private Time theExecuteTime;

	/**
	 * Доза
	 */
	@Comment("Доза")
	public String getDose() {
		return theDose;
	}

	/**
	 * Доза
	 */
	public void setDose(String aNewProperty) {
		theDose = aNewProperty;
	}

	/**
	 * Доза
	 */
	private String theDose;

	/**
	 * Серия
	 */
	@Comment("Серия")
	public String getSeries() {
		return theSeries;
	}

	/**
	 * Серия
	 */
	public void setSeries(String aNewProperty) {
		theSeries = aNewProperty;
	}

	/**
	 * Серия
	 */
	private String theSeries;

	/**
	 * Метод вакцинации
	 */
	@Comment("Метод вакцинации")
	@OneToOne
	public VocVaccinationMethod getMethod() {
		return theMethod;
	}

	/**
	 * Метод вакцинации
	 */
	public void setMethod(VocVaccinationMethod aNewProperty) {
		theMethod = aNewProperty;
	}

	/**
	 * Метод вакцинации
	 */
	private VocVaccinationMethod theMethod;

	/**
	 * Контрольный номер
	 */
	@Comment("Контрольный номер")
	public String getControlNumber() {
		return theControlNumber;
	}

	/**
	 * Контрольный номер
	 */
	public void setControlNumber(String aNewProperty) {
		theControlNumber = aNewProperty;
	}

	/**
	 * Контрольный номер
	 */
	private String theControlNumber;

	

	/**
	 * Дата, на которую планировалась вакцинация
	 */
	@Comment("Дата, на которую планировалась вакцинация")
	public Date getPlanDate() {
		return thePlanDate;
	}

	/**
	 * Дата, на которую планировалась вакцинация
	 */
	public void setPlanDate(Date aNewProperty) {
		thePlanDate = aNewProperty;
	}

	/**
	 * Дата, на которую планировалась вакцинация
	 */
	private Date thePlanDate;

	/**
	 * Дата следующей вакцинации
	 */
	@Comment("Дата следующей вакцинации")
	public Date getNextDate() {
		return theNextDate;
	}

	/**
	 * Дата следующей вакцинации
	 */
	public void setNextDate(Date aNewProperty) {
		theNextDate = aNewProperty;
	}

	/**
	 * Дата следующей вакцинации
	 */
	private Date theNextDate;

	/**
	 * Фаза вакцинации
	 */
	@Comment("Фаза вакцинации")
	@OneToOne
	public VocVaccinationPhase getPhase() {
		return thePhase;
	}

	/**
	 * Фаза вакцинации
	 */
	public void setPhase(VocVaccinationPhase aNewProperty) {
		thePhase = aNewProperty;
	}

	/**
	 * Фаза вакцинации
	 */
	private VocVaccinationPhase thePhase;

	/**
	 * 
	 */
	@Comment("Тип причины вакцинации")
	@OneToOne
	public VocVaccinationReasonType getReasonType() {
		return theReasonType;
	}

	/**
	 * Тип причины вакцинации
	 */
	public void setReasonType(VocVaccinationReasonType aNewProperty) {
		theReasonType = aNewProperty;
	}

	/**
	 * Тип причины вакцинации
	 */
	private VocVaccinationReasonType theReasonType;

	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	public String getComments() {
		return theComments;
	}

	/**
	 * Комментарии
	 */
	public void setComments(String a_Property) {
		theComments = a_Property;
	}

	/**
	 * Комментарии
	 */
	private String theComments;

	/**
	 * Медотвод
	 */
	@Comment("Медотвод")
	@OneToOne
	public VaccinationEstop getEstop() {
		return theEstop;
	}

	/**
	 * Медотвод
	 */
	public void setEstop(VaccinationEstop a_Property) {
		theEstop = a_Property;
	}

	/**
	 * Медотвод
	 */
	private VaccinationEstop theEstop;

	/**
	 * Оценка
	 */
	@Comment("Оценка")
	@OneToOne
	public VaccinationAssesment getAssesment() {
		return theAssesment;
	}

	/**
	 * Оценка
	 */
	public void setAssesment(VaccinationAssesment a_Property) {
		theAssesment = a_Property;
	}

	/**
	 * Оценка
	 */
	private VaccinationAssesment theAssesment;

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Patient thePatient;
	
	/**
	 * СМО
	 */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	/**
	 * СМО
	 */
	public void setMedCase(MedCase a_Property) {
		theMedCase = a_Property;
	}

	/**
	 * СМО
	 */
	private MedCase theMedCase;

	/**
	 * Дата окончания годности
	 */
	@Comment("Дата окончания годности")
	public Date getExpirationDate() {
		return theExpirationDate;
	}

	/**
	 * Дата окончания годности
	 */
	public void setExpirationDate(Date a_Property) {
		theExpirationDate = a_Property;
	}

	/**
	 * Дата окончания годности
	 */
	private Date theExpirationDate;

	/**
	 * Разрешил
	 */
	@Comment("Разрешил")
	@OneToOne
	public WorkFunction getPermitWorker() {
		return thePermitWorker;
	}

	/**
	 * Разрешил
	 */
	public void setPermitWorker(WorkFunction a_Property) {
		thePermitWorker = a_Property;
	}

	/**
	 * Разрешил
	 */
	private WorkFunction thePermitWorker;
	
	/**
	 * Недействительность
	 */
	@Comment("Недействительность")
	public Boolean getNoActuality() {
		return theNoActuality;
	}

	/**
	 * Недействительность
	 */
	public void setNoActuality(Boolean a_Property) {
		theNoActuality = a_Property;
	}

	/**
	 * Недействительность
	 */
	private Boolean theNoActuality;
	
	/** Название вакцины */
	@Comment("Название вакцины")
	@Transient
	public String getVaccineInfo() {
		return theVaccine!=null?theVaccine.getName():"";	}
	public void setVaccineInfo(String aVaccineInfo) {
		
	}
	
	/** Разрешил (инфо) */
	@Comment("Разрешил (инфо)")
	@Transient
	public String getPermitWorkerInfo() {
		return thePermitWorker!=null?thePermitWorker.getWorkFunctionInfo():"";
	}

	public void setPermitWorkerInfo(String aPermitWorkerInfo) {
		
	}

	/** Исполнитель (инфо) */
	@Comment("Исполнитель (инфо)")
	@Transient
	public String getExecuteWorkerInfo() {
		return theExecuteWorker!=null?theExecuteWorker.getWorkFunctionInfo():"";
	}

	public void setExecuteWorkerInfo(String aExecuteWorkerInfo) {
		
	}

	/** Фаза вакцинации (текст) */
	@Comment("Фаза вакцинации (текст)")
	@Transient
	public String getPhaseText() {
		return thePhase!=null?thePhase.getName():"";
	}

	public void setPhaseText(String aPhaseText) {
		
	}

	/** Метод вакцинации (текст) */
	@Comment("Метод вакцинации (текст)")
	@Transient
	public String getMethodText() {
		return theMethod!=null?theMethod.getName():"";
	}

	public void setMethodText(String aMethodText) {
		
	}

	/** Вакцинный материал (текст) */
	@Comment("Вакцинный материал (текст)")
	@Transient
	public String getMaterialText() {
		return theMaterial!=null?theMaterial.getName():"";
	}

	public void setMaterialText(String aMaterialText) {
		
	}
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Реакция вакцинации */
	@Comment("Реакция вакцинации")
	@OneToOne
	public VocVaccinationReaction getVaccinationReaction() {return theVaccinationReaction;}
	public void setVaccinationReaction(VocVaccinationReaction aVaccinationReaction) {theVaccinationReaction = aVaccinationReaction;}

	/** Реакция вакцинации */
	private VocVaccinationReaction theVaccinationReaction;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;
}
