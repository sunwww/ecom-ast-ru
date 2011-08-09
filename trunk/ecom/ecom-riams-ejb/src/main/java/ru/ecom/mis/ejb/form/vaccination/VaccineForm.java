package ru.ecom.mis.ejb.form.vaccination;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.vaccination.VaccinationNosology;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.ecom.mis.ejb.domain.vaccination.voc.VocVaccinationCommonReaction;
import ru.ecom.mis.ejb.domain.vaccination.voc.VocVaccinationLocalReaction;
import ru.ecom.mis.ejb.domain.vaccination.voc.VocVaccinationMethod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;
/**
 * Вакцина
 */
@Comment("Вакцина")
@EntityForm
@EntityFormPersistance(clazz=Vaccine.class)
@WebTrail(comment = "Вакцина", nameProperties= "name", view="entityView-vac_vaccine.do")
@EntityFormSecurityPrefix("/Policy/Mis/Vaccination/Vaccine")
public class VaccineForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Required
	@Persist
	public String getName() {
		return theName;
	}

	/**
	 * Название
	 */
	public void setName(String a_Property) {
		theName = a_Property;
	}

	/**
	 * Название
	 */
	private String theName;

	/**
	 * Аббревиатура
	 */
	@Comment("Аббревиатура")
	@Required
	@Persist
	public String getAbbrevation() {
		return theAbbrevation;
	}

	/**
	 * Аббревиатура
	 */
	public void setAbbrevation(String a_Property) {
		theAbbrevation = a_Property;
	}

	
	/** Список общих реакций */
	@Comment("Список общих реакций")
	@PersistManyToManyOneProperty(collectionGenericType=VocVaccinationCommonReaction.class)
	public String getCommonReactionList() {
		return theCommonReactionList;
	}

	public void setCommonReactionList(String aCommonReactionList) {
		theCommonReactionList = aCommonReactionList;
	}

	/** Список местных реакций */
	@Comment("Список местных реакций")
	@PersistManyToManyOneProperty(collectionGenericType=VocVaccinationLocalReaction.class)
	public String getLocalReactionList() {
		return theLocalReactionList;
	}

	public void setLocalReactionList(String aLocalReactionList) {
		theLocalReactionList = aLocalReactionList;
	}

	/** Список методов вакцинации */
	@Comment("Список методов вакцинации")
	@PersistManyToManyOneProperty(collectionGenericType=VocVaccinationMethod.class)
	public String getMethodList() {
		return theMethodList;
	}

	/** Список вакцинируемых нозологий */
	@Comment("Список вакцинируемых нозологий")
	@PersistManyToManyOneProperty(collectionGenericType=VaccinationNosology.class)
	public String getNosologyList() {
		return theNosologyList;
	}

	public void setNosologyList(String aNosologyList) {
		theNosologyList = aNosologyList;
	}

	/** Список вакцинируемых нозологий */
	private String theNosologyList;
	public void setMethodList(String aMethodList) {
		theMethodList = aMethodList;
	}

	/** Список методов вакцинации */
	private String theMethodList;
	/** Список местных реакций */
	private String theLocalReactionList;
	/** Список общих реакций */
	private String theCommonReactionList;
	/**
	 * Аббревиатура
	 */
	private String theAbbrevation;

	

}
