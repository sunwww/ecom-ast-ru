package ru.ecom.mis.ejb.form.patient;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
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
import ru.nuzmsh.forms.validator.validators.*;

/**
 * Пациент
 */
@EntityForm
@EntityFormPersistance(clazz = Patient.class)
@Comment("Персона")
@WebTrail(comment = "Персона", nameProperties = {"patientInfo","patientSync"}
	, view = "entityView-mis_patient.do", shortView="entityShortView-mis_patient.do"
			,journal=true,listStyle={
		"select case when (deathDate is not null) n cast('border: 2px solid;font-size: 14px; color: black;font-weight:bold;text-decoration:blink' as varchar(200)) when colorType='1' n cast('font-size: 14px; color: red;font-weight:bold;text-decoration:blink' as varchar(200)) end from patient where id=:id"
		,"select 'background:'||coalesce((select max(pl.colorName) from PatientList pl left join VocPatientListType vplt on vplt.id=pl.type where vplt.code='SUICIDE'),'#CDE')||';color:'||coalesce((select max(pl.colorText) from PatientList pl left join VocPatientListType vplt on vplt.id=pl.type where vplt.code='SUICIDE'),'black')||';' from SuicideMessage where patient_id=:id group by patient_id"
		,"select coalesce('background:'||max(pl.colorName)||';','')||coalesce('color:'||max(pl.colorText)||';font-size: 14px;','') from PatientListRecord plr left join PatientList pl on pl.id=plr.PatientList where plr.patient=:id and pl.isViewInWebTrail='1' group by plr.patient"
		
		})
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
@Setter
public class PatientForm extends IdEntityForm {

    /** Голосует в палате */
    @Comment("Голосует в палате")
    @Persist
    public Boolean getVoteInRoom() {return voteInRoom;}
    /** Голосует в палате */
    private Boolean voteInRoom ;

	/** ППЗ */
	@Persist
	@Comment("ППЗ")
	public Boolean getPpz() {return ppz;}
	/** ППЗ */
	private Boolean ppz ;

	/** Номер телефона для мобильного приложения */
	@Comment("Номер телефона для мобильного приложения")
	public String getMobileAppPhoneNumber() {return mobileAppPhoneNumber;}
	/** Номер телефона для мобильного приложения */
	private String mobileAppPhoneNumber ;

	/** Не голосует */
	@Comment("Не голосует")
	@Persist
	public Boolean getNotVote() {return notVote;}
	/** Не голосует */
	private Boolean notVote;
	    
	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhone() {return phone;}

	/** Телефон */
	private String phone;

    @Comment("Синхронизация пациента")
    @Persist @DoUpperCase
    public String getPatientSync() {
        return patientSync;
    }
    
    /* Синхронизация пациента */
    private String patientSync;
    /** Название ЛПУ */
    @Persist
    @Comment("Название ЛПУ")
    public String getLpuName() {    return lpuName ;}

    /** Название участка */
    @Persist
    @Comment("Название участка")
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getLpuAreaName() {    return lpuAreaName ;}

    /** Участок */
    @Persist
    @Comment("Участок")
    public Long getLpuArea() {    return lpuArea ;}

    /** Адрес участка */
    @Persist
    @Comment("Адрес участка")
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getLpuAreaAddressText() {    return lpuAreaAddressText ;}

    /** ЛПУ */
    @Persist
    @Comment("ЛПУ")
    public Long getLpu() {    return lpu ;}

    /** Имя */
    @Comment("Имя")
    @Persist
    @Required @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
    @VInputFIOByMaskOmc
    public String getFirstname() { return firstname ; }

    /** Фамилия */
    @Comment("Фамилия")
    @Persist
    @Required @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
    @VInputFIOByMaskOmc
    public String getLastname() { return lastname ; }

    /** Отчество */
    @Comment("Отчество")
    @Persist
    @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
    @VInputFIOByMaskOmc
    public String getMiddlename() { return middlename ; }

    /** Пол */
    @Comment("Пол")
    @Persist
    @Required
    public Long getSex() { return sex ; }

