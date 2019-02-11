package ru.ecom.expomc.ejb.domain.med;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 *  КСГ 
 */
@Entity
@Comment("КСГ")
@Table(schema="SQLUser")
public class VocKsg extends VocIdCodeName {

	/** Год КСГ */
	@Comment("Год КСГ")
	public Integer getYear() {return theYear;}
	public void setYear(Integer aYear) {theYear = aYear;}
	/** Год КСГ */
	private Integer theYear ;

	/** Сверхдлительный КСГ (45 дней)	*/
	@Comment("Длительный срок лечения КСГ")
	public Boolean getLongKsg() {return theLongKsg;}
	public void setLongKsg(Boolean aLongKsg) {theLongKsg = aLongKsg;}
	/** Длительный срок лечения КСГ */
	private Boolean theLongKsg ;

	/** Является операцией */
	@Comment("Является операцией")
	public Boolean getIsOperation() {return theIsOperation;}
	public void setIsOperation(Boolean aIsOperation) {theIsOperation = aIsOperation;}
	/** Является операцией */
	private Boolean theIsOperation ;

	/** Оплачивать в полном объеме */
	@Comment("Оплачивать в полном объеме")
	public Boolean getIsFullPayment() {return theIsFullPayment;}
	public void setIsFullPayment(Boolean aIsFullPayment) {theIsFullPayment = aIsFullPayment;}
	/** Оплачивать в полном объеме */
	private Boolean theIsFullPayment ;

	/** Коэффициент затрат */
	@Comment("Коэффициент затрат")
	public Double getKZ() {return theKZ;}
	public void setKZ(Double aKZ) {theKZ = aKZ;}
	/** Коэффициент затрат */
	private Double theKZ ;

	/** Профиль помощи */
	@Comment("Профиль помощи")
	public String getProfile() {return theProfile;}
	public void setProfile(String aProfile) {theProfile = aProfile;}
	/** Профиль помощи */
	private String theProfile ;
	
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

	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}
	/** Тип коек */
	private VocBedSubType theBedSubType ;

	/** Не учитывать КУСмо */
	@Comment("Не учитывать КУСмо")
	public Boolean getDoNotUseCusmo() {return theDoNotUseCusmo;}
	public void setDoNotUseCusmo(Boolean aDoNotUseCusmo) {theDoNotUseCusmo = aDoNotUseCusmo;}
	/** Не учитывать КУСмо */
	private Boolean theDoNotUseCusmo = false;


}
