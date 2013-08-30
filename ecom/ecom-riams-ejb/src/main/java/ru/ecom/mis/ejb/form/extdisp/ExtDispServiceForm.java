package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
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
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ExtDispServiceForm extends IdEntityForm{
	/**
	 * Карта дополнительной диспансеризации
	 */
	@Comment("Карта дополнительной диспансеризации")
	@Persist
	public Long getCard() {
		return theCard;
	}
	public void setCard(Long aCard) {
		theCard = aCard;
	}
	/**
	 * Карта дополнительной диспансеризации
	 */
	private Long theCard;
	/**
	 * Дата оказания услуги
	 */
	@Comment("Дата оказания услуги")
	@Persist
	@DateString @DoDateString
	public String getServiceDate() {
		return theServiceDate;
	}
	public void setServiceDate(String aServiceDate) {
		theServiceDate = aServiceDate;
	}
	/**
	 * Дата оказания услуги
	 */
	private String theServiceDate;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@Persist
	public Long getServiceType() {
		return theServiceType;
	}
	public void setServiceType(Long aServiceType) {
		theServiceType = aServiceType;
	}
	/**
	 * Тип услуги
	 */
	private Long theServiceType;
}
