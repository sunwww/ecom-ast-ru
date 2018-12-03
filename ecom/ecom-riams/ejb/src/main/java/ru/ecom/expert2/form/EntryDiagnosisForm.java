package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.EntryDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = EntryDiagnosis.class)
@Comment("Диагноз по записи")
@WebTrail(comment = "Диагноз по записи", nameProperties = "id", view = "entityParentView-e2_entryDiagnosis.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "entry", parentForm = E2EntryForm.class)
public class EntryDiagnosisForm extends IdEntityForm {


    /** Запись */
    private Long theEntry ;
    /** Характер заболевания */
    private String theIllnessPrimary ;
    /** Диагноз */
    private Long theMkb ;
    /** Тип регистрации */
    private Long theRegistrationType ;
    /** Приоритет */
    private Long thePriority ;
    /** Доп. код МКБ */
    private String theDopMkb ;
    /** Справочник характеров заболевания */
    private Long theVocIllnessPrimary ;

    @Comment("Запись")
    @Persist
    public Long getEntry() {return theEntry;}
    public void setEntry(Long aEntry) {theEntry = aEntry;}

    @Comment("Диагноз")
    @Persist
    public Long getMkb() {return theMkb;}
    public void setMkb(Long aMkb) {theMkb = aMkb;}

    @Comment("Тип регистрации")
    @Persist
    public Long getRegistrationType() {return theRegistrationType;}
    public void setRegistrationType(Long aRegistrationType) {theRegistrationType = aRegistrationType;}

    @Comment("Приоритет")
    @Persist
    public Long getPriority() {return thePriority;}
    public void setPriority(Long aPriority) {thePriority = aPriority;}

    @Comment("Доп. код МКБ")
    @Persist
    public String getDopMkb() {return theDopMkb;}
    public void setDopMkb(String aDopMkb) {theDopMkb = aDopMkb;}

    @Comment("Характер заболевания")
    @Persist
    public String getIllnessPrimary() {return theIllnessPrimary;}
    public void setIllnessPrimary(String aIllnessPrimary) {theIllnessPrimary = aIllnessPrimary;}

    /** Справочник характеров заболевания */
    @Comment("Справочник характеров заболевания")
    @Persist
    public Long getVocIllnessPrimary() {return theVocIllnessPrimary;}
    public void setVocIllnessPrimary(Long aVocIllnessPrimary) {theVocIllnessPrimary = aVocIllnessPrimary;}






}
