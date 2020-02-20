package ru.ecom.expert2.domain.price;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ExtDispPriceMedService extends BaseEntity {

    /** Цена ДД */
    @Comment("Цена ДД")
    @ManyToOne
    public ExtDispPrice getPrice() {return thePrice;}
    public void setPrice(ExtDispPrice aPrice) {thePrice = aPrice;}
    private ExtDispPrice thePrice ;

    /** Услуга */
    @Comment("Услуга")
    public String getMedService() {return theMedService;}
    public void setMedService(String aMedService) {theMedService = aMedService;}
    private String theMedService ;

    /** Обязательное */
    @Comment("Обязательное")
    public Boolean getIsRequired() {return theIsRequired;}
    public void setIsRequired(Boolean aIsRequired) {theIsRequired = aIsRequired;}
    private Boolean theIsRequired ;

}