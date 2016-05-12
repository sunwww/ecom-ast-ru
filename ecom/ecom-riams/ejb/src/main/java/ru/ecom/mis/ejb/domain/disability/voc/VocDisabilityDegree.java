package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Степень ограничения трудоспособности
 * (трудоспособен, временное ограничение, стойкое ограничение)
 * @author azviagin
 *
 */
@Comment("Степень ограничения трудоспособности")
@Entity
@Table(schema="SQLUser")
public class VocDisabilityDegree extends VocBaseEntity{

}
