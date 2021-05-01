package ru.ecom.mis.ejb.domain.workcalendar;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
	@AIndex(properties = {"workFunction"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class WorkCalendar extends BaseEntity{

	/** Журналы шаблонов календаря */
	@Comment("Журналы шаблонов календаря")
	@OneToMany(mappedBy="workCalendar", cascade=CascadeType.ALL)
	public List<JournalPatternCalendar> getJournals() {return journals;}

	/** Журналы шаблонов календаря */
	private List<JournalPatternCalendar> journals;
	
	/** Дни календаря */
	@Comment("Дни календаря")
	@OneToMany(mappedBy="workCalendar", cascade=CascadeType.ALL)
	public List<WorkCalendarDay> getDays() {return days;}

	/** Дни календаря */
	private List<WorkCalendarDay> days;
	
		
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return workFunction;
	}

	/** Рабочая функция */
	private WorkFunction workFunction;
	
	/** Автоматически генерировать */
	private Boolean autoGenerate;
	
	/** Через сколько дней генерировать */
	private Long afterDaysGenerate;

}
