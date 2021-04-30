package ru.ecom.expert2.form;

import lombok.Setter;
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
@Setter
public class EntryDiagnosisForm extends IdEntityForm {


    /** Запись */
    private Long entry ;
    /** Характер заболевания */
    private String illnessPrimary ;
    /** Диагноз */
    private Long mkb ;
    /** Тип регистрации */
    private Long registrationType ;
    /** Приоритет */
    private Long priority ;
    /** Доп. код МКБ */
    private String dopMkb ;
    /** Справочник характеров заболевания */
    private Long vocIllnessPrimary ;

    @Comment("Запись")
    @Persist
    public Long getEntry() {return entry;}

    @Comment("Диагноз")
    @Persist
    public Long getMkb() {return mkb;}

    @Comment("Тип регистрации")
    @Persist
    public Long getRegistrationType() {return registrationType;}

    @Comment("Приоритет")
    @Persist
    public Long getPriority() {return priority;}

    @Comment("Доп. код МКБ")
    @Persist
    public String getDopMkb() {return dopMkb;}

    @Comment("Характер заболевания")
    @Persist
    public String getIllnessPrimary() {return illnessPrimary;}

    /** Справочник характеров заболевания */
    @Comment("Справочник характеров заболевания")
    @Persist
    public Long getVocIllnessPrimary() {return vocIllnessPrimary;}






}
