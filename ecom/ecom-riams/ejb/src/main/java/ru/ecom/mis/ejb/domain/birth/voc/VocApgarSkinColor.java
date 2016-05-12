package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник окраски кожи по Апгар
 * @author azviagin
 *
 */

@Comment("Справочник окраски кожи по Апгар")
@Entity
@Table(schema="SQLUser")
public class VocApgarSkinColor extends VocBall{

}
