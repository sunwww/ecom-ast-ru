package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

	/**
	 * Шаблон услуги рабочего календаря
	 */
	@Comment("Шаблон услуги рабочего календаря")
@Entity
	@Getter
	@Setter
public class WorkCalendarServicePattern extends WorkCalendarReservePattern{
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@OneToOne
	public MedService getMedService() {
		return medService;
	}
	/**
	 * Медицинская услуга
	 */
	private MedService medService;
	/**
	 * Тип резерва
	 */
	@Comment("Тип резерва")
	@OneToOne
	public VocServiceReserveType getReserveType() {
		return reserveType;
	}
	/**
	 * Тип резерва
	 */
	private VocServiceReserveType reserveType;
}
