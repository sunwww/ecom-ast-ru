package ru.ecom.mis.ejb.form.diet;

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
public class DishForm extends IdEntityForm{
	
	/** Растительные белки */
	@Comment("Растительные белки")
	@Persist
	public String getPlantProteins() {
		return thePlantProteins;
	}

	public void setPlantProteins(String aPlantProteins) {
		thePlantProteins = aPlantProteins;
	}

	/** Растительные белки */
	private String thePlantProteins;
	
	/** Животные белки */
	@Comment("Животные белки")
	@Persist
	public String getAnimalProteins() {
		return theAnimalProteins;
	}

	public void setAnimalProteins(String aAnimalProteins) {
		theAnimalProteins = aAnimalProteins;
	}

	/** Животные белки */
	private String theAnimalProteins;
	
	/** Растительные жиры */
	@Comment("Растительные жиры")
	@Persist
	public String getPlantLipids() {
		return thePlantLipids;
	}

	public void setPlantLipids(String aPlantLipids) {
		thePlantLipids = aPlantLipids;
	}

	/** Растительные жиры */
	private String thePlantLipids;
	
	/** Животные жиры */
	@Comment("Животные жиры")
	@Persist
	public String getAnimalLipids() {
		return theAnimalLipids;
	}

	public void setAnimalLipids(String aAnimalLipids) {
		theAnimalLipids = aAnimalLipids;
	}

	/** Животные жиры */
	private String theAnimalLipids;
	
	/** Железо */
	@Comment("Железо")
	@Persist
	public String getFerrum() {
		return theFerrum;
	}

	public void setFerrum(String aFerrum) {
		theFerrum = aFerrum;
	}

	/** Железо */
	private String theFerrum;
	
	/** Фосфор */
	@Comment("Фосфор")
	@Persist
	public String getPhosphorus() {
		return thePhosphorus;
	}

	public void setPhosphorus(String aPhosphorus) {
		thePhosphorus = aPhosphorus;
	}

	/** Фосфор */
	private String thePhosphorus;
	
	/** Магний */
	@Comment("Магний")
	@Persist
	public String getMagnesium() {
		return theMagnesium;
	}

	public void setMagnesium(String aMagnesium) {
		theMagnesium = aMagnesium;
	}

	/** Магний */
	private String theMagnesium;
	
	/** Кальций */
	@Comment("Кальций")
	@Persist
	public String getCalcium() {
		return theCalcium;
	}

	public void setCalcium(String aCalcium) {
		theCalcium = aCalcium;
	}

	/** Кальций */
	private String theCalcium;
	
	/** Калий */
	@Comment("Калий")
	@Persist
	public String getPotassium() {
		return thePotassium;
	}

	public void setPotassium(String aPotassium) {
		thePotassium = aPotassium;
	}

	/** Калий */
	private String thePotassium;
	
	/** Натрий */
	@Comment("Натрий")
	@Persist
	public String getNatrium() {
		return theNatrium;
	}

	public void setNatrium(String aNatrium) {
		theNatrium = aNatrium;
	}

	/** Натрий */
	private String theNatrium;
	
	/** Витамин C */
	@Comment("Витамин C")
	@Persist
	public String getCevitamicAcid() {
		return theCevitamicAcid;
	}

	public void setCevitamicAcid(String aCevitamicAcid) {
		theCevitamicAcid = aCevitamicAcid;
	}

	/** Витамин C */
	private String theCevitamicAcid;
	
	/** Витамин PP */
	@Comment("Витамин PP")
	@Persist
	public String getNicotinamid() {
		return theNicotinamid;
	}

	public void setNicotinamid(String aNicotinamid) {
		theNicotinamid = aNicotinamid;
	}

	/** Витамин PP */
	private String theNicotinamid;
	
	/** Витамин B2 */
	@Comment("Витамин B2")
	@Persist
	public String getRiboflavin() {
		return theRiboflavin;
	}

	public void setRiboflavin(String aRiboflavin) {
		theRiboflavin = aRiboflavin;
	}

	/** Витамин B2 */
	private String theRiboflavin;
	
	/** Витамин B1 */
	@Comment("Витамин B1")
	@Persist
	public String getTiamin() {
		return theTiamin;
	}

	public void setTiamin(String aTiamin) {
		theTiamin = aTiamin;
	}

	/** Витамин B1 */
	private String theTiamin;
	
	/** Бета-каротин */
	@Comment("Бета-каротин")
	@Persist
	public String getBetaCarotin() {
		return theBetaCarotin;
	}

	public void setBetaCarotin(String aBetaCarotin) {
		theBetaCarotin = aBetaCarotin;
	}

	/** Бета-каротин */
	private String theBetaCarotin;
	
	/** Витамин А */
	@Comment("Витамин А")
	@Persist
	public String getRetinol() {
		return theRetinol;
	}

	public void setRetinol(String aRetinol) {
		theRetinol = aRetinol;
	}

	/** Витамин А */
	private String theRetinol;
	
