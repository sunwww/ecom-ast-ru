package ru.ecom.mis.ejb.domain.contract;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Операция договорного счета списание
 */
@Comment("Операция договорного счета списание")
@Entity
public class OperationWriteOff extends ContractAccountOperation {

}
