package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов порядка использования
 * @author azviagin
 *
 */

@Comment("Справочник типов порядка использования")
@Entity
@Table(schema="SQLUser")
public class VocPrescriptOrderType extends VocBaseEntity{

}
