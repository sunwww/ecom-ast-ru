package ru.ecom.mis.ejb.form.prescription.template;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
import ru.ecom.mis.ejb.form.prescription.AbstractPrescriptionListForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * 
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz = Prescription.class)
@Comment("Назначение")
@WebTrail(comment = "Назначение", nameProperties= "id", view="entitySubclassView-pres_template_prescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@Subclasses({DrugPrescriptionForm.class, DietPrescriptionForm.class, ServicePrescriptionForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/Template")
@Setter
public class PrescriptionForm extends ru.ecom.mis.ejb.form.prescription.PrescriptionForm{
	

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
