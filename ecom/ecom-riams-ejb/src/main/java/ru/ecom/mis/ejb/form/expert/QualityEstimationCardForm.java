package ru.ecom.mis.ejb.form.expert;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.QualityEstimationCard;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = QualityEstimationCard.class)
@Comment("Экспертная карта")
@WebTrail(comment = "Экспертная карта", nameProperties = "id", view = "entityView-expert_card.do")
@Parent(property = "medcase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/QualityEstimationCard")
public class QualityEstimationCardForm extends IdEntityForm{
	/**
	  * Номер карты
	  */
	 @Comment("Номер карты")
	 @Persist
	 public String getCardNumber() {
	  return theCardNumber;
	 }
	 public void setCardNumber(String aCardNumber) {
	  theCardNumber = aCardNumber;
	 }
	 /**
	  * Номер карты
	  */
	 private String theCardNumber;
	 /**
	  * Случай медицинского обслуживания
	  */
	 @Comment("Случай медицинского обслуживания")
	 @Persist @Required
	 public Long getMedcase() {
	  return theMedcase;
	 }
	 public void setMedcase(Long aMedcase) {
	  theMedcase = aMedcase;
	 }
	 /**
	  * Случай медицинского обслуживания
	  */
	 private Long theMedcase;
	 /**
	  * Вид оценки качества
	  */
	 @Comment("Вид оценки качества")
	 @Persist @Required
	 public Long getKind() {
	  return theKind;
	 }
	 public void setKind(Long aKind) {
	  theKind = aKind;
	 }
	 /**
	  * Вид оценки качества
	  */
	 private Long theKind;
	 
	 /** Пациент */
	@Comment("Пациент")
	@Persist 
	public Long getPatient() {
		return thePatient;
	}

	public void setPatient(Long aPatient) {
		thePatient = aPatient;
	}

	/** Пациент */
	private Long thePatient;
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(String aNAME) {
		theCreateDate = aNAME;
	}
	/** Пользователь, создавший экспертную карту */
	@Comment("Пользователь, создавший экспертную карту")
	@Persist
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}

	
	/** Лечащий врач */
	@Comment("Лечащий врач")
	@Persist 
	public Long getDoctorCase() {
		return theDoctorCase;
	}

	public void setDoctorCase(Long aLechDoctor) {
		theDoctorCase = aLechDoctor;
	}
	
	/** Отделение */
	@Comment("Отделение")
	@Persist 
	public Long getDepartment() {
		return theDepartment;
	}

	public void setDepartment(Long aDepartment) {
		theDepartment = aDepartment;
	}
	
	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public Long getIdc10() {
		return theIdc10;
	}

	public void setIdc10(Long aIdc10) {
		theIdc10 = aIdc10;
	}
	
	/** Текст диагноза */
	@Comment("Текст диагноза")
	@Persist
	public String getDiagnosis() {
		return theDiagnosis;
	}

	public void setDiagnosis(String aDiagnosis) {
		theDiagnosis = aDiagnosis;
	}
	
	/** СЛО */
	@Comment("СЛО")
	public Long getSlo() {
		return theSlo;
	}

	public void setSlo(Long aSlo) {
		theSlo = aSlo;
	}

	/** СЛО */
	private Long theSlo;

	/** Текст диагноза */
	private String theDiagnosis;

	/** Диагноз */
	private Long theIdc10;

	/** Отделение */
	private Long theDepartment;

	/** Лечащий врач */
	private Long theDoctorCase;
	/** Пользователь, создавший экспертную карту */
	private String theCreateUsername;

	/** Дата создания */
	private String theCreateDate;
}
