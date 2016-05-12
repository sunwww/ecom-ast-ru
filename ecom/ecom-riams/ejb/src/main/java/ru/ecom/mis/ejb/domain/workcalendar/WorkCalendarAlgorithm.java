package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Алгоритм шаблона рабочего календаря
	 */
	@Comment("Алгоритм шаблона рабочего календаря")
@Entity
@AIndexes({
	@AIndex(unique = false, properties = {"pattern"})
})
@Table(schema="SQLUser")	
public class WorkCalendarAlgorithm extends BaseEntity{
	/**
	 * Шаблон рабочего календаря
	 */
	@Comment("Шаблон рабочего календаря")
	@ManyToOne
	public WorkCalendarPattern getPattern() {
		return thePattern;
	}
	public void setPattern(WorkCalendarPattern aPattern) {
		thePattern = aPattern;
	}
	
	/** infoClass */
	@Comment("infoClass")
	@Transient
	public String getInfoClass() {
		return "";
	}
	/** info */
	@Comment("info")
	@Transient
	public String getInfo() {
		return "";
	}
	/**
	 * Шаблон рабочего календаря
	 */
	private WorkCalendarPattern thePattern;
}
