package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник причин отмены назначений
 * @author azviagin
 *
 */

@Comment("Справочник причин отмены назначений")
@Entity
@Table(schema="SQLUser")
public class VocPrescriptCancelReason extends VocBaseEntity{

}
