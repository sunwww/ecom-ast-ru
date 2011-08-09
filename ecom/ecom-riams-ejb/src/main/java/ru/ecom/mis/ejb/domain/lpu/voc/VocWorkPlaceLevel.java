package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Уровень рабочего места
 * @author azviagin
 *
 */

@Comment("Уровень рабочего места")
@Entity
@Table(schema="SQLUser")
public class VocWorkPlaceLevel extends VocBaseEntity {

}
