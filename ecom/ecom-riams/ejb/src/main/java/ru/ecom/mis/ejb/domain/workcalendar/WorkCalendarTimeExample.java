package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Time;

	/**
	 * Шаблон времени рабочего календаря
	 */
	@Comment("Шаблон времени рабочего календаря")
@Entity
	@Getter
	@Setter
public class WorkCalendarTimeExample extends WorkCalendarTimePattern{
	/**
	 * Время
	 */
	private Time calendarTime;
}
