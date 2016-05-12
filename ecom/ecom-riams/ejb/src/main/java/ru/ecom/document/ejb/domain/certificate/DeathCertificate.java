package ru.ecom.document.ejb.domain.certificate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Свидетельство о смерти
 * @author oegorova
 *
 */
@Entity
@Table(schema="SQLUser")
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

	/** Случай смерти */
	private DeathCase theDeathCase;
	
}