	/** Внешний вид блюда */
	@Comment("Внешний вид блюда")
	@Persist
	public String getDishAppearance() {
		return theDishAppearance;
	}
	public void setDishAppearance(String aDishAppearance) {
		theDishAppearance = aDishAppearance;
	}
	/** Внешний вид блюда */
	private String theDishAppearance; 
	
	/** Цвет блюда */
	@Comment("Цвет блюда")
	@Persist
	public String getDishColor() {
		return theDishColor;
	}

	public void setDishColor(String aDishColor) {
		theDishColor = aDishColor;
	}

	/** Цвет блюда */
	private String theDishColor;
	
	/** Запах блюда */
	@Comment("Запах блюда")
	@Persist
	public String getDishScent() {
		return theDishScent;
	}

	public void setDishScent(String aDishScent) {
		theDishScent = aDishScent;
	}

	/** Запах блюда */
	private String theDishScent;
	
	/** Вкус блюда */
	@Comment("Вкус блюда")
	@Persist
	public String getDishTaste() {
		return theDishTaste;
	}

	public void setDishTaste(String aDishTaste) {
		theDishTaste = aDishTaste;
	}

	/** Вкус блюда */
	private String theDishTaste;
	
	/** Консистенция блюда */
	@Comment("Консистенция блюда")
	@Persist
	public String getDishConsistence() {
		return theDishConsistence;
	}

	public void setDishConsistence(String aDishConsistence) {
		theDishConsistence = aDishConsistence;
	}

	/** Консистенция блюда */
	private String theDishConsistence;
	
	/** Срок годности и условия хранения блюда */
	@Comment("Срок годности и условия хранения блюда")
	@Persist
	public String getDishStorageConditions() {
		return theDishStorageConditions;
	}

	public void setDishStorageConditions(String aDishStorageConditions) {
		theDishStorageConditions = aDishStorageConditions;
	}

	/** Срок годности и условия хранения блюда */
	private String theDishStorageConditions;
	
	
	/** Номер карточки-раскладки */
	@Comment("Номер карточки-раскладки")
	@Persist
	public String getDishNumber() {
		return theDishNumber;
	}

	public void setDishNumber(String aDishNumber) {
		theDishNumber = aDishNumber;
	}

	/** Номер карточки-раскладки */
	private String theDishNumber; 
	
	
	/** Вес */
	@Comment("Вес")
	@Persist
	public String  getWeight() {
		return theWeight;
	}

	public void setWeight(String aWeight) {
		theWeight = aWeight;
	}

	/** Вес */
	private String theWeight;
	
	/** Калорийность */
	@Comment("Калорийность")
	@Persist
	public String getCalorieContent() {
		return theCalorieContent;
	}

	public void setCalorieContent(String aCalorieContent) {
		theCalorieContent = aCalorieContent;
	}

	/** Калорийность */
	private String theCalorieContent;
	
	/** Углеводы */
	@Comment("Углеводы")
	@Persist
	public String getCarbohydrates() {
		return theCarbohydrates;
	}

	public void setCarbohydrates(String aCarbohydrates) {
		theCarbohydrates = aCarbohydrates;
	}

	/** Углеводы */
	private String theCarbohydrates;
	
	/** Жиры */
	@Comment("Жиры")
	@Persist
	public String getLipids() {
		return theLipids;
	}

	public void setLipids(String aLipids) {
		theLipids = aLipids;
	}

	/** Жиры */
	private String theLipids;
	
	/** Белки */
	@Comment("Белки")
	@Persist
	public String getProteins() {
		return theProteins;
	}

	public void setProteins(String aProteins) {
		theProteins = aProteins;
	}

	/** Белки */
	private String theProteins;
	
	/** Название диеты */
	@Comment("Название диеты")
	@Persist
	public String getDietsShortName() {
		return theDietsShortName;
	}

	public void setDietsShortName(String aDietsShortName) {
		theDietsShortName = aDietsShortName;
	}

	/** Название диеты */
	private String theDietsShortName;
	
	/** Технология приготовления */
	@Comment("Технология приготовления")
	@Persist
	public String getPreparationTechnology() {
		return thePreparationTechnology;
	}

	public void setPreparationTechnology(String aPreparationTechnology) {
		thePreparationTechnology = aPreparationTechnology;
	}

	/** Технология приготовления */
	private String thePreparationTechnology;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Наименование */
	private String theName;
	
	/** Тип блюда */
	@Comment("Тип блюда")
	@Persist
	public Long getDishType() {
		return theDishType;
	}

	public void setDishType(Long aDishType) {
		theDishType = aDishType;
	}

	/** Тип блюда */
	private Long theDishType;
	
	/** Диеты */
	@Comment("Диета")
	@Persist
	@PersistManyToManyOneProperty(collectionGenericType=Diet.class)
	   public String getDiets() {    return theDiets ;}

	   /** Диеты */
	   public void setDiets(String aDiets ) {  theDiets = aDiets ; }
	    
	   /** Диеты */
	   private String theDiets ;
	}
