package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
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
	@Getter
	@Setter
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
		return workWeek;
	}
	/**
	 * Рабочая неделя
	 */
	private VocWorkWeek workWeek;
	/**
	 * Четная
	 */
	@Comment("Четная")
	@OneToOne
	public VocDayParity getParity() {
		return parity;
	}
	/**
	 * Четная
	 */
	private VocDayParity parity;
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
