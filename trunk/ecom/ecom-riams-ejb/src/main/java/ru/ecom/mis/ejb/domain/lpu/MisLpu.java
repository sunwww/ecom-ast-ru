package ru.ecom.mis.ejb.domain.lpu;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.document.ejb.domain.DocumentFormJournal;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.diet.Diet;
import ru.ecom.mis.ejb.domain.equipment.Equipment;
import ru.ecom.mis.ejb.domain.lpu.voc.VocLpuFunction;
import ru.ecom.mis.ejb.domain.lpu.voc.VocMzDepType;
import ru.ecom.mis.ejb.domain.lpu.voc.VocPropertyAdmin;
import ru.ecom.mis.ejb.domain.lpu.voc.VocSubordination;
import ru.ecom.mis.ejb.domain.lpu.voc.VocWorkPlaceLevel;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
import ru.ecom.mis.ejb.domain.worker.Staff;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.service.lpu.ILpuService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
	
	/** Уровень рабочего места */
	@Comment("Уровень рабочего места")
	@OneToOne
	public VocWorkPlaceLevel getWorkPlaceLevel() {
		return theWorkPlaceLevel;
	}

	public void setWorkPlaceLevel(VocWorkPlaceLevel aWorkPlaceLevel) {
		theWorkPlaceLevel = aWorkPlaceLevel;
	}

	/** Уровень рабочего места */
	private VocWorkPlaceLevel theWorkPlaceLevel;
	
	/** Диеты */
	@Comment("Диеты")
	@OneToMany(mappedBy="lpu")
	public List<Diet> getDiets() {
		return theDiets;
	}

	public void setDiets(List<Diet> aDiets) {
		theDiets = aDiets;
	}
	
	/** Операционные */
	@Comment("Операционные")
	@OneToMany(mappedBy="lpu")
	public List<OperatingRoom> getOperatingRooms() {
		return theOperatingRooms;
	}

	public void setOperatingRooms(List<OperatingRoom> aOperatingRooms) {
		theOperatingRooms = aOperatingRooms;
	}

	/** Операционные */
	private List<OperatingRoom> theOperatingRooms;

	/** Диеты */
	private List<Diet> theDiets;
	
	/** Вид собственности */
	@Comment("Вид собственности")
	@OneToOne
	public VocPropertyAdmin getPropertyAdmin() {
		return thePropertyAdmin;
	}

	public void setPropertyAdmin(VocPropertyAdmin aPropertyAdmin) {
		thePropertyAdmin = aPropertyAdmin;
	}

	/** Вид собственности */
	private VocPropertyAdmin thePropertyAdmin;
	 
	/** Подчиненность */
	@Comment("Подчиненность")
	@OneToOne
	public VocSubordination getSubordination() {
		return theSubordination;
	}

	public void setSubordination(VocSubordination aSubordination) {
		theSubordination = aSubordination;
	}

	/** Подчиненность */
	private VocSubordination theSubordination;
	
	/** Журналы учета бланков документов */
	@Comment("Журналы учета бланков документов")
	@OneToMany(mappedBy="recieveLpu", cascade=CascadeType.ALL)
	public List<DocumentFormJournal> getDocumentFormJournals() {
		return theDocumentFormJournals;
	}

	public void setDocumentFormJournals(List<DocumentFormJournal> aDocumentFormJournals) {
		theDocumentFormJournals = aDocumentFormJournals;
	}

	/** Журналы учета бланков документов */
	private List<DocumentFormJournal> theDocumentFormJournals;

	
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

    /** Штатное расписание */
    @OneToMany(mappedBy = "lpu", cascade= ALL)
    public List<Staff> getStateList() { return theStateList ; }
    public void setStateList(List<Staff> aStateList) { theStateList = aStateList ; }


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
	
	/** Профиль отеделения */
	@Comment("Профиль отеделения")
	@OneToOne
	public VocMzDepType getProfile() {
		return theProfile;
	}

	public void setProfile(VocMzDepType aProfile) {
		theProfile = aProfile;
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

	/** Профиль отеделения */
	private VocMzDepType theProfile;

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
    /** Штатное расписание */
     private List<Staff> theStateList ;
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

}
