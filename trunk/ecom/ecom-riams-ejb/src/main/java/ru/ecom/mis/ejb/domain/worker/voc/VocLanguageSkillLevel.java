package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Уровень знания языка
 * @author azviagin
 *
 */
@Comment("Уровень знания языка")
@Entity
@Table(schema="SQLUser")
public class VocLanguageSkillLevel extends VocBaseEntity{

}
