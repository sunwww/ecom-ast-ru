package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
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
@Setter
public class WorkCalendarWeekAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Рабочая неделя
	 */
	@Comment("Рабочая неделя")
	@Persist @Required
	public Long getWorkWeek() {
		return workWeek;
	}
	/**
	 * Рабочая неделя
	 */
	private Long workWeek;
	/**
	 * Четная
	 */
	@Comment("Четная")
	@Persist 
	public Long getParity() {
		return parity;
	}
	/**
	 * Четная
	 */
	private Long parity;
	/**
	 * Шаблон дня
	 */
	@Comment("Шаблон дня")
	@Persist
	public Long getDayPattern() {
		return dayPattern;
	}
	/**
	 * Шаблон дня
	 */
	private Long dayPattern;
	/**
	 * Тип четности
	 */
	@Comment("Тип четности")
	@Persist
	public Long getCalendarParity() {
		return calendarParity;
	}
	/**
	 * Тип четности
	 */
	private Long calendarParity;
}
