package ru.ecom.mis.ejb.form.medcase.hospital;

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
public class CriminalPhoneMessageForm extends PhoneMessageForm {

	/**Getter of the property <tt>theDate</tt>*/
	@Comment("Дата регистрации")
	@Persist @DoDateString @DateString
	@Required
	public String getPhoneDate() {return thePhoneDate;}
	/** Setter of the property <tt>theDate</tt>*/
	public void setPhoneDate(String aDate) {thePhoneDate = aDate;	}


	/** Getter of the property <tt>theTime</tt>*/
	@Comment("Время регистрации")
	@Persist @DoTimeString @TimeString
	@Required
	public String getPhoneTime() {return thePhoneTime;}
	/** Setter of the property <tt>theTime</tt> */
	public void setPhoneTime(String aTime) {thePhoneTime = aTime;}

	@Comment("Фамилия принявшего")
	/** Getter of the property <tt>theRecieverFio</tt> */
	@Persist
	public String getRecieverFio() {return theRecieverFio;}
	/** Setter of the property <tt>theRecieverFio</tt>*/
	public void setRecieverFio(String aRecieverFio) {theRecieverFio = aRecieverFio;}
	
	/** Getter of the property <tt>theRecieverPost</tt>*/
	@Comment("Должность принявшего сообщение")
	@Persist @DoUpperCase
	public String getRecieverPost() {return theRecieverPost;}
	/** Setter of the property <tt>theRecieverPost</tt> */
	public void setRecieverPost(String aRecieverPost) {theRecieverPost = aRecieverPost;}



	/** Телефон */
	@Comment("Телефон")
	@Persist @PhoneString
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhoneNumber) {thePhone = aPhoneNumber;}

	
	/** Getter of the property <tt>theRecieverOrganization</tt>*/
	@Persist @DoUpperCase
	@Comment("Принявшая сообщение организация")
	public String getRecieverOrganization() {return theRecieverOrganization;}
	public void setRecieverOrganization(String aRecieverOrganization) {theRecieverOrganization = aRecieverOrganization;}

	@Comment("Текст сообщения")
	@Persist @DoUpperCase
	public String getText() {return theText;}
	public void setText(String theText) {this.theText = theText;}

	@Comment("Передавший сообщение специалист")
	@Persist
	public Long getWorker() {return theWorker;}
	public void setWorker(Long aWorker) {theWorker = aWorker;}

	/** Тип сообщения */
	@Comment("Тип сообщения")
	@Persist @Required
	public Long getPhoneMessageType() {return thePhoneMessageType;}
	/** Тип сообщения */
	public void setPhoneMessageType(Long aNewProperty) {thePhoneMessageType = aNewProperty;	}


	/** Номер сообщения */
	@Comment("Номер сообщения")
	@Persist 
	public String getNumber() {return theNumber;}
	/** Номер сообщения */
	public void setNumber(String aNewProperty) {theNumber = aNewProperty;}


	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return theMedCase;}
	/** Случай медицинского обслуживания */
	public void setMedCase(Long aNewProperty) {theMedCase = aNewProperty;}
	

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}


	/** Рабочая функция */
	private Long theWorkFunction;

	/** Тип сообщения */
	private Long thePhoneMessageType;
	/** Номер сообщения */
	private String theNumber;
	/** Случай медицинского обслуживания */
	private Long theMedCase;
	/** Дата регистрации*/
	private String thePhoneDate;
	/** Время регистрации	 */
	private String thePhoneTime;
	/** Фамилия принявшего */
	private String theRecieverFio;
	/** Должность принявшего сообщение*/
	private String theRecieverPost;
	/** Принявшая сообщение организация */
	private String theRecieverOrganization;
	/** Текст сообщения */
	private String theText;
	/** Передавший сообщение специалист */
	private Long theWorker;
	/** Телефон */
	private String thePhone;

	/** Когда произошло событие */
	@Comment("Дата, когда произошло событие")
	@Persist @DoDateString @DateString @Required
	public String getWhenDateEventOccurred() {return theWhenDateEventOccurred;}
	public void setWhenDateEventOccurred(String aWhenEventOccurred) {theWhenDateEventOccurred = aWhenEventOccurred;}
	
	/** Место где произошло событие */
	@Comment("Место где произошло событие")
	@Persist @DoUpperCase @Required
	public String getPlace() {return thePlace;	}
	public void setPlace(String aPlace) {thePlace = aPlace;}

	/** Пояснение обстоятельств */
	@Comment("Пояснение обстоятельств")
	@Persist @DoUpperCase
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	
	/** Исход */
	@Comment("Исход")
	@Persist @Required
	public Long getOutcome() {return theOutcome;}
	public void setOutcome(Long aOutcome) {theOutcome = aOutcome;}

	/** Исход */
	private Long theOutcome;
	/** Пояснение обстоятельств */
	private String theComment;
	/** Место где произошло событие */
	private String thePlace;
	/** Когда произошло событие */
	private String theWhenDateEventOccurred;
	
	/** Фамилия принявшего сообщение */
	@Comment("Фамилия принявшего сообщение")
	@Persist
	public Long getRecieverEmploye() {return theRecieverEmploye;}
	public void setRecieverEmploye(Long aRecieverEmploye) {theRecieverEmploye = aRecieverEmploye;}

	/** Фамилия принявшего сообщение */
	private Long theRecieverEmploye;
	/** Организация */
	@Comment("Организация")
	@Persist
	public Long getOrganization() {return theOrganization;}
	public void setOrganization(Long aOrganization) {theOrganization = aOrganization;}

	/** Организация */
	private Long theOrganization;
	/** Время, когда произошло событие */
	@Comment("Время, когда произошло событие")
	@Persist @DoTimeString @TimeString @Required
	public String getWhenTimeEventOccurred() {return theWhenTimeEventOccurred;}
	public void setWhenTimeEventOccurred(String aWhenTimeEventOccurred) {theWhenTimeEventOccurred = aWhenTimeEventOccurred;}

	/** Время, когда произошло событие */
	private String theWhenTimeEventOccurred;

	/** Подтип сообщения*/
	@Comment("Подтип сообщения")
	@Persist @Required
	public Long getPhoneMessageSubType() {return thePhoneMessageSubType;}
	/** Подтип сообщения*/
	public void setPhoneMessageSubType(Long a_Property) {
		thePhoneMessageSubType = a_Property;
	}


	/** Подтип сообщения*/
	private Long thePhoneMessageSubType;
	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;	}

	/** Диагноз */
	private String theDiagnosis;

	/** Район */
	@Comment("Район")
	@Persist
	@Required
	public Long getRayon() {return theRayon;}
	public void setRayon(Long aRayon) {theRayon = aRayon;}

	/** Код МКБ */
	@Comment("Код МКБ")
	@Persist
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@Persist
	public Long getState() {return theState;}
	public void setState(Long aState) {theState = aState;}

	/** Тяжесть состояния */
	private Long theState;

	/** Код МКБ */
	private Long theIdc10;
	/** Район */
	private Long theRayon;
}
