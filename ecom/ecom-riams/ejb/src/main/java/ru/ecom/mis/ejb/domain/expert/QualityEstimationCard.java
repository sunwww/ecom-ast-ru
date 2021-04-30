package ru.ecom.mis.ejb.domain.expert;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
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
 @Getter
 @Setter
public class QualityEstimationCard extends BaseEntity{
 /**
  * Номер карты
  */
 private String cardNumber;
 /**
  * Случай медицинского обслуживания
  */
 @Comment("Случай медицинского обслуживания")
 @OneToOne
 public MedCase getMedcase() {
  return medcase;
 }
 /**
  * Случай медицинского обслуживания
  */
 private MedCase medcase;
 /**
  * Оценки качества
  */
 @Comment("Оценки качества")
 @OneToMany(mappedBy="card", cascade=CascadeType.ALL)
 public List<QualityEstimation> getEstimations() {
  return estimations;
 }
 /**
  * Оценки качества
  */
 private List<QualityEstimation> estimations;
 /**
  * Вид оценки качества
  */
 @Comment("Вид оценки качества")
 @OneToOne
 public VocQualityEstimationKind getKind() {
  return kind;
 }
 /**
  * Вид оценки качества
  */
 private VocQualityEstimationKind kind;
 
 /** Пациент */
@Comment("Пациент")
@OneToOne
public Patient getPatient() {
	return patient;
}

/** Пациент */
private Patient patient;
	
	/** Лечащий врач */
	@Comment("Лечащий врач")
	@OneToOne
	public WorkFunction getDoctorCase() {
		return doctorCase;
	}

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {
		return department;
	}

	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {
		return idc10;
	}

	@Transient
	public String getDepartmentInfo() {
		return department!=null? department.getName():"" ;
	}
	@Transient
	public String getPatientInfo() {
		return patient!=null?patient.getFio():"" ;
	}
	@Transient
	public String getDoctorCaseInfo() {
		return doctorCase!=null? doctorCase.getWorkFunctionInfo():"" ;
	}
	@Transient
	public String getIdc10Info() {
		return idc10!=null? idc10.getCode():"" ;
	}
	@Transient
	public String getKindInfo() {
		return kind!=null? kind.getName():"" ;
	}
	@Transient
	public String getSmoInfo() {
		return medcase!=null?medcase.getInfo():"" ;
	}
	
	/** Оценка */
	private BigDecimal markTransient;

	/** Текст диагноза */
	private String diagnosis;

	/** Диагноз */
	private VocIdc10 idc10;

	/** Отделение */
	private MisLpu department;

	/** Лечащий врач */
	private WorkFunction doctorCase;

	/** Пользователь, создавший экспертную карту */
	private String createUsername;

	/** Дата создания */
	private Date createDate;
}
