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

}
