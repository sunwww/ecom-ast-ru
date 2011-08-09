package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Время резерва обслуживания
 * @author azviagin
 *
 */
@Comment("Время резерва обслуживания")
@Entity
@Table(schema="SQLUser")
public class ServiceReserveTime extends BaseEntity{
	
	/** Времена рабочего календаря */
	@Comment("Времена рабочего календаря")
	@ManyToOne
	public WorkCalendarTime getWorkCalendarTime() {
		return theWorkCalendarTime;
	}

	public void setWorkCalendarTime(WorkCalendarTime aWorkCalendarTime) {
		theWorkCalendarTime = aWorkCalendarTime;
	}

	/** Времена рабочего календаря */
	private WorkCalendarTime theWorkCalendarTime;
	
	/** Резерв обслуживания */
	@Comment("Резерв обслуживания")
	@ManyToOne
	public ServiceReserve getServiceReserve() {
		return theServiceReserve;
	}

	public void setServiceReserve(ServiceReserve aServiceReserve) {
		theServiceReserve = aServiceReserve;
	}

	/** Резерв обслуживания */
	private ServiceReserve theServiceReserve;

}
