package ru.ecom.mis.ejb.form.contract;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
@EntityForm
@EntityFormPersistance(clazz = NaturalPerson.class)
@Comment("Физическое лицо")
@WebTrail(comment = "Физическое лицо ", nameProperties= "id", list="entityList-contract_naturalPerson.do", view="entityView-contract_naturalPerson.do")
//@Parent(property="parent", parentForm=PARENT.class)
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
