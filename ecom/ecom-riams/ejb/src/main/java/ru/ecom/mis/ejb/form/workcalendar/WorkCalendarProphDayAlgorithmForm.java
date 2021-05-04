package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarProphDayAlgorithm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Max;
import ru.nuzmsh.forms.validator.validators.Min;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarProphDayAlgorithm.class)
@Comment("Алгоритм по проф. дню")
@WebTrail(comment = "Алгоритм по проф. дню", nameProperties= "id", list="entityParentList-cal_prophDayAlgorithm.do", view="entityParentView-cal_prophDayAlgorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
@Setter
public class WorkCalendarProphDayAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Порядок недели в месяце
	 */
	@Comment("Порядок недели в месяце")
	@Persist
	public Long getMonthOrder() {
		return monthOrder;
	}
	/**
	 * Порядок недели в месяце
	 */
	private Long monthOrder;
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
	 * Тип рабочей недели
	 */
	@Comment("Тип рабочей недели")
	@Persist
	public Long getWorkWeek() {
		return workWeek;
	}
	/**
	 * Тип рабочей недели
	 */
	private Long workWeek;
	/** День недели */
	@Comment("День недели")
	@Persist
	public Long getWeekDay() {
		return weekDay;
	}

	/** День недели */
	private Long weekDay;
	
	/** День месяца */
	@Comment("День месяца")
	@Persist @Max(value="31") @Min(value="1")
	public Integer getMonthDay() {
		return monthDay;
	}

	/** День месяца */
	private Integer monthDay;
}
