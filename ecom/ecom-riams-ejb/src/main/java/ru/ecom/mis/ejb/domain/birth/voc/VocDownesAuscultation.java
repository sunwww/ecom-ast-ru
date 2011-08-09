package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник аускультации по Downes
 * @author azviagin
 *
 */

@Comment("Справочник аускультации по Downes")
@Entity
@Table(schema="SQLUser")
public class VocDownesAuscultation extends VocBall{

}
