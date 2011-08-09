package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип рабочего места	
 * @author azviagin
 *
 */

@Comment("Тип рабочего места")
@Entity
@Table(schema="SQLUser")
public class VocPostType extends VocBaseEntity{

}
