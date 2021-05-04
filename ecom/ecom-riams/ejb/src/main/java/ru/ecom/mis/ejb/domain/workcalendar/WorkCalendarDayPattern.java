package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
	 * Шаблон дня рабочего календаря
	 */
	@Comment("Шаблон дня рабочего календаря")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties={"workFunction"}) })
	@Getter
	@Setter
public class WorkCalendarDayPattern extends BaseEntity{
	/**
	 * Название
	 */
	private String name;
	/**
	 * Рабочая функция
	 */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return workFunction;
	}
	/**
	 * Рабочая функция
	 */
	private WorkFunction workFunction;
	@OneToMany(mappedBy="dayPattern", cascade=CascadeType.ALL)
	public List<WorkCalendarTimePattern> getTimePatterns() {
		return timePatterns;
	}
	/**
	 * Шаблоны времен
	 */
	private List<WorkCalendarTimePattern> timePatterns;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@OneToOne
	public VocWorkBusy getWorkBusy() {
		return workBusy;
	}

	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	/** ЛПУ */
	private MisLpu lpu;
	/**
	 * Тип занятости
	 */
	private VocWorkBusy workBusy;
}
