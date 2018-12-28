package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.patient.voc.VocRayon;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

@Comment("Пациент прикрепление")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
	@AIndex(properties = { "lastname","firstname","middlename","birthday" }),
	@AIndex(properties = { "commonNumber" })
})
public class PatientAttachedImport extends BaseEntity implements IImportData {

	public static final String STATUS_CHECK_TYPE_AUTOMATIC="A" ;
	public static final String STATUS_CHECK_TYPE_PACKAGE="P" ;
	public static final String STATUS_CHECK_TYPE_MANUAL="M" ;

    /** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
    @AFormatFieldSuggest({"ENP" })
	public String getCommonNumber() {return theCommonNumber;}
	public void setCommonNumber(String aCommonNumber) {theCommonNumber = aCommonNumber;}

	/** Фамилия */
	@Comment("Фамилия")
	@AFormatFieldSuggest({"FAM" })
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

	/** Имя */
	@Comment("Имя")
	@AFormatFieldSuggest({"IM" })
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

	/** Отчество */
	@Comment("Отчество")
	@AFormatFieldSuggest({"OT" })
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Пол */
	@Comment("Пол")
	@AFormatFieldSuggest({"CW" })
	public String getSex() {return theSex;}
	public void setSex(String aSex) {theSex = aSex;}

