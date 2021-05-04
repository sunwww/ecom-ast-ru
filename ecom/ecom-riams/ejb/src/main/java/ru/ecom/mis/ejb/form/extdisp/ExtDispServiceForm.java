package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ExtDispService.class)
@Comment("Медицинская услуга по дополнительной диспансеризации")
@WebTrail(comment = "Медицинская услуга по дополнительной диспансеризации", nameProperties= "id", list="entityParentList-extdisp_extDispService.do", view="entityParentView-extdisp_extDispService.do")
@Parent(property="card", parentForm=ExtDispCardForm.class)
@Subclasses(value = { ExtDispVisitForm.class, ExtDispExamForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Service")
@Setter
public class ExtDispServiceForm extends IdEntityForm{
	/**
	 * Карта дополнительной диспансеризации
	 */
	@Comment("Карта дополнительной диспансеризации")
	@Persist
	public Long getCard() {
		return card;
	}
	/**
	 * Карта дополнительной диспансеризации
	 */
	private Long card;
	/**
	 * Дата оказания услуги
	 */
	@Comment("Дата оказания услуги")
	@Persist
	@DateString @DoDateString
	public String getServiceDate() {
		return serviceDate;
	}
	/**
	 * Дата оказания услуги
	 */
	private String serviceDate;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@Persist
	public Long getServiceType() {
		return serviceType;
	}
	/**
	 * Тип услуги
	 */
	private Long serviceType;
}
