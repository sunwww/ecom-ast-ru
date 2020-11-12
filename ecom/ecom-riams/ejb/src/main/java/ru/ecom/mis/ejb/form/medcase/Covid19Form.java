package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Covid19;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = Covid19.class)
@Comment("Карта коронавируса")
@WebTrail(comment = "Карта коронавируса", list = "entityParentList-smo_covid19.do"
        , nameProperties = "cardNumber", view = "entityParentView-smo_covid19.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Covid19")
@Parent(parentForm = HospitalMedCaseForm.class,  property = "medCase")
public class Covid19Form extends IdEntityForm {
    /** Пациент */
    @Comment("Пациент")
    @Persist @Required
    public Long getPatient() {return thePatient;}
    public void setPatient(Long aPatient) {thePatient = aPatient;}
    private Long thePatient ;

    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    private Long theMedCase ;

    /** Номер ИБ */
    @Comment("Номер ИБ")
    @Persist
    public String getCardNumber() {return theCardNumber;}
    public void setCardNumber(String aCardNumber) {theCardNumber = aCardNumber;}
    private String theCardNumber ;

    /** Дата появления клинических симптомов */
    @Comment("Дата появления клинических симптомов")
    @Persist
    @DateString @DoDateString
    @Required
    public String getSymptomsDate() {return theSymptomsDate;}
    public void setSymptomsDate(String aSymptomsDate) {theSymptomsDate = aSymptomsDate;}
    private String theSymptomsDate ;

    /** Дата постановки диагноза */
    @Comment("Дата постановки диагноза")
    @Persist
    @DateString @DoDateString
    public String getDiagnosisDate() {return theDiagnosisDate;}
    public void setDiagnosisDate(String aDiagnosisDate) {theDiagnosisDate = aDiagnosisDate;}
    private String theDiagnosisDate ;

    /** Дата исследования на COVID */
    @Comment("Дата исследования на COVID")
    @Persist
    @DateString @DoDateString
    public String getCovidResearchDate() {return theCovidResearchDate;}
    public void setCovidResearchDate(String aCovidResearchDate) {theCovidResearchDate = aCovidResearchDate;}
    private String theCovidResearchDate ;

    /** МО, где проводилось ЛИ */
    @Comment("МО, где проводилось ЛИ")
    @Persist
    public String getLabOrganization() {return theLabOrganization;}
    public void setLabOrganization(String aLabOrganization) {theLabOrganization = aLabOrganization;}
    private String theLabOrganization ;

    /** Результаты лабораторных исследования */
    @Comment("Результаты лабораторных исследования")
    @Persist
    public String getLabResult() {return theLabResult;}
    public void setLabResult(String aLabResult) {theLabResult = aLabResult;}
    private String theLabResult ;

    /** Номер анализа */
    @Comment("Номер анализа")
    @Persist
    public String getLabResultNumber() {return theLabResultNumber;}
    public void setLabResultNumber(String aLabResultNumber) {theLabResultNumber = aLabResultNumber;}
    private String theLabResultNumber ;

    /** Вакцинация пневмококком */
    @Comment("Вакцинация пневмококком")
    @Persist
    public Long getVacPnKok() {return theVacPnKok;}
    public void setVacPnKok(Long aVacPnKok) {theVacPnKok = aVacPnKok;}
    private Long theVacPnKok ;

    /** Вакцинация от гриппа */
    @Comment("Вакцинация от гриппа")
    @Persist
    public Long getVacFlu() {return theVacFlu;}
    public void setVacFlu(Long aVacFlu) {theVacFlu = aVacFlu;}
    private Long theVacFlu ;

    /** Беременность */
    @Comment("Беременность")
    @Persist
    public Boolean getIsPregnant() {return theIsPregnant;}
    public void setIsPregnant(Boolean aIsPregnant) {theIsPregnant = aIsPregnant;}
    private Boolean theIsPregnant ;

    /** Противовирусное лечение */
    @Comment("Противовирусное лечение")
    @Persist
    public Long getIsAntivirus() {return theIsAntivirus;}
    public void setIsAntivirus(Long aIsAntivirus) {theIsAntivirus = aIsAntivirus;}
    private Long theIsAntivirus ;

    /** Респираторная поддержка */
    @Comment("Респираторная поддержка")
    @Persist
    public Long getIsIvl() {return theIsIvl;}
    public void setIsIvl(Long aIsIvl) {theIsIvl = aIsIvl;}
    private Long theIsIvl ;

    /** Хронические заболевания бронхолегочной системы */
    @Comment("Хронические заболевания бронхолегочной системы")
    @Persist
    public String getSoputBronho() {return theSoputBronho;}
    public void setSoputBronho(String aSoputBronho) {theSoputBronho = aSoputBronho;}
    private String theSoputBronho ;

    /** Хронические заболевания сердечно-сосудистой системы */
    @Comment("Хронические заболевания сердечно-сосудистой системы")
    @Persist
    public String getSoputHeart() {return theSoputHeart;}
    public void setSoputHeart(String aSoputHeart) {theSoputHeart = aSoputHeart;}
    private String theSoputHeart ;

