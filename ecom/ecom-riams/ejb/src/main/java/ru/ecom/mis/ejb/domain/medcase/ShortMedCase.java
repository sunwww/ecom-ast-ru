package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAmbulance;
import ru.ecom.mis.ejb.domain.medcase.voc.VocVisitOutcome;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.Medcard;
import ru.ecom.poly.ejb.domain.voc.VocDispanseryRegistration;
import ru.ecom.poly.ejb.domain.voc.VocReason;
import ru.ecom.poly.ejb.domain.voc.VocVisitResult;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Time;

/**
 * Короткий СМО
 * СМО, у которого дата начала = дата окончания.
 * @author oegorova
 *
 */

@Comment("Короткий СМО")
@Entity
@AIndexes({
	@AIndex(properties="datePlan", table="MedCase"),
    @AIndex(properties="workFunctionExecute", table="MedCase")
    }) 
public class ShortMedCase extends MedCase{
	
	/**
	 * Планируемая дата исполнения
	 */
	@Comment("Планируемая дата исполнения")
	@OneToOne
	public WorkCalendarDay getDatePlan() {
		return theDatePlan;
	}

	/**
	 * Планируемая дата исполнения
	 */
	public void setDatePlan(WorkCalendarDay aNewProperty) {
		theDatePlan = aNewProperty;
	}

	/**
	 * Планируемая дата исполнения
	 */
	private WorkCalendarDay theDatePlan;
	
	/**
	 * Планируемая рабочая функция исполнения
	 */
	@Comment("Планируемая рабочая функция исполнения")
	@OneToOne
	public WorkFunction getWorkFunctionPlan() {
		return theWorkFunctionPlan;
	}

	/**
	 * Планируемая рабочая функция исполнения
	 */
	public void setWorkFunctionPlan(WorkFunction aNewProperty) {
		theWorkFunctionPlan = aNewProperty;
	}

	/**
	 * Планируемая рабочая функция исполнения
	 */
	private WorkFunction theWorkFunctionPlan;

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@OneToOne
	public WorkFunction getWorkFunctionExecute() {
		return theWorkFunctionExecute;
	}

	/**
	 * Рабочая функция исполнения
	 */
	public void setWorkFunctionExecute(WorkFunction aNewProperty) {
		theWorkFunctionExecute = aNewProperty;
	}

	/**
	 * Рабочая функция исполнения
	 */
	private WorkFunction theWorkFunctionExecute;
	
	
	/**
	 * Планируемое время исполнения
	 */
	@Comment("Планируемое время исполнения")
	@OneToOne
	public WorkCalendarTime getTimePlan() {
		return theTimePlan;
	}

	/**
	 * Планируемое время исполнения
	 */
	public void setTimePlan(WorkCalendarTime aNewProperty) {
		theTimePlan = aNewProperty;
	}

	/**
	 * Планируемое время исполнения
	 */
	private WorkCalendarTime theTimePlan;
	
	/**
	 * Штамп времени исполнения
	 */
	@Comment("Время исполнения")
	public Time getTimeExecute() {
		return theTimeExecute;
	}

	/**
	 * Время исполнения
	 */
	public void setTimeExecute(Time aNewProperty) {
		theTimeExecute = aNewProperty;
	}
	/**
	 * Время исполнения
	 */
	private Time theTimeExecute;
	
	
	//Информация по рабочей функции исполнителя
	@Transient
	public String getWorkFunctionExecuteInfo() {
		return theWorkFunctionExecute!=null ? theWorkFunctionExecute.getWorkFunctionInfo():"" ;
	}
		

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@OneToOne
	public MisLpu getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(MisLpu aOrderLpu) {theOrderLpu = aOrderLpu;}
	
	/**Диагнозы */
	//@Comment("Диагнозы")
	//@OneToMany(mappedBy="medCase", cascade=CascadeType.ALL)
	//public List<Diagnosis> getDiagnosis() {return theDiagnosis;}
	//public void setDiagnosis(List<Diagnosis> aNewProperty) {theDiagnosis = aNewProperty;}
	

