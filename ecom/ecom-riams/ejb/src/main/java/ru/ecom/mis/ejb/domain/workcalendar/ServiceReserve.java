package ru.ecom.mis.ejb.domain.workcalendar;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Резерв обслуживания
 * @author azviagin
 *
 */
@Comment("Резерв обслуживания")
@Entity
@Table(schema="SQLUser")
public class ServiceReserve extends BaseEntity{
	
	/** Рабочие времена */
	@Comment("Рабочие времена резерва")
	@OneToMany(mappedBy="serviceReserve", cascade=CascadeType.ALL)
	public List<ServiceReserveTime> getReserveTimes() {
		return theReserveTimes;
	}

	public void setReserveTimes(List<ServiceReserveTime> aReserveTimes) {
		theReserveTimes = aReserveTimes;
	}

	/** Рабочие времена резерва */
	private List<ServiceReserveTime> theReserveTimes;
	
	/** Услуги резерва */
	@Comment("Услуги резерва")
	@OneToMany(mappedBy="serviceReserve", cascade=CascadeType.ALL)
	public List<ServiceReserveService> getReserveServices() {
		return theReserveServices;
	}

	public void setReserveServices(List<ServiceReserveService> aReserveServices) {
		theReserveServices = aReserveServices;
	}

	/** Услуги резерва */
	private List<ServiceReserveService> theReserveServices;

	/** Тип резервирования */
	@Comment("Тип резервирования")
	@OneToOne
	public VocServiceReserveType getServiceReserveType() {
		return theServiceReserveType;
	}

	public void setServiceReserveType(VocServiceReserveType aServiceReserveType) {
		theServiceReserveType = aServiceReserveType;
	}

	/** Тип резервирования */
	private VocServiceReserveType theServiceReserveType;
	
	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;

}
