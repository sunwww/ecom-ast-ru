package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerDirection;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerDirection.class)
@Comment("Направление на онк. лечение")
@WebTrail(comment = "Направление на онк. лечение", nameProperties = "id", view = "entityView-e2_cancerDirection.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "cancerEntry", parentForm = E2CancerEntryForm.class)
public class E2CancerDirectionForm extends IdEntityForm {

    /** ЛПУ, куда сделали направление */
    @Comment("ЛПУ, куда сделали направление")
    @Persist
    public String getDirectLpu() {return theDirectLpu;}
    public void setDirectLpu(String aDirectLpu) {theDirectLpu = aDirectLpu;}
    /** ЛПУ, куда сделали направление */
    private String theDirectLpu ;

    /** Случай рака */
    @Comment("Случай рака")
    @Persist
    public Long getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(Long aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Случай рака */
    private Long theCancerEntry ;

    /** Дата направления */
    @Comment("Дата направления")
    @Persist
    @DateString @DoDateString
    public String getDate() {return theDate;}
    public void setDate(String aDate) {theDate = aDate;}
    /** Дата направления */
    private String theDate ;

    /** Вид направление */
    @Comment("Вид направление")
    @Persist
    public String getType() {return theType;}
    public void setType(String aType) {theType = aType;}
    /** Вид направление */
    private String theType ;

    /** Метод диагностического исследования */
    @Comment("Метод диагностического исследования")
    @Persist
    public String getSurveyMethod() {return theSurveyMethod;}
    public void setSurveyMethod(String aSurveyMethod) {theSurveyMethod = aSurveyMethod;}
    /** Метод диагностического исследования */
    private String theSurveyMethod ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    @Persist
    public String getMedService() {return theMedService;}
    public void setMedService(String aMedService) {theMedService = aMedService;}
    /** Медицинская услуга */
    private String theMedService ;
}
