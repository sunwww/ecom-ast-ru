package ru.ecom.expomc.ejb.services.form.registry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Id;

/**
 * Страховая компания
 */
@EntityForm
@EntityFormPersistance(clazz= RegInsuranceCompany.class)
@Comment("Страховая компания")
public class RegInsuranceCompanyForm extends IdEntityForm {
    /** Идентификатор */
    @Id
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }

    /** Название страховой компании */
    @Required
    @Persist
    public String getName() { return name ; }
    public void setName(String aName) { name = aName ; }

    /** Код страховой компании в ОМС */
    @Persist
    @Required
    public String getOmcCode() { return omcCode ; }
    public void setOmcCode(String aOmcCode) { omcCode = aOmcCode ; }

    /** Код страховой компании в ОМС */
    private String omcCode ;
    /** Название страховой компании */
    private String name ;
    /** Идентификатор */
    private long id ;

}
