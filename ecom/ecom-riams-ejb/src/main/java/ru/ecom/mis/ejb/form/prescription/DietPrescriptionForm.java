package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DietPrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Назначение на диету
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = DietPrescription.class)
@Comment("Назначение на диету")
@WebTrail(comment = "Назначение на диету", nameProperties= "diet",list="entityParentList-pres_dietPrescription.do", view="entityParentView-pres_dietPrescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/DietPrescription")
public class DietPrescriptionForm extends PrescriptionForm{
	
	/** Диета */
	@Comment("Диета")
	@Persist @Required
	public Long getDiet() {
		return theDiet;
	}

	public void setDiet(Long aDiet) {
		theDiet = aDiet;
	}

	/** Диета */
	private Long theDiet;
	
}
