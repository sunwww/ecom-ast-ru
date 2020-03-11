package ru.ecom.expert2.form;

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
public class EntryMedServiceForm extends IdEntityForm {


    /** Запись */
    private Long theEntry ;
    /** Мед. услуга */
    private Long theMedService ;

    @Comment("Запись")
    @Persist @Required
    public Long getEntry() {return theEntry;}
    public void setEntry(Long aEntry) {theEntry = aEntry;}


    @Comment("Мед. услуга")
    @Persist @Required
    public Long getMedService() {return theMedService;}
    public void setMedService(Long aMedService) {theMedService = aMedService;}

    /** СНИЛС специалиста, выполневшего услугу */
    @Comment("СНИЛС специалиста, выполневшего услугу")
    @Persist
    public String getDoctorSnils() {return theDoctorSnils;}
    public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}
    private String theDoctorSnils ;

    /** Дата оказания мед. услуги */
    @Comment("Дата оказания мед. услуги")
    @Persist
    @DateString @DoDateString
    public String getServiceDate() {return theServiceDate;}
    public void setServiceDate(String aServiceDate) {theServiceDate = aServiceDate;}
    /** Дата оказания мед. услуги */
    private String theServiceDate ;

    /** Специальность врача */
    @Comment("Специальность врача")
    @Persist
    public Long getDoctorSpeciality() {return theDoctorSpeciality;}
    public void setDoctorSpeciality(Long aDoctorSpeciality) {theDoctorSpeciality = aDoctorSpeciality;}
    /** Специальность врача */
    private Long theDoctorSpeciality ;

    /** Диагноз, выявленный при оказании услуги */
    @Comment("Диагноз, выявленный при оказании услуги")
    @Persist
    public Long getMkb() {return theMkb;}
    public void setMkb(Long aMkb) {theMkb = aMkb;}
    /** Диагноз, выявленный при оказании услуги */
    private Long theMkb ;

    /** Цена */
    @Comment("Цена")
    @Persist
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    /** Цена */
    private String theCost ;

    /** Коммент */
    @Comment("Коммент")
    @Persist
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    private String theComment ;


}
