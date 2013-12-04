package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Прикрепление по ведомству
 */
@EntityForm
@EntityFormPersistance(clazz = LpuAttachedByDepartment.class)
@Comment("Специальное прикрепление")
@WebTrail(comment = "Специальное прикрепление", nameProperties = "info", view = "entityView-mis_lpuAttachedByDepartment.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/AttachedByDepartment")
public class LpuAttachedByDepartmentForm extends IdEntityForm {

	/** Полное имя ЛПУ */
	@Comment("Полное имя ЛПУ")
	@Persist
	public String getLpuFullname() {
		return theLpuFullname;
	}

	public void setLpuFullname(String aLpuFullname) {
		theLpuFullname = aLpuFullname;
	}

	/** ПОлное имя ЛПУ */
	private String theLpuFullname;
	
	public String getInfo() {
		return theLpuFullname ;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** Участок */
	@Comment("Участок")
	@Persist
	public Long getArea() {
		return theArea;
	}

	public void setArea(Long aArea) {
		theArea = aArea;
	}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {
		return thePatient;
	}

	public void setPatient(Long aPatient) {
		thePatient = aPatient;
	}

	/** Пациент */
	private Long thePatient;
	/** Участок */
	private Long theArea;
	/** ЛПУ */
	private Long theLpu;
}