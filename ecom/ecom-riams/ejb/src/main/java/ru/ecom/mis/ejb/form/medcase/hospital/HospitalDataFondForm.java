package ru.ecom.mis.ejb.form.medcase.hospital;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.hospital.HospitalDataFond;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Данные фонда по 263 приказу")
@EntityForm
@EntityFormPersistance(clazz= HospitalDataFond.class)
@WebTrail(comment = "Данные фонда по 263 приказу", nameProperties= "id", view="entityParentView-stac_dataFond.do")
//@Parent(property="surgicalOperation", parentForm= SurgicalOperationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/HospitalDataFond")
public class HospitalDataFondForm extends IdEntityForm{

	/** Направление */
	@Persist @Comment("Направление")
	public Long getDirectMedCase() {return theDirectMedCase;}
	public void setDirectMedCase(Long aDirectMedCase) {theDirectMedCase = aDirectMedCase;}

	/** Госпитализация */
	@Persist @Comment("Госпитализация")
	public Long getHospitalMedCase() {return theHospitalMedCase;}
	public void setHospitalMedCase(Long aHospitalMedCase) {theHospitalMedCase = aHospitalMedCase;}

	/** Экстренность */
	@Persist @Comment("Экстренность")
	public Integer getEmergency() {return theEmergency;}
	public void setEmergency(Integer aEmergency) {theEmergency = aEmergency;}

	/** Номер направления из фонда */
	@Persist @Comment("Номер направления из фонда")
	public String getNumberFond() {return theNumberFond;}
	public void setNumberFond(String aNumberFond) {theNumberFond = aNumberFond;}

	/** Фамилия */
	@Persist @Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

	/** Имя */
	@Persist @Comment("Имя")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

	/** Отчетсво */
	@Persist @Comment("Отчетсво")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Дата рождения */
	@Persist @Comment("Дата рождения")
	@DateString @DoDateString
	public String getBirthday() {return theBirthday;}
	public void setBirthday(String aBirthday) {theBirthday = aBirthday;}

	/** Профиль */
	@Persist @Comment("Профиль")
	public String getProfile() {return theProfile;}
	public void setProfile(String aProfile) {theProfile = aProfile;}

	/** Дата предварительной госпитализации */
	@Persist @Comment("Дата предварительной госпитализации")
	@DateString @DoDateString
	public String getPreHospDate() {return thePreHospDate;}
	public void setPreHospDate(String aPreHospDate) {thePreHospDate = aPreHospDate;}

	/** Дата госпитализации */
	@Persist @Comment("Дата госпитализации")
	@DateString @DoDateString
	public String getHospDate() {return theHospDate;}
	public void setHospDate(String aHospDate) {theHospDate = aHospDate;}

