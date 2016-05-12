package ru.ecom.mis.ejb.domain.contract;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Гарантийное письмо по договору
	 */
	@Comment("Гарантийное письмо по договору")
@Entity
@Table(schema="SQLUser")
public class ContractGuaranteeLetter extends ContractGuarantee {
}
