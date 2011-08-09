package ru.ecom.document.ejb.domain.certificate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.medcase.hospital.BirthCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Свидетельство о рождении
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class BirthCertificate extends Certificate {
	
	/** Случай рождения */
	@Comment("Случай рождения")
	@ManyToOne
	public BirthCase getBirthCase() {
		return theBirthCase;
	}

	public void setBirthCase(BirthCase aBirthCase) {
		theBirthCase = aBirthCase;
	}

	/** Случай рождения */
	private BirthCase theBirthCase;

}
