package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.ExternalCarrierOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalCarrierOperation.class)
@Comment("Операции с внешними электронными носителями")
@WebTrail(comment = "Операции с внешними электронными носителями", nameProperties= "id", list="entityParentList-personaldata_externalCarrierOperation.do", view="entityParentView-personaldata_externalCarrierOperation.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ExternalCarrierOperationForm extends IdEntityForm{
	/**
	 * Внешний электронный носитель
	 */
	@Comment("Внешний электронный носитель")
	@Persist
	public Long getExternalCarrier() {
		return theExternalCarrier;
	}
	public void setExternalCarrier(Long aExternalCarrier) {
		theExternalCarrier = aExternalCarrier;
	}
	/**
	 * Внешний электронный носитель
	 */
	private Long theExternalCarrier;
	/**
	 * Тип операции
	 */
	@Comment("Тип операции")
	@Persist
	public Long getType() {
		return theType;
	}
	public void setType(Long aType) {
		theType = aType;
	}
	/**
	 * Тип операции
	 */
	private Long theType;
}
