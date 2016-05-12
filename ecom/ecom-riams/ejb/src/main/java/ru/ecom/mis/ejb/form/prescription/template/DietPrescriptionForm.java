package ru.ecom.mis.ejb.form.prescription.template;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DietPrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = DietPrescription.class)
@Comment("Назначение на диету")
@WebTrail(comment = "Назначение на диету", nameProperties= "id", view="entityParentView-pres_template_dietPrescription.do")
@Parent(property="prescriptionList", parentForm=PrescriptListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/Template/DietPrescription")
public class DietPrescriptionForm extends ru.ecom.mis.ejb.form.prescription.DietPrescriptionForm {
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist 
	@DateString @DoDateString
	public String getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(String aPlanStartDate) {thePlanStartDate = aPlanStartDate;}
	
	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString 
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}
	
	/** Назначивший */
	@Comment("Назначивший")
	@Persist 
	public Long getPrescriptSpecial() {return thePrescriptSpecial;}
	public void setPrescriptSpecial(Long aPrescriptor) {thePrescriptSpecial = aPrescriptor;	}

	/** Назначивший */
	private Long thePrescriptSpecial;
	/** Плановая дата начала */
	private String thePlanStartDate;
	/** Плановое время начала */
	private String thePlanStartTime;	
}
