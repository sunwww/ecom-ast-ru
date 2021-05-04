package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
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
 *
 * @author azviagin
 */

@EntityForm
@EntityFormPersistance(clazz = ServicePrescription.class)
@Comment("Назначение на услугу")
@WebTrail(comment = "Назначение на услугу", nameProperties = "medService", list = "entityParentList-pres_servicePrescription.do", view = "entityParentView-pres_servicePrescription.do")
@Parent(property = "prescriptionList", parentForm = AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/ServicePrescription")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(PrescriptionPreCreateInterceptor.class)
)
@Setter
public class OperationPrescriptionForm extends ServicePrescriptionForm {

    /**
     * Раб. функция, осущ. забор
     */
    @Comment("Раб. функция, осущ. забор")
    @Persist
    @Required
    public Long getIntakeSpecial() {
        return intakeSpecial;
    }

    private Long intakeSpecial;

    /**
     * Продолжительность услуги (операции)
     */
    @Comment("Продолжительность услуги (операции)")
    @Required
    public Integer getDuration() {
        return duration;
    }
    private Integer duration;

    /**
     * Плановое время начала
     */
    @Comment("Плановое время начала")
    @Persist
    @TimeString
    @DoTimeString
    public String getPlanStartTime() {
        return planStartTime;
    }
    private String planStartTime;

    /**
     * Медицинская услуга
     */
    @Comment("Медицинская услуга")
    @Persist
    @Required
    public Long getMedService() {
        return medService;
    }
    private Long medService;


    /**
     * Время для направления на операцию
     */
    @Comment("Время для направления на операцию")
    public Long getSurgCalTime() {
        return surgCalTime;
    }
    private Long surgCalTime;

    /**
     * Дата для направления на операцию
     */
    @Comment("Дата для направления на операцию")
    @Required
    public Long getSurgCalDate() {
        return surgCalDate;
    }
    private Long surgCalDate;

    /**
     * Операционная
     */
    @Comment("Операционная")
    @Required
    @Persist
    public Long getPrescriptCabinet() {
        return prescriptCabinet;
    }
    private Long prescriptCabinet;

    /**
     * Вид наркоза
     */
    @Comment("Вид наркоза")
    @Persist
    @Required
    public Long getAnesthesiaType() {
        return anesthesiaType;
    }
    private Long anesthesiaType;

    /** Отделение */
    @Comment("Отделение")
    @Required
    public Long getDepartment() {return department;}
    private Long department;
}
