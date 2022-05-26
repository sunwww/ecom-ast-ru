package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.EntryMedServiceMedImplant;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = EntryMedServiceMedImplant.class)
@Comment("Услуга по записи")
@WebTrail(comment = "Услуга по записи", nameProperties = "id", view = "entityParentView-e2_entryMedServiceMedImplant.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "medService", parentForm = EntryMedServiceForm.class)
@Setter
public class EntryMedServiceMedImplantForm extends IdEntityForm {

    private Long medService;
    private String serialNumber;
    private String typeCode;

    @Persist
    @Required
    public Long getMedService() {
        return medService;
    }

    @Required
    public String getSerialNumber() {
        return serialNumber;
    }

    @Required
    public String getTypeCode() {
        return typeCode;
    }
}
