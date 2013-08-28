package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Identifier;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Identifier.class)
@Comment("Идентификатор")
@WebTrail(comment = "Идентификатор", nameProperties= "id", list="entityParentList-personaldata_identifier.do", view="entityParentView-personaldata_identifier.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class IdentifierForm extends IdEntityForm{
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
	 * Идентифицирующая система
	 */
	@Comment("Идентифицирующая система")
	@Persist
	public Long getIdentificationSystem() {
		return theIdentificationSystem;
	}
	public void setIdentificationSystem(Long aIdentificationSystem) {
		theIdentificationSystem = aIdentificationSystem;
	}
	/**
	 * Идентифицирующая система
	 */
	private Long theIdentificationSystem;
	/**
	 * Временный
	 */
	@Comment("Временный")
	@Persist
	public Boolean getIsTransient() {
		return theIsTransient;
	}
	public void setIsTransient(Boolean aIsTransient) {
		theIsTransient = aIsTransient;
	}
	/**
	 * Временный
	 */
	private Boolean theIsTransient;
	/**
	 * Идентификационный номер
	 */
	@Comment("Идентификационный номер")
	@Persist
	public String getIdentificationNumber() {
		return theIdentificationNumber;
	}
	public void setIdentificationNumber(String aIdentificationNumber) {
		theIdentificationNumber = aIdentificationNumber;
	}
	/**
	 * Идентификационный номер
	 */
	private String theIdentificationNumber;
}
