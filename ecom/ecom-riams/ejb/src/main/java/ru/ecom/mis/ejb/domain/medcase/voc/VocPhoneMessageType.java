package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов телефонных сообщений (инфекционное, криминальное)
 */
@Entity
@Comment("Справочник типов телефонных сообщений (инфекционное, криминальное)")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPhoneMessageType extends VocBaseEntity {
	/** Инфекционный тип */
	private Boolean isInfectious;
	/** Криминальное тип */
	private Boolean isCriminal;
}
