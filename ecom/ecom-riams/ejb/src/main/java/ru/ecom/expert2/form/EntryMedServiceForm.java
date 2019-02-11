package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.EntryMedService;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


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
    @Persist
    public Long getEntry() {return theEntry;}
    public void setEntry(Long aEntry) {theEntry = aEntry;}


    @Comment("Мед. услуга")
    @Persist
    public Long getMedService() {return theMedService;}
    public void setMedService(Long aMedService) {theMedService = aMedService;}


}
