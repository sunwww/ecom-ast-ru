package ru.ecom.mis.ejb.domain.prescription;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptCancelReason;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptFulfilState;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Назначение
 * @author azviagin
 *
 */

@Comment("Назначение")
@Entity
@Table(schema="SQLUser")
public abstract class Prescription extends BaseEntity{

	/** Лист назначений */
	@Comment("Лист назначений")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	public AbstractPrescriptionList getPrescriptionList() {return thePrescriptionList;}
	public void setPrescriptionList(AbstractPrescriptionList aPrescriptionList) {thePrescriptionList = aPrescriptionList;}
	
	/** Выполнения */
	@Comment("Выполнения")
	@OneToMany(mappedBy="prescription", cascade=CascadeType.ALL)
	public List<PrescriptionFulfilment> getFulfilments() {return theFulfilments;}
	public void setFulfilments(List<PrescriptionFulfilment> aFulfilments) {theFulfilments = aFulfilments;}
	
	/** Дата и время регистрации */
	@Comment("Дата и время регистрации")
	public Timestamp getRegistrationTimeStamp() {return theRegistrationTimeStamp;}
	public void setRegistrationTimeStamp(Timestamp aRegistrationTimeStamp) {theRegistrationTimeStamp = aRegistrationTimeStamp;}

	/** Регистратор */
	@Comment("Регистратор")
	@OneToOne
	public Worker getRegistrator() {return theRegistrator;}
	public void setRegistrator(Worker aRegistrator) {theRegistrator = aRegistrator;}
	
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	public Date getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(Date aPlanStartDate) {thePlanStartDate = aPlanStartDate;}
	
	/** Плановое время начала */
	@Comment("Плановое время начала")
	public Time getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(Time aPlanStartTime) {thePlanStartTime = aPlanStartTime;}
	
	/** Плановая дата окончания */
	@Comment("Плановая дата окончания")
	public Date getPlanEndDate() {return thePlanEndDate;}
	public void setPlanEndDate(Date aPlanEndDate) {thePlanEndDate = aPlanEndDate;}
	
	/** Плановое время окончания */
	@Comment("Плановое время окончания")
	public Time getPlanEndTime() {return thePlanEndTime;}
	public void setPlanEndTime(Time aPlanEndTime) {thePlanEndTime = aPlanEndTime;}
	
	/** Дата отмены */
	@Comment("Дата отмены")
	public Date getCancelDate() {return theCancelDate;}
	public void setCancelDate(Date aCancelDate) {theCancelDate = aCancelDate;}
	
	/** Время отмены */
	@Comment("Время отмены")
	public Time getCancelTime() {return theCancelTime;}
	public void setCancelTime(Time aCancelTime) {theCancelTime = aCancelTime;}
	
	/** Причина отмены */
	@Comment("Причина отмены")
	@OneToOne
	public VocPrescriptCancelReason getCancelReason() {return theCancelReason;}
	public void setCancelReason(VocPrescriptCancelReason aCancelReason) {theCancelReason = aCancelReason;}
	
	///** Отменивший */
	//@Comment("Отменивший")
	//@OneToOne
	//public Worker getCancelWorker() {return theCancelWorker;}
	//public void setCancelWorker(Worker aCancelWorker) {theCancelWorker = aCancelWorker;}
	
