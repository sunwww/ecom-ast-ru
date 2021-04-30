package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник подтипов телефонных сообщений (инфекционное, криминальное)
 */
@Entity
@Comment("Справочник подтипов телефонных сообщений (инфекционное, криминальное)")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPhoneMessageSubType extends VocBaseEntity {
	/** Тип сообщения */
	@Comment("Тип сообщения")
	@OneToOne
	public VocPhoneMessageType getType() {return type;}
	/** Тип сообщения */
	private VocPhoneMessageType type;
}
