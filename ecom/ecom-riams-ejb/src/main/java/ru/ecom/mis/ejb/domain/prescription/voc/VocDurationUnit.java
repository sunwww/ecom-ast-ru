package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник длительности использования
 * @author azviagin
 *
 */

@Comment("Справочник длительности использования")
@Entity
@Table(schema="SQLUser")
public class VocDurationUnit extends VocBaseEntity{

}
