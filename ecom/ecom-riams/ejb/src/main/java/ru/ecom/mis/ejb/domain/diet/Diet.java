package ru.ecom.mis.ejb.domain.diet;

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
public class Diet extends BaseEntity{

	/** Список меню */
	@Comment("Список меню")
	@OneToMany(mappedBy="diet", cascade=CascadeType.ALL)
	public List<MealMenu> getMenus() {
		return theMenus;
	}

	public void setMenus(List<MealMenu> aMenus) {
		theMenus = aMenus;
	}

	/** Список меню */
	private List<MealMenu> theMenus;
	
	/** Минимальная энергетическая ценность (ккал) */
	@Comment("Минимальная энергетическая ценность (ккал)")
	public Integer getMinCalorieContent() {
		return theMinCalorieContent;
	}

	public void setMinCalorieContent(Integer aMinCalorieContent) {
		theMinCalorieContent = aMinCalorieContent;
	}

	/** Минимальная энергетическая ценность (ккал) */
	private Integer theMinCalorieContent;
	
	/** Максимальная энергетическая ценность (ккал) */
	@Comment("Максимальная энергетическая ценность (ккал)")
	public Integer getMaxCalorieContent() {
		return theMaxCalorieContent;
	}

	public void setMaxCalorieContent(Integer aMaxCalorieContent) {
		theMaxCalorieContent = aMaxCalorieContent;
	}

	/** Максимальная энергетическая ценность (ккал) */
	private Integer theMaxCalorieContent;
	
	/** Минимальное количество моно- и дисахаридов */
	@Comment("Минимальное количество моно- и дисахаридов")
	public Integer getMinMonoDisaccharides() {
		return theMinMonoDisaccharides;
	}

	public void setMinMonoDisaccharides(Integer aMinMonoDisaccharides) {
		theMinMonoDisaccharides = aMinMonoDisaccharides;
	}

	/** Минимальное количество моно- и дисахаридов */
	private Integer theMinMonoDisaccharides;
	
	/** Максимальное количество моно- и дисахаридов (г) */
	@Comment("Максимальное количество моно- и дисахаридов (г)")
	public Integer getMaxMonoDisaccharides() {
		return theMaxMonoDisaccharides;
	}

	public void setMaxMonoDisaccharides(Integer aMaxMonoDisaccharides) {
		theMaxMonoDisaccharides = aMaxMonoDisaccharides;
	}

	/** Максимальное количество моно- и дисахаридов (г) */
	private Integer theMaxMonoDisaccharides;
	
	/** Минимальное количество углеводов (г) */
	@Comment("Минимальное количество углеводов (г)")
	public Integer getMinCarbohydrates() {
		return theMinCarbohydrates;
	}

	public void setMinCarbohydrates(Integer aMinCarbohydrates) {
		theMinCarbohydrates = aMinCarbohydrates;
	}

	/** Минимальное количество углеводов (г) */
	private Integer theMinCarbohydrates;
	
	/** Максимальное количество углеводов (г)*/
	@Comment("Максимальное количество углеводов (г)")
	public Integer getMaxCarbohydrates() {
		return theMaxCarbohydrates;
	}

	public void setMaxCarbohydrates(Integer aMaxCarbohydrates) {
		theMaxCarbohydrates = aMaxCarbohydrates;
	}

	/** Максимальное количество углеводов (г)*/
	private Integer theMaxCarbohydrates;
	
	/** Минимальное количество растительных жиров (г) */
	@Comment("Минимальное количество растительных жиров (г)")
	public Integer getMinPlantLipids() {
		return theMinPlantLipids;
	}

	public void setMinPlantLipids(Integer aMinPlantLipids) {
		theMinPlantLipids = aMinPlantLipids;
	}

	/** Минимальное количество растительных жиров (г) */
	private Integer theMinPlantLipids;
	
	/** Максимальное количество растительных жиров (г)*/
	@Comment("Максимальное количество растительных жиров (г)")
	public Integer getMaxPlantLipids() {
		return theMaxPlantLipids;
	}

