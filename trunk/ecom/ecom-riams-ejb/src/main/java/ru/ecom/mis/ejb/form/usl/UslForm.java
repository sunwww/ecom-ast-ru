package ru.ecom.mis.ejb.form.usl;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.usl.MisUsl;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Услуга
 */
@Comment("Услуга")
@EntityForm
@EntityFormPersistance(clazz= MisUsl.class)
@WebTrail(comment = "Услуга", nameProperties= "render", view="entityParentView-mis_usl.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Usl")
public class UslForm extends IdEntityForm {

    /** Пациент */
    @Persist
    public Long getPatient() { return thePatient ; }
    public void setPatient(Long aPatient) { thePatient = aPatient ; }

    /** Пациент */
    private Long thePatient ;
    /** Код ЛПУ */
    @Comment("Код ЛПУ")
    @Persist
    public String getKodLpu() { return theKodLpu ; }
    public void setKodLpu(String aKodLpu) { theKodLpu = aKodLpu ; }

    /** Вид ЛПУ */
    @Comment("Вид ЛПУ")
    @Persist
    public String getVidLpu() { return theVidLpu ; }
    public void setVidLpu(String aVidLpu) { theVidLpu = aVidLpu ; }

    /** Тип страхования */
    @Comment("Тип страхования")
    @Persist
    public String getInsureType() { return theInsureType ; }
    public void setInsureType(String aInsureType) { theInsureType = aInsureType ; }

    /** Услуга */
    @Comment("Услуга")
    @Persist
    public String getRender() { return theRender ; }
    public void setRender(String aRender) { theRender = aRender ; }

    /** Код диагноза основного заболевания по МКБ10 */
    @Comment("Код диагноза основного заболевания по МКБ10")
    @Persist
    public String getDiagnosisMain() { return theDiagnosisMain ; }
    public void setDiagnosisMain(String aDiagnosisMain) { theDiagnosisMain = aDiagnosisMain ; }

    /** Код диагноза сопутствующего заболевания (по классификатору МКБ-10) */
    @Comment("Код диагноза сопутствующего заболевания (по классификатору МКБ-10)")
    @Persist
    public String getDiagnosisConcomitant() { return theDiagnosisConcomitant ; }
    public void setDiagnosisConcomitant(String aDiagnosisConcomitant) { theDiagnosisConcomitant = aDiagnosisConcomitant ; }

    /** Дата поступления для стационара, дата обращения для поликлиники */
    @Comment("Дата поступления для стационара, дата обращения для поликлиники")
    @DateString
    @Persist
    public String getAdmissionDate() { return theAdmissionString ; }
    public void setAdmissionDate(String aAdmissionString) { theAdmissionString = aAdmissionString ; }

    /** Дата перевода из одного стационара в другой 	 */
    @Comment("Дата перевода из одного стационара в другой 	")
    @DateString
    public String getTransferDate() { return theTransferString ; }
    public void setTransferDate(String aTransferString) { theTransferString = aTransferString ; }

    /** Дата перевода из одного стационара в другой 	 */
    @Comment("Дата перевода из одного стационара в другой 	")
    @Persist
    @DateString
    public String getDischargeDate() { return theDischargeString ; }
    public void setDischargeDate(String aDischargeString) { theDischargeString = aDischargeString ; }

    /** Количество фактических койко-дней для стационара, количество услуг для поликлиники */
    @Comment("Количество фактических койко-дней для стационара, количество услуг для поликлиники")
    @Persist
    public int getBedDays() { return theBedDays ; }
    public void setBedDays(int aBedDays) { theBedDays = aBedDays ; }

    /** Код результата обращения */
    @Persist
    @Comment("Код результата обращения")
    public int getResult() { return theResult ; }
    public void setResult(int aResult) { theResult = aResult ; }

    /** Обращение пациента с данным заболеванием в течение года */
    @Comment("Обращение пациента с данным заболеванием в течение года")
    @Persist
    public String getRehospitalization() { return theRehospitalization ; }
    public void setRehospitalization(String aRehospitalization) { theRehospitalization = aRehospitalization ; }

    /** Рассчитанная стоимость случая */
//    @Comment("Рассчитанная стоимость случая")
//    @Persist
//    public BigDecimal getCasePrice() { return theCasePrice ; }
//    public void setCasePrice(BigDecimal aCasePrice) { theCasePrice = aCasePrice ; }

    /** Дата счета */
    @Comment("Дата счета")
    @Persist
    @DateString
    public String getBillDate() { return theBillString ; }
    public void setBillDate(String aBillString) { theBillString = aBillString ; }

    /** Номер счета */
    @Comment("Номер счета")
    @Persist
    public String getBillNumber() { return theBillNumber ; }
    public void setBillNumber(String aBillNumber) { theBillNumber = aBillNumber ; }

    /** СНИЛС врача */
    @Comment("СНИЛС врача")
    @Persist
    public String getDoctorSnils() { return theDoctorSnils ; }
    public void setDoctorSnils(String aDoctorSnils) { theDoctorSnils = aDoctorSnils ; }

    /** Код врачебной должности */
    @Comment("Код врачебной должности")
    @Persist
    public String getDoctorPost() { return theDoctorPost ; }
    public void setDoctorPost(String aDoctorPost) { theDoctorPost = aDoctorPost ; }

    /** Номер истории болезни для стационара, номер амбулаторной карты для поликлиники  */
    @Comment("Номер истории болезни для стационара, номер амбулаторной карты для поликлиники ")
    @Persist
    public String getCaseHistoryNumber() { return theCaseHistoryNumber ; }
    public void setCaseHistoryNumber(String aCaseHistoryNumber) { theCaseHistoryNumber = aCaseHistoryNumber ; }

