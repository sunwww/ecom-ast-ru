package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.DocumentPersonalization;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = DocumentPersonalization.class)
@Comment("Персонализация документа")
@WebTrail(comment = "Персонализация документа", nameProperties= "id", list="entityParentList-personaldata_documentPersonalization.do", view="entityParentView-personaldata_documentPersonalization.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class DocumentPersonalizationForm extends IdEntityForm{
	/**
	 * Тип
	 */
	@Comment("Тип")
	@Persist
	public Long getType() {
		return theType;
	}
	public void setType(Long aType) {
		theType = aType;
	}
	/**
	 * Тип
	 */
	private Long theType;
	/**
	 * Внешний носитель
	 */
	@Comment("Внешний носитель")
	@Persist
	public Long getExternalCarrier() {
		return theExternalCarrier;
	}
	public void setExternalCarrier(Long aExternalCarrier) {
		theExternalCarrier = aExternalCarrier;
	}
	/**
	 * Внешний носитель
	 */
	private Long theExternalCarrier;
	/**
	 * ФИО заказчика
	 */
	@Comment("ФИО заказчика")
	@Persist
	public String getCustomerFullname() {
		return theCustomerFullname;
	}
	public void setCustomerFullname(String aCustomerFullname) {
		theCustomerFullname = aCustomerFullname;
	}
	/**
	 * ФИО заказчика
	 */
	private String theCustomerFullname;
	/**
	 * Должность заказчика
	 */
	@Comment("Должность заказчика")
	@Persist
	public String getCustomerPost() {
		return theCustomerPost;
	}
	public void setCustomerPost(String aCustomerPost) {
		theCustomerPost = aCustomerPost;
	}
	/**
	 * Должность заказчика
	 */
	private String theCustomerPost;
}
