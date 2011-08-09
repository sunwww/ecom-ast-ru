package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код области (нахождения СМО)
 */
@Comment("Код области (нахождения СМО)")
@Entity
@Table(name = "OMC_KODTER",schema="SQLUser")
public class OmcKodTer extends OmcAbstractVoc {
	/** ОКАТО */
	@Comment("ОКАТО")
	public String getOkato() {
		return theOkato;
	}

	public void setOkato(String aOkato) {
		theOkato = aOkato;
	}

	/** ОКАТО */
	private String theOkato;
    
}
