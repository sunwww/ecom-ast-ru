package ru.ecom.mis.ejb.form.diet.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.voc.VocFoodStuffTemplate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Шаблон продукта блюда
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = VocFoodStuffTemplate.class)
@Comment("Шаблон продукта блюда")
@WebTrail(comment = "Шаблон продукта блюда", nameProperties= "id", view="entityView-diet_vocFoodStuffTemplate.do")
@Parent(property = "foodStuff", parentForm = VocFoodStuffForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/VocFoodStuffTemplate")
public class VocFoodStuffTemplateForm  extends IdEntityForm {

	/** Продукт */
	@Comment("Продукт")
	@Persist
	public Long getFoodStuff() {
		return theFoodStuff;
	}

	public void setFoodStuff(Long aFoodStuff) {
		theFoodStuff = aFoodStuff;
	}

	/** Продукт */
	private Long theFoodStuff;

	
	/** Брутто */
	@Comment("Брутто")
	@Persist
	public String getGross() {
		return theGross;
	}

	public void setGross(String aGross) {
		theGross = aGross;
	}

	/** Брутто */
	private String theGross;
	
	/** Месяц начала действия */
	@Comment("Месяц начала действия")
	@Persist
	public Long getMonthStart() {
		return theMonthStart;
	}

	public void setMonthStart(Long aMonthStart) {
		theMonthStart = aMonthStart;
	}

	/** Месяц начала действия */
	private Long theMonthStart;
	
	/** Месяц окончания действия (включительно) */
	@Comment("Месяц окончания действия (включительно)")
	@Persist
	public Long getMonthEnd() {
		return theMonthEnd;
	}

	public void setMonthEnd(Long aMonthEnd) {
		theMonthEnd = aMonthEnd;
	}

	/** Месяц окончания действия (включительно) */
	private Long theMonthEnd;
	
	 /** Месяц начала действия (текст) */
	@Comment("Месяц начала действия (текст)")
	@Persist
	public String getMonthStartText() {
		return theMonthStartText;
	}
	
	public void setMonthStartText(String aMonthStartText) {
		theMonthStartText = aMonthStartText;
	}
	
	/** Месяц начала действия (текст) */
	private String theMonthStartText;
	
	/** Месяц окончания действия (текст) */
	@Comment("Месяц окончания действия (текст)")
	@Persist
	public String getMonthEndText() {
		return theMonthEndText;
	}

	public void setMonthEndText(String aMonthEndText) {
		theMonthEndText = aMonthEndText;
	}

	/** Месяц окончания действия (текст) */
	private String theMonthEndText;

	/** Продукт питания (текст) */
	@Comment("Продукт питания (текст)")
	@Persist
	public String getFoodStuffText() {
		return theFoodStuffText;
	}

	public void setFoodStuffText(String aFoodStuffText) {
		theFoodStuffText = aFoodStuffText;
	}

	/** Продукт питания (текст) */
	private String theFoodStuffText;

}




