package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkCalendarType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

	/**
	 * Шаблон рабочего календаря
	 */
	@Comment("Шаблон рабочего календаря")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties={"workFunction"}) })
	@Getter
	@Setter
public class WorkCalendarPattern extends BaseEntity{
	/**
	 * Тип календаря
	 */
	@Comment("Тип календаря")
	@OneToOne
	public VocWorkCalendarType getCalendarType() {
		return calendarType;
	}
	/**
	 * Тип календаря
	 */
	private VocWorkCalendarType calendarType;
	/**
	 * Рабочая функция
	 */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return workFunction;
	}

	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return lpu;
	}

	/** ЛПУ */
	private MisLpu lpu;
	/**
	 * Рабочая функция
	 */
	private WorkFunction workFunction;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@OneToOne
	public VocWorkBusy getWorkBusy() {
		return workBusy;
	}
	/**
	 * Тип занятости
	 */
	private VocWorkBusy workBusy;
	@OneToMany(mappedBy="pattern",  cascade=CascadeType.ALL)
	public List<WorkCalendarAlgorithm> getAlgorithms() {
		return algorithms;
	}
	/**
	 * Алгоритмы шаблона рабочего календаря
	 */
	private List<WorkCalendarAlgorithm> algorithms;
	/**
	 * Название
	 */
	private String name;
	
	@Transient
	public String getInfo() {
		return getName() ;
	}
}
