package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Короткий СМО
 * СМО, у которого дата начала = дата окончания.
 * @author oegorova
 *
 */

@Comment("Короткий СМО")
@Entity
@Table(schema="SQLUser")
@AIndexes({
    @AIndex(properties="datePlan", table="MedCase")
    }) 
abstract public class ShortMedCase extends MedCase{
	
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

	/** Дата выполнения */
	@Comment("Дата выполнения")
	public Date getDateExecute() {
		return theDateExecute;
	}

	public void setDateExecute(Date aDateExecute) {
		theDateExecute = aDateExecute;
	}

	/** Дата выполнения */
	private Date theDateExecute;
	/**
	 * Время исполнения
	 */
	private Time theTimeExecute;
	
	
	//Информация по рабочей функции исполнителя
	@Transient
	public String getWorkFunctionExecuteInfo() {
		return theWorkFunctionExecute!=null ? theWorkFunctionExecute.getWorkFunctionInfo():"" ;
	}
		

}
