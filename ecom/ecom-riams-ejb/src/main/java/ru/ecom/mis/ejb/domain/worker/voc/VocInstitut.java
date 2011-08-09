package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 Справочник наименований основных образовательных институтов
 */
@Entity
@Comment("Справочник наименований основных образовательных институтов")
@Table(schema="SQLUser")
public class VocInstitut extends VocIdName {
}