package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.ModePrescription;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ModePrescription.class)
@Comment("Назначение режима")
@WebTrail(comment = "Назначение режима", nameProperties= "modePrescription",list="entityParentList-pres_modePrescription.do", view="entityParentView-pres_modePrescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/ModePrescription")
@Setter
public class ModePrescriptionForm extends PrescriptionForm {
	/** Режим */
	@Comment("Режим")
	@Persist @Required
	public Long getModePrescription() {return modePrescription;}

	/** Режим */
	private Long modePrescription;
}
