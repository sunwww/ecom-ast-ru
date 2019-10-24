package ru.ecom.mis.ejb.domain.workcalendar;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;

	/**
	 * Шаблон времен рабочего календаря
	 */
	@Comment("Шаблон времен рабочего календаря")
@Entity
@Table(schema="SQLUser")
public class WorkCalendarTimeInterval extends WorkCalendarTimePattern {

	/** Начиная с времени */
	@Comment("Начиная с времени")
	public Time getTimeFrom() {return theTimeFrom;}
	public void setTimeFrom(Time aTimeFrom) {theTimeFrom = aTimeFrom;}
	private Time theTimeFrom;

	/** Заканчивая временем */
	@Comment("Заканчивая временем")
	public Time getTimeTo() {return theTimeTo;}
	public void setTimeTo(Time aTimeTo) {theTimeTo = aTimeTo;}
	private Time theTimeTo;

	/** Среднее время на визит */
	@Comment("Среднее время на визит")
	public Integer getVisitTime() {return theVisitTime;}
	public void setVisitTime(Integer aVisitTime) {theVisitTime = aVisitTime;}
	private Integer theVisitTime;

	/** Кол-во визитов */
	@Comment("Кол-во визитов")
	public Integer getCountVisits() {return theCountVisits;}
	public void setCountVisits(Integer aCountVisits) {theCountVisits = aCountVisits;}
	private Integer theCountVisits;
}