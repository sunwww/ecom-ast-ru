package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PhoneMessage;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Сообщения в полицию")
@EntityForm
@EntityFormPersistance(clazz= PhoneMessage.class)
@WebTrail(comment = "Сообщения", nameProperties= "id", view="entitySublcassView-stac_phoneMessage.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage")
@Subclasses(value = { CriminalPhoneMessageForm.class, InfectiousPhoneMessageForm.class })
public class PhoneMessageForm extends IdEntityForm {

	/**
	 * Дата регистрации
	 */
	@Comment("Дата регистрации")
	@Persist @DateString @DoDateString
	public String getPhoneDate() {return thePhoneDate;}
	public void setPhoneDate(String theDate) {this.thePhoneDate = theDate;}
	private String thePhoneDate;
	
	/**
	 * Время регистрации
	 */
	@Comment("Время регистрации")
	@Persist @TimeString @DoTimeString
	public String getPhoneTime() {return thePhoneTime;}
	public void setPhoneTime(String theTime) {this.thePhoneTime = theTime;}
	private String thePhoneTime;
	
	@Comment("Фамилия принявшего")
	@Persist
	public String getRecieverFio() {return theRecieverFio;}
	public void setRecieverFio(String aRecieverFio) {theRecieverFio = aRecieverFio;}

	/** Фамилия принявшего сообщение */
	@Comment("Фамилия принявшего сообщение")
	@Persist
	public Long getRecieverEmploye() {return theRecieverEmploye;}
	public void setRecieverEmploye(Long aRecieverEmploye) {theRecieverEmploye = aRecieverEmploye;}

	/** Фамилия принявшего сообщение */
	private Long theRecieverEmploye;
	/** Должность принявшего сообщение*/
	private String theRecieverPost;
	/** Фамилия принявшего */
	private String theRecieverFio;

	/**
	 * Getter of the property <tt>theRecieverPost</tt>
	 */
	@Comment("Должность принявшего сообщение")
	@Persist
	public String getRecieverPost() {return theRecieverPost;}
	public void setRecieverPost(String aRecieverPost) {theRecieverPost = aRecieverPost;}

	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Телефон */
	private String thePhone;
	/** Принявшая сообщение организация */
	private String theRecieverOrganization = "";

	@Comment("Принявшая сообщение организация")
	@Persist
	public String getRecieverOrganization() {
		return theRecieverOrganization;
	}

	public void setRecieverOrganization(String theRecieverOrganization) {
		this.theRecieverOrganization = theRecieverOrganization;
	}

	@Comment("Текст сообщения")
	@Persist
	public String getText() {return theText;}
	public void setText(String aText) {theText = aText;}
	/**
	 * Текст сообщения
	 */
	private String theText;

	/**
	 * Передавший сообщение специалист
	 */
	private Long theWorker;

	/**
	 * Getter of the property <tt>theWorker</tt>

	 */
	@Comment("Передавший сообщение специалист")
	@Persist
	public Long getWorker() {
		return theWorker;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция */
	private Long theWorkFunction;

	/**
	 * Setter of the property <tt>theWorker</tt>
	 */
	public void setWorker(Long theWorker) {
		this.theWorker = theWorker;
	}

	/**
	 * Тип сообщения
	 */
	@Comment("Тип сообщения")
	@Persist
	public Long getPhoneMessageType() {
		return thePhoneMessageType;
	}

	/**
	 * Тип сообщения
	 */
	public void setPhoneMessageType(Long aNewProperty) {
		thePhoneMessageType = aNewProperty;
	}

	/**
	 * Тип сообщения
	 */
	private Long thePhoneMessageType;

	/**
	 * Номер сообщения
	 */
	@Comment("Номер сообщения")
	@Persist
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
	@Persist
	public Long getMedCase() {
		return theMedCase;
	}

	/**
	 * Случай медицинского обслуживания
	 */
	public void setMedCase(Long aNewProperty) {
		theMedCase = aNewProperty;
	}

	/**
	 * Случай медицинского обслуживания
	 */
	private Long theMedCase;
		

	/** Подтип сообщения*/
	@Comment("Подтип сообщения")
	@Persist
	public Long getPhoneMessageSubType() {return thePhoneMessageSubType;}
	/** Подтип сообщения*/
	public void setPhoneMessageSubType(Long a_Property) {
		thePhoneMessageSubType = a_Property;
	}


	/** Подтип сообщения*/
	private Long thePhoneMessageSubType;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return theCreateTime;	}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}

	/** Пользователь, которые последним редактировал запись */
	@Comment("Пользователь, которые последним редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата редакции */
	@Comment("Дата редакции")
	@Persist @DateString @DoDateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

	/** Время редакции */
	@Comment("Время редакции")
	@Persist @TimeString @DoTimeString
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}

	/** Время редакции */
	private String theEditTime;
	/** Дата редакции */
	private String theEditDate;
	/** Пользователь, которые последним редактировал запись */
	private String theEditUsername;
	/** Время создания */
	private String theCreateTime;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;


}
