package ru.ecom.mis.ejb.domain.disability.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Причина аннулирования ЭЛН
 * @author milamesher
 *
 */
@Comment("Причина аннулирования ЭЛН")
@Entity
@Table(schema="SQLUser")
public class VocAnnulReason extends VocBaseEntity {
}