	public void setMaxPlantLipids(Integer aMaxPlatLipids) {
		theMaxPlantLipids = aMaxPlatLipids;
	}

	/** Максимальное количество растительных жиров (г)*/
	private Integer theMaxPlantLipids;
	
	/** Минимальное количество общих жиров (г) */
	@Comment("Минимальное количество общих жиров (г)")
	public Integer getMinLipids() {
		return theMinLipids;
	}

	public void setMinLipids(Integer aMinLipids) {
		theMinLipids = aMinLipids;
	}

	/** Минимальное количество общих жиров (г) */
	private Integer theMinLipids;
	
	/** Максимальное количество общих жиров (г)*/
	@Comment("Максимальное количество общих жиров (г)")
	public Integer getMaxLipids() {
		return theMaxLipids;
	}

	public void setMaxLipids(Integer aMaxLipids) {
		theMaxLipids = aMaxLipids;
	}

	/** Максимальное количество общих жиров (г)*/
	private Integer theMaxLipids;
	
	/** Минимальное количество животных белков (г) */
	@Comment("Минимальное количество животных белков (г)")
	public Integer getminAnimalProteins() {
		return theminAnimalProteins;
	}

	public void setminAnimalProteins(Integer aminAnimalProteins) {
		theminAnimalProteins = aminAnimalProteins;
	}

	/** Минимальное количество животных белков (г) */
	private Integer theminAnimalProteins;
	
	/** Максимальное количество животных белков (г) */
	@Comment("Максимальное количество животных белков (г)")
	public Integer getmaxAnimalProteins() {
		return themaxAnimalProteins;
	}

	public void setmaxAnimalProteins(Integer amaxAnimalProteins) {
		themaxAnimalProteins = amaxAnimalProteins;
	}

	/** Максимальное количество животных белков (г) */
	private Integer themaxAnimalProteins;
	
	/** Минимальное количество общих белков (г) */
	@Comment("Минимальное количество общих белков (г)")
	public Integer getminProteins() {
		return theminProteins;
	}
	public void setminProteins(Integer aminProteins) {
		theminProteins = aminProteins;
	}
	private Integer theminProteins;
	
	/** Максимальное количество общих белков (г) */
	@Comment("Максимальное количество общих белков (г)")
	public Integer getmaxProteins() {
		return themaxProteins;
	}
	public void setmaxProteins(Integer amaxProteins) {
		themaxProteins = amaxProteins;
	}
	private Integer themaxProteins;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {
		return theLpu;
	}
	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}
	private MisLpu theLpu;

	/** Описание */
	@Comment("Описание")
	
	public String getDescription() {
		return theDescription;
	}
	
	public void setDescription(String aDescription) {
		theDescription = aDescription;
	}
	
	private String theDescription;
	
	/** Показания к применению */
	@Comment("Показания к применению")
	public String getPrescription() {
		return thePrescription;
	}
	public void setPrescription(String aPrescription) {
		thePrescription = aPrescription;
	}
	private String thePrescription;
	
	/** Блюда */
	@Comment("Блюда")
	@ManyToMany
	public List<Dish> getDishes() {
		return theDishes;
	}
	public void setDishes(List<Dish> aDishes) {
		theDishes = aDishes;
	}
	private List<Dish> theDishes;
	
	/** Подчиненные диеты */
	@Comment("Подчиненные диеты")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<Diet> getChildren() {
		return theChildren;
	}
	public void setChildren(List<Diet> aChildren) {
		theChildren = aChildren;
	}
	private List<Diet> theChildren;
	
	/** Головная диета */
	@Comment("Головная диета")
	@ManyToOne
	public Diet getParent() {
		return theParent;
	}
	public void setParent(Diet aParent) {
		theParent = aParent;
	}
	private Diet theParent;
	
	/** Короткое название */
	@Comment("Короткое название")
	public String getShortName() {
		return theShortName;
	}
	public void setShortName(String aShortName) {
		theShortName = aShortName;
	}
	private String theShortName;
	
	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	private String theName;
	
	/** В архиве */
	@Comment("В архиве")
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** В архиве */
	private Boolean theIsArchival;

}
