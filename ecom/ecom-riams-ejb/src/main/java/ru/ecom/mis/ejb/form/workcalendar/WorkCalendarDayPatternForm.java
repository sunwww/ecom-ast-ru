package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.ecom.mis.ejb.form.worker.WorkFunctionForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarDayPattern.class)
@Comment("Шаблон дня")
@WebTrail(comment = "Шаблон дня", nameProperties= "id", list="entityParentList-cal_dayPattern.do", view="entityParentView-cal_dayPattern.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern/Day")
public class WorkCalendarDayPatternForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
	/**
	 * Рабочая функция
	 */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(Long aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	/**
	 * Рабочая функция
	 */
	private Long theWorkFunction;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist
	public Long getWorkBusy() {
		return theWorkBusy;
	}
	public void setWorkBusy(Long aWorkBusy) {
		theWorkBusy = aWorkBusy;
	}
	/**
	 * Тип занятости
	 */
	private Long theWorkBusy;
	
	/** Форма времен */
	@Comment("Форма времен")
	public WorkCalendarTimePatternForm getTimeIntervalForm() {return theTimeIntervalForm;}
	public void setTimeIntervalForm(WorkCalendarTimePatternForm aTimeIntervalForm) {theTimeIntervalForm = aTimeIntervalForm;}

	/** Форма времен */
	private WorkCalendarTimePatternForm theTimeIntervalForm;
}
