package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

	/**
	 * Алгоритм шаблона рабочего календаря
	 */
	@Comment("Алгоритм шаблона рабочего календаря")
@Entity
@AIndexes({
	@AIndex(properties = {"pattern"})
})
@Table(schema="SQLUser")
	@Getter
	@Setter
public class WorkCalendarAlgorithm extends BaseEntity{
	/**
	 * Шаблон рабочего календаря
	 */
	@Comment("Шаблон рабочего календаря")
	@ManyToOne
	public WorkCalendarPattern getPattern() {
		return pattern;
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
	private WorkCalendarPattern pattern;
}
