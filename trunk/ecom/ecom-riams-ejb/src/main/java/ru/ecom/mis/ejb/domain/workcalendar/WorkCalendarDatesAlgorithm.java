package ru.ecom.mis.ejb.domain.workcalendar;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Алгоритм шаблона рабочего календаря по датам
	 */
	@Comment("Алгоритм шаблона рабочего календаря по датам")
@Entity
@Table(schema="SQLUser")
public class WorkCalendarDatesAlgorithm extends WorkCalendarAlgorithm{

		/** infoClass */
		@Comment("infoClass")
		@Transient
		public String getInfoClass() {
			return "Алгоритм по датам";
		}
		/**
	 * Начиная с даты
	 */
	@Comment("Начиная с даты")
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Начиная с даты
	 */
	private Date theDateFrom;
	/**
	 * Заканчивая датой
	 */
	@Comment("Заканчивая датой")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Заканчивая датой
	 */
	private Date theDateTo;
	/**
	 * Шаблон дня рабочего календаря
	 */
	@Comment("Шаблон дня рабочего календаря")
	@OneToOne
	public WorkCalendarDayPattern getDayPattern() {
		return theDayPattern;
	}
	public void setDayPattern(WorkCalendarDayPattern aDayPattern) {
		theDayPattern = aDayPattern;
	}
	/**
	 * Шаблон дня рабочего календаря
	 */
	private WorkCalendarDayPattern theDayPattern;
}
