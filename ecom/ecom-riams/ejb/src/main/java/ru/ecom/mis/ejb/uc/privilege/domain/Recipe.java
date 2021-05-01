package ru.ecom.mis.ejb.uc.privilege.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Рецепт
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
abstract public class Recipe extends BaseEntity {
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return workFunction;
	}

	/** Рабочая функция */
	private WorkFunction workFunction;

	/** МКБ10 */
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {
		return idc10;
	}

	/** Лекарство */
	@Comment("Лекарство")
	@OneToOne
	public VocDrugClassify getDrugClassify() {
		return drugClassify;
	}

	/** Процент оплаты */
	private Long payPercent;
	/** Длительность приема */
	private String useDuration;
		/** Количество приемов в день */
	private Integer dayTakingAmount;
	/** Количество лекарства на прием */
	private BigDecimal takingDoseAmount;
	/** Количество ЛС по рецепту */
	private BigDecimal drugQuantity;
	/** МКБ10 */
	private VocIdc10 idc10;
	/** Дата выписки */
	private Date recipeDate;
	/** Номер рецепта */
	private String recipeNumber;
	/** Лекарство */
	private VocDrugClassify drugClassify;
}
