package ru.ecom.mis.ejb.form.prescription;

import javax.persistence.OneToOne;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.ModePrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ModePrescription.class)
@Comment("Назначение режима")
@WebTrail(comment = "Назначение режима", nameProperties= "modePrescription",list="entityParentList-pres_modePrescription.do", view="entityParentView-pres_modePrescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/ModePrescription")
public class ModePrescriptionForm extends PrescriptionForm {
	/** Режим */
	@Comment("Режим")
	@OneToOne
	public Long getModePrescription() {return theModePrescription;}
	public void setModePrescription(Long aModePrescription) {theModePrescription = aModePrescription;}

	/** Режим */
	private Long theModePrescription;
}
