package ru.ecom.mis.ejb.domain.psychiatry.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник кто передал сообщение о суициде")
@Entity
@Table(schema="SQLUser")
public class VocPsychLeftMessage extends VocBaseEntity {

}
