package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
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
@Setter
public class ServicePrescriptionForm extends PrescriptionForm{

	/** Продолжительность услуги (операции) */
	@Comment("Продолжительность услуги (операции)")
	public Integer getDuration() {return duration;}
	private Integer duration ;

	/** Подтверждено назначение услуги без оплаты */
	@Comment("Подтверждено назначение услуги без оплаты")
	public Boolean getUnpaidConfirmation() {return unpaidConfirmation;}
	private Boolean unpaidConfirmation ;

	/** Номер штрих-кода */
	@Comment("Номер штрих-кода")
	@Persist
	public String getBarcodeNumber() {return barcodeNumber;}
	private String barcodeNumber;
	
	/** количество дней */
	@Comment("количество дней")
	public Long getCountDays() {return countDays;}
	private Long countDays;

	/** Назначивший */
	private Long prescriptSpecial;
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist @DateString @DoDateString
	public String getPlanStartDate() {return planStartDate;}
    private String planStartDate;

	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString
	public String getPlanStartTime() {return planStartTime;}
    private String planStartTime;

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist 
	public Long getMedService() {return medService;}
	private Long medService;

   /** Операци */
	@Comment("Операци")
	public String getSurgServicies() {return surgServicies;}
	private String surgServicies;

	/** Время для направления на операцию */
	@Comment("Время для направления на операцию")
	public Long getSurgCalTime() {return surgCalTime;}
	private Long surgCalTime;
	
	/** Дата для направления на операцию */
	@Comment("Дата для направления на операцию")
	public Long getSurgCalDate() {return surgCalDate;}
	private Long surgCalDate;
	
	/** Операционная */
	@Comment("Операционная")
	public Long getSurgCabinet() {return surgCabinet;}
	private Long surgCabinet;

	/** Вид наркоза */
	@Comment("Вид наркоза")
	@Persist
	public Long getAnesthesiaType() {return anesthesiaType;}
	private Long anesthesiaType ;

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@Persist
	public Long getBloodGroup() {return bloodGroup;}
	private Long bloodGroup;

	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@Persist
	public Long getRhesusFactor() {return rhesusFactor;}
	private Long rhesusFactor;
}
