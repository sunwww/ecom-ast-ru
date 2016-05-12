package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class JournalPatternCalendar extends BaseEntity {

	/** Шаблон календаря */
	@Comment("Шаблон календаря")
	@OneToOne
	public WorkCalendarPattern getPattern() {return thePattern;}
	public void setPattern(WorkCalendarPattern aPattern) {thePattern = aPattern;}

	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	
	/** Не действует */
	@Comment("Не действует")
	public Boolean getNoActive() {return theNoActive;}
	public void setNoActive(Boolean aNoActive) {theNoActive = aNoActive;}
	
	/** Информация */
	@Comment("Информация")
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		if (thePattern!=null) ret.append(thePattern.getInfo()) ;
		return ret.toString();
	}

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@ManyToOne
	public WorkCalendar getWorkCalendar() {return theWorkCalendar;}
	public void setWorkCalendar(WorkCalendar aWorkCalendar) {theWorkCalendar = aWorkCalendar;}

	/** Рабочий календарь */
	private WorkCalendar theWorkCalendar;
	/** Не действует */
	private Boolean theNoActive;
	/** Дата окончания действия */
	private Date theDateTo;
	/** Дата начала действия */
	private Date theDateFrom;
	/** Шаблон календаря */
	private WorkCalendarPattern thePattern;
//	/** Рабочая функция */
//	private WorkFunction theWorkFunction;
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
}
