package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы области и города для фонда
 */
@Entity
@Table(schema="SQLUser")
public class VocRayon extends VocBaseEntity {
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
