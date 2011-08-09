package ru.ecom.mis.ejb.domain.diet.voc;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Продукт питания
 * @author azviagin
 *
 */

@Comment("Продукт питания")
@Entity
@Table(schema="SQLUser")
public class VocFoodStuff extends VocBaseEntity{
	
//	/** Название */
//	@Comment("Название")
//	public String getFoodStuffName() {
//		return theFoodStuffName;
//	}
//
//	public void setFoodStuffName(String aFoodStuffName) {
//		theFoodStuffName = aFoodStuffName;
//	}
//
//	/** Название */
//	private String theFoodStuffName;
	
	/** Железо */
	@Comment("Железо")
	public BigDecimal getFerrum() {
		return theFerrum;
	}

	public void setFerrum(BigDecimal aFerrum) {
		theFerrum = aFerrum;
	}

	/** Железо */
	private BigDecimal theFerrum;
	/** Фосфор */
	@Comment("Фосфор")
	public BigDecimal getPhosphorus() {
		return thePhosphorus;
	}

	public void setPhosphorus(BigDecimal aPhosphorus) {
		thePhosphorus = aPhosphorus;
	}

	/** Фосфор */
	private BigDecimal thePhosphorus;
	/** Магний */
	@Comment("Магний")
	public BigDecimal getMagnesium() {
		return theMagnesium;
	}

	public void setMagnesium(BigDecimal aMagnesium) {
		theMagnesium = aMagnesium;
	}

	/** Магний */
	private BigDecimal theMagnesium;
	/** Кальций */
	@Comment("Кальций")
	public BigDecimal getCalcium() {
		return theCalcium;
	}

	public void setCalcium(BigDecimal aCalcium) {
		theCalcium = aCalcium;
	}

	/** Кальций */
	private BigDecimal theCalcium;
	/** Калий */
	@Comment("Калий")
	public BigDecimal getPotassium() {
		return thePotassium;
	}

	public void setPotassium(BigDecimal aPotassium) {
		thePotassium = aPotassium;
	}

	/** Калий */
	private BigDecimal thePotassium;
	/** Натрий */
	@Comment("Натрий")
	public BigDecimal getNatrium() {
		return theNatrium;
	}

	public void setNatrium(BigDecimal aNatrium) {
		theNatrium = aNatrium;
	}

	/** Натрий */
	private BigDecimal theNatrium;
	/** Витамин C */
	@Comment("Витамин C")
	public BigDecimal getCevitamicAcid() {
		return theCevitamicAcid;
	}

	public void setCevitamicAcid(BigDecimal aCevitamicAcid) {
		theCevitamicAcid = aCevitamicAcid;
	}

	/** Витамин C */
	private BigDecimal theCevitamicAcid;
	/** Витамин PP */
	@Comment("Витамин PP")
	public BigDecimal getNicotinamid() {
		return theNicotinamid;
	}

	public void setNicotinamid(BigDecimal aNicotinamid) {
		theNicotinamid = aNicotinamid;
	}

	/** Витамин PP */
	private BigDecimal theNicotinamid;
	/** Витамин B2 */
	@Comment("Витамин B2")
	public BigDecimal getRiboflavin() {
		return theRiboflavin;
	}

	public void setRiboflavin(BigDecimal aRiboflavin) {
		theRiboflavin = aRiboflavin;
	}

	/** Витамин B2 */
	private BigDecimal theRiboflavin;
	/** Витамин B1 */
	@Comment("Витамин B1")
	public BigDecimal getTiamin() {
		return theTiamin;
	}

	public void setTiamin(BigDecimal aTiamin) {
		theTiamin = aTiamin;
	}

	/** Витамин B1 */
	private BigDecimal theTiamin;
	
	/** Бета-каротин */
	@Comment("Бета-каротин")
	public BigDecimal getBetaCarotin() {
		return theBetaCarotin;
	}

	public void setBetaCarotin(BigDecimal aBetaCarotin) {
		theBetaCarotin = aBetaCarotin;
	}

	/** Бета-каротин */
	private BigDecimal theBetaCarotin;
	
	/** Витамин А */
	@Comment("Витамин А")
	public BigDecimal getRetinol() {
		return theRetinol;
	}

	public void setRetinol(BigDecimal aRetinol) {
		theRetinol = aRetinol;
	}

	/** Витамин А */
	private BigDecimal theRetinol;

	/** Калорийность */
	@Comment("Калорийность")
	public BigDecimal getCalorieContent() {
		return theCalorieContent;
	}

	public void setCalorieContent(BigDecimal aCalorieContent) {
		theCalorieContent = aCalorieContent;
	}

	/** Калорийность */
	private BigDecimal theCalorieContent;
	
	/** Углеводы */
	@Comment("Углеводы")
	public BigDecimal getCarbohydrates() {
		return theCarbohydrates;
	}

	public void setCarbohydrates(BigDecimal aCarbohydrates) {
		theCarbohydrates = aCarbohydrates;
	}

	/** Углеводы */
	private BigDecimal theCarbohydrates;
	
	/** Жиры */
	@Comment("Жиры")
	public BigDecimal getLipids() {
		return theLipids;
	}

	public void setLipids(BigDecimal aLipids) {
		theLipids = aLipids;
	}

	/** Жиры */
	private BigDecimal theLipids;
	
	/** Белки */
	@Comment("Белки")
	public BigDecimal getProteins() {
		return theProteins;
	}

	public void setProteins(BigDecimal aProteins) {
		theProteins = aProteins;
	}

	/** Белки */
	private BigDecimal theProteins;
	
	/** Шаблоны */
	@Comment("Шаблоны")
	@OneToMany(mappedBy="foodStuff", cascade=CascadeType.ALL)
	public List<VocFoodStuffTemplate> getTemplates() {
		return theTemplates;
	}

	public void setTemplates(List<VocFoodStuffTemplate> aTemplates) {
		theTemplates = aTemplates;
	}

	/** Шаблоны */
	private List<VocFoodStuffTemplate> theTemplates;
	
	/** Растительные белки */
	@Comment("Растительные белки")
	public BigDecimal getPlantProteins() {
		return thePlantProteins;
	}

	public void setPlantProteins(BigDecimal aPlantProteins) {
		thePlantProteins = aPlantProteins;
	}

	/** Растительные белки */
	private BigDecimal thePlantProteins;
	
	/** Животные белки */
	@Comment("Животные белки")
	public BigDecimal getAnimalProteins() {
		return theAnimalProteins;
	}

	public void setAnimalProteins(BigDecimal aAnimalProteins) {
		theAnimalProteins = aAnimalProteins;
	}

	/** Животные белки */
	private BigDecimal theAnimalProteins;
	
	/** Растительные жиры */
	@Comment("Растительные жиры")
	public BigDecimal getPlantLipids() {
		return thePlantLipids;
	}

	public void setPlantLipids(BigDecimal aPlantLipids) {
		thePlantLipids = aPlantLipids;
	}

	/** Растительные жиры */
	private BigDecimal thePlantLipids;
	
	/** Животные жиры */
	@Comment("Животные жиры")
	public BigDecimal getAnimalLipids() {
		return theAnimalLipids;
	}

	public void setAnimalLipids(BigDecimal aAnimalLipids) {
		theAnimalLipids = aAnimalLipids;
	}

	/** Животные жиры */
	private BigDecimal theAnimalLipids;

}
