package ru.ecom.mis.ejb.domain.contract;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Платежное поручение по договору
	 */
	@Comment("Платежное поручение по договору")
@Entity
@Table(schema="SQLUser")
public class ContractPaymentOrder extends ContractGuarantee{
}
