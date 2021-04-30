package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekMonthOrder;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkWeek;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

	/**
	 * Алгоритм шаблога рабочего календаря по профилактическому дню
	 */
	@Comment("Алгоритм шаблога рабочего календаря по профилактическому дню")
@Entity
	@Getter
	@Setter
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
		return monthOrder;
	}
	/**
	 * Порядок недели в месяце
	 */
	private VocWeekMonthOrder monthOrder;
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@OneToOne
	public WorkCalendarDayPattern getDayPattern() {
		return dayPattern;
	}
	/**
	 * Шаблон дня
	 */
	private WorkCalendarDayPattern dayPattern;
	/**
	 * Тип рабочей недели
	 */
	@Comment("Тип рабочей недели")
	@OneToOne
	public VocWorkWeek getWorkWeek() {
		return workWeek;
	}
	/**
	 * Тип рабочей недели
	 */
	private VocWorkWeek workWeek;

	/** День недели */
	@Comment("День недели")
	@OneToOne
	public VocWeekDay getWeekDay() {
		return weekDay;
	}

	/** День недели */
	private VocWeekDay weekDay;
	
	/** День месяца */
	private Integer monthDay;
	
}
