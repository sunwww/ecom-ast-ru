
package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAmbulance;
import ru.ecom.mis.ejb.domain.medcase.voc.VocVisitOutcome;
import ru.ecom.mis.ejb.domain.medcase.voc.VocWorkMedservice;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocDispanseryRegistration;
import ru.ecom.poly.ejb.domain.voc.VocReason;
import ru.ecom.poly.ejb.domain.voc.VocVisitResult;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;


/**
 * Визит
 * @author oegorova
 *
 */
@Comment("Визит")
@Entity
@Table(schema="SQLUser")
public class Visit extends ShortMedCase{
	
	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	public Integer getPrivilegeRecipeAmount() {return thePrivilegeRecipeAmount;}
	public void setPrivilegeRecipeAmount(Integer aPrivilegeRecipeAmount) {thePrivilegeRecipeAmount = aPrivilegeRecipeAmount;}
	
	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@OneToOne
	public MisLpu getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(MisLpu aOrderLpu) {theOrderLpu = aOrderLpu;}
	
	/**Диагнозы */
	@Comment("Диагнозы")
	@OneToMany(mappedBy="medCase", cascade=CascadeType.ALL)
	public List<Diagnosis> getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(List<Diagnosis> aNewProperty) {theDiagnosis = aNewProperty;}
	
	

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
   
	
    /** Количество выписанных льготных рецептов */
	private Integer thePrivilegeRecipeAmount;
	/** Внешний направитель (ЛПУ) */
	private MisLpu theOrderLpu;
	/**Диагнозы*/
	private List<Diagnosis> theDiagnosis;
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
	
	@Transient @Comment("Планируемая дата исполнения (текст)")
	public String getDatePlanText() {
		return getDatePlan()!=null && getDatePlan().getCalendarDate()!=null
			? DateFormat.formatToDate(getDatePlan().getCalendarDate()) 
			: "" ;		
	}

	@Transient @Comment("Планируемое время исполнения (текст)")
	public String getTimePlanText() {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm"); 
		return getTimePlan()!=null && getTimePlan().getTimeFrom()!=null
			? f.format(getTimePlan().getTimeFrom()) 
			: "" ;		
	}
	
	@Transient @Comment("Цель визита (текст)")
	public String getVisitReasonText() {
		return theVisitReason!=null ? theVisitReason.getName() : null ;
	}
	
	@Transient @Comment("Дата выполнения")
	public String getDateExecuteText() {
		return getDateExecute()!=null ? DateFormat.formatToDate(getDateExecute()) : "";
	}
	
	@Transient @Comment("Рабочая функция исполнения (текст)")
	public String getWorkFunctionExecuteText() {
		return getWorkFunctionExecute()!=null ? getWorkFunctionExecute().getWorkFunctionInfo() : "";
		
	}
	
	@Transient @Comment("Рабочая функция направителя (текст)")
	public String getOrderWorkFunctionText() {
		return getOrderWorkFunction()!=null ? getOrderWorkFunction().getWorkFunctionInfo() : "";
		
	} 
	// [end]
	
	
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Визит ").append(getId()) ;
		return ret.toString() ;
	}
	
	/** Следующая дата приема */
	@Comment("Следующая дата приема")
	public Date getNextVisitDate() {
		return theNextVisitDate;
	}

	public void setNextVisitDate(Date aNextVisitDate) {
		theNextVisitDate = aNextVisitDate;
	}

	/** Следующая дата приема */
	private Date theNextVisitDate;
	
	/** Тип мед. обслуживания */
	@Comment("Тип мед. обслуживания")
	@OneToOne
	public VocWorkMedservice getWorkMedservice() {return theWorkMedservice;}
	public void setWorkMedservice(VocWorkMedservice aWorkMedservice) {theWorkMedservice = aWorkMedservice;}

	/** Тип мед. обслуживания */
	private VocWorkMedservice theWorkMedservice;
	
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


}
