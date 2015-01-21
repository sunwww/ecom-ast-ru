package ru.ecom.mis.ejb.domain.medcase.hospital;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeniedHospitalizatingFond;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Госпитализации данные фонда")
@Entity
@Table(schema="SQLUser")
public class HospitalDataFond extends BaseEntity {

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

	/** Отказ от госпитализации */
	@Comment("Отказ от госпитализации")
	@OneToOne
	public VocDeniedHospitalizatingFond getDeniedHosp() {return theDeniedHosp;}
	public void setDeniedHosp(VocDeniedHospitalizatingFond aDeniedHosp) {theDeniedHosp = aDeniedHosp;}

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
	/** Отказ от госпитализации */
	private VocDeniedHospitalizatingFond theDeniedHosp;
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
}
