package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов оценок качества
 */
@Comment("Справочник типов оценок качества")
@Entity
@Table(schema="SQLUser")
public class VocQualityEstimationCritType extends VocBaseEntity{
}
