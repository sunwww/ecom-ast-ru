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
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimePattern;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

	/**
	 * Шаблон дня рабочего календаря
	 */
	@Comment("Шаблон дня рабочего календаря")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties={"workFunction"}) })
public class WorkCalendarDayPattern extends BaseEntity{
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
	/**
	 * Рабочая функция
	 */
	private WorkFunction theWorkFunction;
	@OneToMany(mappedBy="dayPattern", cascade=CascadeType.ALL)
	public List<WorkCalendarTimePattern> getTimePatterns() {
		return theTimePatterns;
	}
	public void setTimePatterns(List<WorkCalendarTimePattern> aTimePatterns) {
		theTimePatterns = aTimePatterns;
	}
	/**
	 * Шаблоны времен
	 */
	private List<WorkCalendarTimePattern> theTimePatterns;
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
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private MisLpu theLpu;
	/**
	 * Тип занятости
	 */
	private VocWorkBusy theWorkBusy;
}