	/** Результат визита */
	@Comment("Результат визита")
	@OneToOne
	public VocVisitResult getVisitResult() {return theVisitResult;}
	public void setVisitResult(VocVisitResult aResult) {theVisitResult = aResult;}
	
	/** Цель визита */
	@Comment("Цель визита")
	@OneToOne
	public VocReason getVisitReason() {return theVisitReason;}
	public void setVisitReason(VocReason aReason) {theVisitReason = aReason;}
	
	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@OneToOne
	public VocWorkPlaceType getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(VocWorkPlaceType aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;}
	
	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@OneToOne
	public WorkFunction getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(WorkFunction aNewProperty) {
		theOrderWorkFunction = aNewProperty;}
	
	 /**Диспансерный учет  */
    @OneToOne
    public VocDispanseryRegistration getDispRegistration() { return theDispRegistration;}
    public void setDispRegistration(VocDispanseryRegistration aVocDispanseryRegistration) { theDispRegistration = aVocDispanseryRegistration; }
   
	
	/** Внешний направитель (ЛПУ) */
	private MisLpu theOrderLpu;
	///**Диагнозы*/
	//private List<Diagnosis> theDiagnosis;
	/** Результат визита */
	private VocVisitResult theVisitResult;
	/** Цель визита */
	private VocReason theVisitReason;
	/** Тип рабочего места обслуживания */
	private VocWorkPlaceType theWorkPlaceType;
	/** Рабочая функция направителя */
	private WorkFunction theOrderWorkFunction;
	/**Диспансерный учет  */
	private VocDispanseryRegistration theDispRegistration;

	// [start] Вычисляемые свойства
	
	
	/** Следующая дата приема */
	@Comment("Следующая дата приема")
	public Date getNextVisitDate() {return theNextVisitDate;}
	public void setNextVisitDate(Date aNextVisitDate) {theNextVisitDate = aNextVisitDate;}

	/** Следующая дата приема */
	private Date theNextVisitDate;

	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@OneToOne
	public VocAmbulance getAmbulance() {return theAmbulance;}
	public void setAmbulance(VocAmbulance aAmbulance) {theAmbulance = aAmbulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@OneToOne
	public VocVisitOutcome getVisitOutcome() {return theVisitOutcome;}
	public void setVisitOutcome(VocVisitOutcome aVisitOutcome) {theVisitOutcome = aVisitOutcome;}

	/** Исход визита */
	private VocVisitOutcome theVisitOutcome;
	/** Бригада скорой помощи */
	private VocAmbulance theAmbulance;
	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	public Integer getPrivilegeRecipeAmount() {return thePrivilegeRecipeAmount;}
	public void setPrivilegeRecipeAmount(Integer aPrivilegeRecipeAmount) {thePrivilegeRecipeAmount = aPrivilegeRecipeAmount;}
    /** Количество выписанных льготных рецептов */
	private Integer thePrivilegeRecipeAmount;

	/**  Медицинская карта */
    @OneToOne
    public Medcard getMedcard() {return theMedcard;}
    public void setMedcard(Medcard aMedcard) {theMedcard = aMedcard;}
    /** Медицинская карта */
    private Medcard theMedcard;
    
    /** Разговор с родственником */
	@Comment("Разговор с родственником")
	public Boolean getIsTalk() {return theIsTalk;}
	public void setIsTalk(Boolean aIsTalk) {theIsTalk = aIsTalk;}

	/** Направление на госпитализацию */
	@Comment("Направление на госпитализацию")
	public Boolean getIsDirectHospital() {return theIsDirectHospital;}
	public void setIsDirectHospital(Boolean aIsDirectHospital) {theIsDirectHospital = aIsDirectHospital;}

	/** Направление на госпитализацию */
	private Boolean theIsDirectHospital;
	/** Разговор с родственником */
	private Boolean theIsTalk;

}
