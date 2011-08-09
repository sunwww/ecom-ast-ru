package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник специальностей
 */
@Entity
@Comment("Справочник специальностей")
@Table(schema="SQLUser")
public class VocSpec extends VocIdName {
}
