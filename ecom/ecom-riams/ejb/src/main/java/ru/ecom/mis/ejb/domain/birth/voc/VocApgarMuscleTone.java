package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник тонуса мышц по Апгар
 * @author azviagin
 *
 */

@Comment("Справочник тонуса мышц по Апгар")
@Entity
@Table(schema="SQLUser")
public class VocApgarMuscleTone extends VocBall{

}
