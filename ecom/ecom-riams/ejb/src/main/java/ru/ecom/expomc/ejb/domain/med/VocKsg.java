package ru.ecom.expomc.ejb.domain.med;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *  КСГ 
 */
@Entity
@Comment("КСГ")
@Table(schema="SQLUser")
public class VocKsg extends VocIdCodeName {
	
	/** Расширение для МКБ */
	@Comment("Расширение для МКБ")
	@OneToOne
	public VocIdc10 getExtendsForIdc10() {
		return theExtendsForIdc10;
	}

	public void setExtendsForIdc10(VocIdc10 aExtendsForIdc10) {
		theExtendsForIdc10 = aExtendsForIdc10;
	}

	/** МКБ10 */
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {
		return theIdc10;
	}

	public void setIdc10(VocIdc10 aIdc10) {
		theIdc10 = aIdc10;
	}

	/** МКБ10 */
	private VocIdc10 theIdc10;
	/** Расширение для МКБ */
	private VocIdc10 theExtendsForIdc10;
	
	/** Уровень оказания для детей */
	@Comment("Уровень оказания для детей")
	@OneToOne
	public VocRenderLevel getChildLevel() {
		return theChildLevel;
	}

	/** Ср. количество дней для детей */
	@Comment("Ср. количество дней для детей")
	public BigDecimal getChildDays() {
		return theChildDays;
	}

	public void setChildDays(BigDecimal aChildDays) {
		theChildDays = aChildDays;
	}

	/** Уровень оказания для взрослых */
	@Comment("Уровень оказания для взрослых")
	@OneToOne
	public VocRenderLevel getAdultLevel() {
		return theAdultLevel;
	}

	public void setAdultLevel(VocRenderLevel aAdultLevel) {
		theAdultLevel = aAdultLevel;
	}

	/** Ср. кол-во дней для взрослых */
	@Comment("Ср. кол-во дней для взрослых")
	public BigDecimal getAdultDays() {
		return theAdultDays;
	}

	public void setAdultDays(BigDecimal aAdultDays) {
		theAdultDays = aAdultDays;
	}

	public void setChildLevel(VocRenderLevel aChildLevel) {
		theChildLevel = aChildLevel;
	}
	
	/** Ср. кол-во дней для взрослых */
	private BigDecimal theAdultDays;
	/** Уровень оказания для взрослых */
	private VocRenderLevel theAdultLevel;
	/** Ср. количество дней для детей */
	private BigDecimal theChildDays;

	/** Уровень оказания для детей */
	private VocRenderLevel theChildLevel;
}
