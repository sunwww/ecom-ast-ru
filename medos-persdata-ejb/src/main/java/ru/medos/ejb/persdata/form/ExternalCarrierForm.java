package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.ExternalCarrier;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalCarrier.class)
@Comment("Внешний электронный носитель")
@WebTrail(comment = "Внешний электронный носитель", nameProperties= "id", list="entityParentList-personaldata_externalCarrier.do", view="entityParentView-personaldata_externalCarrier.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ExternalCarrierForm extends IdEntityForm{
	/**
	 * Номер носителя
	 */
	@Comment("Номер носителя")
	@Persist
	public String getExternalCarrierNumber() {
		return theExternalCarrierNumber;
	}
	public void setExternalCarrierNumber(String aExternalCarrierNumber) {
		theExternalCarrierNumber = aExternalCarrierNumber;
	}
	/**
	 * Номер носителя
	 */
	private String theExternalCarrierNumber;
	/**
	 * Акт уничтожения внешних носителей
	 */
	@Comment("Акт уничтожения внешних носителей")
	@Persist
	public Long getDestructionAct() {
		return theDestructionAct;
	}
	public void setDestructionAct(Long aDestructionAct) {
		theDestructionAct = aDestructionAct;
	}
	/**
	 * Акт уничтожения внешних носителей
	 */
	private Long theDestructionAct;
}
