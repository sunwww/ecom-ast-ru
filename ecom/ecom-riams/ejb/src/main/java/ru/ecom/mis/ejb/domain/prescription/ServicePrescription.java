package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBloodGroup;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRhesusFactor;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Назначение на услугу
 * @author azviagin
 *
 */

@Comment("Назначение на услугу")
@Entity
public class ServicePrescription extends Prescription {

	/** Вид наркоза */
	@Comment("Вид наркоза")
	@OneToOne
	public VocAnesthesia getAnesthesiaType() {return theAnesthesiaType;}
	public void setAnesthesiaType(VocAnesthesia aAnesthesiaType) {theAnesthesiaType = aAnesthesiaType;}
	private VocAnesthesia theAnesthesiaType ;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Transient
	public VocServiceStream getServiceStream() {
		return getPrescriptionList().getServiceStream() ;}
	
	/** Номер штрих-кода */
	@Comment("Номер штрих-кода")
	public String getBarcodeNumber() {return theBarcodeNumber;}
	public void setBarcodeNumber(String aBarcodeNumber) {theBarcodeNumber = aBarcodeNumber;}
	/** Номер штрих-кода */
	private String theBarcodeNumber;
	
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
	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@OneToOne
	public VocBloodGroup getBloodGroup() {return theBloodGroup;}
	public void setBloodGroup(VocBloodGroup aBloodGroup) {theBloodGroup = aBloodGroup;}
	private VocBloodGroup theBloodGroup;

	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@OneToOne
	public VocRhesusFactor getRhesusFactor() {return theRhesusFactor;}
	public void setRhesusFactor(VocRhesusFactor aRhesusFactor) {theRhesusFactor = aRhesusFactor;}
	private VocRhesusFactor theRhesusFactor;
	
}
