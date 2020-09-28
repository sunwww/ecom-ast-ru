package ru.ecom.expert2.form;

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
public class E2CancerEntryForm extends IdEntityForm {

    /** Запись */
    @Comment("Запись")
    @Persist
    public Long getEntry() {return theEntry;}
    public void setEntry(Long aEntry) {theEntry = aEntry;}
    /** Запись */
    private Long theEntry ;

    /** Признак подозрения на на ЗО */
    @Comment("Признак подозрения на на ЗО")
    @Persist
    public Boolean getMaybeCancer() {return theMaybeCancer;}
    public void setMaybeCancer(Boolean aMaybeCancer) {theMaybeCancer = aMaybeCancer;}
    /** Признак подозрения на на ЗО */
    private Boolean theMaybeCancer ;

    /** Повод обращения */
    @Comment("Повод обращения")
    @Persist
    public String getOccasion() {return theOccasion;}
    public void setOccasion(String aOccasion) {theOccasion = aOccasion;}
    /** Повод обращения */
    private String theOccasion ;

    /** Стадия */
    @Comment("Стадия")
    @Persist
    public String getStage() {return theStage;}
    public void setStage(String aStage) {theStage = aStage;}
    /** Стадия */
    private String theStage ;

    /** Tumor */
    @Comment("Tumor")
    @Persist
    public String getTumor() {return theTumor;}
    public void setTumor(String aTumor) {theTumor = aTumor;}
    /** Tumor */
    private String theTumor ;

    /** Nodus */
    @Comment("Nodus")
    @Persist
    public String getNodus() {return theNodus;}
    public void setNodus(String aNodus) {theNodus = aNodus;}
    /** Nodus */
    private String theNodus ;

    /** Metastasis */
    @Comment("Metastasis")
    @Persist
    public String getMetastasis() {return theMetastasis;}
    public void setMetastasis(String aMetastasis) {theMetastasis = aMetastasis;}
    /** Metastasis */
    private String theMetastasis ;

    /** Выявлены отдаленные метастазы */
    @Comment("Выявлены отдаленные метастазы")
    @Persist
    public Boolean getIsMetastasisFound() {return theIsMetastasisFound;}
    public void setIsMetastasisFound(Boolean aIsMetastasisFound) {theIsMetastasisFound = aIsMetastasisFound;}
    /** Выявлены отдаленные метастазы */
    private Boolean theIsMetastasisFound ;

    /** Суммарная очаговая доза */
    @Comment("Суммарная очаговая доза")
    @Persist
    public String getSod() {return theSod;}
    public void setSod(String aSod) {theSod = aSod;}
    /** Суммарная очаговая доза */
    private String theSod ;

    /** Сведения о решении консилиума */
    @Comment("Сведения о решении консилиума")
    @Persist
    public String getConsiliumResult() {return theConsiliumResult;}
    public void setConsiliumResult(String aConsiliumResult) {theConsiliumResult = aConsiliumResult;}
    /** Сведения о решении консилиума */
    private String theConsiliumResult ;

    /** Дата проведения консилиума */
    @Comment("Дата проведения консилиума")
    @Persist
    @DateString @DoDateString
    public String getConsiliumDate() {return theConsiliumDate;}
    public void setConsiliumDate(String aConsiliumDate) {theConsiliumDate = aConsiliumDate;}
    /** Дата проведения консилиума */
    private String theConsiliumDate ;

    /** Тип услуги */
    @Comment("Тип услуги")
    @Persist
    public String getServiceType() {return theServiceType;}
    public void setServiceType(String aServiceType) {theServiceType = aServiceType;}
    /** Тип услуги */
    private String theServiceType ;

    /** Тип хирургического лечения */
    @Comment("Тип хирургического лечения")
    @Persist
    public String getSurgicalType() {return theSurgicalType;}
    public void setSurgicalType(String aSurgicalType) {theSurgicalType = aSurgicalType;}
    /** Тип хирургического лечения */
    private String theSurgicalType ;

