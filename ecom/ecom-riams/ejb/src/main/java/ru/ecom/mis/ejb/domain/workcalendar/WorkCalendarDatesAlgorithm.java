package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;

	/**
	 * Алгоритм шаблона рабочего календаря по датам
	 */
	@Comment("Алгоритм шаблона рабочего календаря по датам")
@Entity
	@Getter
	@Setter
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
	private Date dateFrom;
	/**
	 * Заканчивая датой
	 */
	private Date dateTo;
	/**
	 * Шаблон дня рабочего календаря
	 */
	@Comment("Шаблон дня рабочего календаря")
	@OneToOne
	public WorkCalendarDayPattern getDayPattern() {
		return dayPattern;
	}
	/**
	 * Шаблон дня рабочего календаря
	 */
	private WorkCalendarDayPattern dayPattern;
}
