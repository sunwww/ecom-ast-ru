package ru.ecom.mis.ejb.domain.diet;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.diet.voc.VocDishType;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Блюдо
 * @author azviagin
 *
 */

@Comment("Блюдо")
@Entity
@Table(schema="SQLUser")
public class Dish extends BaseEntity{
	
	/** Растительные белки */
	@Comment("Растительные белки")
	@Transient
	public BigDecimal getPlantProteins() {
		return calcSum("PlantProteins");
	}

	public void setPlantProteins(BigDecimal aPlantProteins) {
	}

	/** Животные белки */
	@Comment("Животные белки")
	@Transient
	public BigDecimal getAnimalProteins() {
		return calcSum("AnimalProteins");
	}

	public void setAnimalProteins(BigDecimal aAnimalProteins) {
	}
	
	/** Растительные жиры */
	@Comment("Растительные жиры")
	@Transient
	public BigDecimal getPlantLipids() {
		return calcSum("PlantLipids");
	}

	public void setPlantLipids(BigDecimal aPlantLipids) {
	}
	
	/** Животные жиры */
	@Comment("Животные жиры")
	@Transient
	public BigDecimal getAnimalLipids() {
		return calcSum("AnimalLipids");
	}

	public void setAnimalLipids(BigDecimal aAnimalLipids) {
	}
	
	/** Железо */
	@Comment("Железо")
	@Transient
	public BigDecimal getFerrum() {
		return calcSum("Ferrum");
	}

	public void setFerrum(BigDecimal aFerrum) {
	}
	
	/** Фосфор */
	@Comment("Фосфор")
	@Transient
	public BigDecimal getPhosphorus() {
		return calcSum("Phosphorus");
	}

	public void setPhosphorus(BigDecimal aPhosphorus) {
	}
	
	/** Магний */
	@Comment("Магний")
	@Transient
	public BigDecimal getMagnesium() {
		return calcSum("Magnesium");
	}

	public void setMagnesium(BigDecimal aMagnesium) {
	}
	
	/** Кальций */
	@Comment("Кальций")
	@Transient
	public BigDecimal getCalcium() {
		return calcSum("Calcium");
	}

	public void setCalcium(BigDecimal aCalcium) {
	}

	/** Калий */
	@Comment("Калий")
	@Transient
	public BigDecimal getPotassium() {
		return calcSum("Potassium");
	}

	public void setPotassium(BigDecimal aPotassium) {
	}
	
	/** Натрий */
	@Comment("Натрий")
	@Transient
	public BigDecimal getNatrium() {
		return calcSum("Natrium");
	}

	public void setNatrium(BigDecimal aNatrium) {
	}
	
	/** Витамин C */
	@Comment("Витамин C")
	@Transient
	public BigDecimal getCevitamicAcid() {
		return calcSum("CevitamicAcid");
	}

	public void setCevitamicAcid(BigDecimal aCevitamicAcid) {
	}
	
	/** Витамин PP */
	@Comment("Витамин PP")
	@Transient
	public BigDecimal getNicotinamid() {
		return calcSum("Nicotinamid");
	}

	public void setNicotinamid(BigDecimal aNicotinamid) {
	}
	
	/** Витамин B2 */
	@Comment("Витамин B2")
	@Transient
	public BigDecimal getRiboflavin() {
		return calcSum("Riboflavin");
	}

	public void setRiboflavin(BigDecimal aRiboflavin) {
	}
	
	/** Витамин B1 */
	@Comment("Витамин B1")
	@Transient
	public BigDecimal getTiamin() {
		return calcSum("Tiamin");
	}
	public void setTiamin(BigDecimal aTiamin) {
	}
	
	/** Бета-каротин */
	@Comment("Бета-каротин")
	@Transient
	public BigDecimal getBetaCarotin() {
		return calcSum("BetaCarotin");
	}
	public void setBetaCarotin(BigDecimal aBetaCarotin) {
	}

	/** Витамин А */
	@Comment("Витамин А")
	@Transient
	public BigDecimal getRetinol() {
		return calcSum("Retinol");
	}
	public void setRetinol(BigDecimal aRetinol) {
	}

