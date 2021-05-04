package ru.ecom.mis.ejb.form.vaccination;

import lombok.Setter;
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
@Setter
public class VaccineForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Required
	@Persist
	public String getName() {
		return name;
	}


	/**
	 * Название
	 */
	private String name;

	/**
	 * Аббревиатура
	 */
	@Comment("Аббревиатура")
	@Required
	@Persist
	public String getAbbrevation() {
		return abbrevation;
	}

	/** Список общих реакций */
	@Comment("Список общих реакций")
	@PersistManyToManyOneProperty(collectionGenericType=VocVaccinationCommonReaction.class)
	public String getCommonReactionList() {
		return commonReactionList;
	}

	/** Список местных реакций */
	@Comment("Список местных реакций")
	@PersistManyToManyOneProperty(collectionGenericType=VocVaccinationLocalReaction.class)
	public String getLocalReactionList() {
		return localReactionList;
	}

	/** Список методов вакцинации */
	@Comment("Список методов вакцинации")
	@PersistManyToManyOneProperty(collectionGenericType=VocVaccinationMethod.class)
	public String getMethodList() {
		return methodList;
	}

	/** Список вакцинируемых нозологий */
	@Comment("Список вакцинируемых нозологий")
	@PersistManyToManyOneProperty(collectionGenericType=VaccinationNosology.class)
	public String getNosologyList() {
		return nosologyList;
	}

	/** Список вакцинируемых нозологий */
	private String nosologyList;

	/** Список методов вакцинации */
	private String methodList;
	/** Список местных реакций */
	private String localReactionList;
	/** Список общих реакций */
	private String commonReactionList;
	/**
	 * Аббревиатура
	 */
	private String abbrevation;
}
