package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeInterval;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarTimeInterval.class)
@Comment("Шаблон времени")
@WebTrail(comment = "Шаблон времени", nameProperties= "id"
		, list="entityParentList-cal_timeInterval.do"
		, view="entityParentView-cal_timeInterval.do")
@Parent(property="dayPattern", parentForm=WorkCalendarDayPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time")
@Setter
public class WorkCalendarTimeIntervalForm extends WorkCalendarTimePatternForm{
	/**
	 * Начиная с времени
	 */
	@Comment("Начиная с времени")
	@Persist @Required
	@TimeString @DoTimeString 
	public String getTimeFrom() {
		return timeFrom;
	}
	/**
	 * Начиная с времени
	 */
	private String timeFrom;
	/**
	 * Заканчивая временем
	 */
	@Comment("Заканчивая временем")
	@Persist @Required
	@TimeString @DoTimeString
	public String getTimeTo() {
		return timeTo;
	}
	/**
	 * Заканчивая временем
	 */
	private String timeTo;
	/**
	 * Среднее время на визит
	 */
	@Comment("Среднее время на визит")
	@Persist 
	public Integer getVisitTime() {
		return visitTime;
	}

	/** Кол-во визитов */
	@Comment("Кол-во визитов")
	@Persist
	public Integer getCountVisits() {return countVisits;}

	/** Кол-во визитов */
	private Integer countVisits;
	/**
	 * Среднее время на визит
	 */
	private Integer visitTime;
}
