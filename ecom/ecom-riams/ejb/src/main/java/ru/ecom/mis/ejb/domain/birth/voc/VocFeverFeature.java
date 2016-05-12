package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник особенностей повышения температуры тела
 * @author azviagin
 *
 */

@Comment("Справочник особенностей повышения температуры тела")
@Entity
@Table(schema="SQLUser")
public class VocFeverFeature extends VocBaseEntity{

}
