package ru.ecom.document.ejb.domain.certificate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Родовый сертификат
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
public class ConfinementCertificate extends Certificate {
	@Transient
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("серия ").append(getSeries()).append(" номер ").append(getNumber()) ;
		return ret.toString() ;
	}
}
