package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник организаций куда происходят звонки по телеф. сообщениям")
@Table(schema="SQLUser")
public class VocPhoneMessageOrganization extends VocBaseEntity {
	/** Телефон */
	@Comment("Телефон")
	public String getPhoneNumber() {
		return thePhoneNumber;
	}

	public void setPhoneNumber(String aPhoneNumber) {
		thePhoneNumber = aPhoneNumber;
	}

	/** Телефон */
	private String thePhoneNumber;
}
