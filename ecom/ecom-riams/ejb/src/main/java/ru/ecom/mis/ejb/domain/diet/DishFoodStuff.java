package ru.ecom.mis.ejb.domain.diet;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.diet.voc.VocFoodStuff;
import ru.ecom.mis.ejb.domain.diet.voc.VocProcessingType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Продукт блюда
 * @author azviagin
 *
 */

@Comment("Продукт блюда")
@Entity
@Table(schema="SQLUser")
public class DishFoodStuff extends BaseEntity{

	/** Нетто */
	@Comment("Нетто")
	public BigDecimal getNet() {
		return theNet;
	}

	public void setNet(BigDecimal aNet) {
		theNet = aNet;
	}

	/** Нетто */
	private BigDecimal theNet;
	
	/** Брутто */
	@Comment("Брутто")
	public BigDecimal getGross() {
		return theGross;
	}

	public void setGross(BigDecimal aGross) {
		theGross = aGross;
	}

	/** Брутто */
	private BigDecimal theGross;
	
	
	/** Железо */
	@Comment("Железо")
	@Transient
	public BigDecimal getFerrum() {
		return calcProperty("Ferrum");
	}

	public void setFerrum(BigDecimal aFerrum) {}

	/** Фосфор */
	@Comment("Фосфор")
	@Transient
	public BigDecimal getPhosphorus() {
		return calcProperty("Phosphorus");
	}

	public void setPhosphorus(BigDecimal aPhosphorus) {}

	/** Магний */
	@Comment("Магний")
	@Transient
	public BigDecimal getMagnesium() {
		return calcProperty("Magnesium");
	}

	public void setMagnesium(BigDecimal aMagnesium) {}

	/** Кальций */
	@Comment("Кальций")
	@Transient
	public BigDecimal getCalcium() {
		return calcProperty("Calcium");
	}

	public void setCalcium(BigDecimal aCalcium) {}

	/** Калий */
	@Comment("Калий")
	@Transient
	public BigDecimal getPotassium() {
		return calcProperty("Potassium");
	}

	public void setPotassium(BigDecimal aPotassium) {}

	/** Натрий */
	@Comment("Натрий")
	@Transient
	public BigDecimal getNatrium() {
		return calcProperty("Natrium");
	}

	public void setNatrium(BigDecimal aNatrium) {}

	/** Витамин C */
	@Comment("Витамин C")
	@Transient
	public BigDecimal getCevitamicAcid() {
		return calcProperty("CevitamicAcid");
	}

	public void setCevitamicAcid(BigDecimal aCevitamicAcid) {}

	/** Витамин PP */
	@Comment("Витамин PP")
	@Transient
	public BigDecimal getNicotinamid() {
		return calcProperty("Nicotinamid");
	}

	public void setNicotinamid(BigDecimal aNicotinamid) {}

	/** Витамин B2 */
	@Comment("Витамин B2")
	@Transient
	public BigDecimal getRiboflavin() {
		return calcProperty("Riboflavin");
	}

	public void setRiboflavin(BigDecimal aRiboflavin) {}

	/** Витамин B1 */
	@Comment("Витамин B1")
	@Transient
	public BigDecimal getTiamin() {
		return calcProperty("Tiamin");
	}

	public void setTiamin(BigDecimal aTiamin) {}

	/** Бета-каротин */
	@Comment("Бета-каротин")
	@Transient
	public BigDecimal getBetaCarotin() {
		return calcProperty("BetaCarotin");
	}

	public void setBetaCarotin(BigDecimal aBetaCarotin) {}
	
	/** Витамин А */
	@Comment("Витамин А")
	@Transient
	public BigDecimal getRetinol() {
		return calcProperty("Retinol");
	}

	public void setRetinol(BigDecimal aRetinol) {}
	
	/** Название продукта */
	@Transient
	public String getName() {return theFoodStuff!=null?theFoodStuff.getName():"";}
	public void setName(String aName) {}

