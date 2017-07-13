package ru.ecom.mis.ejb.domain.directory.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Уровень рабочего места
 * @author rkurbanov
 */

@Comment("Уровень рабочего места")
@Entity
@Table(schema="SQLUser")
public class VocBuildingLevel extends VocBaseEntity {

}
