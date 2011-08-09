package ru.ecom.address.ejb.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Район адреса
 * @author azviagin
 *
 */
@Comment("Район адреса")
@Entity
@AIndexes({
	@AIndex(unique=true, properties={"kladrCode"})
	, @AIndex(unique=true, properties={"okatoCode"})
	, @AIndex(unique=true, properties={"unoCode"})
})
@Table(schema="SQLUser")
public class AddressRegion extends VocBaseEntity{
	
	/** Код КЛАДР */
	@Comment("Код КЛАДР")
	public String getKladrCode() {
		return theKladrCode;
	}

	public void setKladrCode(String aKladrCode) {
		theKladrCode = aKladrCode;
	}

	/** Код КЛАДР */
	private String theKladrCode;
	
	/** Код ОКАТО */
	@Comment("Код ОКАТО")
	public String getOkatoCode() {
		return theOkatoCode;
	}

	public void setOkatoCode(String aOkatoCode) {
		theOkatoCode = aOkatoCode;
	}

	/** Код ОКАТО */
	private String theOkatoCode;
	
	/** Код УНО */
	@Comment("Код УНО")
	public String getUnoCode() {
		return theUnoCode;
	}

	public void setUnoCode(String aUnoCode) {
		theUnoCode = aUnoCode;
	}

	/** Код УНО */
	private String theUnoCode;

}
