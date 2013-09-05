package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Phone;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Phone.class)
@Comment("Телефон")
@WebTrail(comment = "Телефон", nameProperties= "id", list="entityParentList-personaldata_phone.do", view="entityParentView-personaldata_phone.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/Person/Phone")
public class PhoneForm extends IdEntityForm{
	/**
	 * Персоны
	 */
	@Comment("Персоны")
	@Persist
	public Long getPerson() {return thePerson;}
	public void setPerson(Long aPerson) {thePerson = aPerson;}
	/**
	 * Персоны
	 */
	private Long thePerson;
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
	/**
	 * Номер телефона
	 */
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
