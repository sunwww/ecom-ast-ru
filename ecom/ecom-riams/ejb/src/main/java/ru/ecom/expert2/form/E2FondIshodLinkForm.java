package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2FondIshodLink;
import ru.ecom.expert2.form.voc.federal.VocE2FondV012Form;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Соответствие исхода случая результату медоса (V012)
 */
@EntityForm
@EntityFormPersistance(clazz = E2FondIshodLink.class)
@Comment("Связь с V012")
@WebTrail(comment = "Справочник настроек экспертизы", nameProperties = "id", view = "entityParentView-e2_v012Link.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "ishod", parentForm = VocE2FondV012Form.class)
@Setter
public class E2FondIshodLinkForm extends IdEntityForm {

     /** Исход обращения */
     @Comment("Результат обращения")
     @Persist
     public Long getIshod() {return ishod;}
     /** Результат обращения */
    private Long ishod ;

 /** Результат госпитализации */
    @Comment("Результат госпитализации")
    @Persist
    public String getMedosHospResult() {return medosHospResult;}
    /** Результат госпитализации */
    private String medosHospResult ;

   /** Тип коек*/
   @Comment("Тип коек")
   @Persist
   public String getBedSubType() {return bedSubType;}
   /** Тип коек */
   private String bedSubType ;



}
