package ru.ecom.mis.ejb.domain.contract;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Гарантийный документ по договору
	 */
	@Comment("Гарантийный документ по договору")
@Entity
@Table(schema="SQLUser")
public abstract class ContractGuarantee extends BaseEntity{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@ManyToOne
	public MedContract getContract() {
		return theContract;
	}
	public void setContract(MedContract aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private MedContract theContract;
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@OneToOne
	public ContractPerson getContractPerson() {
		return theContractPerson;
	}
	public void setContractPerson(ContractPerson aContractPerson) {
		theContractPerson = aContractPerson;
	}
	/**
	 * Договорная персона
	 */
	private ContractPerson theContractPerson;
}
