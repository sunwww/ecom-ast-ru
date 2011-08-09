package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ServedPerson.class)
@Comment("Обслуживаемая персона")
@WebTrail(comment = "Обслуживаемая персона", nameProperties= "id", list="entityParentList-contract_servedPerson.do", view="entityParentView-contract_servedPerson.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson")
public class ServedPersonForm extends IdEntityForm{
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@Persist
	public Long getPerson() {
		return thePerson;
	}
	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Договорная персона
	 */
	private Long thePerson;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return theContract;
	}
	public void setContract(Long aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private Long theContract;
	/**
	 * Дата начала обслуживания
	 */
	@Comment("Дата начала обслуживания")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала обслуживания
	 */
	private String theDateFrom;
	/**
	 * Дата окончания обслуживания
	 */
	@Comment("Дата окончания обслуживания")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания обслуживания
	 */
	private String theDateTo;
}
