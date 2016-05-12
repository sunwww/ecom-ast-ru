package ru.ecom.mis.ejb.domain.workcalendar;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkCalendarType;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarAlgorithm;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Шаблон рабочего календаря
	 */
	@Comment("Шаблон рабочего календаря")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties={"workFunction"}) })
public class WorkCalendarPattern extends BaseEntity{
	/**
	 * Тип календаря
	 */
	@Comment("Тип календаря")
	@OneToOne
	public VocWorkCalendarType getCalendarType() {
		return theCalendarType;
	}
	public void setCalendarType(VocWorkCalendarType aCalendarType) {
		theCalendarType = aCalendarType;
	}
	/**
	 * Тип календаря
	 */
	private VocWorkCalendarType theCalendarType;
	/**
	 * Рабочая функция
	 */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return theWorkFunction;
	}
	public void setWorkFunction(WorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private MisLpu theLpu;
	/**
	 * Рабочая функция
	 */
	private WorkFunction theWorkFunction;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@OneToOne
	public VocWorkBusy getWorkBusy() {
		return theWorkBusy;
	}
	public void setWorkBusy(VocWorkBusy aWorkBusy) {
		theWorkBusy = aWorkBusy;
	}
	/**
	 * Тип занятости
	 */
	private VocWorkBusy theWorkBusy;
	@OneToMany(mappedBy="pattern",  cascade=CascadeType.ALL)
	public List<WorkCalendarAlgorithm> getAlgorithms() {
		return theAlgorithms;
	}
	public void setAlgorithms(List<WorkCalendarAlgorithm> aAlgorithms) {
		theAlgorithms = aAlgorithms;
	}
	/**
	 * Алгоритмы шаблона рабочего календаря
	 */
	private List<WorkCalendarAlgorithm> theAlgorithms;
	/**
	 * Название
	 */
	@Comment("Название")
	
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
	
	@Transient
	public String getInfo() {
		return getName() ;
	}
}
