package ru.ecom.mis.ejb.domain.workcalendar;

import ru.ecom.mis.ejb.domain.workcalendar.voc.VocDayParity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkCalendarParity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkWeek;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

	/**
	 * Алгоритм шаблона рабочего календаря по неделям
	 */
	@Comment("Алгоритм шаблона рабочего календаря по неделям")
@Entity
public class WorkCalendarWeekAlgorithm extends WorkCalendarAlgorithm{
		/** infoClass */
		@Comment("infoClass")
		@Transient
		public String getInfoClass() {
			return "Алгоритм по неделям";
		}
		/**
	 * Рабочая неделя
	 */
	@Comment("Рабочая неделя")
	@OneToOne
	public VocWorkWeek getWorkWeek() {
		return theWorkWeek;
	}
	public void setWorkWeek(VocWorkWeek aWorkWeek) {
		theWorkWeek = aWorkWeek;
	}
	/**
	 * Рабочая неделя
	 */
	private VocWorkWeek theWorkWeek;
	/**
	 * Четная
	 */
	@Comment("Четная")
	@OneToOne
	public VocDayParity getParity() {
		return theParity;
	}
	public void setParity(VocDayParity aParity) {
		theParity = aParity;
	}
	/**
	 * Четная
	 */
	private VocDayParity theParity;
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
