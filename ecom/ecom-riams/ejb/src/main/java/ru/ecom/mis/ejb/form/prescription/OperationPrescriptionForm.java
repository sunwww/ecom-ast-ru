package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.prescription.ServicePrescription;
import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptionPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.Required;
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
public class OperationPrescriptionForm extends ServicePrescriptionForm {

	/** Раб. функция, осущ. забор */
	@Comment("Раб. функция, осущ. забор")
	@Persist @Required
	public Long getIntakeSpecial() {return theIntakeSpecial;}
	public void setIntakeSpecial(Long aIntakeSpecial) {theIntakeSpecial = aIntakeSpecial;}
	private Long theIntakeSpecial;

	/** Продолжительность услуги (операции) */
	@Comment("Продолжительность услуги (операции)")
	@Required
	public Integer getDuration() {return theDuration;}
	public void setDuration(Integer aDuration) {theDuration = aDuration;}
	private Integer theDuration ;


	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}
    private String thePlanStartTime;

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist @Required
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}
	private Long theMedService;


	/** Время для направления на операцию */
	@Comment("Время для направления на операцию")
	public Long getSurgCalTime() {return theSurgCalTime;}
	public void setSurgCalTime(Long aSurgCalTime) {	theSurgCalTime = aSurgCalTime;}
	private Long theSurgCalTime;
	
	/** Дата для направления на операцию */
	@Comment("Дата для направления на операцию")
	@Required
	public Long getSurgCalDate() {return theSurgCalDate;}
	public void setSurgCalDate(Long aSurgCalDate) {theSurgCalDate = aSurgCalDate;}
	private Long theSurgCalDate;
	
	/** Операционная */
	@Comment("Операционная")
	@Required @Persist
	public Long getPrescriptCabinet() {return thePrescriptCabinet;}
	public void setPrescriptCabinet(Long aPrescriptCabinet) {thePrescriptCabinet = aPrescriptCabinet;}
	private Long thePrescriptCabinet;

	/** Вид наркоза */
	@Comment("Вид наркоза")
	@Persist @Required
	public Long getAnesthesiaType() {return theAnesthesiaType;}
	public void setAnesthesiaType(Long aAnesthesiaType) {theAnesthesiaType = aAnesthesiaType;}
	private Long theAnesthesiaType ;

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@Persist @Required
	public Long getBloodGroup() {return theBloodGroup;}
	public void setBloodGroup(Long aBloodGroup) {theBloodGroup = aBloodGroup;}
	private Long theBloodGroup;

	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@Persist @Required
	public Long getRhesusFactor() {return theRhesusFactor;}
	public void setRhesusFactor(Long aRhesusFactor) {theRhesusFactor = aRhesusFactor;}
	private Long theRhesusFactor;
}
