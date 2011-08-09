package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник причин увольнения
 */
@Entity
@Comment("Справочник причин увольнения")
@Table(schema="SQLUser")
public class VocLeave extends VocIdName {
}
