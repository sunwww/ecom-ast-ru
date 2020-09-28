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

	/** Продолжительность услуги (операции) */
	@Comment("Продолжительность услуги (операции)")
	public Integer getDuration() {return theDuration;}
	public void setDuration(Integer aDuration) {theDuration = aDuration;}
	private Integer theDuration ;

	/** Подтверждено назначение услуги без оплаты */
	@Comment("Подтверждено назначение услуги без оплаты")
	public Boolean getUnpaidConfirmation() {return theUnpaidConfirmation;}
	public void setUnpaidConfirmation(Boolean aUnpaidConfirmation) {theUnpaidConfirmation = aUnpaidConfirmation;}
	private Boolean theUnpaidConfirmation ;

	/** Номер штрих-кода */
	@Comment("Номер штрих-кода")
	@Persist
	public String getBarcodeNumber() {return theBarcodeNumber;}
	public void setBarcodeNumber(String aBarcodeNumber) {theBarcodeNumber = aBarcodeNumber;}
	private String theBarcodeNumber;
	
	/** количество дней */
	@Comment("количество дней")
	public Long getCountDays() {return theCountDays;}
	public void setCountDays(Long aCountDays) {theCountDays = aCountDays;}
	private Long theCountDays;

	/** Назначивший */
	private Long thePrescriptSpecial;
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist @DateString @DoDateString
	public String getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(String aPlanStartDate) {thePlanStartDate = aPlanStartDate;}
    private String thePlanStartDate;

	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}
    private String thePlanStartTime;

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist 
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}
	private Long theMedService;

   /** Операци */
	@Comment("Операци")
	public String getSurgServicies() {return theSurgServicies;}
	public void setSurgServicies(String aSurgServicies) {theSurgServicies = aSurgServicies;	}
	private String theSurgServicies;

	/** Время для направления на операцию */
	@Comment("Время для направления на операцию")
	public Long getSurgCalTime() {return theSurgCalTime;}
	public void setSurgCalTime(Long aSurgCalTime) {	theSurgCalTime = aSurgCalTime;}
	private Long theSurgCalTime;
	
	/** Дата для направления на операцию */
	@Comment("Дата для направления на операцию")
	public Long getSurgCalDate() {return theSurgCalDate;}
	public void setSurgCalDate(Long aSurgCalDate) {theSurgCalDate = aSurgCalDate;}
	private Long theSurgCalDate;
	
	/** Операционная */
	@Comment("Операционная")
	public Long getSurgCabinet() {return theSurgCabinet;}
	public void setSurgCabinet(Long aSurgCabinet) {theSurgCabinet = aSurgCabinet;}
	private Long theSurgCabinet;

	/** Вид наркоза */
	@Comment("Вид наркоза")
	@Persist
	public Long getAnesthesiaType() {return theAnesthesiaType;}
	public void setAnesthesiaType(Long aAnesthesiaType) {theAnesthesiaType = aAnesthesiaType;}
	private Long theAnesthesiaType ;

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@Persist
	public Long getBloodGroup() {return theBloodGroup;}
	public void setBloodGroup(Long aBloodGroup) {theBloodGroup = aBloodGroup;}
	private Long theBloodGroup;

	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@Persist
	public Long getRhesusFactor() {return theRhesusFactor;}
	public void setRhesusFactor(Long aRhesusFactor) {theRhesusFactor = aRhesusFactor;}
	private Long theRhesusFactor;
}
