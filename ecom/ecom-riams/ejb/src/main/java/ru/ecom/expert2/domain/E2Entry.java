package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.VocE2EntrySubType;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.VocE2Podvid;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@UnDeletable
@AIndexes({
        @AIndex(unique = false, properties= {"listEntry"})
        , @AIndex(unique = false, properties= {"entryType"})
        , @AIndex(unique = false, properties= {"lastname","firstname","middlename", "birthDate"})
        , @AIndex(unique = false, properties= {"billDate", "billNumber"})
        , @AIndex(unique = false, properties= {"historyNumber"})
        , @AIndex(unique = false, properties= {"startDate"})
        , @AIndex(unique = false, properties= {"finishDate"})
        , @AIndex(unique = false, properties= {"serviceStream"})

})
public class E2Entry extends BaseEntity {


    //Все поля ниже размещены в EntityForm!

    /** Консультативно-диагностическое обращение */
    @Comment("Консультативно-диагностическое обращение")
    public Boolean getIsDiagnosticSpo() {return theIsDiagnosticSpo;}
    public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {theIsDiagnosticSpo = aIsDiagnosticSpo;}
    /** Консультативно-диагностическое обращение */
    private Boolean theIsDiagnosticSpo ;

    /** Подтип записи */
    @Comment("Подтип записи")
    @OneToOne
    public VocE2EntrySubType getSubType() {return theSubType;}
    public void setSubType(VocE2EntrySubType aSubType) {theSubType = aSubType;}
    /** Подтип записи */
    private VocE2EntrySubType theSubType ;

    /** Признак мобильной поликлиники */
    @Comment("Признак мобильной поликлиники")
    public Boolean getIsMobilePolyclinic() {return theIsMobilePolyclinic;}
    public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {theIsMobilePolyclinic = aIsMobilePolyclinic;}
    /** Признак мобильной поликлиники */
    private Boolean theIsMobilePolyclinic ;

    //Все поля ниже размещены в EntityForm!
    /** Был произведен аборт по медицинским показаниям */
    @Comment("Был произведен аборт по медицинским показаниям")
    public Boolean getMedicalAbort() {return theMedicalAbort;}
    public void setMedicalAbort(Boolean aMedicalAbort) {theMedicalAbort = aMedicalAbort;}
    /** Был произведен аборт по медицинским показаниям */
    private Boolean theMedicalAbort ;

    /** Доп. критерий КСГ */
    @Comment("Доп. критерий КСГ")
    public String getDopKritKSG() {return theDopKritKSG;}
    public void setDopKritKSG(String aDopKritKSG) {theDopKritKSG = aDopKritKSG;}
    /** Доп. критерий КСГ */
    private String theDopKritKSG ;

    /** Место приема пациента */
    @Comment("Место приема пациента")
    public String getWorkPlace() {return theWorkPlace;}
    public void setWorkPlace(String aWorkPlace) {theWorkPlace = aWorkPlace;}
    /** Место приема пациента */
    private String theWorkPlace ;

    /** Признак дефекта */
    @Comment("Признак дефекта")
    public Boolean getIsDefect() {return theIsDefect;}
    public void setIsDefect(Boolean aIsDefect) {theIsDefect = aIsDefect;}
    /** Признак дефекта */
    private Boolean theIsDefect ;

    /** Случай объединен */
    @Comment("Случай объединен")
    public Boolean getIsUnion() {return theIsUnion;}
    public void setIsUnion(Boolean aIsUnion) {theIsUnion = aIsUnion;}
    /** Случай объединен */
    private Boolean theIsUnion ;

    /** Расчет случая ФОМС */
    @Comment("Расчет случая ФОМС")
    public String getFondComment() {return theFondComment;}
    public void setFondComment(String aFondComment) {theFondComment = aFondComment;}
    /** Расчет случая ФОМС */
    private String theFondComment ;

    /** Удаленная запись */
    @Comment("Удаленная запись")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted ;

    /** Случай реанимации */
    @Comment("Случай реанимации")
    @OneToOne
    public E2Entry getReanimationEntry() {return theReanimationEntry;}
    public void setReanimationEntry(E2Entry aReanimationEntry) {theReanimationEntry = aReanimationEntry;}
    /** Случай реанимации */
    private E2Entry theReanimationEntry ;

    /** Основная услуга случая */
    @Comment("Основная услуга случая")
    public String getMainService() {return theMainService;}
    public void setMainService(String aMainService) {theMainService = aMainService;}
    /** Основная услуга случая */
    private String theMainService ;

    /** Основной МКБ случая */
    @Comment("Основной МКБ случая")
    public String getMainMkb() {return theMainMkb;}
    public void setMainMkb(String aMainMkb) {theMainMkb = aMainMkb;}
    /** Основной МКБ случая */
    private String theMainMkb ;

    /** Прерванный случай */
    @Comment("Прерванный случай")
    public Boolean getIsBreakedCase() {return theIsBreakedCase;}
    public void setIsBreakedCase(Boolean aIsBreakedCase) {theIsBreakedCase = aIsBreakedCase;}
    /** Прерванный случай */
    private Boolean theIsBreakedCase ;
    /** Не подавать на оплату */
    @Comment("Не подавать на оплату")
    public Boolean getDoNotSend() {return theDoNotSend;}
    public void setDoNotSend(Boolean aDoNotSend) {theDoNotSend = aDoNotSend;}
    /** Не подавать на оплату */
    private Boolean theDoNotSend ;

