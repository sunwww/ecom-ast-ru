package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarWeekAlgorithm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarWeekAlgorithm.class)
@Comment("Алгоритм по неделям")
@WebTrail(comment = "Алгоритм по неделям", nameProperties= "id", list="entityParentList-cal_weekAlgorithm.do", view="entityParentView-cal_weekAlgorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
public class WorkCalendarWeekAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Рабочая неделя
	 */
	@Comment("Рабочая неделя")
	@Persist @Required
	public Long getWorkWeek() {
		return theWorkWeek;
	}
	public void setWorkWeek(Long aWorkWeek) {
		theWorkWeek = aWorkWeek;
	}
	/**
	 * Рабочая неделя
	 */
	private Long theWorkWeek;
	/**
	 * Четная
	 */
	@Comment("Четная")
	@Persist 
	public Long getParity() {
		return theParity;
	}
	public void setParity(Long aParity) {
		theParity = aParity;
	}
	/**
	 * Четная
	 */
	private Long theParity;
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@Persist
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
	 * Тип четности
	 */
	@Comment("Тип четности")
	@Persist
	public Long getCalendarParity() {
		return theCalendarParity;
	}
	public void setCalendarParity(Long aCalendarParity) {
		theCalendarParity = aCalendarParity;
	}
	/**
	 * Тип четности
	 */
	private Long theCalendarParity;
}