    /** Хронические заболевания эндокринной системы */
    @Comment("Хронические заболевания эндокринной системы")
    @Persist
    public String getSoputEndo() {return theSoputEndo;}
    public void setSoputEndo(String aSoputEndo) {theSoputEndo = aSoputEndo;}
    private String theSoputEndo ;

    /** Онкологические заболевания */
    @Comment("Онкологические заболевания")
    @Persist
    public String getSoputOnko() {return theSoputOnko;}
    public void setSoputOnko(String aSoputOnko) {theSoputOnko = aSoputOnko;}
    private String theSoputOnko ;

    /** Болезнь, вызванная ВИЧ */
    @Comment("Болезнь, вызванная ВИЧ")
    @Persist
    public String getSoputSpid() {return theSoputSpid;}
    public void setSoputSpid(String aSoputSpid) {theSoputSpid = aSoputSpid;}
    private String theSoputSpid ;

    /** Туберкулез */
    @Comment("Туберкулез")
    @Persist
    public String getSoputTuber() {return theSoputTuber;}
    public void setSoputTuber(String aSoputTuber) {theSoputTuber = aSoputTuber;}
    private String theSoputTuber ;

    /** Иные  */
    @Comment("Иные ")
    @Persist
    public String getSoputOther() {return theSoputOther;}
    public void setSoputOther(String aSoputOther) {theSoputOther = aSoputOther;}
    private String theSoputOther ;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist
    @DateString @DoDateString
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    private String theCreateDate ;

    /** Время создания */
    @Comment("Время создания")
    @Persist
    @TimeString @DoTimeString
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    private String theCreateTime ;

    /** Создатель */
    @Comment("Создатель")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    private String theCreateUsername ;

    /** Дата выгрузки на портал */
    @Comment("Дата выгрузки на портал")
    @Persist
    @DateString @DoDateString
    public String getExportDate() {return theExportDate;}
    public void setExportDate(String aExportDate) {theExportDate = aExportDate;}
    private String theExportDate ;

    /** Время выгузки на портал */
    @Comment("Время выгузки на портал")
    @Persist
    @TimeString @DoTimeString
    public String getExportTime() {return theExportTime;}
    public void setExportTime(String aExportTime) {theExportTime = aExportTime;}
    private String theExportTime ;

    /** Пользователь, выгузивший на портал */
    @Comment("Пользователь, выгузивший на портал")
    @Persist
    public String getExportUsername() {return theExportUsername;}
    public void setExportUsername(String aExportUsername) {theExportUsername = aExportUsername;}
    private String theExportUsername ;

    /** Карта заменена на новую */
    @Comment("Карта заменена на новую")
    @Persist
    public Boolean getNoActual() {return theNoActual;}
    public void setNoActual(Boolean aNoActual) {theNoActual = aNoActual;}
    private Boolean theNoActual ;

    /** Дата исхода */
    @Comment("Дата исхода")
    @Persist
    @DateString @DoDateString
    public String getIshodDate() {return theIshodDate;}
    public void setIshodDate(String aIshodDate) {theIshodDate = aIshodDate;}
    private String theIshodDate ;

    /** Результат исхода */
    @Comment("Результат исхода")
    @Persist
    public Long getHospResult() {return theHospResult;}
    public void setHospResult(Long aHospResult) {theHospResult = aHospResult;}
    private Long theHospResult ;

    /** Место работы, должность */
    @Comment("Место работы, должность")
    @Persist
    @Required
    public String getWorkPlace() {return theWorkPlace;}
    public void setWorkPlace(String aWorkPlace) {theWorkPlace = aWorkPlace;}
    private String theWorkPlace ;

    /** Диагноз */
    @Comment("Диагноз")
    @Persist
    @Required
    public Long getMkb() {return theMkb;}
    public void setMkb(Long aMkb) {theMkb = aMkb;}
    private Long theMkb ;

    /** Номер бригады СМП */
    @Comment("Номер бригады СМП")
    @Persist
    public String getBrigadeNumber() {return theBrigadeNumber;}
    public void setBrigadeNumber(String aBrigadeNumber) {theBrigadeNumber = aBrigadeNumber;}
    private String theBrigadeNumber ;

    /** Эпид. номер */
    @Comment("Эпид. номер")
    @Persist
    public String getEpidNumber() {return theEpidNumber;}
    public void setEpidNumber(String aEpidNumber) {theEpidNumber = aEpidNumber;}
    private String theEpidNumber ;
    
    /** Форма контакта */
    @Comment("Форма контакта")
    public Covid19ContactForm getContactForm() {return theContactForm;}
    public void setContactForm(Covid19ContactForm aContactForm) {theContactForm = aContactForm;}
    private Covid19ContactForm theContactForm = new Covid19ContactForm() ;

    /** Мед. работник */
    @Comment("Мед. работник")
    @Persist
    public Boolean getIsDoctor() {return theIsDoctor;}
    public void setIsDoctor(Boolean aIsDoctor) {theIsDoctor = aIsDoctor;}
    private Boolean theIsDoctor ;

