package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
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
@Setter
public class PhoneMessageForm extends IdEntityForm {

	/**
	 * Дата регистрации
	 */
	@Comment("Дата регистрации")
	@Persist @DateString @DoDateString
	public String getPhoneDate() {return phoneDate;}
	private String phoneDate;
	
	/**
	 * Время регистрации
	 */
	@Comment("Время регистрации")
	@Persist @TimeString @DoTimeString
	public String getPhoneTime() {return phoneTime;}
	private String phoneTime;
	
	@Comment("Фамилия принявшего")
	@Persist
	public String getRecieverFio() {return recieverFio;}

	/** Фамилия принявшего сообщение */
	@Comment("Фамилия принявшего сообщение")
	@Persist
	public Long getRecieverEmploye() {return recieverEmploye;}

	/** Фамилия принявшего сообщение */
	private Long recieverEmploye;
	/** Должность принявшего сообщение*/
	private String recieverPost;
	/** Фамилия принявшего */
	private String recieverFio;

	/**
	 * Getter of the property <tt>theRecieverPost</tt>
	 */
	@Comment("Должность принявшего сообщение")
	@Persist
	public String getRecieverPost() {return recieverPost;}

	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhone() {return phone;}

	/** Телефон */
	private String phone;
	/** Принявшая сообщение организация */
	private String recieverOrganization = "";

	@Comment("Принявшая сообщение организация")
	@Persist
	public String getRecieverOrganization() {
		return recieverOrganization;
	}

	@Comment("Текст сообщения")
	@Persist
	public String getText() {return text;}
	/**
	 * Текст сообщения
	 */
	private String text;

	/**
	 * Передавший сообщение специалист
	 */
	private Long worker;

	/**
	 * Getter of the property <tt>theWorker</tt>

	 */
	@Comment("Передавший сообщение специалист")
	@Persist
	public Long getWorker() {
		return worker;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return workFunction;}

	/** Рабочая функция */
	private Long workFunction;

	/**
	 * Тип сообщения
	 */
	@Comment("Тип сообщения")
	@Persist
	public Long getPhoneMessageType() {
		return phoneMessageType;
	}


	/**
	 * Тип сообщения
	 */
	private Long phoneMessageType;

	/**
	 * Номер сообщения
	 */
	@Comment("Номер сообщения")
	@Persist
	public String getNumber() {
		return number;
	}

	/**
	 * Номер сообщения
	 */
	private String number;

	/**
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {
		return medCase;
	}

	/**
	 * Случай медицинского обслуживания
	 */
	private Long medCase;
		

	/** Подтип сообщения*/
	@Comment("Подтип сообщения")
	@Persist
	public Long getPhoneMessageSubType() {return phoneMessageSubType;}

	/** Подтип сообщения*/
	private Long phoneMessageSubType;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return createTime;	}

	/** Пользователь, которые последним редактировал запись */
	@Comment("Пользователь, которые последним редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Дата редакции */
	@Comment("Дата редакции")
	@Persist @DateString @DoDateString
	public String getEditDate() {return editDate;}

	/** Время редакции */
	@Comment("Время редакции")
	@Persist @TimeString @DoTimeString
	public String getEditTime() {return editTime;}

	/** Время редакции */
	private String editTime;
	/** Дата редакции */
	private String editDate;
	/** Пользователь, которые последним редактировал запись */
	private String editUsername;
	/** Время создания */
	private String createTime;
	/** Дата создания */
	private String createDate;
	/** Пользователь, создавший запись */
	private String createUsername;
}
