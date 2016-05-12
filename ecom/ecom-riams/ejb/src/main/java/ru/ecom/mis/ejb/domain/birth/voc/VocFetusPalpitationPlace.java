package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Место сердцебиения плода
 * @author azviagin
 *
 */

@Comment("Место сердцебиения плода")
@Entity
@Table(schema="SQLUser")
public class VocFetusPalpitationPlace extends VocBaseEntity{

}
