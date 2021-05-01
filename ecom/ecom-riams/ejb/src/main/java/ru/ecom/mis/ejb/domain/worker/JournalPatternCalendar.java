package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Журнал шаблона календаря
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class JournalPatternCalendar extends BaseEntity {

	/** Шаблон календаря */
	@Comment("Шаблон календаря")
	@OneToOne
	public WorkCalendarPattern getPattern() {return pattern;}

	/** Информация */
	@Comment("Информация")
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		if (pattern!=null) ret.append(pattern.getInfo()) ;
		return ret.toString();
	}

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@ManyToOne
	public WorkCalendar getWorkCalendar() {return workCalendar;}

	/** Рабочий календарь */
	private WorkCalendar workCalendar;
	/** Не действует */
	private Boolean noActive;
	/** Дата окончания действия */
	private Date dateTo;
	/** Дата начала действия */
	private Date dateFrom;
	/** Шаблон календаря */
	private WorkCalendarPattern pattern;
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
}
