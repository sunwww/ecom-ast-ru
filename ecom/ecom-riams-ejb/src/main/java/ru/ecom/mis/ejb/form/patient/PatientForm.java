package ru.ecom.mis.ejb.form.patient;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.interceptors.PatientCreateInterceptor;
import ru.ecom.mis.ejb.form.patient.interceptors.PatientDynamicSecurityInterceptor;
import ru.ecom.mis.ejb.form.patient.interceptors.PatientSaveInterceptor;
import ru.ecom.mis.ejb.form.patient.interceptors.PatientViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoInputNonLat;
import ru.nuzmsh.forms.validator.transforms.DoTrimString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.MaxLength;
import ru.nuzmsh.forms.validator.validators.MinLength;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.SnilsString;
import ru.nuzmsh.forms.validator.validators.VInputFIOByMaskOmc;
import ru.nuzmsh.forms.validator.validators.VInputNonLat;

/**
 * Пациент
 */
@EntityForm
@EntityFormPersistance(clazz = Patient.class)
@Comment("Персона")
@WebTrail(comment = "Персона", nameProperties = {"patientInfo","patientSync"}
	, view = "entityView-mis_patient.do", shortView="entityShortView-mis_patient.do"
			,journal=true)
@EntityFormSecurityPrefix("/Policy/Mis/Patient")
@ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)

@ACreateInterceptors({
     @AEntityFormInterceptor(PatientCreateInterceptor.class)
       , @AEntityFormInterceptor(PatientSaveInterceptor.class)
})