	/** Дата рождения */
	@Comment("Дата рождения")
	@AFormatFieldSuggest({"DR" })
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}

	/** СНИЛС */
	@Comment("СНИЛС")
	@AFormatFieldSuggest({"SS" })
	public String getSnils() {return theSnils;}
	public void setSnils(String aSnils) {theSnils = aSnils;}

	/** Тип документа */
	@Comment("Тип документа")
	@AFormatFieldSuggest({"DOCTP" })
	public String getDocType() {return theDocType;}
	public void setDocType(String aDocType) {theDocType = aDocType;}

	/** Серия документа */
	@Comment("Серия документа")
	@AFormatFieldSuggest({"DOCS" })
	public String getDocSeries() {return theDocSeries;}
	public void setDocSeries(String aDocSeries) {theDocSeries = aDocSeries;}

	/** Номер документа */
	@Comment("Номер документа")
	@AFormatFieldSuggest({"DOCN" })
	public String getDocNumber() {return theDocNumber;}
	public void setDocNumber(String aDocNumber) {theDocNumber = aDocNumber;}

	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	@AFormatFieldSuggest({"DOCDT" })
	public Date getDocDateIssued() {return theDocDateIssued;}
	public void setDocDateIssued(Date aDocDateIssued) {theDocDateIssued = aDocDateIssued;}

	/** Место рождения */
	@Comment("Место рождения")
	@AFormatFieldSuggest({"MR" })
	public String getBirthPlace() {return theBirthPlace;}
	public void setBirthPlace(String aBirthPlace) {theBirthPlace = aBirthPlace;}

	/** Бомж */
	@Comment("Бомж")
	@AFormatFieldSuggest({"BOMJ" })
	public String getBomj() {return theBomj;}
	public void setBomj(String aBomj) {theBomj = aBomj;}

	/** Область */
	@Comment("Область")
	@AFormatFieldSuggest({"SUBJ" })
	public String getRegion() {return theRegion;}
	public void setRegion(String aRegion) {theRegion = aRegion;}

	/** RN& */
	@Comment("RN&")
	@AFormatFieldSuggest({"RN" })
	public String getRn() {return theRn;}
	public void setRn(String aRn) {theRn = aRn;}

	/** Индекс */
	@Comment("Индекс")
	@AFormatFieldSuggest({"INDX" })
	public String getIndex() {return theIndex;}
	public void setIndex(String aIndex) {theIndex = aIndex;}

	/** Район наименование */
	@Comment("Район наименование")
	@AFormatFieldSuggest({"RNNAME" })
	public String getRayonName() {return theRayonName;}
	public void setRayonName(String aRayonName) {theRayonName = aRayonName;}

	/** Город */
	@Comment("Город")
	@AFormatFieldSuggest({"CITY" })
	public String getCity() {return theCity;}
	public void setCity(String aCity) {theCity = aCity;}

	/** NP& */
	@Comment("NP&")
	@AFormatFieldSuggest({"NP" })
	public String getNp() {return theNp;}
	public void setNp(String aNp) {theNp = aNp;}

	/** Улица */
	@Comment("Улица")
	@AFormatFieldSuggest({"UL" })
	public String getStreet() {return theStreet;}
	public void setStreet(String aStreet) {theStreet = aStreet;}

	/** Дом */
	@Comment("Дом")
	@AFormatFieldSuggest({"DOM" })
	public String getHouse() {return theHouse;}
	public void setHouse(String aHouse) {theHouse = aHouse;}

	/** Корпус */
	@Comment("Корпус")
	@AFormatFieldSuggest({"KOR" })
	public String getHousing() {return theHousing;}
	public void setHousing(String aHousing) {theHousing = aHousing;}

	/** Квартира */
	@Comment("Квартира")
	@AFormatFieldSuggest({"KV" })
	public String getApartment() {return theApartment;}
	public void setApartment(String aApartment) {theApartment = aApartment;}

	/** Страховая компания */
	@Comment("Страховая компания")
	@AFormatFieldSuggest({"Q" })
	public String getInsCompName() {return theInsCompName;}
	public void setInsCompName(String aInsCompName) {theInsCompName = aInsCompName;}

	/** Тип полиса */
	@Comment("Тип полиса")
	@AFormatFieldSuggest({"OPDOC" })
	public String getPolicyType() {return thePolicyType;}
	public void setPolicyType(String aPolicyType) {thePolicyType = aPolicyType;}

	/** Серия полиса */
	@Comment("Серия полиса")
	@AFormatFieldSuggest({"SPOL" })
	public String getPolicySeries() {return thePolicySeries;}
	public void setPolicySeries(String aPolicySeries) {thePolicySeries = aPolicySeries;}

	/** Номер полиса */
	@Comment("Номер полиса")
	@AFormatFieldSuggest({"NPOL" })
	public String getPolicyNumber() {return thePolicyNumber;}
	public void setPolicyNumber(String aPolicyNumber) {thePolicyNumber = aPolicyNumber;}

	/** Дата начала действия полиса */
	@Comment("Дата начала действия полиса")
	@AFormatFieldSuggest({"DBEG" })
	public Date getPolicyDateFrom() {return thePolicyDateFrom;}
	public void setPolicyDateFrom(Date aPolicyDateFrom) {thePolicyDateFrom = aPolicyDateFrom;}

	/** Дата окончания действия полиса */
	@Comment("Дата окончания действия полиса")
	@AFormatFieldSuggest({"DEND" })
	public Date getPolicyDateTo() {return thePolicyDateTo;}
	public void setPolicyDateTo(Date aPolicyDateTo) {thePolicyDateTo = aPolicyDateTo;}

	/** Дата окончания действия полиса */
	private Date thePolicyDateTo;
	/** Дата начала действия полиса */
	private Date thePolicyDateFrom;
	/** Номер полиса */
	private String thePolicyNumber;
	/** Серия полиса */
	private String thePolicySeries;
	/** Тип полиса */
	private String thePolicyType;
	/** Страховая компания */
	private String theInsCompName;
	/** Квартира */
	private String theApartment;
	/** Корпус */
	private String theHousing;
	/** Дом */
	private String theHouse;
	/** Улица */
	private String theStreet;
	/** NP& */
	private String theNp;
	/** Город */
	private String theCity;
	/** Район наименование */
	private String theRayonName;
	/** Индекс */
	private String theIndex;
	/** RN& */
	private String theRn;
	/** Область */
	private String theRegion;
	/** Бомж */
	private String theBomj;
	/** Место рождения */
	private String theBirthPlace;
	/** Дата выдачи документа */
	private Date theDocDateIssued;
	/** Номер документа */
	private String theDocNumber;
	/** Серия документа */
	private String theDocSeries;
	/** Тип документа */
	private String theDocType;
	/** СНИЛС */
	private String theSnils;
	/** Дата рождения */
	private Date theBirthday;
	/** Пол */
	private String theSex;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
	/** Единый номер застрахованного */
	private String theCommonNumber;
	
    /** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Полис */
	@Comment("Полис")
	@OneToOne
	public MedPolicy getMedPolicy() {return theMedPolicy;}
	public void setMedPolicy(MedPolicy aMedPolicy) {theMedPolicy = aMedPolicy;}

	/** Адрес */
	@Comment("Адрес")
	@OneToOne
	public Address getAddressRegistration() {return theAddressRegistration;}
	public void setAddressRegistration(Address aAddressRegistration) {theAddressRegistration = aAddressRegistration;}

	/** Город врем */
	@Comment("Город врем")
	public String getCityT() {return theCityT;}
	public void setCityT(String aCityT) {theCityT = aCityT;}

	/** Город врем */
	private String theCityT;
	/** Адрес */
	private Address theAddressRegistration;
	/** Полис */
	private MedPolicy theMedPolicy;
	/** Пациент */
	private Long thePatient;
	
	/** Создан новый пациент */
	@Comment("Создан новый пациент")
	public Boolean getIsCreateNewPatient() {return theIsCreateNewPatient;}
	public void setIsCreateNewPatient(Boolean aIsCreateNewPatient) {theIsCreateNewPatient = aIsCreateNewPatient;}

	/** Обновлен пациент */
	@Comment("Обновлен пациент")
	public Boolean getIsUpdatePatient() {return theIsUpdatePatient;}
	public void setIsUpdatePatient(Boolean aIsUpdatePatient) {theIsUpdatePatient = aIsUpdatePatient;}

	/** Обновлен пациент */
	private Boolean theIsUpdatePatient;
	/** Создан новый пациент */
	private Boolean theIsCreateNewPatient;

    public long getTime() {return theTime  ;}
    public void setTime(long aTime) {theTime = aTime ;}
    private long theTime ;
    
    /** Серия полиса измененная */
	@Comment("Серия полиса измененная")
	public String getPolicySeriesEdit() {return thePolicySeriesEdit;}
	public void setPolicySeriesEdit(String aPolicySeriesEdit) {thePolicySeriesEdit = aPolicySeriesEdit;}

	/** Номер полиса измененный */
	@Comment("Номер полиса измененный")
	public String getPolicyNumberEdit() {return thePolicyNumberEdit;}
	public void setPolicyNumberEdit(String aPolicyNumberEdit) {thePolicyNumberEdit = aPolicyNumberEdit;}

	/** Номер полиса измененный */
	private String thePolicyNumberEdit;
	/** Серия полиса измененная */
	private String thePolicySeriesEdit;
	
	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getInsuranceCompany() {
		return theInsuranceCompany;
	}

	public void setInsuranceCompany(RegInsuranceCompany aInsuranceCompany) {
		theInsuranceCompany = aInsuranceCompany;
	}

	/** Страховая компания */
	private RegInsuranceCompany theInsuranceCompany;
	/** Район */
	@Comment("Район")
	@OneToOne
	public VocRayon getRayon() {return theRayon;}
	public void setRayon(VocRayon aRayon) {theRayon = aRayon;}

	/** Район */
	private VocRayon theRayon;
	
	/** Страна */
	@Comment("Страна")
	@AFormatFieldSuggest({"CN" })
	public String getCountry() {
		return theCountry;
	}

	public void setCountry(String aCountry) {
		theCountry = aCountry;
	}

	/** Страна */
	private String theCountry;
	
	/** Lpuauto */
	@Comment("Lpuauto")
	@AFormatFieldSuggest({"LPUAUTO" })
	public String getLpuauto() {return theLpuauto;}
	public void setLpuauto(String aLpuauto) {theLpuauto = aLpuauto;}

	/** Lpuauto */
	private String theLpuauto;
	
	/** Lpu */
	@Comment("Lpu")
	@AFormatFieldSuggest({"LPU" })
	public String getLpu() {return theLpu;}
	public void setLpu(String aLpu) {theLpu = aLpu;}

	/** Lpu */
	private String theLpu;
	
	/** Дата прикрепления */
	@Comment("Дата прикрепления")
	@AFormatFieldSuggest({"LPUDT" })
	public Date getLpuDateFrom() {return theLpuDateFrom;}
	public void setLpuDateFrom(Date aLpuDateFrom) {theLpuDateFrom = aLpuDateFrom;}

	/** Дата прикрепления */
	private Date theLpuDateFrom;
	
	/** Дата открепления */
	@Comment("Дата открепления")
	@AFormatFieldSuggest({"LPUDX" })
	public Date getLpuDateTo() {return theLpuDateTo;}
	public void setLpuDateTo(Date aLpuDateTo) {theLpuDateTo = aLpuDateTo;}

	/** Дата открепления */
	private Date theLpuDateTo;
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	@AFormatFieldSuggest({"DOCORG" })
	public String getDocWhom() {
		return theDocWhom;
	}

	public void setDocWhom(String aDocWhom) {
		theDocWhom = aDocWhom;
	}

	/** Кем выдан паспорт */
	private String theDocWhom;

	/** Идентификатор ФОМС */
	@Comment("Идентификатор ФОМС")
	@AFormatFieldSuggest({"PID"})
	public String getFondId() {return theFondId;}
	public void setFondId(String aFondId) {theFondId = aFondId;}
	/** Идентификатор ФОМС */
	private String theFondId;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	@AFormatFieldSuggest({"KODPODR"})
	public String getDepartment() {return theDepartment;}
	public void setDepartment(String aDepartment) {theDepartment = aDepartment;}
	/** Код подразделения */
	private String theDepartment;
	
	/** СНИЛС участкового врача */
	@Comment("СНИЛС участкового врача")
	@AFormatFieldSuggest({"SSD"})
	public String getDoctorSnils() {return theDoctorSnils;}
	public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}
	/** СНИЛС участкового врача */
	private String theDoctorSnils;
	
	/** Телефон */
	@Comment("Телефон")
	public String getPhone() {
		return thePhone;
	}

	public void setPhone(String aPhone) {
		thePhone = aPhone;
	}

	/** Телефон */
	private String thePhone;
	
	/** Участок */
	@Comment("Участок")
	public String getAreaNumber() {
		return theAreaNumber;
	}

	public void setAreaNumber(String aAreaNumber) {
		theAreaNumber = aAreaNumber;
	}

	/** Участок */
	private String theAreaNumber;
}