	/** Пол */
	@Persist @Comment("Пол")
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}

	/** Дата направления */
	@Persist @Comment("Дата направления")
	@DateString @DoDateString
	public String getDirectDate() {return theDirectDate;}
	public void setDirectDate(String aDirectDate) {theDirectDate = aDirectDate;}

	/** Дата направления */
	private String theDirectDate;
	/** Пол */
	private Long theSex;
	/** Дата госпитализации */
	private String theHospDate;
	/** Дата предварительной госпитализации */
	private String thePreHospDate;
	/** Профиль */
	private String theProfile;
	/** Дата рождения */
	private String theBirthday;
	/** Отчетсво */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
	/** Номер направления из фонда */
	private String theNumberFond;
	/** Экстренность */
	private Integer theEmergency;
	/** Госпитализация */
	private Long theHospitalMedCase;
	/** Направление */
	private Long theDirectMedCase;
	
	/** Диагноз */
	@Persist @Comment("Диагноз")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Телефон */
	@Persist @Comment("Телефон")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Номер стат.карты */
	@Persist @Comment("Номер стат.карты")
	public String getStatCard() {return theStatCard;}
	public void setStatCard(String aStatCard) {theStatCard = aStatCard;}

	/** Номер стат.карты */
	private String theStatCard;
	/** Телефон */
	private String thePhone;
	/** Диагноз */
	private String theDiagnosis;
	
	/** Вид полиса */
	@Persist @Comment("Вид полиса")
	public String getTypePolicy() {return theTypePolicy;}
	public void setTypePolicy(String aTypePolicy) {theTypePolicy = aTypePolicy;}

	/** Серия полиса */
	@Persist @Comment("Серия полиса")
	public String getSeriesPolicy() {return theSeriesPolicy;}
	public void setSeriesPolicy(String aSeriesPolicy) {theSeriesPolicy = aSeriesPolicy;}

	/** Номер полиса */
	@Persist @Comment("Номер полиса")
	public String getNumberPolicy() {return theNumberPolicy;}
	public void setNumberPolicy(String aNumberPolicy) {theNumberPolicy = aNumberPolicy;}

	/** СМО */
	@Persist @Comment("СМО")
	public String getSmo() {return theSmo;}
	public void setSmo(String aSmo) {theSmo = aSmo;}

	/** СМО ОГРН */
	@Persist @Comment("СМО ОГРН")
	public String getSmoOgrn() {return theSmoOgrn;}
	public void setSmoOgrn(String aSmoOgrn) {theSmoOgrn = aSmoOgrn;}

	/** СМО ОКАТО */
	@Persist @Comment("СМО ОКАТО")
	public String getSmoOkato() {return theSmoOkato;}
	public void setSmoOkato(String aSmoOkato) {theSmoOkato = aSmoOkato;}

	/** СМО наименование */
	@Persist @Comment("СМО наименование")
	public String getSmoName() {return theSmoName;}
	public void setSmoName(String aSmoName) {theSmoName = aSmoName;}

	/** Форма помощи */
	@Persist @Comment("Форма помощи")
	public String getFormHelp() {return theFormHelp;}
	public void setFormHelp(String aFormHelp) {theFormHelp = aFormHelp;}

	/** Направ. ЛПУ */
	@Persist @Comment("Направ. ЛПУ")
	public String getOrderLpuCode() {return theOrderLpuCode;}
	public void setOrderLpuCode(String aOrderLpuCode) {	theOrderLpuCode = aOrderLpuCode;}

	/** Куда направлен пациент */
	@Persist @Comment("Куда направлен пациент")
	public String getDirectLpuCode() {return theDirectLpuCode;}
	public void setDirectLpuCode(String aDirectLpuCode) {theDirectLpuCode = aDirectLpuCode;}

	/** Куда направлен пациент */
	private String theDirectLpuCode;
	/** Направ. ЛПУ */
	private String theOrderLpuCode;
	/** Форма помощи */
	private String theFormHelp;
	/** СМО наименование */
	private String theSmoName;
	/** СМО ОКАТО */
	private String theSmoOkato;
	/** СМО ОГРН */
	private String theSmoOgrn;
	/** СМО */
	private String theSmo;
	/** Номер полиса */
	private String theNumberPolicy;
	/** Серия полиса */
	private String theSeriesPolicy;
	/** Вид полиса */
	private String theTypePolicy;
	
	/** Снилс */
	@Persist @Comment("Снилс")
	public String getSnils() {return theSnils;}
	public void setSnils(String aSnils) {theSnils = aSnils;}

	/** Снилс */
	private String theSnils;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getImportDate() {return theImportDate;}
	public void setImportDate(String aImportDate) {theImportDate = aImportDate;}

	/** Дата импорта */
	private String theImportDate;
	
	/** Table1 */
	@Persist @Comment("Table1")
	public Boolean getIsTable1() {return theIsTable1;}
	public void setIsTable1(Boolean aIsTable1) {theIsTable1 = aIsTable1;}
	
	/** Table1 */
	private Boolean theIsTable1;
	/** Table2 */
	@Persist @Comment("Table2")
	public Boolean getIsTable2() {return theIsTable2;}
	public void setIsTable2(Boolean aIsTable2) {theIsTable2 = aIsTable2;}
	
	/** Table2 */
	private Boolean theIsTable2;
	/** Table3 */
	@Persist @Comment("Table3")
	public Boolean getIsTable3() {return theIsTable3;}
	public void setIsTable3(Boolean aIsTable3) {theIsTable3 = aIsTable3;}
	
	/** Table3 */
	private Boolean theIsTable3;
	/** Table4 */
	@Persist @Comment("Table4")
	public Boolean getIsTable4() {return theIsTable4;}
	public void setIsTable4(Boolean aIsTable4) {theIsTable4 = aIsTable4;}
	
	/** Table4 */
	private Boolean theIsTable4;
	/** Table4 */
	@Persist @Comment("Table5")
	public Boolean getIsTable5() {return theIsTable5;}
	public void setIsTable5(Boolean aIsTable5) {theIsTable5 = aIsTable5;}
	
	/** Table5 */
	private Boolean theIsTable5;
	
	/** Время госпитализации */
	@Persist @Comment("Время госпитализации")
	@TimeString @DoTimeString
	public String getHospTime() {return theHospTime;}
	public void setHospTime(String aHospTime) {theHospTime = aHospTime;}

	/** Дата выписки */
	@Persist @Comment("Дата выписки")
	@DateString @DoDateString
	public String getHospDischargeDate() {return theHospDischargeDate;}
	public void setHospDischargeDate(String aHospDischargeDate) {theHospDischargeDate = aHospDischargeDate;}

	/** Дата выписки */
	private String theHospDischargeDate;
	/** Время госпитализации */
	private String theHospTime;
	
	/** Отказ от госпитализации */
	@Persist @Comment("Отказ от госпитализации")
	public Long getDeniedHospital() {return theDeniedHospital;}
	public void setDeniedHospital(Long aDeniedHospital) {theDeniedHospital = aDeniedHospital;}

	/** Отказ от госпитализации */
	private Long theDeniedHospital;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename5() {return theFilename5;}
	public void setFilename5(String aFilename5) {theFilename5 = aFilename5;}

	/** Файл */
	private String theFilename5;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport5() {return theDateImport5;}
	public void setDateImport5(String aDateImport5) {theDateImport5 = aDateImport5;}

	/** Дата импорта */
	private String theDateImport5;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename4() {return theFilename4;}
	public void setFilename4(String aFilename4) {theFilename4 = aFilename4;}

	/** Файл */
	private String theFilename4;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport4() {return theDateImport4;}
	public void setDateImport4(String aDateImport4) {theDateImport4 = aDateImport4;}

	/** Дата импорта */
	private String theDateImport4;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename3() {return theFilename3;}
	public void setFilename3(String aFilename3) {theFilename3 = aFilename3;}

	/** Файл */
	private String theFilename3;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport3() {return theDateImport3;}
	public void setDateImport3(String aDateImport3) {theDateImport3 = aDateImport3;}

	/** Дата импорта */
	private String theDateImport3;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename2() {return theFilename2;}
	public void setFilename2(String aFilename2) {theFilename2 = aFilename2;}

	/** Файл */
	private String theFilename2;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport2() {return theDateImport2;}
	public void setDateImport2(String aDateImport2) {theDateImport2 = aDateImport2;}

	/** Дата импорта */
	private String theDateImport2;
	

	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename1() {return theFilename1;}
	public void setFilename1(String aFilename1) {theFilename1 = aFilename1;}

	/** Файл */
	private String theFilename1;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport1() {return theDateImport1;}
	public void setDateImport1(String aDateImport1) {theDateImport1 = aDateImport1;}

	/** Дата импорта */
	private String theDateImport1;
	
	
	/** Тип помощи дн круг */
	@Comment("Тип помощи дн круг")
	@Persist
	public String getBedSubType() {
		return theBedSubType;
	}

	public void setBedSubType(String aBedSubType) {
		theBedSubType = aBedSubType;
	}

	/** Тип помощи дн круг */
	private String theBedSubType;


	
	/** Детский профиль */
	@Persist @Comment("Детский профиль")
	public String getForChild() {return theForChild;}
	public void setForChild(String aForChild) {theForChild = aForChild;}

	/** Детский профиль */
	private String theForChild;
	
	/** Без госпитализаций */
	@Persist @Comment("Без госпитализаций")
	public Boolean getWithoutHosp() {return theWithoutHosp;}
	public void setWithoutHosp(Boolean aWithoutHosp) {theWithoutHosp = aWithoutHosp;}

	/** Без госпитализаций */
	private Boolean theWithoutHosp;

    /** Время импорта */
    public long getTime() { return theTime ; }
    public void setTime(long aTime) { theTime = aTime ; }


    /** Время импорта */
    private long theTime ;
    
    /** Фамилия имя отчетство */
	@Persist @Comment("Фамилия имя отчетство")
	public String getFio() {return theFio;}
	public void setFio(String aFio) {theFio = aFio;	}

	/** Фамилия имя отчетство */
	private String theFio;

}
