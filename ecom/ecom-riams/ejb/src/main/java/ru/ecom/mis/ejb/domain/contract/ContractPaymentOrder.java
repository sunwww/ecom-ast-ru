package ru.ecom.mis.ejb.domain.contract;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
	/**
	 * Платежное поручение по договору
	 */
	@Comment("Платежное поручение по договору")
@Entity
public class ContractPaymentOrder extends ContractGuarantee {
}
