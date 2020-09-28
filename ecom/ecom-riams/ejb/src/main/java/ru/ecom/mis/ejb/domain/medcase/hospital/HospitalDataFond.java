package ru.ecom.mis.ejb.domain.medcase.hospital;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Comment("Госпитализации данные фонда")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "hospitalMedCase" }) 
,@AIndex(properties = { "numberFond" })})
public class HospitalDataFond extends BaseEntity  implements IImportData {

	/** Направление */
	@Comment("Направление")
	@OneToOne
	public MedCase getDirectMedCase() {return theDirectMedCase;}
	public void setDirectMedCase(MedCase aDirectMedCase) {theDirectMedCase = aDirectMedCase;}

	/** Госпитализация */
	@Comment("Госпитализация")
	@OneToOne
	public MedCase getHospitalMedCase() {return theHospitalMedCase;}
	public void setHospitalMedCase(MedCase aHospitalMedCase) {theHospitalMedCase = aHospitalMedCase;}

	/** Экстренность */
	@Comment("Экстренность")
	public Integer getEmergency() {return theEmergency;}
	public void setEmergency(Integer aEmergency) {theEmergency = aEmergency;}

	/** Номер направления из фонда */
	@Comment("Номер направления из фонда")
	public String getNumberFond() {return theNumberFond;}
	public void setNumberFond(String aNumberFond) {theNumberFond = aNumberFond;}

	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

	/** Имя */
	@Comment("Имя")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

