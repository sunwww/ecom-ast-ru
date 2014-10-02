package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeInterval;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
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
public class WorkCalendarTimeIntervalForm extends WorkCalendarTimePatternForm{
	/**
	 * Начиная с времени
	 */
	@Comment("Начиная с времени")
	@Persist @Required
	@TimeString @DoTimeString 
	public String getTimeFrom() {
		return theTimeFrom;
	}
	public void setTimeFrom(String aTimeFrom) {
		theTimeFrom = aTimeFrom;
	}
	/**
	 * Начиная с времени
	 */
	private String theTimeFrom;
	/**
	 * Заканчивая временем
	 */
	@Comment("Заканчивая временем")
	@Persist @Required
	@TimeString @DoTimeString
	public String getTimeTo() {
		return theTimeTo;
	}
	public void setTimeTo(String aTimeTo) {
		theTimeTo = aTimeTo;
	}
	/**
	 * Заканчивая временем
	 */
	private String theTimeTo;
	/**
	 * Среднее время на визит
	 */
	@Comment("Среднее время на визит")
	@Persist 
	public Integer getVisitTime() {
		return theVisitTime;
	}
	public void setVisitTime(Integer aVisitTime) {
		theVisitTime = aVisitTime;
	}
	
	/** Кол-во визитов */
	@Comment("Кол-во визитов")
	@Persist
	public Integer getCountVisits() {return theCountVisits;}
	public void setCountVisits(Integer aCountVisits) {theCountVisits = aCountVisits;}

	/** Кол-во визитов */
	private Integer theCountVisits;
	/**
	 * Среднее время на визит
	 */
	private Integer theVisitTime;
}
