package ru.ecom.expert2.domain.voc.federal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Результат обращения  (RSLT)
 */
@Entity
public class VocE2FondV009  extends VocBaseFederal{
    
    /** Условия оказания помощи (v006)*/
    @Comment("Условия оказания помощи (v006)")
    public String getUsl() {return theUsl;}
    public void setUsl(String aUsl) {theUsl = aUsl;}
    /** Условия оказания помощи */
    private String theUsl ;

    /** Коды исхода доп. диспансеризации */
    @Comment("Коды исхода доп. диспансеризации")
    public String getExtDispCodes() {return theExtDispCodes;}
    public void setExtDispCodes(String aExtDispCodes) {theExtDispCodes = aExtDispCodes;}
    /** Коды исхода доп. диспансеризации */
    private String theExtDispCodes ;
    
  
}
