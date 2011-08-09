package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник видов педикулёза
 * @author stkacheva
 */
@Entity
@Comment("Справочник видов педикулёза")
@Table(schema="SQLUser")
public class VocPediculosis extends VocBaseEntity{

}
