package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник рефлексов по Апгар
 * @author azviagin
 *
 */

@Comment("Справочник рефлексов по Апгар")
@Entity
@Table(schema="SQLUser")
public class VocApgarReflexes extends VocBall{

}
