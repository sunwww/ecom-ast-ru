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

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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
	private String dishAppearance;
	
	/** Цвет блюда */
	private String dishColor;
	
	/** Запах блюда */
	private String dishScent;
	
	/** Вкус блюда */
	private String dishTaste;
	
	/** Консистенция блюда */
	private String dishConsistence;
	
	/** Срок годности и условия хранения блюда */
	private String dishStorageConditions;
	
	/** Номер карточки-раскладки */
	private String dishNumber; 
	
	/** Вес */
	private BigDecimal weight;
	
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
	if (diets!=null) {for (Diet diet : diets) {sb.append(diet.getShortName() ) ; sb.append(" ,") ;}
	} 
	return sb.toString() ;} 
	
	/** Технология приготовления */
	private String preparationTechnology;
	
	/** Продукты */
	@Comment("Продукты")
	@OneToMany(mappedBy="dish", cascade=CascadeType.ALL)
	public List<DishFoodStuff> getDishFoodStuffs() {
		return dishFoodStuffs;
	}

	/** Продукты */
	private List<DishFoodStuff> dishFoodStuffs;
	
	/** Наименование */
	private String name;
	
	/** Тип блюда */
	@Comment("Тип блюда")
	@OneToOne
	public VocDishType getDishType() {
		return dishType;
	}

	/** Тип блюда */
	private VocDishType dishType;
	
	/** Диеты */
	@Comment("Диеты")
	@ManyToMany
    public List<Diet> getDiets() {
		return diets;
	}

	/** Диеты */
	private List<Diet> diets;
	
	@Comment("Сумма значений по свойству")
	public BigDecimal calcSum(String aPropertyName) {
		BigDecimal sum = new BigDecimal(0);
		BigDecimal value;
        if (dishFoodStuffs!=null) {
        	for (DishFoodStuff foodStuff : dishFoodStuffs) {
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
