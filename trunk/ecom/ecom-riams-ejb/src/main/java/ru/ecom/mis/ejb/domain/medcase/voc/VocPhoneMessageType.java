package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов телефонных сообщений (инфекционное, криминальное)
 */
@Entity
@Comment("Справочник типов телефонных сообщений (инфекционное, криминальное)")
@Table(schema="SQLUser")
public class VocPhoneMessageType extends VocBaseEntity {
	/** Криминальное тип */
	@Comment("Криминальное тип")
	public Boolean getIsCriminal() {
		return theIsCriminal;
	}

	public void setIsCriminal(Boolean aIsCriminal) {
		theIsCriminal = aIsCriminal;
	}
	/** Инфекционный тип */
	@Comment("Инфекционный тип")
	public Boolean getIsInfectious() {
		return theIsInfectious;
	}

	public void setIsInfectious(Boolean aIsInfectious) {
		theIsInfectious = aIsInfectious;
	}

	/** Инфекционный тип */
	private Boolean theIsInfectious;
	/** Криминальное тип */
	private Boolean theIsCriminal;
}
