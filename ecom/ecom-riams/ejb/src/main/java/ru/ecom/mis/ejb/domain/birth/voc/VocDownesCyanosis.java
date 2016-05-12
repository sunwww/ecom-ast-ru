package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник цианоза по Downes
 * @author azviagin
 *
 */

@Comment("Справочник цианоза по Downes")
@Entity
@Table(schema="SQLUser")
public class VocDownesCyanosis extends VocBall{

}
