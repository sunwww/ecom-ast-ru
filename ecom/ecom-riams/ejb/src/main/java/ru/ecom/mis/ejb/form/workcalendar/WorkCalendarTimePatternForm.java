package ru.ecom.mis.ejb.form.workcalendar;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimePattern;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarTimePattern.class)
@Comment("Шаблон времени")
@WebTrail(comment = "Шаблон времени", nameProperties= "id"
		, list="entityParentList-cal_timePattern.do", view="entityParentView-cal_timePattern.do")
@Parent(property="dayPattern", parentForm=WorkCalendarDayPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time")
@Subclasses(value = { WorkCalendarTimeExampleForm.class, WorkCalendarTimeIntervalForm.class })
public class WorkCalendarTimePatternForm extends IdEntityForm{
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@Persist @Required
	public Long getDayPattern() {
		return theDayPattern;
	}
	public void setDayPattern(Long aDayPattern) {
		theDayPattern = aDayPattern;
	}
	/**
	 * Шаблон дня
	 */
	private Long theDayPattern;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist 
	public Long getWorkBusy() {
		return theWorkBusy;
	}
	public void setWorkBusy(Long aWorkBusy) {
		theWorkBusy = aWorkBusy;
	}
	/**
	 * Тип занятости
	 */
	private Long theWorkBusy;
	
	/** Список времен */
	@Comment("Список времен")
	public String getListTimes() {return theListTimes;}
	public void setListTimes(String aListTimes) {theListTimes = aListTimes;}

	/** Список времен */
	private String theListTimes;
	/** Резерв времени */
	@Comment("Резерв времени")
	@Persist
	public Long getReserveType() {return theReserveType;}
	public void setReserveType(Long aReserveType) {theReserveType = aReserveType;}

	/** Резерв времени */
	private Long theReserveType;
}
