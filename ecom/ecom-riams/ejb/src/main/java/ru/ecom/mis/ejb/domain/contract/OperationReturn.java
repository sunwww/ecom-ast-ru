package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Операция договорного счета возврата
 */
@Comment("Операция договорного счета возврата")
@Entity
@UnDeletable
public class OperationReturn extends ContractAccountOperation {

}
