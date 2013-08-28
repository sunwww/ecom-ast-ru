package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.IdentificationCard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = IdentificationCard.class)
@Comment("Идентификационная карта")
@WebTrail(comment = "Идентификационная карта", nameProperties= "id", list="entityParentList-personaldata_identificationCard.do", view="entityParentView-personaldata_identificationCard.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class IdentificationCardForm extends IdEntityForm{
	/**
	 * Акт передачи
	 */
	@Comment("Акт передачи")
	@Persist
	public Long getAct() {
		return theAct;
	}
	public void setAct(Long aAct) {
		theAct = aAct;
	}
	/**
	 * Акт передачи
	 */
	private Long theAct;
}
