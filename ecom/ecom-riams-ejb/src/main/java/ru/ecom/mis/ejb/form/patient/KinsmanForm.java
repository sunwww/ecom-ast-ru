package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Родственник
 */
@EntityForm
@EntityFormPersistance(clazz = Kinsman.class)
@Comment("Родственник")
@WebTrail(comment = "Родственник", nameProperties = "id", view = "entityView-mis_kinsman.do")
@Parent(property = "person", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/Kinsman")
public class KinsmanForm  extends IdEntityForm {
	/** Персона */
	@Comment("Персона")
	@Persist @Required
	public Long getPerson() {
		return thePerson;
	}
	

	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}

	/** Персона */
	private Long thePerson;
	
	/** Родственник */
	@Comment("Родственник")
	@Persist @Required
	public Long getKinsman() {
		return theKinsman;
	}

	public void setKinsman(Long aKinsman) {
		theKinsman = aKinsman;
	}

	/** Родственник */
	private Long theKinsman;
	
	/** Родственная роль */
	@Comment("Родственная роль")
	@Persist @Required
	public Long getKinsmanRole() {
		return theKinsmanRole;
	}

	public void setKinsmanRole(Long aKinsmanRole) {
		theKinsmanRole = aKinsmanRole;
	}

	/** Родственная роль */
	private Long theKinsmanRole;
	
	/** Родственник инфо */
	@Comment("Родственник инфо")
	@Persist
	public String getKinsmanInfo() {
		return theKinsmanInfo;
	}

	public void setKinsmanInfo(String aKinsmanInfo) {
		theKinsmanInfo = aKinsmanInfo;
	}

	/** Родственник инфо */
	private String theKinsmanInfo;
	
	/** Родственная роль инфо */
	@Comment("Родственная роль инфо")
	@Persist
	public String getKinsmanRoleInfo() {
		return theKinsmanRoleInfo;
	}

	public void setKinsmanRoleInfo(String aKinsmanRoleInfo) {
		theKinsmanRoleInfo = aKinsmanRoleInfo;
	}

	/** Родственная роль инфо */
	private String theKinsmanRoleInfo;
	
	/** Информация о родственнике */
	@Comment("Информация о родственнике")
	@Persist
	public String getInfo() {
		return theInfo;
	}

	public void setInfo(String aInfo) {
		theInfo = aInfo;
	}

	/** Информация о родственнике */
	private String theInfo;
}
