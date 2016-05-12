package ru.ecom.expomc.ejb.domain.registry;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.expomc.ejb.domain.impdoc.ADomain;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcAs;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcDepType;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcExpert;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKl;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcMkb10;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcOrg;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcOsl;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcPrvd;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcQz;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcRayon;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcResG;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSex;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSgroup;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSk;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcStreetType;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcTariff;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcVidLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class LpuFond extends NoLiveBaseEntity {
    @Transient
    public Date getActualDate() {
        if(theDischargeDate!=null) return theDischargeDate ;
        if(theAdmissionDate!=null) return theAdmissionDate ;
        if(theTransferDate!=null) return theTransferDate ;
        return null ;
    }

//    /** Реестр */
//    @ManyToOne
//    public Registry getRegistry() { return theRegistry ; }
//    public void setRegistry(Registry aRegistry) { theRegistry = aRegistry ; }

    /** Период */
//    @ManyToOne
//    public RegPeriod getPeriod() { return thePeriod ; }
//    public void setPeriod(RegPeriod aPeriod) { thePeriod = aPeriod ; }
//
//    /** Период */
//    private RegPeriod thePeriod ;


    /** Код ЛПУ */
    @Comment("Код ЛПУ")
    @AFormatFieldSuggest("KOD_LPU")
    @ADomain(clazz= OmcLpu.class)
    public String getKodLpu() { return theKodLpu ; }
    public void setKodLpu(String aKodLpu) { theKodLpu = aKodLpu ; }

    /** Вид ЛПУ */
    @Comment("Вид ЛПУ")
    @AFormatFieldSuggest("VID_LPU")
    @ADomain(clazz= OmcVidLpu.class)
    public String getVidLpu() { return theVidLpu ; }
    public void setVidLpu(String aVidLpu) { theVidLpu = aVidLpu ; }

    /** Тип страхования */
    @Comment("Тип страхования")
    public String getInsureType() { return theInsureType ; }
    public void setInsureType(String aInsureType) { theInsureType = aInsureType ; }

    /** Код социальной группы */
    @Comment("Код социальной группы")
    @AFormatFieldSuggest("SGROUP")
    @ADomain(clazz= OmcSgroup.class)
    public String getSgroup() { return theSgroup ; }
    public void setSgroup(String aSgroup) { theSgroup = aSgroup ; }

    /** Серия полиса */
    @Comment("Серия полиса")
    @AFormatFieldSuggest("S_POLIS")
    public String getSPolis() { return theSPolis ; }
    public void setSPolis(String aSPolis) { theSPolis = aSPolis ; }

    /** Номер полиса */
    @Comment("Номер полиса")
    @AFormatFieldSuggest("N_POLIS")
    public String getNPolis() { return theNPolis ; }
    public void setNPolis(String aNPolis) { theNPolis = aNPolis ; }

    /** Имя */
    @Comment("Имя")
    @AFormatFieldSuggest("I")
    public String getFirstname() { return theFirstname ; }
    public void setFirstname(String aFirstname) { theFirstname = aFirstname ; }

    /** Фамилия */
    @Comment("Фамилия")
    @AFormatFieldSuggest("F")
    public String getLastname() { return theLastname ; }
    public void setLastname(String aLastname) { theLastname = aLastname ; }

    /** Отчество */
    @Comment("Отчество")
    @AFormatFieldSuggest("O")
    public String getMiddlename() { return theMiddlename ; }
    public void setMiddlename(String aMiddlename) { theMiddlename = aMiddlename ; }

    /** Дата рождения */
    @Comment("Дата рождения")
    @AFormatFieldSuggest("DR")
    public Date getBirthDate() { return theBirthDate ; }
    public void setBirthDate(Date aBirthDate) { theBirthDate = aBirthDate ; }

    /** Пол */
    @Comment("Пол")
    @AFormatFieldSuggest({"SЕХ","SEX"})
    @ADomain(clazz= OmcSex.class)
    public String getSex() { return theSex ; }
    public void setSex(String aSex) { theSex = aSex ; }

    /** Старый код предприятия */
    @Comment("Старый код предприятия")
    @AFormatFieldSuggest("RNUMBER")
    @ADomain(clazz= OmcOrg.class)
    public String getWorksOldCode() { return theWorksOldCode ; }
    public void setWorksOldCode(String aWorksOldCode) { theWorksOldCode = aWorksOldCode ; }

    /** Новый код предприятия */
    @Comment("Новый код предприятия")
    @AFormatFieldSuggest("RNUMBER15")
    @ADomain(clazz= OmcOrg.class, property="newCode")
    public String getWorksCode() { return theWorksCode ; }
    public void setWorksCode(String aWorksCode) { theWorksCode = aWorksCode ; }

    /** Наименование предприятия */
    @Comment("Наименование предприятия")
    @AFormatFieldSuggest("M_RABOT")
    public String getWorksName() { return theWorksName ; }
    public void setWorksName(String aWorksName) { theWorksName = aWorksName ; }

    /** Район */
    @Comment("Район")
    @AFormatFieldSuggest("RAYON")
    @ADomain(clazz= OmcRayon.class)
    public String getRegion() { return theRegion ; }
    public void setRegion(String aRegion) { theRegion = aRegion ; }

    /** Населенный пункт */
    @Comment("Населенный пункт")
    @AFormatFieldSuggest("PUNCT")
    public String getSettlement() { return theSettlement ; }
    public void setSettlement(String aSettlement) { theSettlement = aSettlement ; }

    /** Тип улицы */
    @Comment("Тип улицы")
    @AFormatFieldSuggest("STREET_T")
    @ADomain(clazz= OmcStreetType.class)
    public String getStreetType() { return theStreetType ; }
    public void setStreetType(String aStreetType) { theStreetType = aStreetType ; }

    /** Улица */
    @Comment("Улица")
    @AFormatFieldSuggest("STREET")
    public String getStreetName() { return theStreetName ; }
    public void setStreetName(String aStreetName) { theStreetName = aStreetName ; }

    /** Дом */
    @Comment("Дом")
    @AFormatFieldSuggest("HOUSE")
    public String getHouse() { return theHouse ; }
    public void setHouse(String aHouse) { theHouse = aHouse ; }

    /** Корпус дома */
    @Comment("Корпус дома")
    @AFormatFieldSuggest("SECTION")
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }

    /** Квартира */
    @Comment("Квартира")
    @AFormatFieldSuggest("APARTMENT")
    public String getFlat() { return theFlat ; }
    public void setFlat(String aFlat) { theFlat = aFlat ; }

    /** Код страховой мед. организации */
    @Comment("Код страховой мед. организации")
    @AFormatFieldSuggest("SK")
    @ADomain(clazz= OmcSk.class)
    public String getInsuranceCompany() { return theInsuranceCompany ; }
    public void setInsuranceCompany(String aInsuranceCompany) { theInsuranceCompany = aInsuranceCompany ; }

    /** Услуга */
    @Comment("Услуга")
    @AFormatFieldSuggest("USLUGA")
    @ADomain(clazz= OmcTariff.class)
    public String getRender() { return theRender ; }
    public void setRender(String aRender) { theRender = aRender ; }

    /** Код диагноза основного заболевания по МКБ10 */
    @Comment("Код диагноза основного заболевания по МКБ10")
    @AFormatFieldSuggest("DS")
    @ADomain(clazz= OmcMkb10.class)
    public String getDiagnosisMain() { return theDiagnosisMain ; }
    public void setDiagnosisMain(String aDiagnosisMain) { theDiagnosisMain = aDiagnosisMain ; }

    /** Код диагноза сопутствующего заболевания (по классификатору МКБ-10) */
    @Comment("Код диагноза сопутствующего заболевания (по классификатору МКБ-10)")
    @AFormatFieldSuggest("DS_S")
    @ADomain(clazz= OmcMkb10.class)
    public String getDiagnosisConcomitant() { return theDiagnosisConcomitant ; }
    public void setDiagnosisConcomitant(String aDiagnosisConcomitant) { theDiagnosisConcomitant = aDiagnosisConcomitant ; }

    /** Дата поступления для стационара, дата обращения для поликлиники */
    @Comment("Дата поступления для стационара, дата обращения для поликлиники")
    @AFormatFieldSuggest("DAT_POSTUP")
    public Date getAdmissionDate() { return theAdmissionDate ; }
    public void setAdmissionDate(Date aAdmissionDate) { theAdmissionDate = aAdmissionDate ; }

    /** Дата перевода из одного стационара в другой 	 */
    @Comment("Дата перевода из одного стационара в другой 	")
    @AFormatFieldSuggest("PEREVOD")
    public Date getTransferDate() { return theTransferDate ; }
    public void setTransferDate(Date aTransferDate) { theTransferDate = aTransferDate ; }

    /** Дата перевода из одного стационара в другой 	 */
    @Comment("Дата перевода из одного стационара в другой 	")
    @AFormatFieldSuggest({"DАТА_WIPIS","DATA_WIPIS"})
    public Date getDischargeDate() { return theDischargeDate ; }
    public void setDischargeDate(Date aDischargeDate) { theDischargeDate = aDischargeDate ; }

    /** Количество фактических койко-дней для стационара, количество услуг для поликлиники */
    @Comment("Количество фактических койко-дней для стационара, количество услуг для поликлиники")
    @AFormatFieldSuggest("KOL_DNEY")
    public Integer getBedDays() { return theBedDays ; }
    public void setBedDays(Integer aBedDays) { theBedDays = aBedDays ; }

    /** Код результата обращения */
    @Comment("Код результата обращения")
    @AFormatFieldSuggest("RES_G")
    @ADomain(clazz= OmcResG.class)
    public Integer getResult() { return theResult ; }
    public void setResult(Integer aResult) { theResult = aResult ; }

    /** Обращение пациента с данным заболеванием в течение года */
    @Comment("Обращение пациента с данным заболеванием в течение года")
    @AFormatFieldSuggest("POVTOR_GOS")
    public String getRehospitalization() { return theRehospitalization ; }
    public void setRehospitalization(String aRehospitalization) { theRehospitalization = aRehospitalization ; }

    /** Рассчитанная стоимость случая */
    @Comment("Рассчитанная стоимость случая")
    @AFormatFieldSuggest("CENA_SLUCH")
    public BigDecimal getCasePrice() { return theCasePrice ; }
    public void setCasePrice(BigDecimal aCasePrice) { theCasePrice = aCasePrice ; }

    /** Дата счета */
    @Comment("Дата счета")
    @AFormatFieldSuggest("DATA_SCHET")
    public Date getBillDate() { return theBillDate ; }
    public void setBillDate(Date aBillDate) { theBillDate = aBillDate ; }

    /** Номер счета */
    @Comment("Номер счета")
    @AFormatFieldSuggest("NOM_SCHET")
    public String getBillNumber() { return theBillNumber ; }
    public void setBillNumber(String aBillNumber) { theBillNumber = aBillNumber ; }

    /** СНИЛС врача */
    @Comment("СНИЛС врача")
    @AFormatFieldSuggest("SSD")
    public String getDoctorSnils() { return theDoctorSnils ; }
    public void setDoctorSnils(String aDoctorSnils) { theDoctorSnils = aDoctorSnils ; }

    /** Код врачебной должности */
    @Comment("Код врачебной должности")
    @AFormatFieldSuggest("PRVD")
    @ADomain(clazz= OmcPrvd.class)
    public String getDoctorPost() { return theDoctorPost ; }
    public void setDoctorPost(String aDoctorPost) { theDoctorPost = aDoctorPost ; }

    /** Номер истории болезни для стационара, номер амбулаторной карты для поликлиники  */
    @Comment("Номер истории болезни для стационара, номер амбулаторной карты для поликлиники ")
    @AFormatFieldSuggest("N_IST_BOL")
    public String getCaseHistoryNumber() { return theCaseHistoryNumber ; }
    public void setCaseHistoryNumber(String aCaseHistoryNumber) { theCaseHistoryNumber = aCaseHistoryNumber ; }

    /** Время поступления */
    @Comment("Время поступления")
    @AFormatFieldSuggest("TIME")
    public String getAdmissionTime() { return theAdmissionTime ; }
    public void setAdmissionTime(String aAdmissionTime) { theAdmissionTime = aAdmissionTime ; }

    /** Код направившего ЛПУ  */
    @Comment("Код направившего ЛПУ ")
    @AFormatFieldSuggest("HD")
    @ADomain(clazz= OmcLpu.class)
    public String getLpuFrom() { return theLpuFrom ; }
    public void setLpuFrom(String aLpuFrom) { theLpuFrom = aLpuFrom ; }

    /** Подразделение направившего ЛПУ  */
    @Comment("Подразделение направившего ЛПУ ")
    @AFormatFieldSuggest("FRM")
    @ADomain(clazz= OmcVidLpu.class)
    public String getLpuFromUnit() { return theLpuFromUnit ; }
    public void setLpuFromUnit(String aLpuFromUnit) { theLpuFromUnit = aLpuFromUnit ; }

    /** Вид направления */
    @Comment("Вид направления")
    @AFormatFieldSuggest("AS")
    @ADomain(clazz= OmcAs.class)
    public String getDirectionType() { return theDirectionType ; }
    public void setDirectionType(String aDirectionType) { theDirectionType = aDirectionType ; }

    /** Уникальный идентификатор застрахованного */
    @Comment("Уникальный идентификатор застрахованного")
    @AFormatFieldSuggest("RZ")
    public String getRz() { return theRz ; }
    public void setRz(String aRz) { theRz = aRz ; }

    /** Номер реестр */
    @Comment("Номер реестр")
    @AFormatFieldSuggest("REESTR")
    public String getRegistryNumber() { return theRegistryNumber ; }
    public void setRegistryNumber(String aRegistryNumber) { theRegistryNumber = aRegistryNumber ; }

    /** Код уровня оказания медицинской помощи  */
    @Comment("Код уровня оказания медицинской помощи ")
    @AFormatFieldSuggest("KL")
    @ADomain(clazz= OmcKl.class)
    public String getLevel() { return theLevel ; }
    public void setLevel(String aLevel) { theLevel = aLevel ; }

    /** Номер записи в файле  */
    @Comment("Номер записи в файле ")
    @AFormatFieldSuggest("NPP")
    public String getSerialNumber() { return theSerialNumber ; }
    public void setSerialNumber(String aSerialNumber) { theSerialNumber = aSerialNumber ; }

    /** СНИЛС гражданина (пациента) */
    @Comment("СНИЛС гражданина (пациента)")
    @AFormatFieldSuggest("SS")
    public String getPatientSnils() { return thePatientSnils ; }
    public void setPatientSnils(String aPatientSnils) { thePatientSnils = aPatientSnils ; }

    /** Код улицы по классификатору адресов России (КЛАДР)  */
    @Comment("Код улицы по классификатору адресов России (КЛАДР) ")
    @AFormatFieldSuggest("STREET_GNI")
    public String getKladr() { return theKladr ; }
    public void setKladr(String aKladr) { theKladr = aKladr ; }

    /** Профиль отделения  */
    @Comment("Профиль отделения")
    @AFormatFieldSuggest("PROF_LPU")
    @ADomain(clazz= OmcDepType.class)
    public String getDepType() { return theDepType ; }
    public void setDepType(String aDepType) { theDepType = aDepType ; }

    /** Признак осложнения  */
    @Comment("Признак осложнения ")
    @AFormatFieldSuggest("OSL")
    @ADomain(clazz= OmcOsl.class)
    public String getOsl() { return theOsl ; }
    public void setOsl(String aOsl) { theOsl = aOsl ; }

    /** Дата открытия листка нетрудоспособности */
    @Comment("Дата открытия листка нетрудоспособности")
    @AFormatFieldSuggest("D_LISTIN")
    public Date getDisabilityOpen() { return theDisabilityOpen ; }
    public void setDisabilityOpen(Date aDisabilityOpen) { theDisabilityOpen = aDisabilityOpen ; }

    /** Дата закрытия листка нетрудоспособности */
    @Comment("Дата закрытия листка нетрудоспособности")
    @AFormatFieldSuggest("D_LISTOUT")
    public Date getDisabilityClose() { return theDisabilityClose ; }
    public void setDisabilityClose(Date aDisabilityClose) { theDisabilityClose = aDisabilityClose ; }

    /** Код характера заболевания */
    @Comment("Код характера заболевания")
    @AFormatFieldSuggest("Q_Z")
    @ADomain(clazz= OmcQz.class)
    public Integer getQz() { return theQz ; }
    public void setQz(Integer aQz) { theQz = aQz ; }

    /** Результаты обработки записи СМО (экспертная оценка)  */
    @Comment("Результаты обработки записи СМО (экспертная оценка) ")
    @AFormatFieldSuggest("EXPERT")
    @ADomain(clazz= OmcExpert.class)
    public String getExpert() { return theExpert ; }
    public void setExpert(String aExpert) { theExpert = aExpert ; }

    /** Код специалиста */
    @Comment("Код специалиста")
    public String getDoctorCode() { return theDoctorCode ; }
    public void setDoctorCode(String aDoctorCode) { theDoctorCode = aDoctorCode ; }

    /** Наименование страховой   медицинской организации   (или ТФОМС, выполняющего  функции СМО) */
    @Comment("Наименование страховой   медицинской организации   (или ТФОМС, выполняющего  функции СМО)")
    @AFormatFieldSuggest("SMO")
    public String getSmo() { return theSmo ; }
    public void setSmo(String aSmo) { theSmo = aSmo ; }

    /** Код области (нахождения СМО) */
    @Comment("Код области (нахождения СМО)")
    @AFormatFieldSuggest("OBLSMO")
    @ADomain(clazz= OmcKodTer.class)
    public String getOblSmo() { return theOblSmo ; }
    public void setOblSmo(String aOblSmo) { theOblSmo = aOblSmo ; }

    /** Город (нахождения СМО) */
    @Comment("Город (нахождения СМО)")
    @AFormatFieldSuggest("GORSMO")
    public String getGorSmo() { return theGorSmo ; }
    public void setGorSmo(String aGorSmo) { theGorSmo = aGorSmo ; }

    /** Страна проживания */
    @Comment("Страна проживания")
    @AFormatFieldSuggest("COUNTRY")
    public String getCountry() { return theCountry ; }
    public void setCountry(String aCountry) { theCountry = aCountry ; }

    /** Субъект Федерации */
    @Comment("Субъект Федерации")
    @AFormatFieldSuggest("OBLCOUN")
    public String getOblCoun() { return theOblCoun ; }
    public void setOblCoun(String aOblCoun) { theOblCoun = aOblCoun ; }

    /** Населенный пункт */
    @Comment("Населенный пункт")
    @AFormatFieldSuggest("GORCOUN")
    public String getGorCoun() { return theGorCoun ; }
    public void setGorCoun(String aGorCoun) { theGorCoun = aGorCoun ; }

    /** Район проживания */
    @Comment("Район проживания")
    @AFormatFieldSuggest("RCOUN")
    public String getRCoun() { return theRCoun ; }
    public void setRCoun(String aRCoun) { theRCoun = aRCoun ; }

    /** Улица д.-кор.-кв */
    @Comment("Улица д.-кор.-кв")
    @AFormatFieldSuggest("SCOUN")
    public String getSCoun() { return theSCoun ; }
    public void setSCoun(String aSCoun) { theSCoun = aSCoun ; }

    /** Особый случай */
    @Comment("Особый случай")
    @AFormatFieldSuggest("CASUS")
    public String getCasus() { return theCasus ; }
    public void setCasus(String aCasus) { theCasus = aCasus ; }

    /** Код типа документа, удостоверяющего личность */
    @Comment("Код типа документа, удостоверяющего личность")
    @AFormatFieldSuggest("DOC_T")
    public String getDocT() { return theDocT ; }
    public void setDocT(String aDocT) { theDocT = aDocT ; }

    /** Серия документа, удостоверяющего личность */
    @Comment("Серия документа, удостоверяющего личность")
    @AFormatFieldSuggest("DOC_S")
    public String getDocS() { return theDocS ; }
    public void setDocS(String aDocS) { theDocS = aDocS ; }

    /** Номер документа, удостоверяющего личность */
    @Comment("Номер документа, удостоверяющего личность")
    @AFormatFieldSuggest("DOC_N")
    public String getDocN() { return theDocN ; }
    public void setDocN(String aDocN) { theDocN = aDocN ; }

    /** Дата выдачи документа, удостоверяющего личность */
    @Comment("Дата выдачи документа, удостоверяющего личность")
    @AFormatFieldSuggest("DOC_D")
    public Date getDocD() { return theDocD ; }
    public void setDocD(Date aDocD) { theDocD = aDocD ; }

    /** Наименование органа, выдавшего документ, удостоверяющий личность */
    @Comment("Наименование органа, выдавшего документ, удостоверяющий личность")
    @AFormatFieldSuggest("DOC_V")
    public String getDocV() { return theDocV ; }
    public void setDocV(String aDocV) { theDocV = aDocV ; }

    /** Фамилия представителя ребенка */
    @Comment("Фамилия представителя ребенка")
    @AFormatFieldSuggest("FAM")
    public String getForFam() { return theForFam ; }
    public void setForFam(String aForFam) { theForFam = aForFam ; }

    /** Имя представителя ребенка */
    @Comment("Имя представителя ребенка")
    @AFormatFieldSuggest("IM")
    public String getForIm() { return theForIm ; }
    public void setForIm(String aForIm) { theForIm = aForIm ; }
    
    /** Дата оперативного вмешательства */
	@Comment("Дата оперативного вмешательства")
	public Date getOperationDate() {return theOperationDate; }
	public void setOperationDate(Date aOperationDate) {	theOperationDate = aOperationDate; }
    
    /** Количество выписанных рецептов */
	@Comment("Количество выписанных рецептов")
	public Integer getRecipeAmount() { return theRecipeAmount; }
	public void setRecipeAmount(Integer aRecipeAmount) { theRecipeAmount = aRecipeAmount; }

    /** Отчество представителя ребенка */
    @Comment("Отчество представителя ребенка")
    @AFormatFieldSuggest("OTH")
    public String getForOth() { return theForOth ; }
    public void setForOth(String aForOth) { theForOth = aForOth ; }


	/** Дата оперативного вмешательства */
	private Date theOperationDate;
	/** Количество выписанных рецептов */
	private Integer theRecipeAmount;
	/** Отчество представителя ребенка */
    private String theForOth ;
    /** Имя представителя ребенка */
    private String theForIm ;
    /** Фамилия представителя ребенка */
    private String theForFam ;
    /** Наименование органа, выдавшего документ, удостоверяющий личность */
    private String theDocV ;
    /** Дата выдачи документа, удостоверяющего личность */
    private Date theDocD ;
    /** Номер документа, удостоверяющего личность */
    private String theDocN ;
    /** Серия документа, удостоверяющего личность */
    private String theDocS ;
    /** Код типа документа, удостоверяющего личность */
    private String theDocT ;
    /** Особый случай */
    private String theCasus ;
    /** Улица д.-кор.-кв */
    private String theSCoun ;
    /** Район проживания */
    private String theRCoun ;
    /** Населенный пункт */
    private String theGorCoun ;
    /** Субъект Федерации */
    private String theOblCoun ;
    /** Страна проживания */
    private String theCountry ;
    /** Город (нахождения СМО) */
    private String theGorSmo ;
    /** Код области (нахождения СМО) */
    private String theOblSmo ;
    /** Наименование страховой   медицинской организации   (или ТФОМС, выполняющего  функции СМО) */
    private String theSmo ;
    /** Код специалиста */
    private String theDoctorCode ;
    /** Результаты обработки записи СМО (экспертная оценка)  */
    private String theExpert ;
    /** Код характера заболевания */
    private Integer theQz ;
    /** Дата закрытия листка нетрудоспособности */
    private Date theDisabilityClose ;
    /** Дата открытия листка нетрудоспособности */
    private Date theDisabilityOpen ;
    /** Признак осложнения  */
    private String theOsl ;
    /** Профиль отделения  */
    private String theDepType ;
    /** Код улицы по классификатору адресов России (КЛАДР)  */
    private String theKladr ;
    /** СНИЛС гражданина (пациента) */
    private String thePatientSnils ;
    /** Номер записи в файле  */
    private String theSerialNumber ;
    /** Код уровня оказания медицинской помощи  */
    private String theLevel ;
    /** Номер реестр */
    private String theRegistryNumber ;
    /** Уникальный идентификатор застрахованного */
    private String theRz ;
    /** Вид навравления */
    private String theDirectionType ;
    /** Подразделение направившего ЛПУ  */
    private String theLpuFromUnit ;
    /** Код направившего ЛПУ  */
    private String theLpuFrom ;
    /** Время поступления */
    private String theAdmissionTime ;
    /** Номер истории болезни для стационара, номер амбулаторной карты для поликлиники  */
    private String theCaseHistoryNumber ;
    /** Код врачебной должности */
    private String theDoctorPost ;
    /** СНИЛС врача */
    private String theDoctorSnils ;
    /** Номер счета */
    private String theBillNumber ;
    /** Дата счета */
    private Date theBillDate ;
    /** Рассчитанная стоимость случая */
    private BigDecimal theCasePrice ;
    /** Обращение пациента с данным заболеванием в течение года */
    private String theRehospitalization ;
    /** Код результата обращения */
    private Integer theResult ;
    /** Количество фактических койко-дней для стационара, количество услуг для поликлиники */
    private Integer theBedDays ;
    /** Дата перевода из одного стационара в другой 	 */
    private Date theDischargeDate ;
    /** Дата перевода из одного стационара в другой 	 */
    private Date theTransferDate ;
    /** 	Дата поступления для стационара, дата обращения для поликлиники */
    private Date theAdmissionDate ;
    /** Код диагноза сопутствующего заболевания (по классификатору МКБ-10) */
    private String theDiagnosisConcomitant ;
    /** Код диагноза основного заболевания по МКБ10 */
    private String theDiagnosisMain ;
    /** Услуга */
    private String theRender ;
    /** Код страховой мед. организации */
    private String theInsuranceCompany ;
    /** Квартира */
    private String theFlat ;
    /** Корпус дома */
    private String theHouseBuilding ;
    /** Дом */
    private String theHouse ;
    /** Улица */
    private String theStreetName ;
    /** Тип улицы */
    private String theStreetType ;
    /** Населенный пункт */
    private String theSettlement ;
    /** Район */
    private String theRegion ;
    /** Наименование предприятия */
    private String theWorksName ;
    /** Новый код предприятия */
    private String theWorksCode ;
    /** Старый код предприятия */
    private String theWorksOldCode ;
    /** Пол */
    private String theSex ;
    /** Дата рождения */
    private Date theBirthDate ;
    /** Отчество */
    private String theMiddlename ;
    /** Фамилия */
    private String theLastname ;
    /** Фамилия */
    private String theFirstname ;
    /** Номер полиса */
    private String theNPolis ;
    /** Серия полиса */
    private String theSPolis ;
    /** Код социальной группы */
    private String theSgroup ;
    /** Районы города и области */
    private String theVidLpu ;
    /** Код ЛПУ */
    private String theKodLpu ;

//    /** Реестр */
//    private Registry theRegistry ;
    /** Тип страхования */
    private String theInsureType ;

   

}
