package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarReservePattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarReservePattern.class)
@Comment("Шаблон резерва")
@WebTrail(comment = "Шаблон резерва", nameProperties= "id", list="entityParentList-cal_reservePattern.do"
	, view="entityParentView-cal_reservePattern.do")
@Parent(property="timePattern", parentForm=WorkCalendarTimePatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Reserve")
@Subclasses(value = { WorkCalendarServicePatternForm.class, WorkCalendarStreamPatternForm.class })
public class WorkCalendarReservePatternForm extends IdEntityForm{
	/**
	 * Шаблон времени
	 */
	@Comment("Шаблон времени")
	@Persist
	public Long getTimePattern() {
		return theTimePattern;
	}
	public void setTimePattern(Long aTimePattern) {
		theTimePattern = aTimePattern;
	}
	/**
	 * Шаблон времени
	 */
	private Long theTimePattern;
}
