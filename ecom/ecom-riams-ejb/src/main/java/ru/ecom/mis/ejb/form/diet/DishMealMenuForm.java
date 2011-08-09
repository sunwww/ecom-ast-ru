package ru.ecom.mis.ejb.form.diet;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.DishMealMenu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Блюдо меню приема пищи
 * @author oegorova
 */

@EntityForm
@EntityFormPersistance(clazz = DishMealMenu.class)
@Comment("Блюдо меню")
@WebTrail(comment = "Блюдо меню", nameProperties= "menu", view="entityParentView-diet_dishMealMenu.do")
@Parent(property="menu", parentForm=MealMenuForm.class)
@Subclasses(value = { DishMealMenuOrderForm.class, DishMealMenuTemplateForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/DishMealMenu")
public class DishMealMenuForm extends IdEntityForm{
	
	/** Блюдо */
	@Comment("Блюдо")
	@Persist
	public Long getDish() {return theDish;	}
	public void setDish(Long aDish) {theDish = aDish;}
	
	/** Меню */
	@Comment("Меню")
    @Persist
	public Long getMenu() {return theMenu;}
	public void setMenu(Long aMenu) {theMenu = aMenu;}

	/** Название блюда */
	@Comment("Название блюда")
	@Persist
	public String getDishName() {return theDishName;}
	public void setDishName(String aNewProperty) {theDishName = aNewProperty;}

	/** Меню */
	private Long theMenu;
	/** Название блюда */
	private String theDishName;
	/** Блюдо */
	private Long theDish;

}
