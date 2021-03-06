package ru.ecom.mis.ejb.domain.prescription;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptCancelReason;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptFulfilState;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * Назначение
 * @author azviagin
 *
 */

@Comment("Назначение")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "intakeDate" })
					,@AIndex(properties = { "calendarTime" })
					,@AIndex(properties = { "prescriptionList" })
})
@EntityListeners(DeleteListener.class)
public abstract class Prescription extends BaseEntity{

	@PrePersist
	void onPrePersist() {
		Long currentTime = System.currentTimeMillis();
		theCreateDate=new java.sql.Date(currentTime);
		theCreateTime=new java.sql.Time(currentTime);
	}

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
		//sb.append(getDescriptionInfo());
		sb.append("Описание назначения");
		return sb.toString();
	}
	
	/** Дата и время назначения */
	@Comment("Дата и время назначения")
	@Transient
	public String getPrescriptTimeStamp() {
		StringBuilder sb=new StringBuilder();
		if (getPlanStartDate()!=null) {
			sb.append(getPlanStartDate());
			sb.append(" ");
		}
		if (getPlanStartTime()!=null) {
			sb.append(getPlanStartTime());
		}
		return sb.toString();
	}
	
	/** Дата и время отмены назначения */
	@Comment("Дата и время отмены назначения")
	@Transient
	public String getPrescriptCancelTimeStamp() {
		StringBuilder sb=new StringBuilder();
		if (getCancelDate()!=null) {
			sb.append(getCancelDate());
		}
		if (getCancelTime()!=null) {
			sb.append(" ");
			sb.append(getCancelTime());
		}
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
	/** Кабинет назначения */
	@Comment("Кабинет назначения")
	@OneToOne
	public WorkFunction getPrescriptCabinet() {
		return thePrescriptCabinet;
	}

	public void setPrescriptCabinet(WorkFunction aPrescriptCabinet) {
		thePrescriptCabinet = aPrescriptCabinet;
	}

	/** Кабинет назначения */
	private WorkFunction thePrescriptCabinet;
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
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Тип назначения */
	@Comment("Тип назначения")
	@OneToOne
	public VocPrescriptType getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(VocPrescriptType aPrescriptType) {thePrescriptType = aPrescriptType;}
	/** Тип назначения */
	private VocPrescriptType thePrescriptType;

	/** Дата забора */
	@Comment("Дата забора")
	public Date getIntakeDate() {return theIntakeDate;}
	public void setIntakeDate(Date aIntakeDate) {theIntakeDate = aIntakeDate;}

	/** Время забора */
	@Comment("Время забора")
	public Time getIntakeTime() {return theIntakeTime;}
	public void setIntakeTime(Time aIntakeTime) {theIntakeTime = aIntakeTime;}

	/** Пользователь, осуществившей забор */
	@Comment("Пользователь, осуществившей забор")
	public String getIntakeUsername() {return theIntakeUsername;}
	public void setIntakeUsername(String aIntakeUsername) {theIntakeUsername = aIntakeUsername;}

	/** Идентификатор материала */
	@Comment("Идентификатор материала")
	public String getMaterialId() {return theMaterialId;}
	public void setMaterialId(String aMaterialId) {theMaterialId = aMaterialId;}

	/** Идентификатор материала */
	private String theMaterialId;
	/** Пользователь, осуществившей забор */
	private String theIntakeUsername;
	/** Время забора */
	private Time theIntakeTime;
	/** Дата забора */
	private Date theIntakeDate;
	
	/** Причина отмены текст */
	@Comment("Причина отмены текст")
	public String getCancelReasonText() {return theCancelReasonText;}
	public void setCancelReasonText(String aCancelReasonText) {theCancelReasonText = aCancelReasonText;}

	/** Пользователь отменивший */
	@Comment("Пользователь отменивший")
	public String getCancelUsername() {return theCancelUsername;}
	public void setCancelUsername(String aCancelUsername) {theCancelUsername = aCancelUsername;}

	/** Пользователь отменивший */
	private String theCancelUsername;
	/** Причина отмены текст */
	private String theCancelReasonText;

	/** Дата передачи в лабораторию */
	@Comment("Дата передачи в лабораторию")
	public Date getTransferDate() {return theTransferDate;}
	public void setTransferDate(Date aTransferDate) {theTransferDate = aTransferDate;}

	/** Время передачи */
	@Comment("Время передачи")
	public Time getTransferTime() {return theTransferTime;}
	public void setTransferTime(Time aTransferTime) {theTransferTime = aTransferTime;}

	/** Пользователь, принявший биоматериал */
	@Comment("Пользователь, принявший биоматериал")
	public String getTransferUsername() {return theTransferUsername;}
	public void setTransferUsername(String aTransferUsername) {theTransferUsername = aTransferUsername;}
	
	/** Раб. функция, принявшего биоматериал */
	@Comment("Раб. функция, принявшего биоматериал")
	@OneToOne
	public WorkFunction getTransferSpecial() {return theTransferSpecial;}
	public void setTransferSpecial(WorkFunction aTransferSpecial) {theTransferSpecial = aTransferSpecial;}

	/** Раб. функция, осущ. забор */
	@Comment("Раб. функция, осущ. забор")
	@OneToOne
	public WorkFunction getIntakeSpecial() {return theIntakeSpecial;}
	public void setIntakeSpecial(WorkFunction aIntakeSpecial) {theIntakeSpecial = aIntakeSpecial;}

	/** Раб. функция, осущ. забор */
	private WorkFunction theIntakeSpecial;
	/** Раб. функция, принявшего биоматериал */
	private WorkFunction theTransferSpecial;

	/** Пользователь, принявший биоматериал */
	private String theTransferUsername;
	/** Время передачи */
	private Time theTransferTime;
	/** Дата передачи в лабораторию */
	private Date theTransferDate;
	
	/** Время из wct */
	@Comment("Время из wct")
	@OneToOne
	public WorkCalendarTime getCalendarTime() {return theCalendarTime;}
	public void setCalendarTime(WorkCalendarTime aCalendarTime) {theCalendarTime = aCalendarTime;}

	/** Время из wct */
	private WorkCalendarTime theCalendarTime;
	
	/** Отделение (забора) */
	@Comment("Отделение (забора)")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}

	/** Отделение (забора) */
	private MisLpu theDepartment;
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** СМО */
	private MedCase theMedCase;

	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	public Long getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(Long aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}
	/** Хирургическая операция */
	private Long theSurgicalOperation;
	

}
