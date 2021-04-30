package ru.ecom.document.ejb.domain.certificate;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class DeathCertificate  extends Certificate {

	/** Случай смерти */
	@Comment("Случай смерти")
	@ManyToOne
	public DeathCase getDeathCase() {
		return deathCase;
	}
	private DeathCase deathCase;
}