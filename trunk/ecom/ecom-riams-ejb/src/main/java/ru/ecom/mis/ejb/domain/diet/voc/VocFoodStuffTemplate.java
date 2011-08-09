package ru.ecom.mis.ejb.domain.diet.voc;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class VocFoodStuffTemplate extends BaseEntity{

	/** Продукт */
	@Comment("Продукт")
	@ManyToOne
	public VocFoodStuff getFoodStuff() {
		return theFoodStuff;
	}

	public void setFoodStuff(VocFoodStuff aFoodStuff) {
		theFoodStuff = aFoodStuff;
	}

	/** Продукт */
	private VocFoodStuff theFoodStuff;

	
	/** Брутто */
	@Comment("Брутто")
	public BigDecimal getGross() {
		return theGross;
	}

	public void setGross(BigDecimal aGross) {
		theGross = aGross;
	}

	/** Брутто */
	private BigDecimal theGross;
	
	/** Месяц начала действия */
	@Comment("Месяц начала действия")
	@OneToOne
	public VocMonth getMonthStart() {
		return theMonthStart;
	}

	public void setMonthStart(VocMonth aMonthStart) {
		theMonthStart = aMonthStart;
	}

	/** Месяц начала действия */
	private VocMonth theMonthStart;
	
	/** Месяц начала действия (текст) */
	@Comment("Месяц начала действия (текст)")
	@Transient
	public String getMonthStartText() {
		return theMonthStart!=null ? theMonthStart.getName() : "";
	}

	public void setMonthStartText(String aMonthStartText) {
	}
	
	/** Месяц окончания действия (включительно) */
	@Comment("Месяц окончания действия (включительно)")
	@OneToOne
	public VocMonth getMonthEnd() {
		return theMonthEnd;
	}

	public void setMonthEnd(VocMonth aMonthEnd) {
		theMonthEnd = aMonthEnd;
	}

	/** Месяц окончания действия (включительно) */
	private VocMonth theMonthEnd;

	/** Месяц окончания действия (текст) */
	@Comment("Месяц окончания действия (текст)")
	@Transient
	public String getMonthEndText() {
		return theMonthEnd!=null ? theMonthEnd.getName() : "";
	}
	public void setMonthEndText(String aMonthEndText) {
	}

	/** Продукт питания (текст) */
	@Comment("Продукт питания (текст)")
	@Transient
	public String getFoodStuffText() {
		return theFoodStuff!=null ? theFoodStuff.getName() : "";
	}

	public void setFoodStuffText(String aFoodStuffText) {
	}

}
