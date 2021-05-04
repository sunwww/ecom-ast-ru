package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerEntry;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Случай онкологического лечения
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerEntry.class)
@Comment("Случай онкологического лечения")
@WebTrail(comment = "Случай онкологического лечения", nameProperties = "id", view = "entityParentView-e2_cancerEntry.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "entry", parentForm = E2EntryForm.class)
@Setter
public class E2CancerEntryForm extends IdEntityForm {

    /** Запись */
    @Comment("Запись")
    @Persist
    public Long getEntry() {return entry;}
    /** Запись */
    private Long entry ;

    /** Признак подозрения на на ЗО */
    @Comment("Признак подозрения на на ЗО")
    @Persist
    public Boolean getMaybeCancer() {return maybeCancer;}
    /** Признак подозрения на на ЗО */
    private Boolean maybeCancer ;

    /** Повод обращения */
    @Comment("Повод обращения")
    @Persist
    public String getOccasion() {return occasion;}
    /** Повод обращения */
    private String occasion ;

    /** Стадия */
    @Comment("Стадия")
    @Persist
    public String getStage() {return stage;}
    /** Стадия */
    private String stage ;

    /** Tumor */
    @Comment("Tumor")
    @Persist
    public String getTumor() {return tumor;}
    /** Tumor */
    private String tumor ;

    /** Nodus */
    @Comment("Nodus")
    @Persist
    public String getNodus() {return nodus;}
    /** Nodus */
    private String nodus ;

    /** Metastasis */
    @Comment("Metastasis")
    @Persist
    public String getMetastasis() {return metastasis;}
    /** Metastasis */
    private String metastasis ;

    /** Выявлены отдаленные метастазы */
    @Comment("Выявлены отдаленные метастазы")
    @Persist
    public Boolean getIsMetastasisFound() {return isMetastasisFound;}
    /** Выявлены отдаленные метастазы */
    private Boolean isMetastasisFound ;

    /** Суммарная очаговая доза */
    @Comment("Суммарная очаговая доза")
    @Persist
    public String getSod() {return sod;}
    /** Суммарная очаговая доза */
    private String sod ;

    /** Сведения о решении консилиума */
    @Comment("Сведения о решении консилиума")
    @Persist
    public String getConsiliumResult() {return consiliumResult;}
    /** Сведения о решении консилиума */
    private String consiliumResult ;

    /** Дата проведения консилиума */
    @Comment("Дата проведения консилиума")
    @Persist
    @DateString @DoDateString
    public String getConsiliumDate() {return consiliumDate;}
    /** Дата проведения консилиума */
    private String consiliumDate ;

    /** Тип услуги */
    @Comment("Тип услуги")
    @Persist
    public String getServiceType() {return serviceType;}
    /** Тип услуги */
    private String serviceType ;

    /** Тип хирургического лечения */
    @Comment("Тип хирургического лечения")
    @Persist
    public String getSurgicalType() {return surgicalType;}
    /** Тип хирургического лечения */
    private String surgicalType ;

    /** Линия лекарственной терапии */
    @Comment("Линия лекарственной терапии")
    @Persist
    public String getDrugLine() {return drugLine;}
    /** Линия лекарственной терапии */
    private String drugLine ;

    /** Цикл лекарственной терапии */
    @Comment("Цикл лекарственной терапии")
    @Persist
    public String getDrugCycle() {return drugCycle;}
    /** Цикл лекарственной терапии */
    private String drugCycle ;

    /** Тип лучевой терапии */
    @Comment("Тип лучевой терапии")
    @Persist
    public String getRadiationTherapy() {return radiationTherapy;}
    /** Тип лучевой терапии */
    private String radiationTherapy ;

    //Поля чисто для формы

    //Направление
    @Comment("Дата направления")
    @DateString @DoDateString
    public String getDirectionDate() {return directionDate;}
    /** Дата направления */
    private String directionDate ;

    /** Вид направление */
    @Comment("Вид направление")
    public String getDirectionType() {return directionType;}
    /** Вид направление */
    private String directionType ;

    /** ЛПУ направления */
    @Comment("ЛПУ направления")
    public String getDirectionDirectLpu() {return directionDirectLpu;}
    /** ЛПУ направления */
    private String directionDirectLpu ;

    /** Метод диагностического исследования */
    @Comment("Метод диагностического исследования")
    public String getDirectionSurveyMethod() {return directionSurveyMethod;}
    /** Метод диагностического исследования */
    private String directionSurveyMethod ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    public String getDirectionMedService() {return directionMedService;}
    /** Медицинская услуга */
    private String directionMedService ;

    //Противопоказания
    /** Код противопоказания */
    @Comment("Код противопоказания")
    public String getRefusalCode() {return refusalCode;}
    /** Код противопоказания */
    private String refusalCode ;

    /** Дата регистрации противопоказания */
    @Comment("Дата регистрации противопоказания")
    @DateString @DoDateString
    public String getRefusalDate() {return refusalDate;}
    /** Дата регистрации противопоказания */
    private String refusalDate ;

    //Диагностика
    /** Тип показателя */
    @Comment("Тип показателя")
    public String getDiagnosticType() {return diagnosticType;}
    /** Тип показателя */
    private String diagnosticType ;

    /** Код показателя */
    @Comment("Код показателя")
    public String getDiagnosticCode() {return diagnosticCode;}
    /** Код показателя */
    private String diagnosticCode ;

    /** Результат показателя */
    @Comment("Результат показателя")
    public String getDiagnosticResult() {return diagnosticResult;}
    /** Результат показателя */
    private String diagnosticResult ;



}
