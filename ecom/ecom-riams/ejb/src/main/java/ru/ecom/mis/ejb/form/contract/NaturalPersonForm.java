package ru.ecom.mis.ejb.form.contract;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
@EntityForm
@EntityFormPersistance(clazz = NaturalPerson.class)
@Comment("Физическое лицо")
@WebTrail(comment = "Физическое лицо ", nameProperties= "id", list="entityList-contract_naturalPerson.do", view="entityView-contract_naturalPerson.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/ContractPerson/NaturalPerson")
public class NaturalPersonForm extends ContractPersonForm{
	/**
	 * Пациента
	 */
	@Comment("Пациента")
	@Persist
	public Long getPatient() {
		return thePatient;
	}
	public void setPatient(Long aPatient) {
		thePatient = aPatient;
	}
	/**
	 * Пациента
	 */
	private Long thePatient;
}
