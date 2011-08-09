package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов ЛПУ
 */
@Entity
@Table(schema="SQLUser")
@Comment("Справочник типов ЛПУ")
public class VocMzDepType extends VocBaseEntity {
	/** Профиль */
	@Comment("Профиль")
	public String getProfile() {
		return theProfile;
	}

	public void setProfile(String aProfile) {
		theProfile = aProfile;
	}
	
	/** DepType */
	@Comment("DepType")
	public String getDepType() {
		return theDepType;
	}

	public void setDepType(String aDepType) {
		theDepType = aDepType;
	}

	/** DepType */
	private String theDepType;

	/** Профиль */
	private String theProfile;

}