    /** Дата первичной выгрузки */
    @Comment("Дата первичной выгрузки")
    @DateString @DoDateString
    @Persist
    public String getExportFirstDate() {return theExportFirstDate;}
    public void setExportFirstDate(String aExportFirstDate) {theExportFirstDate = aExportFirstDate;}
    /** Дата первичной выгрузки */
    private String theExportFirstDate ;

    /** Время первичной выгрузки */
    @Comment("Время первичной выгрузки")
    @TimeString @DoTimeString
    @Persist
    public String getExportFirstTime() {return theExportFirstTime;}
    public void setExportFirstTime(String aExportFirstTime) {theExportFirstTime = aExportFirstTime;}
    /** Время первичной выгрузки */
    private String theExportFirstTime ;

    /** Кто первичную выгрузил? */
    @Comment("Кто первичную выгрузил?")
    @Persist
    public String getExportFirstUsername() {return theExportFirstUsername;}
    public void setExportFirstUsername(String aExportFirstUsername) {theExportFirstUsername = aExportFirstUsername;}
    /** Кто первичную выгрузил? */
    private String theExportFirstUsername ;

    /** Дата повторной выгрузки */
    @Comment("Дата повторной выгрузки")
    @DateString @DoDateString
    @Persist
    public String getExportDoubleDate() {return theExportDoubleDate;}
    public void setExportDoubleDate(String aExportDoubleDate) {theExportDoubleDate = aExportDoubleDate;}
    /** Дата повторной выгрузки */
    private String theExportDoubleDate ;

    /** Время повторной выгрузки */
    @Comment("Время повторной выгрузки")
    @TimeString @DoTimeString
    @Persist
    public String getExportDoubleTime() {return theExportDoubleTime;}
    public void setExportDoubleTime(String aExportDoubleTime) {theExportDoubleTime = aExportDoubleTime;}
    /** Время повторной выгрузки */
    private String theExportDoubleTime ;

    /** Кто повторную выгрузил? */
    @Comment("Кто повторную выгрузил?")
    @Persist
    public String getExportDoubleUsername() {return theExportDoubleUsername;}
    public void setExportDoubleUsername(String aExportDoubleUsername) {theExportDoubleUsername = aExportDoubleUsername;}
    /** Кто повторную выгрузил? */
    private String theExportDoubleUsername ;

    /** Дата выписной выгрузки */
    @Comment("Дата выписной выгрузки")
    @DateString @DoDateString
    @Persist
    public String getExportDischargeDate() {return theExportDischargeDate;}
    public void setExportDischargeDate(String aExportDischargeDate) {theExportDischargeDate = aExportDischargeDate;}
    /** Дата выписной выгрузки */
    private String theExportDischargeDate ;

    /** Время выписной выгрузки */
    @Comment("Время выписной выгрузки")
    @TimeString @DoTimeString
    @Persist
    public String getExportDischargeTime() {return theExportDischargeTime;}
    public void setExportDischargeTime(String aExportDischargeTime) {theExportDischargeTime = aExportDischargeTime;}
    /** Время выписной выгрузки */
    private String theExportDischargeTime ;

    /** Кто при выписке выгрузил? */
    @Comment("Кто при выписке выгрузил?")
    @Persist
    public String getExportDischargeUsername() {return theExportDischargeUsername;}
    public void setExportDischargeUsername(String aExportDischargeUsername) {theExportDischargeUsername = aExportDischargeUsername;}
    /** Кто при выписке выгрузил? */
    private String theExportDischargeUsername ;

    /** Диагноз основной выписной */
    @Comment("Диагноз основной выписной")
    @Persist
    public Long getMkbDischarge() {return theMkbDischarge;}
    public void setMkbDischarge(Long aMkbDischarge) {theMkbDischarge = aMkbDischarge;}
    private Long theMkbDischarge ;

    /** Уровень SpO2(%)(на момент поступления) */
    @Comment("Уровень SpO2(%)(на момент поступления)")
    @Persist
    public String getSaturationLevel() {return theSaturationLevel;}
    public void setSaturationLevel(String aSaturationLevel) {theSaturationLevel = aSaturationLevel;}
    private String theSaturationLevel ;

    /** КТ */
    @Comment("КТ")
    @Persist
    public Long getCT() {return theCT;}
    public void setCT(Long aCT) {theCT = aCT;}
    private Long theCT ;

    /** Дата проведения КТ */
    @Comment("Дата проведения КТ")
    @DateString @DoDateString
    @Persist
    public String getDateCT() {return theDateCT;}
    public void setDateCT(String aDateCT) {theDateCT = aDateCT;}
    private String theDateCT ;

    /** Место проведения КТ */
    @Comment("Место проведения КТ")
    @Persist
    public String getLpuCT() {return theLpuCT;}
    public void setLpuCT(String aLpuCT) {theLpuCT = aLpuCT;}
    /** Место проведения КТ */
    private String theLpuCT ;
}
