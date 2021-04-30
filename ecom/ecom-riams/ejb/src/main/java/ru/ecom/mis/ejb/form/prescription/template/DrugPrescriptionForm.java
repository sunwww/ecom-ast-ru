package ru.ecom.mis.ejb.form.prescription.template;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
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
@EntityFormPersistance(clazz = DrugPrescription.class)
@Comment("Назначение на лекарства")
@WebTrail(comment = "Назначение на лекарства", nameProperties= "drug", view="entityParentView-pres_template_drugPrescription.do")
@Parent(property="prescriptionList", parentForm=PrescriptListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/Template/DrugPrescription")
@Setter
public class DrugPrescriptionForm extends ru.ecom.mis.ejb.form.prescription.DrugPrescriptionForm{
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist 
	@DateString @DoDateString
	public String getPlanStartDate() {return planStartDate;}

	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString 
	public String getPlanStartTime() {return planStartTime;}

	/** Назначивший */
	@Comment("Назначивший")
	@Persist 
	public Long getPrescriptSpecial() {return prescriptSpecial;}

	/** Назначивший */
	private Long prescriptSpecial;
	/** Плановая дата начала */
	private String planStartDate;
	/** Плановое время начала */
	private String planStartTime;	
}
