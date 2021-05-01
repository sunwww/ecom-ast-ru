package ru.ecom.mis.ejb.form.diet;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.Diet;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Диета
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = Diet.class)
@Comment("Диета")
@WebTrail(comment = "Диета", nameProperties= "name", view="entityParentView-diet_diet.do")
@Parent(property="parent", parentForm=DietForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Diet")
@Setter
public class DietForm extends IdEntityForm{

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return lpu;
	}

	/** ЛПУ */
	private Long lpu;

	/** Описание */
	@Comment("Описание")
	@Persist
	
	public String getDescription() {
		return description;
	}

	/** Описание */
	private String description;
	
	/** Показания к применению */
	@Comment("Показания к применению")
	@Persist
	public String getPrescription() {
		return prescription;
	}

	/** Показания к применению */
	private String prescription;
	
	
	/** Головная диета */
	@Comment("Головная диета")
	@Persist
	public Long getParent() {
		return parent;
	}

	/** Головная диета */
	private Long parent;
	
	/** Короткое название */
	@Comment("Короткое название")
	@Persist
	public String getShortName() {
		return shortName;
	}

	/** Короткое название */
	private String shortName;
	
	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {
		return name;
	}

	/** Название */
	private String name;
	
	/** В архиве */
	@Comment("В архиве")
	@Persist
	public Boolean getIsArchival() {return isArchival;}
	/** В архиве */
	private Boolean isArchival;

}
