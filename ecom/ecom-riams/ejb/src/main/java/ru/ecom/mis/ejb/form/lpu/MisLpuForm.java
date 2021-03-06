package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdNameEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.form.lpu.interceptors.MisLpuDynamicSecurity;
import ru.ecom.mis.ejb.form.lpu.interceptors.RepMisLpuChildInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * ЛПУ
 */
@Comment("ЛПУ")
@EntityForm
@EntityFormPersistance(clazz = MisLpu.class)
@WebTrail(comment = "ЛПУ", nameProperties = "name", view = "entityView-mis_lpu.do")
@Parent(property = "parent", parentForm = MisLpuForm.class)
// не нужно
//@AParentPrepareCreateInterceptors(
//        @AParentEntityFormInterceptor(MisLpuPrepareCreateInterceptor.class)
//)

@EntityFormSecurityPrefix("/Policy/Mis/MisLpu")

@ADynamicSecurityInterceptor(MisLpuDynamicSecurity.class)
@ADynamicParentSecurityInterceptor(MisLpuDynamicSecurity.class)

@ACreateInterceptors(
        @AEntityFormInterceptor(RepMisLpuChildInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(RepMisLpuChildInterceptor.class)
)
public class MisLpuForm extends IdNameEntityForm {

	/** Признак мобильной поликлиники */
	@Comment("Признак мобильной поликлиники")
	@Persist
	public Boolean getIsMobilePolyclinic() {return theIsMobilePolyclinic;}
	public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {theIsMobilePolyclinic = aIsMobilePolyclinic;}
	/** Признак мобильной поликлиники */
	private Boolean theIsMobilePolyclinic ;

	
	/** Стандарт оказания мед. помощи */
	@Comment("Стандарт оказания мед. помощи")
	@Persist
	public Long getMedicalStandard() {return theMedicalStandard;}
	public void setMedicalStandard(Long aMedicalStandard) {theMedicalStandard = aMedicalStandard;}
	/** Стандарт оказания мед. помощи */
	private Long theMedicalStandard;
	
	/** Не входит в оплату по ОМС */
	@Comment("Не входит в оплату по ОМС")
	@Persist
	public Boolean getIsNoOmc() {
		return theIsNoOmc;
	}

	public void setIsNoOmc(Boolean aIsNoOmc) {
		theIsNoOmc = aIsNoOmc;
	}

	/** Не входит в оплату по ОМС */
	private Boolean theIsNoOmc;
	
	/** Вид собственности */
	@Comment("Вид собственности")
	@Persist
	public Long getPropertyAdmin() {
		return thePropertyAdmin;
	}

	public void setPropertyAdmin(Long aPropertyAdmin) {
		thePropertyAdmin = aPropertyAdmin;
	}

	/** Вид собственности */
	private Long thePropertyAdmin;
	/** Подчиненность */
	@Comment("Подчиненность")
	@Persist
	public Long getSubordination() {
		return theSubordination;
	}

	public void setSubordination(Long aSubordination) {
		theSubordination = aSubordination;
	}

	/** Подчиненность */
	private Long theSubordination;
	
    /** Участки */
//    @PersistOneToManyOneProperty(valueProperty="type", parentProperty="lpu")
    public String getAreas() { return theAreas ; }
    public void setAreas(String aAreas) { theAreas = aAreas ; }

    /** Участки */
    private String theAreas ;

    @Comment("Название")
    @Persist
    @Required @DoUpperCase
    public String getName() {
        return super.getName();
    }

    /** Полное имя */
    @Comment("Полное имя")
    @Persist
    public String getFullname() { return theFullname ; }
    public void setFullname(String aFullname) { theFullname = aFullname ; }

    /** Полное имя */
    private String theFullname ;

    /**
     * Родитель
     */
    @Persist
    public Long getParent() {
        return theParent;
    }

    public void setParent(Long aParent) {
        theParent = aParent;
    }

    /**
     * Родитель
     */
    private Long theParent;

    /**
     * Код ОМС
     */
    @Comment("Код ОМС")
    @Persist
    public String getOmcCode() {
        return theOmcCode;
    }

    public void setOmcCode(String aOmcCode) {
        theOmcCode = aOmcCode;
    }

    /**
     * Адрес
     */
    @Persist
    @Comment("Адрес")
    public Long getAddress() {
        return theAddress;
    }

    public void setAddress(Long aAddress) {
        theAddress = aAddress;
    }

    /**
     * Номер дома
     */
    @Comment("Номер дома")
    @Persist
    public String getHouseNumber() {
        return theHouseNumber;
    }

    public void setHouseNumber(String aHouseNumber) {
        theHouseNumber = aHouseNumber;
    }

    /**
     * Корпус
     */
    @Comment("Корпус")
    @Persist
    public String getHouseBuilding() {
        return theHouseBuilding;
    }

    public void setHouseBuilding(String aHouseBuilding) {
        theHouseBuilding = aHouseBuilding;
    }

    /**
     * ИНН
     */
    @Comment("ИНН")
    @Persist
    public Long getInn() {
        return theInn;
    }

    public void setInn(Long aInn) {
        theInn = aInn;
    }

    /**
     * ОГРН
     */
    @Comment("ОГРН")
    @Persist
    public Long getOgrn() {
        return theOgrn;
    }

    public void setOgrn(Long aOgrn) {
        theOgrn = aOgrn;
    }

    /**
     * Эл. адрес
     */
    @Comment("Эл. адрес")
    @Persist
    public String getEmail() {
        return theEmail;
    }

    public void setEmail(String aEmail) {
        theEmail = aEmail;
    }

    /**
     * Номер лицензии
     */
    @Comment("Номер лицензии")
    @Persist
    public String getLicenseNumber() {
        return theLicenseNumber;
    }

    public void setLicenseNumber(String aLicenseNumber) {
        theLicenseNumber = aLicenseNumber;
    }

    /**
     * Срок действия лицензии
     */
    @Comment("Срок действия лицензии")
    @Persist
    @DateString
    @DoDateString
    public String getLicenseExpired() {
        return theLincenseExpired;
    }

    public void setLicenseExpired(String aLincenseExpired) {
        theLincenseExpired = aLincenseExpired;
    }

    /**
     * Руководитель
     */
    @Comment("Руководитель")
    @Persist
    public String getDirector() {
        return theDirector;
    }

    public void setDirector(String aDirector) {
        theDirector = aDirector;
    }

    /**
     * Телефон
     */
    @Comment("Телефон")
    @Persist
    public String getPhone() {
        return thePhone;
    }

    public void setPhone(String aPhone) {
        thePhone = aPhone;
    }
    
    /** Функция ЛПУ */
	@Comment("Функция ЛПУ")
	@Persist @Required
	public Long getLpuFunction() {
		return theLpuFunction;
	}

	public void setLpuFunction(Long aLpuFunction) {
		theLpuFunction = aLpuFunction;
	}
	
	/** Профиль отделения */
	@Comment("Профиль отделения")
	@Persist
	public Long getProfile() {
		return theProfile;
	}

	public void setProfile(Long aProfile) {
		theProfile = aProfile;
	}

	/** Профиль отделения */
	private Long theProfile;

	/** Функция ЛПУ */
	private Long theLpuFunction;


    /**
     * Телефон
     */
    private String thePhone;
    /**
     * Руководитель
     */
    private String theDirector;
    /**
     * Срок действия лицензии
     */
    private String theLincenseExpired;
    /**
     * Номер лицензии
     */
    private String theLicenseNumber;
    /**
     * Эл. адрес
     */
    private String theEmail;
    /**
     * ОГРН
     */
    private Long theOgrn;
    /**
     * ИНН
     */
    private Long theInn;
    /**
     * Корпус
     */
    private String theHouseBuilding;
    /**
     * Номер дома
     */
    private String theHouseNumber;
    /**
     * Адрес
     */
    private Long theAddress;
    /**
     * Код ОМС
     */
    private String theOmcCode;
	/** Название для печати */
	@Comment("Название для печати")
	@Persist
	public String getPrintName() {return thePrintName;}
	public void setPrintName(String aPrintName) {thePrintName = aPrintName;}

	/** Адрес для печати */
	@Comment("Адрес для печати")
	@Persist
	public String getPrintAddress() {return thePrintAddress;}
	public void setPrintAddress(String aPrintAddress) {thePrintAddress = aPrintAddress;}

	/** Адрес для печати */
	private String thePrintAddress;
	/** Название для печати */
	private String thePrintName;
	
	/** Руководитель */
	@Comment("Руководитель")
	@Persist
	public Long getManager() {
		return theManager;
	}

	public void setManager(Long aManager) {
		theManager = aManager;
	}

	/** Руководитель */
	private Long theManager;

	/** Приемное отделение */
	@Comment("Приемное отделение")
	@Persist
	public Long getPigeonHole() {
		return thePigeonHole;
	}

	public void setPigeonHole(Long aPigeonHole) {
		thePigeonHole = aPigeonHole;
	}

	/** Приемное отделение */
	private Long thePigeonHole;
	/** Интервал разрешенной регистрации */
	@Comment("Интервал разрешенной регистрации")
	@Persist
	public Integer getRegistrationInterval() {return theRegistrationInterval;}
	public void setRegistrationInterval(Integer aRegistrationInterval) {theRegistrationInterval = aRegistrationInterval;}

	/** Не показывать удаленным пользователям */
	@Comment("Не показывать удаленным пользователям")
	@Persist
	public Boolean getIsNoViewRemoteUser() {return theIsNoViewRemoteUser;}
	public void setIsNoViewRemoteUser(Boolean aNoViewRemoteUser) {theIsNoViewRemoteUser = aNoViewRemoteUser;}

	/** Принтер по умолчанию */
	@Comment("Принтер по умолчанию")
	@Persist
	public Long getCopyingEquipmentDefault() {return theCopyingEquipmentDefault;}
	public void setCopyingEquipmentDefault(Long aCopyingEquipmentDefault) {theCopyingEquipmentDefault = aCopyingEquipmentDefault;}

	/** Принтер по умолчанию */
	private Long theCopyingEquipmentDefault;
	/** Не показывать удаленным пользователям */
	private Boolean theIsNoViewRemoteUser;
	/** Интервал разрешенной регистрации */
	private Integer theRegistrationInterval;
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	
	/** Автогенерация расписания */
	@Comment("Автогенерация расписания")
	@Persist
	public Boolean getAutoGeneration() {return theAutoGeneration;}
	public void setAutoGeneration(Boolean aAutoGeneration) {theAutoGeneration = aAutoGeneration;}

	/** Автогенерация расписания */
	private Boolean theAutoGeneration;
    /** Код федеральный */
	@Comment("Код федеральный")
	@Persist
	public String getCodef() {return theCodef;}
	public void setCodef(String aCodef) {theCodef = aCodef;}

	/** Код федеральный */
	private String theCodef;
	/** Номер в ФСС */
	@Persist
	public String getSocCode() {return theSocCode;}
	public void setSocCode(String aSocCode) {theSocCode = aSocCode;}

	/** Номер в ФСС */
	private String theSocCode;
	
	/** Возможен забор крови */
	@Persist
	@Comment("Возможен забор крови")
	public Boolean getIsIntakeBioMaterial() {return theIsIntakeBioMaterial;}
	public void setIsIntakeBioMaterial(Boolean aIsIntakeBioMaterial) {theIsIntakeBioMaterial = aIsIntakeBioMaterial;}

	/** Возможен забор крови */
	private Boolean theIsIntakeBioMaterial;

	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	@Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
	public String getSnilsDoctorDirect263() {return theSnilsDoctorDirect263;}
	public void setSnilsDoctorDirect263(String aSnilsDoctorDirect263) {theSnilsDoctorDirect263 = aSnilsDoctorDirect263;}

	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	private String theSnilsDoctorDirect263;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	@Persist 
	public String getCodeDepartment() {return theCodeDepartment;}
	public void setCodeDepartment(String aCodeDepartment) {theCodeDepartment = aCodeDepartment;}

	/** Код подразделения */
	private String theCodeDepartment;
	/** Короткое наименование */
	@Comment("Короткое наименование")
	@Persist
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Короткое наименование */
	private String theShortName;
	/** Доступ на создание операций по отделению */
	@Comment("Доступ на создание операций по отделению")
	@Persist
	public Long getAccessEnterOperation() {return theAccessEnterOperation;}
	public void setAccessEnterOperation(Long aAccessEnterOperation) {theAccessEnterOperation = aAccessEnterOperation;}

	/** Доступ на создание операций по отделению */
	private Long theAccessEnterOperation;
	
	/** Префикс для шаблонов ЛН */
	@Comment("Префикс для шаблонов ЛН")
	@Persist
	public String getPrefixForLN() {
		return thePrefixForLN;
	}

	public void setPrefixForLN(String aPrefixForLN) {
		thePrefixForLN = aPrefixForLN;
	}

	/** Префикс для шаблонов ЛН */
	private String thePrefixForLN;
	
	/** Экстренный кабинет */
	@Comment("Экстренный кабинет")
	@Persist
	public Long getEmergencyCabinet() {return theEmergencyCabinet;}
	public void setEmergencyCabinet(Long aEmergencyCabinet) {theEmergencyCabinet = aEmergencyCabinet;}

	/** Экстренный кабинет */
	private Long theEmergencyCabinet;
	
	/** Отделения для новорожденных */
	@Comment("Отделения для новорожденных")
	@Persist
	public Boolean getIsNewBornDep() {
		return theIsNewBornDep;
	}

	public void setIsNewBornDep(Boolean aIsNewBornDep) {
		theIsNewBornDep = aIsNewBornDep;
	}

	/** Отделения для новорожденных */
	private Boolean theIsNewBornDep;
	
	/** Родильное отделение */
	@Comment("Родильное отделение")
	@Persist
	public Boolean getIsMaternityWard() {
		return theIsMaternityWard;
	}

	public void setIsMaternityWard(Boolean aIsMaternityWard) {
		theIsMaternityWard = aIsMaternityWard;
	}

	/** Родильное отделение */
	private Boolean theIsMaternityWard;
	
	/** Уровень оказания медицинской помощи */
	@Comment("Уровень оказания медицинской помощи")
	@Persist
	public Integer getLpuLevel() {return theLpuLevel;}
	public void setLpuLevel(Integer aLpuLevel) {theLpuLevel = aLpuLevel;}
	/** Уровень оказания медицинской помощи */
	private Integer theLpuLevel;
	

	/** Профиль КИЛИ */
	@Comment("Профиль КИЛИ")
	@Persist
	public Long getKiliProfile() {return theKiliProfile;}
	public void setKiliProfile(Long aKiliProfile) {theKiliProfile = aKiliProfile;}
	/** Профиль КИЛИ */
	private Long theKiliProfile;
	
	/** В архиве */
	@Comment("В архиве")
	@Persist
	public Boolean getIsArchive() {return theIsArchive;}
	public void setIsArchive(Boolean aIsArchive) {theIsArchive = aIsArchive;}
	/** В архиве */
	private Boolean theIsArchive;

	/** Обсервационное? */
	@Comment("Обсервационное?")
	@Persist
	public Boolean getIsObservable() {return theIsObservable;}
	public void setIsObservable(Boolean aIsObservable) {theIsObservable = aIsObservable;}
	/** Обсервационное? */
	private Boolean theIsObservable;

	/** Палата новорождённых? */
	@Comment("Палата новорождённых?")
	@Persist
	public Boolean getIsNewBorn() {return theIsNewBorn;}
	public void setIsNewBorn(Boolean aIsNewBorn) {theIsNewBorn = aIsNewBorn;}
	/** Палата новорождённых? */
	private Boolean theIsNewBorn;

	/** Создают ли кадриоскрининг новорождённым? */
	@Comment("Создают ли кадриоскрининг новорождённым?")
	@Persist
	public Boolean getIsCreateCardiacScreening() {return theIsCreateCardiacScreening;}
	public void setIsCreateCardiacScreening(Boolean aIsCreateCardiacScreening) {theIsCreateCardiacScreening = aIsCreateCardiacScreening;}
	/** Создают ли кадриоскрининг новорождённым? */
	private Boolean theIsCreateCardiacScreening;
}
