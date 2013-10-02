package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.EmergencyIdentification;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = EmergencyIdentification.class)
@Comment("Экстренная идентификация")
@WebTrail(comment = "Экстренная идентификация", nameProperties= "id"
, view="entityParentView-pd_emergencyIdentification.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/EmergencyIdentification")
public class EmergencyIdentificationForm extends IdEntityForm{
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
	 * ФИО звонившего
	 */
	@Comment("ФИО звонившего")
	@Persist
	public String getCallerFullname() {
		return theCallerFullname;
	}
	public void setCallerFullname(String aCallerFullname) {
		theCallerFullname = aCallerFullname;
	}
	/**
	 * ФИО звонившего
	 */
	private String theCallerFullname;
	/**
	 * Должность звонившего
	 */
	@Comment("Должность звонившего")
	@Persist
	public String getCallerPost() {
		return theCallerPost;
	}
	public void setCallerPost(String aCallerPost) {
		theCallerPost = aCallerPost;
	}
	/**
	 * Должность звонившего
	 */
	private String theCallerPost;
	/**
	 * Откуда звонил
	 */
	@Comment("Откуда звонил")
	@Persist
	public Long getCallerSystem() {
		return theCallerSystem;
	}
	public void setCallerSystem(Long aCallerSystem) {
		theCallerSystem = aCallerSystem;
	}
	/**
	 * Откуда звонил
	 */
	private Long theCallerSystem;
}
