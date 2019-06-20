package ru.ecom.mis.ejb.domain.expert.voc;/**
 * Created by Milamesher on 20.06.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник причин изменения оценки в карте по 203 приказу
 */
@Comment("Справочник причин изменения оценки в карте по 203 приказу")
@Entity
@Table(schema="SQLUser")
public class VocQualityEstimationChangeReason extends VocBaseEntity {
}