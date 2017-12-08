package ru.ecom.mis.ejb.domain.workcalendar;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Дни рабочего календаря
 * @author azviagin
 *
 */
@Comment("Дни рабочего календаря")
@Entity
@AIndexes({
	@AIndex(unique = false, properties = {"calendarDate"})
	,@AIndex(unique = false, properties = {"workCalendar"})
	//,@AIndex(unique = false, properties = {"calendarDate","workFunction"})
})
@Table(schema="SQLUser")
public class WorkCalendarDay extends BaseEntity{
	/** Удаленная запись */
	@Comment("Удаленная запись")
	public Boolean getIsDeleted() {return theIsDeleted;}
	public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
	/** Удаленная запись */
	private Boolean theIsDeleted ;

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@ManyToOne
	public WorkCalendar getWorkCalendar() {
		return theWorkCalendar;
	}

	public void setWorkCalendar(WorkCalendar aWorkCalendar) {
		theWorkCalendar = aWorkCalendar;
	}

	/** Рабочий календарь */
	private WorkCalendar theWorkCalendar;
	
	/** Выходной */
	@Comment("Выходной")
	public Boolean getHoliday() {
		return theHoliday;
	}

	public void setHoliday(Boolean aHoliday) {
		theHoliday = aHoliday;
	}

	/** Выходной */
	private Boolean theHoliday;
	
	/** Календарная дата */
	@Comment("Календарная дата")
	public Date getCalendarDate() {
		return theCalendarDate;
	}

	public void setCalendarDate(Date aCalendarDate) {
		theCalendarDate = aCalendarDate;
	}

	/** Календарная дата */
	private Date theCalendarDate;
	
	/** День недели */
	@Comment("День недели")
	@Transient
	@OneToOne
	public VocWeekDay getWeekDay() {
		return theWeekDay;
	}

	public void setWeekDay(VocWeekDay aWeekDay) {
		theWeekDay = aWeekDay;
	}

	/** День недели */
	private VocWeekDay theWeekDay;
	
	/** Номер недели в месяце */
	@Comment("Номер недели в месяце")
	@Transient
	public Integer getWeekNumberInMonth() {
		return theWeekNumberInMonth;
	}

	public void setWeekNumberInMonth(Integer aWeekNumberInMonth) {
		theWeekNumberInMonth = aWeekNumberInMonth;
	}

	/** Номер недели в месяце */
	private Integer theWeekNumberInMonth;
	
	/** Номер недели в году */
	@Comment("Номер недели в году")
	@Transient
	public Integer getWeekNumberInYear() {
		return theWeekNumberInYear;
	}

	public void setWeekNumberInYear(Integer aWeekNumberInYear) {
		theWeekNumberInYear = aWeekNumberInYear;
	}

	/** Номер недели в году */
	private Integer theWeekNumberInYear;
	
	/** Вместо дня */
	@Comment("Вместо дня")
	@OneToOne
	public WorkCalendarDay getInsteadOfDay() {
		return theInsteadOfDay;
	}

	public void setInsteadOfDay(WorkCalendarDay aInsteadOfDay) {
		theInsteadOfDay = aInsteadOfDay;
	}

	/** Вместо дня */
	private WorkCalendarDay theInsteadOfDay;
	
	/** Рабочие времена */
	@Comment("Рабочие времена")
	@OneToMany(mappedBy="workCalendarDay", cascade=CascadeType.ALL)
	public List<WorkCalendarTime> getWorkCalendarTimes() {
		return theWorkCalendarTimes;
	}

	public void setWorkCalendarTimes(List<WorkCalendarTime> aWorkCalendarTimes) {
		theWorkCalendarTimes = aWorkCalendarTimes;
	}

	/** Рабочие времена */
	private List<WorkCalendarTime> theWorkCalendarTimes;
	
	@Transient @OneToOne
	public WorkFunction getWorkFunction() {
		return theWorkCalendar!=null? theWorkCalendar.getWorkFunction():null ;
	}
	
	

}
