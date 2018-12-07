package ru.ecom.mis.ejb.domain.workcalendar;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

	/**
	 * Шаблон времени рабочего календаря
	 */
	@Comment("Шаблон времени рабочего календаря")
@Entity
@Table(schema="SQLUser")
public class WorkCalendarTimePattern extends BaseEntity{
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@ManyToOne
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
	@OneToMany(mappedBy="timePattern", cascade=CascadeType.ALL)
	public List<WorkCalendarReservePattern> getReservePatterns() {
		return theReservePatterns;
	}
	public void setReservePatterns(List<WorkCalendarReservePattern> aReservePatterns) {
		theReservePatterns = aReservePatterns;
	}
	/**
	 * Шаблоны резервов
	 */
	private List<WorkCalendarReservePattern> theReservePatterns;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@OneToOne
	public VocWorkBusy getWorkBusy() {
		return theWorkBusy;
	}
	public void setWorkBusy(VocWorkBusy aWorkBusy) {
		theWorkBusy = aWorkBusy;
	}
	/**
	 * Тип занятости
	 */
	private VocWorkBusy theWorkBusy;
	/** Резерв времени */
	@Comment("Резерв времени")
	@OneToOne
	public VocServiceReserveType getReserveType() {return theReserveType;}
	public void setReserveType(VocServiceReserveType aReserveType) {theReserveType = aReserveType;}

	/** Резерв времени */
	private VocServiceReserveType theReserveType;
}
