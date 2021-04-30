package ru.ecom.mis.ejb.domain.diet;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DishMealMenu extends BaseEntity {
	
	/** Блюдо */
	@Comment("Блюдо")
	@OneToOne
	public Dish getDish() {
		return dish;
	}
	private Dish dish;
	
	/** Меню */
	@Comment("Меню")
	@ManyToOne
	public MealMenu getMenu() {
		return menu;
	}
	private MealMenu menu;

	/** Название блюда */
	@Comment("Название блюда")
	@Transient
	public String getDishName() {
		return dish!=null?dish.getName():"";
	}
	public void setDietName(String aName) {}
	}