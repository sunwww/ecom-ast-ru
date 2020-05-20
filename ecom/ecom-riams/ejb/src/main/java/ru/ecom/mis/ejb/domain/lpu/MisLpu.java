package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.equipment.Equipment;
import ru.ecom.mis.ejb.domain.lpu.voc.VocLpuAccessEnterOperation;
import ru.ecom.mis.ejb.domain.lpu.voc.VocLpuFunction;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliProfile;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
import ru.ecom.mis.ejb.domain.medstandard.MedicalStandard;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.service.lpu.ILpuService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * ЛПУ
 */
@Entity(name="MisLpu")
@AIndexes({
	@AIndex(properties={"lpuFunction"})
	,@AIndex(properties={"parent"})
	,@AIndex(properties={"name"})
	,@AIndex(properties={"pigeonHole"})
	,@AIndex(properties={"parent","name"})
})
@Table(schema="SQLUser")
public class MisLpu extends BaseEntity {

	/** Признак мобильной поликлиники */
	@Comment("Признак мобильной поликлиники")
	public Boolean getIsMobilePolyclinic() {return theIsMobilePolyclinic;}
	public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {theIsMobilePolyclinic = aIsMobilePolyclinic;}
	/** Признак мобильной поликлиники */
	private Boolean theIsMobilePolyclinic ;

	
	/** Стандарт оказания мед. помощи */
	@Comment("Стандарт оказания мед. помощи")
	@OneToOne
	public MedicalStandard getMedicalStandard() {return theMedicalStandard;}
	public void setMedicalStandard(MedicalStandard aMedicalStandard) {theMedicalStandard = aMedicalStandard;}
	/** Стандарт оказания мед. помощи */
	private MedicalStandard theMedicalStandard;


	/** Рабочие функции */
	@Comment("Рабочие функции")
	@OneToMany(mappedBy="lpu", cascade=CascadeType.ALL)
	public List<WorkFunction> getWorkFunctions() {
		return theWorkFunctions;
	}

	public void setWorkFunctions(List<WorkFunction> aWorkFunctions) {
		theWorkFunctions = aWorkFunctions;
	}

	/** Рабочие функции */
	private List<WorkFunction> theWorkFunctions;


    /** Родитель */
    @ManyToOne
    public MisLpu getParent() { return theParent ; }
    public void setParent(MisLpu aParent) { theParent = aParent ; }

    /** Подразделения ЛПУ */
    @OneToMany(mappedBy = "parent", cascade= ALL)
    public List<MisLpu> getSubdivisions() { return theSubdivisions ; }
    public void setSubdivisions(List<MisLpu> aSubdivisions) { theSubdivisions = aSubdivisions ; }

    /** Подразделения ЛПУ */
    private List<MisLpu> theSubdivisions ;
   
    /** Родитель */
    private MisLpu theParent ;
    
    /** Наименование ЛПУ */
    @Comment("Наименование ЛПУ")
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Полное имя */
    @Transient
    public String getFullname() {
        if(theParent!=null) {
            return theParent.getName()+" / "+theName ;
        } else {
            return theName ;
        }
    }
    public void setFullname(String aFullname) {}

    /** Комментарий */
    @Comment("Комментарий")
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Терапевтические участки */
    @OneToMany(mappedBy = "lpu", cascade= ALL)
    public List<LpuArea> getAreas() { return theAreas ; }
    public void setAreas(List<LpuArea> aAreas) { theAreas = aAreas ; }

    /** Работники ЛПУ */
    @OneToMany(mappedBy = "lpu", cascade= ALL)
    public List<Worker> getWorker() { return theWorker ; }
    public void setWorker(List<Worker> aWorker) { theWorker = aWorker ; }

    /** Код ОМС */
    @Comment("Код ОМС")
    public String getOmcCode() { return theOmcCode ; }
    public void setOmcCode(String aOmcCode) { theOmcCode = aOmcCode ; }

    /** Адрес */
    @OneToOne
    public Address getAddress() { return theAddress ; }
    public void setAddress(Address aAddress) { theAddress = aAddress ; }