    /** Условия оказания мед. помощи */
    @Comment("Условия оказания мед. помощи")
    @OneToOne
    public VocE2FondV006 getMedHelpUsl() {return theMedHelpUsl;}
    public void setMedHelpUsl(VocE2FondV006 aMedHelpUsl) {theMedHelpUsl = aMedHelpUsl;}
    /** Условия оказания мед. помощи */
    private VocE2FondV006 theMedHelpUsl ;
    
    
    /** Вид медицинской помощи */
    @Comment("Вид медицинской помощи")
    @OneToOne
    public VocE2FondV008 getMedHelpKind() {return theMedHelpKind;}
    public void setMedHelpKind(VocE2FondV008 aMedHelpKind) {theMedHelpKind = aMedHelpKind;}
    /** Вид медицинской помощи */
    private VocE2FondV008 theMedHelpKind ;

    /** Профиль оказания мед. помощи */
    @Comment("Профиль оказания мед. помощи")
    @OneToOne
    public VocE2MedHelpProfile getMedHelpProfile() {return theMedHelpProfile;}
    public void setMedHelpProfile(VocE2MedHelpProfile aMedHelpProfile) {theMedHelpProfile = aMedHelpProfile;}
    /** Профиль оказания мед. помощи */
    private VocE2MedHelpProfile theMedHelpProfile ;

    /** Базовый тариф */
    @Comment("Базовый тариф")
    public BigDecimal getBaseTarif() {return theBaseTarif;}
    public void setBaseTarif(BigDecimal aBaseTarif) {theBaseTarif = aBaseTarif;}
    /** Базовый коэффициент */
    private BigDecimal theBaseTarif ;
     /** Формула расчета цены */
     @Comment("Формула расчета цены")
     public String getCostFormulaString() {return theCostFormulaString;}
     public void setCostFormulaString(String aCostFormulaString) {theCostFormulaString = aCostFormulaString;}
     /** Формула расчета цены */
     private String theCostFormulaString ;

    /** Позиция группировщика, по которой высчитан КСГ */
    @Comment("Позиция группировщика, по которой высчитан КСГ")
    @OneToOne
    public GrouperKSGPosition getKsgPosition() {return theKsgPosition;}
    public void setKsgPosition(GrouperKSGPosition aKsgPosition) {theKsgPosition = aKsgPosition;}
    /** Позиция группировщика, по которой высчитан КСГ */
    private GrouperKSGPosition theKsgPosition ;

    /** Отделение не входит в ОМС */
    @Comment("Отделение не входит в ОМС")
    public Boolean getNoOmcDepartment() {return theNoOmcDepartment;}
    public void setNoOmcDepartment(Boolean aNoOmcDepartment) {theNoOmcDepartment = aNoOmcDepartment;}
    /** Отделение не входит в ОМС */
    private Boolean theNoOmcDepartment ;

    /** Итоговый коэффициент */
    @Comment("Итоговый коэффициент")
    @Column( precision = 15, scale = 12 )
    public BigDecimal getTotalCoefficient() {return theTotalCoefficient;}
    public void setTotalCoefficient(BigDecimal aTotalCoefficient) {theTotalCoefficient = aTotalCoefficient;}
    /** Итоговый коэффициент */
    private BigDecimal theTotalCoefficient ;


    /** Родительский случай */
    @Comment("Родительский случай")
    @OneToOne
    public E2Entry getParentEntry() {return theParentEntry;}
    public void setParentEntry(E2Entry aParentEntry) {theParentEntry = aParentEntry;}
    /** Родительский случай */
    private E2Entry theParentEntry ;

     /** Сложность лечения пациента */
     @Comment("Сложность лечения пациента")
     @OneToMany(mappedBy = "entry")
     public List<E2CoefficientPatientDifficultyEntryLink> getPatientDifficulty() {return thePatientDifficulty;}
     public void setPatientDifficulty(List<E2CoefficientPatientDifficultyEntryLink> aPatientDifficulty) {thePatientDifficulty = aPatientDifficulty;}
     /** Сложность лечения пациента */
     private List<E2CoefficientPatientDifficultyEntryLink> thePatientDifficulty ;

     @Transient
     public String getPatientDifficultyCodes() {
      StringBuilder ret=new StringBuilder();
      for (E2CoefficientPatientDifficultyEntryLink link:  getPatientDifficulty()) {
       if (ret.length()>0) {ret.append(";");}
       ret.append(link.getDifficulty().getCode());
      }
      return ret.toString();
     }

     /** Причины неполной оплаты */
     @Comment("Причины неполной оплаты")
     public String getNotFullPaymentReason() {return theNotFullPaymentReason;}
     public void setNotFullPaymentReason(String aNotFullPaymentReason) {theNotFullPaymentReason = aNotFullPaymentReason;}
     /** Причины неполной оплаты */
     private String theNotFullPaymentReason ;

     /** Способ оплаты медицинской помощи */
     @Comment("Способ оплаты медицинской помощи")
     @OneToOne
     public VocE2FondV010 getIDSP() {return theIDSP;}
     public void setIDSP(VocE2FondV010 aIDSP) {theIDSP = aIDSP;}
     /** Способ оплаты медицинской помощи */
     private VocE2FondV010 theIDSP ;

     /** ТИп записи */ //стационар, ВМП, пол-ка, подушевка, ДД
     @Comment("ТИп записи")
     public String getEntryType() {return theEntryType;}
     public void setEntryType(String aEntryType) {theEntryType = aEntryType;}
     /** ТИп записи */
     private String theEntryType ;

