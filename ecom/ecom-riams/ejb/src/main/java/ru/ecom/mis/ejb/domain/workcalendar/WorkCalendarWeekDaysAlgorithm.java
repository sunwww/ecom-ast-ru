package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.workcalendar.voc.VocDayParity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkCalendarParity;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Алгоритм шаблона рабочего календаря по дням недели
	 */
	@Comment("Алгоритм шаблона рабочего календаря по дням недели")
@Entity
@Table(schema="SQLUser")
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
		return theMonday;
	}
	public void setMonday(WorkCalendarDayPattern aMonday) {
		theMonday = aMonday;
	}
	/**
	 * Понедельник
	 */
	private WorkCalendarDayPattern theMonday;
	/**
	 * Вторник
	 */
	@Comment("Вторник")
	@OneToOne
	public WorkCalendarDayPattern getTuesday() {
		return theTuesday;
	}
	public void setTuesday(WorkCalendarDayPattern aTuesday) {
		theTuesday = aTuesday;
	}
	/**
	 * Вторник
	 */
	private WorkCalendarDayPattern theTuesday;
	/**
	 * Среда
	 */
	@Comment("Среда")
	@OneToOne
	public WorkCalendarDayPattern getWednesday() {
		return theWednesday;
	}
	public void setWednesday(WorkCalendarDayPattern aWednesday) {
		theWednesday = aWednesday;
	}
	/**
	 * Среда
	 */
	private WorkCalendarDayPattern theWednesday;
	/**
	 * Четверг
	 */
	@Comment("Четверг")
	@OneToOne
	public WorkCalendarDayPattern getThursday() {
		return theThursday;
	}
	public void setThursday(WorkCalendarDayPattern aThursday) {
		theThursday = aThursday;
	}
	/**
	 * Четверг
	 */
	private WorkCalendarDayPattern theThursday;
	/**
	 * Пятница
	 */
	@Comment("Пятница")
	@OneToOne
	public WorkCalendarDayPattern getFriday() {
		return theFriday;
	}
	public void setFriday(WorkCalendarDayPattern aFriday) {
		theFriday = aFriday;
	}
	/**
	 * Пятница
	 */
	private WorkCalendarDayPattern theFriday;
	/**
	 * Суббота
	 */
	@Comment("Суббота")
	@OneToOne
	public WorkCalendarDayPattern getSaturday() {
		return theSaturday;
	}
	public void setSaturday(WorkCalendarDayPattern aSaturday) {
		theSaturday = aSaturday;
	}
	/**
	 * Суббота
	 */
	private WorkCalendarDayPattern theSaturday;
	/**
	 * Воскресенье
	 */
	@Comment("Воскресенье")
	@OneToOne
	public WorkCalendarDayPattern getSunday() {
		return theSunday;
	}
	public void setSunday(WorkCalendarDayPattern aSunday) {
		theSunday = aSunday;
	}
	/**
	 * Воскресенье
	 */
	private WorkCalendarDayPattern theSunday;
	/**
	 * Четный день
	 */
	@Comment("Четный день")
	@OneToOne
	public VocDayParity getParity() {
		return theParity;
	}
	public void setParity(VocDayParity aParity) {
		theParity = aParity;
	}
	/**
	 * Четный день
	 */
	private VocDayParity theParity;
	/**
	 * Тип четности
	 */
	@Comment("Тип четности")
	@OneToOne
	public VocWorkCalendarParity getCalendarParity() {
		return theCalendarParity;
	}
	public void setCalendarParity(VocWorkCalendarParity aCalendarParity) {
		theCalendarParity = aCalendarParity;
	}
	/**
	 * Тип четности
	 */
	private VocWorkCalendarParity theCalendarParity;
}