	/** Внешний вид блюда */
	@Comment("Внешний вид блюда")
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
	public BigDecimal getWeight() {
		return theWeight;
	}

	public void setWeight(BigDecimal aWeight) {
		theWeight = aWeight;
	}

	/** Вес */
	private BigDecimal theWeight;
	
	/** Калорийность */
	@Comment("Калорийность")
	@Transient
	public BigDecimal getCalorieContent() {
		return calcSum("CalorieContent");
	}

	public void setCalorieContent(BigDecimal aCalorieContent) {
	}
	
	/** Углеводы */
	@Comment("Углеводы")
	@Transient
	public BigDecimal getCarbohydrates() {
		return calcSum("Carbohydrates");
	}

	public void setCarbohydrates(BigDecimal aCarbohydrates) {
	}
	
	/** Жиры */
	@Comment("Жиры")
	@Transient
	public BigDecimal getLipids() {
		return calcSum("Lipids");
	}

	public void setLipids(BigDecimal aLipids) {
	}
	
	/** Белки */
	@Comment("Белки")
	@Transient
	public BigDecimal getProteins() {
		return calcSum("Proteins");
	}

	public void setProteins(BigDecimal aProteins) {
	}
	
	/** Название диеты */
	@Transient
	public String getDietsShortName() {StringBuilder sb = new StringBuilder() ;
	if (theDiets!=null) {for (Diet diet : theDiets) {sb.append(diet.getShortName() ) ; sb.append(" ,") ;}
	} 
	return sb.toString() ;} 
	
	/** Технология приготовления */
	@Comment("Технология приготовления")
	public String getPreparationTechnology() {
		return thePreparationTechnology;
	}
	public void setPreparationTechnology(String aPreparationTechnology) {
		thePreparationTechnology = aPreparationTechnology;
	}
	/** Технология приготовления */
	private String thePreparationTechnology;
	
	/** Продукты */
	@Comment("Продукты")
	@OneToMany(mappedBy="dish", cascade=CascadeType.ALL)
	public List<DishFoodStuff> getDishFoodStuffs() {
		return theDishFoodStuffs;
	}

	public void setDishFoodStuffs(List<DishFoodStuff> aDishFoodStuffs) {
		theDishFoodStuffs = aDishFoodStuffs;
	}

	/** Продукты */
	private List<DishFoodStuff> theDishFoodStuffs;
	
	/** Наименование */
	@Comment("Наименование")
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
	@OneToOne
	public VocDishType getDishType() {
		return theDishType;
	}

	public void setDishType(VocDishType aDishType) {
		theDishType = aDishType;
	}

	/** Тип блюда */
	private VocDishType theDishType;
	
	/** Диеты */
	@Comment("Диеты")
	@ManyToMany
    public List<Diet> getDiets() {
		return theDiets;
	}

	public void setDiets(List<Diet> aDiets) {
		theDiets = aDiets;
	}
	/** Диеты */
	private List<Diet> theDiets;
	
	@Comment("Сумма значений по свойству")
	public BigDecimal calcSum(String aPropertyName) {
		BigDecimal sum = new BigDecimal(0);
		BigDecimal value;
        if (theDishFoodStuffs!=null) {
        	for (DishFoodStuff foodStuff : theDishFoodStuffs) {
        		value = calcProperty(foodStuff, aPropertyName);
        		sum = value!=null ? sum.add(value) : sum;
        	} 
        }
		return sum.setScale(2,BigDecimal.ROUND_HALF_DOWN);		
	}
	
	@Comment("Расчет свойства продукта питания")
	public BigDecimal calcProperty(DishFoodStuff aFoodStuff, String aPropertyName) {
		BigDecimal ret = new BigDecimal(0);
		if (aFoodStuff!=null){
			Class foodStuffClass = aFoodStuff.getClass();
			String methodName = "get" + aPropertyName;
			try {
				Object value = foodStuffClass.getMethod(methodName).invoke(aFoodStuff);
				ret = value!=null ? (BigDecimal) value : ret;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret.setScale(2,BigDecimal.ROUND_HALF_DOWN);
	};
}
