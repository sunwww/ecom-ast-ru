package ru.ecom.mis.ejb.domain.prescription;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.prescription.voc.VocModePrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Назначение режима")
@Entity
@Table(schema="SQLUser")
public class ModePrescription extends Prescription {
	/** Режим */
	@Comment("Режим")
	@OneToOne
	public VocModePrescription getModePrescription() {return theModePrescription;}
	public void setModePrescription(VocModePrescription aModePrescription) {theModePrescription = aModePrescription;}

	/** Режим */
	private VocModePrescription theModePrescription;
}
