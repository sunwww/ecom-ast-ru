package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Место смерти - в стационаре-1, дома-2, в другом месте-3)
 * @author oegorova
 *
 */
@Entity
@Comment("Место смерти")
@Table(schema="SQLUser")
public class VocDeathPlace extends VocBaseEntity {

}
