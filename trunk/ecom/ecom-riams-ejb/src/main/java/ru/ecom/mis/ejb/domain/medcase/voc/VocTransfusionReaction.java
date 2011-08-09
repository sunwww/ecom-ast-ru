package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник трансфузионных реакций
 * @author azviagin
 *
 */
@Comment("Справочник трансфузионных реакций")
@Entity
@Table(schema="SQLUser")
public class VocTransfusionReaction extends VocBaseEntity{

}
