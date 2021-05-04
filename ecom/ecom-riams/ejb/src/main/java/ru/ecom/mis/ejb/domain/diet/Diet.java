package ru.ecom.mis.ejb.domain.diet;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
 * Диета
 * @author azviagin
 *
 */

@Comment("Диета")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class Diet extends BaseEntity{

	/** Список меню */
	@Comment("Список меню")
	@OneToMany(mappedBy="diet", cascade=CascadeType.ALL)
	public List<MealMenu> getMenus() {
		return menus;
	}
	/** Список меню */
	private List<MealMenu> menus;
	
	/** Минимальная энергетическая ценность (ккал) */
	private Integer minCalorieContent;
	/** Максимальная энергетическая ценность (ккал) */
	private Integer maxCalorieContent;
	/** Минимальное количество моно- и дисахаридов */
	private Integer minMonoDisaccharides;
	/** Максимальное количество моно- и дисахаридов (г) */
	private Integer maxMonoDisaccharides;
	/** Минимальное количество углеводов (г) */
	private Integer minCarbohydrates;
	/** Максимальное количество углеводов (г)*/
	private Integer maxCarbohydrates;
	/** Минимальное количество растительных жиров (г) */
	private Integer minPlantLipids;
	/** Максимальное количество растительных жиров (г)*/
	private Integer maxPlantLipids;
	/** Минимальное количество общих жиров (г) */
	private Integer minLipids;
	/** Максимальное количество общих жиров (г)*/
	private Integer maxLipids;
	/** Минимальное количество животных белков (г) */
	private Integer minAnimalProteins;
	/** Максимальное количество животных белков (г) */
	private Integer maxAnimalProteins;
	/** Минимальное количество общих белков (г) */
	private Integer minProteins;
	/** Максимальное количество общих белков (г) */
	private Integer maxProteins;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {
		return lpu;
	}
	private MisLpu lpu;

	/** Описание */
	private String description;
	
	/** Показания к применению */
	private String prescription;
	
	/** Блюда */
	@Comment("Блюда")
	@ManyToMany
	public List<Dish> getDishes() {
		return dishes;
	}
	private List<Dish> dishes;
	
	/** Подчиненные диеты */
	@Comment("Подчиненные диеты")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<Diet> getChildren() {
		return children;
	}
	private List<Diet> children;
	
	/** Головная диета */
	@Comment("Головная диета")
	@ManyToOne
	public Diet getParent() {
		return parent;
	}
	private Diet parent;
	/** Короткое название */
	private String shortName;
	/** Название */
	private String name;
	/** В архиве */
	private Boolean isArchival;

}
