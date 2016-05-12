package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Инвалидность
 * @author azviagin
 *
 */
@Comment("Инвалидность")
@Entity
@Table(schema="SQLUser")
public class VocInvalidity extends VocBaseEntity{

}
