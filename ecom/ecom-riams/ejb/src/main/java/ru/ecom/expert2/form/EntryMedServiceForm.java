package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.EntryMedService;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;


@EntityForm
@EntityFormPersistance(clazz = EntryMedService.class)
@Comment("Услуга по записи")
@WebTrail(comment = "Услуга по записи", nameProperties = "id", view = "entityParentView-e2_entryMedService.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "entry", parentForm = E2EntryForm.class)
@Setter
public class EntryMedServiceForm extends IdEntityForm {


    /** Запись */
    private Long entry ;
    /** Мед. услуга */
    private Long medService ;

    @Comment("Запись")
    @Persist @Required
    public Long getEntry() {return entry;}


    @Comment("Мед. услуга")
    @Persist @Required
    public Long getMedService() {return medService;}

    /** СНИЛС специалиста, выполневшего услугу */
    @Comment("СНИЛС специалиста, выполневшего услугу")
    @Persist
    public String getDoctorSnils() {return doctorSnils;}
    private String doctorSnils ;

    /** Дата оказания мед. услуги */
    @Comment("Дата оказания мед. услуги")
    @Persist
    @DateString @DoDateString
    public String getServiceDate() {return serviceDate;}
    /** Дата оказания мед. услуги */
    private String serviceDate ;

    /** Специальность врача */
    @Comment("Специальность врача")
    @Persist
    public Long getDoctorSpeciality() {return doctorSpeciality;}
    /** Специальность врача */
    private Long doctorSpeciality ;

    /** Диагноз, выявленный при оказании услуги */
    @Comment("Диагноз, выявленный при оказании услуги")
    @Persist
    public Long getMkb() {return mkb;}
    /** Диагноз, выявленный при оказании услуги */
    private Long mkb ;

    /** Цена */
    @Comment("Цена")
    @Persist
    public String getCost() {return cost;}
    /** Цена */
    private String cost ;

    /** Коммент */
    @Comment("Коммент")
    @Persist
    public String getComment() {return comment;}
    private String comment ;
}
