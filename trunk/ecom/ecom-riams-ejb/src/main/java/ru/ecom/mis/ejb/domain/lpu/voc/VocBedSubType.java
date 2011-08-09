package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник по типу коек (дневной, круглосуточный)
 * @author stkacheva
 *
 */
@Comment("Справочник по типу коек (дневной, круглосуточный)")
@Entity
@Table(schema="SQLUser")
public class VocBedSubType extends VocBaseEntity {

}
