package ru.ecom.mis.ejb.domain.expert;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationKind;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Карта оценки качества медицинской помощи
  */
 @Comment("Карта оценки качества медицинской помощи")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	 @AIndex(properties="medcase")
	 ,@AIndex(properties="kind")
	 ,@AIndex(properties={"kind","medcase"})
 })
public class QualityEstimationCard extends BaseEntity{
 /**
  * Номер карты
  */
 @Comment("Номер карты")
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
 @OneToOne
 public MedCase getMedcase() {
  return theMedcase;
 }
 public void setMedcase(MedCase aMedcase) {
  theMedcase = aMedcase;
 }
 /**
  * Случай медицинского обслуживания
  */
 private MedCase theMedcase;
 /**
  * Оценки качества
  */
 @Comment("Оценки качества")
 @OneToMany(mappedBy="card", cascade=CascadeType.ALL)
 public List<QualityEstimation> getEstimations() {
  return theEstimations;
 }
 public void setEstimations(List<QualityEstimation> aEstimations) {
  theEstimations = aEstimations;
 }
 /**
  * Оценки качества
  */
 private List<QualityEstimation> theEstimations;
 /**
  * Вид оценки качества
  */
 @Comment("Вид оценки качества")
 @OneToOne
 public VocQualityEstimationKind getKind() {
  return theKind;
 }
 public void setKind(VocQualityEstimationKind aKind) {
  theKind = aKind;
 }
 /**
  * Вид оценки качества
  */
 private VocQualityEstimationKind theKind;
 
 /** Пациент */
@Comment("Пациент")
@OneToOne
public Patient getPatient() {
	return thePatient;
}

public void setPatient(Patient aPatient) {
	thePatient = aPatient;
}

/** Пациент */
private Patient thePatient;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(Date aNAME) {
		theCreateDate = aNAME;
	}
	/** Пользователь, создавший экспертную карту */
	@Comment("Пользователь, создавший экспертную карту")
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}
	
	/** Лечащий врач */
	@Comment("Лечащий врач")
	@OneToOne
	public WorkFunction getDoctorCase() {
		return theDoctorCase;
	}

	public void setDoctorCase(WorkFunction aLechDoctor) {
		theDoctorCase = aLechDoctor;
	}
	
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {
		return theDepartment;
	}

	public void setDepartment(MisLpu aDepartment) {
		theDepartment = aDepartment;
	}
	
	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {
		return theIdc10;
	}

	public void setIdc10(VocIdc10 aIdc10) {
		theIdc10 = aIdc10;
	}
	
	/** Текст диагноза */
	@Comment("Текст диагноза")
	public String getDiagnosis() {
		return theDiagnosis;
	}

	public void setDiagnosis(String aDiagnosis) {
		theDiagnosis = aDiagnosis;
	}
	@Transient
	public String getDepartmentInfo() {
		return theDepartment!=null? theDepartment.getName():"" ;
	}
	@Transient
	public String getPatientInfo() {
		return thePatient!=null?thePatient.getFio():"" ;
	}
	@Transient
	public String getDoctorCaseInfo() {
		return theDoctorCase!=null? theDoctorCase.getWorkFunctionInfo():"" ;
	}
	@Transient
	public String getIdc10Info() {
		return theIdc10!=null? theIdc10.getCode():"" ;
	}
	@Transient
	public String getKindInfo() {
		return theKind!=null? theKind.getName():"" ;
	}
	@Transient
	public String getSmoInfo() {
		return theMedcase!=null?theMedcase.getInfo():"" ;
	}
	


	/** Текст диагноза */
	private String theDiagnosis;

	/** Диагноз */
	private VocIdc10 theIdc10;

	/** Отделение */
	private MisLpu theDepartment;

	/** Лечащий врач */
	private WorkFunction theDoctorCase;

	/** Пользователь, создавший экспертную карту */
	private String theCreateUsername;

	/** Дата создания */
	private Date theCreateDate;
}
