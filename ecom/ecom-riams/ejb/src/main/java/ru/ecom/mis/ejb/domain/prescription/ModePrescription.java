package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.prescription.voc.VocModePrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Comment("Назначение режима")
@Entity
@Getter
@Setter
public class ModePrescription extends Prescription {
	/** Режим */
	@Comment("Режим")
	@OneToOne
	public VocModePrescription getModePrescription() {return modePrescription;}

	/** Режим */
	private VocModePrescription modePrescription;
}
