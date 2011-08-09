package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Статус документа
 */
@Entity
@Comment("Статус документа")
@Table(schema="SQLUser")
public class VocStatus extends VocIdName {
}
