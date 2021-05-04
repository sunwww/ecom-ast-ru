package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarServicePattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarServicePattern.class)
@Comment("Шаблон услуги")
@WebTrail(comment = "Шаблон услуги", nameProperties= "id"
	, list="entityParentList-cal_servicePattern.do", view="entityParentView-cal_servicePattern.do")
@Parent(property="timePattern", parentForm=WorkCalendarTimePatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Reserve")
@Setter
public class WorkCalendarServicePatternForm extends WorkCalendarReservePatternForm{
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@Persist
	public Long getMedService() {
		return medService;
	}
	/**
	 * Медицинская услуга
	 */
	private Long medService;
	/**
	 * Тип резерва
	 */
	@Comment("Тип резерва")
	@Persist
	public Long getReserveType() {
		return reserveType;
	}
	/**
	 * Тип резерва
	 */
	private Long reserveType;
}
