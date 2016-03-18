package ru.ecom.mis.ejb.domain.workcalendar;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.JournalPatternCalendar;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Календарь работы
 * @author azviagin
 *
 */
@Comment("Календарь работы")
@Entity
@AIndexes({
	@AIndex(unique = false, properties = {"workFunction"})
})
@Table(schema="SQLUser")
public class WorkCalendar extends BaseEntity{
	
	
	
//	/** Шаблоны календаря */
//	@Comment("Шаблоны календаря")
//	@OneToMany
//	public List<WorkCalendarPattern> getPatterns() {return thePatterns;}
//	public void setPatterns(List<WorkCalendarPattern> aPatterns) {thePatterns = aPatterns;}

//	/** Шаблоны календаря */
//	private List<WorkCalendarPattern> thePatterns;
	
	/** Журналы шаблонов календаря */
	@Comment("Журналы шаблонов календаря")
	@OneToMany(mappedBy="workCalendar", cascade=CascadeType.ALL)
	public List<JournalPatternCalendar> getJournals() {return theJournals;}
	public void setJournals(List<JournalPatternCalendar> aJournals) {theJournals = aJournals;}

	/** Журналы шаблонов календаря */
	private List<JournalPatternCalendar> theJournals;
	
	/** Дни календаря */
	@Comment("Дни календаря")
	@OneToMany(mappedBy="workCalendar", cascade=CascadeType.ALL)
	public List<WorkCalendarDay> getDays() {return theDays;}
	public void setDays(List<WorkCalendarDay> aDays) {theDays = aDays;}

	/** Дни календаря */
	private List<WorkCalendarDay> theDays;
	
		
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return theWorkFunction;
	}

	public void setWorkFunction(WorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}

	/** Рабочая функция */
	private WorkFunction theWorkFunction;
	
	/** Автоматически генерировать */
	@Comment("Автоматически генерировать")
	public Boolean getAutoGenerate() {
		return theAutoGenerate;
	}

	public void setAutoGenerate(Boolean aAutoGenerate) {
		theAutoGenerate = aAutoGenerate;
	}

	/** Автоматически генерировать */
	private Boolean theAutoGenerate;

}
