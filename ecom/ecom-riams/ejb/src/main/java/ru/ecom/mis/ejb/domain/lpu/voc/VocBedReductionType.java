package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип свертывания койки
 * @author azviagin
 *
 */

@Comment("Тип свертывания койки")
@Entity
@Table(schema="SQLUser")
public class VocBedReductionType extends VocBaseEntity{

}
