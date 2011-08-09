package ru.ecom.expomc.ejb.services.form.registry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.expomc.ejb.domain.registry.RegPeriod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Период
 */
@EntityForm
@EntityFormPersistance(clazz= RegPeriod.class)
@Comment("Период")
public class RegPeriodForm extends IdEntityForm {

    /** Идентификатор */
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Дата с */
    @DateString
    @Required
    @Persist
    public String getDateFrom() { return theDateFrom ; }
    public void setDateFrom(String aDateFrom) { theDateFrom = aDateFrom ; }

    /** Дата по */
    @DateString
    @Required
    @Persist
    public String getDateTo() { return theDateTo ; }
    public void setDateTo(String aDateTo) { theDateTo = aDateTo ; }

    /** Дата по */
    private String theDateTo ;
    /** Дата с */
    private String theDateFrom ;
    /** Идентификатор */
    private long theId ;
}