    /** Дата рождения */
    @Comment("Дата рождения")
    @Persist @MaxDateCurrent
    @Required @DateString @DoDateString
    public String getBirthday() { return birthday ; }

    /** Социальный статус */
    @Comment("Социальный статус")
    @Persist @Required
    public Long getSocialStatus() { return socialStatus ; }

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    @Persist
    public String getPatientInfo() { return patientInfo ; }

    /** Номер паспорта */
    @Persist @DoUpperCase @DoTrimString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportNumber() { return passportNumber ; }

    /** Серия паспорта */
    @Persist @DoUpperCase @DoTrimString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportSeries() { return passportSeries ; }

    /** Дата выдачи */
    @Persist @DateString @DoDateString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportDateIssued() { return passportDateIssue ; }

    /** Кем выдан */
    @Persist @DoUpperCase @DoTrimString
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getPassportWhomIssued() { return passportWhomIssued ; }

    /** СНИЛС */
    @Persist @SnilsString
    public String getSnils() { return snils ; }

    /** Место работы */
    @Comment("Место работы")
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getWorks() { return works ; }

    /** Дом */
    @Persist
    @DoTrimString @DoInputNonLat @VInputNonLat
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getHouseNumber() { return houseNumber ; }

    /** Корпус */
    @Persist
    @DoUpperCase @DoTrimString @DoInputNonLat @VInputNonLat
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getHouseBuilding() { return houseBuilding ; }

    /** Квартира */
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public String getFlatNumber() { return flatNumber ; }

    /** Адрес */
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getAddress() { return address ; }

    /** Тип удостоверения личности */
    @Persist
    @ADynamicSecurityInterceptor(PatientDynamicSecurityInterceptor.class)
    public Long getPassportType() { return passportType ; }

    /** Полис прикрепления */
	@Comment("Полис прикрепления")
	
	public Long getAttachedOmcPolicy() {return attachedPolicyOmc;}

	/** Новые полис для прикрепления */
	@Comment("Полис для прикрепления")
	public MedPolicyOmcForm getPolicyOmcForm () {return policyOmcForm ;}

	/** Создать новый полис ОМС */
	public boolean getCreateNewOmcPolicy() {return createNewOmcPolicy;}

	/** Ведомственное прикрепление */
	@Comment("Ведомственное прикрепление")
	public boolean getCreateAttachedByDepartment() {return createAttachedByDepartment;}

	/** Дата смерти*/
	@Comment("Дата смерти")
	@Persist @DateString @DoDateString
	public String getDeathDate() {return deathDate;}

	/** Префикс адреса проживания*/
	@Comment("Префикс адреса проживания")
	@Persist
	public Long getRealAddress() {return realAddress;}

	/**Квартира проживания*/
	@Comment("Квартира проживания")
	@Persist
	public String getRealFlatNumber() {return realFlatNumber;}

	/**Корпус проживания*/
	@Persist
	@Comment("Корпус проживания")
	public String getRealHouseBuilding() {return realHouseBuilding;}

	/** Дом проживания*/
	@Comment("Дом проживания")
	@Persist
	public String getRealHouseNumber() {return realHouseNumber;}

	/**Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return noActuality;}

	/** Район */
	@Comment("Район")
	@Persist
	public Long getRayon() {return rayon;}

	
	/** Национальность */
	@Comment("Национальность")
	@Persist
	public Long getEthnicity() {return ethnicity;}

	/** Семейное положение */
	@Comment("Семейное положение")
	@Persist
	public Long getMarriageStatus() {return marriageStatus;}

	/** Вид образования */
	@Comment("Вид образования")
	@Persist
	public Long getEducationType() {return educationType;}

	/** Должность */
	@Comment("Должность")
	@Persist
	public String getWorkPost() {return workPost;}

	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getNotice() {return notice;}

	/** Иностранный адрес регистрации */
	@Comment("Иностранный адрес регистрации")
	@Persist
	public String getForeignRegistrationAddress() {
		return foreignRegistrationAddress;
	}

