package ru.ecom.mis.ejb.domain.prescription;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Назначение на услугу
 * @author azviagin
 *
 */

@Comment("Назначение на услугу")
@Entity
@Table(schema="SQLUser")
public class ServicePrescription extends Prescription{
	
	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@OneToOne
	public MedService getMedService() {
		return theMedService;
	}

	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}

	/** Медицинская услуга */
	private MedService theMedService;

	/** Описание назначения */
	@Comment("Описание назначения")
	@Transient
	public String getDescriptionInfo() {
	    StringBuilder sb=new StringBuilder();
	    if (getMedService()!=null) {
	    	sb.append(" ");
	    	sb.append(getMedService().getName()) ;
	    }
	    	return sb.toString();
	}

	
}
