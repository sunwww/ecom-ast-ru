package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class KinsmanForm  extends IdEntityForm {
	/** Персона */
	@Comment("Персона")
	@Persist @Required
	public Long getPerson() {
		return person;
	}

	/** Персона */
	private Long person;
	
	/** Родственник */
	@Comment("Родственник")
	@Persist @Required
	public Long getKinsman() {
		return kinsman;
	}

	/** Родственник */
	private Long kinsman;
	
	/** Родственная роль */
	@Comment("Родственная роль")
	@Persist @Required
	public Long getKinsmanRole() {
		return kinsmanRole;
	}

	/** Родственная роль */
	private Long kinsmanRole;
	
	/** Родственник инфо */
	@Comment("Родственник инфо")
	@Persist
	public String getKinsmanInfo() {
		return kinsmanInfo;
	}

	/** Родственник инфо */
	private String kinsmanInfo;
	
	/** Родственная роль инфо */
	@Comment("Родственная роль инфо")
	@Persist
	public String getKinsmanRoleInfo() {
		return kinsmanRoleInfo;
	}

	/** Родственная роль инфо */
	private String kinsmanRoleInfo;
	
	/** Информация о родственнике */
	@Comment("Информация о родственнике")
	@Persist
	public String getInfo() {
		return info;
	}

	/** Информация о родственнике */
	private String info;
}
