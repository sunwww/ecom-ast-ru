package ru.ecom.expert2.form;

import lombok.Setter;
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
@Setter
public class E2FondResultLinkForm extends IdEntityForm {

     /** Результат обращения */
     @Comment("Результат обращения")
     @Persist
     public Long getResult() {return result;}
     /** Результат обращения */
    private Long result ;

    /** Причина выписки */
    @Comment("Причина выписки")
    @Persist
    public String getMedosReasonDischarge() {return medosReasonDischarge;}
    /** Причина выписки */
    private String medosReasonDischarge ;

    /** Результат госпитализации */
    @Comment("Результат госпитализации")
    @Persist
    public String getMedosHospResult() {return medosHospResult;}
    /** Результат госпитализации */
    private String medosHospResult ;

   /** Исход госпитализации */
   @Comment("Исход госпитализации")
   @Persist
   public String getMedosHospOutcome() {return medosHospOutcome;}
   /** Исход госпитализации */
   private String medosHospOutcome ;

   /** Тип коек*/
   @Comment("Тип коек")
   @Persist
   public String getBedSubType() {return bedSubType;}
   /** Тип коек */
   private String bedSubType ;
}

