package ru.ecom.mis.ejb.domain.patient;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcOksm;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcQnp;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcStreetT;
import ru.ecom.mis.ejb.domain.birth.voc.VocNewBorn;
import ru.ecom.mis.ejb.domain.disability.DisabilityCase;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.voc.VocAdditionStatus;
import ru.ecom.mis.ejb.domain.patient.voc.VocCategoryChild;
import ru.ecom.mis.ejb.domain.patient.voc.VocEthnicity;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdentityCard;
import ru.ecom.mis.ejb.domain.patient.voc.VocLivelihoodSource;
import ru.ecom.mis.ejb.domain.patient.voc.VocMarriageStatus;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.patient.voc.VocPassportBirthPlace;
import ru.ecom.mis.ejb.domain.patient.voc.VocPassportWhomIssue;
import ru.ecom.mis.ejb.domain.patient.voc.VocRayon;
import ru.ecom.mis.ejb.domain.patient.voc.VocResidenceConditions;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.patient.voc.VocSocialStatus;
import ru.ecom.mis.ejb.domain.vaccination.Vaccination;
import ru.ecom.mis.ejb.domain.worker.Education;
import ru.ecom.mis.ejb.domain.worker.LanguageSkill;
import ru.ecom.mis.ejb.domain.worker.WorkBook;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.domain.worker.voc.VocEducationType;
import ru.ecom.mis.ejb.uc.privilege.domain.Privilege;
import ru.ecom.poly.ejb.domain.Medcard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;


/**
 * Пациент
 **/
@Entity
//@EntityListeners(PatientListener.class) // теперь вызывается при сохранении формы
@AIndexes({
   	    @AIndex(unique = false, properties= {"dtype"})
   	  , @AIndex(unique = false, properties= {"patientSync"})
   	    , @AIndex(unique = false, properties= {"snils"})
   	    , @AIndex(unique = false, properties= {"passportNumber"})
   	    , @AIndex(unique = false, properties= {"passportSeries"})
   	    , @AIndex(unique = false, properties= {"editDate"})
   	  , @AIndex(unique = false, properties= {"createDate"})
      //, @AIndex(unique = false, properties= {"sex"})
      , @AIndex(unique = false, properties= {"lastname","firstname","middlename", "birthday"})
      , @AIndex(unique = false, properties= {"lastname"})
      , @AIndex(unique = false, properties= {"lastname","firstname"})
      , @AIndex(unique = false, properties= {"lastname","firstname","middlename"})
      , @AIndex(unique = false, properties= ("lpuArea"))
      , @AIndex(unique = false, properties= ("lpuAreaAddressText"))
      , @AIndex(unique = false, properties= ("lpu"))
      , @AIndex(unique = false, properties= ("address"))
      // используется при прикреплении при изменении LpuAreaText в AddressPointServiceBean
   	    /*
      , @AIndex(unique = false, properties= {"attachedOmcPolicy","address","birthday"})
      , @AIndex(unique = false, properties= {"attachedOmcPolicy","address","houseNumber","birthday"})
      , @AIndex(unique = false, properties= {"attachedOmcPolicy","address","houseNumber","flatNumber","birthday"})
      , @AIndex(unique = false, properties= {"attachedOmcPolicy","address","houseNumber","houseBuilding","birthday"})
      , @AIndex(unique = false, properties= {"attachedOmcPolicy","address","houseNumber","houseBuilding","flatNumber","birthday"})
      */
})
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
public class Patient extends BaseEntity{
	/** Телефон */
	@Comment("Телефон")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Телефон */
	private String thePhone;
	
	/** DTYPE */
	//@Comment("DTYPE")
	//public String getDTYPE() {return theDTYPE;}
	//public void setDTYPE(String aDTYPE) {theDTYPE = aDTYPE;}

	/** Вакцинации */
	/*@Comment("Вакцинации")
	@OneToMany(mappedBy="patient", cascade=CascadeType.ALL)
	public List<Vaccination> getVaccinations() {return theVaccinations;}
	public void setVaccinations(List<Vaccination> aVaccinations) {theVaccinations = aVaccinations;}
	*/
	/** Сотрудники */
	@Comment("Сотрудники")
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<Worker> getWorkers() {return theWorkers;}
	public void setWorkers(List<Worker> aWorkers) {theWorkers = aWorkers;}

	/** Прикрепление по адресу */
	public static String ATTACHED_TYPE_ADDRESS = null ;
	
	/** Прикрепление по страховой компании/заявлению */
	public static String ATTACHED_TYPE_SK = "2" ;
	
    /** Участок основного прикрепления*/
    @Comment("Участок основного прикрепления")
    @OneToOne
    public LpuArea getLpuArea() { return theLpuArea ; }
    public void setLpuArea(LpuArea aLpuArea) { theLpuArea = aLpuArea ; }
     
