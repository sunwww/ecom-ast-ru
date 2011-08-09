package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.NetworkPortLink;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = NetworkPortLink.class)
@Comment("Соединение с сетевым портом")
@WebTrail(comment = "Соединение с сетевым портом", nameProperties= "id", list="entityParentList-asset_networkPortLink.do", view="entityParentView-asset_networkPortLink.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class NetworkPortLinkForm extends IdEntityForm{
	/**
	 * 1-й порт
	 */
	@Comment("1-й порт")
	@Persist
	public Long getPort1() {
		return thePort1;
	}
	public void setPort1(Long aPort1) {
		thePort1 = aPort1;
	}
	/**
	 * 1-й порт
	 */
	private Long thePort1;
	/**
	 * 2-й порт
	 */
	@Comment("2-й порт")
	@Persist
	public Long getPort2() {
		return thePort2;
	}
	public void setPort2(Long aPort2) {
		thePort2 = aPort2;
	}
	/**
	 * 2-й порт
	 */
	private Long thePort2;
	/**
	 * Длина соединения в метрах
	 */
	@Comment("Длина соединения в метрах")
	@Persist
	public String getLinkLength() {
		return theLinkLength;
	}
	public void setLinkLength(String aLinkLength) {
		theLinkLength = aLinkLength;
	}
	/**
	 * Длина соединения в метрах
	 */
	private String theLinkLength;
	/**
	 * Фабричное
	 */
	@Comment("Фабричное")
	@Persist
	public Boolean getFactory() {
		return theFactory;
	}
	public void setFactory(Boolean aFactory) {
		theFactory = aFactory;
	}
	/**
	 * Фабричное
	 */
	private Boolean theFactory;
}
