package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип телефона
 * @author azviagin
 *
 */
@Comment("Тип телефона")
@Entity
@Table(schema="SQLUser")
public class VocPhoneType extends VocBaseEntity{

}
