package ru.ecom.mis.ejb.form.diet;


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
public class MealMenuForm extends IdEntityForm{
	
	/** Название диеты */
	@Comment("Название диеты")
	@Persist
	public String getDietName() {return theDietName;}
	public void setDietName(String aDietName) {theDietName = aDietName ;}
			
	/** Дата начала действия */
	@Comment("Дата начала действия")
	@DateString @DoDateString @Persist
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Вид приема пищи */
	@Comment("Вид приема пищи")
	@Persist
	public Long getMealTime() {return theMealTime;}
	public void setMealTime(Long aMealTime) {theMealTime = aMealTime;}
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	
	/** Поток обслуживания название */
	@Comment("Поток обслуживания название")
	@Persist
	public String getServiceStreamName() {return theServiceStreamName;}
	public void setServiceStreamName(String aServiceStreamName) {theServiceStreamName = aServiceStreamName;	}

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}
	
	/** Диета */
	@Comment("Диета")
	@Persist
	public Long getDiet() {return theDiet;}
	public void setDiet(Long aDiet) {theDiet = aDiet;}

	/** Список блюд */
	@Comment("Список блюд")
	@Persist
	public String getListDishes() {
		return theListDishes;
	}

	public void setListDishes(String aListDishes) {
		theListDishes = aListDishes;
	}
	
	/** Название приема пищи */
	@Comment("Название приема пищи")
	@Persist
	public String getMealTimeName() {
		return theMealTimeName;
	}

	public void setMealTimeName(String aMealMenuName) {
		theMealTimeName = aMealMenuName;
	}

	/** Название приема пищи */
	private String theMealTimeName;

	/** Список блюд */
	private String theListDishes;
	/** Дата начала действия */
	private String theDateFrom;
	/** Вид приема пищи */
	private Long theMealTime;
	/** Поток обслуживания */
	private Long theServiceStream;
	/** Поток обслуживания название */
	private String theServiceStreamName;
	/** ЛПУ */
	private Long theLpu;
	/** Диета */
	private Long theDiet;
	/** Название диеты */
	private String theDietName;
	
	

}
