package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarWeekDaysAlgorithm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarWeekDaysAlgorithm.class)
@Comment("Алгоритм по дням недели")
@WebTrail(comment = "Алгоритм по дням недели", nameProperties= "id", list="entityParentList-cal_weekDaysAlgorithm.do", view="entityParentView-cal_weekDaysAlgorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
public class WorkCalendarWeekDaysAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Понедельник
	 */
	@Comment("Понедельник")
	@Persist
	public Long getMonday() {
		return theMonday;
	}
	public void setMonday(Long aMonday) {
		theMonday = aMonday;
	}
	/**
	 * Понедельник
	 */
	private Long theMonday;
	/**
	 * Вторник
	 */
	@Comment("Вторник")
	@Persist
	public Long getTuesday() {
		return theTuesday;
	}
	public void setTuesday(Long aTuesday) {
		theTuesday = aTuesday;
	}
	/**
	 * Вторник
	 */
	private Long theTuesday;
	/**
	 * Среда
	 */
	@Comment("Среда")
	@Persist
	public Long getWednesday() {
		return theWednesday;
	}
	public void setWednesday(Long aWednesday) {
		theWednesday = aWednesday;
	}
	/**
	 * Среда
	 */
	private Long theWednesday;
	/**
	 * Четверг
	 */
	@Comment("Четверг")
	@Persist
	public Long getThursday() {
		return theThursday;
	}
	public void setThursday(Long aThursday) {
		theThursday = aThursday;
	}
	/**
	 * Четверг
	 */
	private Long theThursday;
	/**
	 * Пятница
	 */
	@Comment("Пятница")
	@Persist
	public Long getFriday() {
		return theFriday;
	}
	public void setFriday(Long aFriday) {
		theFriday = aFriday;
	}
	/**
	 * Пятница
	 */
	private Long theFriday;
	/**
	 * Суббота
	 */
	@Comment("Суббота")
	@Persist
	public Long getSaturday() {
		return theSaturday;
	}
	public void setSaturday(Long aSaturday) {
		theSaturday = aSaturday;
	}
	/**
	 * Суббота
	 */
	private Long theSaturday;
	/**
	 * Воскресенье
	 */
	@Comment("Воскресенье")
	@Persist
	public Long getSunday() {
		return theSunday;
	}
	public void setSunday(Long aSunday) {
		theSunday = aSunday;
	}
	/**
	 * Воскресенье
	 */
	private Long theSunday;
	/**
	 * Четный день
	 */
	@Comment("Четный день")
	@Persist 
	public Long getParity() {
		return theParity;
	}
	public void setParity(Long aParity) {
		theParity = aParity;
	}
	/**
	 * Четный день
	 */
	private Long theParity;
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