    /** Номер дома */
    @Comment("Номер дома")
    public String getHouseNumber() { return theHouseNumber ; }
    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }

    /** Корпус */
    @Comment("Корпус")
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }


    /** ИНН */
    @Comment("ИНН")
    public Long getInn() { return theInn ; }
    public void setInn(Long aInn) { theInn = aInn ; }

    /** ОГРН */
    @Comment("ОГРН")
    public Long getOgrn() { return theOgrn ; }
    public void setOgrn(Long aOgrn) { theOgrn = aOgrn ; }

    /** Эл. адрес */
    @Comment("Эл. адрес")
    public String getEmail() { return theEmail ; }
    public void setEmail(String aEmail) { theEmail = aEmail ; }

    /** Номер лицензии */
    @Comment("Номер лицензии")
    public String getLicenseNumber() { return theLicenseNumber ; }
    public void setLicenseNumber(String aLicenseNumber) { theLicenseNumber = aLicenseNumber ; }

    /** Срок действия лицензии */
    @Comment("Срок действия лицензии")
    public Date getLicenseExpired() { return theLincenseExpired ; }
    public void setLicenseExpired(Date aLincenseExpired) { theLincenseExpired = aLincenseExpired ; }

    /** Руководитель */
    @Comment("Руководитель")
    public String getDirector() { return theDirector ; }
    public void setDirector(String aDirector) { theDirector = aDirector ; }

    /** Телефон */
    @Comment("Телефон")
    public String getPhone() { return thePhone ; }
    public void setPhone(String aPhone) { thePhone = aPhone ; }

    /** Оборудование */
     @OneToMany(mappedBy = "lpu", cascade= ALL)
     public List<Equipment> getEquipment() { return theEquipment ; }
     public void setEquipment(List<Equipment> aEquipment) { theEquipment = aEquipment ; }

     /** Оборудование */
     private List<Equipment> theEquipment ;
     
     /** Коечный фонд */
	@Comment("Коечный фонд")
	@OneToMany(mappedBy="lpu", cascade = ALL)
	public List<BedFund> getBedFund() {
		return theBedFund;
	}

	public void setBedFund(List<BedFund> aBedFund) {
		theBedFund = aBedFund;
	}

	/** Коечный фонд */
	private List<BedFund> theBedFund;

     @PreRemove
     public void onRemove() {
    	 EjbInjection.getInstance().getLocalService(ILpuService.class).onRemoveLpu(getId()) ;
     }
     
     /** Функция ЛПУ */
	@Comment("Функция ЛПУ")
	@OneToOne
	public VocLpuFunction getLpuFunction() {return theLpuFunction;}
	public void setLpuFunction(VocLpuFunction aLpuFunction) {theLpuFunction = aLpuFunction;}

	
	/** Не входит в оплату по ОМС */
	@Comment("Не входит в оплату по ОМС")
	public Boolean getIsNoOmc() {
		return theIsNoOmc;
	}

	public void setIsNoOmc(Boolean aIsNoOmc) {
		theIsNoOmc = aIsNoOmc;
	}

	/** Название для печати */
	@Comment("Название для печати")
	public String getPrintName() {return thePrintName;}
	public void setPrintName(String aPrintName) {thePrintName = aPrintName;}

	/** Адрес для печати */
	@Comment("Адрес для печати")
	public String getPrintAddress() {return thePrintAddress;}
	public void setPrintAddress(String aPrintAddress) {thePrintAddress = aPrintAddress;}

	/** Адрес для печати */
	private String thePrintAddress;
	/** Название для печати */
	private String thePrintName;

	/** Не входит в оплату по ОМС */
	private Boolean theIsNoOmc;
	/** Функция ЛПУ */
	private VocLpuFunction theLpuFunction;
     
    /** Телефон */
    private String thePhone ;
    /** Руководитель */
    private String theDirector ;
    /** Срок действия лицензии */
    private Date theLincenseExpired ;
    /** Номер лицензии */
    private String theLicenseNumber ;
    /** Эл. адрес */
    private String theEmail ;
    /** ОГРН */
    private Long theOgrn ;
    /** ИНН */
    private Long theInn ;
    /** Корпус */
    private String theHouseBuilding ;
    /** Номер дома */
    private String theHouseNumber ;
    /** Адрес */
    private Address theAddress ;
    /** Код ОМС */
    private String theOmcCode ;

    /** Терапевтические участки */
    private List<LpuArea> theAreas ;
    /** Наименование ЛПУ */
    private String theName ;

    /** Комментарий */
    private String theComment ;
     /** Работники ЛПУ */
     private List<Worker> theWorker ;
     
     /** Код федеральный */
	@Comment("Код федеральный")
	public String getCodef() {return theCodef;}
	public void setCodef(String aCodef) {theCodef = aCodef;}

	/** Код федеральный */
	private String theCodef;
	
	/** Руководитель */
	@Comment("Руководитель")
	@OneToOne
	public WorkFunction getManager() {
		return theManager;
	}

	public void setManager(WorkFunction aManager) {
		theManager = aManager;
	}

	/** Руководитель */
	private WorkFunction theManager;
	
	/** Приемное отделение */
	@Comment("Приемное отделение")
	@OneToOne
	public VocPigeonHole getPigeonHole() {
		return thePigeonHole;
	}

	public void setPigeonHole(VocPigeonHole aPigeonHole) {
		thePigeonHole = aPigeonHole;
	}
	
	/** Интервал разрешенной регистрации */
	@Comment("Интервал разрешенной регистрации")
	public Integer getRegistrationInterval() {return theRegistrationInterval;}
	public void setRegistrationInterval(Integer aRegistrationInterval) {theRegistrationInterval = aRegistrationInterval;}

	/** Интервал разрешенной регистрации */
	private Integer theRegistrationInterval;

	/** Приемное отделение */
	private VocPigeonHole thePigeonHole;
	
	/** Не показывать удаленным пользователям */
	@Comment("Не показывать удаленным пользователям")
	public Boolean getIsNoViewRemoteUser() {return theIsNoViewRemoteUser;}
	public void setIsNoViewRemoteUser(Boolean aNoViewRemoteUser) {theIsNoViewRemoteUser = aNoViewRemoteUser;}

	/** Не показывать удаленным пользователям */
	private Boolean theIsNoViewRemoteUser;
	
	/** Принтер по умолчанию */
	@Comment("Принтер по умолчанию")
	@OneToOne
	public CopyingEquipment getCopyingEquipmentDefault() {return theCopyingEquipmentDefault;}
	public void setCopyingEquipmentDefault(CopyingEquipment aCopyingEquipmentDefault) {theCopyingEquipmentDefault = aCopyingEquipmentDefault;}

	/** Принтер по умолчанию */
	private CopyingEquipment theCopyingEquipmentDefault;
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Автогенерация расписания */
	@Comment("Автогенерация расписания")
	public Boolean getAutoGeneration() {return theAutoGeneration;}
	public void setAutoGeneration(Boolean aAutoGeneration) {theAutoGeneration = aAutoGeneration;}

	/** Автогенерация расписания */
	private Boolean theAutoGeneration;
	
	/** Номер в ФСС */
	public String getSocCode() {return theSocCode;}
	public void setSocCode(String aSocCode) {theSocCode = aSocCode;}

	/** Номер в ФСС */
	private String theSocCode;

	/** Возможен забор крови */
	@Comment("Возможен забор крови")
	public Boolean getIsIntakeBioMaterial() {return theIsIntakeBioMaterial;}
	public void setIsIntakeBioMaterial(Boolean aIsIntakeBioMaterial) {theIsIntakeBioMaterial = aIsIntakeBioMaterial;}

	/** Возможен забор крови */
	private Boolean theIsIntakeBioMaterial;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	public String getCodeDepartment() {return theCodeDepartment;}
	public void setCodeDepartment(String aCodeDepartment) {theCodeDepartment = aCodeDepartment;}

	/** Код подразделения */
	private String theCodeDepartment;
	
	/** Короткое наименование */
	@Comment("Короткое наименование")
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Короткое наименование */
	private String theShortName;
	
	/** Доступ на создание операций по отделению */
	@Comment("Доступ на создание операций по отделению")
	@OneToOne
	public VocLpuAccessEnterOperation getAccessEnterOperation() {return theAccessEnterOperation;}
	public void setAccessEnterOperation(VocLpuAccessEnterOperation aAccessEnterOperation) {theAccessEnterOperation = aAccessEnterOperation;}

	/** Доступ на создание операций по отделению */
	private VocLpuAccessEnterOperation theAccessEnterOperation;
	/** Префикс для шаблонов ЛН */
	@Comment("Префикс для шаблонов ЛН")
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
	public Long getEmergencyCabinet() {return theEmergencyCabinet;}
	public void setEmergencyCabinet(Long aEmergencyCabinet) {theEmergencyCabinet = aEmergencyCabinet;}

	/** Экстренный кабинет */
	private Long theEmergencyCabinet;
	
	/** Отделения для новорожденных */
	@Comment("Отделения для новорожденных")
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
	public Boolean getIsMaternityWard() {
		return theIsMaternityWard;
	}

	public void setIsMaternityWard(Boolean aIsMaternityWard) {
		theIsMaternityWard = aIsMaternityWard;
	}

	/** Родильное отделение */
	private Boolean theIsMaternityWard;

	/** Отделение патологии беременности */
	@Comment("Отделение патологии беременности")
	public Boolean getIsPatologyPregnant() {
		return theIsPatologyPregnant;
	}

	public void setIsPatologyPregnant(Boolean aIsPatologyPregnant) {
		theIsPatologyPregnant = aIsPatologyPregnant;
	}

	/** Отделение патологии беременности */
	private Boolean theIsPatologyPregnant;
	
	/** Уровень оказания медицинской помощи */
	@Comment("Уровень оказания медицинской помощи")
	public Integer getLpuLevel() {return theLpuLevel;}
	public void setLpuLevel(Integer aLpuLevel) {theLpuLevel = aLpuLevel;}
	/** Уровень оказания медицинской помощи */
	private Integer theLpuLevel;

	/** Профиль КИЛИ */
	@Comment("Профиль КИЛИ")
	@ManyToOne
	public VocKiliProfile getKiliProfile() {return theKiliProfile;}
	public void setKiliProfile(VocKiliProfile aKiliProfile) {theKiliProfile = aKiliProfile;}
	/** Профиль КИЛИ */
	private VocKiliProfile theKiliProfile;
	
	/** В архиве */
	@Comment("В архиве")
	public Boolean getIsArchive() {return theIsArchive;}
	public void setIsArchive(Boolean aIsArchive) {theIsArchive = aIsArchive;}
	/** В архиве */
	private Boolean theIsArchive;

	/** Обсервационное? */
	@Comment("Обсервационное?")
	public Boolean getIsObservable() {return theIsObservable;}
	public void setIsObservable(Boolean aIsObservable) {theIsObservable = aIsObservable;}
	/** Обсервационное? */
	private Boolean theIsObservable;

	/** Палата новорождённых? */
	@Comment("Палата новорождённых?")
	public Boolean getIsNewBorn() {return theIsNewBorn;}
	public void setIsNewBorn(Boolean aIsNewBorn) {theIsNewBorn = aIsNewBorn;}
	/** Палата новорождённых? */
	private Boolean theIsNewBorn;

	/** Создают ли кадриоскрининг новорождённым? */
	@Comment("Создают ли кадриоскрининг новорождённым?")
	public Boolean getIsCreateCardiacScreening() {return theIsCreateCardiacScreening;}
	public void setIsCreateCardiacScreening(Boolean aIsCreateCardiacScreening) {theIsCreateCardiacScreening = aIsCreateCardiacScreening;}
	/** Создают ли кадриоскрининг новорождённым? */
	private Boolean theIsCreateCardiacScreening;

	/** Офтальмологическое? */
	@Comment("Офтальмологическое?")
	public Boolean getIsOphthalmic() {return theIsOphthalmic;}
	public void setIsOphthalmic(Boolean aIsOphthalmic) {theIsOphthalmic = aIsOphthalmic;}
	/** Офтальмологическое? */
	private Boolean theIsOphthalmic;

	/** Учитывать в отчёте по КР? */
	@Comment("Учитывать в отчёте по КР?")
	public Boolean getIsReportKMP() {return theIsReportKMP;}
	public void setIsReportKMP(Boolean aIsReportKMP) {theIsReportKMP = aIsReportKMP;}
	/** Учитывать в отчёте по КР? */
	private Boolean theIsReportKMP;
}