package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Подчиненность
 *
 */
@Entity
@Comment("Подчиненность")
@Table(schema="SQLUser")
public class VocSubordination extends VocBaseEntity{

}
