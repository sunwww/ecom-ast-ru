package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.contract.voc.VocAccountOperation;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
/**
 * Операция договорного счета
 */
@Comment("Операция договорного счета")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"account"})
})
@EntityListeners(DeleteListener.class)
@UnDeletable
@Getter
@Setter
public class ContractAccountOperation extends BaseEntity{

	/** Признак удаленной записи */
	private Boolean isDeleted ;

	/** Договорной счет */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {return account;}
	private ContractAccount account;

	/** Тип операции (не используется) */
	@Comment("Тип операции (не используется)")
	@OneToOne
	public VocAccountOperation getType() {return type;}
	private VocAccountOperation type;

	/**
	 * Дата
	 */
	private Date operationDate;
	/**
	 * Время операции
	 */
	private Time operationTime;
	/**
	 * Стоимость
	 */
	private BigDecimal cost;

	/**
	 * Отменившая операция
	 */
	@Comment("Отменившая операция")
	@OneToOne
	public ContractAccountOperation getRepealOperation() {
		return repealOperation;
	}
	/**
	 * Отменившая операция
	 */
	private ContractAccountOperation repealOperation;
	/**
	 * Оператор
	 */
	@Comment("Оператор")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return workFunction;
	}
	/**
	 * Оператор
	 */
	private WorkFunction workFunction;
	/** Скидка */
	private BigDecimal discount;
	/** Пользователь, последний изменивший запись */
	private String editUsername;
	/** Время, последнего изменения */
	private Time editTime;
	/** Дата последнего изменения */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	/** Оплата терминалом */
	private Boolean isPaymentTerminal;
	/** Номер телефона для чека */
	private String customerPhone ;
}
