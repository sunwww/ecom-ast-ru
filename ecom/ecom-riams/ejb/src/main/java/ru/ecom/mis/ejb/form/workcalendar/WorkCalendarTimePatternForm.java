package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimePattern;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@Setter
public class WorkCalendarTimePatternForm extends IdEntityForm{
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@Persist @Required
	public Long getDayPattern() {
		return dayPattern;
	}
	/**
	 * Шаблон дня
	 */
	private Long dayPattern;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist 
	public Long getWorkBusy() {
		return workBusy;
	}
	/**
	 * Тип занятости
	 */
	private Long workBusy;
	
	/** Список времен */
	@Comment("Список времен")
	public String getListTimes() {return listTimes;}

	/** Список времен */
	private String listTimes;
	/** Резерв времени */
	@Comment("Резерв времени")
	@Persist
	public Long getReserveType() {return reserveType;}

	/** Резерв времени */
	private Long reserveType;
}
