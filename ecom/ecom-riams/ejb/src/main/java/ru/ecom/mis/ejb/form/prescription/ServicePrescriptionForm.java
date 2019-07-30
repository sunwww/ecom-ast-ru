package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.prescription.ServicePrescription;
import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptionPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Назначение на услугу
 * @author azviagin
 *
 */

@EntityForm
@EntityFormPersistance(clazz = ServicePrescription.class)
@Comment("Назначение на услугу")
@WebTrail(comment = "Назначение на услугу", nameProperties= "medService",list="entityParentList-pres_servicePrescription.do", view="entityParentView-pres_servicePrescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/ServicePrescription")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(PrescriptionPreCreateInterceptor.class)
)
public class ServicePrescriptionForm extends PrescriptionForm{

	/** Подтверждено назначение услуги без оплаты */
	@Comment("Подтверждено назначение услуги без оплаты")
	public Boolean getUnpaidConfirmation() {return theUnpaidConfirmation;}
	public void setUnpaidConfirmation(Boolean aUnpaidConfirmation) {theUnpaidConfirmation = aUnpaidConfirmation;}
	/** Подтверждено назначение услуги без оплаты */
	private Boolean theUnpaidConfirmation ;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	private Long theServiceStream ;
	
	/** Номер штрих-кода */
	@Comment("Номер штрих-кода")
	@Persist
	public String getBarcodeNumber() {return theBarcodeNumber;}
	public void setBarcodeNumber(String aBarcodeNumber) {theBarcodeNumber = aBarcodeNumber;}
	/** Номер штрих-кода */
	private String theBarcodeNumber;
	
	/** количество дней */
	@Comment("количество дней")
	public Long getCountDays() {return theCountDays;}
	public void setCountDays(Long aCountDays) {theCountDays = aCountDays;}
	/** количество дней */
	private Long theCountDays;
	
	/** Назначивший */
	@Comment("Назначивший")
	@Persist 
	public Long getPrescriptSpecial() {return thePrescriptSpecial;}
	public void setPrescriptSpecial(Long aPrescriptor) {thePrescriptSpecial = aPrescriptor;	}

	/** Назначивший */
	private Long thePrescriptSpecial;
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist @DateString @DoDateString
	public String getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(String aPlanStartDate) {thePlanStartDate = aPlanStartDate;}
	
	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString 
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}
	
	/** Плановая дата начала */
	private String thePlanStartDate;
	/** Плановое время начала */
	private String thePlanStartTime;
	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist 
	public Long getMedService() {
		return theMedService;
	}

	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}

	/** Медицинская услуга */
	private Long theMedService;

	/** Описание назначения */
	@Comment("Описание назначения")
	@Persist
	public String getDescriptionInfo() {
	    	    
		return theDescriptionInfo ;
	}
   public void setDescriptionInfo (String aDescriptionInfo) {theDescriptionInfo = aDescriptionInfo ; }
   private String theDescriptionInfo ;
   
   /** Операци */
	@Comment("Операци")
	public String getSurgServicies() {return theSurgServicies;}
	public void setSurgServicies(String aSurgServicies) {theSurgServicies = aSurgServicies;	}
	/** Операци */
	private String theSurgServicies;
	
/*	*//** Дата операции *//*
	@Comment("Дата операции")
	@DateString @DoDateString
	public String getSurgDate() {return theSurgDate;}
	public void setSurgDate(String aSurgDate) {theSurgDate = aSurgDate;}
	*//** Дата операции *//*
	private String theSurgDate;*/
	
	/** Время для направления на операцию */
	@Comment("Время для направления на операцию")
	public Long getSurgCalTime() {return theSurgCalTime;}
	public void setSurgCalTime(Long aSurgCalTime) {	theSurgCalTime = aSurgCalTime;}
	/** Время для направления на операцию */
	private Long theSurgCalTime;
	
	/** Дата для направления на операцию */
	@Comment("Дата для направления на операцию")
	public Long getSurgCalDate() {return theSurgCalDate;}
	public void setSurgCalDate(Long aSurgCalDate) {theSurgCalDate = aSurgCalDate;}
	/** Дата для направления на операцию */
	private Long theSurgCalDate;
	
	/** Операционная */
	@Comment("Операционная")
	public Long getSurgCabinet() {return theSurgCabinet;}
	public void setSurgCabinet(Long aSurgCabinet) {theSurgCabinet = aSurgCabinet;}
	/** Операционная */
	private Long theSurgCabinet;
}
