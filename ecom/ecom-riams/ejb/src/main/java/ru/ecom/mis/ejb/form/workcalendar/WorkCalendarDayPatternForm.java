package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarDayPattern.class)
@Comment("Шаблон дня")
@WebTrail(comment = "Шаблон дня", nameProperties= "id", list="entityParentList-cal_dayPattern.do", view="entityParentView-cal_dayPattern.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day")
@Setter
public class WorkCalendarDayPatternForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;
	/**
	 * Рабочая функция
	 */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {
		return workFunction;
	}

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return lpu;}

	/** ЛПУ */
	private Long lpu;
	/**
	 * Рабочая функция
	 */
	private Long workFunction;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist
	public Long getWorkBusy() {
		return workBusy;
	}
	/**
	 * Тип занятости
	 */
	private Long workBusy;
	
	/** Форма времен */
	@Comment("Форма времен")
	public WorkCalendarTimeIntervalForm getTimeIntervalForm() {return timeIntervalForm;}

	/** Форма времен */
	private WorkCalendarTimeIntervalForm timeIntervalForm = new WorkCalendarTimeIntervalForm();
}
