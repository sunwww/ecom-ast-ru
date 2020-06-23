package ru.ecom.mis.ejb.domain.contract;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
	/**
	 * Гарантийное письмо по договору
	 */
	@Comment("Гарантийное письмо по договору")
@Entity
public class ContractGuaranteeLetter extends ContractGuarantee {
}
