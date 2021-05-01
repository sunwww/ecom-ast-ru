package ru.ecom.mis.ejb.form.vaccination;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.vaccination.VaccinationAssesment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;
/**
 * Оценка вакцинации
 * @author azviagin
 *
 */
@Comment("Оценка вакцинации")
@EntityForm
@EntityFormPersistance(clazz=VaccinationAssesment.class)
@WebTrail(comment = "Оценка", nameProperties= "id", view="entityView-vac_vaccinationAssesment.do")
@Parent(property = "vaccination", parentForm =VaccinationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Vaccination/VaccinationAssesment")
@Setter
public class VaccinationAssesmentForm extends IdEntityForm {

	/** Вакцинация */
	@Comment("Вакцинация")
	@Persist @Required
	public Long getVaccination() {
		return vaccination;
	}

	/** Вакцинация */
	private Long vaccination;
	
	/**
	 * Дата оценки
	 */
	@Comment("Дата оценки")
	@Persist @Required
	@DateString
	@DoDateString
	public String getAssesmentDate() {
		return assesmentDate;
	}

	/**
	 * Дата оценки
	 */
	private String assesmentDate;

	/**
	 * Время оценки
	 */
	@Comment("Время оценки")
	@Persist @Required
	@TimeString
	@DoTimeString
	public String getAssesmentTime() {
		return assesmentTime;
	}

	/**
	 * Время оценки
	 */
	private String assesmentTime;

	/**
	 * Кто оценивал
	 */
	@Comment("Кто оценивал")
	@Persist @Required
	public Long getWorker() {
		return worker;
	}

	/**
	 * Кто оценивал
	 */
	private Long worker;
	
	/**
	 * Комментарий к общей реакции
	 */
	@Comment("Комментарий к общей реакции")
	@Persist
	public String getCommonReactionComment() {
		return commonReactionComment;
	}

	/**
	 * Комментарий к общей реакции
	 */
	private String commonReactionComment;

	/**
	 * Комментарий к локальной реакции
	 */
	@Comment("Комментарий к локальной реакции")
	@Persist
	public String getLocalReactionComment() {
		return localReactionComment;
	}

	/**
	 * Комментарий к локальной реакции
	 */
	private String localReactionComment;

}
