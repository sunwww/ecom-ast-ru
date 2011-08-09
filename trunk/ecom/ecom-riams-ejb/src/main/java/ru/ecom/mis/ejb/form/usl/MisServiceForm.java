package ru.ecom.mis.ejb.form.usl;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.usluga.MisService;
import ru.ecom.mis.ejb.domain.usluga.VocServiceType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Услуга
 */
@EntityForm
@EntityFormPersistance(clazz= MisService.class)
@Comment("Услуга")
@WebTrail(comment = "Услуга", nameProperties = "servicetype", view = "entityView-mis_service.do")
public class MisServiceForm {

    /** Тип услуги */
    @Comment("Тип услуги")
    @Persist
    @Required
    public VocServiceType getServiceType() { return theServiceType ; }
    public void setServiceType(VocServiceType aServiceType) { theServiceType = aServiceType ; }


    /** Тип услуги */
    private VocServiceType theServiceType ;

}
