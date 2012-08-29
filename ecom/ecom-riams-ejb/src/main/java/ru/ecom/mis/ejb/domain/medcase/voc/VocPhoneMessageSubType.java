package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник подтипов телефонных сообщений (инфекционное, криминальное)
 */
@Entity
@Comment("Справочник подтипов телефонных сообщений (инфекционное, криминальное)")
@Table(schema="SQLUser")
public class VocPhoneMessageSubType extends VocBaseEntity {
	/** Тип сообщения */
	@Comment("Тип сообщения")
	@OneToOne
	public VocPhoneMessageType getType() {return theType;}
	public void setType(VocPhoneMessageType aType) {theType = aType;}

	/** Тип сообщения */
	private VocPhoneMessageType theType;
}
