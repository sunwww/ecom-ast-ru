package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.PatientListRecord;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;


@EntityForm
@EntityFormPersistance(clazz= PatientListRecord.class)
@Comment("Пациент в списке")
@WebTrail(comment = "Пациент в списке", nameProperties="patientList", view="entityView-mis_patientListRecord.do")
@Parent(property="patientList", parentForm= PatientListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/PatientList")


public class PatientListRecordForm extends IdEntityForm {

	/** Список */
	@Comment("Список")
	@Persist @Required
	public Long getPatientList() {return thePatientList;}
	public void setPatientList(Long aPatientList) {thePatientList = aPatientList;}
	/** Список */
	private Long thePatientList;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** Сообщение */
	@Comment("Сообщение")
	@Persist
	public String getMessage() {return theMessage;}
	public void setMessage(String aMessage) {theMessage = aMessage;}
	/** Сообщение */
	private String theMessage;

	/** Номер телефона */
	@Comment("Номер телефона")
	@Persist
	public String getPhoneNumber() {return thePhoneNumber;}
	public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
	/** Номер телефона */
	private String thePhoneNumber ;
}