    /** Адрес участка основного прикрепления */
    @Comment("Адрес участка основного прикрепления")
    @OneToOne
    public LpuAreaAddressText getLpuAreaAddressText() { return theLpuAreaAddressText ; }
    public void setLpuAreaAddressText(LpuAreaAddressText aLpuAreaAddressText) { theLpuAreaAddressText = aLpuAreaAddressText ; }
    
    /** ЛПУ основного прикрепления*/
    @Comment("ЛПУ основного прикрепления")
    @OneToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }
    
    /** Название ЛПУ */
    @Transient
    public String getLpuName() { return theLpu!=null ? theLpu.getFullname() : "" ; }
    public void setLpuName(String aLpuName) {}

    /** Название участка */
    @Transient
    public String getLpuAreaName() { return theLpuArea!=null ? theLpuArea.getName() : ""; }
    public void setLpuAreaName(String aLpuAreaName) {}

    /** Полис прикрепления */
	@Comment("Полис прикрепления")
	@OneToOne
	public MedPolicyOmc getAttachedOmcPolicy() {return theAttachedOmcPolicy;}
	public void setAttachedOmcPolicy(MedPolicyOmc aAttachedOmcPolicy) {theAttachedOmcPolicy = aAttachedOmcPolicy;}

    /** Ведомственное прикрепление */
	@Comment("Ведомственное прикрепление")
	@OneToMany(cascade=ALL, mappedBy="patient")
	public List<LpuAttachedByDepartment> getAttachedByDepartments() {return theAttachedByDepartments;}
	public void setAttachedByDepartments(List<LpuAttachedByDepartment> aAttachedByDepartments) {theAttachedByDepartments = aAttachedByDepartments;}
	
	/** СМО */
	@Comment("СМО")
	@OneToMany(mappedBy="patient", cascade=CascadeType.ALL)
	public List<MedCase> getMedCases() {return theMedCases;}
	public void setMedCases(List<MedCase> aMedCases) {theMedCases = aMedCases;}

//    /** Инвалидность */
//    private Invalidity theInvalidity ;

	/** Случаи нетрудоспособности */
	@Comment("Случаи нетрудоспособности")
	@OneToMany(mappedBy="patient", cascade=CascadeType.ALL)
	public List<DisabilityCase> getDisabilityCases() {return theDisabilityCases;}
	public void setDisabilityCases(List<DisabilityCase> aDisabilityCases) {theDisabilityCases = aDisabilityCases;}

    /** Трудовые книги */
    @Comment("Трудовые книги")
    @OneToMany(mappedBy = "person", cascade= ALL)
    public List<WorkBook> getWorkBook() { return theWorkBook ; }
    public void setWorkBook(List<WorkBook> aWorkBook) { theWorkBook = aWorkBook ; }
   
	/** Знание языков */
	@Comment("Знание языков")
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<LanguageSkill> getLanguageSkills() {return theLanguageSkills;}
	public void setLanguageSkills(List<LanguageSkill> aLanguageSkills) {theLanguageSkills = aLanguageSkills;}
	
    /** Образования */
    @Comment("Образования")
	@OneToMany(mappedBy = "person", cascade= ALL)
    public List<Education> getEducations() { return theEducations ; }
    public void setEducations(List<Education> aEducations) { theEducations = aEducations ; }
    
    /** Квалификации */
    //@Comment("Квалификации")
    //@OneToMany(mappedBy = "person", cascade= ALL)
    //public List<Qualification> getQualifications() { return theQualifications ; }
    //public void setQualifications(List<Qualification> aQualifications) { theQualifications = aQualifications ; }
	
	/** Награды */
	//@Comment("Награды")
	//@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	//public List<Award> getAwards() {return theAwards;}
	//public void setAwards(List<Award> aAwards) {theAwards = aAwards;}
	
	/** Медицинские карты */
	@Comment("Медицинские карты")
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL) 
	public List<Medcard> getMedcard() {return theMedcard;}
	public void setMedcard(List<Medcard> aMedcard) {theMedcard = aMedcard;}
	
	/** Район города и области для фонда */
	@Comment("Район города и области для фонда")
	@OneToOne
	public VocRayon getRayon() {return theRayon;}
	public void setRayon(VocRayon aRayon) {theRayon = aRayon;}
	
	/** Синхронизация адресов */
	@PostPersist @PostUpdate
	protected void syncAddresses() {
		// адрес проживания пустой
	}

    /** Льготы */
	@Comment("Льготы")
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	public List<Privilege> getPrivileges() {return thePrivileges;}
	public void setPrivileges(List<Privilege> aPrivileges) {thePrivileges = aPrivileges;}
	
    /** Имя */
    @Comment("Имя")
	public String getFirstname() { return theFirstname ; }
    public void setFirstname(String aFirstname) { theFirstname = aFirstname ; }
    
    /** Фамилия */
    @Comment("Фамилия")
    public String getLastname() { return theLastname ; }
    public void setLastname(String aLastname) { theLastname = aLastname ; }
    
    /** Отчество */
    @Comment("Отчество")
    public String getMiddlename() { return theMiddlename ; }
    public void setMiddlename(String aMiddlename) { theMiddlename = aMiddlename ; }
    
    /** Дата рождения */
    @Comment("Дата рождения")
    public Date getBirthday() { return theBirthdate ; }
    public void setBirthday(Date aBirthdate) { theBirthdate = aBirthdate ; }
    
    /** Пол */
    @Comment("Пол")
    @OneToOne
    public VocSex getSex() { return theSex ; }
    public void setSex(VocSex aSex) { theSex = aSex ; }
    
    /** Место работы */
    @Comment("Место работы")
    @OneToOne
    public VocOrg getWorks() { return theWorks ; }
    public void setWorks(VocOrg aWorks) { theWorks = aWorks ; }
    
    /** ИНН */
    @Comment("ИНН")
    public Long getInn() { return theInn ; }
    public void setInn(Long aInn) { theInn = aInn ; }
    
    /** Тип удостоверения личности */
    @Comment("Тип удостоверения личности")
    @OneToOne
    public VocIdentityCard getPassportType() { return thePassportType ; }
    public void setPassportType(VocIdentityCard aPassportType) { thePassportType = aPassportType ; }
   
    /** Полисы */
    @Comment("Полисы")
    @OneToMany(mappedBy = "patient", cascade= ALL)
    public List<MedPolicy> getMedPolicies() { return theMedPolicies ; }
    public void setMedPolicies(List<MedPolicy> aMedPolicies) { theMedPolicies = aMedPolicies ; }

    /** Социальный статус, в т.ч. и занятость */
    @Comment("Социальный статус, в т.ч. и занятость")
    @OneToOne
    public VocSocialStatus getSocialStatus() { return theStatusSocial ; }
    public void setSocialStatus(VocSocialStatus aStatusSocial) { theStatusSocial = aStatusSocial ; }
    
