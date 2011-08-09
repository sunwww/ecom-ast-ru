package ru.ecom.expomc.ejb.domain.registry;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *  Страховая компания
 */
@Entity
@Table(name="REG_IC",schema="SQLUser")
@Comment("Страховая компания")
public class RegInsuranceCompany extends VocIdNameOmcCode {


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
    /** Устарел */
//	@Comment("Устарел")
//	public Boolean getDeprecated() {
//		return theDeprecated;
//	}

//	public void setDeprecated(Boolean aDeprecated) {
//		theDeprecated = aDeprecated;
//	}

	/** Устарел */
//	private Boolean theDeprecated;
}
