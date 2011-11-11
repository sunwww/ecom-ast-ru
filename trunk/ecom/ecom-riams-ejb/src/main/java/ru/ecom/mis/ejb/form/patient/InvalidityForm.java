package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.Invalidity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Инвалидность
 */
@EntityForm
@EntityFormPersistance(clazz = Invalidity.class)
@Comment("Инвалидность")
@WebTrail(comment = "Инвалидность", nameProperties = "id", view = "entityParentView-mis_invalidity.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/Invalidity")
public class InvalidityForm extends IdEntityForm{
	 /**
	  * Инвалид ВОВ
	  */
	 @Comment("Инвалид ВОВ")
	 @Persist
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
	 @Persist
	 public Long getHealthViolation() {
	  return theHealthViolation;
	 }
	 public void setHealthViolation(Long aHealthViolation) {
	  theHealthViolation = aHealthViolation;
	 }
	 /**
	  * Главное нарушение состояния здоровья
	  */
	 private Long theHealthViolation;
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 @Comment("Ведущее ограничение жизнедеятельности")
	 @Persist
	 public Long getVitalRestriction() {
	  return theVitalRestriction;
	 }
	 public void setVitalRestriction(Long aVitalRestriction) {
	  theVitalRestriction = aVitalRestriction;
	 }
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 private Long theVitalRestriction;
	 /**
	  * Место работы
	  */
	 @Comment("Место работы")
	 @Persist
	 public Long getWorkPlace() {
	  return theWorkPlace;
	 }
	 public void setWorkPlace(Long aWorkPlace) {
	  theWorkPlace = aWorkPlace;
	 }
	 /**
	  * Место работы
	  */
	 private Long theWorkPlace;
	 /**
	  * Дата начала
	  */
	 @Comment("Дата начала")
	 @Persist @DateString @DoDateString @Required
	 public String getDateFrom() {
	  return theDateFrom;
	 }
	 public void setDateFrom(String aDateFrom) {
	  theDateFrom = aDateFrom;
	 }
	 /**
	  * Дата начала
	  */
	 private String theDateFrom;
	 /**
	  * Дата окончания
	  */
	 @Comment("Дата окончания")
	 @Persist @DateString @DoDateString
	 public String getDateTo() {
	  return theDateTo;
	 }
	 public void setDateTo(String aDateTo) {
	  theDateTo = aDateTo;
	 }
	 /**
	  * Дата окончания
	  */
	 private String theDateTo;
	 /**
	  * Группа
	  */
	 @Comment("Группа")
	 @Persist @Required
	 public Long getGroup() {
	  return theGroup;
	 }
	 public void setGroup(Long aGroup) {
	  theGroup = aGroup;
	 }
	 /**
	  * Группа
	  */
	 private Long theGroup;
	 /**
	  * Пациент
	  */
	 @Comment("Пациент")
	 @Persist @Required
	 public Long getPatient() {
	  return thePatient;
	 }
	 public void setPatient(Long aPatient) {
	  thePatient = aPatient;
	 }
	 /**
	  * Пациент
	  */
	 private Long thePatient;
	 /**
	  * Мкб10
	  */
	 @Comment("Мкб10")
	 @Persist 
	 public Long getIdc10() {
	  return theIdc10;
	 }
	 public void setIdc10(Long aIdc10) {
	  theIdc10 = aIdc10;
	 }
	 /**
	  * Мкб10
	  */
	 private Long theIdc10;
	 /**
	  * Диагноз
	  */
	 @Comment("Диагноз")
	 @Persist
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
	 @Persist @DateString @DoDateString
	 public String getLastRevisionDate() {
	  return theLastRevisionDate;
	 }
	 public void setLastRevisionDate(String aLastRevisionDate) {
	  theLastRevisionDate = aLastRevisionDate;
	 }
	 /**
	  * Дата последнего пересмотра
	  */
	 private String theLastRevisionDate;
	 /**
	  * Дата следующего пересмотра
	  */
	 @Comment("Дата следующего пересмотра")
	 @Persist @DateString @DoDateString
	 public String getNextRevisionDate() {
	  return theNextRevisionDate;
	 }
	 public void setNextRevisionDate(String aNextRevisionDate) {
	  theNextRevisionDate = aNextRevisionDate;
	 }
	 /**
	  * Дата следующего пересмотра
	  */
	 private String theNextRevisionDate;
	 /**
	  * Инвалид с детства
	  */
	 @Comment("Инвалид с детства")
	 @Persist
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
	@Persist @DateString @DoDateString
	public String getFirstDiscloseDate() {
		return theFirstDiscloseDate;
	}

	public void setFirstDiscloseDate(String aNAME) {
		theFirstDiscloseDate = aNAME;
	}

	/** Дата постановки (впервые) */
	private String theFirstDiscloseDate;
	
	/** Дата освидетельствования */
	@Comment("Дата освидетельствования")
	@Persist @DateString @DoDateString
	public String getRevisionDate() {
		return theRevisionDate;
	}

	public void setRevisionDate(String aRevisionDate) {
		theRevisionDate = aRevisionDate;
	}

	/** Дата освидетельствования */
	private String theRevisionDate;
	
	/** Категория работника */
	@Comment("Категория работника")
	@Persist
	public Long getCategoryWorker() {
		return theCategoryWorker;
	}

	public void setCategoryWorker(Long aCategoryWorker) {
		theCategoryWorker = aCategoryWorker;
	}

	/** Категория работника */
	private Long theCategoryWorker;
	
	/** Место регистрации */
	@Comment("Место регистрации")
	@Persist @Required
	public Long getRegistrationPlace() {
		return theRegistrationPlace;
	}

	public void setRegistrationPlace(Long aRegistrationPlace) {
		theRegistrationPlace = aRegistrationPlace;
	}

	/** Место регистрации */
	private Long theRegistrationPlace;
	
	/** Профиль заболевания */
	@Comment("Профиль заболевания")
	@Persist 
	public Long getProfileIllness() {
		return theProfileIllness;
	}

	public void setProfileIllness(Long aProfileIllness) {
		theProfileIllness = aProfileIllness;
	}

	/** Профиль заболевания */
	private Long theProfileIllness;
	
	/** Работа в условиях профвредности */
	@Comment("Работа в условиях профвредности")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
	public Boolean getPrimary() {return thePrimary;}
	public void setPrimary(Boolean aPrimary) {thePrimary = aPrimary;}
	
	/** Без переосвидетельствования */
	@Comment("Без переосвидетельствования")
	@Persist
	public Boolean getWithoutExam() {return theWithoutExam;}
	public void setWithoutExam(Boolean aWithoutExam) {theWithoutExam = aWithoutExam;}

	/** Без переосвидетельствования */
	private Boolean theWithoutExam;
	
	/** Недееспособный */
	@Comment("Недееспособный")
	@Persist
	public Boolean getIncapable() {return theIncapable;}
	public void setIncapable(Boolean aIncapable) {theIncapable = aIncapable;}

	/** Первичность */
	private Boolean thePrimary;
	/** Недееспособный */
	private Boolean theIncapable;
}
