package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.CriminalPhoneMessage;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.PhoneString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Сообщения в полицию")
@EntityForm
@EntityFormPersistance(clazz= CriminalPhoneMessage.class)
@WebTrail(comment = "Сообщение в полицию", nameProperties= "id", view="entityParentView-stac_criminalMessages.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage")
@Setter
public class CriminalPhoneMessageForm extends PhoneMessageForm {

	/**Getter of the property <tt>theDate</tt>*/
	@Comment("Дата регистрации")
	@Persist @DoDateString @DateString
	@Required
	public String getPhoneDate() {return phoneDate;}

	/** Getter of the property <tt>theTime</tt>*/
	@Comment("Время регистрации")
	@Persist @DoTimeString @TimeString
	@Required
	public String getPhoneTime() {return phoneTime;}

	@Comment("Фамилия принявшего")
	/** Getter of the property <tt>theRecieverFio</tt> */
	@Persist
	public String getRecieverFio() {return recieverFio;}

	/** Getter of the property <tt>theRecieverPost</tt>*/
	@Comment("Должность принявшего сообщение")
	@Persist @DoUpperCase
	public String getRecieverPost() {return recieverPost;}

	/** Телефон */
	@Comment("Телефон")
	@Persist @PhoneString
	public String getPhone() {return phone;}

	/** Getter of the property <tt>theRecieverOrganization</tt>*/
	@Persist @DoUpperCase
	@Comment("Принявшая сообщение организация")
	public String getRecieverOrganization() {return recieverOrganization;}

	@Comment("Текст сообщения")
	@Persist @DoUpperCase
	public String getText() {return text;}

	@Comment("Передавший сообщение специалист")
	@Persist
	public Long getWorker() {return worker;}

	/** Тип сообщения */
	@Comment("Тип сообщения")
	@Persist @Required
	public Long getPhoneMessageType() {return phoneMessageType;}

	/** Номер сообщения */
	@Comment("Номер сообщения")
	@Persist 
	public String getNumber() {return number;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}

	/** Рабочая функция */
	private Long workFunction;

	/** Тип сообщения */
	private Long phoneMessageType;
	/** Номер сообщения */
	private String number;
	/** Случай медицинского обслуживания */
	private Long medCase;
	/** Дата регистрации*/
	private String phoneDate;
	/** Время регистрации	 */
	private String phoneTime;
	/** Фамилия принявшего */
	private String recieverFio;
	/** Должность принявшего сообщение*/
	private String recieverPost;
	/** Принявшая сообщение организация */
	private String recieverOrganization;
	/** Текст сообщения */
	private String text;
	/** Передавший сообщение специалист */
	private Long worker;
	/** Телефон */
	private String phone;

	/** Когда произошло событие */
	@Comment("Дата, когда произошло событие")
	@Persist @DoDateString @DateString @Required
	public String getWhenDateEventOccurred() {return whenDateEventOccurred;}

	/** Место где произошло событие */
	@Comment("Место где произошло событие")
	@Persist @DoUpperCase @Required
	public String getPlace() {return place;	}

	/** Пояснение обстоятельств */
	@Comment("Пояснение обстоятельств")
	@Persist @DoUpperCase
	public String getComment() {return comment;}

	/** Исход */
	@Comment("Исход")
	@Persist @Required
	public Long getOutcome() {return outcome;}

	/** Исход */
	private Long outcome;
	/** Пояснение обстоятельств */
	private String comment;
	/** Место где произошло событие */
	private String place;
	/** Когда произошло событие */
	private String whenDateEventOccurred;
	
	/** Фамилия принявшего сообщение */
	@Comment("Фамилия принявшего сообщение")
	@Persist
	public Long getRecieverEmploye() {return recieverEmploye;}

	/** Фамилия принявшего сообщение */
	private Long recieverEmploye;
	/** Организация */
	@Comment("Организация")
	@Persist
	public Long getOrganization() {return organization;}

	/** Организация */
	private Long organization;
	/** Время, когда произошло событие */
	@Comment("Время, когда произошло событие")
	@Persist @DoTimeString @TimeString @Required
	public String getWhenTimeEventOccurred() {return whenTimeEventOccurred;}

	/** Время, когда произошло событие */
	private String whenTimeEventOccurred;

	/** Подтип сообщения*/
	@Comment("Подтип сообщения")
	@Persist @Required
	public Long getPhoneMessageSubType() {return phoneMessageSubType;}
	/** Подтип сообщения*/
	private Long phoneMessageSubType;
	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public String getDiagnosis() {return diagnosis;}

	/** Диагноз */
	private String diagnosis;

	/** Район */
	@Comment("Район")
	@Persist
	@Required
	public Long getRayon() {return rayon;}

	/** Код МКБ */
	@Comment("Код МКБ")
	@Persist
	public Long getIdc10() {return idc10;}

	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@Persist
	public Long getState() {return state;}

	/** Тяжесть состояния */
	private Long state;

	/** Код МКБ */
	private Long idc10;
	/** Район */
	private Long rayon;
}
