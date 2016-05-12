package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник тяжести состояния по телеф. сообщениям")
@Table(schema="SQLUser")
public class VocPhoneMessageState extends VocBaseEntity{

}
