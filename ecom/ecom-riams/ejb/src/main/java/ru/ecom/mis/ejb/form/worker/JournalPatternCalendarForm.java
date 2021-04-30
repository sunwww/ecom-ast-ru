package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
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
@Setter
public class JournalPatternCalendarForm extends IdEntityForm {

	/** Шаблон календаря */
	@Comment("Шаблон календаря")
	@Persist 
	public Long getPattern() {return pattern;}

	/** Дата начала действия */
	@Comment("Дата начала действия")
	@Persist @Required @DateString @DoDateString
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	@Persist @Required @DateString @DoDateString
	public String getDateTo() {return dateTo;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return info;}

	/** Не действует */
	@Comment("Не действует")
	@Persist
	public Boolean getNoActive() {return noActive;}

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@Persist
	public Long getWorkCalendar() {return workCalendar;}

	/** Рабочий календарь */
	private Long workCalendar;
	/** Не действует */
	private Boolean noActive;
	/** Информация */
	private String info;
	/** Дата окончания действия */
	private String dateTo;
	/** Дата начала действия */
	private String dateFrom;
	/** Шаблон календаря */
	private Long pattern;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist @Required
	public Long getWorkBusy() {
		return workBusy;
	}
	/**
	 * Тип занятости
	 */
	private Long workBusy;
}
