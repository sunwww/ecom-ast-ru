package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.CriminalPhoneMessage;
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
import ru.nuzmsh.forms.validator.validators.PhoneString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Сообщения в милицию")
@EntityForm
@EntityFormPersistance(clazz= PhoneMessage.class)
@WebTrail(comment = "Сообщения", nameProperties= "id", view="entitySublcassView-stac_phoneMessage.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage")
@Subclasses(value = { CriminalPhoneMessageForm.class, InfectiousPhoneMessageForm.class })
public class PhoneMessageForm extends IdEntityForm {

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
	@Persist
	public String getRecieverPost() {return theRecieverPost;}
	/** Setter of the property <tt>theRecieverPost</tt> */
	public void setRecieverPost(String aRecieverPost) {theRecieverPost = aRecieverPost;}



	/** Телефон */
	@Comment("Телефон")
	@Persist @Required @PhoneString
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhoneNumber) {thePhone = aPhoneNumber;}

	
	/** Getter of the property <tt>theRecieverOrganization</tt>*/
	@Persist
	@Comment("Принявшая сообщение организация")
	public String getRecieverOrganization() {return theRecieverOrganization;}
	public void setRecieverOrganization(String aRecieverOrganization) {theRecieverOrganization = aRecieverOrganization;}

	@Comment("Текст сообщения")
	@Persist
	public String getText() {return theText;}
	public void setText(String theText) {this.theText = theText;}

	@Comment("Передавший сообщение специалист")
	@Persist
	public Long getWorker() {return theWorker;}
	public void setWorker(Long aWorker) {theWorker = aWorker;}

	/** Тип сообщения */
	@Comment("Тип сообщения")
	@Persist
	public Long getPhoneMessageType() {return thePhoneMessageType;}
	/** Тип сообщения */
	public void setPhoneMessageType(Long aNewProperty) {thePhoneMessageType = aNewProperty;	}


	/** Номер сообщения */
	@Comment("Номер сообщения")
	@Persist @Required
	public String getNumber() {return theNumber;}
	/** Номер сообщения */
	public void setNumber(String aNewProperty) {theNumber = aNewProperty;}


	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return theMedCase;}
	/** Случай медицинского обслуживания */
	public void setMedCase(Long aNewProperty) {theMedCase = aNewProperty;}
	
	/** WorkerInfo */
	@Comment("WorkerInfo")
	@Persist
	public String getWorkerInfo() {return theWorkerInfo;}
	public void setWorkerInfo(String aWorkerInfo) {theWorkerInfo = aWorkerInfo;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция инфо */
	@Comment("Рабочая функция инфо")
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aWorkFunctionInfo) {theWorkFunctionInfo = aWorkFunctionInfo;}

	/** Рабочая функция инфо */
	private String theWorkFunctionInfo;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** WorkerInfo */
	private String theWorkerInfo;
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

}
