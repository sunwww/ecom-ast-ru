package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ShortMedCase extends MedCase{
	
	/**
	 * Планируемая дата исполнения
	 */
	@Comment("Планируемая дата исполнения")
	@OneToOne
	public WorkCalendarDay getDatePlan() {
		return datePlan;
	}


	/**
	 * Планируемая дата исполнения
	 */
	private WorkCalendarDay datePlan;
	
	/**
	 * Планируемая рабочая функция исполнения
	 */
	@Comment("Планируемая рабочая функция исполнения")
	@OneToOne
	public WorkFunction getWorkFunctionPlan() {
		return workFunctionPlan;
	}


	/**
	 * Планируемая рабочая функция исполнения
	 */
	private WorkFunction workFunctionPlan;

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@OneToOne
	public WorkFunction getWorkFunctionExecute() {
		return workFunctionExecute;
	}

	/**
	 * Рабочая функция исполнения
	 */
	private WorkFunction workFunctionExecute;
	
	
	/**
	 * Планируемое время исполнения
	 */
	@Comment("Планируемое время исполнения")
	@OneToOne
	public WorkCalendarTime getTimePlan() {
		return timePlan;
	}

	/**
	 * Планируемое время исполнения
	 */
	private WorkCalendarTime timePlan;
	
	/**
	 * Время исполнения
	 */
	private Time timeExecute;
	
	
	//Информация по рабочей функции исполнителя
	@Transient
	public String getWorkFunctionExecuteInfo() {
		return workFunctionExecute!=null ? workFunctionExecute.getWorkFunctionInfo():"" ;
	}
		

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@OneToOne
	public MisLpu getOrderLpu() {return orderLpu;}

	/** Результат визита */
	@Comment("Результат визита")
	@OneToOne
	public VocVisitResult getVisitResult() {return visitResult;}

	/** Цель визита */
	@Comment("Цель визита")
	@OneToOne
	public VocReason getVisitReason() {return visitReason;}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@OneToOne
	public VocWorkPlaceType getWorkPlaceType() {return workPlaceType;}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@OneToOne
	public WorkFunction getOrderWorkFunction() {return orderWorkFunction;}

	 /**Диспансерный учет  */
    @OneToOne
    public VocDispanseryRegistration getDispRegistration() { return dispRegistration;}

	/** Внешний направитель (ЛПУ) */
	private MisLpu orderLpu;
	/** Результат визита */
	private VocVisitResult visitResult;
	/** Цель визита */
	private VocReason visitReason;
	/** Тип рабочего места обслуживания */
	private VocWorkPlaceType workPlaceType;
	/** Рабочая функция направителя */
	private WorkFunction orderWorkFunction;
	/**Диспансерный учет  */
	private VocDispanseryRegistration dispRegistration;


	/** Следующая дата приема */
	private Date nextVisitDate;

	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@OneToOne
	public VocAmbulance getAmbulance() {return ambulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@OneToOne
	public VocVisitOutcome getVisitOutcome() {return visitOutcome;}

	/** Исход визита */
	private VocVisitOutcome visitOutcome;
	/** Бригада скорой помощи */
	private VocAmbulance ambulance;
    /** Количество выписанных льготных рецептов */
	private Integer privilegeRecipeAmount;

	/**  Медицинская карта */
    @OneToOne
    public Medcard getMedcard() {return medcard;}
    /** Медицинская карта */
    private Medcard medcard;
    
	/** Направление на госпитализацию */
	private Boolean isDirectHospital;
	/** Разговор с родственником */
	private Boolean isTalk;

}
