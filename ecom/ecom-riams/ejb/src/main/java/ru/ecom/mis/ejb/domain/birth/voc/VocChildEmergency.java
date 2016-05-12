package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocChildEmergency extends VocBaseEntity{
	/** Роды */
	@Comment("Роды")
	public String getChildBirthCode() {
		return theChildBirthCode;
	}

	public void setChildBirthCode(String aChildBirthCode) {
		theChildBirthCode = aChildBirthCode;
	}

	/** Роды */
	private String theChildBirthCode;
}