     /** Тип заполнения */
     @Comment("Тип заполнения")
     @Transient
     public String getEntryListType() {return theListEntry.getEntryType().getCode();}
    /** Многоплодная беременность */
    @Comment("Многоплодная беременность")
    public Boolean getMultiplyBirth() {return theMultiplyBirth;}
    public void setMultiplyBirth(Boolean aMultiplyBirth) {theMultiplyBirth = aMultiplyBirth;}
    /** Многоплодная беременность */
    private Boolean theMultiplyBirth ;


     /** Специальность врача по фонду */
     @Comment("Специальность врача по фонду")
     @OneToOne
     public VocE2FondV015 getFondDoctorSpec() {return theFondDoctorSpec;}
     public void setFondDoctorSpec(VocE2FondV015 aFondDoctorSpec) {theFondDoctorSpec = aFondDoctorSpec;}
     /** Специальность врача по фонду */
     private VocE2FondV015 theFondDoctorSpec ;

     /** Исход случая */
     @Comment("Исход случая")
     @OneToOne
     public VocE2FondV012 getFondIshod() {return theFondIshod;}
     public void setFondIshod(VocE2FondV012 aFondIshod) {theFondIshod = aFondIshod;}
     /** Исход случая */
     private VocE2FondV012 theFondIshod ;

     /** Результат оказания медицинской помощи */
     @Comment("Результат оказания медицинской помощи")
     @OneToOne
     public VocE2FondV009 getFondResult() {return theFondResult;}
     public void setFondResult(VocE2FondV009 aFondResult) {theFondResult = aFondResult;}
     /** Результат оказания медицинской помощи */
     private VocE2FondV009 theFondResult ;

     /** ОКАТО регистрации */
     @Comment("ОКАТО регистрации")
     public String getOkatoReg() {return theOkatoReg;}
     public void setOkatoReg(String aOkatoReg) {theOkatoReg = aOkatoReg;}
     /** ОКАТО регистрации */
     private String theOkatoReg ;

     /** ОКАТО проживания */
     @Comment("ОКАТО проживания")
     public String getOkatoReal() {return theOkatoReal;}
     public void setOkatoReal(String aOkatoReal) {theOkatoReal = aOkatoReal;}
     /** ОКАТО проживания */
     private String theOkatoReal ;

     /** policyMedcaseString */
     @Comment("policyMedcaseString")
     @Column(length= 2500)
     public String getPolicyMedcaseString() {return thePolicyMedcaseString;}
     public void setPolicyMedcaseString(String aPolicyMedcaseString) {thePolicyMedcaseString = aPolicyMedcaseString;}
     /** policyMedcaseString */
     private String thePolicyMedcaseString ;

     /** policyKinsmanString */
     @Comment("policyKinsmanString")
     @Column(length= 2500)
     public String getPolicyKinsmanString() {return thePolicyKinsmanString;}
     public void setPolicyKinsmanString(String aPolicyKinsmanString) {thePolicyKinsmanString = aPolicyKinsmanString;}
     /** policyKinsmanString */
     private String thePolicyKinsmanString ;

    /** policyPatientString */
    @Comment("policyPatientString")
    @Column(length= 2500)
    public String getPolicyPatientString() {return thePolicyPatientString;}
    public void setPolicyPatientString(String aPolicyPatientString) {thePolicyPatientString = aPolicyPatientString;}
    /** policyPatientString */
    private String thePolicyPatientString ;

     /** Применять пониженный коэффициент */
     @Comment("Применять пониженный коэффициент")
     public Boolean getUseLowerCoefficient() {return theUseLowerCoefficient;}
     public void setUseLowerCoefficient(Boolean aUseLowerCoefficient) {theUseLowerCoefficient = aUseLowerCoefficient;}
     /** Применять пониженный коэффициент */
     private Boolean theUseLowerCoefficient ;

      /** Вычисляемая услуга */
      @Comment("Вычисляемая услуга")
      @Transient
      public String getAutoService() {
        return theHelpKind+"#"+theBedSubType+(thePodvid!=null?thePodvid.getCode():"---");
      }


     /** Подвид медицинской помощи */
     @Comment("Подвид медицинской помощи")
     @OneToOne
     @Deprecated
     public VocE2Podvid getPodvid() {return thePodvid;}
     public void setPodvid(VocE2Podvid aPodvid) {thePodvid = aPodvid;}
     /** Подвид медицинской помощи */
     private VocE2Podvid thePodvid ;

     /** Количество рожденных детей */
     @Comment("Количество рожденных детей")
     public Long getNewbornAmount() {return theNewbornAmount;}
     public void setNewbornAmount(Long aNewbornAmount) {theNewbornAmount = aNewbornAmount;}
     /** Количество рожденных детей */
     private Long theNewbornAmount ;

     /** Признак исправленной записи */
     @Comment("Признак исправленной записи")
     public Boolean getPRNOV() {return thePRNOV;}
     public void setPRNOV(Boolean aPRNOV) {thePRNOV = aPRNOV;}
     /** Признак исправленной записи */
     private Boolean thePRNOV ;


     /** Номер счета */
     @Comment("Номер счета")
     public String getBillNumber() {return theBillNumber;}
     public void setBillNumber(String aBillNumber) {theBillNumber = aBillNumber;}
     /** Номер счета */
     private String theBillNumber ;

