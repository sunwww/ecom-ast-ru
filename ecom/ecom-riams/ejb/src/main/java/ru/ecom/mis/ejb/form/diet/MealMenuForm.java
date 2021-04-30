package ru.ecom.mis.ejb.form.diet;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealMenu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Меню блюд
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = MealMenu.class)
@Comment("Меню блюд")
@WebTrail(comment = "Меню блюд", nameProperties= "description", view="entitySubclassView-diet_mealMenu.do")
@Parent(property="diet", parentForm=DietForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealMenu")
@Subclasses(value = { MealMenuOrderForm.class, MealMenuTemplateForm.class, ChildMealMenuTemplateForm.class })
@Setter
public class MealMenuForm extends IdEntityForm{
	
	/** Название диеты */
	@Comment("Название диеты")
	@Persist
	public String getDietName() {return dietName;}

	/** Дата начала действия */
	@Comment("Дата начала действия")
	@DateString @DoDateString @Persist
	public String getDateFrom() {return dateFrom;}

	/** Вид приема пищи */
	@Comment("Вид приема пищи")
	@Persist
	public Long getMealTime() {return mealTime;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return serviceStream;}

	/** Поток обслуживания название */
	@Comment("Поток обслуживания название")
	@Persist
	public String getServiceStreamName() {return serviceStreamName;}

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return lpu;}

	/** Диета */
	@Comment("Диета")
	@Persist
	public Long getDiet() {return diet;}

	/** Список блюд */
	@Comment("Список блюд")
	@Persist
	public String getListDishes() {
		return listDishes;
	}

	/** Название приема пищи */
	@Comment("Название приема пищи")
	@Persist
	public String getMealTimeName() {
		return mealTimeName;
	}

	/** Название приема пищи */
	private String mealTimeName;

	/** Список блюд */
	private String listDishes;
	/** Дата начала действия */
	private String dateFrom;
	/** Вид приема пищи */
	private Long mealTime;
	/** Поток обслуживания */
	private Long serviceStream;
	/** Поток обслуживания название */
	private String serviceStreamName;
	/** ЛПУ */
	private Long lpu;
	/** Диета */
	private Long diet;
	/** Название диеты */
	private String dietName;
	
	

}