	/** Иностранный адрес регистрации */
	private String foreignRegistrationAddress;
	
	/** Иностранный адрес проживания */
	@Comment("Иностранный адрес проживания")
	@Persist
	public String getForeignRealAddress() {
		return foreignRealAddress;
	}

	/** Гражданство */
	@Comment("Гражданство")
	@Persist
	public Long getNationality() {
		return nationality;
	}

	/** Территория регистрации  (иногороднего) */
	@Comment("Территория регистрации")
	@Persist
	public Long getTerritoryRegistrationNonresident() {
		return territoryRegistrationNonresident;
	}

	/** Район проживания  (иногороднего) */
	@Comment("Район проживания")
	@Persist
	public String getRegionRegistrationNonresident() {
		return regionRegistrationNonresident;
	}

	/** Вид населенного пункта  (иногороднего) */
	@Comment("Вид населенного пункта")
	@Persist
	public Long getTypeSettlementNonresident() {
		return typeSettlementNonresident;
	}

	/** Населенный пункт  (иногороднего) */
	@Comment("Населенный пункт")
	@Persist
	public String getSettlementNonresident() {
		return settlementNonresident;
	}

	/** Тип наименования улицы  (иногороднего) */
	@Comment("Тип наименования улицы")
	@Persist
	public Long getTypeStreetNonresident() {
		return typeStreetNonresident;
	}

	/** Наименование улицы  (иногороднего) */
	@Comment("Наименование улицы")
	@Persist
	public String getStreetNonresident() {
		return streetNonresident;
	}

	/** Дом (иногороднего) */
	@Comment("Дом (иногороднего)")
	@Persist
	public String getHouseNonresident() {
		return houseNonresident;
	}

	/** Корпус дома (иногороднего) */
	@Comment("Корпус дома (иногороднего)")
	@Persist
	public String getBuildingHousesNonresident() {
		return buildingHousesNonresident;
	}

	/** Квартира (иногороднего) */
	@Comment("Квартира (иногороднего)")
	@Persist
	public String getApartmentNonresident() {
		return apartmentNonresident;
	}

	/** Дополнительный статус */
	@Comment("Дополнительный статус")
	@Persist @Required
	public Long getAdditionStatus() {return additionStatus;}

	/** Адрес */
	@Comment("Адрес")
	@Persist
	public String getAddressInfo() {return addressInfo;}

	/** Адрес */
	private String addressInfo;

	/** Дополнительный статус */
	private Long additionStatus;
	/** Квартира (иногороднего) */
	private String apartmentNonresident;
	/** Корпус дома (иногороднего) */
	private String buildingHousesNonresident;
	/** Дом (иногороднего) */
	private String houseNonresident;
	/** Наименование улицы  (иногороднего) */
	private String streetNonresident;
	/** Тип наименования улицы  (иногороднего) */
	private Long typeStreetNonresident;
	/** Населенный пункт  (иногороднего) */
	private String settlementNonresident;
	/** Вид населенного пункта  (иногороднего) */
	private Long typeSettlementNonresident;
	/** Район проживания  (иногороднего) */
	private String regionRegistrationNonresident;
	/** Территория регистрации  (иногороднего) */
	private Long territoryRegistrationNonresident;
	/** Гражданство */
	private Long nationality;
	/** Иностранный адрес проживания */
	private String foreignRealAddress;
	/** Ведомственное прикрепление */
	private boolean createAttachedByDepartment;
	/** Создать новый полис ОМС */
	private boolean createNewOmcPolicy;
	/** Полис для прикрепления */
	private MedPolicyOmcForm policyOmcForm = new MedPolicyOmcForm();
	/** Полис прикрепления */
	private Long attachedPolicyOmc;
    /** Тип удостоверения личности */
    private Long passportType ;
    /** Адрес */
    private Long address ;
    /** Квартира */
    private String flatNumber ;
    /** Корпус */
    private String houseBuilding ;
    /** Дом */
    private String houseNumber ;
    /** Место работы */
    private String works ;
    /** СНИЛС */
    private String snils ;
    /** Кем выдан */
    private String passportWhomIssued ;
    /** Дата выдачи */
    private String passportDateIssue ;
    /** Серия паспорта */
    private String passportSeries ;
    /** Номер паспорта */
    private String passportNumber ;
    /** Информация о пациенте */
    private String patientInfo ;
    /** Социальный статус */
    private Long socialStatus ;
   /** Дата рождения */
    private String birthday ;
    /** Пол */
    private Long sex ;
    /** Отчество */
    private String middlename ;
    /** Фамилия */
    private String lastname ;
    /** Имя */
    private String firstname ;
    /** ЛПУ */
    private Long lpu ;
    /** Адрес участка */
    private Long lpuAreaAddressText ;
    /** Участок */
    private Long lpuArea ;
    /** Название участка */
    private String lpuAreaName ;
    /** Название ЛПУ */
    private String lpuName ;
	/**Дата смерти */
	private String deathDate;
	/**Префикс адреса проживания */
	private Long realAddress;
	/**Квартира проживания */
	private String realFlatNumber;
	/**Корпус проживания */
	private String realHouseBuilding;
	/**Дом проживания */
	private String realHouseNumber;
	/**Недействительность */
	private Boolean noActuality;
	/** Район */
	private Long rayon;

