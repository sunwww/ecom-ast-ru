package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник отделения плаценты
 * @author azviagin
 *
 */

@Comment("Справочник отделения плаценты")
@Entity
@Table(schema="SQLUser")
public class VocPlacentaSeparation extends VocBaseEntity{

}