    /** Линия лекарственной терапии */
    @Comment("Линия лекарственной терапии")
    @Persist
    public String getDrugLine() {return theDrugLine;}
    public void setDrugLine(String aDrugLine) {theDrugLine = aDrugLine;}
    /** Линия лекарственной терапии */
    private String theDrugLine ;

    /** Цикл лекарственной терапии */
    @Comment("Цикл лекарственной терапии")
    @Persist
    public String getDrugCycle() {return theDrugCycle;}
    public void setDrugCycle(String aDrugCycle) {theDrugCycle = aDrugCycle;}
    /** Цикл лекарственной терапии */
    private String theDrugCycle ;

    /** Тип лучевой терапии */
    @Comment("Тип лучевой терапии")
    @Persist
    public String getRadiationTherapy() {return theRadiationTherapy;}
    public void setRadiationTherapy(String aRadiationTherapy) {theRadiationTherapy = aRadiationTherapy;}
    /** Тип лучевой терапии */
    private String theRadiationTherapy ;

    //Поля чисто для формы

    //Направление
    @Comment("Дата направления")
    @DateString @DoDateString
    public String getDirectionDate() {return theDirectionDate;}
    public void setDirectionDate(String aDirectionDate) {theDirectionDate = aDirectionDate;}
    /** Дата направления */
    private String theDirectionDate ;

    /** Вид направление */
    @Comment("Вид направление")
    public String getDirectionType() {return theDirectionType;}
    public void setDirectionType(String aDirectionType) {theDirectionType = aDirectionType;}
    /** Вид направление */
    private String theDirectionType ;

    /** ЛПУ направления */
    @Comment("ЛПУ направления")
    public String getDirectionDirectLpu() {return theDirectionDirectLpu;}
    public void setDirectionDirectLpu(String aDirectionDirectLpu) {theDirectionDirectLpu = aDirectionDirectLpu;}
    /** ЛПУ направления */
    private String theDirectionDirectLpu ;

    /** Метод диагностического исследования */
    @Comment("Метод диагностического исследования")
    public String getDirectionSurveyMethod() {return theDirectionSurveyMethod;}
    public void setDirectionSurveyMethod(String aDirectionSurveyMethod) {theDirectionSurveyMethod = aDirectionSurveyMethod;}
    /** Метод диагностического исследования */
    private String theDirectionSurveyMethod ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    public String getDirectionMedService() {return theDirectionMedService;}
    public void setDirectionMedService(String aDirectionMedService) {theDirectionMedService = aDirectionMedService;}
    /** Медицинская услуга */
    private String theDirectionMedService ;

    //Противопоказания
    /** Код противопоказания */
    @Comment("Код противопоказания")
    public String getRefusalCode() {return theRefusalCode;}
    public void setRefusalCode(String aRefusalCode) {theRefusalCode = aRefusalCode;}
    /** Код противопоказания */
    private String theRefusalCode ;

    /** Дата регистрации противопоказания */
    @Comment("Дата регистрации противопоказания")
    @DateString @DoDateString
    public String getRefusalDate() {return theRefusalDate;}
    public void setRefusalDate(String aRefusalDate) {theRefusalDate = aRefusalDate;}
    /** Дата регистрации противопоказания */
    private String theRefusalDate ;

    //Диагностика
    /** Тип показателя */
    @Comment("Тип показателя")
    public String getDiagnosticType() {return theDiagnosticType;}
    public void setDiagnosticType(String aDiagnosticType) {theDiagnosticType = aDiagnosticType;}
    /** Тип показателя */
    private String theDiagnosticType ;

    /** Код показателя */
    @Comment("Код показателя")
    public String getDiagnosticCode() {return theDiagnosticCode;}
    public void setDiagnosticCode(String aDiagnosticCode) {theDiagnosticCode = aDiagnosticCode;}
    /** Код показателя */
    private String theDiagnosticCode ;

    /** Результат показателя */
    @Comment("Результат показателя")
    public String getDiagnosticResult() {return theDiagnosticResult;}
    public void setDiagnosticResult(String aDiagnosticResult) {theDiagnosticResult = aDiagnosticResult;}
    /** Результат показателя */
    private String theDiagnosticResult ;



}