     /** Дата счета */
     @Comment("Дата счета")
     public Date getBillDate() {return theBillDate;}
     public void setBillDate(Date aBillDate) {theBillDate = aBillDate;}
     /** Дата счета */
     private Date theBillDate ;

     /** Единый номер пациента (представителя) */
     @Comment("Единый номер пациента (представителя)")
     public String getCommonNumber() {return theCommonNumber;}
     public void setCommonNumber(String aCommonNumber) {theCommonNumber = aCommonNumber;}
     /** Единый номер пациента (представителя) */
     private String theCommonNumber ;



     /** Тип полиса OMC*/
     @Comment("Тип полиса OMC")
     public String getMedPolicyType() {return theMedPolicyType;}
     public void setMedPolicyType(String aMedPolicyType) {theMedPolicyType = aMedPolicyType;}
     /** Тип полиса OMC*/
     private String theMedPolicyType ;

     /** Серия полиса */
     @Comment("Серия полиса")
     public String getMedPolicySeries() {return theMedPolicySeries;}
     public void setMedPolicySeries(String aMedPolicySeries) {theMedPolicySeries = aMedPolicySeries;}
     /** Серия полиса */
     private String theMedPolicySeries ;

     /** Номер полиса */
     @Comment("Номер полиса")
     public String getMedPolicyNumber() {return theMedPolicyNumber;}
     public void setMedPolicyNumber(String aMedPolicyNumber) {theMedPolicyNumber = aMedPolicyNumber;}
     /** Номер полиса */
     private String theMedPolicyNumber ;

     /** Страх. компания (федеральный 5 значный код) */
     @Comment("Страх. компания (федеральный код)")
     public String getInsuranceCompanyCode() {return theInsuranceCompanyCode;}
     public void setInsuranceCompanyCode(String aInsuranceCompanyCode) {theInsuranceCompanyCode = aInsuranceCompanyCode;}
     /** Страх. компания (федеральный код) */
     private String theInsuranceCompanyCode ;

     /** Название страх. компании */
     @Comment("Название страх. компании")
     @Column(length= 2500)
     public String getInsuranceCompanyName() {return theInsuranceCompanyName;}
     public void setInsuranceCompanyName(String aInsuranceCompanyName) {theInsuranceCompanyName = aInsuranceCompanyName;}
     /** Название страх. компании */
     private String theInsuranceCompanyName ;

     /** ОГРН страховой компании */
     @Comment("ОГРН страховой компании")
     public String getInsuranceCompanyOgrn() {return theInsuranceCompanyOgrn;}
     public void setInsuranceCompanyOgrn(String aInsuranceCompanyOgrn) {theInsuranceCompanyOgrn = aInsuranceCompanyOgrn;}
     /** ОГРН страховой компании */
     private String theInsuranceCompanyOgrn ;

    /** Регион нахождения страховой компании */
    @Comment("Регион нахождения страховой компании")
    public String getInsuranceCompanyTerritory() {return theInsuranceCompanyTerritory;}
    public void setInsuranceCompanyTerritory(String aInsuranceCompanyTerritory) {theInsuranceCompanyTerritory = aInsuranceCompanyTerritory;}
    /** Регион нахождения страховой компании */
    private String theInsuranceCompanyTerritory ;

     /** ИД отделения СЛО */
     @Comment("ИД отделения СЛО")
     public Long getDepartmentId() {return theDepartmentId;}
     public void setDepartmentId(Long aDepartmentId) {theDepartmentId = aDepartmentId;}
     /** ИД отделения СЛО */
     private Long theDepartmentId ;

      /** Услуги по случаю */
      @Comment("Услуги по случаю")
      @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      public List<EntryMedService> getMedServices() {return theMedServices;}
      public void setMedServices(List<EntryMedService> aMedServices) {theMedServices = aMedServices;}
      /** Услуги по случаю */
      private List<EntryMedService> theMedServices ;

     /** Список диагнозов по случаю */
     @Comment("Список диагнозов по случаю")
     @OneToMany(mappedBy="entry", cascade= CascadeType.ALL , fetch = FetchType.LAZY)
     public List<EntryDiagnosis> getDiagnosis() {return theDiagnosis;}
     public void setDiagnosis(List<EntryDiagnosis> aDiagnosis) {theDiagnosis = aDiagnosis;}
     /** Список диагнозов по случаю */
     private List<EntryDiagnosis> theDiagnosis ;

     /** Полис представителя */
     @Comment("Полис представителя")
     public String getKinsmanSnils() {return theKinsmanSnils;}
     public void setKinsmanSnils(String aKinsmanSnils) {theKinsmanSnils = aKinsmanSnils;}
     /** Полис представителя */
     private String theKinsmanSnils ;

    /** КСГ */
    @Comment("КСГ")
    @OneToOne
    public VocKsg getKsg() {return theKsg;}
    public void setKsg(VocKsg aKsg) {theKsg = aKsg;}
    /** КСГ */
    private VocKsg theKsg ;

    /** Список операций */
   @Comment("Список операций")
   @Column(length= 2500)
   public String getOperationList() {return theOperationList;}
   public void setOperationList(String aOperationList) {theOperationList = aOperationList;}
   /** Список операций */
   private String theOperationList ;

   /** Список диагнозов по случаю */
   @Comment("Список диагнозов по случаю")
   @Column(length= 2500)
   public String getDiagnosisList() {return theDiagnosisList;}
   public void setDiagnosisList(String aDiagnosisList) {theDiagnosisList = aDiagnosisList;}
   /** Список диагнозов по случаю */
   private String theDiagnosisList ;


