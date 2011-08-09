package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarStreamPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarStreamPattern.class)
@Comment("Шаблон потока обслуживания")
@WebTrail(comment = "Шаблон потока обслуживания", nameProperties= "id"
	, list="entityParentList-cal_streamPattern.do", view="entityParentView-cal_streamPattern.do")
@Parent(property="timePattern", parentForm=WorkCalendarTimePatternForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Reserve")
public class WorkCalendarStreamPatternForm extends WorkCalendarReservePatternForm {
	/**
	 * Поток обслуживания
	 */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {
		return theServiceStream;
	}
	public void setServiceStream(Long aServiceStream) {
		theServiceStream = aServiceStream;
	}
	/**
	 * Поток обслуживания
	 */
	private Long theServiceStream;
	/**
	 * Процент резерва
	 */
	@Comment("Процент резерва")
	@Persist
	public Integer getPercent() {
		return thePercent;
	}
	public void setPercent(Integer aPercent) {
		thePercent = aPercent;
	}
	/**
	 * Процент резерва
	 */
	private Integer thePercent;
}
