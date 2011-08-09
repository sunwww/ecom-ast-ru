package ru.ecom.mis.ejb.domain.diet;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Заказ меню приема пищи
 * @author azviagin
 *
 */

@Comment("Заказ меню приема пищи")
@Entity
@Table(schema="SQLUser")
public class MealMenuOrder extends MealMenu{
		
	/** Шаблон меню приема пищи */
	@Comment("Шаблон меню приема пищи")
	@OneToOne
	public MealMenuTemplate getTemplateMenu() {
		return theTemplateMenu;
	}

	public void setTemplateMenu(MealMenuTemplate aTemplateMenu) {
		theTemplateMenu = aTemplateMenu;
	}

	/** Шаблон меню приема пищи */
	private MealMenuTemplate theTemplateMenu;
	
	/** Количество порций */
	@Comment("Количество порций")
	public Integer getPortionAmount() {
		return thePortionAmount;
	}

	public void setPortionAmount(Integer aPortionAmount) {
		thePortionAmount = aPortionAmount;
	}

	/** Количество порций */
	private Integer thePortionAmount;

	
}
