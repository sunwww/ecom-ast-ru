package ru.ecom.expert2.domain.voc.federal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Классификатор способов оплаты медицинской помощи (IDSP)
 */
@Entity
public class VocE2FondV010 extends VocBaseFederal{
    
    /** Условия оказания помощи (v006)*/
    @Comment("Условия оказания помощи (v006)")
    public String getUsl() {return theUsl;}
    public void setUsl(String aUsl) {theUsl = aUsl;}
    /** Условия оказания помощи */
    private String theUsl ;
    
  
}
