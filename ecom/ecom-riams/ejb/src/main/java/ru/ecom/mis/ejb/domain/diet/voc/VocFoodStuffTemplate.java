package ru.ecom.mis.ejb.domain.diet.voc;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Шаблон продукта блюда
 * @author azviagin
 *
 */

@Comment("Шаблон продукта блюда")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocFoodStuffTemplate extends BaseEntity{

	/** Продукт */
	@Comment("Продукт")
	@ManyToOne
	public VocFoodStuff getFoodStuff() {
		return foodStuff;
	}
	/** Продукт */
	private VocFoodStuff foodStuff;

	/** Брутто */
	private BigDecimal gross;
	
	/** Месяц начала действия */
	@Comment("Месяц начала действия")
	@OneToOne
	public VocMonth getMonthStart() {
		return monthStart;
	}

	/** Месяц начала действия */
	private VocMonth monthStart;
	
	/** Месяц начала действия (текст) */
	@Comment("Месяц начала действия (текст)")
	@Transient
	public String getMonthStartText() {
		return monthStart!=null ? monthStart.getName() : "";
	}

	public void setMonthStartText(String aMonthStartText) {
	}
	
	/** Месяц окончания действия (включительно) */
	@Comment("Месяц окончания действия (включительно)")
	@OneToOne
	public VocMonth getMonthEnd() {
		return monthEnd;
	}

	/** Месяц окончания действия (включительно) */
	private VocMonth monthEnd;

	/** Месяц окончания действия (текст) */
	@Comment("Месяц окончания действия (текст)")
	@Transient
	public String getMonthEndText() {
		return monthEnd!=null ? monthEnd.getName() : "";
	}
	public void setMonthEndText(String aMonthEndText) {
	}

	/** Продукт питания (текст) */
	@Comment("Продукт питания (текст)")
	@Transient
	public String getFoodStuffText() {
		return foodStuff!=null ? foodStuff.getName() : "";
	}

	public void setFoodStuffText(String aFoodStuffText) {
	}

}
