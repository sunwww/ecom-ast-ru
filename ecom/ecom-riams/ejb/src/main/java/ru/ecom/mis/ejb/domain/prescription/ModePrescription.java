package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.mis.ejb.domain.prescription.voc.VocModePrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Comment("Назначение режима")
@Entity
public class ModePrescription extends Prescription {
	/** Режим */
	@Comment("Режим")
	@OneToOne
	public VocModePrescription getModePrescription() {return theModePrescription;}
	public void setModePrescription(VocModePrescription aModePrescription) {theModePrescription = aModePrescription;}

	/** Режим */
	private VocModePrescription theModePrescription;
}