	/** Национальность */
	private Long ethnicity;
	/** Семейное положение */
	private Long marriageStatus;
	/** Вид образования */
	private Long educationType;
	/** Должность */
	private String workPost;
	/** Примечание */
	private String notice;



	 /**
	  * Горожанин
	  */
	 @Comment("Горожанин")
	 @Persist
	 public Boolean getTownsman() {
	  return townsman;
	 }
	 /**
	  * Горожанин
	  */
	 private Boolean townsman;
	 /**
	  * Участник ВОВ
	  */
	 @Comment("Участник ВОВ")
	 @Persist
	 public Boolean getGreatPatrioticWarPaticipant() {
	  return greatPatrioticWarPaticipant;
	 }
	 /**
	  * Участник ВОВ
	  */
	 private Boolean greatPatrioticWarPaticipant;
	 /**
	  * Число законченных классов общеобразовательной школы
	  */
	 @Comment("Число законченных классов общеобразовательной школы")
	 @Persist
	 public Integer getGeneralEducationGradeNumber() {
	  return generalEducationGradeNumber;
	 }
	 /**
	  * Число законченных классов общеобразовательной школы
	  */
	 private Integer generalEducationGradeNumber;
	 /**
	  * Источник средств существования
	  */
	 @Comment("Источник средств существования")
	 @Persist
	 public Long getLivelihoodSource() {
	  return livelihoodSource;
	 }
	 /**
	  * Источник средств существования
	  */
	 private Long livelihoodSource;
	 /**
	  * Условия проживания
	  */
	 @Comment("Условия проживания")
	 @Persist
	 public Long getResidenceConditions() {
	  return residenceConditions;
	 }
	 /**
	  * Условия проживания
	  */
	 private Long residenceConditions;
	 /**
	  * Проживает в семье
	  */
	 @Comment("Проживает в семье")
	 @Persist
	 public Boolean getFamilyResident() {
	  return familyResident;
	 }
	 /**
	  * Проживает в семье
	  */
	 private Boolean familyResident;
	 /**
	  * Дееспособный
	  */
	 @Comment("Дееспособный")
	 @Persist
	 public Boolean getIncapable() {
	  return incapable;
	 }
		/** Почтовый индекс */
		@Comment("Почтовый индекс")
		@Persist
		public String getZipcode() {return zipcode;	}

		/** Почтовый индекс проживания */
		@Comment("Почтовый индекс проживания")
		@Persist
		public String getRealZipcode() {return realZipcode;}

		/** Почтовый индекс иногороднего */
		@Comment("Почтовый индекс иногороднего")
		@Persist
		public String getNonresidentZipcode() {return nonresidentZipcode;}