    /** Идентификатор пациента во внешней системе */
    @Comment("Идентификатор пациента во внешней системе")
    public Long getExternalPatientId() {return theExternalPatientId;}
    public void setExternalPatientId(Long aExternalPatientId) {theExternalPatientId = aExternalPatientId;}
    /** Идентификатор пациента во внешней системе */
    private Long theExternalPatientId ;

   /** Фамилия представителя */
   @Comment("Фамилия представителя")
   public String getKinsmanLastname() {return theKinsmanLastname;}
   public void setKinsmanLastname(String aKinsmanLastname) {theKinsmanLastname = aKinsmanLastname;}
   /** Фамилия представителя */
   private String theKinsmanLastname ;

   /** Имя представитель */
   @Comment("Имя представитель")
   public String getKinsmanFirstname() {return theKinsmanFirstname;}
   public void setKinsmanFirstname(String aKinsmanFirstname) {theKinsmanFirstname = aKinsmanFirstname;}
   /** Имя представитель */
   private String theKinsmanFirstname ;

   /** Отчество представителя */
   @Comment("Отчество представителя")
   public String getKinsmanMiddlename() {return theKinsmanMiddlename;}
   public void setKinsmanMiddlename(String aKinsmanMiddlename) {theKinsmanMiddlename = aKinsmanMiddlename;}
   /** Отчество представителя */
   private String theKinsmanMiddlename ;

   /** Дата рождения представителя */
   @Comment("Дата рождения представителя")
   public Date getKinsmanBirthDate() {return theKinsmanBirthDate;}
   public void setKinsmanBirthDate(Date aKinsmanBirthDate) {theKinsmanBirthDate = aKinsmanBirthDate;}
   /** Дата рождения представителя */
   private Date theKinsmanBirthDate ;

   /** Пол представителя */
   @Comment("Пол представителя")
   public String getKinsmanSex() {return theKinsmanSex;}
   public void setKinsmanSex(String aKinsmanSex) {theKinsmanSex = aKinsmanSex;}
   /** Пол представителя */
   private String theKinsmanSex ;

   /** Тип родственной связи */
   @Comment("Тип родственной связи")
   public String getKinsmanRole() {return theKinsmanRole;}
   public void setKinsmanRole(String aKinsmanRole) {theKinsmanRole = aKinsmanRole;}
   /** Тип родственной связи */
   private String theKinsmanRole ;

    /** Фамилия пациента */
    @Comment("Фамилия пациента")
    public String getLastname() {return theLastname;}
    public void setLastname(String aLastname) {theLastname = aLastname;}
    /** Фамилия пациента */
    private String theLastname ;

    /** Имя пациента */
    @Comment("Имя пациента")
    public String getFirstname() {return theFirstname;}
    public void setFirstname(String aFirstname) {theFirstname = aFirstname;}
    /** Имя пациента */
    private String theFirstname ;

