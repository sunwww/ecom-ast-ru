package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
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
	@Getter
	@Setter
public class WorkCalendarTimePattern extends BaseEntity{
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@ManyToOne
	public WorkCalendarDayPattern getDayPattern() {
		return dayPattern;
	}
	/**
	 * Шаблон дня
	 */
	private WorkCalendarDayPattern dayPattern;
	@OneToMany(mappedBy="timePattern", cascade=CascadeType.ALL)
	public List<WorkCalendarReservePattern> getReservePatterns() {
		return reservePatterns;
	}
	/**
	 * Шаблоны резервов
	 */
	private List<WorkCalendarReservePattern> reservePatterns;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@OneToOne
	public VocWorkBusy getWorkBusy() {
		return workBusy;
	}
	/**
	 * Тип занятости
	 */
	private VocWorkBusy workBusy;
	/** Резерв времени */
	@Comment("Резерв времени")
	@OneToOne
	public VocServiceReserveType getReserveType() {return reserveType;}

	/** Резерв времени */
	private VocServiceReserveType reserveType;
}
