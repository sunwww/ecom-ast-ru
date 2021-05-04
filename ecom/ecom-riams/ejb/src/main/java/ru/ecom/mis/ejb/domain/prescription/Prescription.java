package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public abstract class Prescription extends BaseEntity{

	@PrePersist
	void onPrePersist() {
		long currentTime = System.currentTimeMillis();
		createDate=new java.sql.Date(currentTime);
		createTime=new java.sql.Time(currentTime);
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
	public AbstractPrescriptionList getPrescriptionList() {return prescriptionList;}

	/** Выполнения */
	@Comment("Выполнения")
	@OneToMany(fetch = FetchType.EAGER, mappedBy="prescription", cascade=CascadeType.ALL)
	public List<PrescriptionFulfilment> getFulfilments() {return fulfilments;}

	@Transient
	//Берем информацию о первом выполнении
	public String getFulfilmentDateTime() {
		if (getFulfilments()!=null && !getFulfilments().isEmpty()) {
			PrescriptionFulfilment fulfilment = getFulfilments().get(0);
			return DateFormat.formatToDate(fulfilment.getFulfilDate())+" "+DateFormat.formatToTime(fulfilment.getFulfilTime());
		}
		return "";
	}
	
	/** Регистратор */
	@Comment("Регистратор")
	@OneToOne
	public Worker getRegistrator() {return registrator;}


	/** Причина отмены */
	@Comment("Причина отмены")
	@OneToOne
	public VocPrescriptCancelReason getCancelReason() {return cancelReason;}

	/** Описание */
	@Comment("Описание")
	@Transient
	public String getDescription() {return "";}
	public void setDescription(String aDescription) {}

	/** Состояние исполнения */
	@Comment("Состояние исполнения")
	@OneToOne
	public VocPrescriptFulfilState getFulfilmentState() {return fulfilmentState;}

	/** Отменивший(text) */
	@Comment("Отменивший (text)")
	@Transient
	public String getCancelWorkerInfo() {return cancelSpecial!=null ? cancelSpecial.getWorkFunctionInfo(): "";}

   /** Назначивший (text)*/
   @Comment("Назначивший (text)")
   @Transient
   public String getPrescriptorInfo() { return prescriptSpecial!=null ? prescriptSpecial.getWorkFunctionInfo() : ""; }
 
   /** Регистратор (text) */
	@Comment("Регистратор (text)")
	@Transient
	public String getRegistratorInfo() {return registrator!=null? registrator.getWorkerInfo() : "";}

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
	public String getSignature() {return signature;}

	/** Назначил специалист */
	@Comment("Назначил специалист")
	@OneToOne
	public WorkFunction getPrescriptSpecial() {return prescriptSpecial;}

	/** Отменил специалист */
	@Comment("Отменил специалист")
	@OneToOne
	public WorkFunction getCancelSpecial() {return cancelSpecial;}
	/** Кабинет назначения */
	@Comment("Кабинет назначения")
	@OneToOne
	public WorkFunction getPrescriptCabinet() {
		return prescriptCabinet;
	}

	/** Кабинет назначения */
	private WorkFunction prescriptCabinet;
	/** Отменил специалист */
	private WorkFunction cancelSpecial;
	/** Назначил специалист */
	private WorkFunction prescriptSpecial;
	/** Роспись */
	private String signature;	
	/** Лист назначений */
	private AbstractPrescriptionList prescriptionList;
	/** Выполнения */
	private List<PrescriptionFulfilment> fulfilments;
	/** Дата и время регистрации */
	private Timestamp registrationTimeStamp;
	/** Регистратор */
	private Worker registrator;
	/** Плановая дата начала */
	private Date planStartDate;
	/** Плановое время начала */
	private Time planStartTime;
	/** Плановая дата окончания */
	private Date planEndDate;
	/** Плановое время окончания */
	private Time planEndTime;
	/** Дата отмены */
	private Date cancelDate;
	/** Время отмены */
	private Time cancelTime;
	/** Причина отмены */
	private VocPrescriptCancelReason cancelReason;
	/** Комментарии */
	private String comments;
	/** Состояние исполнения */
	private VocPrescriptFulfilState fulfilmentState;
	
	/** Дата создания */
	private Date createDate;
	
	/** Дата редактирования */
	private Date editDate;
	
	/** Время создания */
	private Time createTime;

	/** Время редактрования */
	private Time editTime;

	/** Пользователь, который создал запись */
	private String createUsername;

	/** Пользователь, который последний редактировал запись */
	private String editUsername;

	/** Тип назначения */
	@Comment("Тип назначения")
	@OneToOne
	public VocPrescriptType getPrescriptType() {return prescriptType;}
	private VocPrescriptType prescriptType;

	/** Дата забора */
	private Date intakeDate;

	/** Время забора */
	private Time intakeTime;

	/** Пользователь, осуществившей забор */
	private String intakeUsername;

	/** Идентификатор материала */
	private String materialId;

	/** Номер пробирки ПЦР*/
	private String materialPCRId;
	
	/** Пользователь отменивший */
	private String cancelUsername;
	/** Причина отмены текст */
	private String cancelReasonText;

	/** Дата передачи в лабораторию */
	private Date transferDate;

	/** Время передачи */
	private Time transferTime;

	/** Пользователь, принявший биоматериал */
	private String transferUsername;
	
	/** Раб. функция, принявшего биоматериал */
	@Comment("Раб. функция, принявшего биоматериал")
	@OneToOne
	public WorkFunction getTransferSpecial() {return transferSpecial;}
	private WorkFunction transferSpecial;

	/** Раб. функция, осущ. забор */
	@Comment("Раб. функция, осущ. забор")
	@OneToOne
	public WorkFunction getIntakeSpecial() {return intakeSpecial;}
	private WorkFunction intakeSpecial;

	/** Время из wct */
	@Comment("Время из wct")
	@OneToOne
	public WorkCalendarTime getCalendarTime() {return calendarTime;}
	private WorkCalendarTime calendarTime;
	
	/** Отделение (забора) */
	@Comment("Отделение (забора)")
	@OneToOne
	public MisLpu getDepartment() {return department;}
	private MisLpu department;
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}
	private MedCase medCase;

	/** Хирургическая операция */
	private Long surgicalOperation;

	/** Дата установки патологии*/
	private Date setPatologyDate;

	/** Время установки патологии */
	private Time setPatologyTime;

	/** Пользователь, установивший патологию*/
	private String setPatologyUsername;

	/** Проставил патологию специалист */
	@Comment("Проставил патологию специалист")
	@OneToOne
	public WorkFunction getSetPatologySpecial() {return setPatologySpecial;}
	/** Проставил патологию специалист */
	private WorkFunction setPatologySpecial;

	/** Примечание для лаборатории*/
	private String noteForLab;
}