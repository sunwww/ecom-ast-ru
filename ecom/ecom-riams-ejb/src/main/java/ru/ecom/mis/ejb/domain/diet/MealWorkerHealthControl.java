package ru.ecom.mis.ejb.domain.diet;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.worker.Worker;

/**
 * Контроль здоровья работающих с пищей (УФ 2-лп)
 * @author oegorova
 *
 */

@Comment("Контроль здоровья работающих с пищей (УФ 2-лп)")
@Entity
@Table(schema="SQLUser")
public class MealWorkerHealthControl extends BaseEntity{
	
	/** Диета */
	@Comment("Диета")
	public Diet getDiet() {
		return theDiet;
	}
	public void setDiet(Diet aDiet) {
		theDiet = aDiet;
	}
	/** Диета */
	private Diet theDiet;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private MisLpu theLpu;
	
	
	/** Информация о диетологе */
	@Comment("Информация о диетологе")
	@Transient
	public String getDietitianInfo() {
		return theDietitian!=null ? theDietitian.getWorkerInfo() : "";
	}

	public void setDietitianInfo(String aDietitianInfo) {
		
	}
	
	/** Диетолог */
	@Comment("Диетолог")
	@OneToOne
	public Worker getDietitian() {
		return theDietitian;
	}

	public void setDietitian(Worker aDietitian) {
		theDietitian = aDietitian;
	}

	/** Диетолог */
	private Worker theDietitian;
	
	/** Диагноз нетрудоспособности по уходу */
	@Comment("Диагноз нетрудоспособности по уходу")
	public String getNursingDisabilityDiagnosis() {
		return theNursingDisabilityDiagnosis;
	}

	public void setNursingDisabilityDiagnosis(String aNursingDisabilityDiagnosis) {
		theNursingDisabilityDiagnosis = aNursingDisabilityDiagnosis;
	}

	/** Диагноз нетрудоспособности по уходу */
	private String theNursingDisabilityDiagnosis;
	
	/** Отсутствие гнойных заболеваний */
	@Comment("Отсутствие гнойных заболеваний")
	public Boolean getPurulentDiseaseAbsence() {
		return thePurulentDiseaseAbsence;
	}

	public void setPurulentDiseaseAbsence(Boolean aPurulentDiseaseAbsence) {
		thePurulentDiseaseAbsence = aPurulentDiseaseAbsence;
	}

	/** Отсутствие гнойных заболеваний */
	private Boolean thePurulentDiseaseAbsence;
	
	/** Отсутствие острых кишечных заболеваний */
	@Comment("Отсутствие острых кишечных заболеваний")
	public Boolean getAcuteIntestinalDiseaseAbsence() {
		return theAcuteIntestinalDiseaseAbsence;
	}

	public void setAcuteIntestinalDiseaseAbsence(Boolean aAcuteIntestinalDiseaseAbsence) {
		theAcuteIntestinalDiseaseAbsence = aAcuteIntestinalDiseaseAbsence;
	}

	/** Отсутствие острых кишечных заболеваний */
	private Boolean theAcuteIntestinalDiseaseAbsence;
	
	
	/** Информация о контроллируемом сотруднике */
	@Comment("Информация о контроллируемом сотруднике")
	@Transient
	public String getControllingWorkerInfo() {
		return  theControllingWorker!=null ? theControllingWorker.getWorkerInfo() : "";
	}

	public void setControllingWorkerInfo(String aControllingWorkerInfo) {
		
	}
	
	/** Контролируемый сотрудник */
	@Comment("Контролируемый сотрудник")
	@OneToOne
	public Worker getControllingWorker() {
		return theControllingWorker;
	}

	public void setControllingWorker(Worker aControllingWorker) {
		theControllingWorker = aControllingWorker;
	}

	/** Контролируемый сотрудник */
	private Worker theControllingWorker;
	
	
	/** Дата контроля */
	@Comment("Дата контроля")
	public Date getControlDate() {
		return theControlDate;
	}

	public void setControlDate(Date aControlDate) {
		theControlDate = aControlDate;
	}

	/** Дата контроля */
	private Date theControlDate;

}
