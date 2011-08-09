package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник совместительства
 * (основная работа, внутренний совместитель, внешний совместитель)
 */
@Entity
@Comment("Справочник совместительства")
@Table(schema="SQLUser")
public class VocCombo extends VocIdName {
}
