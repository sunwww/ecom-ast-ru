package ru.ecom.mis.ejb.domain.workcalendar;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Time;

	/**
	 * Шаблон времени рабочего календаря
	 */
	@Comment("Шаблон времени рабочего календаря")
@Entity
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
