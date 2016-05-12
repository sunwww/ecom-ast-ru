package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник других трансфузионных жидкостей
 * @author azviagin
 *
 */

@Comment("Справочник других трансфузионных жидкостей")
@Entity
@Table(schema="SQLUser")
public class VocOtherTransfusPreparation extends VocBaseEntity{

}
