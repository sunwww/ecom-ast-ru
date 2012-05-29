package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.disability.voc.VocInvalidity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocCategoryWorker;
import ru.ecom.mis.ejb.domain.patient.voc.VocInvalidityHealthViolation;
import ru.ecom.mis.ejb.domain.patient.voc.VocInvalidityVitalRestriction;
import ru.ecom.mis.ejb.domain.patient.voc.VocInvalidWorkPlace;
import ru.ecom.mis.ejb.domain.patient.voc.VocProfileIllness;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocLawCourt;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Инвалидность
 */

@Entity
@AIndexes({
	@AIndex(properties={"patient"})
}
)
@Table(schema="SQLUser")
public class Invalidity extends BaseEntity{
	 /**
	  * Инвалид ВОВ
	  */
	 @Comment("Инвалид ВОВ")
	 public Boolean getGreatePatrioticWarInvalid() {
	  return theGreatePatrioticWarInvalid;
	 }
	 public void setGreatePatrioticWarInvalid(Boolean aGreatePatrioticWarInvalid) {
	  theGreatePatrioticWarInvalid = aGreatePatrioticWarInvalid;
	 }
	 /**
	  * Инвалид ВОВ
	  */
	 private Boolean theGreatePatrioticWarInvalid;
	 /**
	  * Главное нарушение состояния здоровья
	  */
	 @Comment("Главное нарушение состояния здоровья")
	 @OneToOne
	 public VocInvalidityHealthViolation getHealthViolation() {
	  return theHealthViolation;
	 }
	 public void setHealthViolation(VocInvalidityHealthViolation aHealthViolation) {
	  theHealthViolation = aHealthViolation;
	 }
	 /**
	  * Главное нарушение состояния здоровья
	  */
	 private VocInvalidityHealthViolation theHealthViolation;
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 @Comment("Ведущее ограничение жизнедеятельности")
	 @OneToOne
	 public VocInvalidityVitalRestriction getVitalRestriction() {
	  return theVitalRestriction;
	 }
	 public void setVitalRestriction(VocInvalidityVitalRestriction aVitalRestriction) {
	  theVitalRestriction = aVitalRestriction;
	 }
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 private VocInvalidityVitalRestriction theVitalRestriction;
	 /**
	  * Место работы
	  */
	 @Comment("Место работы")
	 @OneToOne
	 public VocInvalidWorkPlace getWorkPlace() {
	  return theWorkPlace;
	 }
	 public void setWorkPlace(VocInvalidWorkPlace aWorkPlace) {
	  theWorkPlace = aWorkPlace;
	 }
	 /**
	  * Место работы
	  */
	 private VocInvalidWorkPlace theWorkPlace;
	 /**
	  * Дата начала
	  */
	 @Comment("Дата начала")
	 public Date getDateFrom() {
	  return theDateFrom;
	 }
	 public void setDateFrom(Date aDateFrom) {
	  theDateFrom = aDateFrom;
	 }
	 /**
	  * Дата начала
	  */
	 private Date theDateFrom;
	 /**
	  * Дата окончания
	  */
	 @Comment("Дата окончания")
	 public Date getDateTo() {
	  return theDateTo;
	 }
	 public void setDateTo(Date aDateTo) {
	  theDateTo = aDateTo;
	 }
	 /**
	  * Дата окончания
	  */
	 private Date theDateTo;
	 /**
	  * Группа
	  */
	 @Comment("Группа")
	 @OneToOne
	 public VocInvalidity getGroup() {
	  return theGroup;
	 }
	 public void setGroup(VocInvalidity aGroup) {
	  theGroup = aGroup;
	 }
	 /**
	  * Группа
	  */
	 private VocInvalidity theGroup;
	 /**
	  * Пациент
	  */
	 @Comment("Пациент")
	 @OneToOne
	 public Patient getPatient() {
	  return thePatient;
	 }
	 public void setPatient(Patient aPatient) {
	  thePatient = aPatient;
	 }
	 /**
	  * Пациент
	  */
	 private Patient thePatient;
	 /**
	  * Мкб10
	  */
	 @Comment("Мкб10")
	 @OneToOne
	 public VocIdc10 getIdc10() {
	  return theIdc10;
	 }
	 public void setIdc10(VocIdc10 aIdc10) {
	  theIdc10 = aIdc10;
	 }
	 /**
	  * Мкб10
	  */
	 private VocIdc10 theIdc10;
	 /**
	  * Диагноз
	  */
	 @Comment("Диагноз")
	 public String getDiagnosis() {
	  return theDiagnosis;
	 }
	 public void setDiagnosis(String aDiagnosis) {
	  theDiagnosis = aDiagnosis;
	 }
	 /**
	  * Диагноз
	  */
	 private String theDiagnosis;
	 /**
	  * Дата последнего пересмотра
	  */
	 @Comment("Дата последнего пересмотра")
	 public Date getLastRevisionDate() {
	  return theLastRevisionDate;
	 }
	 public void setLastRevisionDate(Date aLastRevisionDate) {
	  theLastRevisionDate = aLastRevisionDate;
	 }
	 /**
	  * Дата последнего пересмотра
	  */
	 private Date theLastRevisionDate;
	 /**
	  * Дата следующего пересмотра
	  */
	 @Comment("Дата следующего пересмотра")
	 public Date getNextRevisionDate() {
	  return theNextRevisionDate;
	 }
	 public void setNextRevisionDate(Date aNextRevisionDate) {
	  theNextRevisionDate = aNextRevisionDate;
	 }
	 /**
	  * Дата следующего пересмотра
	  */
	 private Date theNextRevisionDate;
	 /**
	  * Инвалид с детства
	  */
	 @Comment("Инвалид с детства")
	 public Boolean getChildhoodInvalid() {
	  return theChildhoodInvalid;
	 }
	 public void setChildhoodInvalid(Boolean aChildhoodInvalid) {
	  theChildhoodInvalid = aChildhoodInvalid;
	 }
	 /**
	  * Инвалид с детства
	  */
	 private Boolean theChildhoodInvalid;
	 
