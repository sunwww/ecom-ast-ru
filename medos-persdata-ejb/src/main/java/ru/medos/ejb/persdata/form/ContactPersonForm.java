package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.ContactPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ContactPerson.class)
@Comment("Контактная персона")
@WebTrail(comment = "Контактная персона", nameProperties= "id", list="entityParentList-personaldata_contactPerson.do", view="entityParentView-personaldata_contactPerson.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/Person")
public class ContactPersonForm extends JournalDataForm{
	/**
	 * Контактная персона
	 */
	@Comment("Контактная персона")
	@Persist
	public Long getContactPerson() {
		return theContactPerson;
	}
	public void setContactPerson(Long aContactPerson) {
		theContactPerson = aContactPerson;
	}
	/**
	 * Контактная персона
	 */
	private Long theContactPerson;
	/**
	 * Тип связи с персоной
	 */
	@Comment("Тип связи с персоной")
	@Persist
	public Long getContactType() {
		return theContactType;
	}
	public void setContactType(Long aContactType) {
		theContactType = aContactType;
	}
	/**
	 * Тип связи с персоной
	 */
	private Long theContactType;
	/**
	 * Персона
	 */
	@Comment("Персона")
	@Persist
	public Long getPerson() {
		return thePerson;
	}
	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Персона
	 */
	private Long thePerson;
	/**
	 * Основной контакт
	 */
	@Comment("Основной контакт")
	@Persist
	public Boolean getIsPrimary() {
		return theIsPrimary;
	}
	public void setIsPrimary(Boolean aIsPrimary) {
		theIsPrimary = aIsPrimary;
	}
	/**
	 * Основной контакт
	 */
	private Boolean theIsPrimary;
	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	@Persist
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарий
	 */
	private String theComment;
}
