package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarTimeExample.class)
@Comment("Шаблон времени")
@WebTrail(comment = "Шаблон времени", nameProperties= "id"
	, list="entityParentList-cal_timeExample.do"
		, view="entityParentView-cal_timeExample.do")
@Parent(property="dayPattern", parentForm=WorkCalendarDayPatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time")
public class WorkCalendarTimeExampleForm extends WorkCalendarTimePatternForm {
	/**
	 * Время
	 */
	@Comment("Время")
	@Persist
	@TimeString @DoTimeString
	public String getCalendarTime() {
		return theCalendarTime;
	}
	public void setCalendarTime(String aCalendarTime) {
		theCalendarTime = aCalendarTime;
	}
	/**
	 * Время
	 */
	private String theCalendarTime;
}
