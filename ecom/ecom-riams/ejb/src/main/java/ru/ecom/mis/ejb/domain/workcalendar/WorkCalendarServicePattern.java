package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Шаблон услуги рабочего календаря
	 */
	@Comment("Шаблон услуги рабочего календаря")
@Entity
@Table(schema="SQLUser")
public class WorkCalendarServicePattern extends WorkCalendarReservePattern{
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@OneToOne
	public MedService getMedService() {
		return theMedService;
	}
	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}
	/**
	 * Медицинская услуга
	 */
	private MedService theMedService;
	/**
	 * Тип резерва
	 */
	@Comment("Тип резерва")
	@OneToOne
	public VocServiceReserveType getReserveType() {
		return theReserveType;
	}
	public void setReserveType(VocServiceReserveType aReserveType) {
		theReserveType = aReserveType;
	}
	/**
	 * Тип резерва
	 */
	private VocServiceReserveType theReserveType;
}
