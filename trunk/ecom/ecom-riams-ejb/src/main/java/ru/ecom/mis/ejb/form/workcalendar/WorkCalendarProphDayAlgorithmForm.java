package ru.ecom.mis.ejb.form.workcalendar;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarProphDayAlgorithm;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Max;
import ru.nuzmsh.forms.validator.validators.Min;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarProphDayAlgorithm.class)
@Comment("Алгоритм по проф. дню")
@WebTrail(comment = "Алгоритм по проф. дню", nameProperties= "id", list="entityParentList-cal_prophDayAlgorithm.do", view="entityParentView-cal_prophDayAlgorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
public class WorkCalendarProphDayAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Порядок недели в месяце
	 */
	@Comment("Порядок недели в месяце")
	@Persist
	public Long getMonthOrder() {
		return theMonthOrder;
	}
	public void setMonthOrder(Long aMonthOrder) {
		theMonthOrder = aMonthOrder;
	}
	/**
	 * Порядок недели в месяце
	 */
	private Long theMonthOrder;
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
	 * Тип рабочей недели
	 */
	@Comment("Тип рабочей недели")
	@Persist
	public Long getWorkWeek() {
		return theWorkWeek;
	}
	public void setWorkWeek(Long aWorkWeek) {
		theWorkWeek = aWorkWeek;
	}
	/**
	 * Тип рабочей недели
	 */
	private Long theWorkWeek;
	/** День недели */
	@Comment("День недели")
	@Persist
	public Long getWeekDay() {
		return theWeekDay;
	}

	public void setWeekDay(Long aWeekDay) {
		theWeekDay = aWeekDay;
	}

	/** День недели */
	private Long theWeekDay;
	
	/** День месяца */
	@Comment("День месяца")
	@Persist @Max(value="31") @Min(value="1")
	public Integer getMonthDay() {
		return theMonthDay;
	}

	public void setMonthDay(Integer aMonthDay) {
		theMonthDay = aMonthDay;
	}

	/** День месяца */
	private Integer theMonthDay;
}
