package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocDayParity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkCalendarParity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

	/**
	 * Алгоритм шаблона рабочего календаря по дням недели
	 */
	@Comment("Алгоритм шаблона рабочего календаря по дням недели")
@Entity
	@Getter
	@Setter
public class WorkCalendarWeekDaysAlgorithm extends WorkCalendarAlgorithm{
		/** infoClass */
		@Comment("infoClass")
		@Transient
		public String getInfoClass() {
			return "Алгоритм по дням недели";
		}
		/**
	 * Понедельник
	 */
	@Comment("Понедельник")
	@OneToOne
	public WorkCalendarDayPattern getMonday() {
		return monday;
	}
	/**
	 * Понедельник
	 */
	private WorkCalendarDayPattern monday;
	/**
	 * Вторник
	 */
	@Comment("Вторник")
	@OneToOne
	public WorkCalendarDayPattern getTuesday() {
		return tuesday;
	}
	/**
	 * Вторник
	 */
	private WorkCalendarDayPattern tuesday;
	/**
	 * Среда
	 */
	@Comment("Среда")
	@OneToOne
	public WorkCalendarDayPattern getWednesday() {
		return wednesday;
	}
	/**
	 * Среда
	 */
	private WorkCalendarDayPattern wednesday;
	/**
	 * Четверг
	 */
	@Comment("Четверг")
	@OneToOne
	public WorkCalendarDayPattern getThursday() {
		return thursday;
	}
	/**
	 * Четверг
	 */
	private WorkCalendarDayPattern thursday;
	/**
	 * Пятница
	 */
	@Comment("Пятница")
	@OneToOne
	public WorkCalendarDayPattern getFriday() {
		return friday;
	}
	/**
	 * Пятница
	 */
	private WorkCalendarDayPattern friday;
	/**
	 * Суббота
	 */
	@Comment("Суббота")
	@OneToOne
	public WorkCalendarDayPattern getSaturday() {
		return saturday;
	}
	/**
	 * Суббота
	 */
	private WorkCalendarDayPattern saturday;
	/**
	 * Воскресенье
	 */
	@Comment("Воскресенье")
	@OneToOne
	public WorkCalendarDayPattern getSunday() {
		return sunday;
	}
	/**
	 * Воскресенье
	 */
	private WorkCalendarDayPattern sunday;
	/**
	 * Четный день
	 */
	@Comment("Четный день")
	@OneToOne
	public VocDayParity getParity() {
		return parity;
	}
	/**
	 * Четный день
	 */
	private VocDayParity parity;
	/**
	 * Тип четности
	 */
	@Comment("Тип четности")
	@OneToOne
	public VocWorkCalendarParity getCalendarParity() {
		return calendarParity;
	}
	/**
	 * Тип четности
	 */
	private VocWorkCalendarParity calendarParity;
}
