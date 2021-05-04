package ru.ecom.mis.ejb.form.diet;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.DishFoodStuff;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Продукт блюда
 * @author oegorova
 *
 */
@EntityForm
@EntityFormPersistance(clazz = DishFoodStuff.class)
@Comment("Продукт блюда")
@WebTrail(comment = "Продукт блюда", nameProperties= "foodStuff", view="entityView-diet_dishFoodStuff.do")
@Parent(property="dish", parentForm=DishForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/DishFoodStuff")
@Setter
public class DishFoodStuffForm extends IdEntityForm{
	
	/** Железо */
	@Comment("Железо")
	@Persist
	public String getFerrum() {
		return ferrum;
	}

	/** Железо */
	private String ferrum;
	
	/** Фосфор */
	@Comment("Фосфор")
	@Persist
	public String getPhosphorus() {
		return phosphorus;
	}

	/** Фосфор */
	private String phosphorus;
	
	/** Магний */
	@Comment("Магний")
	@Persist
	public String getMagnesium() {
		return magnesium;
	}

	/** Магний */
	private String magnesium;
	
	/** Кальций */
	@Comment("Кальций")
	@Persist
	public String getCalcium() {
		return calcium;
	}

	/** Кальций */
	private String calcium;
	
	/** Калий */
	@Comment("Калий")
	@Persist
	public String getPotassium() {
		return potassium;
	}

	/** Калий */
	private String potassium;
	
	/** Натрий */
	@Comment("Натрий")
	@Persist
	public String getNatrium() {
		return natrium;
	}

	/** Натрий */
	private String natrium;
	
	/** Витамин C */
	@Comment("Витамин C")
	@Persist
	public String getCevitamicAcid() {
		return cevitamicAcid;
	}

	/** Витамин C */
	private String cevitamicAcid;
	
	/** Витамин PP */
	@Comment("Витамин PP")
	@Persist
	public String getNicotinamid() {
		return nicotinamid;
	}

	/** Витамин PP */
	private String nicotinamid;
	
	/** Витамин B2 */
	@Comment("Витамин B2")
	@Persist
	public String getRiboflavin() {
		return riboflavin;
	}

	/** Витамин B2 */
	private String riboflavin;
	
	/** Витамин B1 */
	@Comment("Витамин B1")
	@Persist
	public String getTiamin() {
		return tiamin;
	}

	/** Витамин B1 */
	private String tiamin;
	
	/** Бета-каротин */
	@Comment("Бета-каротин")
	@Persist
	public String getBetaCarotin() {
		return betaCarotin;
	}

	/** Бета-каротин */
	private String betaCarotin;
	
	/** Витамин А */
	@Comment("Витамин А")
	@Persist
	public String getRetinol() {
		return retinol;
	}

	/** Витамин А */
	private String retinol;
	
	/** Название продукта */
	@Comment("Название продукта")
	@Persist
	public String getName() {return name;}

	private String name;	
	
	/** Вес */
	@Comment("Вес")
	public String getWeight() {
		return weight;
	}

	/** Вес */
	private String weight;
	
	/** Калорийность */
	@Comment("Калорийность")
	@Persist
	public String getCalorieContent() {
		return calorieContent;
	}

	/** Калорийность */
	private String calorieContent;
	
	/** Углеводы */
	@Comment("Углеводы")
	@Persist
	public String getCarbohydrates() {
		return carbohydrates;
	}

	/** Углеводы */
	private String carbohydrates;
	
	/** Жиры */
	@Comment("Жиры")
	@Persist
	public String getLipids() {
		return lipids;
	}

	/** Жиры */
	private String lipids;
	
	/** Белки */
	@Comment("Белки")
	@Persist
	public String getProteins() {
		return proteins;
	}

	/** Белки */
	private String proteins;
	
	/** Нетто */
	@Comment("Нетто")
	@Persist
	public String getNet() {
		return net;
	}

	/** Нетто */
	private String net;
	
	/** Брутто */
	@Comment("Брутто")
	@Persist
	public String getGross() {
		return gross;
	}

	/** Брутто */
	private String gross;
	
	/** Продукт питания */
	@Comment("Продукт питания")
	@Persist
	public Long getFoodStuff() {
		return foodStuff;
	}

	/** Продукт питания */
	private Long foodStuff;

	
	/** Блюдо */
	@Comment("Блюдо")
	@Persist
	public Long getDish() {
		return dish;
	}

	/** Блюдо */
	private Long dish;
}
