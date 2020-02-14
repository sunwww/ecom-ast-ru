package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocGuaranteeKindHelp;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
	/**
	 * Гарантийный документ по договору
	 */
	@Comment("Гарантийный документ по договору")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"contract"})
	})
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
	
	/** Лимит денег */
	@Comment("Лимит денег")
	public BigDecimal getLimitMoney() {return theLimitMoney;}
	public void setLimitMoney(BigDecimal aLimitMoney) {theLimitMoney = aLimitMoney;}

	/** Без лимита */
	@Comment("Без лимита")
	public Boolean getIsNoLimit() {return theIsNoLimit;}
	public void setIsNoLimit(Boolean aIsNoLimit) {theIsNoLimit = aIsNoLimit;}

	/** Без лимита */
	private Boolean theIsNoLimit;
	/** Лимит денег */
	private BigDecimal theLimitMoney;
	
	/** Номер */
	@Comment("Номер")
	public String getNumberDoc() {return theNumberDoc;}
	public void setNumberDoc(String aNumberDoc) {theNumberDoc = aNumberDoc;}

	/** Номер */
	private String theNumberDoc;
	
	/** Дата выдачи */
	@Comment("Дата выдачи")
	public Date getIssueDate() {return theIssueDate;}
	public void setIssueDate(Date aIssueDate) {theIssueDate = aIssueDate;}

	
	/** Дата действия */
	@Comment("Дата действия")
	public Date getActionDate() {return theActionDate;}
	public void setActionDate(Date aActionDate) {theActionDate = aActionDate;}

	/** Дата действия */
	private Date theActionDate;
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getActionDateTo() {return theActionDateTo;}
	public void setActionDateTo(Date aActionDateTo) {theActionDateTo = aActionDateTo;}

	/** Дата окончания действия */
	private Date theActionDateTo;
	/** Дата выдачи */
	private Date theIssueDate;
	
	/** Вид медицинской помощи */
	@Comment("Вид медицинской помощи")
	@OneToOne
	public VocGuaranteeKindHelp getKindHelp() {
		return theKindHelp;
	}

	public void setKindHelp(VocGuaranteeKindHelp aKindHelp) {
		theKindHelp = aKindHelp;
	}

	/** Вид медицинской помощи */
	private VocGuaranteeKindHelp theKindHelp;
	
	/** Палата */
	@Comment("Палата")
	@OneToOne
	public VocRoomType getRoomType() {
		return theRoomType;
	}

	public void setRoomType(VocRoomType aRoomType) {
		theRoomType = aRoomType;
	}

	/** Палата */
	private VocRoomType theRoomType;
}
