package ru.ecom.mis.ejb.form.vaccination;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.vaccination.VaccinationEstop;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Медотвод
 * @author oegorova
 *
 */

@Comment("Медотвод")
@EntityForm
@EntityFormPersistance(clazz=VaccinationEstop.class)
@WebTrail(comment = "Медотвод", nameProperties= "id", view="entityView-vac_vaccinationEstop.do")
@Parent(property = "vaccination", parentForm =VaccinationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Vaccination/VaccinationEstop")
public class VaccinationEstopForm extends IdEntityForm {

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
	 * Дата медотвода
	 */
	@Comment("Дата медотвода")
	@Persist @Required
	@DateString @DoDateString
	public String getEstopDate() {
		return theEstopDate;
	}

	/**
	 * Дата медотвода
	 */
	public void setEstopDate(String a_Property) {
		theEstopDate = a_Property;
	}

	/**
	 * Дата медотвода
	 */
	private String theEstopDate;
	
	/**
	 * Тип медотвода
	 */
	@Comment("Тип медотвода")
	@Persist @Required
	public Long getEstopType() {return theEstopType;}
	public void setEstopType(Long a_Property) {theEstopType = a_Property;}

	/**
	 * Тип медотвода
	 */
	private Long theEstopType;

	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String a_Property) {theComments = a_Property;}

	/**
	 * Комментарий
	 */
	private String theComments;

	/**
	 * Дата окончания медотвода
	 */
	@Comment("Дата окончания медотвода")
	@Persist
	@DateString @DoDateString
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String a_Property) {theDateFinish = a_Property;}

	/**
	 * Дата окончания медотвода
	 */
	private String theDateFinish;

	/**
	 * Недействительность
	 */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean a_Property) {theNoActuality = a_Property;}

	/**
	 * Недействительность
	 */
	private Boolean theNoActuality;

}
