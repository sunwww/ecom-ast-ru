package ru.ecom.mis.ejb.form.diet;

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
public class DietForm extends IdEntityForm{

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private Long theLpu;

	/** Описание */
	@Comment("Описание")
	@Persist
	
	public String getDescription() {
		return theDescription;
	}

	public void setDescription(String aDescription) {
		theDescription = aDescription;
	}

	/** Описание */
	private String theDescription;
	
	/** Показания к применению */
	@Comment("Показания к применению")
	@Persist
	public String getPrescription() {
		return thePrescription;
	}

	public void setPrescription(String aPrescription) {
		thePrescription = aPrescription;
	}

	/** Показания к применению */
	private String thePrescription;
	
	
	/** Головная диета */
	@Comment("Головная диета")
	@Persist
	public Long getParent() {
		return theParent;
	}

	public void setParent(Long aParent) {
		theParent = aParent;
	}

	/** Головная диета */
	private Long theParent;
	
	/** Короткое название */
	@Comment("Короткое название")
	@Persist
	public String getShortName() {
		return theShortName;
	}

	public void setShortName(String aShortName) {
		theShortName = aShortName;
	}

	/** Короткое название */
	private String theShortName;
	
	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	
	/** В архиве */
	@Comment("В архиве")
	@Persist
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** В архиве */
	private Boolean theIsArchival;

}
