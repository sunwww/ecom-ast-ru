package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV009;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV012;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие исхода случая результату медоса
 */
@Entity
public class E2FondIshodLink extends BaseEntity {

     /** Исход обращения */
     @Comment("Результат обращения")
     @OneToOne
     public VocE2FondV012 getIshod() {return theIshod;}
     public void setIshod(VocE2FondV012 aIshod) {theIshod = aIshod;}
     /** Результат обращения */
    private VocE2FondV012 theIshod ;

    /** Результат госпитализации */
    @Comment("Результат госпитализации")
    public String getMedosHospResult() {return theMedosHospResult;}
    public void setMedosHospResult(String aMedosHospResult) {theMedosHospResult = aMedosHospResult;}
    /** Результат госпитализации */
    private String theMedosHospResult ;

   /** Тип коек*/
   @Comment("Тип коек")
   public String getBedSubType() {return theBedSubType;}
   public void setBedSubType(String aBedSubType) {theBedSubType = aBedSubType;}
   /** Тип коек */
   private String theBedSubType ;

}
