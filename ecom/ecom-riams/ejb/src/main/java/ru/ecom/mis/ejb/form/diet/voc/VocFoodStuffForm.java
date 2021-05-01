package ru.ecom.mis.ejb.form.diet.voc;

import lombok.Setter;
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
@Setter
	public class VocFoodStuffForm  extends IdEntityForm{
		
		/** Наименование */
		@Comment("Наименование")
		@Persist
		public String getName() {
			return name;
		}

		/** Наименование */
		private String name;
	
	    /** Железо */
		@Comment("Железо")
		@Persist
		public String getFerrum() {return ferrum;}
		/** Железо */
		private String ferrum;
		
		/** Фосфор */
		@Comment("Фосфор")
		@Persist
		public String getPhosphorus() {return phosphorus;}

		/** Фосфор */
		private String phosphorus;
		
		/** Магний */
		@Comment("Магний")
		@Persist
		public String getMagnesium() {return magnesium;}

		/** Магний */
		private String magnesium;
		
		/** Кальций */
		@Comment("Кальций")
		@Persist
		public String getCalcium() {return calcium;}

		/** Кальций */
		private String calcium;
		
		/** Калий */
		@Comment("Калий")
		@Persist
		public String getPotassium() {return potassium;}

		/** Калий */
		private String potassium;
		
		/** Натрий */
		@Comment("Натрий")
		@Persist
		public String getNatrium() {return natrium;}

		/** Натрий */
		private String natrium;
		
		/** Витамин C */
		@Comment("Витамин C")
		@Persist
		public String getCevitamicAcid() {return cevitamicAcid;}

		/** Витамин C */
		private String cevitamicAcid;
	
		/** Витамин PP */
		@Comment("Витамин PP")
		@Persist
		public String getNicotinamid() {return nicotinamid;}

		/** Витамин PP */
		private String nicotinamid;
		
		/** Витамин B2 */
		@Comment("Витамин B2")
		@Persist
		public String getRiboflavin() {return riboflavin;}

		/** Витамин B2 */
		private String riboflavin;
		
		/** Витамин B1 */
		@Comment("Витамин B1")
		@Persist
		public String getTiamin() {return tiamin;}

		/** Витамин B1 */
		private String tiamin;
		
		/** Бета-каротин */
		@Comment("Бета-каротин")
		@Persist
		public String getBetaCarotin() {return betaCarotin;}

		/** Бета-каротин */
		private String betaCarotin;
		
		/** Витамин А */
		@Comment("Витамин А")
		@Persist
		public String getRetinol() {return retinol;}

		/** Витамин А */
		private String retinol;
	
		/** Калорийность */
		@Comment("Калорийность")
		@Persist
		public String getCalorieContent() {return calorieContent;}

		/** Калорийность */
		private String calorieContent;
		
		/** Углеводы */
		@Comment("Углеводы")
		@Persist
		public String getCarbohydrates() {return carbohydrates;}

		/** Углеводы */
		private String carbohydrates;
		
		/** Жиры */
		@Comment("Жиры")
		@Persist
		public String getLipids() {return lipids;}

		/** Жиры */
		private String lipids;
		
		/** Белки */
		@Comment("Белки")
		@Persist
		public String getProteins() {return proteins;}

		/** Белки */
		private String proteins;

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
	}
