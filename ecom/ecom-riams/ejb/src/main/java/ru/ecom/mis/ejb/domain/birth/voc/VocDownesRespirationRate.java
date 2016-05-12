package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник частоты дыхательных движений по Downes
 * @author azviagin
 *
 */

@Comment("Справочник частоты дыхательных движений по Downes")
@Entity
@Table(schema="SQLUser")
public class VocDownesRespirationRate extends VocBall{

}
