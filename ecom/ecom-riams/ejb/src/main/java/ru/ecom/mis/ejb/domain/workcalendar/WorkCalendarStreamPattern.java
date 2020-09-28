package ru.ecom.mis.ejb.domain.workcalendar;

import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

	/**
	 * Шаблон потока обслуживания рабочего календаря
	 */
	@Comment("Шаблон потока обслуживания рабочего календаря")
@Entity
public class WorkCalendarStreamPattern extends WorkCalendarReservePattern{
	/**
	 * Поток обслуживания
	 */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {
		return theServiceStream;
	}
	public void setServiceStream(VocServiceStream aServiceStream) {
		theServiceStream = aServiceStream;
	}
	/**
	 * Поток обслуживания
	 */
	private VocServiceStream theServiceStream;
	/**
	 * Процент резерва
	 */
	@Comment("Процент резерва")
	
	public Integer getPercent() {
		return thePercent;
	}
	public void setPercent(Integer aPercent) {
		thePercent = aPercent;
	}
	/**
	 * Процент резерва
	 */
	private Integer thePercent;
}
