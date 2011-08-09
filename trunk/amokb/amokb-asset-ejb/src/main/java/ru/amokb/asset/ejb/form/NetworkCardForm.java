package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.NetworkCard;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = NetworkCard.class)
@Comment("Сетевая карта")
@WebTrail(comment = "Сетевая карта", nameProperties= "id", list="entityParentList-asset_networkCard.do", view="entityParentView-asset_networkCard.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class NetworkCardForm extends ComponentForm{
	/**
	 * MAK адрес
	 */
	@Comment("MAK адрес")
	@Persist
	public String getMAC() {
		return theMAC;
	}
	public void setMAC(String aMAC) {
		theMAC = aMAC;
	}
	/**
	 * MAK адрес
	 */
	private String theMAC;
	/**
	 * IP адрес
	 */
	@Comment("IP адрес")
	@Persist
	public String getIdaddress() {
		return theIdaddress;
	}
	public void setIdaddress(String aIdaddress) {
		theIdaddress = aIdaddress;
	}
	/**
	 * IP адрес
	 */
	private String theIdaddress;
	/**
	 * Тип загрузки
	 */
	@Comment("Тип загрузки")
	@Persist
	public Long getBootType() {
		return theBootType;
	}
	public void setBootType(Long aBootType) {
		theBootType = aBootType;
	}
	/**
	 * Тип загрузки
	 */
	private Long theBootType;
	/**
	 * Оптическая
	 */
	@Comment("Оптическая")
	@Persist
	public Boolean getOptical() {
		return theOptical;
	}
	public void setOptical(Boolean aOptical) {
		theOptical = aOptical;
	}
	/**
	 * Оптическая
	 */
	private Boolean theOptical;
	/**
	 * Скорость в Мб/с
	 */
	@Comment("Скорость в Мб/с")
	@Persist
	public int getSpeed() {
		return theSpeed;
	}
	public void setSpeed(int aSpeed) {
		theSpeed = aSpeed;
	}
	/**
	 * Скорость в Мб/с
	 */
	private int theSpeed;
}
