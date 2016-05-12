package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип электронной почты
 * (домашняя, служебная)
 * @author azviagin
 *
 */
@Comment("Тип электронной почты")
@Entity
@Table(schema="SQLUser")
public class VocEMailType extends VocBaseEntity{

}
