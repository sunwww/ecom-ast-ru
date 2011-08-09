package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник квалификаций
 */
@Entity
@Comment("Справочник квалификаций")
@Table(schema="SQLUser")
public class VocQualif extends VocIdName {
}
