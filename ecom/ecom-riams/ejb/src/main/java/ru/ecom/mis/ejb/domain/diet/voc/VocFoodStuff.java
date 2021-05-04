package ru.ecom.mis.ejb.domain.diet.voc;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocFoodStuff extends VocBaseEntity{

	/** Железо */
	private BigDecimal ferrum;
	/** Фосфор */
	private BigDecimal phosphorus;
	/** Магний */
	private BigDecimal magnesium;
	/** Кальций */
	private BigDecimal calcium;
	/** Калий */
	private BigDecimal potassium;
	/** Натрий */
	private BigDecimal natrium;
	/** Витамин C */
	private BigDecimal cevitamicAcid;
	/** Витамин PP */
	private BigDecimal nicotinamid;
	/** Витамин B2 */
	private BigDecimal riboflavin;
	/** Витамин B1 */
	private BigDecimal tiamin;
	/** Бета-каротин */
	private BigDecimal betaCarotin;
	/** Витамин А */
	private BigDecimal retinol;
	/** Калорийность */
	private BigDecimal calorieContent;
	/** Углеводы */
	private BigDecimal carbohydrates;
	/** Жиры */
	private BigDecimal lipids;
	/** Белки */
	private BigDecimal proteins;
	
	/** Шаблоны */
	@Comment("Шаблоны")
	@OneToMany(mappedBy="foodStuff", cascade=CascadeType.ALL)
	public List<VocFoodStuffTemplate> getTemplates() {
		return templates;
	}

	/** Шаблоны */
	private List<VocFoodStuffTemplate> templates;
	/** Растительные белки */
	private BigDecimal plantProteins;
	/** Животные белки */
	private BigDecimal animalProteins;
	/** Растительные жиры */
	private BigDecimal plantLipids;
	/** Животные жиры */
	private BigDecimal animalLipids;

}
