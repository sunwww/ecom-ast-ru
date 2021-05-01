package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
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
	@Getter
	@Setter
public abstract class ContractGuarantee extends BaseEntity{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@ManyToOne
	public MedContract getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private MedContract contract;
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@OneToOne
	public ContractPerson getContractPerson() {
		return contractPerson;
	}
	/**
	 * Договорная персона
	 */
	private ContractPerson contractPerson;
	
	/** Без лимита */
	private Boolean isNoLimit;
	/** Лимит денег */
	private BigDecimal limitMoney;
	
	/** Номер */
	private String numberDoc;
	
	/** Дата действия */
	private Date actionDate;
	
	/** Дата окончания действия */
	private Date actionDateTo;
	/** Дата выдачи */
	private Date issueDate;
	
	/** Вид медицинской помощи */
	@Comment("Вид медицинской помощи")
	@OneToOne
	public VocGuaranteeKindHelp getKindHelp() {
		return kindHelp;
	}

	/** Вид медицинской помощи */
	private VocGuaranteeKindHelp kindHelp;
	
	/** Палата */
	@Comment("Палата")
	@OneToOne
	public VocRoomType getRoomType() {
		return roomType;
	}

	/** Палата */
	private VocRoomType roomType;
}
