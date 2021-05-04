package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarWeekDaysAlgorithm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarWeekDaysAlgorithm.class)
@Comment("Алгоритм по дням недели")
@WebTrail(comment = "Алгоритм по дням недели", nameProperties= "id", list="entityParentList-cal_weekDaysAlgorithm.do", view="entityParentView-cal_weekDaysAlgorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
@Setter
public class WorkCalendarWeekDaysAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Понедельник
	 */
	@Comment("Понедельник")
	@Persist
	public Long getMonday() {
		return monday;
	}
	/**
	 * Понедельник
	 */
	private Long monday;
	/**
	 * Вторник
	 */
	@Comment("Вторник")
	@Persist
	public Long getTuesday() {
		return tuesday;
	}
	/**
	 * Вторник
	 */
	private Long tuesday;
	/**
	 * Среда
	 */
	@Comment("Среда")
	@Persist
	public Long getWednesday() {
		return wednesday;
	}
	/**
	 * Среда
	 */
	private Long wednesday;
	/**
	 * Четверг
	 */
	@Comment("Четверг")
	@Persist
	public Long getThursday() {
		return thursday;
	}
	/**
	 * Четверг
	 */
	private Long thursday;
	/**
	 * Пятница
	 */
	@Comment("Пятница")
	@Persist
	public Long getFriday() {
		return friday;
	}
	/**
	 * Пятница
	 */
	private Long friday;
	/**
	 * Суббота
	 */
	@Comment("Суббота")
	@Persist
	public Long getSaturday() {
		return saturday;
	}
	/**
	 * Суббота
	 */
	private Long saturday;
	/**
	 * Воскресенье
	 */
	@Comment("Воскресенье")
	@Persist
	public Long getSunday() {
		return sunday;
	}
	/**
	 * Воскресенье
	 */
	private Long sunday;
	/**
	 * Четный день
	 */
	@Comment("Четный день")
	@Persist 
	public Long getParity() {
		return parity;
	}
	/**
	 * Четный день
	 */
	private Long parity;
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
