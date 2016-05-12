package ru.ecom.mis.ejb.domain.workcalendar;

import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Шаблон времени рабочего календаря
	 */
	@Comment("Шаблон времени рабочего календаря")
@Entity
@Table(schema="SQLUser")
public class WorkCalendarTimeExample extends WorkCalendarTimePattern{
	/**
	 * Время
	 */
	@Comment("Время")
	
	public Time getCalendarTime() {
		return theCalendarTime;
	}
	public void setCalendarTime(Time aCalendarTime) {
		theCalendarTime = aCalendarTime;
	}
	/**
	 * Время
	 */
	private Time theCalendarTime;
}
