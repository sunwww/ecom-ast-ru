package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник выбора ДА-НЕТ
 */

@Entity
@Table(schema="SQLUser")
public class VocYesNo extends VocIdName {
	/** nameIs */
	@Comment("nameIs")
	public String getNameIs() {
		return theNameIs;
	}

	public void setNameIs(String aNameIs) {
		theNameIs = aNameIs;
	}

	/** nameIs */
	private String theNameIs;
}
