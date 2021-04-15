package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV009;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие резальтата госпитализации результату медоса
 */
@Entity
public class E2FondResultLink extends BaseEntity {

     /** Результат обращения */
     @Comment("Результат обращения")
     @OneToOne
     public VocE2FondV009 getResult() {return theResult;}
     public void setResult(VocE2FondV009 aResult) {theResult = aResult;}
     /** Результат обращения */
    private VocE2FondV009 theResult ;

    /** Причина выписки */
    @Comment("Причина выписки")
    public String getMedosReasonDischarge() {return theMedosReasonDischarge;}
    public void setMedosReasonDischarge(String aMedosReasonDischarge) {theMedosReasonDischarge = aMedosReasonDischarge;}
    /** Причина выписки */
    private String theMedosReasonDischarge ;

    /** Результат госпитализации */
    @Comment("Результат госпитализации")
    public String getMedosHospResult() {return theMedosHospResult;}
    public void setMedosHospResult(String aMedosHospResult) {theMedosHospResult = aMedosHospResult;}
    /** Результат госпитализации */
    private String theMedosHospResult ;

   /** Исход госпитализации */
   @Comment("Исход госпитализации")
   public String getMedosHospOutcome() {return theMedosHospOutcome;}
   public void setMedosHospOutcome(String aMedosHospOutcome) {theMedosHospOutcome = aMedosHospOutcome;}
   /** Исход госпитализации */
   private String theMedosHospOutcome ;

   /** Тип коек*/
   @Comment("Тип коек")
   public String getBedSubType() {return theBedSubType;}
   public void setBedSubType(String aBedSubType) {theBedSubType = aBedSubType;}
   /** Тип коек */
   private String theBedSubType ;



}
