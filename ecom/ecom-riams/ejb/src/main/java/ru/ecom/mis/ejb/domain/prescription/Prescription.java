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
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

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
@AIndexes(value = {  @AIndex(properties = { "intakeDate" })
					,@AIndex(properties = { "calendarTime" })
					,@AIndex(properties = { "prescriptionList" })
					,@AIndex(properties = { "planStartDate" })
					,@AIndex(properties = { "medCase" })
})
@EntityListeners(DeleteListener.class)
public abstract class Prescription extends BaseEntity{

	@PrePersist
	void onPrePersist() {
		long currentTime = System.currentTimeMillis();
		theCreateDate=new java.sql.Date(currentTime);
		theCreateTime=new java.sql.Time(currentTime);
	}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Transient
	public VocServiceStream getServiceStream() {
	    return getPrescriptionList().getServiceStream() ;}

	/** Лист назначений */
	@Comment("Лист назначений")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	public AbstractPrescriptionList getPrescriptionList() {return thePrescriptionList;}
	public void setPrescriptionList(AbstractPrescriptionList aPrescriptionList) {thePrescriptionList = aPrescriptionList;}
	
	/** Выполнения */
	@Comment("Выполнения")
	@OneToMany(fetch = FetchType.EAGER, mappedBy="prescription", cascade=CascadeType.ALL)
	public List<PrescriptionFulfilment> getFulfilments() {return theFulfilments;}
	public void setFulfilments(List<PrescriptionFulfilment> aFulfilments) {theFulfilments = aFulfilments;}

	@Transient
	//Берем информацию о первом выполнении
	public String getFulfilmentDateTime() {
		if (getFulfilments()!=null && !getFulfilments().isEmpty()) {
			PrescriptionFulfilment fulfilment = getFulfilments().get(0);
			return DateFormat.formatToDate(fulfilment.getFulfilDate())+" "+DateFormat.formatToTime(fulfilment.getFulfilTime());
		}
		return "";
	}
	
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

	/** Описание назначений */
	@Comment("Описание назначений")
	@Transient
	public String getDescriptionInfo() {
		return "Описание назначения";
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
	/** Комментарии */
	private String theComments;
	/** Состояние исполнения */
	private VocPrescriptFulfilState theFulfilmentState;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	private Date theCreateDate;
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	private Date theEditDate;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	private Time theCreateTime;

	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	private Time theEditTime;

	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	private String theCreateUsername;

	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	private String theEditUsername;

	/** Тип назначения */
	@Comment("Тип назначения")
	@OneToOne
	public VocPrescriptType getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(VocPrescriptType aPrescriptType) {thePrescriptType = aPrescriptType;}
	private VocPrescriptType thePrescriptType;

	/** Дата забора */
	@Comment("Дата забора")
	public Date getIntakeDate() {return theIntakeDate;}
	public void setIntakeDate(Date aIntakeDate) {theIntakeDate = aIntakeDate;}
	private Date theIntakeDate;

	/** Время забора */
	@Comment("Время забора")
	public Time getIntakeTime() {return theIntakeTime;}
	public void setIntakeTime(Time aIntakeTime) {theIntakeTime = aIntakeTime;}
	private Time theIntakeTime;

	/** Пользователь, осуществившей забор */
	@Comment("Пользователь, осуществившей забор")
	public String getIntakeUsername() {return theIntakeUsername;}
	public void setIntakeUsername(String aIntakeUsername) {theIntakeUsername = aIntakeUsername;}
	private String theIntakeUsername;

	/** Идентификатор материала */
	@Comment("Идентификатор материала")
	public String getMaterialId() {return theMaterialId;}
	public void setMaterialId(String aMaterialId) {theMaterialId = aMaterialId;}
	private String theMaterialId;

	/** Номер пробирки ПЦР*/
	@Comment("Номер пробирки ПЦР")
	public String getMaterialPCRId() {return theMaterialPCRId;}
	public void setMaterialPCRId(String aMaterialPCRId) {theMaterialPCRId = aMaterialPCRId;}
	private String theMaterialPCRId;
	
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
	private Date theTransferDate;

	/** Время передачи */
	@Comment("Время передачи")
	public Time getTransferTime() {return theTransferTime;}
	public void setTransferTime(Time aTransferTime) {theTransferTime = aTransferTime;}
	private Time theTransferTime;

	/** Пользователь, принявший биоматериал */
	@Comment("Пользователь, принявший биоматериал")
	public String getTransferUsername() {return theTransferUsername;}
	public void setTransferUsername(String aTransferUsername) {theTransferUsername = aTransferUsername;}
	private String theTransferUsername;
	
	/** Раб. функция, принявшего биоматериал */
	@Comment("Раб. функция, принявшего биоматериал")
	@OneToOne
	public WorkFunction getTransferSpecial() {return theTransferSpecial;}
	public void setTransferSpecial(WorkFunction aTransferSpecial) {theTransferSpecial = aTransferSpecial;}
	private WorkFunction theTransferSpecial;

	/** Раб. функция, осущ. забор */
	@Comment("Раб. функция, осущ. забор")
	@OneToOne
	public WorkFunction getIntakeSpecial() {return theIntakeSpecial;}
	public void setIntakeSpecial(WorkFunction aIntakeSpecial) {theIntakeSpecial = aIntakeSpecial;}
	private WorkFunction theIntakeSpecial;

	/** Время из wct */
	@Comment("Время из wct")
	@OneToOne
	public WorkCalendarTime getCalendarTime() {return theCalendarTime;}
	public void setCalendarTime(WorkCalendarTime aCalendarTime) {theCalendarTime = aCalendarTime;}
	private WorkCalendarTime theCalendarTime;
	
	/** Отделение (забора) */
	@Comment("Отделение (забора)")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
	private MisLpu theDepartment;
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
	private MedCase theMedCase;

	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	public Long getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(Long aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}
	private Long theSurgicalOperation;

	/** Дата установки патологии*/
	@Comment("Дата установки патологии")
	public Date getSetPatologyDate() {return theSetPatologyDate;}
	public void setSetPatologyDate(Date aSetPatologyDate) {theSetPatologyDate = aSetPatologyDate;}
	/** Дата установки патологии */
	private Date theSetPatologyDate;

	/** Время установки патологии */
	@Comment("Время установки патологии")
	public Time getSetPatologyTime() {return theSetPatologyTime;}
	public void setSetPatologyTime(Time aSetPatologyTime) {theSetPatologyTime = aSetPatologyTime;}
	/** Время установки патологии */
	private Time theSetPatologyTime;

	/** Пользователь, установивший патологию*/
	@Comment("Пользователь, установивший патологию")
	public String getSetPatologyUsername() {return theSetPatologyUsername;}
	public void setSetPatologyUsername(String aSetPatologyUsername) {theSetPatologyUsername = aSetPatologyUsername;}
	/** Пользователь, установивший патологию*/
	private String theSetPatologyUsername;

	/** Проставил патологию специалист */
	@Comment("Проставил патологию специалист")
	@OneToOne
	public WorkFunction getSetPatologySpecial() {return theSetPatologySpecial;}
	public void setSetPatologySpecial(WorkFunction aSetPatologySpecial) {theSetPatologySpecial = aSetPatologySpecial;}
	/** Проставил патологию специалист */
	private WorkFunction theSetPatologySpecial;
}