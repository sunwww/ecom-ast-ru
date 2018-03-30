package ru.ecom.expert2.form;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2FondMedSpecLink;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV015;
import ru.ecom.expert2.form.voc.federal.VocE2FondV015Form;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие исхода случая результату медоса (V015)
 */
@EntityForm
@EntityFormPersistance(clazz = E2FondMedSpecLink.class)
@Comment("Случай в заполнении")
@WebTrail(comment = "Запись в заполнении", nameProperties = "id", view = "entityParentView-e2_v015Link.do")
@Parent(property = "medSpec", parentForm = VocE2FondV015Form.class)
@EntityFormSecurityPrefix("/Policy/E2")
public class E2FondMedSpecLinkForm extends IdEntityForm {

     /** Медицинская специальность  */
     @Comment("Медицинская специальность")
     @Persist
     public Long getMedSpec() {return theMedSpec;}
     public void setMedSpec(Long aMedSpec) {theMedSpec = aMedSpec;}
     /** Медицинская специальность  */
    private Long theMedSpec ;

 /** Рабочая функция специалиста */
    @Comment("Рабочая функция специалиста(VWF_CODE)")
    @Persist
    public String getMedosWorkFunction() {return theMedosWorkFunction;}
    public void setMedosWorkFunction(String aMedosWorkFunction) {theMedosWorkFunction = aMedosWorkFunction;}
    /** Рабочая функция специалиста */
    private String theMedosWorkFunction ;

}