	///** Назначивший */
	//@Comment("Назначивший")
	//@OneToOne
	//public Worker getPrescriptor() {return thePrescriptor;}
	//public void setPrescriptor(Worker aPrescriptor) {thePrescriptor = aPrescriptor;}
	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}
	
	/** Описание */
	@Comment("Описание")
	@Transient
	public String getDescription() {return "";}
	public void setDescription(String aDescription) {}
	
	/** Состояние исполнения */
	@Comment("Состояние исполнения")
	@OneToOne
	public VocPrescriptFulfilState getFulfilmentState() {return theFulfilmentState;}
	public void setFulfilmentState(VocPrescriptFulfilState aFulfilmentState) {theFulfilmentState = aFulfilmentState;}
	
	/** Отменивший(text) */
	@Comment("Отменивший (text)")
	@Transient
	public String getCancelWorkerInfo() {return theCancelSpecial!=null ? theCancelSpecial.getWorkFunctionInfo(): "";}

   /** Назначивший (text)*/
   @Comment("Назначивший (text)")
   @Transient
   public String getPrescriptorInfo() { return thePrescriptSpecial!=null ? thePrescriptSpecial.getWorkFunctionInfo() : ""; }
 
   /** Регистратор (text) */
	@Comment("Регистратор (text)")
	@Transient
	public String getRegistratorInfo() {return theRegistrator!=null? theRegistrator.getWorkerInfo() : "";}
	public void setRegistratorInfo(String aRegistratorInfo) {}
	
	/** Описание назначений */
	@Comment("Описание назначений")
	@Transient
	public String getDescriptionInfo() {
		StringBuilder sb=new StringBuilder();
		sb.append(getDescriptionInfo());
		sb.append(",");
		return sb.toString();
	}
	
	/** Дата и время назначения */
	@Comment("Дата и время назначения")
	@Transient
	public String getPrescriptTimeStamp() {
		StringBuilder sb=new StringBuilder();
		sb.append(getPlanStartDate());
		sb.append(" ");
		sb.append(getPlanStartTime());
		return sb.toString();
	}
	
	/** Дата и время отмены назначения */
	@Comment("Дата и время отмены назначения")
	@Transient
	public String getPrescriptCancelTimeStamp() {
		StringBuilder sb=new StringBuilder();
		sb.append(getCancelDate());
		sb.append(" ");
		sb.append(getCancelTime());
		return sb.toString();
	}
	
	/** Роспись */
	@Comment("Роспись")
	public String getSignature() {return theSignature;}
	public void setSignature(String aSignature) {theSignature = aSignature;}
	
	/** Назначил специалист */
	@Comment("Назначил специалист")
	@OneToOne
	public WorkFunction getPrescriptSpecial() {return thePrescriptSpecial;}
	public void setPrescriptSpecial(WorkFunction aPrescriptSpecial) {thePrescriptSpecial = aPrescriptSpecial;}

	/** Отменил специалист */
	@Comment("Отменил специалист")
	@OneToOne
	public WorkFunction getCancelSpecial() {return theCancelSpecial;}
	public void setCancelSpecial(WorkFunction aCancelSpecial) {theCancelSpecial = aCancelSpecial;}

	/** Отменил специалист */
	private WorkFunction theCancelSpecial;
	/** Назначил специалист */
	private WorkFunction thePrescriptSpecial;
	/** Роспись */
	private String theSignature;	
	/** Лист назначений */
	private AbstractPrescriptionList thePrescriptionList;
	/** Выполнения */
	private List<PrescriptionFulfilment> theFulfilments;
	/** Дата и время регистрации */
	private Timestamp theRegistrationTimeStamp;
	/** Регистратор */
	private Worker theRegistrator;
	/** Плановая дата начала */
	private Date thePlanStartDate;
	/** Плановое время начала */
	private Time thePlanStartTime;
	/** Плановая дата окончания */
	private Date thePlanEndDate;
	/** Плановое время окончания */
	private Time thePlanEndTime;
	/** Дата отмены */
	private Date theCancelDate;
	/** Время отмены */
	private Time theCancelTime;
	/** Причина отмены */
	private VocPrescriptCancelReason theCancelReason;
	///** Отменивший */
	//private Worker theCancelWorker;
	///** Назначивший */
	//private Worker thePrescriptor;
	/** Комментарии */
	private String theComments;
	/** Состояние исполнения */
	private VocPrescriptFulfilState theFulfilmentState;
	

//	/**
//	 * Стационарный случай?
//	 * @return
//	 */
//	@Transient
//	public boolean isInHospitalMedCase() {
//        MedCase medCase = getPrescriptionList() != null
//                && getPrescriptionList().getMedCase() != null
//                ? getPrescriptionList().getMedCase(): null;
//        return medCase instanceof HospitalMedCase;
//    }
//	{
//System.out.println(getPrescriptionList()) ;}
}
