package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Способ переливания трансфузионных сред
 * @author azviagin
 *
 */

@Comment("Способ переливания трансфузионных сред")
@Entity
@Table(schema="SQLUser")
public class VocTransfusionMethod extends VocBaseEntity{

}
