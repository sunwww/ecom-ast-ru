package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.ecom.mis.ejb.form.worker.WorkFunctionForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz= WorkCalendarPattern.class)
@Comment("Шаблон рабочего календаря")
@WebTrail(comment = "Шаблон рабочего календаря", nameProperties= "name", view="entitySublassView-cal_pattern.do",list= "entityList-cal_pattern.do")
//@Parent(property="workFunction", parentForm=WorkFunctionForm.class)
@Subclasses(value = { WorkCalendarPatternByOperatingRoomForm.class,WorkCalendarPatternBySpecialistForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern")
public class WorkCalendarPatternForm extends IdEntityForm {
	/**
	 * Тип календаря
	 */
	@Comment("Тип календаря")
	@Persist @Required
	public Long getCalendarType() {
		return theCalendarType;
	}
	public void setCalendarType(Long aCalendarType) {
		theCalendarType = aCalendarType;
	}
	/**
	 * Тип календаря
	 */
	private Long theCalendarType;
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
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Информация */
	private String theInfo;
	/**
	 * Название
	 */
	private String theName;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private Long theLpu;
}
