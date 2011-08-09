package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Характер сердцебиения плода
 * @author azviagin
 *
 */

@Comment("Характер сердцебиения плода")
@Entity
@Table(schema="SQLUser")
public class VocFetusPalpitationNature extends VocBaseEntity{

}
