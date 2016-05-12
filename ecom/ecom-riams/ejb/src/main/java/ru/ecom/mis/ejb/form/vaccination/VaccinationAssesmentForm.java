package ru.ecom.mis.ejb.form.vaccination;

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
public class VaccinationAssesmentForm extends IdEntityForm {

	/** Вакцинация */
	@Comment("Вакцинация")
	@Persist @Required
	public Long getVaccination() {
		return theVaccination;
	}

	public void setVaccination(Long aVaccination) {
		theVaccination = aVaccination;
	}

	/** Вакцинация */
	private Long theVaccination;
	
	/**
	 * Дата оценки
	 */
	@Comment("Дата оценки")
	@Persist @Required
	@DateString
	@DoDateString
	public String getAssesmentDate() {
		return theAssesmentDate;
	}

	/**
	 * Дата оценки
	 */
	public void setAssesmentDate(String a_Property) {
		theAssesmentDate = a_Property;
	}

	/**
	 * Дата оценки
	 */
	private String theAssesmentDate;

	/**
	 * Время оценки
	 */
	@Comment("Время оценки")
	@Persist @Required
	@TimeString
	@DoTimeString
	public String getAssesmentTime() {
		return theAssesmentTime;
	}

	/**
	 * Время оценки
	 */
	public void setAssesmentTime(String a_Property) {
		theAssesmentTime = a_Property;
	}

	/**
	 * Время оценки
	 */
	private String theAssesmentTime;

	/**
	 * Кто оценивал
	 */
	@Comment("Кто оценивал")
	@Persist @Required
	public Long getWorker() {
		return theWorker;
	}

	/**
	 * Кто оценивал
	 */
	public void setWorker(Long a_Property) {
		theWorker = a_Property;
	}

	/**
	 * Кто оценивал
	 */
	private Long theWorker;
	
	/**
	 * Комментарий к общей реакции
	 */
	@Comment("Комментарий к общей реакции")
	@Persist
	public String getCommonReactionComment() {
		return theCommonReactionComment;
	}

	/**
	 * Комментарий к общей реакции
	 */
	public void setCommonReactionComment(String a_Property) {
		theCommonReactionComment = a_Property;
	}

	/**
	 * Комментарий к общей реакции
	 */
	private String theCommonReactionComment;

	/**
	 * Комментарий к локальной реакции
	 */
	@Comment("Комментарий к локальной реакции")
	@Persist
	public String getLocalReactionComment() {
		return theLocalReactionComment;
	}

	/**
	 * Комментарий к локальной реакции
	 */
	public void setLocalReactionComment(String a_Property) {
		theLocalReactionComment = a_Property;
	}

	/**
	 * Комментарий к локальной реакции
	 */
	private String theLocalReactionComment;

}
