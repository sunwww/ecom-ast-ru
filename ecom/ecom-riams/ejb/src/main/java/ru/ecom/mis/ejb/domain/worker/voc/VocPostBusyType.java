package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип занятия должности
 * @author azviagin
 *
 */
@Comment("Тип занятия должности")
@Entity
@Table(schema="SQLUser")
public class VocPostBusyType extends VocBaseEntity{

}
