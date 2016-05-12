package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageEmploye;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageState;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageType;
import ru.ecom.mis.ejb.domain.patient.voc.VocRayon;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

   /**
    * Телефонное сообщение 
    */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="medCase")
})
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

	/** Фамилия принявшего сообщение */
	@Comment("Фамилия принявшего сообщение")
	@OneToOne
	public VocPhoneMessageEmploye getRecieverEmploye() {return theRecieverEmploye;}
	public void setRecieverEmploye(VocPhoneMessageEmploye aRecieverEmploye) {theRecieverEmploye = aRecieverEmploye;}

	/** Фамилия принявшего сообщение */
	private VocPhoneMessageEmploye theRecieverEmploye;
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
		

	/** Подтип сообщения*/
	@Comment("Подтип сообщения")
	@OneToOne
	public VocPhoneMessageSubType getPhoneMessageSubType() {return thePhoneMessageSubType;}
	/** Подтип сообщения*/
	public void setPhoneMessageSubType(VocPhoneMessageSubType a_Property) {
		thePhoneMessageSubType = a_Property;
	}


	/** Подтип сообщения*/
	private VocPhoneMessageSubType thePhoneMessageSubType;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;	}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Пользователь, которые последним редактировал запись */
	@Comment("Пользователь, которые последним редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата редакции */
	@Comment("Дата редакции")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}

	/** Время редакции */
	
	@Comment("Время редакции")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}

	/** Время редакции */
	private Time theEditTime;
	/** Дата редакции */
	private Date theEditDate;
	/** Пользователь, которые последним редактировал запись */
	private String theEditUsername;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	
	/** Район */
	@Comment("Район")
	@OneToOne
	public VocRayon getRayon() {return theRayon;}
	public void setRayon(VocRayon aRayon) {theRayon = aRayon;}

	/** Код МКБ */
	@Comment("Код МКБ")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** Диагноз текст */
	@Comment("Диагноз текст")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@OneToOne
	public VocPhoneMessageState getState() {return theState;}
	public void setState(VocPhoneMessageState aState) {theState = aState;}

	/** Тяжесть состояния */
	private VocPhoneMessageState theState;
	/** Диагноз текст */
	private String theDiagnosis;
	/** Код МКБ */
	private VocIdc10 theIdc10;
	/** Район */
	private VocRayon theRayon;
}
