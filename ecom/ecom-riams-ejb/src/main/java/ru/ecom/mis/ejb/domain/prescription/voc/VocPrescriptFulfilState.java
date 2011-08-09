package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник состояний завершения назначения
 * @author azviagin
 *
 */

@Comment("Справочник состояний завершения назначения")
@Entity
@Table(schema="SQLUser")
public class VocPrescriptFulfilState extends VocBaseEntity{

}