	/** Отчетсво */
	@Comment("Отчетсво")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}

	/** Профиль */
	@Comment("Профиль")
	public String getProfile() {return theProfile;}
	public void setProfile(String aProfile) {theProfile = aProfile;}

	/** Дата предварительной госпитализации */
	@Comment("Дата предварительной госпитализации")
	public Date getPreHospDate() {return thePreHospDate;}
	public void setPreHospDate(Date aPreHospDate) {thePreHospDate = aPreHospDate;}

	/** Дата госпитализации */
	@Comment("Дата госпитализации")
	public Date getHospDate() {return theHospDate;}
	public void setHospDate(Date aHospDate) {theHospDate = aHospDate;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return theSex;}
	public void setSex(VocSex aSex) {theSex = aSex;}

	/** Дата направления */
	@Comment("Дата направления")
	public Date getDirectDate() {return theDirectDate;}
	public void setDirectDate(Date aDirectDate) {theDirectDate = aDirectDate;}

	/** Дата направления */
	private Date theDirectDate;
	/** Пол */
	private VocSex theSex;
	/** Дата госпитализации */
	private Date theHospDate;
	/** Дата предварительной госпитализации */
	private Date thePreHospDate;
	/** Профиль */
	private String theProfile;
	/** Дата рождения */
	private Date theBirthday;
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
	private MedCase theHospitalMedCase;
	/** Направление */
	private MedCase theDirectMedCase;
	
	/** Диагноз */
	@Comment("Диагноз")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Телефон */
	@Comment("Телефон")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Номер стат.карты */
	@Comment("Номер стат.карты")
	public String getStatCard() {return theStatCard;}
	public void setStatCard(String aStatCard) {theStatCard = aStatCard;}

	/** Номер стат.карты */
	private String theStatCard;
	/** Телефон */
	private String thePhone;
	/** Диагноз */
	private String theDiagnosis;
	
	/** Вид полиса */
	@Comment("Вид полиса")
	public String getTypePolicy() {return theTypePolicy;}
	public void setTypePolicy(String aTypePolicy) {theTypePolicy = aTypePolicy;}

	/** Серия полиса */
	@Comment("Серия полиса")
	public String getSeriesPolicy() {return theSeriesPolicy;}
	public void setSeriesPolicy(String aSeriesPolicy) {theSeriesPolicy = aSeriesPolicy;}

	/** Номер полиса */
	@Comment("Номер полиса")
	public String getNumberPolicy() {return theNumberPolicy;}
	public void setNumberPolicy(String aNumberPolicy) {theNumberPolicy = aNumberPolicy;}

	/** СМО */
	@Comment("СМО")
	public String getSmo() {return theSmo;}
	public void setSmo(String aSmo) {theSmo = aSmo;}

	/** СМО ОГРН */
	@Comment("СМО ОГРН")
	public String getSmoOgrn() {return theSmoOgrn;}
	public void setSmoOgrn(String aSmoOgrn) {theSmoOgrn = aSmoOgrn;}

	/** СМО ОКАТО */
	@Comment("СМО ОКАТО")
	public String getSmoOkato() {return theSmoOkato;}
	public void setSmoOkato(String aSmoOkato) {theSmoOkato = aSmoOkato;}

	/** СМО наименование */
	@Comment("СМО наименование")
	public String getSmoName() {return theSmoName;}
	public void setSmoName(String aSmoName) {theSmoName = aSmoName;}

	/** Форма помощи */
	@Comment("Форма помощи")
	public String getFormHelp() {return theFormHelp;}
	public void setFormHelp(String aFormHelp) {theFormHelp = aFormHelp;}

	/** Направ. ЛПУ */
	@Comment("Направ. ЛПУ")
	public String getOrderLpuCode() {return theOrderLpuCode;}
	public void setOrderLpuCode(String aOrderLpuCode) {	theOrderLpuCode = aOrderLpuCode;}

	/** Куда направлен пациент */
	@Comment("Куда направлен пациент")
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
	@Comment("Снилс")
	public String getSnils() {return theSnils;}
	public void setSnils(String aSnils) {theSnils = aSnils;}

	/** Снилс */
	private String theSnils;
	
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getImportDate() {return theImportDate;}
	public void setImportDate(Date aImportDate) {theImportDate = aImportDate;}

	/** Дата импорта */
	private Date theImportDate;
	
	/** Table1 */
	@Comment("Table1")
	public Boolean getIsTable1() {return theIsTable1;}
	public void setIsTable1(Boolean aIsTable1) {theIsTable1 = aIsTable1;}
	
	/** Table1 */
	private Boolean theIsTable1;
	/** Table2 */
	@Comment("Table2")
	public Boolean getIsTable2() {return theIsTable2;}
	public void setIsTable2(Boolean aIsTable2) {theIsTable2 = aIsTable2;}
	
	/** Table2 */
	private Boolean theIsTable2;
	/** Table3 */
	@Comment("Table3")
	public Boolean getIsTable3() {return theIsTable3;}
	public void setIsTable3(Boolean aIsTable3) {theIsTable3 = aIsTable3;}
	
	/** Table3 */
	private Boolean theIsTable3;
	/** Table4 */
	@Comment("Table4")
	public Boolean getIsTable4() {return theIsTable4;}
	public void setIsTable4(Boolean aIsTable4) {theIsTable4 = aIsTable4;}
	
	/** Table4 */
	private Boolean theIsTable4;
	/** Table4 */
	@Comment("Table5")
	public Boolean getIsTable5() {return theIsTable5;}
	public void setIsTable5(Boolean aIsTable5) {theIsTable5 = aIsTable5;}
	
	/** Table5 */
	private Boolean theIsTable5;
	
	/** Время госпитализации */
	@Comment("Время госпитализации")
	public Time getHospTime() {return theHospTime;}
	public void setHospTime(Time aHospTime) {theHospTime = aHospTime;}

	/** Дата выписки */
	@Comment("Дата выписки")
	public Date getHospDischargeDate() {return theHospDischargeDate;}
	public void setHospDischargeDate(Date aHospDischargeDate) {theHospDischargeDate = aHospDischargeDate;}

	/** Дата выписки */
	private Date theHospDischargeDate;
	/** Время госпитализации */
	private Time theHospTime;
	
	/** Отказ от госпитализации */
	@Comment("Отказ от госпитализации")
	public Long getDeniedHospital() {return theDeniedHospital;}
	public void setDeniedHospital(Long aDeniedHospital) {theDeniedHospital = aDeniedHospital;}

	/** Отказ от госпитализации */
	private Long theDeniedHospital;
	
	/** Файл */
	@Comment("Файл")
	public String getFilename5() {return theFilename5;}
	public void setFilename5(String aFilename5) {theFilename5 = aFilename5;}

	/** Файл */
	private String theFilename5;
	
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getDateImport5() {return theDateImport5;}
	public void setDateImport5(Date aDateImport5) {theDateImport5 = aDateImport5;}

	/** Дата импорта */
	private Date theDateImport5;
	
	/** Файл */
	@Comment("Файл")
	public String getFilename4() {return theFilename4;}
	public void setFilename4(String aFilename4) {theFilename4 = aFilename4;}

	/** Файл */
	private String theFilename4;
	
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getDateImport4() {return theDateImport4;}
	public void setDateImport4(Date aDateImport4) {theDateImport4 = aDateImport4;}

	/** Дата импорта */
	private Date theDateImport4;
	
	/** Файл */
	@Comment("Файл")
	public String getFilename3() {return theFilename3;}
	public void setFilename3(String aFilename3) {theFilename3 = aFilename3;}

	/** Файл */
	private String theFilename3;
	
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getDateImport3() {return theDateImport3;}
	public void setDateImport3(Date aDateImport3) {theDateImport3 = aDateImport3;}

	/** Дата импорта */
	private Date theDateImport3;
	
	/** Файл */
	@Comment("Файл")
	public String getFilename2() {return theFilename2;}
	public void setFilename2(String aFilename2) {theFilename2 = aFilename2;}

	/** Файл */
	private String theFilename2;
	
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getDateImport2() {return theDateImport2;}
	public void setDateImport2(Date aDateImport2) {theDateImport2 = aDateImport2;}

	/** Дата импорта */
	private Date theDateImport2;
	

	/** Файл */
	@Comment("Файл")
	public String getFilename1() {return theFilename1;}
	public void setFilename1(String aFilename1) {theFilename1 = aFilename1;}

	/** Файл */
	private String theFilename1;
	
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getDateImport1() {return theDateImport1;}
	public void setDateImport1(Date aDateImport1) {theDateImport1 = aDateImport1;}

	/** Дата импорта */
	private Date theDateImport1;
	
	/** Тип помощи дн круг */
	@Comment("Тип помощи дн круг")
	public String getBedSubType() {
		return theBedSubType;
	}

	public void setBedSubType(String aBedSubType) {
		theBedSubType = aBedSubType;
	}

	/** Тип помощи дн круг */
	private String theBedSubType;
	
	/** Детский профиль */
	@Comment("Детский профиль")
	public String getForChild() {return theForChild;}
	public void setForChild(String aForChild) {theForChild = aForChild;}

	/** Детский профиль */
	private String theForChild;
	
	/** Без госпитализаций */
	@Comment("Без госпитализаций")
	public Boolean getWithoutHosp() {return theWithoutHosp;}
	public void setWithoutHosp(Boolean aWithoutHosp) {theWithoutHosp = aWithoutHosp;}

	/** Без госпитализаций */
	private Boolean theWithoutHosp;

    /** Время импорта */
    @Column(name="voc_time")
    public long getTime() { return theTime ; }
    public void setTime(long aTime) { theTime = aTime ; }


    /** Время импорта */
    private long theTime ;
    
    /** Фамилия имя отчетство */
	@Comment("Фамилия имя отчетство")
	public String getFio() {return theFio;}
	public void setFio(String aFio) {theFio = aFio;	}

	/** Фамилия имя отчетство */
	private String theFio;
}
