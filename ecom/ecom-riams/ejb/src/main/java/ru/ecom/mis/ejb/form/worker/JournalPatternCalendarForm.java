package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.JournalPatternCalendar;
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
@EntityFormPersistance(clazz= JournalPatternCalendar.class)
@Comment("Журнал шаблона календаря")
@WebTrail(comment = "Журнал шаблона  календаря", nameProperties= "info", view="entityParentView-work_journalPatternCalendar.do")
@Parent(property="workCalendar", parentMapForm="$$map$$cal_workCalendarForm")
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/JournalPattern")
public class JournalPatternCalendarForm extends IdEntityForm {

	/** Шаблон календаря */
	@Comment("Шаблон календаря")
	@Persist 
	public Long getPattern() {return thePattern;}
	public void setPattern(Long aPattern) {thePattern = aPattern;}

	/** Дата начала действия */
	@Comment("Дата начала действия")
	@Persist @Required @DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	@Persist @Required @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}
	
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Не действует */
	@Comment("Не действует")
	@Persist
	public Boolean getNoActive() {return theNoActive;}
	public void setNoActive(Boolean aNoActive) {theNoActive = aNoActive;}

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@Persist
	public Long getWorkCalendar() {return theWorkCalendar;}
	public void setWorkCalendar(Long aWorkCalendar) {theWorkCalendar = aWorkCalendar;}

	/** Рабочий календарь */
	private Long theWorkCalendar;
	/** Не действует */
	private Boolean theNoActive;
	/** Информация */
	private String theInfo;
	/** Дата окончания действия */
	private String theDateTo;
	/** Дата начала действия */
	private String theDateFrom;
	/** Шаблон календаря */
	private Long thePattern;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist @Required
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
}
