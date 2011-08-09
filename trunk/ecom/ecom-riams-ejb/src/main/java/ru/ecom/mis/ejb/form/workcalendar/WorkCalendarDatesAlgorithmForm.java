package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDatesAlgorithm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarDatesAlgorithm.class)
@Comment("Алгоритм по датам")
@WebTrail(comment = "Алгоритм по датам", nameProperties= "id", list="entityParentList-cal_datesAlgorithm.do", view="entityParentView-cal_datesAlgorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
public class WorkCalendarDatesAlgorithmForm extends WorkCalendarAlgorithmForm{
	/**
	 * Начиная с даты
	 */
	@Comment("Начиная с даты")
	@Persist @Required
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Начиная с даты
	 */
	private String theDateFrom;
	/**
	 * Заканчивая датой
	 */
	@Comment("Заканчивая датой")
	@Persist @Required
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Заканчивая датой
	 */
	private String theDateTo;
	/**
	 * Шаблон дня рабочего календаря
	 */
	@Comment("Шаблон дня рабочего календаря")
	@Persist @Required
	public Long getDayPattern() {
		return theDayPattern;
	}
	public void setDayPattern(Long aDayPattern) {
		theDayPattern = aDayPattern;
	}
	/**
	 * Шаблон дня рабочего календаря
	 */
	private Long theDayPattern;
}
