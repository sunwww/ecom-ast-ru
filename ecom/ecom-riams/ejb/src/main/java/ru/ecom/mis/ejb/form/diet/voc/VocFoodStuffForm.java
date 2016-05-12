package ru.ecom.mis.ejb.form.diet.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.voc.VocFoodStuff;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

	/**
	 * Продукт питания
	 * @author oegorova
	 *
	 */

@EntityForm
@EntityFormPersistance(clazz = VocFoodStuff.class)
@Comment("Продукт питания")
@WebTrail(comment = "Продукт питания", nameProperties= "id", view="entityView-diet_vocFoodStuff.do")
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/VocFoodStuff")
	public class VocFoodStuffForm  extends IdEntityForm{
		
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
	
	    /** Железо */
		@Comment("Железо")
		@Persist
		public String getFerrum() {return theFerrum;}
		public void setFerrum(String aFerrum) {
			theFerrum = aFerrum;
		}
		/** Железо */
		private String theFerrum;
		
		/** Фосфор */
		@Comment("Фосфор")
		@Persist
		public String getPhosphorus() {return thePhosphorus;}
		public void setPhosphorus(String aPhosphorus) {
			thePhosphorus = aPhosphorus;
		}

		/** Фосфор */
		private String thePhosphorus;
		
		/** Магний */
		@Comment("Магний")
		@Persist
		public String getMagnesium() {return theMagnesium;}
		public void setMagnesium(String aMagnesium) {
			theMagnesium = aMagnesium;
		}

		/** Магний */
		private String theMagnesium;
		
		/** Кальций */
		@Comment("Кальций")
		@Persist
		public String getCalcium() {return theCalcium;}
		public void setCalcium(String aCalcium) {
			theCalcium = aCalcium;
		}

		/** Кальций */
		private String theCalcium;
		
		/** Калий */
		@Comment("Калий")
		@Persist
		public String getPotassium() {return thePotassium;}
		public void setPotassium(String aPotassium) {
			thePotassium = aPotassium;
		}

		/** Калий */
		private String thePotassium;
		
		/** Натрий */
		@Comment("Натрий")
		@Persist
		public String getNatrium() {return theNatrium;}
		public void setNatrium(String aNatrium) {
			theNatrium = aNatrium;
		}

		/** Натрий */
		private String theNatrium;
		
		/** Витамин C */
		@Comment("Витамин C")
		@Persist
		public String getCevitamicAcid() {return theCevitamicAcid;}
		public void setCevitamicAcid(String aCevitamicAcid) {
			theCevitamicAcid = aCevitamicAcid;
		}

		/** Витамин C */
		private String theCevitamicAcid;
	
		/** Витамин PP */
		@Comment("Витамин PP")
		@Persist
		public String getNicotinamid() {return theNicotinamid;}
		public void setNicotinamid(String aNicotinamid) {
			theNicotinamid = aNicotinamid;
		}

		/** Витамин PP */
		private String theNicotinamid;
		
		/** Витамин B2 */
		@Comment("Витамин B2")
		@Persist
		public String getRiboflavin() {return theRiboflavin;}
		public void setRiboflavin(String aRiboflavin) {
			theRiboflavin = aRiboflavin;
		}

		/** Витамин B2 */
		private String theRiboflavin;
		
		/** Витамин B1 */
		@Comment("Витамин B1")
		@Persist
		public String getTiamin() {return theTiamin;}
		public void setTiamin(String aTiamin) {
			theTiamin = aTiamin;
		}

		/** Витамин B1 */
		private String theTiamin;
		
		/** Бета-каротин */
		@Comment("Бета-каротин")
		@Persist
		public String getBetaCarotin() {return theBetaCarotin;}
		public void setBetaCarotin(String aBetaCarotin) {
			theBetaCarotin = aBetaCarotin;
		}

		/** Бета-каротин */
		private String theBetaCarotin;
		
		/** Витамин А */
		@Comment("Витамин А")
		@Persist
		public String getRetinol() {return theRetinol;}
		public void setRetinol(String aRetinol) {
			theRetinol = aRetinol;
		}

		/** Витамин А */
		private String theRetinol;
	
		/** Калорийность */
		@Comment("Калорийность")
		@Persist
		public String getCalorieContent() {return theCalorieContent;}
		public void setCalorieContent(String aCalorieContent) {
			theCalorieContent = aCalorieContent;
		}

		/** Калорийность */
		private String theCalorieContent;
		
		/** Углеводы */
		@Comment("Углеводы")
		@Persist
		public String getCarbohydrates() {return theCarbohydrates;}
		public void setCarbohydrates(String aCarbohydrates) {
			theCarbohydrates = aCarbohydrates;
		}

		/** Углеводы */
		private String theCarbohydrates;
		
		/** Жиры */
		@Comment("Жиры")
		@Persist
		public String getLipids() {return theLipids;}
		public void setLipids(String aLipids) {
			theLipids = aLipids;
		}

		/** Жиры */
		private String theLipids;
		
		/** Белки */
		@Comment("Белки")
		@Persist
		public String getProteins() {return theProteins;}
		public void setProteins(String aProteins) {
			theProteins = aProteins;
		}

		/** Белки */
		private String theProteins;

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
	}
