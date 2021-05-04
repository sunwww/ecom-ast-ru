package ru.ecom.mis.ejb.domain.vaccination;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Vaccination extends BaseEntity {

	/** Вакцина */
	@Comment("Вакцина")
	@OneToOne
	public Vaccine getVaccine() {return vaccine;}
	/** Вакцина */
	private Vaccine vaccine;

	/** Вакцинный материал */
	@Comment("Вакцинный материал")
	@OneToOne
	public VocVaccinationMaterial getMaterial() {return material;}

	/**
	 * Вакцинный материал
	 */
	private VocVaccinationMaterial material;

	/**
	 * Исполнитель
	 */
	@Comment("Исполнитель")
	@OneToOne
	public WorkFunction getExecuteWorker() {
		return executeWorker;
	}

	/**
	 * Исполнитель
	 */
	private WorkFunction executeWorker;


	/**
	 * Дата исполнения
	 */
	private Date executeDate;

	/**
	 * Время исполнения
	 */
	private Time executeTime;


	/**
	 * Доза
	 */
	private String dose;


	/**
	 * Серия
	 */
	private String series;

	/**
	 * Метод вакцинации
	 */
	@Comment("Метод вакцинации")
	@OneToOne
	public VocVaccinationMethod getMethod() {
		return method;
	}

	/**
	 * Метод вакцинации
	 */
	private VocVaccinationMethod method;


	/**
	 * Контрольный номер
	 */
	private String controlNumber;


	/**
	 * Дата, на которую планировалась вакцинация
	 */
	private Date planDate;

	/**
	 * Дата следующей вакцинации
	 */
	private Date nextDate;

	/**
	 * Фаза вакцинации
	 */
	@Comment("Фаза вакцинации")
	@OneToOne
	public VocVaccinationPhase getPhase() {
		return phase;
	}

	/**
	 * Фаза вакцинации
	 */
	private VocVaccinationPhase phase;

	/**
	 * 
	 */
	@Comment("Тип причины вакцинации")
	@OneToOne
	public VocVaccinationReasonType getReasonType() {
		return reasonType;
	}

	/**
	 * Тип причины вакцинации
	 */
	private VocVaccinationReasonType reasonType;

	/**
	 * Комментарии
	 */
	private String comments;

	/**
	 * Медотвод
	 */
	@Comment("Медотвод")
	@OneToOne
	public VaccinationEstop getEstop() {
		return estop;
	}

	/**
	 * Медотвод
	 */
	private VaccinationEstop estop;

	/**
	 * Оценка
	 */
	@Comment("Оценка")
	@OneToOne
	public VaccinationAssesment getAssesment() {
		return assesment;
	}


	/**
	 * Оценка
	 */
	private VaccinationAssesment assesment;

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Пациент */
	private Patient patient;
	
	/**
	 * СМО
	 */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {
		return medCase;
	}

	/**
	 * СМО
	 */
	private MedCase medCase;

	/**
	 * Дата окончания годности
	 */
	private Date expirationDate;

	/**
	 * Разрешил
	 */
	@Comment("Разрешил")
	@OneToOne
	public WorkFunction getPermitWorker() {
		return permitWorker;
	}

	/**
	 * Разрешил
	 */
	private WorkFunction permitWorker;
	
	/**
	 * Недействительность
	 */
	private Boolean noActuality;
	
	/** Название вакцины */
	@Comment("Название вакцины")
	@Transient
	public String getVaccineInfo() {
		return vaccine!=null?vaccine.getName():"";	}
	public void setVaccineInfo(String aVaccineInfo) {
		
	}
	
	/** Разрешил (инфо) */
	@Comment("Разрешил (инфо)")
	@Transient
	public String getPermitWorkerInfo() {
		return permitWorker!=null?permitWorker.getWorkFunctionInfo():"";
	}

	public void setPermitWorkerInfo(String aPermitWorkerInfo) {
		
	}

	/** Исполнитель (инфо) */
	@Comment("Исполнитель (инфо)")
	@Transient
	public String getExecuteWorkerInfo() {
		return executeWorker!=null?executeWorker.getWorkFunctionInfo():"";
	}

	public void setExecuteWorkerInfo(String aExecuteWorkerInfo) {
		
	}

	/** Фаза вакцинации (текст) */
	@Comment("Фаза вакцинации (текст)")
	@Transient
	public String getPhaseText() {
		return phase!=null?phase.getName():"";
	}

	public void setPhaseText(String aPhaseText) {
		
	}

	/** Метод вакцинации (текст) */
	@Comment("Метод вакцинации (текст)")
	@Transient
	public String getMethodText() {
		return method!=null?method.getName():"";
	}

	public void setMethodText(String aMethodText) {
		
	}

	/** Вакцинный материал (текст) */
	@Comment("Вакцинный материал (текст)")
	@Transient
	public String getMaterialText() {
		return material!=null?material.getName():"";
	}

	public void setMaterialText(String aMaterialText) {
		
	}

	/** Реакция вакцинации */
	@Comment("Реакция вакцинации")
	@OneToOne
	public VocVaccinationReaction getVaccinationReaction() {return vaccinationReaction;}

	/** Реакция вакцинации */
	private VocVaccinationReaction vaccinationReaction;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
}