@ASaveInterceptors(
        @AEntityFormInterceptor(PatientSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(PatientViewInterceptor.class)
)
public class PatientForm extends IdEntityForm {
    
	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Телефон */
	private String thePhone;

    @Comment("Синхронизация пациента")
    @Persist @DoUpperCase
    public String getPatientSync() {
        return thePatientSync;
    }
    
    public void setPatientSync(String aPatientSync) {
        thePatientSync = aPatientSync;
    }
    /* Синхронизация пациента */
    private String thePatientSync;
    /** Название ЛПУ */
    @Persist
    @Comment("Название ЛПУ")
    public String getLpuName() {    return theLpuName ;}
    public void setLpuName(String aLpuName ) {  theLpuName = aLpuName ; }

    /** Название участка */
    @Persist
    @Comment("Название участка")
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getLpuAreaName() {    return theLpuAreaName ;}
    public void setLpuAreaName(String aLpuAreaName ) {  theLpuAreaName = aLpuAreaName ; }


    /** Участок */
    @Persist
    @Comment("Участок")
    public Long getLpuArea() {    return theLpuArea ;}
    public void setLpuArea(Long aLpuArea ) {  theLpuArea = aLpuArea ; }

    /** Адрес участка */
    @Persist
    @Comment("Адрес участка")
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getLpuAreaAddressText() {    return theLpuAreaAddressText ;}
    public void setLpuAreaAddressText(Long aLpuAreaAddressText ) {  theLpuAreaAddressText = aLpuAreaAddressText ; }

    /** ЛПУ */
    @Persist
    @Comment("ЛПУ")
    public Long getLpu() {    return theLpu ;}
    public void setLpu(Long aLpu ) {  theLpu = aLpu ; }


    /** Имя */
    @Comment("Имя")
    @Persist
    @Required @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
    @VInputFIOByMaskOmc
    public String getFirstname() { return theFirstname ; }
    public void setFirstname(String aFirstname) { theFirstname = aFirstname ; }

    /** Фамилия */
    @Comment("Фамилия")
    @Persist
    @Required @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
    @VInputFIOByMaskOmc
    public String getLastname() { return theLastname ; }
    public void setLastname(String aLastname) { theLastname = aLastname ; }

    /** Отчество */
    @Comment("Отчество")
    @Persist
    @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
    @VInputFIOByMaskOmc
    public String getMiddlename() { return theMiddlename ; }
    public void setMiddlename(String aMiddlename) { theMiddlename = aMiddlename ; }

    /** Пол */
    @Comment("Пол")
    @Persist
    @Required
    public Long getSex() { return theSex ; }
    public void setSex(Long aSex) { theSex = aSex ; }

    /** Дата рождения */
    @Comment("Дата рождения")
    @Persist @MaxDateCurrent
    @Required @DateString @DoDateString
    public String getBirthday() { return theBirthday ; }
    public void setBirthday(String aBirthday) { theBirthday = aBirthday ; }

    /** Социальный статус */
    @Comment("Социальный статус")
    @Persist @Required
    public Long getSocialStatus() { return theSocialStatus ; }
    public void setSocialStatus(Long aSocialStatus) { theSocialStatus = aSocialStatus ; }

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    @Persist
    public String getPatientInfo() { return thePatientInfo ; }
    public void setPatientInfo(String aPatientInfo) { thePatientInfo = aPatientInfo ; }

    /** Номер паспорта */
    @Persist @DoUpperCase @DoTrimString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportNumber() { return thePassportNumber ; }
    public void setPassportNumber(String aPassportNumber) { thePassportNumber = aPassportNumber ; }

    /** Серия паспорта */
    @Persist @DoUpperCase @DoTrimString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportSeries() { return thePassportSeries ; }
    public void setPassportSeries(String aPassportSeries) { thePassportSeries = aPassportSeries ; }

    /** Дата выдачи */
    @Persist @DateString @DoDateString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportDateIssued() { return thePassportDateIssue ; }
    public void setPassportDateIssued(String aPassportDateIssue) { thePassportDateIssue = aPassportDateIssue ; }

    /** Кем выдан */
    @Persist @DoUpperCase @DoTrimString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportWhomIssued() { return thePassportWhomIssued ; }
    public void setPassportWhomIssued(String aPassportWhomIssued) { thePassportWhomIssued = aPassportWhomIssued ; }

    /** СНИЛС */
    @Persist @SnilsString
    public String getSnils() { return theSnils ; }
    public void setSnils(String aSnils) { theSnils = aSnils ; }

    /** Место работы */
    @Comment("Место работы")
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getWorks() { return theWorks ; }
    public void setWorks(Long aWorks) { theWorks = aWorks ; }

    /** Дом */
    @Persist
    @DoTrimString @DoInputNonLat @VInputNonLat
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getHouseNumber() { return theHouseNumber ; }
    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }

    /** Корпус */
    @Persist
    @DoUpperCase @DoTrimString @DoInputNonLat @VInputNonLat
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }

    /** Квартира */
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getFlatNumber() { return theFlatNumber ; }
    public void setFlatNumber(String aFlatNumber) { theFlatNumber = aFlatNumber ; }

    /** Адрес */
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getAddress() { return theAddress ; }
    public void setAddress(Long aAddress) { theAddress = aAddress ; }

    /** Тип удостоверения личности */
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getPassportType() { return thePassportType ; }
    public void setPassportType(Long aPassportType) { thePassportType = aPassportType ; }

    /** Полис прикрепления */
	@Comment("Полис прикрепления")
	@Persist
	public Long getAttachedOmcPolicy() {return theAttachedPolicyOmc;}
	public void setAttachedOmcPolicy(Long aAttachedPolicyOmc) {theAttachedPolicyOmc = aAttachedPolicyOmc;}
	
	/** Новые полис для прикрепления */
	@Comment("Полис для прикрепления")
	public MedPolicyOmcForm getPolicyOmcForm () {return thePolicyOmcForm ;}
	public void setPolicyOmcForm (MedPolicyOmcForm aPolicyOmcForm ) {thePolicyOmcForm  = aPolicyOmcForm ;}

	/** Прикреплен по полису */
	@Comment("Прикреплен по полису")
	public boolean isAttachedByPolicy() {return theAttachedByPolicy;}
	public void setAttachedByPolicy(boolean aAttachedByPolicy) {theAttachedByPolicy = aAttachedByPolicy;}

	/** Создать новый полис ОМС */
	public boolean getCreateNewOmcPolicy() {return theCreateNewOmcPolicy;}
	public void setCreateNewOmcPolicy(boolean aCreateNewOmcPolicy) {theCreateNewOmcPolicy = aCreateNewOmcPolicy;}
	
	/** Ведомственное прикрепление */
	@Comment("Ведомственное прикрепление")
	public boolean isAttachedByDepartment() {return theAttachedByDepartment;}
	public void setAttachedByDepartment(boolean aAttachedByDepartment) {theAttachedByDepartment = aAttachedByDepartment;}

	/** Дата смерти*/
	@Comment("Дата смерти")
	@Persist @DateString @DoDateString
	public String getDeathDate() {return theDeathDate;}
	public void setDeathDate(String aNewProperty) {theDeathDate = aNewProperty;}
	
	/** Префикс адреса проживания*/
	@Comment("Префикс адреса проживания")
	@Persist
	public Long getRealAddress() {return theRealAddress;}
	public void setRealAddress(Long aNewProperty) {theRealAddress = aNewProperty;}
	
	/**Квартира проживания*/
	@Comment("Квартира проживания")
	@Persist
	public String getRealFlatNumber() {return theRealFlatNumber;}
	public void setRealFlatNumber(String aNewProperty) {theRealFlatNumber = aNewProperty;}
	
	/**Корпус проживания*/
	@Persist
	@Comment("Корпус проживания")
	public String getRealHouseBuilding() {return theRealHouseBuilding;}
	public void setRealHouseBuilding(String aNewProperty) {theRealHouseBuilding = aNewProperty;}
	
	/** Дом проживания*/
	@Comment("Дом проживания")
	@Persist
	public String getRealHouseNumber() {return theRealHouseNumber;}
	public void setRealHouseNumber(String aNewProperty) {theRealHouseNumber = aNewProperty;}
	
	/**Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNewProperty) {theNoActuality = aNewProperty;}
	
	/** Район */
	@Comment("Район")
	@Persist
	public Long getRayon() {return theRayon;}
	public void setRayon(Long aRayon) {theRayon = aRayon;}
	
	
	/** Национальность */
	@Comment("Национальность")
	@Persist
	public Long getEthnicity() {return theEthnicity;}
	public void setEthnicity(Long aEthnicity) {theEthnicity = aEthnicity;}
	
	/** Семейное положение */
	@Comment("Семейное положение")
	@Persist
	public Long getMarriageStatus() {return theMarriageStatus;}
	public void setMarriageStatus(Long aMarriageStatus) {theMarriageStatus = aMarriageStatus;}
	
	/** Вид образования */
	@Comment("Вид образования")
	@Persist
	public Long getEducationType() {return theEducationType;}
	public void setEducationType(Long aEducationType) {theEducationType = aEducationType;}

	/** Должность */
	@Comment("Должность")
	@Persist
	public String getWorkPost() {return theWorkPost;}
	public void setWorkPost(String aWorkPost) {theWorkPost = aWorkPost;}
	
	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getNotice() {return theNotice;}
	public void setNotice(String aNotice) {theNotice = aNotice;}
	
	/** Иностранный адрес регистрации */
	@Comment("Иностранный адрес регистрации")
	@Persist
	public String getForeignRegistrationAddress() {
		return theForeignRegistrationAddress;
	}

	public void setForeignRegistrationAddress(String aForeignRegistrationAddress) {
		theForeignRegistrationAddress = aForeignRegistrationAddress;
	}

	/** Иностранный адрес регистрации */
	private String theForeignRegistrationAddress;
	
	/** Иностранный адрес проживания */
	@Comment("Иностранный адрес проживания")
	@Persist
	public String getForeignRealAddress() {
		return theForeignRealAddress;
	}

	public void setForeignRealAddress(String aForeignRealAddress) {
		theForeignRealAddress = aForeignRealAddress;
	}

	/** Гражданство */
	@Comment("Гражданство")
	@Persist
	public Long getNationality() {
		return theNationality;
	}

	public void setNationality(Long aNationality) {
		theNationality = aNationality;
	}

	/** Территория регистрации  (иногороднего) */
	@Comment("Территория регистрации")
	@Persist
	public Long getTerritoryRegistrationNonresident() {
		return theTerritoryRegistrationNonresident;
	}

	public void setTerritoryRegistrationNonresident(Long aTerritoryRegistrationNonresident) {
		theTerritoryRegistrationNonresident = aTerritoryRegistrationNonresident;
	}
	
	/** Район проживания  (иногороднего) */
	@Comment("Район проживания")
	@Persist
	public String getRegionRegistrationNonresident() {
		return theRegionRegistrationNonresident;
	}

	public void setRegionRegistrationNonresident(String aRegionRegistrationNonresident) {
		theRegionRegistrationNonresident = aRegionRegistrationNonresident;
	}

	/** Вид населенного пункта  (иногороднего) */
	@Comment("Вид населенного пункта")
	@Persist
	public Long getTypeSettlementNonresident() {
		return theTypeSettlementNonresident;
	}

	public void setTypeSettlementNonresident(Long aTypeSettlementNonresident) {
		theTypeSettlementNonresident = aTypeSettlementNonresident;
	}

	/** Населенный пункт  (иногороднего) */
	@Comment("Населенный пункт")
	@Persist
	public String getSettlementNonresident() {
		return theSettlementNonresident;
	}

	public void setSettlementNonresident(String aSettlementNonresident) {
		theSettlementNonresident = aSettlementNonresident;
	}

	/** Тип наименования улицы  (иногороднего) */
	@Comment("Тип наименования улицы")
	@Persist
	public Long getTypeStreetNonresident() {
		return theTypeStreetNonresident;
	}

	public void setTypeStreetNonresident(Long aTypeStreetNonresident) {
		theTypeStreetNonresident = aTypeStreetNonresident;
	}

	/** Наименование улицы  (иногороднего) */
	@Comment("Наименование улицы")
	@Persist
	public String getStreetNonresident() {
		return theStreetNonresident;
	}

	public void setStreetNonresident(String aStreetNonresident) {
		theStreetNonresident = aStreetNonresident;
	}

	/** Дом (иногороднего) */
	@Comment("Дом (иногороднего)")
	@Persist
	public String getHouseNonresident() {
		return theHouseNonresident;
	}

	public void setHouseNonresident(String aHouseNonresident) {
		theHouseNonresident = aHouseNonresident;
	}

	/** Корпус дома (иногороднего) */
	@Comment("Корпус дома (иногороднего)")
	@Persist
	public String getBuildingHousesNonresident() {
		return theBuildingHousesNonresident;
	}

	public void setBuildingHousesNonresident(String aBuildingHousesNonresident) {
		theBuildingHousesNonresident = aBuildingHousesNonresident;
	}

	/** Квартира (иногороднего) */
	@Comment("Квартира (иногороднего)")
	@Persist
	public String getApartmentNonresident() {
		return theApartmentNonresident;
	}

	public void setApartmentNonresident(String aApartmentNonresident) {
		theApartmentNonresident = aApartmentNonresident;
	}
	
	/** Дополнительный статус */
	@Comment("Дополнительный статус")
	@Persist @Required
	public Long getAdditionStatus() {return theAdditionStatus;}
	public void setAdditionStatus(Long aAdditionStatus) {theAdditionStatus = aAdditionStatus;}
	
	/** Адрес */
	@Comment("Адрес")
	@Persist
	public String getAddressInfo() {return theAddressInfo;}
	public void setAddressInfo(String aAddressInfo) {theAddressInfo = aAddressInfo;}

	/** Адрес */
	private String theAddressInfo;

	/** Дополнительный статус */
	private Long theAdditionStatus;
	/** Квартира (иногороднего) */
	private String theApartmentNonresident;
	/** Корпус дома (иногороднего) */
	private String theBuildingHousesNonresident;
	/** Дом (иногороднего) */
	private String theHouseNonresident;
	/** Наименование улицы  (иногороднего) */
	private String theStreetNonresident;
	/** Тип наименования улицы  (иногороднего) */
	private Long theTypeStreetNonresident;
	/** Населенный пункт  (иногороднего) */
	private String theSettlementNonresident;
	/** Вид населенного пункта  (иногороднего) */
	private Long theTypeSettlementNonresident;
	/** Район проживания  (иногороднего) */
	private String theRegionRegistrationNonresident;
	/** Территория регистрации  (иногороднего) */
	private Long theTerritoryRegistrationNonresident;
	/** Гражданство */
	private Long theNationality;
	/** Иностранный адрес проживания */
	private String theForeignRealAddress;
	
	
	/** Ведомственное прикрепление */
	private boolean theAttachedByDepartment;
	/** Создать новый полис ОМС */
	private boolean theCreateNewOmcPolicy;
	/** Прикреплен по полису */
	private boolean theAttachedByPolicy;
	/** Полис для прикрепления */
	private MedPolicyOmcForm thePolicyOmcForm = new MedPolicyOmcForm();
	/** Полис прикрепления */
	private Long theAttachedPolicyOmc;
    /** Тип удостоверения личности */
    private Long thePassportType ;
    /** Адрес */
    private Long theAddress ;
    /** Квартира */
    private String theFlatNumber ;
    /** Корпус */
    private String theHouseBuilding ;
    /** Дом */
    private String theHouseNumber ;
    /** Место работы */
    private Long theWorks ;
    /** СНИЛС */
    private String theSnils ;
    /** Кем выдан */
    private String thePassportWhomIssued ;
    /** Дата выдачи */
    private String thePassportDateIssue ;
    /** Серия паспорта */
    private String thePassportSeries ;
    /** Номер паспорта */
    private String thePassportNumber ;
    /** Информация о пациенте */
    private String thePatientInfo ;
    /** Социальный статус */
    private Long theSocialStatus ;
   /** Дата рождения */
    private String theBirthday ;
    /** Пол */
    private Long theSex ;
    /** Отчество */
    private String theMiddlename ;
    /** Фамилия */
    private String theLastname ;
    /** Имя */
    private String theFirstname ;
    /** ЛПУ */
    private Long theLpu ;
    /** Адрес участка */
    private Long theLpuAreaAddressText ;
    /** Участок */
    private Long theLpuArea ;
    /** Название участка */
    private String theLpuAreaName ;
    /** Название ЛПУ */
    private String theLpuName ;
	/**Дата смерти */
	private String theDeathDate;
	/**Префикс адреса проживания */
	private Long theRealAddress;
	/**Квартира проживания */
	private String theRealFlatNumber;
	/**Корпус проживания */
	private String theRealHouseBuilding;
	/**Дом проживания */
	private String theRealHouseNumber;
	/**Недействительность */
	private Boolean theNoActuality;
	/** Район */
	private Long theRayon;

	/** Национальность */
	private Long theEthnicity;
	/** Семейное положение */
	private Long theMarriageStatus;
	/** Вид образования */
	private Long theEducationType;
	/** Должность */
	private String theWorkPost;
	/** Примечание */
	private String theNotice;



	 /**
	  * Горожанин
	  */
	 @Comment("Горожанин")
	 @Persist
	 public Boolean getTownsman() {
	  return theTownsman;
	 }
	 public void setTownsman(Boolean aTownsman) {
	  theTownsman = aTownsman;
	 }
	 /**
	  * Горожанин
	  */
	 private Boolean theTownsman;
	 /**
	  * Участник ВОВ
	  */
	 @Comment("Участник ВОВ")
	 @Persist
	 public Boolean getGreatPatrioticWarPaticipant() {
	  return theGreatPatrioticWarPaticipant;
	 }
	 public void setGreatPatrioticWarPaticipant(Boolean aGreatPatrioticWarPaticipant) {
	  theGreatPatrioticWarPaticipant = aGreatPatrioticWarPaticipant;
	 }
	 /**
	  * Участник ВОВ
	  */
	 private Boolean theGreatPatrioticWarPaticipant;
	 /**
	  * Число законченных классов общеобразовательной школы
	  */
	 @Comment("Число законченных классов общеобразовательной школы")
	 @Persist
	 public Integer getGeneralEducationGradeNumber() {
	  return theGeneralEducationGradeNumber;
	 }
	 public void setGeneralEducationGradeNumber(Integer aGeneralEducationGradeNumber) {
	  theGeneralEducationGradeNumber = aGeneralEducationGradeNumber;
	 }
	 /**
	  * Число законченных классов общеобразовательной школы
	  */
	 private Integer theGeneralEducationGradeNumber;
	 /**
	  * Источник средств существования
	  */
	 @Comment("Источник средств существования")
	 @Persist
	 public Long getLivelihoodSource() {
	  return theLivelihoodSource;
	 }
	 public void setLivelihoodSource(Long aLivelihoodSource) {
	  theLivelihoodSource = aLivelihoodSource;
	 }
	 /**
	  * Источник средств существования
	  */
	 private Long theLivelihoodSource;
	 /**
	  * Условия проживания
	  */
	 @Comment("Условия проживания")
	 @Persist
	 public Long getResidenceConditions() {
	  return theResidenceConditions;
	 }
	 public void setResidenceConditions(Long aRecidenceConditions) {
	  theResidenceConditions = aRecidenceConditions;
	 }
	 /**
	  * Условия проживания
	  */
	 private Long theResidenceConditions;
	 /**
	  * Проживает в семье
	  */
	 @Comment("Проживает в семье")
	 @Persist
	 public Boolean getFamilyResident() {
	  return theFamilyResident;
	 }
	 public void setFamilyResident(Boolean aFamilyResident) {
	  theFamilyResident = aFamilyResident;
	 }
	 /**
	  * Проживает в семье
	  */
	 private Boolean theFamilyResident;
	 /**
	  * Дееспособный
	  */
	 @Comment("Дееспособный")
	 @Persist
	 public Boolean getIncapable() {
	  return theIncapable;
	 }
	 public void setIncapable(Boolean aCapable) {
	  theIncapable = aCapable;
	 }
		/** Почтовый индекс */
		@Comment("Почтовый индекс")
		@Persist
		public String getZipcode() {return theZipcode;	}
		public void setZipcode(String aZipcode) {theZipcode = aZipcode;}

		/** Почтовый индекс проживания */
		@Comment("Почтовый индекс проживания")
		@Persist
		public String getRealZipcode() {return theRealZipcode;}
		public void setRealZipcode(String aRealZipcode) {theRealZipcode = aRealZipcode;}
		
		/** Почтовый индекс иногороднего */
		@Comment("Почтовый индекс иногороднего")
		@Persist
		public String getNonresidentZipcode() {return theNonresidentZipcode;}
		public void setNonresidentZipcode(String aNonresidentZipcode) {theNonresidentZipcode = aNonresidentZipcode;}

		/** Почтовый индекс иногороднего */
		private String theNonresidentZipcode;
		/** Почтовый индекс проживания */
		private String theRealZipcode;
		/** Почтовый индекс */
		private String theZipcode;
	 /**
	  * Дееспособный
	  */
	 private Boolean theIncapable;
	 /**
	  * Дата первого заполнения
	  */
	 @Comment("Дата первого заполнения")
	 @Persist @DateString @DoDateString
	 public String getFirstRegistrationDate() {
	  return theFirstRegistrationDate;
	 }
	 public void setFirstRegistrationDate(String aFirstRegistrationDate) {
	  theFirstRegistrationDate = aFirstRegistrationDate;
	 }
	 /**
	  * Дата первого заполнения
	  */
	 private String theFirstRegistrationDate;
	 /** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата последнего редактирования */
	@Comment("Дата последнего редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

	/** Пользователь, последний редактировающий запись */
	@Comment("Пользователь, последний редактировающий запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Место рождения */
	@Comment("Место рождения")
	@Persist @DoUpperCase
	public String getBirthPlace() {return theBirthPlace;}
	public void setBirthPlace(String aBirthPlace) {theBirthPlace = aBirthPlace;}

	/** Новорожденный */
	@Comment("Новорожденный")
	@Persist
	public Long getNewborn() {return theNewborn;}
	public void setNewborn(Long aNewborn) {theNewborn = aNewborn;}

	/** Новорожденный */
	private Long theNewborn;
	/** Место рождения */
	private String theBirthPlace;
	/** Пользователь, последний редактировающий запись */
	private String theEditUsername;
	/** Дата последнего редактирования */
	private String theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
	
	/** Код подразделения, выдавшего паспорт */
	@Comment("Код подразделения, выдавшего паспорт")
	@Persist
	public String getPassportCodeDivision() {return thePassportCodeDivision;}
	public void setPassportCodeDivision(String aPassportCodeDivision) {thePassportCodeDivision = aPassportCodeDivision;}

	/** Код подразделения, выдавшего паспорт */
	private String thePassportCodeDivision;
	
	/** Мед.карта */
	@Comment("Мед.карта")
	public Long getMedcardLast() {
		return theMedcardLast;
	}

	public void setMedcardLast(Long aMedcardLast) {
		theMedcardLast = aMedcardLast;
	}

	/** Мед.карта */
	private Long theMedcardLast;
	/** Психиатрическая мед.карта */
	@Comment("Психиатрическая мед.карта")
	public Long getCareCard() {
		return theCareCard;
	}

	public void setCareCard(Long aCareCard) {
		theCareCard = aCareCard;
	}
	
	/** Возраст */
	@Comment("Возраст")
	public String getAge() {return theAge;}
	public void setAge(String aAge) {theAge = aAge;}

	/** Возраст */
	private String theAge;

	/** Психиатрическая мед.карта */
	private Long theCareCard;
	
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	@Persist
	public Long getPassportDivision() {
		return thePassportDivision;
	}

	public void setPassportDivision(Long aPassportDivision) {
		thePassportDivision = aPassportDivision;
	}

	/** Кем выдан паспорт */
	private Long thePassportDivision;
	/** Справочник по месту рождения */
	@Comment("Справочник по месту рождения")
	@Persist
	public Long getPassportBirthPlace() {return thePassportBirthPlace;}
	public void setPassportBirthPlace(Long aPassportBirthPlace) {thePassportBirthPlace = aPassportBirthPlace;}

	/** Справочник по месту рождения */
	private Long thePassportBirthPlace;
	
	/** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
	@Persist @MaxLength(value = 16) @MinLength(value = 16)
	public String getCommonNumber() {return theCommonNumber;}
	public void setCommonNumber(String aNumber) {theCommonNumber = aNumber;}

	/** Соотечественник */
	@Comment("Соотечественник")
	@Persist
	public Boolean getIsCompatriot() {return theIsCompatriot;}
	public void setIsCompatriot(Boolean aIsCompatriot) {theIsCompatriot = aIsCompatriot;}

	/** Соотечественник */
	private Boolean theIsCompatriot;
	/** Единый номер застрахованного */
	private String theCommonNumber;
	/** Категория ребенка */
	@Comment("Категория ребенка")
	@Persist
	public Long getCategoryChild() {return theCategoryChild;}
	public void setCategoryChild(Long aCategoryChild) {theCategoryChild = aCategoryChild;}

	/** Категория ребенка */
	private Long theCategoryChild;
	
}