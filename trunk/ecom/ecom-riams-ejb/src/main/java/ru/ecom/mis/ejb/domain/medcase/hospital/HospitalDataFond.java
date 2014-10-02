package ru.ecom.mis.ejb.domain.medcase.hospital;

import java.sql.Date;

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
	public String getBirthday() {return theBirthday;}
	public void setBirthday(String aBirthday) {theBirthday = aBirthday;}

	/** Профиль */
	@Comment("Профиль")
	public String getProfile() {return theProfile;}
	public void setProfile(String aProfile) {theProfile = aProfile;}

	/** Дата предварительной госпитализации */
	@Comment("Дата предварительной госпитализации")
	public String getPreHospDate() {return thePreHospDate;}
	public void setPreHospDate(String aPreHospDate) {thePreHospDate = aPreHospDate;}

	/** Дата госпитализации */
	@Comment("Дата госпитализации")
	public String getHospDate() {return theHospDate;}
	public void setHospDate(String aHospDate) {theHospDate = aHospDate;}

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
}
