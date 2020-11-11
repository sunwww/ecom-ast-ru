package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocCT;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalizationResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNoMaybe;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
public class Covid19 extends BaseEntity {
    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return thePatient;}
    public void setPatient(Patient aPatient) {thePatient = aPatient;}
    private Patient thePatient ;

    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    private MedCase theMedCase ;

    /** Номер ИБ */
    @Comment("Номер ИБ")
    public String getCardNumber() {return theCardNumber;}
    public void setCardNumber(String aCardNumber) {theCardNumber = aCardNumber;}
    private String theCardNumber ;

    /** Место работы, должность */
    @Comment("Место работы, должность")
    public String getWorkPlace() {return theWorkPlace;}
    public void setWorkPlace(String aWorkPlace) {theWorkPlace = aWorkPlace;}
    private String theWorkPlace ;
    
    /** Дата появления клинических симптомов */
    @Comment("Дата появления клинических симптомов")
    public Date getSymptomsDate() {return theSymptomsDate;}
    public void setSymptomsDate(Date aSymptomsDate) {theSymptomsDate = aSymptomsDate;}
    private Date theSymptomsDate ;

   /** Диагноз */
   @Comment("Диагноз")
   public String getDiagnosis() {return theDiagnosis;}
   public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}
   private String theDiagnosis ;

   /** Дата постановки диагноза */
   @Comment("Дата постановки диагноза")
   public Date getDiagnosisDate() {return theDiagnosisDate;}
   public void setDiagnosisDate(Date aDiagnosisDate) {theDiagnosisDate = aDiagnosisDate;}
   private Date theDiagnosisDate ;

   /** Дата исследования на COVID */
   @Comment("Дата исследования на COVID")
   public Date getCovidResearchDate() {return theCovidResearchDate;}
   public void setCovidResearchDate(Date aCovidResearchDate) {theCovidResearchDate = aCovidResearchDate;}
   private Date theCovidResearchDate ;

   /** МО, где проводилось ЛИ */
   @Comment("МО, где проводилось ЛИ")
   public String getLabOrganization() {return theLabOrganization;}
   public void setLabOrganization(String aLabOrganization) {theLabOrganization = aLabOrganization;}
   private String theLabOrganization ;

   /** Результаты лабораторных исследования */
   @Comment("Результаты лабораторных исследования")
   public String getLabResult() {return theLabResult;}
   public void setLabResult(String aLabResult) {theLabResult = aLabResult;}
   private String theLabResult ;

    /** Номер анализа */
    @Comment("Номер анализа")
    public String getLabResultNumber() {return theLabResultNumber;}
    public void setLabResultNumber(String aLabResultNumber) {theLabResultNumber = aLabResultNumber;}
    private String theLabResultNumber ;

   /** Вакцинация пневмококком */
   @Comment("Вакцинация пневмококком")
   @OneToOne
   public VocYesNoMaybe getVacPnKok() {return theVacPnKok;}
   public void setVacPnKok(VocYesNoMaybe aVacPnKok) {theVacPnKok = aVacPnKok;}
   private VocYesNoMaybe theVacPnKok ;

   /** Вакцинация от гриппа */
   @Comment("Вакцинация от гриппа")
   @OneToOne
   public VocYesNoMaybe getVacFlu() {return theVacFlu;}
   public void setVacFlu(VocYesNoMaybe aVacFlu) {theVacFlu = aVacFlu;}
   private VocYesNoMaybe theVacFlu ;
   
   /** Беременность */
   @Comment("Беременность")
   public Boolean getIsPregnant() {return theIsPregnant;}
   public void setIsPregnant(Boolean aIsPregnant) {theIsPregnant = aIsPregnant;}
   private Boolean theIsPregnant ;
   
   /** Противовирусное лечение */
   @Comment("Противовирусное лечение")
   @OneToOne
   public VocYesNoMaybe getIsAntivirus() {return theIsAntivirus;}
   public void setIsAntivirus(VocYesNoMaybe aIsAntivirus) {theIsAntivirus = aIsAntivirus;}
   private VocYesNoMaybe theIsAntivirus ;
   
   /** Респираторная поддержка */
   @Comment("Респираторная поддержка")
   @OneToOne
   public VocYesNoMaybe getIsIvl() {return theIsIvl;}
   public void setIsIvl(VocYesNoMaybe aIsIvl) {theIsIvl = aIsIvl;}
   private VocYesNoMaybe theIsIvl ;
   
   /** Хронические заболевания бронхолегочной системы */
   @Comment("Хронические заболевания бронхолегочной системы")
   public String getSoputBronho() {return theSoputBronho;}
   public void setSoputBronho(String aSoputBronho) {theSoputBronho = aSoputBronho;}
   private String theSoputBronho ;

   /** Хронические заболевания сердечно-сосудистой системы */
   @Comment("Хронические заболевания сердечно-сосудистой системы")
   public String getSoputHeart() {return theSoputHeart;}
   public void setSoputHeart(String aSoputHeart) {theSoputHeart = aSoputHeart;}
   private String theSoputHeart ;
   
   /** Хронические заболевания эндокринной системы */
   @Comment("Хронические заболевания эндокринной системы")
   public String getSoputEndo() {return theSoputEndo;}
   public void setSoputEndo(String aSoputEndo) {theSoputEndo = aSoputEndo;}
   private String theSoputEndo ;
   
   /** Онкологические заболевания */
   @Comment("Онкологические заболевания")
   public String getSoputOnko() {return theSoputOnko;}
   public void setSoputOnko(String aSoputOnko) {theSoputOnko = aSoputOnko;}
   private String theSoputOnko ;

   /** Болезнь, вызванная ВИЧ */
   @Comment("Болезнь, вызванная ВИЧ")
   public String getSoputSpid() {return theSoputSpid;}
   public void setSoputSpid(String aSoputSpid) {theSoputSpid = aSoputSpid;}
   private String theSoputSpid ;
   
   /** Туберкулез */
   @Comment("Туберкулез")
   public String getSoputTuber() {return theSoputTuber;}
   public void setSoputTuber(String aSoputTuber) {theSoputTuber = aSoputTuber;}
   private String theSoputTuber ;
   
   /** Иные  */
   @Comment("Иные ")
   public String getSoputOther() {return theSoputOther;}
   public void setSoputOther(String aSoputOther) {theSoputOther = aSoputOther;}
   private String theSoputOther ;

   /** Уровень SpO2(%)(на момент поступления) */
   @Comment("Уровень SpO2(%)(на момент поступления)")
   public String getSaturationLevel() {return theSaturationLevel;}
   public void setSaturationLevel(String aSaturationLevel) {theSaturationLevel = aSaturationLevel;}
   private String theSaturationLevel ;

   /** Контактные лица */
   @Comment("Контактные лица")
   @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
   public List<Covid19Contact> getContactList() {return theContactList;}
   public void setContactList(List<Covid19Contact> aContactList) {theContactList = aContactList;}
   private List<Covid19Contact> theContactList ;

   /** Дата создания */
   @Comment("Дата создания")
   public Date getCreateDate() {return theCreateDate;}
   public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
   private Date theCreateDate ;

   /** Время создания */
   @Comment("Время создания")
   public Time getCreateTime() {return theCreateTime;}
   public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
   private Time theCreateTime ;

   /** Создатель */
   @Comment("Создатель")
   public String getCreateUsername() {return theCreateUsername;}
   public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
   private String theCreateUsername ;

   /** Дата выгрузки на портал */
   @Comment("Дата выгрузки на портал")
   public Date getExportDate() {return theExportDate;}
   public void setExportDate(Date aExportDate) {theExportDate = aExportDate;}
   private Date theExportDate ;

   /** Время выгузки на портал */
   @Comment("Время выгузки на портал")
   public Time getExportTime() {return theExportTime;}
   public void setExportTime(Time aExportTime) {theExportTime = aExportTime;}
   private Time theExportTime ;

   /** Пользователь, выгузивший на портал */
   @Comment("Пользователь, выгузивший на портал")
   public String getExportUsername() {return theExportUsername;}
   public void setExportUsername(String aExportUsername) {theExportUsername = aExportUsername;}
   private String theExportUsername ;

    /** Карта заменена на новую */
    @Comment("Карта заменена на новую")
    public Boolean getNoActual() {return theNoActual;}
    public void setNoActual(Boolean aNoActual) {theNoActual = aNoActual;}
    private Boolean theNoActual ;

    /** Дата исхода */
    @Comment("Дата исхода")
    public Date getIshodDate() {return theIshodDate;}
    public void setIshodDate(Date aIshodDate) {theIshodDate = aIshodDate;}
    private Date theIshodDate ;

    /** Результат исхода */
    @Comment("Результат исхода")
    @OneToOne
    public VocHospitalizationResult getHospResult() {return theHospResult;}
    public void setHospResult(VocHospitalizationResult aHospResult) {theHospResult = aHospResult;}
    private VocHospitalizationResult theHospResult ;

    /** Диагноз */
    @Comment("Диагноз")
    @OneToOne
    public VocIdc10 getMkb() {return theMkb;}
    public void setMkb(VocIdc10 aMkb) {theMkb = aMkb;}
    private VocIdc10 theMkb ;
    
    /** Номер бригады СМП */
    @Comment("Номер бригады СМП")
    public String getBrigadeNumber() {return theBrigadeNumber;}
    public void setBrigadeNumber(String aBrigadeNumber) {theBrigadeNumber = aBrigadeNumber;}
    private String theBrigadeNumber ;

    /** Эпид. номер */
    @Comment("Эпид. номер")
    public String getEpidNumber() {return theEpidNumber;}
    public void setEpidNumber(String aEpidNumber) {theEpidNumber = aEpidNumber;}
    private String theEpidNumber ;

    /** Мед. работник */
    @Comment("Мед. работник")
    public Boolean getIsDoctor() {return theIsDoctor;}
    public void setIsDoctor(Boolean aIsDoctor) {theIsDoctor = aIsDoctor;}
    private Boolean theIsDoctor ;

    @PrePersist
    void onPrePersist() {
        long createTime = System.currentTimeMillis();
        setCreateDate(new Date(createTime));
        setCreateTime(new Time(createTime));
    }

    /** Дата первичной выгрузки */
    @Comment("Дата первичной выгрузки")
    public Date getExportFirstDate() {return theExportFirstDate;}
    public void setExportFirstDate(Date aExportFirstDate) {theExportFirstDate = aExportFirstDate;}
    /** Дата первичной выгрузки */
    private Date theExportFirstDate ;
    
    /** Время первичной выгрузки */
    @Comment("Время первичной выгрузки")
    public Time getExportFirstTime() {return theExportFirstTime;}
    public void setExportFirstTime(Time aExportFirstTime) {theExportFirstTime = aExportFirstTime;}
    /** Время первичной выгрузки */
    private Time theExportFirstTime ;

    /** Кто первичную выгрузил? */
    @Comment("Кто первичную выгрузил?")
    public String getExportFirstUsername() {return theExportFirstUsername;}
    public void setExportFirstUsername(String aExportFirstUsername) {theExportFirstUsername = aExportFirstUsername;}
    /** Кто первичную выгрузил? */
    private String theExportFirstUsername ;

    /** Дата повторной выгрузки */
    @Comment("Дата повторной выгрузки")
    public Date getExportDoubleDate() {return theExportDoubleDate;}
    public void setExportDoubleDate(Date aExportDoubleDate) {theExportDoubleDate = aExportDoubleDate;}
    /** Дата повторной выгрузки */
    private Date theExportDoubleDate ;

    /** Время повторной выгрузки */
    @Comment("Время повторной выгрузки")
    public Time getExportDoubleTime() {return theExportDoubleTime;}
    public void setExportDoubleTime(Time aExportDoubleTime) {theExportDoubleTime = aExportDoubleTime;}
    /** Время повторной выгрузки */
    private Time theExportDoubleTime ;

    /** Кто повторную выгрузил? */
    @Comment("Кто повторную выгрузил?")
    public String getExportDoubleUsername() {return theExportDoubleUsername;}
    public void setExportDoubleUsername(String aExportDoubleUsername) {theExportDoubleUsername = aExportDoubleUsername;}
    /** Кто повторную выгрузил? */
    private String theExportDoubleUsername ;

    /** Дата выписной выгрузки */
    @Comment("Дата выписной выгрузки")
    public Date getExportDischargeDate() {return theExportDischargeDate;}
    public void setExportDischargeDate(Date aExportDischargeDate) {theExportDischargeDate = aExportDischargeDate;}
    /** Дата выписной выгрузки */
    private Date theExportDischargeDate ;

    /** Время выписной выгрузки */
    @Comment("Время выписной выгрузки")
    public Time getExportDischargeTime() {return theExportDischargeTime;}
    public void setExportDischargeTime(Time aExportDischargeTime) {theExportDischargeTime = aExportDischargeTime;}
    /** Время выписной выгрузки */
    private Time theExportDischargeTime ;

    /** Кто при выписке выгрузил? */
    @Comment("Кто при выписке выгрузил?")
    public String getExportDischargeUsername() {return theExportDischargeUsername;}
    public void setExportDischargeUsername(String aExportDischargeUsername) {theExportDischargeUsername = aExportDischargeUsername;}
    /** Кто при выписке выгрузил? */
    private String theExportDischargeUsername ;

    /** Диагноз основной выписной */
    @Comment("Диагноз основной выписной")
    @OneToOne
    public VocIdc10 getMkbDischarge() {return theMkbDischarge;}
    public void setMkbDischarge(VocIdc10 aMkbDischarge) {theMkbDischarge = aMkbDischarge;}
    private VocIdc10 theMkbDischarge ;

    /** КТ */
    @Comment("КТ")
    @OneToOne
    public VocCT getCT() {return theCT;}
    public void setCT(VocCT aCT) {theCT = aCT;}
    private VocCT theCT ;

    /** Дата проведения КТ */
    @Comment("Дата проведения КТ")
    public Date getDateCT() {return theDateCT;}
    public void setDateCT(Date aDateCT) {theDateCT = aDateCT;}
    private Date theDateCT ;

    /** Место проведения КТ */
    @Comment("Место проведения КТ")
    public String getLpuCT() {return theLpuCT;}
    public void setLpuCT(String aLpuCT) {theLpuCT = aLpuCT;}
    /** Место проведения КТ */
    private String theLpuCT ;
}