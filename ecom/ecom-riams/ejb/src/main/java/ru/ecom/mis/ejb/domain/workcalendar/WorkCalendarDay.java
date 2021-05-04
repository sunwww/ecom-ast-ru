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

import lombok.Getter;
import lombok.Setter;
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
	@AIndex(properties = {"calendarDate"})
	,@AIndex(properties = {"workCalendar"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class WorkCalendarDay extends BaseEntity{
	/** Удаленная запись */
	private Boolean isDeleted ;

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@ManyToOne
	public WorkCalendar getWorkCalendar() {
		return workCalendar;
	}

	/** Рабочий календарь */
	private WorkCalendar workCalendar;
	
	/** Выходной */
	private Boolean holiday;
	
	/** Календарная дата */
	private Date calendarDate;
	
	/** День недели */
	@Comment("День недели")
	@Transient
	@OneToOne
	public VocWeekDay getWeekDay() {
		return weekDay;
	}

	/** День недели */
	private VocWeekDay weekDay;
	
	/** Номер недели в месяце */
	@Comment("Номер недели в месяце")
	@Transient
	public Integer getWeekNumberInMonth() {
		return weekNumberInMonth;
	}

	public void setWeekNumberInMonth(Integer aWeekNumberInMonth) {
		weekNumberInMonth = aWeekNumberInMonth;
	}

	/** Номер недели в месяце */
	private Integer weekNumberInMonth;
	
	/** Номер недели в году */
	@Comment("Номер недели в году")
	@Transient
	public Integer getWeekNumberInYear() {
		return weekNumberInYear;
	}

	public void setWeekNumberInYear(Integer aWeekNumberInYear) {
		weekNumberInYear = aWeekNumberInYear;
	}

	/** Номер недели в году */
	private Integer weekNumberInYear;
	
	/** Вместо дня */
	@Comment("Вместо дня")
	@OneToOne
	public WorkCalendarDay getInsteadOfDay() {
		return insteadOfDay;
	}

	/** Вместо дня */
	private WorkCalendarDay insteadOfDay;
	
	/** Рабочие времена */
	@Comment("Рабочие времена")
	@OneToMany(mappedBy="workCalendarDay", cascade=CascadeType.ALL)
	public List<WorkCalendarTime> getWorkCalendarTimes() {
		return workCalendarTimes;
	}

	/** Рабочие времена */
	private List<WorkCalendarTime> workCalendarTimes;
	
	@Transient
	public WorkFunction getWorkFunction() {
		return workCalendar!=null? workCalendar.getWorkFunction():null ;
	}
	
	

}
