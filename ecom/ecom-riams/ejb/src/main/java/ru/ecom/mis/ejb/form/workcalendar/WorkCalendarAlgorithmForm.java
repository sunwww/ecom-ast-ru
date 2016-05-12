package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarAlgorithm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarAlgorithm.class)
@Comment("Алгоритм шаблона рабочего календаря")
@WebTrail(comment = "Алгоритм шаблона рабочего календаря", nameProperties= "id", list="entityParentList-cal_algorithm.do", view="entitySubclassView-cal_algorithm.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class)
@Subclasses(value = { 
		WorkCalendarDatesAlgorithmForm.class
		,WorkCalendarProphDayAlgorithmForm.class
		,WorkCalendarWeekAlgorithmForm.class
		,WorkCalendarWeekDaysAlgorithmForm.class
})
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm")
public class WorkCalendarAlgorithmForm extends IdEntityForm{
	/**
	 * Шаблон рабочего календаря
	 */
	@Comment("Шаблон рабочего календаря")
	@Persist @Required
	public Long getPattern() {
		return thePattern;
	}
	public void setPattern(Long aPattern) {
		thePattern = aPattern;
	}
	@Persist
	public String getInfoClass() {
		return theInfoClass;
	}
	
	public void setInfoClass(String aInfoClass) {
		theInfoClass = aInfoClass;
	}
	
	/** infoClass */
	private String theInfoClass;
	@Persist
	public String getInfo() {
		return theInfo;
	}

	public void setInfo(String aInfo) {
		theInfo = aInfo;
	}

	/** info */
	private String theInfo;
	/**
	 * Шаблон рабочего календаря
	 */
	private Long thePattern;
}
