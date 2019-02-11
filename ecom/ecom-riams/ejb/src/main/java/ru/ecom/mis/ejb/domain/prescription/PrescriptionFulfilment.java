package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Выполнениe назначения
 * @author azviagin
 *
 */

@Comment("Выполнениe назначения")
@Entity
@Table(schema="SQLUser")
public class PrescriptionFulfilment extends BaseEntity{
	
	/** Дата выполнения */
	@Comment("Дата выполнения")
	public Date getFulfilDate() {
		return theFulfilDate;
	}

	public void setFulfilDate(Date aFulfilDate) {
		theFulfilDate = aFulfilDate;
	}

	/** Дата выполнения */
	private Date theFulfilDate;
	
	/** Время выполнения */
	@Comment("Время выполнения")
	public Time getFulfilTime() {
		return theFulfilTime;
	}

	public void setFulfilTime(Time aFulfilTime) {
		theFulfilTime = aFulfilTime;
	}

	/** Время выполнения */
	private Time theFulfilTime;
	
	/** Исполнитель */
	@Comment("Исполнитель")
	@OneToOne
	public Worker getExecutor() {
		return theExecutor;
	}

	public void setExecutor(Worker aExecutor) {
		theExecutor = aExecutor;
	}

	/** Исполнитель */
	private Worker theExecutor;
	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {
		return theComments;
	}

	public void setComments(String aComments) {
		theComments = aComments;
	}

	/** Комментарии */
	private String theComments;
	
	/** Дата и время регистрации */
	@Comment("Дата и время регистрации")
	public Timestamp getRegistionTimeStamp() {
		return theRegistionTimeStamp;
	}

	public void setRegistionTimeStamp(Timestamp aRegistionTimeStamp) {
		theRegistionTimeStamp = aRegistionTimeStamp;
	}

	/** Дата и время регистрации */
	private Timestamp theRegistionTimeStamp;
	
	/** Назначение */
	@Comment("Назначение")
	@ManyToOne
	public Prescription getPrescription() {
		return thePrescription;
	}

	public void setPrescription(Prescription aPrescription) {
		thePrescription = aPrescription;
	}

	/** Назначение */
	private Prescription thePrescription;
	
	
	/** Рабочая функция исполнителя */
	@Comment("Рабочая функция исполнителя")
	@OneToOne
	public WorkFunction getExecutorWorkFunction() {
		return theExecutorWorkFunction;
	}

	public void setExecutorWorkFunction(WorkFunction aExecutorWorkFunction) {
		theExecutorWorkFunction = aExecutorWorkFunction;
	}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;	}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	
	/** Дата создания записи */
	@Comment("Дата создания записи")
	public Date getDateCreate() {return theDateCreate;}
	public void setDateCreate(Date aDateCreate) {theDateCreate = aDateCreate;}

	/** Дата создания записи */
	private Date theDateCreate;
	/** Пользователь */
	private String theUsername;
	/** Рабочая функция исполнителя */
	private WorkFunction theExecutorWorkFunction;
	
	@Transient
	public String getExecutorInfo() {
		return theExecutorWorkFunction!=null?theExecutorWorkFunction.getWorkerInfo():"" ;
	}
	
//	/**
//	 * Стационарный случай?
//	 * @return
//	 */
//	@Transient
//    public boolean isInHospitalMedCase() {
//        MedCase medCase = getPrescription() != null
//                && getPrescription().getPrescriptionList() != null
//                && getPrescription().getPrescriptionList().getMedCase() != null
//                ? getPrescription().getPrescriptionList().getMedCase(): null;
//        return medCase instanceof HospitalMedCase;
//    }
}