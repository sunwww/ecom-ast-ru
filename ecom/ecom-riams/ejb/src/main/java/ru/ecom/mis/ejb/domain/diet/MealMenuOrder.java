package ru.ecom.mis.ejb.domain.diet;

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
public class MealMenuOrder extends MealMenu {
	/** Шаблон меню приема пищи */
	@Comment("Шаблон меню приема пищи")
	@OneToOne
	public MealMenuTemplate getTemplateMenu() {
		return theTemplateMenu;
	}
	public void setTemplateMenu(MealMenuTemplate aTemplateMenu) {
		theTemplateMenu = aTemplateMenu;
	}
	private MealMenuTemplate theTemplateMenu;
	
	/** Количество порций */
	@Comment("Количество порций")
	public Integer getPortionAmount() {
		return thePortionAmount;
	}
	public void setPortionAmount(Integer aPortionAmount) {
		thePortionAmount = aPortionAmount;
	}
	private Integer thePortionAmount;
}