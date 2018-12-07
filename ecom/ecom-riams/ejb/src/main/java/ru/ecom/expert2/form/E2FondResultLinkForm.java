package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2FondResultLink;
import ru.ecom.expert2.form.voc.federal.VocE2FondV009Form;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Соответствие резальтата госпитализации (V009) результату медоса
 */
@EntityForm
@EntityFormPersistance(clazz = E2FondResultLink.class)
@Comment("Связь результата госпитализации с V009")
@WebTrail(comment = "Справочник настроек экспертизы", nameProperties = "id", view = "entityView-e2_v009Link.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "result", parentForm = VocE2FondV009Form.class)
public class E2FondResultLinkForm extends IdEntityForm {

     /** Результат обращения */
     @Comment("Результат обращения")
     @Persist
     public Long getResult() {return theResult;}
     public void setResult(Long aResult) {theResult = aResult;}
     /** Результат обращения */
    private Long theResult ;

    /** Причина выписки */
    @Comment("Причина выписки")
    @Persist
    public String getMedosReasonDischarge() {return theMedosReasonDischarge;}
    public void setMedosReasonDischarge(String aMedosReasonDischarge) {theMedosReasonDischarge = aMedosReasonDischarge;}
    /** Причина выписки */
    private String theMedosReasonDischarge ;

    /** Результат госпитализации */
    @Comment("Результат госпитализации")
    @Persist
    public String getMedosHospResult() {return theMedosHospResult;}
    public void setMedosHospResult(String aMedosHospResult) {theMedosHospResult = aMedosHospResult;}
    /** Результат госпитализации */
    private String theMedosHospResult ;

   /** Исход госпитализации */
   @Comment("Исход госпитализации")
   @Persist
   public String getMedosHospOutcome() {return theMedosHospOutcome;}
   public void setMedosHospOutcome(String aMedosHospOutcome) {theMedosHospOutcome = aMedosHospOutcome;}
   /** Исход госпитализации */
   private String theMedosHospOutcome ;

   /** Тип коек*/
   @Comment("Тип коек")
   @Persist
   public String getBedSubType() {return theBedSubType;}
   public void setBedSubType(String aBedSubType) {theBedSubType = aBedSubType;}
   /** Тип коек */
   private String theBedSubType ;



}

