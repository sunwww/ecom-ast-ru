package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Сотрудник
 */
@EntityForm
@EntityFormPersistance(clazz= Worker.class)
@Comment("Сотрудник")
@WebTrail(comment = "Сотрудник", nameProperties= "workerInfo", view="entityView-mis_worker.do", list="entityParentList-mis_worker.do")
@Parent(property="lpu", parentForm= MisLpuForm.class,orderBy="person.lastname,person.firstname,person.middlename")
@EntityFormSecurityPrefix("/Policy/Mis/Worker/Worker")
public class WorkerForm extends IdEntityForm {
    

	/** Персона */
	@Comment("Персона")
	@Persist @Required
	public Long getPerson() {
		return thePerson;
	}

	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}

	/** Персона */
	private Long thePerson;

    
	
	/** ЛПУ */
    @Comment("ЛПУ")
	@Persist @Required
    public Long getLpu() { return theLpu ; }
    public void setLpu(Long aLpu) { theLpu = aLpu ; }
    /** ЛПУ */
    private Long theLpu ;



    /** Информаия о сотруднике */
	@Comment("Информаия о сотруднике")
	@Persist
	public String getWorkerInfo() {
		return theWorkerInfo;
	}

	public void setWorkerInfo(String aWorkerInfo) {
		theWorkerInfo = aWorkerInfo;
	}

	/** Информаия о сотруднике */
	private String theWorkerInfo;





    /** ФИО и должность специалиста */
	@Comment("ФИО и должность специалиста")
	@Persist
	public String getDoctorInfo() {
		return theDoctorInfo;
	}

	public void setDoctorInfo(String aDoctorInfo) {
		theDoctorInfo = aDoctorInfo;
	}

	/** ФИО и должность специалиста */
	private String theDoctorInfo;
    



    /** Должность */
	@Comment("Должность")
	@Persist
	public String getPost() {
		return thePost;
	}

	public void setPost(String aPost) {
		thePost = aPost;
	}

	/** Должность */
	private String thePost;


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

	
	
	
//	/** ЛПУ */
//    @Persist
//    public Long getLpu() { return theLpu ; }
//    public void setLpu(Long aLpu) { theLpu = aLpu ; }

