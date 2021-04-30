package ru.ecom.mis.ejb.domain.diet;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Заказ меню приема пищи
 * @author azviagin
 *
 */

@Comment("Заказ меню приема пищи")
@Entity
@Getter
@Setter
public class MealMenuOrder extends MealMenu {
	/** Шаблон меню приема пищи */
	@Comment("Шаблон меню приема пищи")
	@OneToOne
	public MealMenuTemplate getTemplateMenu() {
		return templateMenu;
	}
	private MealMenuTemplate templateMenu;
	
	/** Количество порций */
	private Integer portionAmount;
}