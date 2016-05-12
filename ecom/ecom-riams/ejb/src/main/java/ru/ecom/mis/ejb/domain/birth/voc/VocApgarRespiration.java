package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник дыхания по Апгар
 * @author azviagin
 *
 */

@Comment("Справочник дыхания по Апгар")
@Entity
@Table(schema="SQLUser")
public class VocApgarRespiration extends VocBall{

}
