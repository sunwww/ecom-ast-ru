package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Операция договорного счета резервирования
 */
@Comment("Операция договорного счета резервирования")
@Entity
@UnDeletable
public class OperationReservation extends ContractAccountOperation {

}
