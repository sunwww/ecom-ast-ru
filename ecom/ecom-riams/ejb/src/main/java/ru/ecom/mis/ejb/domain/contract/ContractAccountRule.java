package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
	/**
	 * Правило договорного счета
	 */
	@Comment("Правило договорного счета")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"contract"})
		,@AIndex(properties = {"servedPerson"})
	})
	@Getter
	@Setter
public class ContractAccountRule extends BaseEntity{
	/**
	 * Скидка
	 */
	private BigDecimal discount;
	/**
	 * Предел платежей
	 */
	private BigDecimal paymentLimit;
	/**
	 * Предел блокирования платежей
	 */
	private BigDecimal blockLimit;
	/**
	 * Автоматическое создание счета
	 */
	private Boolean autocreateAccount;
	/**
	 * Дата начала действия
	 */
	private Date dateFrom;
	/**
	 * Дата окончания действия
	 */
	private Date dateTo;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@OneToOne
	public MedContract getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private MedContract contract;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {
		return servedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private ServedPerson servedPerson;
}
