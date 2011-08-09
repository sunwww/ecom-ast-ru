package ru.ecom.mis.ejb.domain.equipment.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Марка оборудования
 */
@Entity
@Comment("Марка оборудования")
@Table(schema="SQLUser")
public class VocMarka extends VocIdName {
}
