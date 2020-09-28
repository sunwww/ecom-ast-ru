package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Операция договорного счета начисление
 */
@Comment("Операция договорного счета начисление")
@Entity
@UnDeletable
public class OperationAccrual extends ContractAccountOperation {

}
