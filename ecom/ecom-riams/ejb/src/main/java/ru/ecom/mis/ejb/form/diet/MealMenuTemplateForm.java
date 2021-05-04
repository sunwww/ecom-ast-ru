package ru.ecom.mis.ejb.form.diet;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealMenuTemplate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Шаблон меню приема пищи
 * @author oegorova
 *
 */

@Comment("Шаблон меню-раскладки")
@EntityForm
@EntityFormPersistance(clazz = MealMenuTemplate.class)
@WebTrail(comment = "Шаблон меню-раскладки ", nameProperties= "description", view="entityView-diet_mealMenuTemplate.do")
@Parent(property="diet", parentForm=DietForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealMenuTemplate")
@Setter

public class MealMenuTemplateForm extends MealMenuForm{

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	@Persist @DoDateString @DateString
	public String getDateTo() {return dateTo;}

	/** День недели */
	@Comment("День недели")
	@Persist
	public Long getWeekDay() {return weekDay;}

	/** Название дня недели */
	@Comment("Название дня недели")
	@Persist
	public String getWeekDayName() {return weekDayName;}

	/** Шаблон основного меню */
	@Comment("Шаблон основного меню")
	@Persist
	public Long getParentMenu() {
		return parentMenu;
	}

	/** Описание */
	@Comment("Описание")
	@Persist
	public String getDescription() {return description;}

	/** Описание */
	private String description;
	/** Шаблон основного меню */
	private Long parentMenu;
	
	/** Название дня недели */
	private String weekDayName;
	/** День недели */
	private Long weekDay;
	/** Дата окончания действия */
	private String dateTo;
	}
	
	
