package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Операция договорного счета резервирования
 */
@Comment("Операция договорного счета резервирования")
@Entity
@Table(schema="SQLUser")
@UnDeletable
public class OperationReservation extends ContractAccountOperation {

}
