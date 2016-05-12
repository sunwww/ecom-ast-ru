package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Операция договорного счета списание
 */
@Comment("Операция договорного счета списание")
@Entity
@Table(schema="SQLUser")
public class OperationWriteOff extends ContractAccountOperation {

}
