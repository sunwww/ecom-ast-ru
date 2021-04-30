package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

	/**
	 * Шаблон резерва рабочего календаря
	 */
	@Comment("Шаблон резерва рабочего календаря")
@Entity
@Table(schema="SQLUser")
	@Getter
	@Setter
public class WorkCalendarReservePattern extends BaseEntity{
	/**
	 * Шаблон времени
	 */
	@Comment("Шаблон времени")
	@ManyToOne
	public WorkCalendarTimePattern getTimePattern() {
		return timePattern;
	}
	/**
	 * Шаблон времени
	 */
	private WorkCalendarTimePattern timePattern;
}
