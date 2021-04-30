package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class PatientListRecordForm extends IdEntityForm {

	/** Список */
	@Comment("Список")
	@Persist @Required
	public Long getPatientList() {return patientList;}
	/** Список */
	private Long patientList;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return patient;}
	/** Пациент */
	private Long patient;
	
	/** Сообщение */
	@Comment("Сообщение")
	@Persist
	public String getMessage() {return message;}
	/** Сообщение */
	private String message;

	/** Номер телефона */
	@Comment("Номер телефона")
	@Persist
	public String getPhoneNumber() {return phoneNumber;}
	/** Номер телефона */
	private String phoneNumber ;
}
