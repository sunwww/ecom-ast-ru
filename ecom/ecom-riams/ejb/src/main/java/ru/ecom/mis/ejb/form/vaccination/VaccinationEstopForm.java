package ru.ecom.mis.ejb.form.vaccination;

import lombok.Setter;
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
@Setter
public class VaccinationEstopForm extends IdEntityForm {

	/** Вакцинация */
	@Comment("Вакцинация")
	@Persist @Required
	public Long getVaccination() {
		return vaccination;
	}

	/** Вакцинация */
	private Long vaccination;
	
	/**
	 * Дата медотвода
	 */
	@Comment("Дата медотвода")
	@Persist @Required
	@DateString @DoDateString
	public String getEstopDate() {
		return estopDate;
	}

	/**
	 * Дата медотвода
	 */
	private String estopDate;
	
	/**
	 * Тип медотвода
	 */
	@Comment("Тип медотвода")
	@Persist @Required
	public Long getEstopType() {return estopType;}

	/**
	 * Тип медотвода
	 */
	private Long estopType;

	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	@Persist
	public String getComments() {return comments;}

	/**
	 * Комментарий
	 */
	private String comments;

	/**
	 * Дата окончания медотвода
	 */
	@Comment("Дата окончания медотвода")
	@Persist
	@DateString @DoDateString
	public String getDateFinish() {return dateFinish;}

	/**
	 * Дата окончания медотвода
	 */
	private String dateFinish;

	/**
	 * Недействительность
	 */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return noActuality;}

	/**
	 * Недействительность
	 */
	private Boolean noActuality;

}
