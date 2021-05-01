package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class ContractMedCase extends BaseEntity {
	/** Основной СМО */
	private Long mainMedCase;
	/** Счет */
	private Long account;
	/** Договор */
	private Long contract;
	/** Гарантийный документ */
	private Long guarantee;
	/** СМО */
	private Long medCase;
	/** Операция */
	private Long surgicalOperation;
	/** Анестезия */
	private Long anesthesia;
	/** Услуга по прейскуранту */
	private Long pricePosition;
	/** Услуга внутренния */
	private Long medService;
	/** PriceMedService */
	private Long priceMedService;
}