package ru.ecom.mis.ejb.form.diet.voc;

import lombok.Setter;
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
@Setter
public class VocFoodStuffTemplateForm  extends IdEntityForm {

	/** Продукт */
	@Comment("Продукт")
	@Persist
	public Long getFoodStuff() {
		return foodStuff;
	}

	/** Продукт */
	private Long foodStuff;

	
	/** Брутто */
	@Comment("Брутто")
	@Persist
	public String getGross() {
		return gross;
	}

	/** Брутто */
	private String gross;
	
	/** Месяц начала действия */
	@Comment("Месяц начала действия")
	@Persist
	public Long getMonthStart() {
		return monthStart;
	}

	/** Месяц начала действия */
	private Long monthStart;
	
	/** Месяц окончания действия (включительно) */
	@Comment("Месяц окончания действия (включительно)")
	@Persist
	public Long getMonthEnd() {
		return monthEnd;
	}

	/** Месяц окончания действия (включительно) */
	private Long monthEnd;
	
	 /** Месяц начала действия (текст) */
	@Comment("Месяц начала действия (текст)")
	@Persist
	public String getMonthStartText() {
		return monthStartText;
	}

	/** Месяц начала действия (текст) */
	private String monthStartText;
	
	/** Месяц окончания действия (текст) */
	@Comment("Месяц окончания действия (текст)")
	@Persist
	public String getMonthEndText() {
		return monthEndText;
	}

	/** Месяц окончания действия (текст) */
	private String monthEndText;

	/** Продукт питания (текст) */
	@Comment("Продукт питания (текст)")
	@Persist
	public String getFoodStuffText() {
		return foodStuffText;
	}

	/** Продукт питания (текст) */
	private String foodStuffText;

}




