package ru.ecom.mis.ejb.form.diet;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealMenuOrder;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


/**
 * Заказ меню приема пищи
 * @author oegorova
 *
 */

@Comment("Заказ меню-раскладки")
@EntityForm
@EntityFormPersistance(clazz = MealMenuOrder.class)
@WebTrail(comment = "Заказ меню-раскладки ", nameProperties= "id", view="entityParentView-diet_mealMenuOrder.do")
@Parent(property="diet", parentForm=DietForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealMenuOrder")

public class MealMenuOrderForm extends MealMenuForm {
	
		
	/** Шаблон меню приема пищи */
	@Comment("Шаблон меню приема пищи")
	@Persist
	public Long getTemplateMenu() {
		return theTemplateMenu;
	}

	public void setTemplateMenu(Long aTemplateMenu) {
		theTemplateMenu = aTemplateMenu;
	}

	/** Шаблон меню приема пищи */
	private Long theTemplateMenu;
	
	/** Количество порций */
	@Comment("Количество порций")
	@Persist
	public Integer getPortionAmount() {
		return thePortionAmount;
	}

	public void setPortionAmount(Integer aPortionAmount) {
		thePortionAmount = aPortionAmount;
	}

	/** Количество порций */
	private Integer thePortionAmount;

}
	