    /** Время поступления */
    @Comment("Время поступления")
    @Persist
    public String getAdmissionTime() { return theAdmissionTime ; }
    public void setAdmissionTime(String aAdmissionTime) { theAdmissionTime = aAdmissionTime ; }

    /** Код направившего ЛПУ  */
    @Comment("Код направившего ЛПУ ")
    @Persist
    public String getLpuFrom() { return theLpuFrom ; }
    public void setLpuFrom(String aLpuFrom) { theLpuFrom = aLpuFrom ; }

    /** Подразделение направившего ЛПУ  */
    @Comment("Подразделение направившего ЛПУ ")
    @Persist
    public String getLpuFromUnit() { return theLpuFromUnit ; }
    public void setLpuFromUnit(String aLpuFromUnit) { theLpuFromUnit = aLpuFromUnit ; }

    /** Вид направления */
    @Comment("Вид направления")
    @Persist
    public String getDirectionType() { return theDirectionType ; }
    public void setDirectionType(String aDirectionType) { theDirectionType = aDirectionType ; }

    /** Уникальный идентификатор застрахованного */
    @Comment("Уникальный идентификатор застрахованного")
    @Persist
    public String getRz() { return theRz ; }
    public void setRz(String aRz) { theRz = aRz ; }

    /** Номер реестр */
    @Comment("Номер реестр")
    @Persist
    public String getRegistryNumber() { return theRegistryNumber ; }
    public void setRegistryNumber(String aRegistryNumber) { theRegistryNumber = aRegistryNumber ; }

    /** Код уровня оказания медицинской помощи  */
    @Comment("Код уровня оказания медицинской помощи ")
    @Persist
    public String getLevel() { return theLevel ; }
    public void setLevel(String aLevel) { theLevel = aLevel ; }

    /** Профиль отделения  */
    @Comment("Профиль отделения")
    @Persist
    public String getDepType() { return theDepType ; }
    public void setDepType(String aDepType) { theDepType = aDepType ; }

    /** Признак осложнения  */
    @Comment("Признак осложнения ")
    @Persist
    public String getOsl() { return theOsl ; }
    public void setOsl(String aOsl) { theOsl = aOsl ; }

    /** Дата открытия листка нетрудоспособности */
    @Comment("Дата открытия листка нетрудоспособности")
    @Persist
    public String getDisabilityOpen() { return theDisabilityOpen ; }
    public void setDisabilityOpen(String aDisabilityOpen) { theDisabilityOpen = aDisabilityOpen ; }

    /** Дата закрытия листка нетрудоспособности */
    @Comment("Дата закрытия листка нетрудоспособности")
    @Persist
    public String getDisabilityClose() { return theDisabilityClose ; }
    public void setDisabilityClose(String aDisabilityClose) { theDisabilityClose = aDisabilityClose ; }

    /** Код характера заболевания */
    @Comment("Код характера заболевания")
    @Persist
    public int getQz() { return theQz ; }
    public void setQz(int aQz) { theQz = aQz ; }

    /** Результаты обработки записи СМО (экспертная оценка)  */
    @Comment("Результаты обработки записи СМО (экспертная оценка) ")
    @Persist
    public String getExpert() { return theExpert ; }
    public void setExpert(String aExpert) { theExpert = aExpert ; }

    /** Код специалиста */
    @Comment("Код специалиста")
    @Persist
    public String getDoctorCode() { return theDoctorCode ; }
    public void setDoctorCode(String aDoctorCode) { theDoctorCode = aDoctorCode ; }


    /** Особый случай */
    @Comment("Особый случай")
    @Persist
    public String getCasus() { return theCasus ; }
    public void setCasus(String aCasus) { theCasus = aCasus ; }

    /** Особый случай */
    private String theCasus ;
    /** Код специалиста */
    private String theDoctorCode ;
    /** Результаты обработки записи СМО (экспертная оценка)  */
    private String theExpert ;
    /** Код характера заболевания */
    private int theQz ;
    /** Дата закрытия листка нетрудоспособности */
    private String theDisabilityClose ;
    /** Дата открытия листка нетрудоспособности */
    private String theDisabilityOpen ;
    /** Признак осложнения  */
    private String theOsl ;
    /** Профиль отделения  */
    private String theDepType ;
    /** Код уровня оказания медицинской помощи  */
    private String theLevel ;
    /** Номер реестр */
    private String theRegistryNumber ;
    /** Уникальный идентификатор застрахованного */
    private String theRz ;
    /** Вид направления */
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
    private String theBillString ;
    /** Обращение пациента с данным заболеванием в течение года */
    private String theRehospitalization ;
    /** Код результата обращения */
    private int theResult ;
    /** Количество фактических койко-дней для стационара, количество услуг для поликлиники */
    private int theBedDays ;
    /** Дата перевода из одного стационара в другой 	 */
    private String theDischargeString ;
    /** Дата перевода из одного стационара в другой 	 */
    private String theTransferString ;
    /** 	Дата поступления для стационара, дата обращения для поликлиники */
    private String theAdmissionString ;
    /** Код диагноза сопутствующего заболевания (по классификатору МКБ-10) */
    private String theDiagnosisConcomitant ;
    /** Код диагноза основного заболевания по МКБ10 */
    private String theDiagnosisMain ;
    /** Услуга */
    private String theRender ;
    /** Районы города и области */
    private String theVidLpu ;
    /** Код ЛПУ */
    private String theKodLpu ;
    /** Тип страхования */
    private String theInsureType ;
}
