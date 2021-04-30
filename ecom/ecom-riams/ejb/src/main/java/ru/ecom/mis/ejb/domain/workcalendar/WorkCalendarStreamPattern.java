package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

	/**
	 * Шаблон потока обслуживания рабочего календаря
	 */
	@Comment("Шаблон потока обслуживания рабочего календаря")
@Entity
	@Getter
	@Setter
public class WorkCalendarStreamPattern extends WorkCalendarReservePattern{
	/**
	 * Поток обслуживания
	 */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {
		return serviceStream;
	}
	/**
	 * Поток обслуживания
	 */
	private VocServiceStream serviceStream;
	/**
	 * Процент резерва
	 */
	private Integer percent;
}
