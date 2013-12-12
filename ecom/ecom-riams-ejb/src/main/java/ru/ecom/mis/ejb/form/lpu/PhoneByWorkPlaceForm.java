package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.FloorBuilding;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Телефон")
@EntityForm
@EntityFormPersistance(clazz = FloorBuilding.class)
@WebTrail(comment = "Телефон", nameProperties = "phoneNumber"
	, view = "entityView-mis_phonePlace.do")
@Parent(property = "workPlace", parentForm = WorkPlaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/Phone")
public class PhoneByWorkPlaceForm {
	/** Рабочее место */
	@Comment("Рабочее место")
	@Persist @Required
	public Long getWorkPlace() {return theWorkPlace;}
	public void setWorkPlace(Long aWorkPlace) {theWorkPlace = aWorkPlace;}
	
	/** Рабочее место */
	private Long theWorkPlace;
	/**
	 * Тип телефона
	 */
	@Comment("Тип телефона")
	@Persist
	public Long getPhoneType() {return thePhoneType;}
	public void setPhoneType(Long aPhoneType) {thePhoneType = aPhoneType;}
	/**
	 * Тип телефона
	 */
	private Long thePhoneType;
	/**
	 * Номер телефона
	 */
	@Comment("Номер телефона")
	@Persist
	public String getPhoneNumber() {return thePhoneNumber;}
	public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
	/** Номер телефона */
	private String thePhoneNumber;
	/**
	 * Основной телефон
	 */
	@Comment("Основной телефон")
	@Persist
	public Boolean getIsPrimary() {return theIsPrimary;}
	public void setIsPrimary(Boolean aIsPrimary) {theIsPrimary = aIsPrimary;}
	/**
	 * Основной телефон
	 */
	private Boolean theIsPrimary;
	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/**
	 * Комментарий
	 */
	private String theComment;
}
