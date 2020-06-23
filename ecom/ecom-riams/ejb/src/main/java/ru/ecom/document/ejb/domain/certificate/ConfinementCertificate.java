package ru.ecom.document.ejb.domain.certificate;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Родовый сертификат
 * @author stkacheva
 *
 */
@Entity
public class ConfinementCertificate extends Certificate {
	@Transient
	public String getInformation() {
		return "серия " + getSeries() + " номер " + getNumber();
	}
}
