package ru.ecom.mis.ejb.form.diet;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.DishMealMenu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Заказ блюда меню приема пищи
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = DishMealMenu.class)
@Comment("Заказ блюда меню")
@WebTrail(comment = "Заказ блюда меню", nameProperties= "id", view="entityView-diet_dishMealMenuOrder.do")
@Parent(property="menu", parentForm=MealMenuOrderForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/DishMealMenu")
public class DishMealMenuOrderForm extends DishMealMenuForm {
	
	

}
