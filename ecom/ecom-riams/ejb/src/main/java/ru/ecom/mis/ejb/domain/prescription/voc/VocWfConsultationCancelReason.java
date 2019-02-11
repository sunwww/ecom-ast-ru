package ru.ecom.mis.ejb.domain.prescription.voc;

/**
 * Справочник причин отмены консультаций
 * @author Milamesher
 *
 */
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Справочник причин отмены консультаций")
@Entity
@Table(schema="SQLUser")
public class VocWfConsultationCancelReason extends VocBaseEntity {
}