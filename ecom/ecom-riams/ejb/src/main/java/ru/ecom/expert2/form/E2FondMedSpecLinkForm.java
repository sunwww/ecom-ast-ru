package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2FondMedSpecLink;
import ru.ecom.expert2.form.voc.federal.VocE2FondV021Form;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Соответствие профиля специальности и врачу медоса (V015)
 */
@EntityForm
@EntityFormPersistance(clazz = E2FondMedSpecLink.class)
@Comment("Случай в заполнении")
@WebTrail(comment = "Запись в заполнении", nameProperties = "id", view = "entityParentView-e2_v015Link.do")
@Parent(property = "speciality", parentForm = VocE2FondV021Form.class)
@EntityFormSecurityPrefix("/Policy/E2")
public class E2FondMedSpecLinkForm extends IdEntityForm {

     /** Медицинская специальность  */
     @Comment("Медицинская специальность")
     @Persist
     public Long getSpeciality() {return theSpeciality;}
     public void setSpeciality(Long aSpeciality) {theSpeciality = aSpeciality;}
     /** Медицинская специальность  */
    private Long theSpeciality ;

 /** Рабочая функция специалиста */
    @Comment("Рабочая функция специалиста(VWF_CODE)")
    @Persist
    public String getMedosWorkFunction() {return theMedosWorkFunction;}
    public void setMedosWorkFunction(String aMedosWorkFunction) {theMedosWorkFunction = aMedosWorkFunction;}
    /** Рабочая функция специалиста */
    private String theMedosWorkFunction ;

}
