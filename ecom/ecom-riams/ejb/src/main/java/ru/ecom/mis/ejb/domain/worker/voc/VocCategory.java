package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник категорий
 */
@Entity
@Comment("Справочник категорий")
@Table(schema="SQLUser")
public class VocCategory extends VocIdName {
}