package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Услуга резерва обслуживания
 * @author azviagin
 *
 */

@Comment("Услуга резерва обслуживания")
@Entity
@Table(schema="SQLUser")
public class ServiceReserveService extends BaseEntity{
	
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
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@OneToOne
	public MedService getMedService() {
		return theMedService;
	}

	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}

	/** Мед. услуга */
	private MedService theMedService;
	


}