    /** Отчество пациента */
    @Comment("Отчество пациента")
    public String getMiddlename() {return theMiddlename;}
    public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}
    /** Отчество пациента */
    private String theMiddlename ;

    /** СНИЛС пациента */
    @Comment("СНИЛС пациента")
    public String getPatientSnils() {return thePatientSnils;}
    public void setPatientSnils(String aPatientSnils) {thePatientSnils = aPatientSnils;}
    /** СНИЛС пациента */
    private String thePatientSnils ;

    /** Гражданство пациента */
    @Comment("Гражданство пациента")
    public String getNationality() {return theNationality;}
    public void setNationality(String aNationality) {theNationality = aNationality;}
    /** Гражданство пациента */
    private String theNationality ;

    /** КЛАДР регистрации пациента (представителя) */
    @Comment("КЛАДР регистрации пациента (представителя)")
    public String getKLADRRegistration() {return theKLADRRegistration;}
    public void setKLADRRegistration(String aKLADRRegistration) {theKLADRRegistration = aKLADRRegistration;}
    /** КЛАДР регистрации пациента (представителя) */
    private String theKLADRRegistration ;

    /** КЛАДР проживания пациента (представителя) */
    @Comment("КЛАДР проживания пациента (представителя)")
    public String getKLADRReal() {return theKLADRReal;}
    public void setKLADRReal(String aKLADRReal) {theKLADRReal = aKLADRReal;}
    /** КЛАДР проживания пациента (представителя) */
    private String theKLADRReal ;

    /** Адрес проживания пациента (представителя) */
    @Comment("Адрес проживания пациента (представителя)")
    public String getAddressRegistration() {return theAddressRegistration;}
    public void setAddressRegistration(String aAddressRegistration) {theAddressRegistration = aAddressRegistration;}
    /** Адрес проживания пациента (представителя) */
    private String theAddressRegistration ;

    /** Адрес проживания пациента (представителя) */
    @Comment("Адрес проживания пациента (представителя)")
    public String getAddressReal() {return theAddressReal;}
    public void setAddressReal(String aAddressReal) {theAddressReal = aAddressReal;}
    /** Адрес проживания пациента (представителя) */
    private String theAddressReal ;

    /** Город проживания (иногородний) */
    @Comment("Город проживания (иногородний)")
    public String getSettlementNonResident() {return theSettlementNonResident;}
    public void setSettlementNonResident(String aSettlementNonResident) {theSettlementNonResident = aSettlementNonResident;}
    /** Город проживания (иногородний) */
    private String theSettlementNonResident ;

    /** Тип паспорта (ДУЛ) */
    @Comment("Тип паспорта (ДУЛ)")
    public String getPassportType() {return thePassportType;}
    public void setPassportType(String aPassportType) {thePassportType = aPassportType;}
    /** Тип паспорта (ДУЛ) */
    private String thePassportType ;

    /** Серия паспорта */
    @Comment("Серия паспорта")
    public String getPassportSeries() {return thePassportSeries;}
    public void setPassportSeries(String aPassportSeries) {thePassportSeries = aPassportSeries;}
    /** Серия паспорта */
    private String thePassportSeries ;

    /** Номер паспорта */
    @Comment("Номер паспорта")
    public String getPassportNumber() {return thePassportNumber;}
    public void setPassportNumber(String aPassportNumber) {thePassportNumber = aPassportNumber;}
    /** Номер паспорта */
    private String thePassportNumber ;

    /** Дата выдачи паспорта */
    @Comment("Дата выдачи паспорта")
    public Date getPassportDateIssued() {return thePassportDateIssued;}
    public void setPassportDateIssued(Date aPassportDateIssued) {thePassportDateIssued = aPassportDateIssued;}
    /** Дата выдачи паспорта */
    private Date thePassportDateIssued ;
    /** Кем выдан паспорт */
    @Comment("Кем выдан паспорт")
    public String getPassportWhomIssued() {return thePassportWhomIssued;}
    public void setPassportWhomIssued(String aPassportWhomIssued) {thePassportWhomIssued = aPassportWhomIssued;}
    /** Кем выдан паспорт */
    private String thePassportWhomIssued ;

    /** Дата рождения пациента */
    @Comment("Дата рождения пациента")
    public Date getBirthDate() {return theBirthDate;}
    public void setBirthDate(Date aBirthDate) {theBirthDate = aBirthDate;}
    /** Дата рождения пациента */
    private Date theBirthDate ;

    /** Пол пациента */
    @Comment("Пол пациента")
    public String getSex() {return theSex;}
    public void setSex(String aSex) {theSex = aSex;}
    /** Пол пациента */
    private String theSex ;

    /** Социальный статус пациента */
    @Comment("Социальный статус пациента")
    public String  getSocialStatus() {return theSocialStatus;}
    public void setSocialStatus(String  aSocialStatus) {theSocialStatus = aSocialStatus;}
    /** Социальный статус пациента */
    private String  theSocialStatus ;

    /** Каким по счету родился ребенок */
    @Comment("Каким по счету родился ребенок")
    public Long getBirthOrder() {return theBirthOrder;}
    public void setBirthOrder(Long aBirthOrder) {theBirthOrder = aBirthOrder;}
    /** Каким по счету родился ребенок */
    private Long theBirthOrder ;

    /** Код ЛПУ */
    @Comment("Код ЛПУ")
    public String getLpuCode() {return theLpuCode;}
    public void setLpuCode(String aLpuCode) {theLpuCode = aLpuCode;}
    /** Код ЛПУ */
    private String theLpuCode ;

    /** ВМП - кол-во установленных стентов */
    @Comment("ВМП - кол-во установленных стентов")
    public Long getVMPStantAmount() {return theVMPStantAmount;}
    public void setVMPStantAmount(Long aVMPStantAmount) {theVMPStantAmount = aVMPStantAmount;}
    /** ВМП - кол-во установленных стентов */
    private Long theVMPStantAmount ;

    /** Номер талона ВМП */
    @Comment("Номер талона ВМП")
    public String getVMPTicketNumber() {return theVMPTicketNumber;}
    public void setVMPTicketNumber(String aVMPTicketNumber) {theVMPTicketNumber = aVMPTicketNumber;}
    /** Номер талона ВМП */
    private String theVMPTicketNumber ;

    /** Талон ВМП - дата выдачи талона */
    @Comment("Талон ВМП - дата выдачи талона")
    public Date getVMPTicketDate() {return theVMPTicketDate;}
    public void setVMPTicketDate(Date aVMPTicketDate) {theVMPTicketDate = aVMPTicketDate;}
    /** Талон ВМП - дата выдачи талона */
    private Date theVMPTicketDate ;

    /** Талон ВМП - дата плановой госпитализации */
    @Comment("Талон ВМП - дата плановой госпитализации")
    public Date getVMPPlanHospDate() {return theVMPPlanHospDate;}
    public void setVMPPlanHospDate(Date aVMPPlanHospDate) {theVMPPlanHospDate = aVMPPlanHospDate;}
    /** Талон ВМП - дата плановой госпитализации */
    private Date theVMPPlanHospDate ;

    /** Вид ВМП */
    @Comment("Вид ВМП")
    public String getVMPKind() {return theVMPKind;}
    public void setVMPKind(String aVMPKind) {theVMPKind = aVMPKind;}
    /** Вид ВМП */
    private String theVMPKind ;

    /** Метод ВМП */
    @Comment("Метод ВМП")
    public String getVMPMethod() {return theVMPMethod;}
    public void setVMPMethod(String aVMPMethod) {theVMPMethod = aVMPMethod;}
    /** Метод ВМП */
    private String theVMPMethod ;

    /** Поток обслуживания */
    @Comment("Поток обслуживания")
    public String getServiceStream() {return theServiceStream;}
    public void setServiceStream(String aServiceStream) {theServiceStream = aServiceStream;}
    /** Поток обслуживания */
    private String theServiceStream ;
    /** Были сообщения в полицию */
    @Comment("Были сообщения в полицию")
    public Boolean getIsCriminalMessage() {return theIsCriminalMessage;}
    public void setIsCriminalMessage(Boolean aIsCriminalMessage) {theIsCriminalMessage = aIsCriminalMessage;}
    /** Были сообщения в полицию */
    private Boolean theIsCriminalMessage ;

    /** Находился по уходу за родственников */
    @Comment("Находился по уходу за родственников")
    public Boolean getHotelServices() {return theHotelServices;}
    public void setHotelServices(Boolean aHotelServices) {theHotelServices = aHotelServices;}
    /** Находился по уходу за родственников */
    private Boolean theHotelServices ;

    /** Тип стационара */
    @Comment("Тип стационара") //Дневной, круглосуточный
    public String getStacType() {return theStacType;}
    public void setStacType(String aStacType) {theStacType = aStacType;}
    /** Тип стационара */
    private String theStacType ;

    /** ЛПУ прикрепления */
    @Comment("ЛПУ прикрепления")
    public String getAttachedLpu() {return theAttachedLpu;}
    public void setAttachedLpu(String aAttachedLpu) {theAttachedLpu = aAttachedLpu;}
    /** ЛПУ прикрепления */
    private String theAttachedLpu ;

    /** Профиль помощи */
    @Comment("Профиль помощи")
    public String getHelpKind() {return theHelpKind;}
    public void setHelpKind(String aHelpKind) {theHelpKind = aHelpKind;}
    /** Профиль помощи */
    private String theHelpKind ;
    /** Идентификатор случая во внешней системе */
    @Comment("Идентификатор случая во внешней системе")
    public Long getExternalId() {return theExternalId;}
    public void setExternalId(Long aExternalId) {theExternalId = aExternalId;}
    /** Идентификатор случая во внешней системе */
    private Long theExternalId ;

    /** Идентификатор пред. случая во внешней системе */
    @Comment("Идентификатор пред. случая во внешней системе")
    public Long getExternalPrevMedcaseId() {return theExternalPrevMedcaseId;}
    public void setExternalPrevMedcaseId(Long aExternalPrevMedcaseId) {theExternalPrevMedcaseId = aExternalPrevMedcaseId;}
    /** Идентификатор пред. случая во внешней системе */
    private Long theExternalPrevMedcaseId ;

    /** Идентификатор госпитализации во внешней системе */
    @Comment("Идентификатор госпитализации во внешней системе")
    public Long getExternalParentId() {return theExternalParentId;}
    public void setExternalParentId(Long aExternalParentId) {theExternalParentId = aExternalParentId;}
    /** Идентификатор госпитализации во внешней системе */
    private Long theExternalParentId ;

    /** Заполнение */
    @Comment("Заполнение")
    @OneToOne
    public E2ListEntry getListEntry() {return theListEntry;}
    public void setListEntry(E2ListEntry aListEntry) {theListEntry = aListEntry;}
    /** Заполнение */
    private E2ListEntry theListEntry ;

    /** Дата начала случая */
    @Comment("Дата начала случая")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала случая */
    private Date theStartDate ;
    
    /** Время начала случая */
    @Comment("Время начала случая")
    public Time getStartTime() {return theStartTime;}
    public void setStartTime(Time aStartTime) {theStartTime = aStartTime;}
    /** Время начала случая */
    private Time theStartTime ;
    
    /** Дата окончания случая */
    @Comment("Дата окончания случая")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания случая */
    private Date theFinishDate ;

    /** Время окончания случая */
    @Comment("Время окончания случая")
    public Time getFinishTime() {return theFinishTime;}
    public void setFinishTime(Time aFinishTime) {theFinishTime = aFinishTime;}
    /** Время окончания случая */
    private Time theFinishTime ;

    /** Количество календарных дней */
    @Comment("Количество календарных дней")
    public Long getCalendarDays() {return theCalendarDays;}
    public void setCalendarDays(Long aCalendarDays) {theCalendarDays = aCalendarDays;}
    /** Количество календарных дней */
    private Long theCalendarDays ;

    /** Количество койкодней */
    @Comment("Количество койкодней")
    public Long getBedDays() {return theBedDays;}
    public void setBedDays(Long aBedDays) {theBedDays = aBedDays;}
    /** Количество койкодней */
    private Long theBedDays ;

    /** Номер истории болезни */
    @Comment("Номер истории болезни")
    public String getHistoryNumber() {return theHistoryNumber;}
    public void setHistoryNumber(String aHistoryNumber) {theHistoryNumber = aHistoryNumber;}
    /** Номер истории болезни */
    private String theHistoryNumber ;

    /** Название отделения */
    @Comment("Название отделения")
    public String getDepartmentName() {return theDepartmentName;}
    public void setDepartmentName(String aDepartmentName) {theDepartmentName = aDepartmentName;}
    /** Название отделения */
    private String theDepartmentName ;

    /** Тип отделения */
    @Comment("Тип отделения")
    public String getDepartmentType() {return theDepartmentType;}
    public void setDepartmentType(String aDepartmentType) {theDepartmentType = aDepartmentType;}
    /** Тип отделения */
    private String theDepartmentType ;

    /** Код отделения */
    @Comment("Код отделения")
    public String getDepartmentCode() {return theDepartmentCode;}
    public void setDepartmentCode(String aDepartmentCode) {theDepartmentCode = aDepartmentCode;}
    /** Код отделения */
    private String theDepartmentCode ;

    /** ФИО врача */
    @Comment("ФИО врача")
    public String getDoctorName() {return theDoctorName;}
    public void setDoctorName(String aDoctorName) {theDoctorName = aDoctorName;}
    /** ФИО врача */
    private String theDoctorName ;

    /** Должность врача */
    @Comment("Должность врача")
    public String getDoctorWorkfunction() {return theDoctorWorkfunction;}
    public void setDoctorWorkfunction(String aDoctorWorkfunction) {theDoctorWorkfunction = aDoctorWorkfunction;}
    /** Должность врача */
    private String theDoctorWorkfunction ;

    /** СНИЛС врача */
    @Comment("СНИЛС врача")
    public String getDoctorSnils() {return theDoctorSnils;}
    public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}
    /** СНИЛС врача */
    private String theDoctorSnils ;

    /** Экстренность */
    @Comment("Экстренность")
    public Boolean getIsEmergency() {return theIsEmergency;}
    public void setIsEmergency(Boolean aIsEmergency) {theIsEmergency = aIsEmergency;}
    /** Экстренность */
    private Boolean theIsEmergency ;

    /** Направившее ЛПУ */
    @Comment("Направившее ЛПУ")
    public String getDirectLpu() {return theDirectLpu;}
    public void setDirectLpu(String aDirectLpu) {theDirectLpu = aDirectLpu;}
    /** Направившее ЛПУ */
    private String theDirectLpu ;

    /** Вес новорожденного */
    @Comment("Вес новорожденного")
    public Long getNewbornWeight() {return theNewbornWeight;}
    public void setNewbornWeight(Long aNewbornWeight) {theNewbornWeight = aNewbornWeight;}
    /** Вес новорожденного */
    private Long theNewbornWeight ;

    /** Тип направившего ЛПУ */
    @Comment("Тип направившего ЛПУ")
    public String getDirectLpuType() {return theDirectLpuType;}
    public void setDirectLpuType(String aDirectLpuType) {theDirectLpuType = aDirectLpuType;}
    /** Тип направившего ЛПУ */
    private String theDirectLpuType ;

    /** Номер направление ФОМС */
    @Comment("Номер направление ФОМС")
    public String getTicket263Number() {return theTicket263Number;}
    public void setTicket263Number(String aTicket263Number) {theTicket263Number = aTicket263Number;}
    /** Номер направление ФОМС */
    private String theTicket263Number ;

    /** Результат госпитализации */ //vho.code||'#'||vrd.code||'#'||vhr.omcCode
    @Comment("Результат госпитализации")
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}
    /** Результат госпитализации */
    private String theResult ;

    /** Тип коек */
    @Comment("Тип коек") //Круглосуточные, дневные
    public String getBedSubType() {return theBedSubType;}
    public void setBedSubType(String aBedSubType) {theBedSubType = aBedSubType;}
    /** Тип коек */
    private String theBedSubType ;

    /** Дата начала госпитализации */
    @Comment("Дата начала госпитализации")
    public Date getHospitalStartDate() {return theHospitalStartDate;}
    public void setHospitalStartDate(Date aHospitalStartDate) {theHospitalStartDate = aHospitalStartDate;}
    /** Дата начала госпитализации */
    private Date theHospitalStartDate ;

    /** Дата окончания госпитализации */
    @Comment("Дата окончания госпитализации")
    public Date getHospitalFinishDate() {return theHospitalFinishDate;}
    public void setHospitalFinishDate(Date aHospitalFinishDate) {theHospitalFinishDate = aHospitalFinishDate;}
    /** Дата окончания госпитализации */
    private Date theHospitalFinishDate ;

    /** Достигнутый результат */
    @Comment("Достигнутый результат")
    public String getReachedResult() {return theReachedResult;}
    public void setReachedResult(String aReachedResult) {theReachedResult = aReachedResult;}
    /** Достигнутый результат */
    private String theReachedResult ;

    /** Повторная госпитализация */
    @Comment("Повторная госпитализация")
    public String getReHospitalization() {return theReHospitalization;}
    public void setReHospitalization(String aReHospitalization) {theReHospitalization = aReHospitalization;}
    /** Повторная госпитализация */
    private String theReHospitalization ;

    /** Услуги */
    @Comment("Услуги")
    public String getServices() {return theServices;}
    public void setServices(String aServices) {theServices = aServices;}
    /** Услуги */
    private String theServices ;

    /** Цена случая */
    @Comment("Цена случая")
    public BigDecimal getCost() {return theCost;}
    public void setCost(BigDecimal aCost) {theCost = aCost;}
    /** Цена случая */
    private BigDecimal theCost ;

    /** Ручное редактирование КСГ */
    @Comment("Ручное редактирование КСГ")
    public Boolean getIsManualKsg() {return theIsManualKsg;}
    public void setIsManualKsg(Boolean aIsManualKsg) {theIsManualKsg = aIsManualKsg;}
    /** Ручное редактирование цены */
    private Boolean theIsManualKsg ;

    @PrePersist
    void onPrePersist() {
        if (theIsEmergency==null) {theIsEmergency=false;}
        if (theIsCriminalMessage==null) {theIsCriminalMessage=false;}
        if (theNoOmcDepartment==null) {theNoOmcDepartment=false;}

    }

}
