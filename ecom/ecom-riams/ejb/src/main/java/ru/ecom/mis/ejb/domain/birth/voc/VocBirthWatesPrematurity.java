package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник преждевременности отхождения вод
 * @author azviagin
 *
 */

@Comment("Справочник преждевременности отхождения вод")
@Entity
@Table(schema="SQLUser")
public class VocBirthWatesPrematurity extends VocBaseEntity{

}
