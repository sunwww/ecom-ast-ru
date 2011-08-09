package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник сердцебиения по Апгар
 * @author azviagin
 *
 */

@Comment("Справочник сердцебиения по Апгар")
@Entity
@Table(schema="SQLUser")
public class VocApgarPalpitation extends VocBall{

}