	/** Калорийность */
	@Comment("Калорийность")
	@Transient
	public BigDecimal getCalorieContent() {
		return calcProperty("CalorieContent");
	}

	public void setCalorieContent(BigDecimal aCalorieContent) {}
	
	/** Углеводы */
	@Comment("Углеводы")
	@Transient
	public BigDecimal getCarbohydrates() {
		return calcProperty("Carbohydrates");
	}

	public void setCarbohydrates(BigDecimal aCarbohydrates) {}

	/** Жиры */
	@Comment("Жиры")
	@Transient
	public BigDecimal getLipids() {
		return calcProperty("Lipids");
	}

	public void setLipids(BigDecimal aLipids) {}
	
	/** Белки */
	@Comment("Белки")
	@Transient
	public BigDecimal getProteins() {
		return calcProperty("Proteins");
	}

	public void setProteins(BigDecimal aProteins) {}
	
	/** Растительные белки */
	@Comment("Растительные белки")
	@Transient
	public BigDecimal getPlantProteins() {
		return calcProperty("PlantProteins");
	}

	public void setPlantProteins(BigDecimal aPlantProteins) {
	}

	/** Животные белки */
	@Comment("Животные белки")
	@Transient
	public BigDecimal getAnimalProteins() {
		return calcProperty("AnimalProteins");
	}

	public void setAnimalProteins(BigDecimal aAnimalProteins) {
	}
	
	/** Растительные жиры */
	@Comment("Растительные жиры")
	@Transient
	public BigDecimal getPlantLipids() {
		return calcProperty("PlantLipids");
	}

	public void setPlantLipids(BigDecimal aPlantLipids) {
	}
	
	/** Животные жиры */
	@Comment("Животные жиры")
	@Transient
	public BigDecimal getAnimalLipids() {
		return calcProperty("AnimalLipids");
	}

	public void setAnimalLipids(BigDecimal aAnimalLipids) {
	}
		
	/** Продукт питания */
	@Comment("Продукт питания")
	@OneToOne
	public VocFoodStuff getFoodStuff() {
		return theFoodStuff;
	}

	public void setFoodStuff(VocFoodStuff aFoodStuff) {
		theFoodStuff = aFoodStuff;
	}

	/** Продукт питания */
	private VocFoodStuff theFoodStuff;

	
	/** Блюдо */
	@Comment("Блюдо")
	@ManyToOne
	public Dish getDish() {
		return theDish;
	}

	public void setDish(Dish aDish) {
		theDish = aDish;
	}

	/** Блюдо */
	private Dish theDish;

	/** Тип кулинарной обработки */
	@Comment("Тип кулинарной обработки")
	@OneToOne
	public VocProcessingType getProcessingType() {
		return theProcessingType;
	}

	public void setProcessingType(VocProcessingType aProcessingType) {
		theProcessingType = aProcessingType;
	}

	/** Тип кулинарной обработки */
	private VocProcessingType theProcessingType;


	/** Множитель веса */
	@Comment("Множитель веса")
	@Transient
	public BigDecimal getWeightMultiplier() {
		BigDecimal multi = new BigDecimal(0) ;
		BigDecimal net = getNet();
			if (net!=null) {
				BigDecimal div = new BigDecimal(100);
				multi= net.divide(div);
			}
		return multi;
	}
	public BigDecimal calcProperty(String aPropertyName) {
		BigDecimal ret = new BigDecimal(0);
		if (theFoodStuff!=null){
			Class foodStuffClass = theFoodStuff.getClass();
			String methodName = "get" + aPropertyName;
			Object value;
			try {
				value = foodStuffClass.getMethod(methodName).invoke(theFoodStuff);
				ret = value!=null ? (BigDecimal) value : ret;
				ret= ret.multiply(getWeightMultiplier());
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
			} catch (ArithmeticException e) {
				e.printStackTrace() ;
			}
		}
		return ret.setScale(2,BigDecimal.ROUND_HALF_DOWN);
	};
	
}
