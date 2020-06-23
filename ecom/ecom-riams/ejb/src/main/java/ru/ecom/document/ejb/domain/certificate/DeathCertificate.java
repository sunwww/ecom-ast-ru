package ru.ecom.document.ejb.domain.certificate;

import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Свидетельство о смерти
 * @author oegorova
 *
 */
@Entity
public class DeathCertificate  extends Certificate {

	/** Случай смерти */
	@Comment("Случай смерти")
	@ManyToOne
	public DeathCase getDeathCase() {
		return theDeathCase;
	}
	public void setDeathCase(DeathCase aDeathCase) {
		theDeathCase = aDeathCase;
	}
	private DeathCase theDeathCase;
}