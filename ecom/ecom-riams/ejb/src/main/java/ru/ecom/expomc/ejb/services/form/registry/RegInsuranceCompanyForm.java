package ru.ecom.expomc.ejb.services.form.registry;

import java.io.Serializable;

import javax.persistence.Id;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Страховая компания
 */
@EntityForm
@EntityFormPersistance(clazz= RegInsuranceCompany.class)
@Comment("Страховая компания")
public class RegInsuranceCompanyForm extends IdEntityForm {
    /** Идентификатор */
    @Id
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Название страховой компании */
    @Required
    @Persist
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Код страховой компании в ОМС */
    @Persist
    @Required
    public String getOmcCode() { return theOmcCode ; }
    public void setOmcCode(String aOmcCode) { theOmcCode = aOmcCode ; }

    /** Код страховой компании в ОМС */
    private String theOmcCode ;
    /** Название страховой компании */
    private String theName ;
    /** Идентификатор */
    private long theId ;

}
