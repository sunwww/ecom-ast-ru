package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

   /**
    * Телефонное сообщение 
    */
@Entity
@Table(schema="SQLUser")
public class PhoneMessage extends BaseEntity {

	/**
	 * Дата регистрации
	 */
	@Comment("Дата регистрации")
	public Date getPhoneDate() {return thePhoneDate;}
	public void setPhoneDate(Date theDate) {this.thePhoneDate = theDate;}
	private Date thePhoneDate;
	
	/**
	 * Время регистрации
	 */
	@Comment("Время регистрации")
	public Time getPhoneTime() {return thePhoneTime;}
	public void setPhoneTime(Time theTime) {this.thePhoneTime = theTime;}
	private Time thePhoneTime;
	
	@Comment("Фамилия принявшего")
	/**
	 * Getter of the property <tt>theRecieverFio</tt>
	 * @return  Returns the theRecieverFio.
	 */
	public String getRecieverFio() {return theRecieverFio;}
	public void setRecieverFio(String aRecieverFio) {theRecieverFio = aRecieverFio;}

	/** Должность принявшего сообщение*/
	private String theRecieverPost;
	/** Фамилия принявшего */
	private String theRecieverFio;

	/**
	 * Getter of the property <tt>theRecieverPost</tt>
	 */
	@Comment("Должность принявшего сообщение")
	public String getRecieverPost() {return theRecieverPost;}
	public void setRecieverPost(String aRecieverPost) {theRecieverPost = aRecieverPost;}

	/** Телефон */
	@Comment("Телефон")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Телефон */
	private String thePhone;
	/** Принявшая сообщение организация */
	private String theRecieverOrganization = "";

	/**
	 * Getter of the property <tt>theRecieverOrganization</tt>
	 */
	@Comment("Принявшая сообщение организация")
	public String getRecieverOrganization() {
		return theRecieverOrganization;
	}

	/**
	 * Setter of the property <tt>theRecieverOrganization</tt>
	 */
	public void setRecieverOrganization(String theRecieverOrganization) {
		this.theRecieverOrganization = theRecieverOrganization;
	}

	/**
	 * Текст сообщения
	 */
	private String theText = "";

	/**
	 * Getter of the property <tt>theText</tt>
	 */
	@Comment("Текст сообщения")
	public String getText() {
		return theText;
	}

	/**
	 * Setter of the property <tt>theText</tt>
	 */
	public void setText(String theText) {
		this.theText = theText;
	}

	/**
	 * Передавший сообщение специалист
	 */
	private Worker theWorker;

	/**
	 * Getter of the property <tt>theWorker</tt>

	 */
	@Comment("Передавший сообщение специалист")
	@OneToOne
	public Worker getWorker() {
		return theWorker;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция */
	private WorkFunction theWorkFunction;

	/**
	 * Setter of the property <tt>theWorker</tt>
	 */
	public void setWorker(Worker theWorker) {
		this.theWorker = theWorker;
	}

	/**
	 * Тип сообщения
	 */
	@Comment("Тип сообщения")
	@OneToOne
	public VocPhoneMessageType getPhoneMessageType() {
		return thePhoneMessageType;
	}

	/**
	 * Тип сообщения
	 */
	public void setPhoneMessageType(VocPhoneMessageType aNewProperty) {
		thePhoneMessageType = aNewProperty;
	}

	/**
	 * Тип сообщения
	 */
	private VocPhoneMessageType thePhoneMessageType;

	/**
	 * Номер сообщения
	 */
	@Comment("Номер сообщения")
	public String getNumber() {
		return theNumber;
	}

	/**
	 * Номер сообщения
	 */
	public void setNumber(String aNewProperty) {
		theNumber = aNewProperty;
	}

	/**
	 * Номер сообщения
	 */
	private String theNumber;

	/**
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	/**
	 * Случай медицинского обслуживания
	 */
	public void setMedCase(MedCase aNewProperty) {
		theMedCase = aNewProperty;
	}

	/**
	 * Случай медицинского обслуживания
	 */
	private MedCase theMedCase;
	
	@Transient
	public String getWorkerInfo() {return theWorker!=null ? theWorker.getDoctorInfo():"";}
	@Transient
	public String getWorkFunctionInfo() {return theWorkFunction!=null? theWorkFunction.getWorkFunctionInfo():"";}
	

}