//    /** Имя */
//    @Comment("Имя")
//    @Persist
//    @Required
//    @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
//    public String getFirstname() { return theFirstname ; }
//    public void setFirstname(String aFirstname) { theFirstname = aFirstname ; }
//
//    /** Фамилия */
//    @Comment("Фамилия")
//    @Persist
//    @Required
//    @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
//    public String getLastname() { return theLastname ; }
//    public void setLastname(String aLastname) { theLastname = aLastname ; }
//
//    /** Отчество */
//    @Comment("Отчество")
//    @Persist
//    @Required
//    @DoUpperCase @DoInputNonLat @VInputNonLat @DoTrimString
//    public String getMiddlename() { return theMiddlename ; }
//    public void setMiddlename(String aMiddlename) { theMiddlename = aMiddlename ; }
//
//    /** Дата рождения */
//    @Comment("Дата рождения")
//    @Persist
//    @Required
//    @DateString
//    public String getBirthday() { return theBirthdate ; }
//    public void setBirthday(String aBirthdate) { theBirthdate = aBirthdate ; }
//
//    /** Информация о сотруднике */
//    @Persist
//    @Comment("Информация о человеке")
//    public String getWorkerInfo() {    return theWorkerInfo ;}
//    public void setWorkerInfo(String aWorkerInfo ) {  theWorkerInfo = aWorkerInfo ; }
//
//    /** Пол */
//    @Comment("Пол")
//    @Persist
//    @Required
//    public Long getSex() { return theSex ;}
//    public void setSex(Long aSex ) { theSex = aSex ; }
//
//    /** Домашний телефон */
//    @Persist
//    @Comment("Домашний телефон")
//    public String getHomePhone() {    return theHomePhone ;}
//    public void setHomePhone(String aHomePhone ) {  theHomePhone = aHomePhone ; }
//
//    /** Сотовый телефон */
//    @Persist
//    @Comment("Сотовый телефон")
//    public String getMobilPhone() {    return theMobilPhone ;}
//    public void setMobilPhone(String aMobilPhone ) {  theMobilPhone = aMobilPhone ; }
//
//    /** Рабочий телефон */
//    @Persist
//    @Comment("Рабочий телефон")
//    public String getWorkPhone() {    return theWorkPhone ;}
//    public void setWorkPhone(String aWorkPhone ) {  theWorkPhone = aWorkPhone ; }
//
//    /** E-mail */
//    @Persist
//    @Comment("E-mail")
//    public String getEmail() {    return theEmail ;}
//    public void setEmail(String aEmail ) {  theEmail = aEmail ; }
//
//    /** E-mail */
//    private String theEmail ;
//    /** Рабочий телефон */
//    private String theWorkPhone ;
//    /** Сотовый телефон */
//    private String theMobilPhone ;
//    /** Домашний телефон */
//    private String theHomePhone ;
//    /** Пол */
//    private Long theSex ;
//    /** Информация о сотруднике */
//    private String theWorkerInfo ;
//    /** Дата рождения */
//    private String theBirthdate ;
//    /** Отчество */
//    private String theMiddlename ;
//    /** Фамилия */
//    private String theLastname ;
//    /** Имя */
//    private String theFirstname ;
//    /** ЛПУ */
//    private Long theLpu ;
//
//
//    /** Номер паспорта */
//    @Persist @Required @DoUpperCase @DoTrimString
//    @MinLength(value = 6) @MaxLength(value = 6)
//    public String getPassportNumber() { return thePassportNumber ; }
//    public void setPassportNumber(String aPassportNumber) { thePassportNumber = aPassportNumber ; }
//
//    /** Серия паспорта */
//    @Persist @Required @DoUpperCase @DoTrimString
//    @MinLength(value = 4) @MaxLength(value = 4)
//    public String getPassportSeries() { return thePassportSeries ; }
//    public void setPassportSeries(String aPassportSeries) { thePassportSeries = aPassportSeries ; }
//
//    /** Дата выдачи */
//    @Persist @Required @DateString
//    public String getPassportDateIssued() { return thePassportDateIssue ; }
//    public void setPassportDateIssued(String aPassportDateIssue) { thePassportDateIssue = aPassportDateIssue ; }
//
//    /** Кем выдан */
//    @Persist @Required @DoUpperCase @DoTrimString
//    public String getPassportWhomIssued() { return thePassportWhomIssued ; }
//    public void setPassportWhomIssued(String aPassportWhomIssued) { thePassportWhomIssued = aPassportWhomIssued ; }
//
//    /** СНИЛС */
//    @Persist @Required @DoUpperCase @DoTrimString
//    //@MinLength(value = 11) @MaxLength(value = 11)
//    public String getSnils() { return theSnils ; }
//    public void setSnils(String aSnils) { theSnils = aSnils ; }
//
//    /** ИНН */
//    @Persist @Required @DoUpperCase @DoTrimString
//    //@MinLength(value = 12) @MaxLength(value = 12)
//    public String getInn() { return theInn ; }
//    public void setInn(String aInn) { theInn = aInn ; }
//
//    /** Дом */
//    @Persist
//    public String getHouseNumber() { return theHouseNumber ; }
//    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }
//
//    /** Корпус */
//    @Persist
//    public String getHouseBuilding() { return theHouseBuilding ; }
//    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }
//
//    /** Квартира */
//    @Persist
//    public String getFlatNumber() { return theFlatNumber ; }
//    public void setFlatNumber(String aFlatNumber) { theFlatNumber = aFlatNumber ; }
//
//    /** Адрес */
//    @Persist
//    public Long getAddress() { return theAddress ; }
//    public void setAddress(Long aAddress) { theAddress = aAddress ; }
//
//    /** Код профиля отделения для стационара или специалиста для поликлиники */
//	@Comment("Код профиля отделения для стационара или специалиста для поликлиники")
//	@Persist
//	public Long getOmcDepType() {
//		return theOmcDepType;
//	}
//
//	public void setOmcDepType(Long aOmcDepType) {
//		theOmcDepType = aOmcDepType;
//	}
//	
//	/** Врачебная должность по ОМС */
//	@Comment("Врачебная должность по ОМС")
//	@Persist
//	public Long getOmcDoctorPost() {
//		return theOmcDoctorPost;
//	}
//
//	public void setOmcDoctorPost(Long aOmcDoctorPost) {
//		theOmcDoctorPost = aOmcDoctorPost;
//	}    
//
//	/** Пользователь системы */
//	@Comment("Пользователь системы")
//	@Persist
//	public Long getSecUser() {
//		return theSecUser;
//	}
//
//	public void setSecUser(Long aSecUser) {
//		theSecUser = aSecUser;
//	}
//
//	/** Пользователь системы */
//	private Long theSecUser;
//	/** Код профиля отделения для стационара или специалиста для поликлиники */
//	private Long theOmcDepType;
//	/** Врачебная должность по ОМС */
//	private Long theOmcDoctorPost;   
//    /** Адрес */
//    private Long theAddress ;
//    /** Квартира */
//    private String theFlatNumber ;
//    /** Корпус */
//    private String theHouseBuilding ;
//    /** Дом */
//    private String theHouseNumber ;
//    /** СНИЛС */
//    private String theSnils ;
//    /** ИНН */
//    private String theInn ;
//    /** Кем выдан */
//    private String thePassportWhomIssued ;
//    /** Дата выдачи */
//    private String thePassportDateIssue ;
//    /** Серия паспорта */
//    private String thePassportSeries ;
//    /** Номер паспорта */
//    private String thePassportNumber ;
}