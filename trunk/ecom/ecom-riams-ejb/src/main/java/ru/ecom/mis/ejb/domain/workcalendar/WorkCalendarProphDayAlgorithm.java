package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekMonthOrder;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkWeek;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Алгоритм шаблога рабочего календаря по профилактическому дню
	 */
	@Comment("Алгоритм шаблога рабочего календаря по профилактическому дню")
@Entity
@Table(schema="SQLUser")
public class WorkCalendarProphDayAlgorithm extends WorkCalendarAlgorithm{
		/** infoClass */
		@Comment("infoClass")
		@Transient
		public String getInfoClass() {
			return "Алгоритм по проф. дню";
		}
		/**
	 * Порядок недели в месяце
	 */
	@Comment("Порядок недели в месяце")
	@OneToOne
	public VocWeekMonthOrder getMonthOrder() {
		return theMonthOrder;
	}
	public void setMonthOrder(VocWeekMonthOrder aMonthOrder) {
		theMonthOrder = aMonthOrder;
	}
	/**
	 * Порядок недели в месяце
	 */
	private VocWeekMonthOrder theMonthOrder;
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@OneToOne
	public WorkCalendarDayPattern getDayPattern() {
		return theDayPattern;
	}
	public void setDayPattern(WorkCalendarDayPattern aDayPattern) {
		theDayPattern = aDayPattern;
	}
	/**
	 * Шаблон дня
	 */
	private WorkCalendarDayPattern theDayPattern;
	/**
	 * Тип рабочей недели
	 */
	@Comment("Тип рабочей недели")
	@OneToOne
	public VocWorkWeek getWorkWeek() {
		return theWorkWeek;
	}
	public void setWorkWeek(VocWorkWeek aWorkWeek) {
		theWorkWeek = aWorkWeek;
	}
	/**
	 * Тип рабочей недели
	 */
	private VocWorkWeek theWorkWeek;

	/** День недели */
	@Comment("День недели")
	@OneToOne
	public VocWeekDay getWeekDay() {
		return theWeekDay;
	}

	public void setWeekDay(VocWeekDay aWeekDay) {
		theWeekDay = aWeekDay;
	}

	/** День недели */
	private VocWeekDay theWeekDay;
	
	/** День месяца */
	@Comment("День месяца")
	public Integer getMonthDay() {
		return theMonthDay;
	}

	public void setMonthDay(Integer aMonthDay) {
		theMonthDay = aMonthDay;
	}

	/** День месяца */
	private Integer theMonthDay;
	
}
