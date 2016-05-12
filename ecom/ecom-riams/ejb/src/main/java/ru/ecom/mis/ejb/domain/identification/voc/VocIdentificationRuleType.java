package ru.ecom.mis.ejb.domain.identification.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип правила идентификации
 * (количественное, суммовое)
 * @author azviagin
 *
 */

@Comment("Тип правила идентификации")
@Entity
@Table(schema="SQLUser")
public class VocIdentificationRuleType extends VocBaseEntity{

}
