package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


@Entity
public class VocIllnesPrimary extends VocBaseEntity {
	/** Первичность */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {
		return thePrimary;
	}

	public void setPrimary(VocPrimaryDiagnosis aPrimary) {
		thePrimary = aPrimary;
	}
	
	/** Острота */
	@Comment("Острота")
	@OneToOne
	public VocAcuityDiagnosis getIllnesType() {
		return theIllnesType;
	}

	public void setIllnesType(VocAcuityDiagnosis aIllnesType) {
		theIllnesType = aIllnesType;
	}

	/** Острота */
	private VocAcuityDiagnosis theIllnesType;
	/** Первичность */
	private VocPrimaryDiagnosis thePrimary;

}
