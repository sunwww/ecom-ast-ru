package ru.ecom.expert2.form.price;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.price.ExtDispPriceMedService;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Услуга по возрасту
 */
@EntityForm
@EntityFormPersistance(clazz = ExtDispPriceMedService.class)
@Comment("Услуга по возрасту")
@WebTrail(comment = "", nameProperties = "id", view = "entityView-e2_extDispPriceMedService.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "price", parentForm = ExtDispPriceForm.class)
public class ExtDispPriceMedServiceForm extends IdEntityForm {

    /** Цена ДД */
    @Comment("Цена ДД")
    @Persist @Required
    public Long getPrice() {return thePrice;}
    public void setPrice(Long aPrice) {thePrice = aPrice;}
    private Long thePrice ;

    /** Услуга */
    @Comment("Услуга")
    @Persist @Required
    public String getMedService() {return theMedService;}
    public void setMedService(String aMedService) {theMedService = aMedService;}
    private String theMedService ;

    /** Обязательное */
    @Comment("Обязательное")
    @Persist
    public Boolean getIsRequired() {return theIsRequired;}
    public void setIsRequired(Boolean aIsRequired) {theIsRequired = aIsRequired;}
    private Boolean theIsRequired ;

}