package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.form.voc.federal.VocE2FondV010Form;
import ru.ecom.expert2.form.voc.federal.VocE2FondV015Form;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Соответствие исхода случая результату медоса (V015)
 */
//@EntityForm
//@EntityFormPersistance(clazz = E2FondPayTypeLink.class)
//@Comment("Случай в заполнении")
//@WebTrail(comment = "Запись в заполнении", nameProperties = "id", view = "entityParentView-e2_v015Link.do")
//@Parent(property = "PayType", parentForm = VocE2FondV010Form.class)
//@EntityFormSecurityPrefix("/Policy/E2")
public class E2FondPayTypeLinkForm { //extends IdEntityForm {

     /** Медицинская специальность  */
     @Comment("Медицинская специальность")
     public Long getPayType() {return thePayType;}
     public void setPayType(Long aPayType) {thePayType = aPayType;}
     /** Медицинская специальность  */
    private Long thePayType ;

 /** Рабочая функция специалиста */
    @Comment("Рабочая функция специалиста(VWF_CODE)")
    public String getMedosWorkFunction() {return theMedosWorkFunction;}
    public void setMedosWorkFunction(String aMedosWorkFunction) {theMedosWorkFunction = aMedosWorkFunction;}
    /** Рабочая функция специалиста */
    private String theMedosWorkFunction ;

}
