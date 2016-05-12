package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник затруднения дыхания по Downes
 * @author azviagin
 *
 */

@Comment("Справочник затруднения дыхания по Downes")
@Entity
@Table(schema="SQLUser")
public class VocDownesDifExhalation extends VocBall{

}
