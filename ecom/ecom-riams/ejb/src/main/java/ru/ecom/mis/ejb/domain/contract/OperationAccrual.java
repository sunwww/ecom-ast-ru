package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Операция договорного счета начисление
 */
@Comment("Операция договорного счета начисление")
@Entity
@Table(schema="SQLUser")
@UnDeletable
public class OperationAccrual extends ContractAccountOperation {

}
