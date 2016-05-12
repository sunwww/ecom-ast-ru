package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Основание выдачи сертификата
 */
@Entity
@Comment("Основание выдачи сертификата")
@Table(schema="SQLUser")
public class VocCertificateIssueBase extends VocIdName {
}