		/** Почтовый индекс иногороднего */
		private String nonresidentZipcode;
		/** Почтовый индекс проживания */
		private String realZipcode;
		/** Почтовый индекс */
		private String zipcode;
	 /**
	  * Дееспособный
	  */
	 private Boolean incapable;
	 /**
	  * Дата первого заполнения
	  */
	 @Comment("Дата первого заполнения")
	 @Persist @DateString @DoDateString
	 public String getFirstRegistrationDate() {
	  return firstRegistrationDate;
	 }
	 /**
	  * Дата первого заполнения
	  */
	 private String firstRegistrationDate;
	 /** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата последнего редактирования */
	@Comment("Дата последнего редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Пользователь, последний редактировающий запись */
	@Comment("Пользователь, последний редактировающий запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Место рождения */
	@Comment("Место рождения")
	@Persist @DoUpperCase
	public String getBirthPlace() {return birthPlace;}

	/** Новорожденный */
	@Comment("Новорожденный")
	@Persist
	public Long getNewborn() {return newborn;}

	/** Новорожденный */
	private Long newborn;
	/** Место рождения */
	private String birthPlace;
	/** Пользователь, последний редактировающий запись */
	private String editUsername;
	/** Дата последнего редактирования */
	private String editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;
	
	/** Код подразделения, выдавшего паспорт */
	@Comment("Код подразделения, выдавшего паспорт")
	@Persist
	public String getPassportCodeDivision() {return passportCodeDivision;}

	/** Код подразделения, выдавшего паспорт */
	private String passportCodeDivision;
	
	/** Мед.карта */
	@Comment("Мед.карта")
	public Long getMedcardLast() {return medcardLast;}

	/** Мед.карта */
	private Long medcardLast;
	/** Психиатрическая мед.карта */
	@Comment("Психиатрическая мед.карта")
	public Long getCareCard() {return careCard;}

	/** Возраст */
	@Comment("Возраст")
	public String getAge() {return age;}

	/** Возраст */
	private String age;
	/** Психиатрическая мед.карта */
	private Long careCard;
	
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	@Persist
	public Long getPassportDivision() {
		return passportDivision;
	}


	/** Кем выдан паспорт */
	private Long passportDivision;
	/** Справочник по месту рождения */
	@Comment("Справочник по месту рождения")
	@Persist
	public Long getPassportBirthPlace() {return passportBirthPlace;}

	/** Справочник по месту рождения */
	private Long passportBirthPlace;
	
	/** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
	@Persist @MaxLength(value = 16) @MinLength(value = 16)
	public String getCommonNumber() {return commonNumber;}

	/** Соотечественник */
	@Comment("Соотечественник")
	@Persist
	public Boolean getIsCompatriot() {return isCompatriot;}

	/** Соотечественник */
	private Boolean isCompatriot;
	/** Единый номер застрахованного */
	private String commonNumber;
	/** Категория ребенка */
	@Comment("Категория ребенка")
	@Persist
	public Long getCategoryChild() {return categoryChild;}

	/** Категория ребенка */
	private Long categoryChild;
	
	/** Вес новорожденного */
	@Comment("Вес новорожденного")
	@Persist
	public Long getNewbornWeight() {return newbornWeight;}

	/** Вес новорожденного */
	private Long newbornWeight;
	/** Район проживания */
	@Comment("Район проживания")
	@Persist
	public Long getRealRayon() {return realRayon;}

	/** Район проживания */
	private Long realRayon;
	/** Цветовая характеристика */
	@Comment("Цветовая характеристика")
	@Persist
	public Boolean getColorType() {return colorType;}

	/** Цветовая характеристика */
	private Boolean colorType;
	
	/** Прикрепление по участку */
	@Comment("Прикрепление по участку")
	public LpuAttachedByDepartmentForm getAttachedForm() {return attachedForm;}

	/** Прикрепление по участку */
	private LpuAttachedByDepartmentForm attachedForm = new LpuAttachedByDepartmentForm();
	
	/** Создать прикрепление */
	@Comment("Создать прикрепление")
	public Boolean getCreateNewAttachment() {return createNewAttachment;}
	/** Создать прикрепление */
	private Boolean createNewAttachment;
	
}