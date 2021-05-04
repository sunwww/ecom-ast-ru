package ru.ecom.mis.ejb.form.diet;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.diet.Diet;
import ru.ecom.mis.ejb.domain.diet.Dish;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Блюдо
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = Dish.class)
@Comment("Блюдо")
@WebTrail(comment = "Блюдо", nameProperties= "name", view="entityView-diet_dish.do")
@EntityFormSecurityPrefix("/Policy/Mis/Dish")
@Setter
public class DishForm extends IdEntityForm{
	
	/** Растительные белки */
	@Comment("Растительные белки")
	@Persist
	public String getPlantProteins() {
		return plantProteins;
	}

	/** Растительные белки */
	private String plantProteins;
	
	/** Животные белки */
	@Comment("Животные белки")
	@Persist
	public String getAnimalProteins() {
		return animalProteins;
	}

	/** Животные белки */
	private String animalProteins;
	
	/** Растительные жиры */
	@Comment("Растительные жиры")
	@Persist
	public String getPlantLipids() {
		return plantLipids;
	}

	/** Растительные жиры */
	private String plantLipids;
	
	/** Животные жиры */
	@Comment("Животные жиры")
	@Persist
	public String getAnimalLipids() {
		return animalLipids;
	}

	/** Животные жиры */
	private String animalLipids;
	
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
	
	/** Внешний вид блюда */
	@Comment("Внешний вид блюда")
	@Persist
	public String getDishAppearance() {
		return dishAppearance;
	}
	/** Внешний вид блюда */
	private String dishAppearance; 
	
	/** Цвет блюда */
	@Comment("Цвет блюда")
	@Persist
	public String getDishColor() {
		return dishColor;
	}

	/** Цвет блюда */
	private String dishColor;
	
	/** Запах блюда */
	@Comment("Запах блюда")
	@Persist
	public String getDishScent() {
		return dishScent;
	}

	/** Запах блюда */
	private String dishScent;
	
	/** Вкус блюда */
	@Comment("Вкус блюда")
	@Persist
	public String getDishTaste() {
		return dishTaste;
	}

	/** Вкус блюда */
	private String dishTaste;
	
	/** Консистенция блюда */
	@Comment("Консистенция блюда")
	@Persist
	public String getDishConsistence() {
		return dishConsistence;
	}

	/** Консистенция блюда */
	private String dishConsistence;
	
	/** Срок годности и условия хранения блюда */
	@Comment("Срок годности и условия хранения блюда")
	@Persist
	public String getDishStorageConditions() {
		return dishStorageConditions;
	}

	/** Срок годности и условия хранения блюда */
	private String dishStorageConditions;
	
	
	/** Номер карточки-раскладки */
	@Comment("Номер карточки-раскладки")
	@Persist
	public String getDishNumber() {
		return dishNumber;
	}

	/** Номер карточки-раскладки */
	private String dishNumber; 
	
	
	/** Вес */
	@Comment("Вес")
	@Persist
	public String  getWeight() {
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
	
	/** Название диеты */
	@Comment("Название диеты")
	@Persist
	public String getDietsShortName() {
		return dietsShortName;
	}

	/** Название диеты */
	private String dietsShortName;
	
	/** Технология приготовления */
	@Comment("Технология приготовления")
	@Persist
	public String getPreparationTechnology() {
		return preparationTechnology;
	}

	/** Технология приготовления */
	private String preparationTechnology;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {
		return name;
	}

	/** Наименование */
	private String name;
	
	/** Тип блюда */
	@Comment("Тип блюда")
	@Persist
	public Long getDishType() {
		return dishType;
	}

	/** Тип блюда */
	private Long dishType;
	
	/** Диеты */
	@Comment("Диета")
	@Persist
	@PersistManyToManyOneProperty(collectionGenericType=Diet.class)
	   public String getDiets() {    return diets ;}
	   /** Диеты */
	   private String diets ;
	}