	 /** Дата постановки (впервые) */
	@Comment("Дата постановки (впервые)")
	public Date getFirstDiscloseDate() {
		return theFirstDiscloseDate;
	}

	public void setFirstDiscloseDate(Date aNAME) {
		theFirstDiscloseDate = aNAME;
	}

	/** Дата постановки (впервые) */
	private Date theFirstDiscloseDate;
	
	/** Дата освидетельствования */
	@Comment("Дата освидетельствования")
	public Date getRevisionDate() {
		return theRevisionDate;
	}

	public void setRevisionDate(Date aRevisionDate) {
		theRevisionDate = aRevisionDate;
	}

	/** Дата освидетельствования */
	private Date theRevisionDate;
	
	/** Категория работника */
	@Comment("Категория работника")
	@OneToOne
	public VocCategoryWorker getCategoryWorker() {
		return theCategoryWorker;
	}

	public void setCategoryWorker(VocCategoryWorker aCategoryWorker) {
		theCategoryWorker = aCategoryWorker;
	}

	/** Категория работника */
	private VocCategoryWorker theCategoryWorker;
	
	/** Место регистрации */
	@Comment("Место регистрации")
	@OneToOne
	public MisLpu getRegistrationPlace() {
		return theRegistrationPlace;
	}

	public void setRegistrationPlace(MisLpu aRegistrationPlace) {
		theRegistrationPlace = aRegistrationPlace;
	}

	/** Место регистрации */
	private MisLpu theRegistrationPlace;
	
	/** Профиль заболевания */
	@Comment("Профиль заболевания")
	@OneToOne
	public VocProfileIllness getProfileIllness() {
		return theProfileIllness;
	}

	public void setProfileIllness(VocProfileIllness aProfileIllness) {
		theProfileIllness = aProfileIllness;
	}

	/** Профиль заболевания */
	private VocProfileIllness theProfileIllness;
	
	/** Работа в условиях профвредности */
	@Comment("Работа в условиях профвредности")
	public Boolean getWorkProfDisutility() {
		return theWorkProfDisutility;
	}

	public void setWorkProfDisutility(Boolean aWorkProf) {
		theWorkProfDisutility = aWorkProf;
	}

	/** Работа в условиях профвредности */
	private Boolean theWorkProfDisutility;
	
	/** Стаж работы в отрасли */
	@Comment("Стаж работы в отрасли")
	public Integer getSeniority() {
		return theSeniority;
	}

	public void setSeniority(Integer aSeniority) {
		theSeniority = aSeniority;
	}

	/** Стаж работы в отрасли */
	private Integer theSeniority;
	
	/** Стаж работы в нефтегазовой промышленности */
	@Comment("Стаж работы в нефтегазовой промышленности")
	public Integer getSeniorityNGP() {
		return theSeniorityNGP;
	}

	public void setSeniorityNGP(Integer aSeniorityNGP) {
		theSeniorityNGP = aSeniorityNGP;
	}

	/** Стаж работы в нефтегазовой промышленности */
	private Integer theSeniorityNGP;
	
	/** Трудоспособен */
	@Comment("Трудоспособен")
	public Boolean getIsWorking() {
		return theIsWorking;
	}

	public void setIsWorking(Boolean aIsWorking) {
		theIsWorking = aIsWorking;
	}

	/** Трудоспособен */
	private Boolean theIsWorking;
	
	/** Первичность */
	@Comment("Первичность")
	public Boolean getInitial() {
		return theInitial;
	}

	public void setInitial(Boolean aPrimary) {
		theInitial = aPrimary;
	}

	/** Первичность */
	private Boolean theInitial;
	
	/** Без переосвидетельствования */
	@Comment("Без переосвидетельствования")
	public Boolean getWithoutExam() {return theWithoutExam;}
	public void setWithoutExam(Boolean aWithoutExam) {theWithoutExam = aWithoutExam;}

	/** Без переосвидетельствования */
	private Boolean theWithoutExam;

	/** Недееспособный */
	@Comment("Недееспособный")
	public Boolean getIncapable() {return theIncapable;}
	public void setIncapable(Boolean aIncapable) {theIncapable = aIncapable;}

	/** Недееспособный */
	private Boolean theIncapable;
	
	/** Суд */
	@Comment("Суд")
	@OneToOne
	public VocLawCourt getLawCourt() {return theLawCourt;}
	public void setLawCourt(VocLawCourt aLawCourt) {theLawCourt = aLawCourt;}

	/** Дата суда */
	@Comment("Дата суда")
	public Date getLawCourtDate() {return theLawCourtDate;}
	public void setLawCourtDate(Date aLawCourtDate) {theLawCourtDate = aLawCourtDate;}

	/** Дата суда */
	private Date theLawCourtDate;
	/** Суд */
	private VocLawCourt theLawCourt;

}
