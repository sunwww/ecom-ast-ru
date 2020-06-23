package ru.ecom.mis.ejb.domain.diet;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Блюдо меню приема пищи
 * @author azviagin
 *
 */

@Comment("Блюдо меню приема пищи")
@Entity
@Table(schema="SQLUser")
public class DishMealMenu extends BaseEntity {
	
	/** Блюдо */
	@Comment("Блюдо")
	@OneToOne
	public Dish getDish() {
		return theDish;
	}
	public void setDish(Dish aDish) {
		theDish = aDish;
	}
	private Dish theDish;
	
	/** Меню */
	@Comment("Меню")
	@ManyToOne
	public MealMenu getMenu() {
		return theMenu;
	}
	public void setMenu(MealMenu aMenu) {
		theMenu = aMenu;
	}
	private MealMenu theMenu;

	/** Название блюда */
	@Comment("Название блюда")
	@Transient
	public String getDishName() {
		return theDish!=null?theDish.getName():"";
	}
	public void setDietName(String aName) {}
	}