//    /** Инвалидность */
//    @OneToOne(mappedBy = "patient", cascade= ALL)
//    public Invalidity getInvalidity() { return theInvalidity ; }
//    public void setInvalidity(Invalidity aInvalidity) { theInvalidity = aInvalidity ; }

//    /** Документы */
//    @OneToMany(mappedBy = "patient", cascade= ALL)
//    public List<IdentityCard> getIdentityCard() { return theIdentityCard ; }
//    public void setIdentityCard(List<IdentityCard> aIdentityCard) { theIdentityCard = aIdentityCard ; }


//    /** Место проживания */
//    @OneToOne(cascade = CascadeType.ALL)
//    public PatientAddress getRegistrationPlace() { return theRegistrationPlace ; }
//    public void setRegistrationPlace(PatientAddress aRegistrationPlace) { theRegistrationPlace = aRegistrationPlace ; }

//    /** Место проживания */
//    private PatientAddress theRegistrationPlace ;
//    /** Документы */
//    private List<IdentityCard> theIdentityCard ;
    
    /** Номер паспорта */
    @Comment("Номер паспорта")
    public String getPassportNumber() { return thePassportNumber ; }
    public void setPassportNumber(String aPassportNumber) { thePassportNumber = aPassportNumber ; }
    
    /** Серия паспорта */
    @Comment("Серия паспорта")
    public String getPassportSeries() { return thePassportSeries ; }
    public void setPassportSeries(String aPassportSeries) { thePassportSeries = aPassportSeries ; }
    
    /** Дата выдачи */
    @Comment("Дата выдачи")
    public Date getPassportDateIssued() { return thePassportDateIssue ; }
    public void setPassportDateIssued(Date aPassportDateIssue) { thePassportDateIssue = aPassportDateIssue ; }
    
    /** Кем выдан */
    @Comment("Кем выдан")
    public String getPassportWhomIssued() { return thePassportWhomIssued ; }
    public void setPassportWhomIssued(String aPassportWhomIssued) { thePassportWhomIssued = aPassportWhomIssued ; }
    
    /** СНИЛС */
    @Comment("СНИЛС")
    public String getSnils() { return theSnils ; }
    public void setSnils(String aSnils) { theSnils = aSnils ; }
	
    /** Адрес */
    @Comment("Адрес")
    @OneToOne
    public Address getAddress() { return theAddress ; }
    public void setAddress(Address aAddress) { theAddress = aAddress ; }
   
    /** Адрес проживания */
	@Comment("Адрес проживания")
	@OneToOne
	public Address getRealAddress() {return theRealAddress;}
	public void setRealAddress(Address aRealAddress) {theRealAddress = aRealAddress;}
	
    /** Дом по адресу проживания */
	@Comment("Дом по адресу проживания")
	public String getRealHouseNumber() {return theRealHouseNumber;}
	public void setRealHouseNumber(String aRealHouseNumber) {theRealHouseNumber = aRealHouseNumber;}
	
	
    /** Корпус по адресу проживания */
	@Comment("Корпус по адресу проживания")
	public String getRealHouseBuilding() {return theRealHouseBuilding;}
	public void setRealHouseBuilding(String aRealHouseBuilding) {theRealHouseBuilding = aRealHouseBuilding;}
	/** Корпус по адресу проживания */
	private String theRealHouseBuilding;
	
	/** Квартира по адресу проживания */
	@Comment("Квартира по адресу проживания")
	public String getRealFlatNumber() {return theRealFlatNumber;}
	public void setRealFlatNumber(String aRealFlatNumber) {theRealFlatNumber = aRealFlatNumber;}
	
    /** Дом */
    @Comment("Дом")
	public String getHouseNumber() { return theHouseNumber ; }
    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }
    
    /** Корпус */
    @Comment("Корпус")
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }
    
    /** Квартира */
    @Comment("Квартира")
    public String getFlatNumber() { return theFlatNumber ; }
    public void setFlatNumber(String aFlatNumber) { theFlatNumber = aFlatNumber ; }
    
	/**Дата смерти */
	@Comment("Дата смерти")
	public Date getDeathDate() {return theDeathDate;}
	public void setDeathDate(Date aNewProperty) {theDeathDate = aNewProperty;}
	
	/** Недействительность */
	@Comment("Недействительность")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNewProperty) {theNoActuality = aNewProperty;}
	
	/** Национальность */
	@Comment("Национальность")
	@OneToOne
	public VocEthnicity getEthnicity() {return theEthnicity;}
	public void setEthnicity(VocEthnicity aEthnicity) {theEthnicity = aEthnicity;}
	
	/** Семейное положение */
	@Comment("Семейное положение")
	@OneToOne
	public VocMarriageStatus getMarriageStatus() {return theMarriageStatus;}
	public void setMarriageStatus(VocMarriageStatus aMarriageStatus) {theMarriageStatus = aMarriageStatus;}
	
	/** Вид образования */
	@Comment("Вид образования")
	@OneToOne
	public VocEducationType getEducationType() {return theEducationType;}
	public void setEducationType(VocEducationType aEducationType) {theEducationType = aEducationType;}
	
	/** Должность */
	@Comment("Должность")
	public String getWorkPost() {return theWorkPost;}
	public void setWorkPost(String aWorkPost) {theWorkPost = aWorkPost;}
	
	/** Примечание */
	@Comment("Примечание")
	public String getNotice() {return theNotice;}
	public void setNotice(String aNotice) {theNotice = aNotice;}
	
	/** Иностранный адрес регистрации */
	@Comment("Иностранный адрес регистрации")
	public String getForeignRegistrationAddress() {return theForeignRegistrationAddress;	}
	public void setForeignRegistrationAddress(String aForeignRegistrationAddress) {theForeignRegistrationAddress = aForeignRegistrationAddress;}

	/** Иностранный адрес проживания */
	@Comment("Иностранный адрес проживания")
	public String getForeignRealAddress() {return theForeignRealAddress;}
	public void setForeignRealAddress(String aForeignRealAddress) {theForeignRealAddress = aForeignRealAddress;}

	/** Родственники */
	@Comment("Родственники")
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL )
	public List<Kinsman> getKinsmen() {return theKinsmen;}
	public void setKinsmen(List<Kinsman> aKinsmen) {theKinsmen = aKinsmen;}
	
	///** Чей родственник */
	//@Comment("Чей родственник")
	//@OneToMany(mappedBy="kinsman", cascade=CascadeType.ALL)
	//public List<Kinsman> getKinsmanOf() {return theKinsmanOf;}
	//public void setKinsmanOf(List<Kinsman> aKinsmanOf) {theKinsmanOf = aKinsmanOf;}
	
	@Transient
	public String getAddressRegistration() {
		if (theAddress!=null ) return theAddress.getAddressInfo(theHouseNumber, theHouseBuilding, theFlatNumber)  ;
		if (theTerritoryRegistrationNonresident!=null) {
			StringBuilder nonres = new StringBuilder();
			nonres.append(" ").append(theTerritoryRegistrationNonresident.getName()).append(", ") ;
			nonres.append(theRegionRegistrationNonresident) ;
			if (theTypeSettlementNonresident!=null) {
				nonres.append(" ").append(theTypeSettlementNonresident.getName()).append(" ") ;
			}  else {
				nonres.append(" нас.пункт ");
			}
			nonres.append(theSettlementNonresident) ;
			if (theTypeStreetNonresident!=null) {
				nonres.append(" ").append(theTypeStreetNonresident.getName()).append(" ");
			} else {
				nonres.append(" тип ул. ") ;
			}
			nonres.append(theStreetNonresident) ;
	        if (!StringUtil.isNullOrEmpty(theHouseNonresident)) nonres.append(" д.").append(theHouseNonresident).append(" ") ;
	        if (!StringUtil.isNullOrEmpty(theBuildingHousesNonresident)) nonres.append(" корп.").append(theBuildingHousesNonresident).append(" ");
	        if (!StringUtil.isNullOrEmpty(theApartmentNonresident)) nonres.append(" кв.").append(theApartmentNonresident).append(" ");
	        return nonres.toString() ;
		}
		return theForeignRegistrationAddress!=null?theForeignRegistrationAddress:"" ;
	}
	@Transient
	public String getAddressReal() {
		if (theRealAddress!=null ) return theRealAddress.getAddressInfo(theRealHouseNumber, theRealHouseBuilding, theRealFlatNumber)  ;
		
		return theForeignRealAddress ;
	}
	

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    public String getPatientInfo() {
        StringBuilder sb = new StringBuilder();
        add(sb, theLastname,"");
        add(sb, theFirstname," ");
        add(sb, theMiddlename," ");
        if(getBirthday()!=null) {
        	add(sb, " г.р.", " ");
            add(sb, DateFormat.formatToDate(getBirthday())," ") ;
        }
        return sb.toString();
    }
    /** Информация о пациенте */
    @Transient
    @Comment("Информация о пациенте")
    public String getFio() {
        StringBuilder sb = new StringBuilder();
        add(sb, theLastname,"");
        add(sb, theFirstname," ");
        add(sb, theMiddlename," ");
        if(getBirthday()!=null) {
        	add(sb, " г.р.", " ");
            add(sb, DateFormat.formatToDate(getBirthday())," ") ;
        }
        return sb.toString();
    }
    // IKO 070430 +++
    private String thePatientSync;
    @Comment("Синхронизация пациента")
    public String getPatientSync() {
        return thePatientSync;
    }
    
    public void setPatientSync(String aPatientSync) {
        thePatientSync = aPatientSync;
    }

    // IKO 070430 ===

    @Comment("Название пола")
    @Transient
    public String getSexName() {
        return theSex!=null ? theSex.getName() : "" ;
    }
    private static void add(StringBuilder aSb, String aStr, String aPre) {
        if(!StringUtil.isNullOrEmpty(aStr)) {
            if(aSb.length()!=0) aSb.append(aPre) ;
            aSb.append(aStr) ;
        }
    }
    public void setPatientInfo(String aPatientInfo) { }

    /*@Comment("Информация паспорта")
    @Transient
    public String getPassportInfo() {
        final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder sb = new StringBuilder();
        if (thePassportType!=null) sb.append(thePassportType.getName()).append(" ") ;
        addNotEmpty(sb, thePassportSeries) ;
        sb.append(" ") ;
        addNotEmpty(sb, thePassportNumber) ;
        if(thePassportDateIssue!=null) {
            sb.append(", выдан ") ;
        }
        addNotEmpty(sb, thePassportDateIssue!=null ? FORMAT.format(thePassportDateIssue) : "") ;
        sb.append(" ") ;
        addNotEmpty(sb, thePassportWhomIssued) ;
        return sb.toString() ;
    }*/

    //private static void addNotEmpty(StringBuilder aSb, String aValue) {
    //    if(!StringUtil.isNullOrEmpty(aValue)) {
    //        aSb.append(aValue) ;
    //    }
    //}
    @Comment("Информация по адресу")
	@Transient
    public String getAddressInfo() {
        return theAddress!=null ? theAddress.getAddressInfo(theHouseNumber, theHouseBuilding, theFlatNumber) : "" ;
    }
    @Comment ("Дом с корпусом")
    @Transient
    public String getAddressHouse() {
    	StringBuilder ret= new StringBuilder () ;
    	ret.append(theHouseNumber);
    	if (theHouseBuilding!=null && !theHouseBuilding.equals("")) {
    		ret.append("/").append(theHouseBuilding);
    	}
    	return ret.toString() ;
    }
	// [end]
	
	/** Гражданство */
	@Comment("Гражданство")
	@OneToOne
	public OmcOksm getNationality() {return theNationality;}
	public void setNationality(OmcOksm aNationality) {theNationality = aNationality;}
	
	
	
	/** Территория регистрации  (иногороднего) */
	@Comment("Территория регистрации")
	@OneToOne
	public OmcKodTer getTerritoryRegistrationNonresident() {
		return theTerritoryRegistrationNonresident;
	}

	public void setTerritoryRegistrationNonresident(OmcKodTer aTerritoryRegistrationNonresident) {
		theTerritoryRegistrationNonresident = aTerritoryRegistrationNonresident;
	}
	
	/** Район проживания  (иногороднего) */
	@Comment("Район проживания")
	public String getRegionRegistrationNonresident() {
		return theRegionRegistrationNonresident;
	}

	public void setRegionRegistrationNonresident(String aRegionRegistrationNonresident) {
		theRegionRegistrationNonresident = aRegionRegistrationNonresident;
	}

	/** Вид населенного пункта  (иногороднего) */
	@Comment("Вид населенного пункта")
	@OneToOne
	public OmcQnp getTypeSettlementNonresident() {
		return theTypeSettlementNonresident;
	}

	public void setTypeSettlementNonresident(OmcQnp aTypeSettlementNonresident) {
		theTypeSettlementNonresident = aTypeSettlementNonresident;
	}

	/** Населенный пункт  (иногороднего) */
	@Comment("Населенный пункт")
	public String getSettlementNonresident() {
		return theSettlementNonresident;
	}

	public void setSettlementNonresident(String aSettlementNonresident) {
		theSettlementNonresident = aSettlementNonresident;
	}

	/** Тип наименования улицы  (иногороднего) */
	@Comment("Тип наименования улицы")
	@OneToOne
	public OmcStreetT getTypeStreetNonresident() {
		return theTypeStreetNonresident;
	}

	public void setTypeStreetNonresident(OmcStreetT aTypeStreetNonresident) {
		theTypeStreetNonresident = aTypeStreetNonresident;
	}

	/** Наименование улицы  (иногороднего) */
	@Comment("Наименование улицы")
	public String getStreetNonresident() {
		return theStreetNonresident;
	}

	public void setStreetNonresident(String aStreetNonresident) {
		theStreetNonresident = aStreetNonresident;
	}

	/** Дом (иногороднего) */
	@Comment("Дом (иногороднего)")
	public String getHouseNonresident() {
		return theHouseNonresident;
	}

	public void setHouseNonresident(String aHouseNonresident) {
		theHouseNonresident = aHouseNonresident;
	}

	/** Корпус дома (иногороднего) */
	@Comment("Корпус дома (иногороднего)")
	public String getBuildingHousesNonresident() {
		return theBuildingHousesNonresident;
	}

	public void setBuildingHousesNonresident(String aBuildingHousesNonresident) {
		theBuildingHousesNonresident = aBuildingHousesNonresident;
	}

	/** Квартира (иногороднего) */
	@Comment("Квартира (иногороднего)")
	public String getApartmentNonresident() {
		return theApartmentNonresident;
	}

	public void setApartmentNonresident(String aApartmentNonresident) {
		theApartmentNonresident = aApartmentNonresident;
	}
	
	/** Дополнительный статус */
	@Comment("Дополнительный статус")
	@OneToOne
	public VocAdditionStatus getAdditionStatus() {return theAdditionStatus;}
	public void setAdditionStatus(VocAdditionStatus aAdditionStatus) {theAdditionStatus = aAdditionStatus;}

	/** Почтовый индекс */
	@Comment("Почтовый индекс")
	public String getZipcode() {return theZipcode;	}
	public void setZipcode(String aZipcode) {theZipcode = aZipcode;}


	/** Почтовый индекс проживания */
	@Comment("Почтовый индекс проживания")
	public String getRealZipcode() {return theRealZipcode;}
	public void setRealZipcode(String aRealZipcode) {theRealZipcode = aRealZipcode;}
	
	/** Почтовый индекс иногороднего */
	@Comment("Почтовый индекс иногороднего")
	public String getNonresidentZipcode() {return theNonresidentZipcode;}
	public void setNonresidentZipcode(String aNonresidentZipcode) {theNonresidentZipcode = aNonresidentZipcode;}

	/** Почтовый индекс иногороднего */
	private String theNonresidentZipcode;
	/** Почтовый индекс проживания */
	private String theRealZipcode;
	/** Почтовый индекс */
	private String theZipcode;
	/** Дополнительный статус */
	private VocAdditionStatus theAdditionStatus;

	/** Квартира (иногороднего) */
	private String theApartmentNonresident;
	/** Корпус дома (иногороднего) */
	private String theBuildingHousesNonresident;
	/** Дом (иногороднего) */
	private String theHouseNonresident;
	/** Наименование улицы  (иногороднего) */
	private String theStreetNonresident;
	/** Тип наименования улицы  (иногороднего) */
	private OmcStreetT theTypeStreetNonresident;
	/** Населенный пункт  (иногороднего) */
	private String theSettlementNonresident;
	/** Вид населенного пункта  (иногороднего) */
	private OmcQnp theTypeSettlementNonresident;
	/** Район проживания  (иногороднего) */
	private String theRegionRegistrationNonresident;
	/** Территория регистрации  (иногороднего) */
	private OmcKodTer theTerritoryRegistrationNonresident;

	/** Гражданство */
	private OmcOksm theNationality;
    
	/** DTYPE */
	//private String theDTYPE;
	/** Вакцинации */
	//private List<Vaccination> theVaccinations;
	/** Сотрудники */
	private List<Worker> theWorkers;
	/** Участок основного прикрепления */
    private LpuArea theLpuArea ;
    /** Адрес участка основного прикрепления */
    private LpuAreaAddressText theLpuAreaAddressText ;
    /** ЛПУ основного прикрепления */
    private MisLpu theLpu ;
    /** Ведомственное прикрепление */
	private List<LpuAttachedByDepartment> theAttachedByDepartments;
	/** СМО */
	private List<MedCase> theMedCases;
	/** Полис прикрепления */
	private MedPolicyOmc theAttachedOmcPolicy;
	/** Случаи нетрудоспособности */
	private List<DisabilityCase> theDisabilityCases;
	 /** Трудовые книги */
    private List<WorkBook> theWorkBook ;
    /** Знание языков */
	private List<LanguageSkill> theLanguageSkills;
	/** Образования */
    private List<Education> theEducations ;
    ///** Квалификации */
    //private List<Qualification> theQualifications ;
    /** Награды */
	//private List<Award> theAwards;
	/** Медицинские карты */
	private List<Medcard> theMedcard;
	/** Район города и области для фонда */
	private VocRayon theRayon;
	/** Льготы */
	private List<Privilege> thePrivileges;
	/** Имя */
    private String theFirstname ;
    /** Фамилия */
    private String theLastname ;
    /** Отчества */
    private String theMiddlename ;
    /** Дата рождения */
    private Date theBirthdate ;
    /** Пол */
    private VocSex theSex ;
    /** Место работы */
    private VocOrg theWorks ;
    /** ИНН */
    private Long theInn ;
    /** Тип удостоверения личности */
    private VocIdentityCard thePassportType ;
    /** Полисы */
    private List<MedPolicy> theMedPolicies ;
    /** Социальный статус, в т.ч. и занятость */
    private VocSocialStatus theStatusSocial ;
    /** Номер паспорта */
    private String thePassportNumber ;
    /** Серия паспорта */
    private String thePassportSeries ;
    /** Дата выдачи */
    private Date thePassportDateIssue ;
    /** Кем выдан */
    private String thePassportWhomIssued ;
    /** СНИЛС */
    private String theSnils ;
	 /** Адрес */
    private Address theAddress ;
    /** Адрес проживания */
	private Address theRealAddress;
	/** Дом по адресу проживания */
	private String theRealHouseNumber;
	/** Квартира по адресу проживания */
	private String theRealFlatNumber;
	/** Дом */
    private String theHouseNumber ;
    /** Корпус */
    private String theHouseBuilding ;
    /** Квартира */
    private String theFlatNumber ;
    /** Дата смерти */
	private Date theDeathDate;
	/** Недействительность*/
	private Boolean theNoActuality;
	/** Национальность */
	private VocEthnicity theEthnicity;
	/** Семейное положение */
	private VocMarriageStatus theMarriageStatus;
	/** Вид образования */
	private VocEducationType theEducationType;
	/** Должность */
	private String theWorkPost;
	/** Примечание */
	private String theNotice;
	/** Иностранный адрес проживания */
	private String theForeignRealAddress;
	/** Иностранный адрес регистрации */
	private String theForeignRegistrationAddress;
	/** Родственники */
	private List<Kinsman> theKinsmen;
	/** Чей родственник */
	//private List<Kinsman> theKinsmanOf;
	
	
	
	 /**
	  * Горожанин
	  */
	 @Comment("Горожанин")
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
	 @OneToOne
	 public VocLivelihoodSource getLivelihoodSource() {
	  return theLivelihoodSource;
	 }
	 public void setLivelihoodSource(VocLivelihoodSource aLivelihoodSource) {
	  theLivelihoodSource = aLivelihoodSource;
	 }
	 /**
	  * Источник средств существования
	  */
	 private VocLivelihoodSource theLivelihoodSource;
	 /**
	  * Условия проживания
	  */
	 @Comment("Условия проживания")
	 @OneToOne
	 public VocResidenceConditions getResidenceConditions() {
	  return theResidenceConditions;
	 }
	 public void setResidenceConditions(VocResidenceConditions aRecidenceConditions) {
	  theResidenceConditions = aRecidenceConditions;
	 }
	 /**
	  * Условия проживания
	  */
	 private VocResidenceConditions theResidenceConditions;
	 /**
	  * Проживает в семье
	  */
	 @Comment("Проживает в семье")
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
	 public Boolean getIncapable() {
	  return theIncapable;
	 }
	 public void setIncapable(Boolean aCapable) {
	  theIncapable = aCapable;
	 }
	 /**
	  * Дееспособный
	  */
	 private Boolean theIncapable;
	 /**
	  * Дата первого заполнения
	  */
	 @Comment("Дата первого заполнения")
	 public Date getFirstRegistrationDate() {
	  return theFirstRegistrationDate;
	 }
	 public void setFirstRegistrationDate(Date aFirstRegistrationDate) {
	  theFirstRegistrationDate = aFirstRegistrationDate;
	 }
	 /**
	  * Дата первого заполнения
	  */
	 private Date theFirstRegistrationDate;
	 
	 /** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата последнего редактирования */
	@Comment("Дата последнего редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}

	/** Пользователь, последний редактировающий запись */
	@Comment("Пользователь, последний редактировающий запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Место рождения */
	@Comment("Место рождения")
	public String getBirthPlace() {return theBirthPlace;}
	public void setBirthPlace(String aBirthPlace) {theBirthPlace = aBirthPlace;}

	/** Новорожденный */
	@Comment("Новорожденный")
	@OneToOne
	public VocNewBorn getNewborn() {return theNewborn;}
	public void setNewborn(VocNewBorn aNewborn) {theNewborn = aNewborn;}

	/** Новорожденный */
	private VocNewBorn theNewborn;
	/** Место рождения */
	private String theBirthPlace;
	/** Пользователь, последний редактировающий запись */
	private String theEditUsername;
	/** Дата последнего редактирования */
	private Date theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Код подразделения, выдавшего паспорт */
	@Comment("Код подразделения, выдавшего паспорт")
	public String getPassportCodeDivision() {return thePassportCodeDivision;}
	public void setPassportCodeDivision(String aPassportCodeDivision) {thePassportCodeDivision = aPassportCodeDivision;}

	/** Код подразделения, выдавшего паспорт */
	private String thePassportCodeDivision;
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	@OneToOne
	public VocPassportWhomIssue getPassportDivision() {
		return thePassportDivision;
	}

	public void setPassportDivision(VocPassportWhomIssue aPassportDivision) {
		thePassportDivision = aPassportDivision;
	}

	/** Кем выдан паспорт */
	private VocPassportWhomIssue thePassportDivision;
	/** Справочник по месту рождения */
	@Comment("Справочник по месту рождения")
	@OneToOne
	public VocPassportBirthPlace getPassportBirthPlace() {
		return thePassportBirthPlace;
	}

	public void setPassportBirthPlace(VocPassportBirthPlace aPassportBirthPlace) {
		thePassportBirthPlace = aPassportBirthPlace;
	}

	/** Справочник по месту рождения */
	private VocPassportBirthPlace thePassportBirthPlace;
	
	/** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
	public String getCommonNumber() {return theCommonNumber;}
	public void setCommonNumber(String aNumber) {theCommonNumber = aNumber;}

	/** Соотечественник */
	@Comment("Соотечественник")
	public Boolean getIsCompatriot() {return theIsCompatriot;}
	public void setIsCompatriot(Boolean aIsCompatriot) {theIsCompatriot = aIsCompatriot;}

	/** Соотечественник */
	private Boolean theIsCompatriot;
	/** Единый номер застрахованного */
	private String theCommonNumber;
	
	/** Категория ребенка */
	@Comment("Категория ребенка")
	@OneToOne
	public VocCategoryChild getCategoryChild() {return theCategoryChild;}
	public void setCategoryChild(VocCategoryChild aCategoryChild) {theCategoryChild = aCategoryChild;}

	/** Категория ребенка */
	private VocCategoryChild theCategoryChild;
	/** Вес новорожденного */
	@Comment("Вес новорожденного")
	public Long getNewbornWeight() {return theNewbornWeight;}
	public void setNewbornWeight(Long aNewbornWeight) {theNewbornWeight = aNewbornWeight;}

	/** Вес новорожденного */
	private Long theNewbornWeight;
}
